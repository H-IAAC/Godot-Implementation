package com.example.game.test

import godot.Node2D
import godot.PackedScene
import godot.ResourceLoader
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.global.GD

@RegisterClass
class KtTest: Node2D(){
	@RegisterFunction
	override fun _ready() {
		GD.print("Ready called")
		var TestScene = ResourceLoader.load("res://scenes/TestSprite.tscn") as PackedScene
		var testScene = TestScene.instance()
		GD.print("Scene instanced")

		if (testScene != null) {
			addChild(testScene)
			GD.print("Added child")
		} else {
			GD.print("Failed to add child")
		}
	}
}
