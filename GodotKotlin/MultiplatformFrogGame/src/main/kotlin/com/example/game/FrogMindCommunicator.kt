package com.example.game

import com.example.game.godot.Car
import godot.Node
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Vector2

@RegisterClass
class FrogMindCommunicator: Node() {
    @RegisterFunction
    fun getVision(): ArrayList<Car> {
        return ArrayList()
    }

    fun getPosition(): Vector2 {
        return Vector2(0, 0)
    }
}