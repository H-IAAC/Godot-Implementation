package com.example.game.environment

import com.example.game.NotificationCenter
import godot.CPUParticles2D
import godot.KinematicBody2D
import godot.PackedScene
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Vector2
import godot.global.GD
import godot.global.GD.load

@RegisterClass
class Apple2D: KinematicBody2D(), Apple {
	var appleCode: String = ""

	@RegisterFunction
	fun setCode(code: String) {
		appleCode = code
	}

	@RegisterFunction
	override fun getPos(): Vector2 {
		return position
	}

	@RegisterFunction
	override fun getCode(): String {
		return appleCode
	}

	@RegisterFunction
	override fun getEaten() {
		GD.print("Apple $appleCode got eaten!")
		queueFree()
	}
}
