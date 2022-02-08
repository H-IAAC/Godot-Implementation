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

	@RegisterFunction
	override fun _ready() {
		super._ready()
	}

	@RegisterFunction
	fun initialize(d: Vector2, t: Double) {
		dir = d
	}

	@RegisterFunction
	fun turn() {
		if (canMove(dir)) {
			move(dir)
		} else {
			queueFree()
		}
	}

}
