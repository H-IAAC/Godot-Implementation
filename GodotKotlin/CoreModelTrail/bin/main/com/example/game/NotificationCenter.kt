package com.example.game

import godot.Node2D
import godot.RichTextLabel
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.NodePath

@RegisterClass
class NotificationCenter: Node2D() {
	val MAX_TEXT_LENGHT = 2000

	var map = hashMapOf<String, RichTextLabel?>()
	lateinit var notificationLabel: RichTextLabel

	companion object {
		lateinit var instance: NotificationCenter
	}

	@RegisterFunction
	override fun _ready() {
		val KEYS = arrayOf("TOUCH", "POSITION", "VISION", "KNOWN_APPLES", "CLOSEST_APPLE", "HANDS", "LEGS")
		val PATHS = arrayOf("Control/Values/ApplesInReach", "Control/Values/Position", "Control/Values/ApplesInSight", "Control/Values/KnownApples", "Control/Values/ClosestApple", "Control/Values/Hands", "Control/Values/Legs")
		for (i in 0..KEYS.size) {
			map[KEYS[i]] = getNode(NodePath(PATHS[i])) as RichTextLabel?
		}

		notificationLabel = getNode(NodePath("Control/Notification")) as RichTextLabel

		instance = this
	}

	@RegisterFunction
	fun createNotification(notification: String) {
		if (notificationLabel.text.length > MAX_TEXT_LENGHT) notificationLabel.text = ""
		notificationLabel.text += "\n> $notification"
	}

	@RegisterFunction
	fun updateValue(newValue: String, code: String) {
		if (code in map) map.get(code)?.text = "$code - $newValue"
	}
}
