plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.9.0"
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    namespace = "dev.geanbrandao.minhasdespesas"
    defaultConfig {
        applicationId = "dev.geanbrandao.minhasdespesas"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode =  libs.versions.versionCode.get().toInt()
        versionName =  libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
            arg("KOIN_CONFIG_CHECK","true") // talvez precise retirar
        }
    }

    applicationVariants.forEach { variant ->
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/${variant.name}/kotlin")
            }
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
        kotlinCompilerExtensionVersion = libs.versions.composeVersion.get()
    }
}

dependencies {

    implementation(project(":pie-charts"))
    implementation(project(":feature-split-bill"))
    implementation(project(":common"))
    implementation(project(":local-preferences"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.google.android.material)

    implementation(libs.compose.preferences)

//    Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

//    Compose preview
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    
    //    Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    //koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

//    hilt
//    implementation(libs.hilt.android)
//    "kapt"(libs.hilt.android.compiler)
//    "kapt"(libs.androidx.hilt.compiler)
//    implementation(libs.hilt.android)
//    implementation(libs.androidx.hilt.navigation.compose)
//    implementation(libs.androidx.hilt.lifecycle.viewmodel)
//    ksp(libs.hilt.android.compiler)

    implementation("io.github.boguszpawlowski.composecalendar:composecalendar:$calendarCompose")

    // coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.kotlinx.serialization.json)

    testImplementation("junit:junit:$junitVersion")
    testImplementation(libs.mockk)
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    androidTestImplementation("androidx.test.ext:junit:${Androidx.Test.junit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Androidx.Test.espresso}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    androidTestImplementation("androidx.test:core-ktx:${Androidx.Test.core}")
    androidTestImplementation("androidx.compose.ui:ui-test:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // this is for OffsetDataTime
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}
