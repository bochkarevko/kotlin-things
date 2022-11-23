import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.concurrent.locks.ReentrantLock

val lock = ReentrantLock()

suspend fun suspendWithLock() {
    lock.lock()
    randomLengthSuspend(max = 100)
    lock.unlock()
}

fun getProblems() = runBlocking {
    repeat(10) {
        launch(Dispatchers.Unconfined) {
            suspendWithLock()
        }
    }
}

val mutex = Mutex()
var counter = 0

suspend fun suspendWithMutex() {
    withContext(Dispatchers.Default) {
        repeat(1_000) {
            launch {
                // protect each increment with lock
                mutex.withLock {
                    counter++
                }
            }
        }
    }
    println("Counter = $counter")
}

fun main() = runBlocking {
    getProblems()
}