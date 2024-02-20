plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}

buildscript {
    dependencies {
        classpath("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.8.0-1.0.8")
        classpath ("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0")
        classpath("com.google.gms:google-services:4.4.0")
    }
}