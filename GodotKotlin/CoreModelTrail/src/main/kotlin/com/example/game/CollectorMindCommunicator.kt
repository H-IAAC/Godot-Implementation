package com.example.game

import com.example.game.cst.AgentMind
import com.example.game.environment.Apple
import com.example.game.environment.Player
import godot.Node
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class CollectorMindCommunicator: Node() {
	lateinit var mind: AgentMind
	lateinit var player: Player

	companion object {
		lateinit var instance: CollectorMindCommunicator
	}

	@RegisterFunction
	override fun _ready() {
		instance = this

		mind = AgentMind(this)
	}

	@RegisterFunction
	fun clearApple(apple: Apple) {
		mind.clearApple(apple)
	}

	/*
		Agent Requests
	*/

	@RegisterFunction
	fun getPosition(): Vector2 {
		return player.getPos()
	}

	@RegisterFunction
	fun getApplesInReach(): ArrayList<Apple> {
		return player.getApplesInTouch()
	}

	@RegisterFunction
	fun getApplesInVision(): ArrayList<Apple> {
		return player.getApplesInVision()
	}

	@RegisterFunction
	fun eatApple(apple: Apple) {
		player.eatApple(apple)
		clearApple(apple)
	}

	@RegisterFunction
	fun forage() {
		player.forage()
	}

	@RegisterFunction
	fun goTo(pos: Vector2) {
		player.goTo(pos)
	}
}
