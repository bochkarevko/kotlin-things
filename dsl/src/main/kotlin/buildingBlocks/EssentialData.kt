package org.jetbrains.edu.buildingBlocks

data class EssentialData(val name: String, val version: Int) {
    val description: String
        get() = "$name:$version"
}
