// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.godot

import com.example.game.godot.Car
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.BOOL
import godot.core.VariantType.DOUBLE
import godot.core.VariantType.NIL
import godot.core.VariantType.VECTOR2
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class CarRegistrar : ElementRegistrar() {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<Car>("res://src/main/kotlin/com/example/game/godot/Car.kt", "com.example.game.godot.Element", Car::class, false, "Area2D", "com_example_game_godot_Car") {
        constructor(KtConstructor0(::Car))
        function(Car::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Car::initialize, NIL, VECTOR2 to false, DOUBLE to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "d"), KtFunctionArgument(DOUBLE, "kotlin.Double", "t"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Car::endTimer, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Car::_process, NIL, DOUBLE to false, KtFunctionArgument(DOUBLE, "kotlin.Double", "delta"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Car::move, NIL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "dir"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(Car::canMove, BOOL, VECTOR2 to false, KtFunctionArgument(VECTOR2, "godot.core.Vector2", "dir"), KtFunctionArgument(BOOL, "kotlin.Boolean"), DISABLED.id.toInt())
      }
    }
  }
}
