package async

import Item
import Post
import Token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

fun CoroutineScope.preparePostAsync(): Deferred<Token> =
    async<Token> { Token() }

fun CoroutineScope.submitPostAsync(token: Token, item: Item): Deferred<Post> =
    async<Post> { Post() }

suspend fun postItemAsyncAwaitNonIdiomatic(item: Item) {
    coroutineScope {
        val token = preparePostAsync().await()
        val post = submitPostAsync(token, item).await()
        processPost(post)
    }
}

suspend fun postItemAsyncAwaitIdiomatic(item: Item) {
    coroutineScope {
        val deferredToken = async { preparePost() }
        // some work
        val token = deferredToken.await()
        val deferredPost = async { submitPost(token, item) }
        // more work
        val post = deferredPost.await()
        processPost(post)
    }
}