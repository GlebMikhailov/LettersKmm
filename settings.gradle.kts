pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    val kotlinVersion = "1.8.0"
    val kotlinxAtomicfuVersion = "0.19.0"
    val androidPluginVersion = "7.4.1"
    val kspVersion = "1.8.0-1.0.9"
    val ktorfitVersion = "1.0.0"
    val mokoResourcesVersion = "0.20.1"

    plugins {
        kotlin("android") version kotlinVersion
        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        kotlin("plugin.parcelize") version kotlinVersion
        id("com.android.application") version androidPluginVersion
        id("com.android.library") version androidPluginVersion
        id("de.jensklingenberg.ktorfit") version ktorfitVersion
        id("com.google.devtools.ksp") version kspVersion
        id("dev.icerock.mobile.multiplatform-resources") version mokoResourcesVersion
        id("kotlinx-atomicfu") version kotlinxAtomicfuVersion
    }
}

rootProject.name = "Letters KMM"
include(":androidApp")
include(":shared")
include(":androidApp:google")

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            // Kotlin
            val dateTimeVersion = "0.4.0"
            library("kotlinx-datetime", "org.jetbrains.kotlinx", "kotlinx-datetime").version(dateTimeVersion)

            // Concurrency
            val coroutinesVersion = "1.7.0"
            library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").version(coroutinesVersion)
            library("coroutines-android", "org.jetbrains.kotlinx", "kotlinx-coroutines-android").version(coroutinesVersion)

            val atomicfuVersion = "0.20.2"
            library("kotlinx-atomicfu", "org.jetbrains.kotlinx", "atomicfu").version(atomicfuVersion)

            // Architecture
            val decomposeVersion = "1.0.0"
            val essentyVersion = "1.0.0"
            library("decompose-core", "com.arkivanov.decompose", "decompose").version(decomposeVersion)
            library("decompose-compose", "com.arkivanov.decompose", "extensions-compose-jetpack").version(decomposeVersion)
            library("essenty-lifecycle", "com.arkivanov.essenty", "lifecycle").version(essentyVersion)
            library("essenty-backhandler", "com.arkivanov.essenty", "back-handler").version(essentyVersion)

            // Network
            val ktorVersion = "2.2.2"
            val ktorfitVersion = "1.0.0-beta17"
            library("ktor-core", "io.ktor", "ktor-client-core").version(ktorVersion)
            library("ktor-auth", "io.ktor", "ktor-client-auth").version(ktorVersion)
            library("ktor-serialization", "io.ktor", "ktor-serialization-kotlinx-json").version(ktorVersion)
            library("ktor-content-negotiation", "io.ktor", "ktor-client-content-negotiation").version(ktorVersion)
            library("ktor-logging", "io.ktor", "ktor-client-logging").version(ktorVersion)
            library("ktor-android", "io.ktor", "ktor-client-okhttp").version(ktorVersion)
            library("ktor-ios", "io.ktor", "ktor-client-darwin").version(ktorVersion)
            library("ktorfit-lib", "de.jensklingenberg.ktorfit", "ktorfit-lib").version(ktorfitVersion)
            library("ktorfit-ksp", "de.jensklingenberg.ktorfit", "ktorfit-ksp").version(ktorfitVersion)
            bundle(
                "ktor-shared",
                listOf(
                    "ktor-core",
                    "ktor-serialization",
                    "ktor-content-negotiation",
                    "ktor-auth",
                    "ktor-logging"
                )
            )

            // Replica
            val replicaVersion = "1.0.0-alpha13"
            library("replica-core", "com.github.aartikov", "replica-core").version(replicaVersion)
            library("replica-algebra", "com.github.aartikov", "replica-algebra").version(replicaVersion)
            library("replica-decompose", "com.github.aartikov", "replica-decompose").version(replicaVersion)
            library("replica-androidNetwork", "com.github.aartikov", "replica-android-network").version(replicaVersion)
            library("replica-devtools", "com.github.aartikov", "replica-devtools").version(replicaVersion)
            bundle(
                "replica-shared",
                listOf(
                    "replica-core",
                    "replica-algebra",
                    "replica-decompose"
                )
            )

            // Form validation
            val formValidationVersion = "1.0.0-alpha1"
            library("forms", "ru.mobileup", "kmm-form-validation").version(formValidationVersion)

            // Storage
            val okioVersion = "3.3.0"
            library("okio", "com.squareup.okio", "okio").version(okioVersion)

            // DI
            val koinVersion = "3.3.2"
            library("koin-core", "io.insert-koin", "koin-core").version(koinVersion)

            // Logging
            val kermitVersion = "1.2.2"
            library("logger-kermit", "co.touchlab", "kermit").version(kermitVersion)

            // Code quality
            val detectVersion = "1.22.0"
            library("detekt-formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").version(detectVersion)

            // Resources
            val mokoResourcesVersion = "0.20.1"
            library("moko-resources", "dev.icerock.moko", "resources").version(mokoResourcesVersion)
            library("moko-resourcesCompose", "dev.icerock.moko", "resources-compose").version(mokoResourcesVersion)

            // Android
            val androidDesugarVersion = "1.1.5"
            library("android-desugar", "com.android.tools", "desugar_jdk_libs").version(androidDesugarVersion)

            val playServicesAuthVersion = "20.4.0"
            val playServicesAuthApiPhoneVersion = "18.0.1"
            library("play-services-auth", "com.google.android.gms", "play-services-auth").version(playServicesAuthVersion)
            library("play-services-auth-phone", "com.google.android.gms", "play-services-auth-api-phone").version(playServicesAuthApiPhoneVersion)

            // Android UI
            val composeVersion = "1.4.0-beta02"
            val composeMaterialVersion = "1.5.0-alpha01"
            version("composeCompiler", "1.4.0")
            val activityComposeVersion = "1.6.1"
            val activityVersion = "1.6.1"
            val coilVersion = "2.1.0"
            val splashscreenVersion = "1.0.0"
            val phoneNumberVersion = "8.2.0"
            val accompanistVersion = "0.28.0"
            val collapsingToolbarVersion = "2.3.5"
            val lottieAnimationVersion = "6.1.0"
            library("activity", "androidx.activity", "activity-ktx").version(activityVersion)
            library("compose-ui", "androidx.compose.ui", "ui").version(composeVersion)
            library("compose-material", "androidx.compose.material", "material").version(composeMaterialVersion)
            library("compose-tooling", "androidx.compose.ui", "ui-tooling").version(composeVersion)
            library("activity-compose", "androidx.activity", "activity-compose").version(activityComposeVersion)
            library("compose-lottie", "com.airbnb.android", "lottie-compose").version(lottieAnimationVersion)
            bundle(
                "compose",
                listOf(
                    "compose-ui",
                    "compose-material",
                    "compose-tooling",
                    "activity-compose",
                    "compose-lottie"
                )
            )

            library("coil", "io.coil-kt", "coil-compose").version(coilVersion)
            library("splashscreen", "androidx.core", "core-splashscreen").version(splashscreenVersion)
            library("phone-number", "com.googlecode.libphonenumber", "libphonenumber").version(phoneNumberVersion)
            library("collapsing-toolbar", "me.onebone", "toolbar-compose").version(collapsingToolbarVersion)

            library("accompanist-flowlayout", "com.google.accompanist", "accompanist-flowlayout").version(accompanistVersion)
            library("accompanist-systemuicontroller", "com.google.accompanist", "accompanist-systemuicontroller").version(accompanistVersion)
            library("accompanist-swiperefresh", "com.google.accompanist", "accompanist-swiperefresh").version(accompanistVersion)
            bundle(
                "accompanist",
                listOf(
                    "accompanist-systemuicontroller",
                    "accompanist-swiperefresh",
                )
            )

            // Security
            val securityCrypto = "1.0.0"
            library("security-crypto", "androidx.security", "security-crypto").version(securityCrypto)

            // Debug tools
            val chuckerVersion = "3.5.2"
            val hyperionVersion = "0.9.34"
            val hyperionAddonsVersion = "0.3.3"
            library("chucker", "com.github.chuckerteam.chucker", "library").version(chuckerVersion)
            library("hyperion-core", "com.willowtreeapps.hyperion", "hyperion-core").version(hyperionVersion)
            library("hyperion-recorder", "com.willowtreeapps.hyperion", "hyperion-recorder").version(hyperionVersion)
            library("hyperion-crash", "com.willowtreeapps.hyperion", "hyperion-crash").version(hyperionVersion)
            library("hyperion-disk", "com.willowtreeapps.hyperion", "hyperion-disk").version(hyperionVersion)
            library("hyperion-addons-networkEmulation", "me.nemiron.hyperion", "network-emulation").version(hyperionAddonsVersion)
            library("hyperion-addons-chucker", "me.nemiron.hyperion", "chucker").version(hyperionAddonsVersion)
            bundle(
                "hyperion",
                listOf(
                    "hyperion-core",
                    "hyperion-recorder",
                    "hyperion-crash",
                    "hyperion-disk",
                    "hyperion-addons-networkEmulation",
                    "hyperion-addons-chucker"
                )
            )

            // Ad
            val yandexAdVersion = "5.10.0"
            library("ad-yandex", "com.yandex.android", "mobileads").version(yandexAdVersion)
            bundle(
                "ad",
                listOf(
                    "ad-yandex"
                )
            )

            // Compose Balloon
            val composeBalloon = "1.5.2"
            library("compose-balloon", "com.github.skydoves", "balloon-compose").version(composeBalloon)

            val mokoPermissionVersion = "0.14.0"
            library("moko-permissions", "dev.icerock.moko", "permissions").version(mokoPermissionVersion)
            library("moko-permissionsCompose", "dev.icerock.moko", "permissions-compose").version(mokoPermissionVersion)
            library("moko-permissionsTest", "dev.icerock.moko", "permissions-test").version(mokoPermissionVersion)

            val gmsLocationVersion = "21.0.1"
            library("gms-location", "com.google.android.gms", "play-services-location").version(gmsLocationVersion)

            val kotlinCoroutinesVersion = "1.4.1"
            library("coroutines-playServices", "org.jetbrains.kotlinx", "kotlinx-coroutines-play-services").version(kotlinCoroutinesVersion)
        }
    }
}
