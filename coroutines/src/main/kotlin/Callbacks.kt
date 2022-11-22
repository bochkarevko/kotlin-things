import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

suspend fun downloadContent(location: Location): Content {
    randomLengthSuspend()
    return Content()
}

suspend fun processContent(content: Content) {
    randomLengthSuspend()
}