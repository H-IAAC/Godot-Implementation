extends Node2D

const MAX_NOTIFS = 100


var total_notifs = 0

onready var VALUES = {"POSITION": $Control/Values/Position, "VELOCITY": $Control/Values/Velocity, "BASIS": $Control/Values/Basis, "ACCELERATION": $Control/Values/Acceleration}
onready var notification_label = $Control/Notification

func create_notification(notification):
	if total_notifs >= MAX_NOTIFS:
		notification_label.text = ""
		total_notifs = 0
	notification_label.text += "\n>" + notification
	total_notifs += 1


func update_value(new_value, value_code):
	VALUES[value_code].text = value_code + " - " + new_value
