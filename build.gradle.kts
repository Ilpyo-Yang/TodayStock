plugins {
	val kotlinVersion = "1.9.24"
	war
	id("org.springframework.boot") version "3.3.2"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
}

allOpen {
	annotation("javax.persistence.Entity")
	annotation("javax.persistence.MappedSuperclass")
	annotation("javax.persistence.Embeddable")
}

noArg {
	annotation("javax.persistence.Entity")
}

group = "dev"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.2")
	implementation("org.springframework.boot:spring-boot-starter-security:3.3.2")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.2")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.3.2")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.24")
	implementation("org.postgresql:postgresql:42.7.2")
	implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.0")
	implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.0")
	implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.5.0")
	implementation ("com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.7")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat:3.3.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.2")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.24")
	testImplementation("org.springframework.security:spring-security-test:6.3.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.3")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
	jvmToolchain(22)
}

tasks.withType<Test> {
	useJUnitPlatform()
}
