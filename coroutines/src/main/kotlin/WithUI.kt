import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val ui = newSingleThreadContext("UI")
    val io = newSingleThreadContext("IO")
    val uiScope = CoroutineScope(ui)

    uiScope.launch {
        launch {
            try {
                while (isActive) {
                    println("UI thread is not blocked.")
                    delay(100)
                }
            } finally {
                println("UI (view) is destroyed")
            }
        }
        println("UI fetches the answer")
        val answer: Int
        withContext(io) {
            answer = sleepyProvider()
        }
        println("Ui got answer = $answer")
    }

    runBlocking {
        delay(1234)
        uiScope.cancel()
    }
}