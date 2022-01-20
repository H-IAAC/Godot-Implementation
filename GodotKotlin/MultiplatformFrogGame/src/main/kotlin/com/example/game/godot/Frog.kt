package com.example.game.godot

import com.example.game.FrogMindCommunicator
import godot.Area2D
import godot.Input
import godot.KinematicBody2D
import godot.Node2D
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.NodePath
import godot.core.Vector2
import godot.global.GD
import kotlin.math.abs

@RegisterClass
class Frog: Element() {
	val VISION_SIZE = 5

	var carsInVision: ArrayList<Car> = ArrayList()

	lateinit var mindCommunicator: FrogMindCommunicator
	lateinit var visionArea: Area2D

	@RegisterFunction
	override fun _ready() {
		super._ready()

		mindCommunicator = getNode(NodePath("MindCommunicator")) as FrogMindCommunicator
		mindCommunicator.player = this

		visionArea = getNode(NodePath("VisionArea")) as Area2D
	}

	@RegisterFunction
	override fun _physicsProcess(delta: Double) {
		if (Input.isActionJustPressed("up")) {
			turn(Vector2(0, -1))
		} else if (Input.isActionJustPressed("down")) {
			turn(Vector2(0, 1))
		} else if (Input.isActionJustPressed("left")) {
			turn(Vector2(-1, 0))
		} else if (Input.isActionJustPressed("right")) {
			turn(Vector2(1, 0))
		}
	}

	@RegisterFunction
	fun turn(dir: Vector2) {
		if (canMove(dir)) {
			move(dir)
		}
	}

	@RegisterFunction
	fun manhattanDistance(a: Node2D, b: Node2D): Double {
		return abs(a.position[0] - b.position[0]) + abs(a.position[1] - b.position[1])
	}

	@RegisterFunction
	fun getVision(): ArrayList<Car> {
		return carsInVision
	}

	@RegisterFunction
	fun reset(body: KinematicBody2D) {
		if (body != this) {
			base.reset()
		}
	}

	@RegisterFunction
	fun enterVisionArea(body: KinematicBody2D) {
		carsInVision.add(body as Car)

		GD.print("Car entered")
	}

	@RegisterFunction
	fun exitVisionArea(body: KinematicBody2D) {
		carsInVision.remove(body as Car)

		GD.print("Car exited")
	}
}
