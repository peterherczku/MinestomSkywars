plugins {
    id("java")
}

group = "dev.blockeed"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("net.minestom:minestom-snapshots:78f7cffbb3")
    implementation("com.github.TogAr2:MinestomPvP:-SNAPSHOT")
    implementation("com.google.code.gson:gson:2.11.0")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")

    testCompileOnly("org.projectlombok:lombok:1.18.34")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34")
}