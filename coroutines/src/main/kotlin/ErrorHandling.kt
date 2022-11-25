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

fun CoroutineScope.jobThatThrows() =
    launch {
        delay(500)
        println("Some jobs just want to watch the world burn")
        throw Exception("Burn!")
    }

fun allChildrenFail() = runBlocking { // root coroutine
    val job1 = jobThatThrows()
    val job2 = launch {
        println("Going to do something extremely useful")
        delay(10000)
        println("I've done something extremely useful")
    }
    val job3 = launch {
        println("I want to be useful, too")
        delay(5000)
        println("I've done something extremely useful, too")
    }
}

fun supervisorJobDoesNotFail() {
    val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    with(scope) {// root coroutine
        val job1 = jobThatThrows()
        val job2 = launch {
            println("Going to do something extremely useful")
            delay(3000)
            println("I've done something extremely useful")
        }
    }
    scope.coroutineContext[Job.Key]?.let { job ->
        runBlocking {
            try {
                job.children.forEach { it.join() }
            } catch (_: Exception) {
                // we can check which jobs have failed here
            }
        }
    }
}

fun supervisorScopeDoesNotFail() {
    val somePreExistingScope = CoroutineScope(Dispatchers.Default)
    val supervisedJob = somePreExistingScope.launch { // root coroutine
        supervisorScope { // scope with SupervisorJob as the main Job
            val job1 = jobThatThrows()
            val job2 = launch {
                println("Going to do something extremely useful")
                delay(3000)
                println("I've done something extremely useful")
            }
        }
    }
    runBlocking { supervisedJob.join() }
}

fun overridingHandler() {
    val scopeWithHandler = CoroutineScope(CoroutineExceptionHandler { context, error ->
        println("root handler called")
    })
    scopeWithHandler.launch {
        supervisorScope {
            jobThatThrows()
            launch(CoroutineExceptionHandler { context, error ->
                println("personal handler called")
            }) {
                jobThatThrows()
            }
        }
    }
    scopeWithHandler.coroutineContext[Job.Key]?.let { job ->
        runBlocking {
            try {
                job.children.forEach { it.join() }
            } catch (_: Exception) {
                // we can check which jobs have failed here
            }
        }
    }
}

fun main() {
    // uselessExceptionHandler()
    // allChildrenFail()
    // supervisorJobDoesNotFail()
    // supervisorScopeDoesNotFail()
    overridingHandler()
}
