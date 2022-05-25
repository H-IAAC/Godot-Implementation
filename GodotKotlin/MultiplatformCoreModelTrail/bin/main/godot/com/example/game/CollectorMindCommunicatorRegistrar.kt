// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game

import com.example.game.CollectorMindCommunicator
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.NIL
import godot.core.VariantType.OBJECT
import godot.core.VariantType.VECTOR2
import godot.registration.ClassRegistrar
import godot.registration.ClassRegistry
import godot.registration.KtFunctionArgument
import kotlin.Unit

public open class CollectorMindCommunicatorRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<CollectorMindCommunicator>("res://src/main/kotlin/com/example/game/CollectorMindCommunicator.kt", "godot.Node", CollectorMindCommunicator::class, false, "Node", "") {
        constructor(KtConstructor0(::CollectorMindCommunicator))
        function(CollectorMindCommunicator::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(CollectorMindCommunicator::clearApple, NIL, OBJECT to false, KtFunctionArgument(OBJECT, "com.example.game.environment.Apple", "apple"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(CollectorMindCommunicator::getPosition, VECTOR2, KtFunctionArgument(VECTOR2, "godot.core.Vector2"), DISABLED.id.toInt())
        function(CollectorMindCommunicator::getApplesInReach, OBJECT, KtFunctionArgument(OBJECT, "kotlin.collections.ArrayList"), DISABLED.id.toInt())
        function(CollectorMindCommunicator::getApplesInVision, OBJECT, KtFunctionArgument(OBJECT, "kotlin.collections.ArrayList"), DISABLED.id.toInt())
        function(CollectorMindCommunicator::eatApple, NIL, OBJECT to false, KtFunctionArgument(OBJECT, "com.example.game.environment.Apple", "apple"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(CollectorMindCommunicator::forage, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(CollectorMindCommunicator::goTo, NIL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "pos"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
      }
    }
  }
}
