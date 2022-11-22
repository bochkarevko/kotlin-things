package async

import Item
import Post
import Token

object _Blocking { // this object is here to avoid name clashes

    fun preparePost(): Token {
        // makes a request and consequently blocks the main thread
        Thread.sleep(500)
        return Token()
    }

    fun submitPost(token: Token, item: Item): Post {
        Thread.sleep(500)
        return Post()
    }

    fun processPost(post: Post) {
        Thread.sleep(500)
    }

    fun postItem(item: Item) { // 1500 ms of blocks
        val token = preparePost()
        val post = submitPost(token, item)
        processPost(post)
    }
}