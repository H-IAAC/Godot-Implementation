extends "res://scenes/scripts/Element.gd"


var dir = Vector2(0, 0)


func _ready():
	animation_player.play("idle_" + str(randi() % 3))


func initialize(d):
	dir = d


func turn():
	if can_move(Vector2(0, 0)) or can_move(dir):
		move(dir)
	else:
		queue_free()
