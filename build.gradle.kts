val minSdkVersion by extra(23)
val targetSdkVersion by extra(33)

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }
}


