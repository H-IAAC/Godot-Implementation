// THIS FILE IS GENERATED! DO NOT EDIT IT MANUALLY! ALL CHANGES TO IT WILL BE OVERWRITTEN ON EACH BUILD
package godot.com.example.game.godot

import com.example.game.godot.ShakeableCamera
import godot.core.KtConstructor0
import godot.runtime.ClassRegistrar
import godot.runtime.ClassRegistry
import kotlin.Unit

public open class ShakeableCameraRegistrar : ClassRegistrar {
  public override fun register(registry: ClassRegistry): Unit {
    with(registry) {
      registerClass<ShakeableCamera>("res://src/main/kotlin/com/example/game/godot/ShakeableCamera.kt", "godot.Camera2D", ShakeableCamera::class, false, "Camera2D", "com_example_game_godot_ShakeableCamera") {
        constructor(KtConstructor0(::ShakeableCamera))
      }
    }
  }
}
