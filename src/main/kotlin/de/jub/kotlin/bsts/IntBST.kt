package de.jub.kotlin.bsts

interface IntBST {
    val size: Int
    fun getMin(): Int
    fun getMax(): Int
    fun insert(newValue: Int): Boolean
    fun contains(someValue: Int): Boolean
}
