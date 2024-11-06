package org.jetbrains.edu.buildingBlocks

fun String.kebabed() = this.lowercase().replace("_", "-").replace(" ", "-")

class Styler() {
    fun String.snaked() = this.lowercase().replace("-", "_").replace(" ", "_")
    
    fun<R> style(block: Styler.() -> R): R {
        return block()
    }
}

private val usefulRegex = Regex("[ \\-_][a-zA-Z]")

context(Styler)
fun String.pascaled() = 
    lowercase()
    .replace(usefulRegex) { it.value[1].toString().uppercase() }
    .replaceFirstChar { it.uppercase() }

fun extensionsExample() {
    val original = "Kotlin is a nice language"
    println(original.kebabed())
    val myStyler = Styler()
    println(myStyler.style {
        original.snaked()
    })
    println(myStyler.style { 
        original.pascaled()
    })
}
