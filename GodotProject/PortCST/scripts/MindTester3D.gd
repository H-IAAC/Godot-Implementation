extends Spatial


export (PackedScene) var Apple3D


const MIN_X = -11
const MAX_X = 11
const MIN_Y = -37.8
const MAX_Y = -5.5
const SPAWN_TIME = 4
const STARTING_APPLES = 4

const STARTING_HEIGHT = 0.4

var total_apples = 1

onready var apples = $Apples
onready var timer = $Timer
onready var camera_view = $CameraView
onready var player_camera = $Player3D/Viewport/Camera


func _ready():
	randomize()
	
	for i in range(STARTING_APPLES):
		spawn_apple()
	
	timer.start(SPAWN_TIME)


func to_menu():
	var player = $Player3D
	var mind = player.mind
	player.free()
	
	if mind != null:
		mind.stop()
	
	SceneSwitcher.goto_scene("res://Menu.tscn")


func on_timeout():
	spawn_apple()
	timer.start(SPAWN_TIME)


func spawn_apple():
	var new_apple = Apple3D.instance()
	new_apple.translation = Vector3(rand_range(MIN_X, MAX_X), STARTING_HEIGHT, rand_range(MIN_Y, MAX_Y))
	new_apple.apple_name = str(total_apples)
	total_apples += 1
	apples.add_child(new_apple)
