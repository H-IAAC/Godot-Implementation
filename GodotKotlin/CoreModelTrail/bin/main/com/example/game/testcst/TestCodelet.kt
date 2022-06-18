package com.example.game.testcst

import br.unicamp.cst.core.entities.Codelet

class TestCodelet(name: String?) : Codelet() {
    var emotion = 0
        private set
    private var emotionModifier: Float

    override fun accessMemoryObjects() {}

    override fun calculateActivation() {}

    override fun proc() {
        emotion += emotionModifier.toInt()
        if (emotion >= 100) {
            emotionModifier = -1f
        } else if (emotion <= 0) {
            emotionModifier = 1f
        }
    }

    init {
        setName(name)
        emotion = 0
        emotionModifier = 1f
    }
}