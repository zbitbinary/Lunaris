plugins {
    application
}

dependencies {
    implementation(project(":lunaris-common"))
    implementation(project(":lunaris-protocol"))
    implementation(project(":lunaris-network"))
    implementation(project(":lunaris-world"))
    implementation(project(":lunaris-entity"))
    implementation(project(":lunaris-api"))
    implementation(project(":lunaris-plugin"))
    implementation(project(":lunaris-game"))
}

application {
    mainClass.set("org.lunaris.server.LunarisMain")
}
