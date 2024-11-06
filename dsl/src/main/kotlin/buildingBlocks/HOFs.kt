package org.jetbrains.edu.buildingBlocks

import kotlin.math.pow

fun simplyTwo() = 2.0

fun<R> higherOrderFunction(otherFunction: () -> R): R {
    println("higher order function is called")
    val result = otherFunction()
    println("higher order function got $result")
    return result
}

fun hofExample() {
    higherOrderFunction {
        val y = higherOrderFunction({
            val x = higherOrderFunction(::simplyTwo)
            x.pow(2)
        })
        y.pow(2)
    }
}
