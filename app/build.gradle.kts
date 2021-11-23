plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk= 31

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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
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
}

//tasks.register("teste") {
//    doLast {
//       println(VERSION_NAME_REVISION)
//    }
//}

dependencies {

    implementation("androidx.core:core-ktx:${Androidx.core}")
    implementation("androidx.appcompat:appcompat:${Androidx.appcompat}")
    implementation("com.google.android.material:material:${Google.material}")
    implementation("androidx.compose.ui:ui:${Androidx.Compose.ui}")
//    implementation("androidx.compose.material:material:${Androidx.Compose.material}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Androidx.Compose.toolingPreview}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Androidx.lifecycleRuntime}")
    implementation("androidx.activity:activity-compose:${Androidx.activityCompose}")
    implementation("androidx.compose.material3:material3:${Androidx.Compose.material3}")


    testImplementation ("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:${Androidx.Test.junit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Androidx.Test.espresso}")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:${Androidx.Compose.tooling}")
}