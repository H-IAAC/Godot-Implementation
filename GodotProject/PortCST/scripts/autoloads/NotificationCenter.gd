extends Node2D

onready var VALUES = {"TOUCH": $Control/Values/ApplesInReach, "POSITION": $Control/Values/Position, "VISION": $Control/Values/ApplesInSight, "KNOWN_APPLES": $Control/Values/KnownApples, "CLOSEST_APPLE": $Control/Values/ClosestApple, "HANDS": $Control/Values/Hands, "LEGS": $Control/Values/Legs}
onready var notification_label = $Control/Notification

func create_notification(notification):
	notification_label.text += "\n>" + notification


func update_value(new_value, value_code):
	VALUES[value_code].text = value_code + " - " + new_value
