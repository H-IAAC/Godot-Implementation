package com.example.game.environment

import godot.core.Vector2

interface Apple {
    fun getPos(): Vector2
    fun getCode(): String
    fun getEaten()
    fun queueFree()
}