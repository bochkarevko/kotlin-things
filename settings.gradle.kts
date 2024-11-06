plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "kotlin-things"

include("coroutines")
include("deadlock")
include("tree-recursion")
include("various")
include("java-module")
include("dsl")
include("dsl")
