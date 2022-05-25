// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.test

import com.example.game.test.KtTest
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.NIL
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class KtTestRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<KtTest>("res://src/main/kotlin/com/example/game/test/KtTest.kt", "godot.Node2D", KtTest::class, false, "Node2D", "com_example_game_test_KtTest") {
        constructor(KtConstructor0(::KtTest))
        function(KtTest::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
      }
    }
  }
}
