extends KinematicBody2D

enum State {IDLE, MOVE, FORAGE}

# ------------------------------------------------------------------------------
# CONSTANTS
const ROTATION_ACCELERATION = 0.05
const ROTATION_DECELERATION = 0.1
const ROTATION_MAX_SPEED = 1

const MAX_SPEED = 100
const ACCELERATION = 0.05
const DECELERATION = 0.05

const MIN_DISTANCE_TO_MOVE = 25

const MIN_PARTICLE_SPEED = 80
# ------------------------------------------------------------------------------

# ------------------------------------------------------------------------------
# VARIABLES
var dir = Vector2(0, -1)
var velocity = Vector2(0, 0)

var rotation_speed = 0

var aim_pos = Vector2(0, 0)

var apples_in_sight = []
var apples_in_reach = []

var mind
var state
# ------------------------------------------------------------------------------

# ------------------------------------------------------------------------------
# NODES
onready var particles = $Particles
onready var apples = get_parent().get_node("Apples")

# ------------------------------------------------------------------------------

func _ready():
	state = State.IDLE
	NotificationCenter.create_notification("Started")
	if Engine.has_singleton("CollectorAgentMind"):
		NotificationCenter.create_notification("Mind located")
		mind = Engine.get_singleton("CollectorAgentMind")
		
		mind.connect("print_value", NotificationCenter, "create_notification")
		mind.connect("update_value", NotificationCenter, "update_value")
		
		mind.connect("request_apples_in_reach", self, "update_apples_in_reach")
		mind.connect("request_agent_position", self, "update_agent_position")
		mind.connect("request_apples_in_sight", self, "update_apples_in_sight")
		
		mind.connect("forage", self, "forage")
		mind.connect("go_to", self, "go_to")
		mind.connect("eat_apple", self, "eat_apple")
		
		mind.start()
		
		NotificationCenter.create_notification("Mind setup complete")


func update_apples_in_reach():
	if mind != null:
		
		mind.clearApplesInReach()
		
		for apple in apples_in_reach:
			mind.addAppleInReach(apple.apple_name, apple.position[0], apple.position[1])


func update_agent_position():
	if mind != null:
		mind.updatePosition(position[0], position[1])


func update_apples_in_sight():
	if mind != null:
		
		mind.clearApplesInVision()
		
		for apple in apples_in_sight:
			mind.addAppleInVision(apple.apple_name, apple.position[0], apple.position[1])


func forage():
	if state != State.FORAGE:
		NotificationCenter.create_notification("Started foraging")
		state = State.FORAGE


func go_to(x, y):
	aim_pos = Vector2(float(x), float(y))
	
	if state != State.MOVE:
		NotificationCenter.create_notification("Started moving to [" + x + ", " + y + "]")
		state = State.MOVE


func eat_apple(apple_name):
	
	var apples_in_game = apples.get_children()
	
	for apple in apples_in_game:
		if apple.apple_name == apple_name:
			exit_apple_from_reach(apple)
			exit_apple_from_sight(apple)
			
			mind.clearApplesInVision()
			mind.clearApplesInReach()
			
			apple.get_eaten()
			mind.removeAppleFromKnownApples(apple_name)


func _physics_process(delta):
	_action_process(delta)
	
	_animation_process()

func _action_process(delta):
	match state:
		State.IDLE:
			pass
		State.FORAGE:
			velocity = velocity.linear_interpolate(Vector2(0, 0), DECELERATION)
			move_and_slide(velocity)
			
			rotation_speed = lerp(rotation_speed, ROTATION_MAX_SPEED, ROTATION_ACCELERATION)
			
			dir = dir.rotated(rotation_speed * delta)
			rotation = Vector2(0, -1).angle_to(dir)
		State.MOVE:
			var aim_vector = aim_pos - position
			var aim_dir = Vector2(0, 0)
			var acceleration = ACCELERATION
			
			if aim_vector.distance_to(Vector2(0, 0)) > MIN_DISTANCE_TO_MOVE:
				aim_dir = aim_vector.normalized()
				acceleration = DECELERATION
			
			dir = dir.linear_interpolate(aim_dir, ROTATION_ACCELERATION)
			rotation = Vector2(0, -1).angle_to(dir)
			
			
			
			velocity = velocity.linear_interpolate(dir * MAX_SPEED, acceleration)
			move_and_slide(velocity)

func _animation_process():
	if velocity.distance_to(Vector2(0, 0)) > MIN_PARTICLE_SPEED:
		particles.emitting = true
	else:
		particles.emitting = false


func enter_apple_in_sight(apple):
	apples_in_sight.append(apple)


func exit_apple_from_sight(apple):
	var pos = apples_in_sight.find(apple)
	
	if pos != -1:
		apples_in_sight.remove(pos)


func enter_apple_in_reach(apple):
	apples_in_reach.append(apple)


func exit_apple_from_reach(apple):
	var pos = apples_in_reach.find(apple)
	
	if pos != -1:
		apples_in_reach.remove(pos)
