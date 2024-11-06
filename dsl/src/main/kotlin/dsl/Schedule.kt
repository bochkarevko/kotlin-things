package org.jetbrains.edu.dsl

import org.jetbrains.edu.dsl.Schedule.WeekDay

@MakroBotDsl
class Schedule {
    val timePoints = arrayListOf<Pair<WeekDay, Int>>()
    val exceptDaysOfMonth = arrayListOf<Int>()

    override fun toString(): String {
        return buildString {
            append(timePoints.joinToString(prefix = "Schedule: ") { "${it.first} at ${it.second}h" })
            if (exceptDaysOfMonth.isNotEmpty()) {
                append(exceptDaysOfMonth.joinToString(prefix = " except: ", postfix = " days of month"))
            }
        }
    }

    enum class WeekDay {
        Mon, Tue, Wed, Thu, Fri, Sat, Sun
    }
}

typealias time = Pair<WeekDay, Int>

infix fun WeekDay.at(hour: Int) = time(this, hour)

fun Schedule.repeat(vararg timePointsToAdd: time) {
    timePoints.addAll(timePointsToAdd)
}

fun Schedule.except(vararg daysOfMonth: Int) {
    exceptDaysOfMonth.addAll(daysOfMonth.toList())
}

infix fun ClosedRange<WeekDay>.at(hour: Int): List<time> {
    return WeekDay.entries.filter { it in this }.map { time(it, hour) } 
}

fun Schedule.repeat(timePointsToAdd: List<time>) = repeat(*timePointsToAdd.toTypedArray())
