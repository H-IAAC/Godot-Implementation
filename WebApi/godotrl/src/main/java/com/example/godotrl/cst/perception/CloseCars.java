package com.example.godotrl.cst.perception;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.MemoryObject;
import com.example.godotrl.util.Updater;
import com.example.godotrl.util.Vector2;

import java.util.ArrayList;

public class CloseCars extends Codelet {
    MemoryObject knownCarsMO = null;
    MemoryObject positionMO = null;
    MemoryObject closestCarsMO = null;
    MemoryObject updateMO = null;

    private final int TOTAL_CLOSE_CARS = 5;
    private final Vector2 OUT_OF_BOUNDS_V = new Vector2(-999, -999);

    private static void insertionSort(ArrayList<Vector2> l, Vector2 pos) {
        for (int i = 1; i < l.size(); i++) {
            int j = i - 1;
            Vector2 e = l.get(i);

            while (j >= 0 && pos.manhattanDistance(e) > pos.manhattanDistance(l.get(j))) {
                l.set(j + 1, l.get(j));
                j -= 1;
            }

            l.set(j + 1, e);
        }
    }

    @Override
    public void accessMemoryObjects() {
        knownCarsMO = (MemoryObject) getInput("KNOWN_CARS");
        positionMO = (MemoryObject) getInput("POSITION");
        closestCarsMO = (MemoryObject) getOutput("CLOSEST_CARS");
        updateMO = (MemoryObject) getOutput("UPDATE");
    }

    @Override
    public void calculateActivation() {

    }

    @Override
    public void proc() {
        if (((Updater) updateMO.getI()).updateCloseCar()) {
            ArrayList<Vector2> memoryList = (ArrayList<Vector2>) knownCarsMO.getI();

            insertionSort(memoryList, (Vector2) positionMO.getI());

            ArrayList<Vector2> closestCars = new ArrayList<Vector2>();
            for (int i = 0; i < TOTAL_CLOSE_CARS; i++) {
                if (i < memoryList.size()) {
                    closestCars.add(memoryList.get(i));
                } else {
                    closestCars.add(OUT_OF_BOUNDS_V.copy());
                }
            }

            closestCarsMO.setI(closestCars);
        }
    }
}
