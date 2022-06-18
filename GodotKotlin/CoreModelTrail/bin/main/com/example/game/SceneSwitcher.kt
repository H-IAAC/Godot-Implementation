package com.example.game

import godot.Node
import godot.Node2D
import godot.ResourceLoader
import godot.RichTextLabel
import godot.core.NodePath

class SceneSwitcher: Node2D() {
    companion object {
        lateinit var instance: SceneSwitcher
    }
    var currentScene: Node? = null
    var currentPath: String = ""

    init {
        var root = getTree()?.root
        currentScene = root?.getChild(root.getChildCount() - 1)
        currentPath = currentScene?.filename.toString()

        instance = this
    }

    fun goToScene(path: String) {
        currentPath = path
        callDeferred("deferredGoToScene")
    }

    fun deferredGoToScene() {
        currentScene?.free()

        var s = ResourceLoader.load(currentPath)

        TODO("Figure out how to do this on kotlin")
    }
}