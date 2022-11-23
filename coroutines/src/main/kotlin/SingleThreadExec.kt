import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun sleepyProvider(): Int =
    suspendCoroutine { cont ->
        println("Going to sleep a little bit on a thread ${Thread.currentThread().name}")
        Thread.sleep(1000)
        println("Woke up on thread ${Thread.currentThread().name} and ready to roll!")
        cont.resume(42)
    }

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val solo = newSingleThreadContext("MySoloContext")
    val scope = CoroutineScope(solo)

    scope.launch {
        repeat(2) {
            launch {
                try {
                    while (isActive) {
                        println("#$it is chilling on a thread ${Thread.currentThread().name}")
                        delay(100)
                    }
                } finally {
                    println("#$it was cancelled :(")
                }
            }
        }
        val answer = sleepyProvider()
        println("Go my answer $answer and ready to finish")
    }

    runBlocking {
        delay(1234)
        scope.cancel()
    }
}