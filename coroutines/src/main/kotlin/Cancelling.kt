import kotlinx.coroutines.*

fun wrongCancel() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(5) {
            println("coroutine: I'm sleeping $it...")
            Thread.sleep(500)
        }
    }
    yield() // let the coroutine work
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
    println("main: Now I can quit.")
}

fun delayCancel() = runBlocking {
    val job = launch(Dispatchers.Default) {
        repeat(5) {
            try {
                println("coroutine: I'm sleeping $it...")
                delay(500)
            } catch (e: CancellationException) {
                println("coroutine: I wont give up $it")
            }
        }
    }
    yield() // let the coroutine work
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // join + cancel
    println("main: Now I can quit.")
}

fun checkCancel() = runBlocking {
    val job = launch(Dispatchers.Default) {
        var i = 0
        while (isActive && i < 5) {
            println("coroutine: I'm sleeping ${i++}...")
            Thread.sleep(500)
        }
    }
    delay(1300L) // let the coroutine work
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // join + cancel
    println("main: Now I can quit.")
}

fun finallyAndNonCancellable() = runBlocking {
    val job = launch {
        try {
            repeat(1_000) {
                println("job: I'm sleeping $it...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                println("job: I'm running finally")
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}

fun main() = runBlocking {
    wrongCancel()
    delayCancel()
    checkCancel()
    finallyAndNonCancellable()
}