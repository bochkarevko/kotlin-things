package de.jub.kotlin.bench

import de.jub.kotlin.bsts.IntBST

const val M = 5000

enum class BstOps {
    INS {
        override fun useBst(bst: IntBST) {
            bst.insert(oddNumbers.random())
        }
    },
    MIN {
        override fun useBst(bst: IntBST) {
            bst.getMin()
        }
    },
    MAX {
        override fun useBst(bst: IntBST) {
            bst.getMax()
        }
    },
    CON {
        override fun useBst(bst: IntBST) {
            bst.contains(oddNumbers.random())
        }
    };

    abstract fun useBst(bst: IntBST)

    protected val oddNumbers = List(M) { it * 2 + 1 }
}

fun IntBST.run() {
    repeat(5000) {
        val op = BstOps.values().random()
        op.useBst(this)
    }
}

fun IntBST.populate() {
    for (i in 2 until M step 2) {
        insert(M - i)
        insert(M + i)
    }
}
