extends Spatial


const PIVOT_ROT_SPEED = 0.05
const PIVOT_LERP_WEIGHT = 0.05

const DEVICE_LERP_WEIGHT = 0.1


var mind


var left_pressed = false
var right_pressed = false

onready var phone = $Phone
onready var pivot = $CameraPivot
onready var projection = $Projection

onready var fixed_camera = $FixedCamera
onready var tracking_camera = $CameraPivot/TrackingCamera


func _ready():
	NotificationCenter.create_notification("Started")
	if Engine.has_singleton("VisualizerMind"):
		NotificationCenter.create_notification("Mind located")
		
		mind = Engine.get_singleton("VisualizerMind")
		mind.connect("update_value", NotificationCenter, "update_value")
		mind.connect("create_notification", NotificationCenter, "create_notification")
		mind.startRealTime()
		
		NotificationCenter.create_notification("Mind started")
	else:
		NotificationCenter.create_notification("Unable to locate mind")


func _physics_process(delta):
	var rotation_dir = 0
	if left_pressed:
		rotation_dir -= 1
	if right_pressed:
		rotation_dir += 1
	
	pivot.rotate_y(rotation_dir * PIVOT_ROT_SPEED)
	
	if mind != null:
		mind.update(delta)
		
		var basis_string = mind.getBasis()
		var basis = basis_string.rsplit(";")
		var origin_string = mind.getWorldPosition()
		var origin = origin_string.rsplit(";")
		
		# From godot(g) to android(a) we have g.x = -a.y,  g.y = -a.z, g.z = -a.x
		
		phone.transform.basis.x = Vector3(float(basis[0]), float(basis[1]), float(basis[2]))
		phone.transform.basis.y = Vector3(float(basis[3]), float(basis[4]), float(basis[5]))
		phone.transform.basis.z = Vector3(float(basis[6]), float(basis[7]), float(basis[8]))
		
		phone.transform.origin = phone.transform.origin.linear_interpolate(Vector3(float(origin[0]), float(origin[1]), float(origin[2])), DEVICE_LERP_WEIGHT)
	
	pivot.transform.origin = pivot.transform.origin.linear_interpolate(phone.transform.origin, PIVOT_LERP_WEIGHT)


func left_pressed_down():
	left_pressed = true


func left_pressed_up():
	left_pressed = false


func right_pressed_down():
	right_pressed = true


func right_pressed_up():
	right_pressed = false


func reset_pos():
	if mind != null:
		mind.resetPosition()


func use_fixed_camera():
	tracking_camera.current = false
	fixed_camera.current = true


func use_tracking_camera():
	fixed_camera.current = false
	tracking_camera.current = true
