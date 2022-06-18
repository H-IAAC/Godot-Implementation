// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game

import com.example.game.Simple
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.NIL
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class SimpleRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<Simple>("res://src/main/kotlin/com/example/game/Simple.kt", "godot.Spatial", Simple::class, false, "Spatial", "com_example_game_Simple") {
        constructor(KtConstructor0(::Simple))
        function(Simple::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
      }
    }
  }
}
