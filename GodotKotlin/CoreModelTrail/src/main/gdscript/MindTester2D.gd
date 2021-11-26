extends Node2D


var Apple2D = preload("res://scenes/Apple2D.tscn") 


const MIN_X = 20
const MAX_X = 940
const MIN_Y = 20
const MAX_Y = 520
const SPAWN_TIME = 5
const STARTING_APPLES = 4

var total_apples = 1

onready var apples = $Apples
onready var timer = $Timer


func _ready():
	randomize()
	
	for i in range(STARTING_APPLES):
		spawn_apple()
	
	timer.start(SPAWN_TIME)


func to_menu():
	var player = $Player
	var mind = player.mind
	player.free()
	
	if mind != null:
		mind.stop()


func on_timeout():
	spawn_apple()
	timer.start(SPAWN_TIME)


func spawn_apple():
	var new_apple = Apple2D.instance()
	new_apple.position = Vector2(rand_range(MIN_X, MAX_X), rand_range(MIN_Y, MAX_Y))
	new_apple.set_code(str(total_apples))
	total_apples += 1
	apples.add_child(new_apple)
