extends KinematicBody


export (String) var apple_name


const SPIN_SPEED = 1


func _process(delta):
	rotation[1] += delta * SPIN_SPEED


func get_eaten():
	NotificationCenter.create_notification("Apple " + apple_name + " got eaten!")
	
	queue_free()
