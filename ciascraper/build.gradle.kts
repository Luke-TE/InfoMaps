import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	application
	id("org.springframework.boot") version "2.2.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm")
	kotlin("plugin.spring") version "1.3.61"
}

group = "com.infomaps"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

application {
	mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://kotlin.bintray.com/ktor")
	}
	maven {
		url = uri("https://kotlin.bintray.com/kotlin-js-wrappers")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

//	implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version"
//	implementation "io.ktor:ktor-server-netty:$ktor_version"
//	implementation "ch.qos.logback:logback-classic:$logback_version"
//	implementation "io.ktor:ktor-server-core:$ktor_version"
//	implementation "io.ktor:ktor-html-builder:$ktor_version"
//	implementation "org.jetbrains:kotlin-css-jvm:1.0.0-pre.31-kotlin-1.2.41"
//	implementation "io.ktor:ktor-client-core:$ktor_version"
//	implementation "io.ktor:ktor-client-core-jvm:$ktor_version"
//	implementation "io.ktor:ktor-client-apache:$ktor_version"
//	testImplementation "io.ktor:ktor-server-tests:$ktor_version"
//	implementation "io.ktor:ktor-network:$ktor_version"
//	implementation "io.ktor:ktor-client-cio:$ktor_version"
//	implementation "io.ktor:ktor-jackson:$ktor_version"
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}