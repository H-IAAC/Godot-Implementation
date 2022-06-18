package com.example.game.godot

import godot.Node2D
import godot.TileMap
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.NodePath
import godot.core.Vector2

@RegisterClass
class LevelBase: Node2D() {
	var hTiles = 5
	var vTiles = 5

	lateinit var tilemap: TileMap

	@RegisterFunction
	override fun _ready() {
		initialize(5, 5)

		tilemap = getNode(NodePath("TileMap")) as TileMap
	}

	@RegisterFunction
	fun initialize(hSize: Int, vSize: Int) {
		hTiles = hSize
		vTiles = vSize
	}

	@RegisterFunction
	fun isInMap(pos: Vector2): Boolean {
		return (0 <= pos[0] && pos[0] < hTiles) && (0 <= pos[1] && pos[1] < vTiles)
	}
}
