// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.testcst

import com.example.game.testcst.TestCommunicator
import godot.MultiplayerAPI.RPCMode.DISABLED
import godot.core.KtConstructor0
import godot.core.VariantType.DOUBLE
import godot.core.VariantType.NIL
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import godot.runtime.KtFunctionArgument
import kotlin.Unit

public open class TestCommunicatorRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<TestCommunicator>("res://src/main/kotlin/com/example/game/testcst/TestCommunicator.kt", "godot.RichTextLabel", TestCommunicator::class, false, "RichTextLabel", "com_example_game_testcst_TestCommunicator") {
        constructor(KtConstructor0(::TestCommunicator))
        function(TestCommunicator::_ready, NIL, KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
        function(TestCommunicator::_process, NIL, DOUBLE to false, KtFunctionArgument(DOUBLE, "kotlin.Double", "delta"), KtFunctionArgument(NIL, "kotlin.Unit"), DISABLED.id.toInt())
      }
    }
  }
}
