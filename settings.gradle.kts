pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "CurrencyConverter"

include(":app")
include(":core-remote")
include(":core-data")
include(":core-shared")
include(":core-database")
include(":core-domain")
include(":core-testing")
include(":core-ui")
include(":feature-currencyconverter")
include(":feature-splash")