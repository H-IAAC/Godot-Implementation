package com.example.game

import com.example.game.cst.AgentMind
import com.example.game.godot.Car
import com.example.game.godot.Frog
import godot.KinematicBody2D
import godot.Node
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.Vector2
import godot.global.GD

/*
	Communicator between the player's body (Frog) and the player's mind (AgentMind).
	Instanced in the player's body and creates the player's mind
*/

@RegisterClass
class FrogMindCommunicator: Node() {
	lateinit var mind: AgentMind
	lateinit var player: Frog

	/*
		Initializes the player's mind
	*/
	@RegisterFunction
	override fun _ready() {
		GD.print("Attempting to initialize mind")
		mind = AgentMind(this)
		GD.print("Mind initialized")
	}

	@RegisterFunction
	fun print(message: String) {
		GD.print(message)
	}

	/*
		Returns a list of cars in the player's vision
	*/
	@RegisterFunction
	fun getVision(): ArrayList<Car> {
		return player.getVision()
	}

	/*
		Returns the player's position
	*/
	fun getPosition(): Vector2 {
		return player.cellPos
	}

	fun moveUp() {
		player.move(Vector2(0, -1))
	}

	fun moveRight() {
		player.move(Vector2(1, 0))
	}

	fun moveDown() {
		player.move(Vector2(0, 1))
	}

	fun moveLeft() {
		player.move(Vector2(-1, 0))
	}

	/*
	* Reset the game and returns the initial state.
	 */
	fun reset() {
		player.reset(KinematicBody2D())
	}

	/*
	Display the game.
	 */
	fun render() {
		return
	}

	fun step(action: Int): Boolean {
		when (action) {
			0 -> moveUp()
			1 -> moveRight()
			2 -> moveDown()
			3 -> moveLeft()
		}

		return true
	}
}
