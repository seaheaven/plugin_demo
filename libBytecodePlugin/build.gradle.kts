plugins {
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
dependencies {
    // gradle
    implementation(gradleApi())
    // asm
    implementation(libs.asm)
    implementation (libs.asm.commons)
    implementation (libs.asm.analysis)
    implementation (libs.asm.util)
    implementation (libs.asm.tree)
}
version = "1.0.0"
group = "com.yirun"

publishing {
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("publishing-repository"))
        }
    }
}