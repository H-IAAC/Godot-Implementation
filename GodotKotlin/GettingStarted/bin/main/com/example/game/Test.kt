package com.example.game

import godot.RichTextLabel
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.global.GD

@RegisterClass
class Test: RichTextLabel() {

	@RegisterFunction
	override fun _ready() {
		bbcodeText = "[center]Now we're talking!"
	}
}
