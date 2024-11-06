package org.jetbrains.edu.buildingBlocks

infix fun String.times(howMany: Int) = buildString {
    repeat(howMany) {
        append(this@times)
        append(" ")
    }
}

fun infixExample() {
    val what = "What?"
    println(what times 10)
}
