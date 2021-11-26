package com.example.game.cst

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import br.unicamp.cst.core.entities.Mind
import com.example.game.CollectorMindCommunicator
import com.example.game.cst.codelets.behaviors.EatApple
import com.example.game.cst.codelets.behaviors.Forage
import com.example.game.cst.codelets.behaviors.GoToClosestApple
import com.example.game.cst.codelets.motor.HandActionCodelet
import com.example.game.cst.codelets.motor.LegActionCodelet
import com.example.game.cst.codelets.perception.AppleDetector
import com.example.game.cst.codelets.perception.ClosestAppleDetector
import com.example.game.cst.codelets.sensors.InnerSense
import com.example.game.cst.codelets.sensors.Touch
import com.example.game.cst.codelets.sensors.Vision
import com.example.game.environment.Apple

class AgentMind(var communicator: CollectorMindCommunicator) : Mind() {
    var touchMO: MemoryObject
    var visionMO: MemoryObject
    var knownApplesMO: MemoryObject
    var handsMO: MemoryObject
    var closestAppleMO: MemoryObject

    init {
        // Declare Memory Objects
        touchMO = createMemoryObject("TOUCH", ArrayList<Apple>())
        val positionMO = createMemoryObject("POSITION", null)
        visionMO = createMemoryObject("VISION", ArrayList<Apple>())
        knownApplesMO = createMemoryObject("KNOWN_APPLES", ArrayList<Apple>())
        closestAppleMO = createMemoryObject("CLOSEST_APPLE", null)
        handsMO = createMemoryObject("HANDS", ArrayList<Apple>())
        val legsMO = createMemoryObject("LEGS", null)

        // Create Sensor Codelets
        val touch: Codelet = Touch(communicator)
        touch.addOutput(touchMO)
        insertCodelet(touch, "SENSOR")
        val innerSense: Codelet = InnerSense(communicator)
        innerSense.addOutput(positionMO)
        insertCodelet(innerSense, "SENSOR")
        val vision: Codelet = Vision(communicator)
        vision.addOutput(visionMO)
        insertCodelet(vision, "SENSOR")

        // Create Perception Codelets
        val appleDetector: Codelet = AppleDetector()
        appleDetector.addInput(visionMO)
        appleDetector.addOutput(knownApplesMO)
        insertCodelet(appleDetector, "PERCEPTION")
        val closestAppleDetector: Codelet = ClosestAppleDetector()
        closestAppleDetector.addInput(positionMO)
        closestAppleDetector.addInput(knownApplesMO)
        closestAppleDetector.addOutput(closestAppleMO)
        insertCodelet(closestAppleDetector, "PERCEPTION")

        // Create Motor Codelets
        val handActionCodelet: Codelet = HandActionCodelet(communicator)
        handActionCodelet.addInput(handsMO)
        insertCodelet(handActionCodelet, "MOTOR")
        val legActionCodelet: Codelet = LegActionCodelet(communicator)
        legActionCodelet.addInput(legsMO)
        insertCodelet(legActionCodelet, "MOTOR")

        // Create Behavior Codelets
        val forage: Codelet = Forage()
        forage.addInput(knownApplesMO)
        forage.addOutput(legsMO)
        insertCodelet(forage, "BEHAVIOR")
        val eatApple: Codelet = EatApple()
        eatApple.addInput(touchMO)
        eatApple.addOutput(handsMO)
        insertCodelet(eatApple, "BEHAVIOR")
        val goToClosestApple: Codelet = GoToClosestApple()
        goToClosestApple.addInput(closestAppleMO)
        goToClosestApple.addOutput(legsMO)
        insertCodelet(goToClosestApple, "BEHAVIOR")

        // Start Cognitive Cycle
        start()
    }

    fun clearApple(apple: Apple) {
        clearAppleFromMO(apple, touchMO)
        clearAppleFromMO(apple, visionMO)
        clearAppleFromMO(apple, knownApplesMO)
        clearAppleFromMO(apple, handsMO)
        if (closestAppleMO.i == apple) {
            closestAppleMO.i = null
        }
    }

    fun clearAppleFromMO(apple: Apple, MO: MemoryObject) {
        var appleList = MO.i as ArrayList<Apple>

        if (appleList.contains(apple)) {
            appleList.remove(apple)
        }

        MO.i = appleList
    }
}