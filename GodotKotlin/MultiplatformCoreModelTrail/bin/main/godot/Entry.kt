// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot

import com.example.game.CollectorMindCommunicator
import com.example.game.NotificationCenter
import com.example.game.environment.Apple2D
import com.example.game.environment.Environment2D
import com.example.game.environment.Player2D
import godot.com.example.game.CollectorMindCommunicatorRegistrar
import godot.com.example.game.NotificationCenterRegistrar
import godot.com.example.game.environment.Apple2DRegistrar
import godot.com.example.game.environment.Environment2DRegistrar
import godot.com.example.game.environment.Player2DRegistrar
import godot.registration.Entry
import godot.registration.Entry.Context
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.listOf
import kotlin.reflect.KClass

public class Entry : Entry() {
  public override fun Context.`init`(): Unit {
    CollectorMindCommunicatorRegistrar().register(registry)
    NotificationCenterRegistrar().register(registry)
    Apple2DRegistrar().register(registry)
    Environment2DRegistrar().register(registry)
    Player2DRegistrar().register(registry)
  }

  public override fun Context.initEngineTypes(): Unit {
    registerVariantMapping()
    registerEngineTypes()
    registerEngineTypeMethods()
  }

  public override fun Context.getRegisteredClasses(): List<KClass<*>> =
      listOf(CollectorMindCommunicator::class, NotificationCenter::class, Apple2D::class,
      Environment2D::class, Player2D::class)
}
