extends "res://scenes/scripts/Element.gd"

enum Request {SENSOR, MOTOR, WIN, LOSE, INFO, END}

enum Action {UP, RIGHT, DOWN, LEFT, INVALID}


const VISION_DISTANCE = 2


var request_pile = []
var done = false

onready var http_request = $HTTPRequest


func _ready():
	animation_player.play("idle_front")
	
	http_request.connect("request_completed", self, "finish_request")
	
	http_request.request("http://localhost:8080/initialize")
	
	request_pile.append(Request.SENSOR)


func turn(dir):
	if (can_move(dir)):
		move(dir)
		
		if lose_on_collision():
			return false
	
	for car in get_tree().get_nodes_in_group("car"):
		car.turn()
	base.turn()
	
	if cell_pos[1] == 0:
		win()
	
	if lose_on_collision():
		return false
	
	if dir[1] == -1:
		animation_player.play("idle_back")
	elif dir[1] == 1:
		animation_player.play("idle_front")
	else:
		animation_player.play("idle_side")
		sprite.flip_h = dir[0] != 1
	
	return true

func lose_on_collision():
	for car in get_tree().get_nodes_in_group("car"):
			if car.cell_pos == cell_pos:
				lose()
				return true
	return false


func finish_request(result, response_code, headers, body):
	var content_string = body.get_string_from_utf8()
	var content = JSON.parse(content_string).result
	print(content)
	
	var info = content["message"]
	var request_type = content["requestType"]
	
	if request_type == "MOTOR":
		match info:
			"UP":
				if not turn(Vector2(0, -1)):
					return
			"RIGHT":
				if not turn(Vector2(1, 0)):
					return
			"DOWN":
				if not turn(Vector2(0, 1)):
					return
			"LEFT":
				if not turn(Vector2(-1, 0)):
					return
			"INVALID":
				if not turn(Vector2(0, 0)):
					return
			"RESET":
				lose()
				return
	elif request_type == "END":
		if info != "DONE":
			request_pile.append(Request.SENSOR)
			request_pile.append(Request.END)
	request()


func request():
	if len(request_pile) == 0:
		base.reset()
	else:
		var request = request_pile.pop_front()
		
		match request:
			Request.SENSOR:
				var car_pos_in_vision = []
				for car in get_tree().get_nodes_in_group("car"):
					if is_in_sight(car.cell_pos):
						car_pos_in_vision.append({"x": car.cell_pos[0], "y": car.cell_pos[1]})
				
				var sensor_data = {"innersense": {"x": cell_pos[0], "y": cell_pos[1]},
										"vision": car_pos_in_vision}
				
				var sensor_string = JSON.print(sensor_data)
				http_request.request("http://localhost:8080/updatesensors", PoolStringArray(["Content-Type:application/json"]), true, HTTPClient.METHOD_POST, sensor_string)
				
				try_to_add_request(Request.MOTOR)
			Request.MOTOR:
				http_request.request("http://localhost:8080/getmotordata")
				try_to_add_request(Request.SENSOR)
			Request.WIN:
				http_request.request("http://localhost:8080/logwin")
				request_pile.append(Request.END)
			Request.LOSE:
				http_request.request("http://localhost:8080/loglose")
				request_pile.append(Request.END)
			Request.END:
				http_request.request("http://localhost:8080/end")

func is_in_sight(pos):
	return abs(pos[0] - cell_pos[0]) <= VISION_DISTANCE and abs(pos[1] - cell_pos[1]) <= VISION_DISTANCE


func try_to_add_request(request):
	if not done:
		request_pile.append(request)

func game_over():
	if not done:
		http_request.cancel_request()
		request_pile = []
		done = true


func lose():
	game_over()
	
	request_pile.append(Request.LOSE)
	request()


func win():
	game_over()
	
	request_pile.append(Request.WIN)
	request()
