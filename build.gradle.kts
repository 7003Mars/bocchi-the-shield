import io.github.liplum.mindustry.*

plugins {
    kotlin("jvm") version "1.9.0"
    id("io.github.liplum.mgpp") version "1.2.0"
}

sourceSets {
    main {
        java.srcDirs("src")
    }
    test {
        java.srcDir("test")
    }
}
group= "me.mars"
version= "1.0"
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenCentral()
    maven {
        name = "xpdustryRepositoryMindustry"
        url = uri("https://maven.xpdustry.com/mindustry")
    }
    mindustryRepo()
}
dependencies {
    importMindustry()
}
mindustry {

    dependency {
        mindustry on "v159"
        arc on "v159"
    }
    client{
        mindustry from GameLocation("mindustry-antigrief", "mindustry-client-v7-builds",
            "1381", "desktop.jar")
//        mindustry official "v141.3"
    }
//    server {
//        mindustry official "v141.3"
//    }
    deploy {
        baseName = project.name
    }

    run {
        keepOtherMods
        useDefaultDataDir
    }
}
mindustryAssets {
    root at "$projectDir/assets"
}

configurations.all{
    resolutionStrategy.eachDependency {
        if(this.requested.group == "com.github.Anuken.Arc"){
            this.useVersion("v159")
        }
    }
}