plugins {
    java
    application
    eclipse
    `check-lib-versions`
    id("org.graalvm.buildtools.native") version "0.9.10"
}

version = "0.10.4"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("org.asamk.signal.Main")
}

graalvmNative {
    binaries {
        this["main"].run {
            configurationFileDirectories.from(file("graalvm-config-dir"))
            buildArgs.add("--allow-incomplete-classpath")
            buildArgs.add("--report-unsupported-elements-at-runtime")
        }
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3", "okhttp", "4.9.2")
    implementation("com.squareup.okhttp3", "logging-interceptor", "4.9.2")
    implementation("org.bouncycastle", "bcprov-jdk15on", "1.70")
    implementation("com.fasterxml.jackson.core", "jackson-databind", "2.13.1")
    implementation("net.sourceforge.argparse4j", "argparse4j", "0.9.0")
    implementation("com.github.hypfvieh", "dbus-java-transport-native-unixsocket", "4.0.0")
    implementation("org.slf4j", "slf4j-api", "1.7.32")
    implementation("ch.qos.logback", "logback-classic", "1.2.10")
    implementation("org.slf4j", "jul-to-slf4j", "1.7.32")
    implementation(project(":lib"))
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

tasks.withType<Jar> {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Main-Class" to application.mainClass.get()
        )
    }
}

task("fatJar", type = Jar::class) {
    archiveBaseName.set("${project.name}-fat")
    exclude(
        "META-INF/*.SF",
        "META-INF/*.DSA",
        "META-INF/*.RSA",
        "META-INF/NOTICE",
        "META-INF/LICENSE",
        "**/module-info.class"
    )
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}
