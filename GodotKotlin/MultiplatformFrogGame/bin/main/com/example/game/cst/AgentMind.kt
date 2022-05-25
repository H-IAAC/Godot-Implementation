package com.example.game.cst

import br.unicamp.cst.core.entities.Mind
import com.example.game.FrogMindCommunicator
import com.example.game.cst.behavior.Domain
import com.example.game.cst.perception.CarCleaning
import com.example.game.cst.perception.CarDetection
import com.example.game.cst.perception.CloseCars
import com.example.game.cst.perception.StateManager
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
        var stateMO = createMemoryObject("STATE", ArrayList<Domain<Double>>())
        var lastStateMO = createMemoryObject("LAST_STATE", ArrayList<Domain<Double>>())

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

        val stateManager = StateManager()
        stateManager.addInput(positionMO)
        stateManager.addInput(closestCarsMO)
        stateManager.addOutput(stateMO)
        stateManager.addOutput(lastStateMO)

        // Create Behavior Codelets
        TODO("Create behavior structure")

        // Start Cognitive Cycle
        start()
    }
}