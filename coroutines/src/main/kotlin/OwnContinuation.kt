import kotlinx.coroutines.CoroutineName
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

suspend fun susanswer() = 3

suspend fun sussqr(x: Int) = x * x

suspend fun sussum(x: Int, y: Int) = x + y

class PrintContinuation<T> : Continuation<T> {
    override val context: CoroutineContext
        get() = CoroutineName("Empty Context Simulation")

    override fun resumeWith(result: Result<T>) {
        if (result.isFailure) {
            println("failed :(")
        } else {
            println("result: ${result.getOrNull()}")
        }
    }
}

fun main() {
    ::susanswer.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = CoroutineName("Empty Context Simulation")

        override fun resumeWith(result: Result<Int>) {
            val prevResult = result.getOrThrow()
            ::sussqr.startCoroutine(prevResult, PrintContinuation())
        }
    })
}