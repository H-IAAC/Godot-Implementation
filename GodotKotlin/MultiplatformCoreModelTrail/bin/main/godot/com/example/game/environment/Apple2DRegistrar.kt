// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.environment

import com.example.game.environment.Apple2D
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.NIL
import godot.core.VariantType.STRING
import godot.core.VariantType.VECTOR2
import godot.registration.ClassRegistrar
import godot.registration.ClassRegistry
import godot.registration.KtFunctionArgument
import kotlin.Unit

public open class Apple2DRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<Apple2D>("res://src/main/kotlin/com/example/game/environment/Apple2D.kt", "godot.KinematicBody2D", Apple2D::class, false, "KinematicBody2D", "") {
        constructor(KtConstructor0(::Apple2D))
        function(Apple2D::setCode, NIL, STRING to false, KtFunctionArgument(STRING, "kotlin.String", "code"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Apple2D::getPos, VECTOR2, KtFunctionArgument(VECTOR2, "godot.core.Vector2"), DISABLED.id.toInt())
        function(Apple2D::getCode, STRING, KtFunctionArgument(STRING, "kotlin.String"), DISABLED.id.toInt())
        function(Apple2D::getEaten, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
      }
    }
  }
}
