plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlin.serialization)
    id("com.google.firebase.crashlytics")
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.0" // Misma versión de Kotlin
}

android {
    namespace = "com.straccion.adivinalabandera"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.straccion.adivinalabandera"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("D:/Practicas Kotlin GooglePlay/AdivinaElAnimal/adivinaelanimal.jks") // Reemplaza con la ruta a tu keystore
            storePassword = project.property("KEYSTORE_PASSWORD") as String // Reemplaza con tu contraseña
            keyAlias = project.property("KEY_ALIAS") as String
            keyPassword = project.property("KEY_PASSWORD") as String
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true // Cambiado a true, recomendado para release
            isShrinkResources = true // Añadido para reducir el tamaño de recursos no utilizados
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release") // Usa la configuración de firma para release
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:deprecation")
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.kotlinx.serialization.json)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.datastore.preferences)
    //coil
    implementation(libs.coil.compose)


    //publicidad
    implementation(libs.play.services.ads)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.foundation)
    //Musica
    implementation(libs.androidx.media.exoplayer)

    //PlayGames
    implementation (libs.play.services.games.v2)
    implementation(libs.play.services.auth)
    //  implementation(libs.play.services.time)

    //iconos
    implementation(libs.androidx.material.icons.extended)

    //Firebase
    implementation(libs.firebase.database)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.firestore.ktx)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
kapt {
    correctErrorTypes = true
}