package com.example.game.environment

import com.example.game.CollectorMindCommunicator
import godot.*
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction
import godot.core.NodePath
import godot.core.VariantArray
import godot.core.Vector2
import godot.global.GD

@RegisterClass
class Player2D: KinematicBody2D(), Player {
    /*
        CONSTANTS
    */
    val ROTATION_ACCELERATION = 0.05
    val ROTATION_DECELERATION = 0.1
    val ROTATION_MAX_SPEED = 1.0

    val MAX_SPEED = 100.0
    val ACCELERATION = 0.05
    val DECELERATION = 0.05

    val MIN_DISTANCE_TO_MOVE = 25.0

    val MIN_PARTICLE_SPEED = 80.0

    /*
        VARIABLES
    */

    var dir = Vector2(0, -1)
    var velocity = Vector2(0, 0)

    var rotationSpeed = 0.0

    var aimPos = Vector2(0, 0)

    var applesInSight = ArrayList<Apple>()
    var applesInReach = ArrayList<Apple>()

    lateinit var mindCommunicator: CollectorMindCommunicator
    var state = State.IDLE

    /*
        NODES
    */

    lateinit var particles: CPUParticles2D
    lateinit var apples: Node2D

    @RegisterFunction
    override fun _ready() {
        // Setup Nodes
        particles = getNode(NodePath("Particles")) as CPUParticles2D
        apples = getNode(NodePath("../Apples")) as Node2D

        // Initialize Mind
        mindCommunicator = getNode(NodePath("MindCommunicator")) as CollectorMindCommunicator
        mindCommunicator.player = this

        GD.print("2D Player mind started")

        // Setup Signals
//		var collectionArea: Area2D = getNode(NodePath("CollectionArea")) as Area2D
//		collectionArea.connect("body_entered", this, "enterAppleInReach")
//		collectionArea.connect("body_exited", this, "exitAppleFromReach")
//
//		var visionArea: Area2D = getNode(NodePath("VisionArea")) as Area2D
//		visionArea.connect("body_entered", this, "enterAppleInSight")
//		visionArea.connect("body_exited", this, "exitAppleFromSight")
    }

    @RegisterFunction
    override fun forage() {
        if (state != State.FORAGE) {
            GD.print("Started foraging")
            state = State.FORAGE
        }
    }

    @RegisterFunction
    override fun goTo(pos: Vector2) {
        aimPos = pos
        if (state != State.MOVE) {
            GD.print("Started moving!")
            state = State.MOVE
        }
    }

    @RegisterFunction
    override fun getPos(): Vector2 {
        return position
    }

    @RegisterFunction
    override fun getApplesInVision(): ArrayList<Apple> {
        return applesInSight
    }

    @RegisterFunction
    override fun getApplesInTouch(): ArrayList<Apple> {
        return applesInReach
    }

    @RegisterFunction
    override fun eatApple(apple: Apple) {
        var tApple = apple as Apple2D
        exitAppleFromReach(tApple)
        exitAppleFromSight(tApple)
        tApple.getEaten()

//		GD.print("Tried to eat apple")
//		var applesInGame: VariantArray<Any?> = apples.getChildren()
//
//		for (apple in applesInGame) {
//			var tApple = apples as Apple2D
//			if (tApple.getCode() == code) {
//				exitAppleFromReach(tApple)
//				exitAppleFromSight(tApple)
//
//				tApple.getEaten()
//			}
//		}
    }

    @RegisterFunction
    override fun _physicsProcess(delta: Double) {
        actionProcess(delta)
        animationProcess()
    }

    @RegisterFunction
    fun actionProcess(delta: Double) {
        when (state) {
            State.IDLE -> return
            State.FORAGE -> {
                velocity = velocity.linearInterpolate(Vector2(0, 0), DECELERATION)
                moveAndSlide(velocity)

                rotationSpeed = lerp(rotationSpeed, ROTATION_MAX_SPEED, ROTATION_ACCELERATION)

                dir = dir.rotated(rotationSpeed * delta)
                rotation = Vector2(0, -1).angleTo(dir)
            }
            State.MOVE -> {
                var aimVector = aimPos - position
                var aimDir = Vector2(0, 0)
                var acceleration = ACCELERATION

                if (aimVector.distanceTo(Vector2(0, 0)) > MIN_DISTANCE_TO_MOVE) {
                    aimDir = aimVector.normalized()
                    acceleration = DECELERATION
                }

                dir = dir.linearInterpolate(aimDir, ROTATION_ACCELERATION)
                rotation = Vector2(0, -1).angleTo(dir)

                velocity = velocity.linearInterpolate(dir * MAX_SPEED, ACCELERATION)
                moveAndSlide(velocity)
            }
        }
    }

    @RegisterFunction
    fun animationProcess() {
        particles.emitting = velocity.distanceTo(Vector2(0, 0)) > MIN_PARTICLE_SPEED

    }

    @RegisterFunction
    fun enterAppleInSight(apple: Node) {
        applesInSight.add(apple as Apple)
    }

    @RegisterFunction
    fun exitAppleFromSight(apple: Node) {
        var pos = applesInSight.remove(apple as Apple)
    }

    @RegisterFunction
    fun enterAppleInReach(apple: Node) {
        applesInReach.add(apple as Apple)
    }

    @RegisterFunction
    fun exitAppleFromReach(apple: Node) {
        var pos = applesInReach.remove(apple as Apple)
    }

    fun lerp(a: Double, b: Double, t: Double): Double {
        return a + (b-a)*t
    }
}
