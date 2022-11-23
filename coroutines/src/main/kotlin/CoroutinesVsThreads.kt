import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import kotlin.concurrent.thread

fun manyCoroutines(): Unit = runBlocking {
    repeat(1_000_000) { // it: Int
        launch { // new asynchronous activity
            delay(1000L)
            println("Hello from coroutine $it!")
        }
    }
}

fun manyThreads(): Unit = runBlocking {
    repeat(1_000_000) { // it: Int
        thread { // new asynchronous activity
            sleep(1000L)
            println("Hello from thread $it!")
        }
    }
}

fun main() {
    manyCoroutines()
    manyThreads()
}