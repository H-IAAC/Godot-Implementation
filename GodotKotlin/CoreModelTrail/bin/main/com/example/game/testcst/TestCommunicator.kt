package com.example.game.testcst

import br.unicamp.cst.core.entities.Mind
import godot.RichTextLabel
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.global.GD

@RegisterClass
class TestCommunicator: RichTextLabel() {

	private var mind: Mind? = null
	private var testCodelet: TestCodelet? = null

	@RegisterFunction
	override fun _ready() {
		GD.print("Ready")
		mind = Mind()
		GD.print("Mind initialized")

		mind!!.createCodeletGroup("Teste")

		testCodelet = TestCodelet("t1")
		mind!!.insertCodelet(testCodelet, "Teste")

		mind!!.start()
	}

	@RegisterFunction
	override fun _process(delta: Double) {
		text = testCodelet?.emotion.toString()
	}
}
