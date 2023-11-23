// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//    kotlin("jvm") version "1.9.20" apply false
    id("com.android.application") version "8.1.2" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
}

buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.9.20"))
    }
}