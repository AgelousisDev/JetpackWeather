buildscript {
    val kotlinVersion = "1.8.0"
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version "2.0.1" apply false
}

allprojects {
    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
        kotlinOptions {
            freeCompilerArgs =
                listOf(
                    *kotlinOptions.freeCompilerArgs.toTypedArray(),
                    "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                    "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
                    "-opt-in=androidx.compose.runtime.ExperimentalComposeApi",
                    "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
                    //"-opt-in=com.google.accompanist.pager.ExperimentalPagerApi",
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
                )
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}