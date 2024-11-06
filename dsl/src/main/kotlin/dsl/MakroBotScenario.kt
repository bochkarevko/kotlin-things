package org.jetbrains.edu.dsl

import org.jetbrains.edu.MakroBot

@MakroBotDsl
class MakroBotScenario {
    private val actions = arrayListOf<() -> Unit>()
    private var schedule: Schedule? = null

    operator fun MakroBot.invoke(settings: MakroBot.() -> Unit) = this.settings()

    infix fun MakroBot.forward(steps: Int) {
        actions.add { stepForward(steps) }
    }

    infix fun MakroBot.backward(steps: Int) {
        actions.add { stepBack(steps) }
    }

    fun MakroBot.turnBack() {
        actions.add { turnAround() }
    }

    fun MakroBotScenario.schedule(scheduleFun: Schedule.() -> Unit): MakroBotScenario {
        schedule = Schedule().apply(scheduleFun)
        return this
    }

    fun MakroBotScenario.resetSchedule(): MakroBotScenario {
        schedule = null
        return this
    }
}

fun scenario(operations: MakroBotScenario.() -> Unit): MakroBotScenario =
    MakroBotScenario().apply(operations)
