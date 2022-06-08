package com.example.collectoragentmind;

import com.example.collectoragentmind.codelets.behaviors.EatApple;
import com.example.collectoragentmind.codelets.behaviors.Forage;
import com.example.collectoragentmind.codelets.behaviors.GoToClosestApple;
import com.example.collectoragentmind.codelets.motor.HandActionCodelet;
import com.example.collectoragentmind.codelets.motor.LegActionCodelet;
import com.example.collectoragentmind.codelets.perception.AppleDetector;
import com.example.collectoragentmind.codelets.perception.ClosestAppleDetector;
import com.example.collectoragentmind.codelets.sensors.InnerSense;
import com.example.collectoragentmind.codelets.sensors.Touch;
import com.example.collectoragentmind.codelets.sensors.Vision;
import com.example.collectoragentmind.util.Apple;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;

public class AgentMind extends Mind {
    private MemoryObject externalTouchMO;
    private MemoryObject externalPositionMO;
    private MemoryObject externalVisionMO;
    private MemoryObject externalknownApplesMO;

    public AgentMind() {
        // Declare Memory Objects
        MemoryObject touchMO = createMemoryObject("TOUCH", new ArrayList<Apple>());
        MemoryObject positionMO = createMemoryObject("POSITION", null);
        MemoryObject visionMO = createMemoryObject("VISION", new ArrayList<Apple>());
        MemoryObject knownApplesMO = createMemoryObject("KNOWN_APPLES", new ArrayList<Apple>());
        MemoryObject closestAppleMO = createMemoryObject("CLOSEST_APPLE", null);
        MemoryObject handsMO = createMemoryObject("HANDS", new ArrayList<Apple>());
        MemoryObject legsMO = createMemoryObject("LEGS", null);

        // Create Sensor Codelets
        Codelet touch = new Touch();
        insertCodelet(touch, "SENSOR");

        Codelet innerSense = new InnerSense();
        insertCodelet(innerSense, "SENSOR");

        Codelet vision = new Vision();
        insertCodelet(vision, "SENSOR");

        // Create Perception Codelets
        Codelet appleDetector = new AppleDetector();
        appleDetector.addInput(visionMO);
        appleDetector.addOutput(knownApplesMO);
        insertCodelet(appleDetector, "PERCEPTION");

        Codelet closestAppleDetector = new ClosestAppleDetector();
        closestAppleDetector.addInput(positionMO);
        closestAppleDetector.addInput(knownApplesMO);
        closestAppleDetector.addOutput(closestAppleMO);
        insertCodelet(closestAppleDetector, "PERCEPTION");

        // Create Motor Codelets
        Codelet handActionCodelet = new HandActionCodelet();
        handActionCodelet.addInput(handsMO);
        insertCodelet(handActionCodelet, "MOTOR");

        Codelet legActionCodelet = new LegActionCodelet();
        legActionCodelet.addInput(legsMO);
        insertCodelet(legActionCodelet, "MOTOR");

        // Create Behavior Codelets
        Codelet forage = new Forage();
        forage.addInput(knownApplesMO);
        forage.addOutput(legsMO);
        insertCodelet(forage, "BEHAVIOR");

        Codelet eatApple = new EatApple();
        eatApple.addInput(touchMO);
        eatApple.addOutput(handsMO);
        insertCodelet(eatApple, "BEHAVIOR");

        Codelet goToClosestApple = new GoToClosestApple();
        goToClosestApple.addInput(closestAppleMO);
        goToClosestApple.addOutput(legsMO);
        insertCodelet(goToClosestApple, "BEHAVIOR");

        // Start Cognitive Cycle
        start();

        // Store external references for plugin
        externalPositionMO = positionMO;
        externalTouchMO = touchMO;
        externalVisionMO = visionMO;
        externalknownApplesMO = knownApplesMO;
    }

    public MemoryObject getPositionMO() {
        return externalPositionMO;
    }

    public MemoryObject getTouchMO() {
        return externalTouchMO;
    }

    public MemoryObject getVisionMO() {
        return externalVisionMO;
    }

    public MemoryObject getKnownApplesMO() {
        return externalknownApplesMO;
    }
}
