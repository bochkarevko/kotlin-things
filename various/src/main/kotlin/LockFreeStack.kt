import java.lang.Thread.sleep
import java.util.Optional
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

class LockFreeStack<T> {
    private sealed interface Node<T>
    private class EmptyNode<T> : Node<T>
    private data class ValueNode<T>(
        var next: Node<T>,
        val value: T
    ) : Node<T>

    private val head: AtomicReference<Node<T>> = AtomicReference(EmptyNode())

    fun lockFreePush(newValue: T) {
        val newNode = ValueNode(head.get(), newValue)
        do {
            newNode.next = head.get()
        } while (!head.compareAndSet(newNode.next, newNode))
    }

    fun lockFreePop(): Optional<out T?> {
        while (true) {
            val current = head.get()
            when {
                current is EmptyNode -> return Optional.empty()
                current is ValueNode<T> && head.compareAndSet(current, current.next) -> return Optional.ofNullable(
                    current.value
                )
            }
        }
    }

    fun unsafePrint() {
        var current = head.get()
        while (current is ValueNode<T>) {
            print("${current.value} ")
            current = current.next
        }
        println()
    }
}


fun main() {
    val stack = LockFreeStack<Int>()
    // 30 values pushed
    val pushers = (0..9).map { i -> Runnable { repeat(3) { j -> stack.lockFreePush(i * 3 + j) } } }
    // 15 values popped
    val poppers = (1..5).map { i ->
        Runnable {
            repeat(3) {
                val popped = stack.lockFreePop()
                println("[$i] Popped value: $popped")
            }
        }
    }
    (pushers + poppers).shuffled().forEach { runnable -> thread { runnable.run() } }
    sleep(1000)
    stack.unsafePrint()
}