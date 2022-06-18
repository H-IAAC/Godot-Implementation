package com.example.game.godot

import godot.Timer
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.NodePath
import godot.core.Vector2

@RegisterClass
class Car: Element() {
    val DEFAULT_TIME_PER_MOVE = 2.0
    val DEFAULT_DIR = Vector2(1, 0)

    var dir = Vector2(1, 0)
    var time = 2.0

    lateinit var timer: Timer

    @RegisterFunction
    override fun _ready() {
        super._ready()

        timer = getNode(NodePath("Timer")) as Timer
        timer.connect("timeout", this, "endTimer")
    }

    @RegisterFunction
    fun initialize(d: Vector2, t: Double) {
        dir = d

        time = t
        timer.start(time)
    }

    @RegisterFunction
    fun endTimer() {
        timer.start(time)

        turn(dir)
    }

    fun turn(dir: Vector2) {
        if (canMove(dir)) {
            move(dir)
        } else {
            queueFree()
        }
    }

}