import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

suspend fun downloadContent(location: Location): Content {
    randomLengthSuspend()
    return Content()
}

suspend fun processContent(content: Content) {
    randomLengthSuspend()
}

suspend fun processReference(reference: Reference) {
    val location = reference.resolveLocation()
    val content = downloadContent(location)
    processContent(content)
}

fun CoroutineScope.processReferences(refs: List<Reference>) {
    for (ref in refs) {
        val location = ref.resolveLocation() // what if it throws?
        launch {  // child of CoroutineScope [this]
            val content = downloadContent(location)
            processContent(content)
        }
    }
}