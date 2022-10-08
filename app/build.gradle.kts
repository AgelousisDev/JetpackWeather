plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.github.ben-manes.versions") version "0.42.0"
}

val navVersion = "2.5.1"
val composeVersion = "1.3.0-rc01"
val constraintLayoutVersion = "2.1.3"
val constraintLayoutComposeVersion = "1.1.0-alpha04"
val composeActivityVersion = "1.7.0-alpha01"
val composeMaterialYouVersion = "1.0.0-rc01"
val liveDataViewModelVersion = "2.6.0-alpha02"
val playServicesMapsVersion = "18.1.0"
val playServicesLocationVersion = "20.0.0"
val materialYouVersion = "1.8.0-alpha01"
val lottieComposeVersion = "5.2.0"
val browserVersion = "1.4.0"
val dataStorePreferenceVersion = "1.0.0"
val composeMaterialYouWindowSizeVersion = "1.0.0-rc01"

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.agelousis.jetpackweather"
        minSdk = 27
        targetSdk = 33
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
            buildConfigField(type = "String", name = "WEATHER_API_LOGO_URL", value = "\"https://cdn.weatherapi.com/v4/images/weatherapi_logo.png\"")
            buildConfigField(type = "String", name = "WEATHER_API_WEB_URL", value = "\"https://www.weatherapi.com/\"")
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
            buildConfigField(type = "String", name = "WEATHER_API_LOGO_URL", value = "\"https://cdn.weatherapi.com/v4/images/weatherapi_logo.png\"")
            buildConfigField(type = "String", name = "WEATHER_API_WEB_URL", value = "\"https://www.weatherapi.com/\"")
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
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    // Native
    implementation("com.google.android.material:material:$materialYouVersion")
    // Datastore
    implementation("androidx.datastore:datastore-preferences:$dataStorePreferenceVersion")

    // Browser
    implementation("androidx.browser:browser:$browserVersion")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.activity:activity-compose:$composeActivityVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material3:material3:$composeMaterialYouVersion")
    implementation("androidx.compose.material3:material3-window-size-class:$composeMaterialYouWindowSizeVersion")
    implementation("androidx.compose.ui:ui-viewbinding:$composeVersion")
    implementation("androidx.compose.runtime:runtime-livedata:$composeVersion")
    implementation("androidx.constraintlayout:constraintlayout-compose:$constraintLayoutComposeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$liveDataViewModelVersion")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("androidx.glance:glance-appwidget:1.0.0-alpha05")
    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10")

    // Navigation
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-compose:$navVersion")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0-alpha02")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Google Maps
    implementation("com.google.android.gms:play-services-location:$playServicesLocationVersion")
    implementation("com.google.android.gms:play-services-maps:$playServicesMapsVersion")
    implementation("com.google.maps.android:maps-compose:2.7.2")

    // Accompanist
    implementation("com.google.accompanist:accompanist-swiperefresh:0.26.5-rc")

    // Lottie
    implementation("com.airbnb.android:lottie-compose:$lottieComposeVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4-beta01")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0-beta01")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")
}