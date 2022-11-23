import kotlinx.coroutines.*

fun CoroutineScope.uselessExceptionHandler() {
    repeat(23) {
        launch(SupervisorJob() // supervisor job -> other children won't be cancelled
                + CoroutineName("Coroutine #$it")
                + CoroutineExceptionHandler { _, error -> println("Supervisor sees error: ${error.message}") }
        ) { // new asynchronous activity
            randomLengthSuspend(500, 1000)
            if (it % 7 == 0 && it > 0) {
                // another asynchronous activity with "separate" exception handler, which is useless
                launch(CoroutineExceptionHandler { _, error -> println("launch sees error: $error") }) {
                    throw IllegalStateException("${coroutineContext[CoroutineName]?.name} says hi!")
                }
                // exception goes to the CoroutineScope, not to the ExceptionHandler defined above
            }
            println("Hello from coroutine $it!")
        }
    }
}

fun allChildrenFail() = runBlocking { // root coroutine
    val job1 = launch {
        delay(500)
        println("Some jobs just want to watch the world burn")
        throw IndexOutOfBoundsException()
    }
    val job2 = launch {
        println("Going to do something extremely useful")
        delay(10000)
        println("I've done something extremely useful")
    }
}

fun supervisor() = runBlocking {
    val supervisor = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisor)) {
        val job1 = launch {
            delay(500)
            println("Some jobs just want to watch the world burn")
            throw IndexOutOfBoundsException()
        }
        val job2 = launch {
            println("Going to do something extremely useful")
            delay(3000)
            println("I've done something extremely useful")
        }
    }
    supervisor.join()
}

fun supervisorScope() = runBlocking {
    supervisorScope {
        val job1 = launch() {
            delay(500)
            println("Some jobs just want to watch the world burn")
            throw IndexOutOfBoundsException()
        }
        val job2 = launch() {
            println("Going to do something extremely useful")
            delay(3000)
            println("I've done something extremely useful")
        }
    }
}

fun overridingHandler() = runBlocking(CoroutineExceptionHandler { context, error ->
    println("root handler called")
}) {
    supervisorScope {
        launch {
            throw Exception()
        }
        launch(CoroutineExceptionHandler { context, error ->
            println("personal handler called")
        }) {
            throw Exception()
        }
    }
}

fun main() = runBlocking {
    uselessExceptionHandler()
    delay(1000)
}
