// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.godot

import com.example.game.godot.Element
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.BOOL
import godot.core.VariantType.DOUBLE
import godot.core.VariantType.NIL
import godot.core.VariantType.VECTOR2
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class ElementRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<Element>("res://src/main/kotlin/com/example/game/godot/Element.kt", "godot.Area2D", Element::class, false, "Area2D", "com_example_game_godot_Element") {
        constructor(KtConstructor0(::Element))
        function(Element::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Element::_process, NIL, DOUBLE to false, KtFunctionArgument(DOUBLE, "kotlin.Double", "delta"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Element::move, NIL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "dir"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Element::canMove, BOOL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "dir"), KtFunctionArgument(BOOL, "kotlin.Boolean"), DISABLED.id.toInt())
      }
    }
  }
}
