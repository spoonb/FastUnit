plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.3"
}

group = "com.spoonb"
version = "0.0.1-alpha"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.1.4")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("java"))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    jar {
        // 将依赖一并打包进压缩包中
        from(configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        })
    }

    patchPluginXml {
        sinceBuild.set("232")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}

dependencies {
    // https://mvnrepository.com/artifact/org.freemarker/freemarker
    implementation("org.freemarker:freemarker:2.3.31")
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.24")
    // 添加 Lombok 的注解处理器
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    // 在测试文件中使用lombok
    testCompileOnly("org.projectlombok:lombok:1.18.24")
    // 添加 Lombok 的注解处理器适用test文件
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
}
