plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("com.google.devtools.ksp")
    id("de.jensklingenberg.ktorfit")
    kotlin("plugin.parcelize")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            export(libs.decompose.core)
            export(libs.essenty.lifecycle)
            export(libs.essenty.backhandler)
            export(libs.moko.resources)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.forms)
                implementation(libs.kotlinx.datetime)
                implementation(libs.coroutines.core)
                api(libs.decompose.core)
                implementation(libs.bundles.ktor.shared)
                implementation(libs.ktorfit.lib)
                implementation(libs.bundles.replica.shared)
                implementation(libs.koin.core)
                implementation(libs.logger.kermit)
                api(libs.moko.resources)
                implementation(libs.moko.permissions)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.yandex.map)
                implementation(libs.forms)
                implementation(libs.collapsing.toolbar)
                implementation(libs.coroutines.android)
                implementation(libs.ktor.android)
                implementation(libs.decompose.compose)
                implementation(libs.replica.androidNetwork)
                implementation(libs.moko.resourcesCompose)
                implementation(libs.bundles.compose)
                implementation(libs.bundles.accompanist)
                implementation(libs.coil)
                implementation(libs.security.crypto)
                implementation(libs.phone.number)
                implementation(libs.activity)
                implementation(libs.compose.balloon)
                implementation(libs.coroutines.playServices)
                implementation(libs.moko.permissionsCompose)
                implementation(libs.moko.permissionsTest)
                implementation(libs.accompanist.flowlayout)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.ktor.ios)
            }
        }
    }
}

android {
    val minSdkVersion: Int by rootProject.extra
    val targetSdkVersion: Int by rootProject.extra

    namespace = "words.letters.alphabet.game"
    compileSdk = targetSdkVersion
    defaultConfig {
        minSdk = minSdkVersion
        targetSdk = targetSdkVersion
    }


    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packagingOptions {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation(libs.yandex.map)
    coreLibraryDesugaring(libs.android.desugar)
    add("kspCommonMainMetadata", libs.ktorfit.ksp)
    add("kspAndroid", libs.ktorfit.ksp)
    add("kspIosX64", libs.ktorfit.ksp)
    add("kspIosArm64", libs.ktorfit.ksp)
    add("kspIosSimulatorArm64", libs.ktorfit.ksp)
}

multiplatformResources {
    multiplatformResourcesPackage = "letters.game"
}