// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.godot

import com.example.game.godot.Frog
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.BOOL
import godot.core.VariantType.DOUBLE
import godot.core.VariantType.NIL
import godot.core.VariantType.VECTOR2
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class FrogRegistrar : ElementRegistrar() {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<Frog>("res://src/main/kotlin/com/example/game/godot/Frog.kt", "com.example.game.godot.Element", Frog::class, false, "Area2D", "com_example_game_godot_Frog") {
        constructor(KtConstructor0(::Frog))
        function(Frog::_physicsProcess, NIL, DOUBLE to false, KtFunctionArgument(DOUBLE, "kotlin.Double", "delta"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Frog::turn, NIL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "dir"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Frog::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Frog::_process, NIL, DOUBLE to false, KtFunctionArgument(DOUBLE, "kotlin.Double", "delta"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Frog::move, NIL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "dir"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Frog::canMove, BOOL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "dir"), KtFunctionArgument(BOOL, "kotlin.Boolean"), DISABLED.id.toInt())
      }
    }
  }
}
