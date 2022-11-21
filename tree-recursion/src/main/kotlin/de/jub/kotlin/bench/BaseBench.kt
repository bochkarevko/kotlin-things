package de.jub.kotlin.bench

import de.jub.kotlin.bsts.IntBST

interface BaseBench {
    val bst: IntBST

    fun setUp() {
        bst.populate()
    }

    fun contains(): Boolean {
        var nuff = false
        for (i in 1..100) {
            nuff = nuff || bst.contains(i)
        }
        return nuff
    }

    fun insert(): Boolean {
        var norep = true
        for (i in 1..100) {
            norep = norep && bst.insert(i)
        }
        return norep
    }

    fun run() {
        println(bst.size)
        bst.run()
        println(bst.size)
    }
}
