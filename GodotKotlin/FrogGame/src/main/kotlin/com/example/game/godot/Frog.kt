package com.example.game.godot

import godot.Input
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Vector2

@RegisterClass
class Frog: Element() {
	@RegisterFunction
	override fun _physicsProcess(delta: Double) {
		if (Input.isActionJustPressed("up")) {
			turn(Vector2(0, -1))
		} else if (Input.isActionJustPressed("down")) {
			turn(Vector2(0, 1))
		} else if (Input.isActionJustPressed("left")) {
			turn(Vector2(-1, 0))
		} else if (Input.isActionJustPressed("up")) {
			turn(Vector2(1, 0))
		}
	}

	@RegisterFunction
	fun turn(dir: Vector2) {
		if (canMove(dir)) {
			move(dir)
		}
	}
}
