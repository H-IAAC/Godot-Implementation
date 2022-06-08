extends Node2D


var mind


func _ready():
	NotificationCenter.create_notification("Started")
	if Engine.has_singleton("CollectorAgentMind"):
		NotificationCenter.create_notification("Mind located")
		mind = Engine.get_singleton("CollectorAgentMind")
		
		mind.connect("print_value", self, "create_notification")
		
		mind.connect("request_apples_in_reach", self, "update_apples_in_reach")
		mind.connect("request_agent_position", self, "update_agent_position")
		mind.connect("request_apples_in_sight", self, "update_apples_in_sight")
		
		mind.connect("forage", self, "forage")
		mind.connect("go_to", self, "go_to")
		mind.connect("eat_apple", self, "eat_apple")
		
		mind.start()


func update_apples_in_reach():
	NotificationCenter.create_notification("Requested apples in reach")


func update_agent_position():
	NotificationCenter.create_notification("Requested agent position")


func update_apples_in_sight():
	NotificationCenter.create_notification("Requested apples in sight")


func forage():
	NotificationCenter.create_notification("Forage")


func go_to(x, y):
	NotificationCenter.create_notification("Go to (" + x + ", " + y + ")")


func eat_apple(apple_name):
	NotificationCenter.create_notification("Eat apple \"" + apple_name + "\"")


func create_notification(notification):
	NotificationCenter.create_notification(notification)
