import kotlinx.coroutines.CoroutineName
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

suspend fun suspendAnswer() = 42

suspend fun suspendSqr(x: Int) = x * x

fun main() {
    ::suspendAnswer.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = CoroutineName("Empty Context Simulation")

        override fun resumeWith(result: Result<Int>) {
            val prevResult = result.getOrThrow()
            ::suspendSqr.startCoroutine(prevResult,
                Continuation(
                    CoroutineName("")
                ) { it: Result<Int> ->
                    println(it.getOrNull())
            })
        }
    })
}