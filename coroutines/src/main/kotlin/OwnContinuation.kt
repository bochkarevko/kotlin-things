import kotlinx.coroutines.CoroutineName
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

suspend fun susanswer() = 3

suspend fun sussqr(x: Int) = x * x

suspend fun sussum(x: Int, y: Int) = x + y

fun main() {
    ::susanswer.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = CoroutineName("Empty Context Simulation")

        override fun resumeWith(result: Result<Int>) {
            val prevResult = result.getOrThrow()
            ::sussqr.startCoroutine(prevResult,
                Continuation(
                    CoroutineName("")
                ) { it: Result<Int> ->
                    println(it.getOrNull())
            })
        }
    })
}