package com.example.godotrl.cst.perception;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import com.example.godotrl.util.Updater;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;

public class CarDetection extends Codelet {
    MemoryObject visionMO = null;
    MemoryObject knownCarsMO = null;
    MemoryObject updateMO = null;

    @Override
    public void accessMemoryObjects() {
        visionMO = (MemoryObject) getInput("VISION");
        knownCarsMO = (MemoryObject) getOutput("KNOWN_CARS");
        updateMO = (MemoryObject) getOutput("UPDATE");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        if (((Updater) updateMO.getI()).canUpdateCarRead()) {
            ArrayList<Vector2> visionList = (ArrayList<Vector2>) visionMO.getI();
            ArrayList<Vector2> memoryList = (ArrayList<Vector2>) knownCarsMO.getI();

            for (Vector2 v : visionList) {
                if (!memoryList.contains(v)) {
                    memoryList.add(v);
                }
            }

            ((Updater) updateMO.getI()).updateCarRead();
        }
    }
}
