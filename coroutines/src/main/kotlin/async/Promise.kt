package async

import Item
import Post
import Token
import java.util.concurrent.CompletableFuture


fun promiseToken(): CompletableFuture<Token> {
    return CompletableFuture.supplyAsync {
        Thread.sleep(500)
        Token()
    }
}

fun promisePost(token: Token, item: Item): CompletableFuture<Post> {
    return CompletableFuture.supplyAsync {
        Thread.sleep(500)
        Post()
    }
}

fun promiseProcessedPost(post: Post): CompletableFuture<Post> {
    return CompletableFuture.supplyAsync {
        Thread.sleep(500)
        post
    }
}

fun promiseToPostItem(item: Item) {
    promiseToken()
        .thenCompose { token -> promisePost(token, item) }
        .thenAccept { post -> promiseProcessedPost(post) }
}