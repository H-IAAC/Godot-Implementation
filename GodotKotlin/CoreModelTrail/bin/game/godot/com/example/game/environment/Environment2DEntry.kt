// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.environment

import com.example.game.environment.Environment2D
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.NIL
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class Environment2DRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<Environment2D>("res://src/main/kotlin/com/example/game/environment/Environment2D.kt", "godot.Node2D", Environment2D::class, false, "Node2D", "com_example_game_environment_Environment2D") {
        constructor(KtConstructor0(::Environment2D))
        function(Environment2D::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Environment2D::toMenu, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Environment2D::spawnApple, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
      }
    }
  }
}
