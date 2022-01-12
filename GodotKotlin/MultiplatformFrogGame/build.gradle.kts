plugins {
    id("com.utopia-rise.godot-kotlin-jvm") version "0.3.2-3.4.0"
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
    d8ToolPath.set(File("${System.getenv("ANDROID_SDK_ROOT")}/build-tools/30.0.3/d8"))
    androidCompileSdkDir.set(File("${System.getenv("ANDROID_SDK_ROOT")}/platforms/android-30"))
}
