package com.example.game.environment

import godot.Node
import godot.Node2D
import godot.Timer
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.NodePath
import godot.core.Vector2
import godot.global.GD
import kotlin.random.Random

@RegisterClass
class Environment2D: Node2D() {
	val MIN_X = 25.0
	val MAX_X = 867.0
	val MIN_Y = 25.0
	val MAX_Y = 387.0
	val SPAWN_TIME = 5.0
	val STARTING_APPLES = 4

	var totalApples = 1

	lateinit var apples: Node2D
	lateinit var timer: Timer

	@RegisterFunction
	override fun _ready() {
		apples = getNode(NodePath("Apples")) as Node2D
		timer = getNode(NodePath("Timer")) as Timer

		for (i in 0..STARTING_APPLES) {
			spawnApple()
		}

		timer.start(SPAWN_TIME)
	}

	@RegisterFunction
	fun toMenu() {
		(getNode(NodePath("Player")) as Player2D).queueFree()
	}

	@RegisterFunction
	fun spawnApple() {
		var newApple = Apple2D()
		newApple.position = Vector2(Random.nextDouble(MIN_X, MAX_X), Random.nextDouble(MIN_Y, MAX_Y))
		newApple.appleCode = "$totalApples"
		totalApples++
		apples.addChild(newApple)

		GD.print("Instanced apple")
	}
}
