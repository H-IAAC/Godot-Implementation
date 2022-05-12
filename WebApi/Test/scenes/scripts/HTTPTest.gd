extends Control


onready var http_request = $HTTPRequest


func get_api_data():
	var error = http_request.request("http://localhost:8080/hello?name=Gustavo")
	if error != OK:
		push_error("An error occurred in the HTTP request.")


func receive_request(result, response_code, headers, body):
	var raw_string = body.get_string_from_utf8()
	var json = JSON.parse(raw_string)
	print(json.result)


func send_api_data():
	var body = {"name": "Godette"}
	var error = http_request.request("http://localhost:8080/hello?name=Gustavo", [], true, HTTPClient.METHOD_POST, JSON.print(body))
	if error != OK:
		push_error("An error occurred in the HTTP request.")
