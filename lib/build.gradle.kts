plugins {
    `java-library`
    `check-lib-versions`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.github.turasa", "signal-service-java", "2.15.3_unofficial_43")
    implementation("com.fasterxml.jackson.core", "jackson-databind", "2.13.1")
    implementation("com.google.protobuf", "protobuf-javalite", "3.11.4")
    implementation("org.bouncycastle", "bcprov-jdk15on", "1.70")
    implementation("org.slf4j", "slf4j-api", "1.7.32")
    implementation("org.xerial", "sqlite-jdbc", "3.36.0.3")
    implementation("com.zaxxer", "HikariCP", "5.0.1")
    implementation("com.squareup.okhttp3", "okhttp", "4.9.2")
    implementation("com.squareup.okhttp3", "logging-interceptor", "4.9.2")

}

configurations {
    implementation {
        resolutionStrategy.failOnVersionConflict()
    }
}

tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    manifest {
        attributes("Automatic-Module-Name" to "org.asamk.signal.manager")
    }
}
