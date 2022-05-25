// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.godot

import com.example.game.godot.LevelBase
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.BOOL
import godot.core.VariantType.JVM_INT
import godot.core.VariantType.LONG
import godot.core.VariantType.NIL
import godot.core.VariantType.VECTOR2
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class LevelBaseRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<LevelBase>("res://src/main/kotlin/com/example/game/godot/LevelBase.kt", "godot.Node2D", LevelBase::class, false, "Node2D", "com_example_game_godot_LevelBase") {
        constructor(KtConstructor0(::LevelBase))
        function(LevelBase::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(LevelBase::initialize, NIL, JVM_INT to false, JVM_INT to false, KtFunctionArgument(LONG, "kotlin.Int", "hSize"), KtFunctionArgument(LONG, "kotlin.Int", "vSize"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(LevelBase::isInMap, BOOL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "pos"), KtFunctionArgument(BOOL, "kotlin.Boolean"), DISABLED.id.toInt())
      }
    }
  }
}
