package org.example

class KotlinClass {
    var kotlinProperty: String = "Kotlin property"

    @JvmField
    var javaField: String = "Kotlin property / java field"

    companion object {
        fun viaCompanionOnly() {
            println("viaCompanionOnly")
        }

        @JvmStatic
        fun fullStatic() {
            println("fullStatic")
        }
    }
}