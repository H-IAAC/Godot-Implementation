extends Node2D

# -----------------------------------------------------------------------------
# CONSTANTS
# -----------------------------------------------------------------------------

const LERP_ACCEL = 0.4

# -----------------------------------------------------------------------------
# VARIABLES
# -----------------------------------------------------------------------------


var cell_pos = Vector2(0, 0)

onready var sprite = $Sprite
onready var animation_player = $AnimationPlayer
onready var base = get_parent()


func _ready():
	cell_pos = Vector2(floor(position[0]/Global.CELL_SIZE), floor(position[1]/Global.CELL_SIZE))


func _process(delta):
	sprite.position = sprite.position.linear_interpolate(Vector2(0, 0), LERP_ACCEL)


func move(dir):
	cell_pos += dir
	position = cell_pos * Global.CELL_SIZE + Vector2(Global.CELL_SIZE/2, Global.CELL_SIZE/2)
	sprite.position -= dir * Global.CELL_SIZE


func can_move(dir):
	return base.is_in_map(cell_pos + dir)
