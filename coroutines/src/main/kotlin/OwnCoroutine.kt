import kotlinx.coroutines.*
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import java.nio.file.Paths
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

private val sep
    get() = System.lineSeparator()

suspend fun AsynchronousFileChannel.aRead(buf: ByteBuffer, pos: Int) =
    // Scheme: call-with-current-continuation; call/cc
    suspendCoroutine { cont ->
        // CompletionHandler ~ Continuation
        read(buf, pos.toLong(), Unit, object : CompletionHandler<Int, Unit> {
            override fun completed(bytesRead: Int, attachment: Unit) {
                cont.resume(bytesRead)
            }

            override fun failed(exception: Throwable, attachment: Unit) {
                cont.resumeWithException(exception)
            }
        })
    }

fun suspendableRead() = runBlocking {
    val readJob = launch(Dispatchers.IO) {
        val fileName = "coroutines/src/main/kotlin/OwnCoroutine.kt"
        println("Asynchronously loading file \"$fileName\" ...")
        val channel = AsynchronousFileChannel.open(Paths.get(fileName))
        val buf = ByteBuffer.allocate(64)
        var bytesRead = 0
        channel.use {// finally { channel.close() }
            while (isActive) {
                val lastRead = it.aRead(buf, bytesRead)
                Thread.sleep(100) // imitate long read
                if (lastRead <= 0) break
                bytesRead += lastRead
                println("Read $lastRead:$sep```$sep${String(buf.array().take(lastRead).toByteArray())}$sep```")
                buf.clear()
            }
        }
    }
    delay(500)
    readJob.cancel()
}

suspend fun cancellable(x: Int) =
    // Scheme: call-with-current-continuation; call/cc
    suspendCancellableCoroutine { cancellableCont ->
        cancellableCont.invokeOnCancellation { throwable: Throwable? ->
            // release resources and etc.
            println(throwable?.message ?: "no reason provided")
        }

        Thread.sleep(100)

        if (Random.nextDouble(1.0) > 0.5) {
            cancellableCont.cancel(Exception("bad luck"))
        } else {
            cancellableCont.resume(x * 2)
        }

    }

fun main() {
    // suspendableRead()

    runBlocking {
        val a = cancellable(23)
        val b = cancellable(a)
        val c = cancellable(b)
        println(c)
    }
}
