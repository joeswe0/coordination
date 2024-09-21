tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

dependencies {
    implementation(project(":persistence:h2"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}
