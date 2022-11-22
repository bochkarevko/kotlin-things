import kotlinx.coroutines.*
import java.nio.ByteBuffer
import java.nio.channels.AsynchronousFileChannel
import java.nio.channels.CompletionHandler
import java.nio.file.Paths
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun AsynchronousFileChannel.aRead(buf: ByteBuffer): Int =
    suspendCoroutine { cont ->
        read(buf, 0L, Unit, object : CompletionHandler<Int, Unit> {
            override fun completed(bytesRead: Int, attachment: Unit) {
                cont.resume(bytesRead)
            }

            override fun failed(exception: Throwable, attachment: Unit) {
                cont.resumeWithException(exception)
            }
        })
    }

internal val sep
    get() = System.lineSeparator()

fun suspendableRead() = runBlocking {
    coroutineScope {
        val readJob = launch(Dispatchers.IO) {
            val fileName = "coroutines/src/main/kotlin/OwnCoroutine.kt"
            println("Asynchronously loading file \"$fileName\" ...")
            val channel = AsynchronousFileChannel.open(Paths.get(fileName))
            try {
                val buf = ByteBuffer.allocate(4096)
                val bytesRead = channel.aRead(buf)
                println("Read $bytesRead bytes starting with \"${String(buf.array().copyOf(10))}\"")
            } finally {
                channel.close()
            }
        }
//        delay(1000)
//        readJob.cancel()
    }

}