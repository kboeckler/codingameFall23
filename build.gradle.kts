// For `KotlinCompile` task below
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.21" // Kotlin version to use
}

group = "de.kevinboeckler.cg" // A company name, for example, `org.jetbrains`
version = "1.0-SNAPSHOT" // Version to assign to the built artifact

repositories { // Sources of dependencies. See 2️⃣
    mavenCentral() // Maven Central Repository. See 3️⃣
}

dependencies { // All the libraries you want to use. See 4️⃣

    testImplementation(kotlin("test")) // The Kotlin test library
}

tasks.test { // See 5️⃣
    useJUnitPlatform() // JUnitPlatform for tests. See 6️⃣
}

kotlin { // Extension for easy setup
    jvmToolchain(11) // Target version of generated JVM bytecode. See 7️⃣
}
