import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.processReferences(refs: List<Reference>) {
    for (ref in refs) {
        val location = ref.resolveLocation()
        launch {  // child of coroutineScope
            val content = downloadContent(location)
            processContent(content)
        }
    }
}