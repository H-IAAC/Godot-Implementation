package com.example.game.godot

import godot.AnimationPlayer
import godot.Area2D
import godot.RayCast2D
import godot.Sprite
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.NodePath
import godot.core.Vector2

@RegisterClass
open class Element: Area2D() {
    val CELL_SIZE = 16
    val TO_CELL_CENTER = Vector2(8, 8)
    var RAYCAST_LENGHT = 24

    val LERP_ACCEL = 0.4

    lateinit var cellPos: Vector2

    lateinit var sprite: Sprite
    lateinit var animationPlayer: AnimationPlayer
    lateinit var raycast: RayCast2D
    lateinit var base: LevelBase
    lateinit var camera: ShakeableCamera

    @RegisterFunction
    override fun _ready() {
        cellPos = Vector2((position[0]/CELL_SIZE).toInt(), (position[1]/CELL_SIZE).toInt())

        sprite = getNode(NodePath("Sprite")) as Sprite
        animationPlayer = getNode(NodePath("AnimationPlayer")) as AnimationPlayer
        raycast = getNode(NodePath("Raycast")) as RayCast2D
        base = getParent()?.getParent() as LevelBase
        camera = getParent()?.getNode(NodePath("Camera")) as ShakeableCamera
    }

    @RegisterFunction
    override fun _process(delta: Double) {
        sprite.position = sprite.position.linearInterpolate(Vector2(0, 0), LERP_ACCEL)
    }

    @RegisterFunction
    fun move(dir: Vector2) {
        cellPos += dir
        position = cellPos * CELL_SIZE
        sprite.position -= dir * CELL_SIZE
    }

    @RegisterFunction
    fun canMove(dir: Vector2): Boolean {
        return base.isInMap(cellPos + dir)
    }
}