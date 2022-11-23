import kotlinx.coroutines.*
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlin.random.Random

// Message types for counterActor
sealed interface CounterMsg
object Increment : CounterMsg // one-way message to increment counter
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg // a request with reply
object Reset : CounterMsg
class DecreaseBy(val value: Int) : CounterMsg

fun CoroutineScope.getCounter(counter: SendChannel<CounterMsg>) =
    launch {
        val response = CompletableDeferred<Int>()
        counter.send(GetCounter(response))
        println("Counter = ${response.await()}")
    }


// This function launches a new counter actor
@OptIn(ObsoleteCoroutinesApi::class)
fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0 // actor state
    for (msg in channel) { // iterate over incoming messages
        when (msg) {
            is Increment -> counter++
            is GetCounter -> msg.response.complete(counter)
            is Reset -> counter = 0
            is DecreaseBy -> counter -= msg.value
        }
    }
}

fun CoroutineScope.randomlyPokeActor(counter: SendChannel<CounterMsg>) =
    launch {
        while (isActive) {
            delay(Random.nextLong(100))
            val p = Random.nextDouble(7.0)
            when {
                p > 6 -> counter.send(Reset)
                p > 5 -> counter.send(DecreaseBy(3))
                p > 4 -> getCounter(counter)  // we will not wait ;^)
                else -> counter.send(Increment)
            }
        }
    }

fun runActor() = runBlocking<Unit> {
    val counter = counterActor() // create the actor
    val poker1 = randomlyPokeActor(counter)
    val poker2 = randomlyPokeActor(counter)
    delay(2000)
    poker1.cancel()
    poker2.cancel()
    val getJob = getCounter(counter)
    getJob.join()
    counter.close() // shutdown the actor
}

fun main() {
    runActor()
}