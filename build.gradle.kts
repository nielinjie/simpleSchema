import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.2.0"
    id("maven-publish")
}

group = "xyz.nietongxue"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }

}




dependencies {
    //jackson json 相关
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    //jackson kotlin 相关
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("tv.twelvetone.rjson:rjson:1.3.1-SNAPSHOT")

    implementation("xyz.nietongxue:common-jvm:2.0.0")

    implementation("io.swagger.core.v3:swagger-models:2.2.7")

    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("com.github.erosb:json-sKema:0.20.0")


    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xmulti-dollar-interpolation"))
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xmulti-dollar-interpolation"))
}
publishing {
    repositories {
        maven {
            name = "repsy"
            credentials {
                username = project.findProperty("repsyUser") as String? ?: System.getenv("REPSYUSER")
                password = project.findProperty("repsyToken") as String? ?: System.getenv("REPSYPASS")
            }
            url = uri("https://repo.repsy.io/mvn/nielinjie/default")
        }
    }
    publications {
        register<MavenPublication>("default") {
            from(components["java"])
        }
    }
}