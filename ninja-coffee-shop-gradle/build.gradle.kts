plugins {
	java
	id("org.springframework.boot") version "4.0.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.aspectj.post-compile-weaving") version "9.1.0"
}

group = "io.github.thanglequoc"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

val timerNinjaVersion = "1.3.0"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
	mavenLocal()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-webmvc:4.0.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation(group = "io.github.thanglequoc", name = "timer-ninja", version = timerNinjaVersion)
	aspect("io.github.thanglequoc:timer-ninja:$timerNinjaVersion")

	// Enable this if you want to track method in Test classes
	testAspect("io.github.thanglequoc:timer-ninja:$timerNinjaVersion")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
