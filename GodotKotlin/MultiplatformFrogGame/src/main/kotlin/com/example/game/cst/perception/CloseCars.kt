package com.example.game.cst.perception

import br.unicamp.cst.core.entities.Codelet
import br.unicamp.cst.core.entities.MemoryObject
import com.example.game.godot.Car
import godot.core.Vector2
import kotlin.math.abs

/*
    From the knownCarsMO, creates a list of the n closest cars and stores it in the closestCarsMO. This list will always
    have n elements, so if there are fewer than n cars in long-term memory, out of bound cars will be created to fill
    the list
*/

class CloseCars: Codelet() {
    val TOTAL_CLOSE_CARS = 5
    val OUT_OF_BOUNDS_POSITION = Vector2(-999, -999)

    var positionMO: MemoryObject? = null
    var knownCarsMO: MemoryObject? = null
    var closestCarsMO: MemoryObject? = null

    private fun manhattanDistance(v1: Vector2, v2: Vector2): Double {
        return abs(v1[0] - v2[0]) + abs(v1[1] - v2[1])
    }

    private fun insertionSort(l: ArrayList<Car>, pos: Vector2) {
        for (i in 1 until l.size) {
            var j = i - 1
            var e = l[i]

            while(j >= 0 && manhattanDistance(pos, l[i].position) > manhattanDistance(pos, l[i].position)) {
                l[j + 1] = l[j]
                j -= 1
            }

            l[j + 1] = e
        }
    }

    override fun accessMemoryObjects() {
        positionMO = getInput("POSITION") as MemoryObject
        knownCarsMO = getInput("KNOWN_CARS") as MemoryObject
        closestCarsMO = getOutput("CLOSEST_CARS") as MemoryObject
    }

    override fun calculateActivation() {
        TODO("Not yet implemented")
    }

    override fun proc() {
        var memoryList = knownCarsMO?.i as ArrayList<Car>

        insertionSort(memoryList, positionMO?.i as Vector2)

        var closestCars = ArrayList<Car>()
        for (i in 0 until TOTAL_CLOSE_CARS) {
            if (i < memoryList.size) {
                closestCars.add(memoryList[i])
            } else {
                var newCar = Car()
                newCar.position = OUT_OF_BOUNDS_POSITION
                closestCars.add(newCar)
            }
        }

        closestCarsMO?.i = closestCars

    }
}