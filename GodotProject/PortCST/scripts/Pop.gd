extends CPUParticles2D


onready var timer = $Timer


func _ready():
	timer.start(lifetime * (1 + lifetime_randomness))
	timer.connect("timeout", self, "queue_free")
