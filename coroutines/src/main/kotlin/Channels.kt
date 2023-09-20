import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlin.random.Random

fun channels() = runBlocking {
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) channel.send(x * x)
    }
    // here we print five received integers:
    repeat(5) {
        println(channel.receive())
    }
    println("Done!")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
    var x = start
    while (true) send(x++) // infinite stream of integers from start
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
    for (x in numbers) if (x % prime != 0) send(x)
}

fun getPrimes(n: Int, sep: String = System.lineSeparator()) = runBlocking {
    var cur = numbersFrom(2)
    repeat(n) {
        val prime = cur.receive()
        print("$prime$sep")
        cur = filter(cur, prime)
    }
    coroutineContext.cancelChildren()
}

fun tenPrimes() = getPrimes(10)

fun <T> CoroutineScope.production(ch: SendChannel<T>, msg: T) =
    launch {
        while (true) {
            delay(Random.nextLong(23)); ch.send(msg)
        }
    }

fun <T> CoroutineScope.processing(ch: ReceiveChannel<T>, name: String) =
    launch {
        for (msg in ch) {
            println("$name: received $msg")
        }
    }

fun pubSubLike() = runBlocking {
    val channel = Channel<String>()
    listOf("foo", "bar", "baz").forEach { production(channel, it) }
    repeat(8) { processing(channel, "worker #$it") }
    delay(666)
    coroutineContext.cancelChildren(CancellationException("Enough is enough"))
}

suspend fun selector(
    channel1: ReceiveChannel<String>,
    channel2: ReceiveChannel<String>
): String = select<String> {
    // onReceive clause in select fails when the channel is closed
    channel1.onReceive { it: String ->
        "b -> '$it'"
    }
    channel2.onReceiveCatching { it: ChannelResult<String> ->
        val value = it.getOrNull()
        if (value != null) {
            "a -> '$value'"
        } else {
            "Channel 'a' is closed"
        }
    }
}

fun CoroutineScope.prodForSelect(
    channel: SendChannel<String>,
    msg: String,
    delayDuration: Long
) = launch {
    while (true) {
        delay(delayDuration)
        channel.send(msg)
    }
}

fun runSelector() = runBlocking {
    val channel1 = Channel<String>()
    val channel2 = Channel<String>()
    prodForSelect(channel1, "HELLO", 100)
    prodForSelect(channel2, "hello", 50)

    val runningSelect1 = launch {
        while (isActive) {
            println(selector(channel1, channel2))
        }
    }

    val runningSelect2 = launch {
        while (isActive) {
            println(selector(channel2, channel1))
        }
    }

    delay(300)
    // there will be a throw
    channel1.close()
    channel2.close()
}

fun main() {
    getPrimes(20, " ")
}