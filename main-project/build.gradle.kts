plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp")
    application
}

// Allows running from command line using  ./gradlew :main-project:run
application {
    mainClass.set("br.sample.MainKt")
}

// Makes generated code visible to IDE
kotlin.sourceSets.main {
    kotlin.srcDirs(
        file("${layout.buildDirectory}/generated/ksp/main/kotlin"),
    )
}

ksp {
    // Passing an argument to the symbol processor.
    // Change value to "true" in order to apply the argument.
    arg("ignoreGenericArgs", "false")
}

dependencies {
    implementation(project(":annotations"))
    ksp(project(":processor"))
}
