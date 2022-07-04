plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

val navVersion = "2.5.0-beta01"
val composeVersion = "1.2.0-beta03"
val constraintLayoutVersion = "2.1.3"
val constraintLayoutComposeVersion = "1.1.0-alpha02"
val composeActivityVersion = "1.5.0-beta01"
val composeMaterialYouVersion = "1.0.0-alpha14"
val liveDataViewModelVersion = "2.5.0-beta01"
val playServicesMapsVersion = "18.0.2"
val playServicesLocationVersion = "19.0.1"
val materialYouVersion = "1.7.0-alpha02"

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.agelousis.jetpackweather"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName(name = "debug")
            isDebuggable = true
            buildConfigField(type = "String", name = "WEATHER_BASE_URL", value = "\"https://api.weatherapi.com/v1/\"")
            buildConfigField(type ="String", name = "WEATHER_API_KEY", value = "\"3299b75bd83b4133b1e52728221706\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    name = "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
            buildConfigField(type = "String", name = "WEATHER_BASE_URL", value = "\"https://api.weatherapi.com/v1/\"")
            buildConfigField(type ="String", name = "WEATHER_API_KEY", value = "\"3299b75bd83b4133b1e52728221706\"")
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
    }
    packagingOptions {
        resources {
            exclude("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    // Native
    implementation("com.google.android.material:material:$materialYouVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.activity:activity-compose:$composeActivityVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:$composeMaterialYouVersion")
    implementation("androidx.compose.ui:ui-viewbinding:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.constraintlayout:constraintlayout-compose:$constraintLayoutComposeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$liveDataViewModelVersion")
    implementation("io.coil-kt:coil-compose:2.1.0")
    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.8")

    // Navigation
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Google Maps
    implementation("com.google.android.gms:play-services-location:$playServicesLocationVersion")
    implementation("com.google.android.gms:play-services-maps:$playServicesMapsVersion")
    implementation("com.google.maps.android:maps-compose:2.2.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
}