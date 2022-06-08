extends Node2D


var current_scene = null
var current_path = ""



func _ready():
	var root = get_tree().get_root()
	current_scene = root.get_child(root.get_child_count() - 1)
	
	current_path = current_scene.filename


func goto_scene(path):
	current_path = path
	
	call_deferred("deferred_goto_scene")


func deferred_goto_scene():
	current_scene.free()
	
	var s = ResourceLoader.load(current_path)
	current_scene = s.instance()
	
	get_tree().get_root().add_child(current_scene)
	
	get_tree().set_current_scene(current_scene)
