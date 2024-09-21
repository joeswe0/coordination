plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":core"))
    implementation(project(":persistence:h2"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
}

tasks {
    bootJar {
        manifest {
            attributes(
                "Main-Class" to "com.commerce.coordination.api.CoordinationApplication"
            )
        }
    }
}

springBoot {
    mainClass.set("com.commerce.coordination.api.CoordinationApplication")
}
