plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") // Plugin para KSP
    id("com.google.dagger.hilt.android") // Plugin para Hilt
    id("org.jetbrains.kotlin.plugin.compose")
   // id("org.jetbrains.kotlin.plugin.serialization")
    //alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "edu.ucne.composedemo"
    compileSdk = 35

    defaultConfig {
        applicationId = "edu.ucne.composedemo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true") // Mejora el tiempo de compilación
    arg("room.expandProjection", "true") // Optimiza consultas
}

dependencies {
    // Core y Compose
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0")

    implementation ("androidx.compose.ui:ui:1.0.0")
    implementation ("androidx.compose.material3:material3:1.0.0")
    implementation( "androidx.compose.runtime:runtime-livedata:1.0.0")

    //Navigation
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose.v285)

    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
   // implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")
    implementation ("androidx.compose.material3:material3:1.0.0") // Dependencia de Material3 (con íconos)
    implementation ("androidx.compose.material:material-icons-extended:1.0.0") // Íconos extendidos


    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.androidx.storage)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.ktx)
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51")
    ksp("com.google.dagger:hilt-android-compiler:2.51")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Retrofit y Moshi
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Pruebas
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
