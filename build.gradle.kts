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
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
repositories {
    mavenCentral()
    mindustryRepo()
}
dependencies {
    importMindustry()
}
mindustry {

    dependency {
        mindustry on "v145.1"
        arc on "v145"
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