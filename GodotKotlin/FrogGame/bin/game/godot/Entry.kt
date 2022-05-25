// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot

import godot.com.example.game.SimpleRegistrar
import godot.com.example.game.godot.CarRegistrar
import godot.com.example.game.godot.ElementRegistrar
import godot.com.example.game.godot.FrogRegistrar
import godot.com.example.game.godot.LevelBaseRegistrar
import godot.com.example.game.godot.ShakeableCameraRegistrar
import godot.com.example.game.test.KtTestRegistrar
import godot.runtime.Entry
import godot.runtime.Entry.Context
import kotlin.Unit

public class Entry : Entry() {
  public override fun Context.`init`(): Unit {
    SimpleRegistrar().register(registry)
    CarRegistrar().register(registry)
    ElementRegistrar().register(registry)
    FrogRegistrar().register(registry)
    LevelBaseRegistrar().register(registry)
    ShakeableCameraRegistrar().register(registry)
    KtTestRegistrar().register(registry)
  }

  public override fun Context.initEngineTypes(): Unit {
    registerVariantMapping()
    registerEngineTypes()
    registerEngineTypeMethods()
  }
}
