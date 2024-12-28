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
        gradlePluginPortal()
        maven { url= uri("https://jitpack.io") }
        maven { url =uri("https://repository.liferay.com/nexus/content/repositories/public/")}
        maven { url =uri("https://jcenter.bintray.com")}
        jcenter()


    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url= uri("https://jitpack.io") }
        maven { url =uri("https://repository.liferay.com/nexus/content/repositories/public/")}
        maven { url=uri("https://jcenter.bintray.com") }
        gradlePluginPortal()
        jcenter()

    }
}

rootProject.name = "Class 10 All Guide & Manual 2081"
include(":app")
 