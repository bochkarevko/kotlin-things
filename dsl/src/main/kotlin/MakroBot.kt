package org.jetbrains.edu

class MakroBot(val name: String) {
    var speed: Int = 5
    var power: Int = 3
    
    fun stepForward(steps: Int) = println("stepForward $steps")
    
    fun stepBack(steps: Int) = println("stepBack $steps")
    
    fun turnAround() = println("turnAround")
}
