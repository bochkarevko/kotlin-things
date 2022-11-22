import kotlinx.coroutines.*

fun uselessExceptionHandler(): Unit = runBlocking {
    val jobs: List<Job> = List(1_000_000) {
        launch(
            Dispatchers.Default
                    + CoroutineName("Coroutine #$it")
                    + CoroutineExceptionHandler { context, error ->
                println("${context[CoroutineName]?.name} error:  $error")
            },
            CoroutineStart.LAZY
        ) { // new asynchronous activity
            randomLengthSuspend(500, 1000)
            if (it % 10 == 0) {
                throw IllegalStateException("Nothing to say")
            }
            println("Hello from coroutine $it!")
        }
    }

    jobs.forEach { it.start() }
}