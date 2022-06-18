// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.environment

import com.example.game.environment.Player2D
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.DOUBLE
import godot.core.VariantType.NIL
import godot.core.VariantType.OBJECT
import godot.core.VariantType.VECTOR2
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class Player2DRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<Player2D>("res://src/main/kotlin/com/example/game/environment/Player2D.kt", "godot.KinematicBody2D", Player2D::class, false, "KinematicBody2D", "com_example_game_environment_Player2D") {
        constructor(KtConstructor0(::Player2D))
        function(Player2D::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::forage, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::goTo, NIL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "pos"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::getPos, VECTOR2, KtFunctionArgument(VECTOR2, "godot.core.Vector2"), DISABLED.id.toInt())
        function(Player2D::getApplesInVision, OBJECT, KtFunctionArgument(OBJECT, "java.util.ArrayList"), DISABLED.id.toInt())
        function(Player2D::getApplesInTouch, OBJECT, KtFunctionArgument(OBJECT, "java.util.ArrayList"), DISABLED.id.toInt())
        function(Player2D::eatApple, NIL, OBJECT to false, KtFunctionArgument(OBJECT, "com.example.game.environment.Apple", "apple"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::_physicsProcess, NIL, DOUBLE to false, KtFunctionArgument(DOUBLE, "kotlin.Double", "delta"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::actionProcess, NIL, DOUBLE to false, KtFunctionArgument(DOUBLE, "kotlin.Double", "delta"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::animationProcess, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::enterAppleInSight, NIL, OBJECT to false, KtFunctionArgument(OBJECT, "godot.Node", "apple"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::exitAppleFromSight, NIL, OBJECT to false, KtFunctionArgument(OBJECT, "godot.Node", "apple"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::enterAppleInReach, NIL, OBJECT to false, KtFunctionArgument(OBJECT, "godot.Node", "apple"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Player2D::exitAppleFromReach, NIL, OBJECT to false, KtFunctionArgument(OBJECT, "godot.Node", "apple"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
      }
    }
  }
}
