package async

import kotlinx.coroutines.delay

import Item
import Post
import Token

suspend fun preparePost(): Token {
    delay(500)
    return Token()
}

suspend fun submitPost(token: Token, item: Item): Post {
    delay(500)
    return Post()
}

suspend fun processPost(post: Post) {
    delay(500)
}

suspend fun postItem(item: Item) {
    val token = preparePost()
    val post = submitPost(token, item)
    processPost(post)
}
