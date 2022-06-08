extends Node2D


onready var rich_text_label = $RichTextLabel
onready var label = $Label

var HelloWorld
var RequestTest

func _ready():
	rich_text_label.text += "Funcionando\n"
	if Engine.has_singleton("HelloWorld"):
		rich_text_label.text += "Singleton HelloWorld identificado\n"
		HelloWorld = Engine.get_singleton("HelloWorld")
		HelloWorld.connect("hello", self, "_on_hello")
		HelloWorld.sendSignal("AUAUAU")
	
	if Engine.has_singleton("RequestTest"):
		rich_text_label.text += "Singleton RequestTest identificado\n"
		RequestTest = Engine.get_singleton("RequestTest")
		RequestTest.connect("print_test_value", self, "print_string")
		RequestTest.connect("request_external_test_value", self, "_on_requested_test_value")


func print_string(value):
	rich_text_label.text += value + "\n"


func _on_hello(value):
	label.text = value


func _on_requested_test_value():
	if RequestTest != null:
		RequestTest.updateExternalTestValue(["Updated external test value", "Extra value"])
		RequestTest.endPrintExternalTestValue()


func _on_print_external_test_value():
	if RequestTest != null:
		RequestTest.printExternalTestValue()


func _on_print_test_value():
	if RequestTest != null:
		RequestTest.call("emitTestValue")


func _on_update_test_value():
	if RequestTest != null:
		RequestTest.updateTestValue("New value")


func _on_Button_pressed():
	if HelloWorld != null:
		var s = HelloWorld.hello()
		rich_text_label.text += s + "\n"
