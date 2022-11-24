import kotlinx.coroutines.*

suspend fun preparePost(): Token =
    withContext(Dispatchers.IO) {
        println("Long fetch")
        delay(1500)
        Token()
    }

suspend fun submitPost(token: Token, item: Item): Post =
    withContext(Dispatchers.IO) {
        println("Fast submit")
        delay(500)
        Post()
    }

suspend fun processPost(post: Post) =
    withContext(Dispatchers.Default) {
        println("Processing")
        delay(1000)
    }

suspend fun postItem(item: Item) {
    val token = preparePost()
    val post = submitPost(token, item)
    processPost(post)
}

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    val uiThread = newSingleThreadContext("UI") // Main
    val viewScope = CoroutineScope(SupervisorJob() + uiThread)

    viewScope.launch {
        launch {
            try {
                while (isActive) {
                    println("UI thread is not blocked.")
                    delay(300)
                }
            } finally {
                println("UI (view) is destroyed")
            }
        }
        println("Post something")
        postItem(Item())
        println("Posted")
    }

    runBlocking {
        delay(4321)
        viewScope.cancel()
    }
}