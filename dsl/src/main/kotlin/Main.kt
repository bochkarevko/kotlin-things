package org.jetbrains.edu

import org.jetbrains.edu.dsl.Schedule
import org.jetbrains.edu.dsl.repeat
import org.jetbrains.edu.dsl.at
import org.jetbrains.edu.dsl.except
import org.jetbrains.edu.dsl.scenario

fun main() {
    val marie = MakroBot("Marie")
    
    scenario { 
        marie forward 3
        marie backward 2 
        marie.turnBack()
        
        marie {
            speed = 10_000
            power = 9_001
        }
        
        schedule {
            repeat(Schedule.WeekDay.Tue at 10, Schedule.WeekDay.Sat at 12) 
            except(13)
            repeat(Schedule.WeekDay.Wed..Schedule.WeekDay.Fri at 11)
        }

        marie {
            speed = 100_000
            power = 9_001
        }
    }
}
