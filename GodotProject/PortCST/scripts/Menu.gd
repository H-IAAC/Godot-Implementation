extends Sprite


func _ready():
	NotificationCenter.hide()


func go_to(path):
	NotificationCenter.show()
	SceneSwitcher.goto_scene(path)
