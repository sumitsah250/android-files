import java.net.URI

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven { url= uri("https://jitpack.io") }
        gradlePluginPortal()
        maven {
            url =uri("https://github.com/jitsi/jitsi-maven-repository/raw/master/releases")
        }
        maven {
            url =uri("https://maven.google.com")
        }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url= uri("https://jitpack.io") }
        maven {
            url =uri("https://github.com/jitsi/jitsi-maven-repository/raw/master/releases")
        }
        maven {
            url =uri("https://maven.google.com")
        }
    }
}

rootProject.name = "KuraaKaani"
include(":app")
 