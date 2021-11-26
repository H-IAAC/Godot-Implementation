package com.example.game.environment

import godot.core.Vector2

interface Player {
    fun getPos(): Vector2
    fun getApplesInVision(): ArrayList<Apple>
    fun getApplesInTouch(): ArrayList<Apple>
    fun eatApple(apple: Apple)
    fun forage()
    fun goTo(pos: Vector2)
}