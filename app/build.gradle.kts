plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "dev.geanbrandao.minhasdespesas"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
        kotlinCompilerVersion = kotlinVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":pie-charts"))

    implementation("androidx.core:core-ktx:${Androidx.core}")
    implementation("androidx.appcompat:appcompat:${Androidx.appcompat}")
    implementation("com.google.android.material:material:${Google.material}")
    implementation("androidx.compose.ui:ui:${Androidx.Compose.ui}")
//    implementation("androidx.compose.material:material:${Androidx.Compose.material}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Androidx.Compose.toolingPreview}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Androidx.lifecycleRuntime}")
    implementation("androidx.activity:activity-compose:${Androidx.activityCompose}")
    implementation("androidx.compose.material3:material3:${Androidx.Compose.material3}")
    implementation("androidx.navigation:navigation-compose:${Androidx.navigationCompose}")
    implementation("androidx.compose.material:material:${Androidx.Compose.material}")
    implementation("com.github.Sh4dowSoul.ComposePreferences:compose-preferences:${Preferences.composePreferences}")

    implementation("androidx.room:room-runtime:${Androidx.room}")
    implementation("androidx.room:room-ktx:${Androidx.room}")
    "kapt"("androidx.room:room-compiler:${Androidx.room}")

    implementation("com.google.dagger:hilt-android:$hiltVersion")
    "kapt"("com.google.dagger:hilt-android-compiler:$hiltVersion")
//    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    "kapt"("androidx.hilt:hilt-compiler:${Androidx.hilt}")
    implementation("androidx.hilt:hilt-navigation-compose:${Androidx.hilt}")

    implementation("io.github.boguszpawlowski.composecalendar:composecalendar:$calendarCompose")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Jetbrains.kotlinxCoroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Jetbrains.kotlinxCoroutines}")

    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:${Androidx.Test.junit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Androidx.Test.espresso}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    androidTestImplementation("androidx.test:core-ktx:${Androidx.Test.core}")
    androidTestImplementation("androidx.compose.ui:ui-test:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    // this is for OffsetDataTime
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}
