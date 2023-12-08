dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            library("bouncycastle", "org.bouncycastle", "bcprov-jdk18on").version("1.77")
            library("jackson.databind", "com.fasterxml.jackson.core", "jackson-databind").version("2.16.0")
            library("argparse4j", "net.sourceforge.argparse4j", "argparse4j").version("0.9.0")
            library("dbusjava", "com.github.hypfvieh", "dbus-java-transport-native-unixsocket").version("4.3.1")
            version("slf4j", "2.0.9")
            library("slf4j.api", "org.slf4j", "slf4j-api").versionRef("slf4j")
            library("slf4j.jul", "org.slf4j", "jul-to-slf4j").versionRef("slf4j")
            library("logback", "ch.qos.logback", "logback-classic").version("1.4.14")


            library("signalservice", "com.github.turasa", "signal-service-java").version("2.15.3_unofficial_88")
            library("p6spy", "p6spy", "p6spy").version("3.9.1")
            library("sqlite", "org.xerial", "sqlite-jdbc").version("3.44.1.0")
            library("hikari", "com.zaxxer", "HikariCP").version("5.1.0")
            library("okhttp", "com.squareup.okhttp3", "logging-interceptor").version("4.11.0")
            library("junit.jupiter", "org.junit.jupiter", "junit-jupiter").version("5.10.1")
            library("junit.launcher", "org.junit.platform", "junit-platform-launcher").version("1.10.1")
            library("fastjson", "com.alibaba.fastjson2", "fastjson2").version("2.0.43")
        }
    }
}

rootProject.name = "signal-cli"
include("lib")
