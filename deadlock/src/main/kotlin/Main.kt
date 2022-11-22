import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class LockAndCond {
    val lock: Lock = ReentrantLock()
    val cond: Condition = lock.newCondition()
}

fun funny(l1: LockAndCond, l2: LockAndCond) =
    thread {                    // 0
        l1.lock.withLock {      // 1
            l1.cond.signalAll() // 2
            l2.lock.withLock {  // 3
                l2.cond.await() // 4
            }                   // 5
        }                       // 6
        println("${Thread.currentThread().name} is done!")
    }

/**
 * | Thread/State |       | 1/0 | 1/1 | 1/2 | 1/3 | 1/4 | 1/5 |
 * |:------------:|:-----:|:---:|:---:|:---:|:---:|:---:|:---:|
 * |              | Locks |     |  1  |  1  | 1,2 |  1  | 1,2 |
 * |      2/0     |       |  o  |  o  |  o  |  o  |  o  |  x  |
 * |      2/1     |   2   |  o  |  o  |  o  |  x  |  o  |  x  |
 * |      2/2     |   2   |  o  |  o  |  o  |  x  |  o  |  x  |
 * |      2/3     |  2,1  |  o  |  x  |  x  |  x  |  x  |  x  |
 * |      2/4     |   2   |  o  |  o  |  o  |  x  |  x  |  x  |
 * |      2/5     |  2,1  |  x  |  x  |  x  |  x  |  x  |  x  |
 */
fun main() {
    val o1 = LockAndCond()
    val o2 = LockAndCond()
    val t1 = funny(o1, o2)
    val t2 = funny(o2, o1)
}