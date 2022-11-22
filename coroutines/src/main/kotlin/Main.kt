import kotlinx.coroutines.*
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.lang.IllegalStateException
import kotlin.random.Random







//fun main(): Unit = runBlocking {
//    val jobs: List<Job> = List(1_000_000) {
//        launch(
//            Dispatchers.Default
//                    + CoroutineName("Coroutine #$it")
//                    + CoroutineExceptionHandler { context, error ->
//                println("${context[CoroutineName]?.name} error:  $error")
//            },
//            CoroutineStart.LAZY
//        ) { // new asynchronous activity
//            delay(Random.nextLong(1000))
//            if (it % 10 == 0) {
//                throw IllegalStateException("Nothing to say")
//            }
//            println("Hello from coroutine $it!")
//        }
//    }
//    jobs.forEach { it.start() }
//}


//fun main(): Unit = runBlocking {
//    val startTime = System.currentTimeMillis()
//    val job = launch(Dispatchers.Default) {
//        var nextPrintTime = startTime
//        var i = 0
//        while (isActive) { // cancellable computation loop
//            // print a message twice a second
//            if (System.currentTimeMillis() >= nextPrintTime) {
//                println("job: I'm sleeping ${i++} ...")
//                nextPrintTime += 500L
//            }
//        }
//        println("I am not active anymore :(")
//    }
//    delay(1300L) // delay a bit
//    println("main: I'm tired of waiting!")
//    job.cancelAndJoin() // cancels the job and waits for its completion
//    println("main: Now I can quit.")
//}