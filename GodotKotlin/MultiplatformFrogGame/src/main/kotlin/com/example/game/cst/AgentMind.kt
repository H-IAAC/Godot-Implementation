package com.example.game.cst

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.Mind
import com.example.game.FrogMindCommunicator
import com.example.game.cst.behavior.TestValueBasedRL
import com.example.game.cst.motor.LookAction
import com.example.game.cst.motor.MoveAction
import com.example.game.cst.perception.CarCleaning
import com.example.game.cst.perception.CarDetection
import com.example.game.cst.perception.CloseCars
import com.example.game.cst.sensor.InnerSense
import com.example.game.cst.sensor.Vision
import com.example.game.godot.Car
import godot.core.Vector2

class AgentMind(var communicator: FrogMindCommunicator) : Mind() {
    init {
        // Declare Memory Objects
        var positionMO = createMemoryObject("POSITION", Vector2(0, 0))
        var visionMO = createMemoryObject("VISION", ArrayList<Car>())
        var knownCarsMO = createMemoryObject("KNOWN_CARS", ArrayList<Car>())
        var closestCarsMO = createMemoryObject("CLOSEST_CARS", ArrayList<Car>())
        var lookActionMO = createMemoryObject("LOOK_ACTION", 0)
        var moveActionMO = createMemoryObject("MOVE_ACTION", 0)

        // Create Sensor Codelets
        val innerSense = InnerSense(communicator)
        innerSense.addOutput(positionMO)
        insertCodelet(innerSense, "SENSOR")

        val vision = Vision(communicator)
        vision.addOutput(visionMO)
        insertCodelet(vision, "SENSOR")

        // Create Perception Codelets
        val carDetection = CarDetection()
        carDetection.addInput(visionMO)
        carDetection.addOutput(knownCarsMO)
        insertCodelet(carDetection, "PERCEPTION")

        val carCleaning = CarCleaning()
        carCleaning.addInput(positionMO)
        carCleaning.addInput(knownCarsMO)
        carCleaning.addOutput(knownCarsMO)
        insertCodelet(carCleaning, "PERCEPTION")

        val closeCars = CloseCars()
        closeCars.addInput(positionMO)
        closeCars.addInput(knownCarsMO)
        closeCars.addOutput(closestCarsMO)
        insertCodelet(closeCars, "PERCEPTION")

        // Create Motor Codelets
        val lookAction = LookAction()
        lookAction.addInput(lookActionMO)
        insertCodelet(lookAction, "MOTOR")

        val moveAction = MoveAction()
        moveAction.addInput(moveActionMO)
        insertCodelet(moveAction, "MOTOR")

        // Create Behavior Codelets
        val testValueBasedRL = TestValueBasedRL()
        testValueBasedRL.addInput(positionMO)
        testValueBasedRL.addInput(closestCarsMO)
        testValueBasedRL.addOutput(moveActionMO)
        testValueBasedRL.addOutput(lookActionMO)

        // Start Cognitive Cycle
        start()
    }
}