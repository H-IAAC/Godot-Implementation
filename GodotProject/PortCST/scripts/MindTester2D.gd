extends Node2D


export (PackedScene) var Apple2D


const MIN_X = 25
const MAX_X = 867
const MIN_Y = 25
const MAX_Y = 387
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
	
	SceneSwitcher.goto_scene("res://Menu.tscn")


func on_timeout():
	spawn_apple()
	timer.start(SPAWN_TIME)


func spawn_apple():
	var new_apple = Apple2D.instance()
	new_apple.position = Vector2(rand_range(MIN_X, MAX_X), rand_range(MIN_Y, MAX_Y))
	new_apple.apple_name = str(total_apples)
	total_apples += 1
	apples.add_child(new_apple)
