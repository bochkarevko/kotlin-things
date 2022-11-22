import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

class Item
class Token
class Post
class Location
class Content
class Reference

fun Reference.resolveLocation() = Location()

suspend fun longSuspendingWrong() {
    while(true) {
        delay(1000)
    }
}

suspend fun longSuspending() {
    while(coroutineContext.isActive) {
        delay(1000)
    }
}

suspend fun randomLengthSuspend(min: Long = 0, max: Long = 1000) = delay(Random.nextLong(min, max))