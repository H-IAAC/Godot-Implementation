// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game

import com.example.game.NotificationCenter
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.NIL
import godot.core.VariantType.STRING
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class NotificationCenterRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<NotificationCenter>("res://src/main/kotlin/com/example/game/NotificationCenter.kt", "godot.Node2D", NotificationCenter::class, false, "Node2D", "com_example_game_NotificationCenter") {
        constructor(KtConstructor0(::NotificationCenter))
        function(NotificationCenter::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(NotificationCenter::createNotification, NIL, STRING to false, KtFunctionArgument(STRING, "kotlin.String", "notification"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(NotificationCenter::updateValue, NIL, STRING to false, STRING to false, KtFunctionArgument(STRING, "kotlin.String", "newValue"), KtFunctionArgument(STRING, "kotlin.String", "code"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
      }
    }
  }
}
