plugins {
    kotlin("jvm") version "1.4.32"
    id("com.utopia-rise.godot-kotlin-jvm") version "0.2.0-3.3.2"
}

repositories {
    mavenCentral()
    google()
    jcenter()
    maven(url="https://jitpack.io")
}

dependencies {
    implementation("com.github.CST-Group:cst:0.6.1") {
        exclude(group="com.google.guava")
        exclude(group="dnsjava")
        exclude(group="commons-logging")
    }
}

godot {
    isAndroidExportEnabled.set(false)
    dxToolPath.set("${System.getenv("ANDROID_SDK_ROOT")}/build-tools/30.0.3/dx")
}
