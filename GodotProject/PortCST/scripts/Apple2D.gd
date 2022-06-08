extends KinematicBody2D


export (PackedScene) var PopEffect
export (String) var apple_name


func _physics_process(delta):
	if Input.is_action_just_pressed("left"):
		get_eaten()


func get_eaten():
	NotificationCenter.create_notification("Apple " + apple_name + " got eaten!")
	var pop_effect = PopEffect.instance()
	pop_effect.position = position
	pop_effect.emitting = true
	get_parent().add_child(pop_effect)
	
	queue_free()
