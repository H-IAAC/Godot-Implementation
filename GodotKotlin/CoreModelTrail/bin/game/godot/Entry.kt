// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot

import godot.com.example.game.CollectorMindCommunicatorRegistrar
import godot.com.example.game.NotificationCenterRegistrar
import godot.com.example.game.environment.Apple2DRegistrar
import godot.com.example.game.environment.Environment2DRegistrar
import godot.com.example.game.environment.Player2DRegistrar
import godot.com.example.game.testcst.TestCommunicatorRegistrar
import godot.runtime.Entry
import godot.runtime.Entry.Context
import kotlin.Unit

public class Entry : Entry() {
  public override fun Context.`init`(): Unit {
    CollectorMindCommunicatorRegistrar().register(registry)
    NotificationCenterRegistrar().register(registry)
    Apple2DRegistrar().register(registry)
    Environment2DRegistrar().register(registry)
    Player2DRegistrar().register(registry)
    TestCommunicatorRegistrar().register(registry)
  }

  public override fun Context.initEngineTypes(): Unit {
    registerVariantMapping()
    registerEngineTypes()
    registerEngineTypeMethods()
  }
}
