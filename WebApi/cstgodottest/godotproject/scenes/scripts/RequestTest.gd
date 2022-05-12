extends Control

onready var http_request = $HTTPRequest
onready var text_label = $Text


func request_initialize():
	http_request.request("http://localhost:8080/initialize?name=GodotCSTTest")


func request_value():
	http_request.request("http://localhost:8080/check")


func request_completed(result, response_code, headers, body):
	var content = JSON.parse(body.get_string_from_utf8()).result
	print(content)
	
	text_label.text = content["message"]
