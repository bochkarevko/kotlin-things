package async

import Item
import Post
import Token


fun preparePostAsync(callback: (Token) -> Unit) {
    // makes a request and consequently blocks the main thread
    Thread.sleep(500)
    val token = Token()
    callback(token)
}

fun submitPostAsync(token: Token, item: Item, callback: (Post) -> Unit) {
    Thread.sleep(500)
    val post = Post()
    callback(post)
}

fun processPostAsync(post: Post, callback: (Post) -> Unit) {
    Thread.sleep(500)
    callback(post)
}

fun postItemAsync(item: Item) {
    preparePostAsync { token ->
        submitPostAsync(token, item) { post ->
            processPostAsync(post) {
                Thread.sleep(500)
            }
        }
    }
}