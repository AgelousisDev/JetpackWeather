plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.github.ben-manes.versions") version "0.48.0"
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = "com.agelousis.jetpackweather"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCodeVersion
        versionName = ConfigData.versionNameVersion

        // Languages
        resourceConfigurations.clear()
        resourceConfigurations.add("en")
        resourceConfigurations.add("el")

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
        kotlinCompilerExtensionVersion = Versions.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    namespace = ConfigData.packageName
}

dependencies {
    implementation(Dependencies.coreKtx)
    // Native
    implementation(Dependencies.material)
    implementation(Dependencies.appCompat)
    // Datastore
    implementation(Dependencies.dataStorePreferences)

    // Browser
    implementation(Dependencies.browser)

    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.activityCompose)
    implementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeMaterialYou)
    implementation(Dependencies.composeMaterialYouWindowSizeClass)
    implementation(Dependencies.composeUiViewBinding)
    implementation(Dependencies.composeRuntimeLiveData)
    implementation(Dependencies.constraintLayoutCompose)
    implementation(Dependencies.lifecycleViewModelCompose)
    implementation(Dependencies.coilCompose)
    implementation(Dependencies.glanceAppWidget)
    // Retrofit & OkHttp
    implementation(Dependencies.retrofit2ConverterGson)
    implementation(Dependencies.http3LoggingInterceptor)

    // Navigation
    implementation(Dependencies.navigationUiKtx)
    implementation(Dependencies.navigationCompose)

    // ViewModel
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleExtensions)

    // Google Maps
    implementation(Dependencies.gmsPlayServicesLocation)
    implementation(Dependencies.gmsPlayServicesMaps)
    implementation(Dependencies.androidMapsCompose)

    // Lottie
    implementation(Dependencies.lottieCompose)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.testExtJunit)
    androidTestImplementation(Dependencies.testEspressoCore)
    androidTestImplementation(Dependencies.composeUiTestJunit)
    debugImplementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeUiTestManifest)
}