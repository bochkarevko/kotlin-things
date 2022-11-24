import kotlinx.coroutines.*
import kotlin.random.Random

fun main() {
    val scope = CoroutineScope(Job()) // here it is

    scope.launch {
        val jobs: List<Job> = List(1_000_000) {
            launch(
                Dispatchers.Default
                        + CoroutineName("#$it")
                        + CoroutineExceptionHandler { context, error ->
                    println("${context[CoroutineName]?.name}:  $error")
                }, CoroutineStart.LAZY) {
                delay(Random.nextLong(1000))
                if (it % 10 == 0) { throw Exception("No comments") }
                println("Hello from coroutine $it!")
            }
        }

        jobs.forEach { it.join() }
    }

    runBlocking {
        delay(1000)
    }
}