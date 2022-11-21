package de.jub.kotlin.bsts

class RecBST(val value: Int, var left: RecBST? = null, var right: RecBST? = null) : IntBST {
    override var size: Int = 1
    override fun getMin(): Int = left?.getMin() ?: value
    override fun getMax(): Int = right?.getMax() ?: value

    private fun update() {
        size = 1
        size += left?.size ?: 0
        size += right?.size ?: 0
    }

    override fun insert(newValue: Int): Boolean {
        val leftChild = left
        val rightChild = right
        return when {
            newValue < value && leftChild != null -> {
                val res = leftChild.insert(newValue)
                update()
                res
            }
            newValue < value -> {
                left = RecBST(newValue)
                update()
                true
            }
            value < newValue && rightChild != null -> {
                val res = rightChild.insert(newValue)
                update()
                res
            }
            value < newValue -> {
                right = RecBST(newValue)
                update()
                true
            }
            else -> false
        }
    }

    override fun contains(someValue: Int): Boolean =
        when {
            someValue == value -> true
            someValue < value -> left?.contains(someValue) ?: false
            else -> right?.contains(someValue) ?: false
        }
}
