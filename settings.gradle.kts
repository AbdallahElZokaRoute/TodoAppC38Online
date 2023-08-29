import java.net.URI

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
//        maven {
//            url = URI("https://jitpack.io")
//        }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = URI("https://jitpack.io")
        }

    }
}

rootProject.name = "TodosAppC38Online"
include(":app")
