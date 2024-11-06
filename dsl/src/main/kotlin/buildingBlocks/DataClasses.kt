package org.jetbrains.edu.buildingBlocks

fun EssentialData.bumpVersion() = copy(version = version + 1)

infix fun String.of(version: Int) = EssentialData(this, version)

fun dataClassesExample() {
    val library = "KotlinSTD" of 1
    println(library)
    val libraryV2 = library.bumpVersion()
    println(libraryV2)
    
    val (libName, libVersion) = library
    
    val pair = libName to libVersion
    println(pair)
}
