extends Node2D

const Frog = preload("res://scenes/Frog.tscn")
const Car = preload("res://scenes/Car.tscn")

# -----------------------------------------------------------------------------
# CONSTANTS
# -----------------------------------------------------------------------------


const TURNS_FOR_SPAWN = 2

const H_SIZE = 5
const V_SIZE = 5
const MIN_H_SIZE = 5
const MIN_V_SIZE = 5

const GRASS_TILE = 0
const ROAD_UP_TILE = 1
const ROAD_MID_TILE = 2
const ROAD_BOTTOM_TILE = 3


# -----------------------------------------------------------------------------
# VARIABLES
# -----------------------------------------------------------------------------

var h_tiles = 1
var v_tiles = 1

var turn_counter = 0

onready var tilemap = $TileMap

func _ready():
	randomize()

	initialize(H_SIZE, V_SIZE)

	get_parent().size = Vector2(Global.CELL_SIZE * 2 * h_tiles, Global.CELL_SIZE * 2 * v_tiles)


func initialize(h_size, v_size):
	if h_size < MIN_H_SIZE:
		h_size = MIN_H_SIZE

	if v_size < MIN_V_SIZE:
		v_size = MIN_V_SIZE

	tilemap.clear()

	for i in range(h_size):
		for j in range(v_size):
			if j == 0 or j == v_size - 1:
				tilemap.set_cellv(Vector2(i, j), GRASS_TILE)
			elif j == 1:
				tilemap.set_cellv(Vector2(i, j), ROAD_UP_TILE)
			elif j == v_size - 2:
				tilemap.set_cellv(Vector2(i, j), ROAD_BOTTOM_TILE)
			else:
				tilemap.set_cellv(Vector2(i, j), ROAD_MID_TILE)

	var frog = Frog.instance()
	frog.position = Vector2(Global.CELL_SIZE * (randi() % (h_size - 2) + 1) + Global.CELL_SIZE/2, (v_size - 1) * Global.CELL_SIZE + Global.CELL_SIZE/2)
	add_child(frog)

	h_tiles = h_size
	v_tiles = v_size

	sprinkle_cars()


func sprinkle_cars():
	var counter = 0
	for i in range(h_tiles):
		counter += 1
		if counter >= TURNS_FOR_SPAWN:
			counter = 0

			var car = Car.instance()
			car.position = Vector2(Global.CELL_SIZE * i + Global.CELL_SIZE/2, (randi() % (v_tiles - 2) + 1) * Global.CELL_SIZE + Global.CELL_SIZE/2)
			add_child(car)

			car.call_deferred("initialize", Vector2(1, 0))


func reset():
	get_tree().reload_current_scene()


func is_position_winning(cell_pos):
	return cell_pos[1] == 0


func is_in_map(cell_pos):
	return 0 <= cell_pos[0] and cell_pos[0] < h_tiles and 0 <= cell_pos[1] and cell_pos[1] < v_tiles


func turn():
	turn_counter += 1

	if turn_counter >= TURNS_FOR_SPAWN:
		turn_counter = 0
		spawn()


func spawn():
	var car = Car.instance()
	car.position = Vector2(-Global.CELL_SIZE/2, (randi() % (v_tiles - 2) + 1) * Global.CELL_SIZE + Global.CELL_SIZE/2)
	add_child(car)

	car.call_deferred("initialize", Vector2(1, 0))
