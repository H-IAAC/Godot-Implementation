package com.example.game.test

import godot.Node2D
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.global.GD

@RegisterClass
class KtTest: Node2D(){
    @RegisterFunction
    override fun _ready() {
        GD.print(getParent().get_info())
    }
}