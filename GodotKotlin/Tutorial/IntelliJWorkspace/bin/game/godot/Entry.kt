// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot

import godot.com.example.game.SimpleRegistrar
import godot.runtime.Entry
import godot.runtime.Entry.Context
import kotlin.Unit

public class Entry : Entry() {
  public override fun Context.`init`(): Unit {
    SimpleRegistrar().register(registry)
  }

  public override fun Context.initEngineTypes(): Unit {
    registerVariantMapping()
    registerEngineTypes()
    registerEngineTypeMethods()
  }
}
