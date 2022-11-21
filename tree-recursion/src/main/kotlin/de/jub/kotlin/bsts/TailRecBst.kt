package de.jub.kotlin.bsts

class TailRecBst(val value: Int, var left: TailRecBst? = null, var right: TailRecBst? = null) : IntBST {
    override var size: Int = 1
    override fun getMin(): Int {
        return mmin(this)
    }

    private tailrec fun mmin(node: TailRecBst): Int =
        if (node.left == null) node.value else mmin(node.left!!)

    override fun getMax(): Int {
        return mmax(this)
    }

    private tailrec fun mmax(node: TailRecBst): Int =
        if (node.right == null) node.value else mmax(node.right!!)

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
                left = TailRecBst(newValue)
                update()
                true
            }

            value < newValue && rightChild != null -> {
                val res = rightChild.insert(newValue)
                update()
                res
            }

            value < newValue -> {
                right = TailRecBst(newValue)
                update()
                true
            }

            else -> false
        }
    }

    override fun contains(someValue: Int): Boolean {
        return ccon(this, someValue)
    }

    private tailrec fun ccon(node: TailRecBst, someValue: Int): Boolean {
        if (someValue == node.value) {
            return true
        }
        val nextNode = (if (someValue < node.value) node.left else node.right) ?: return false
        return ccon(nextNode, someValue)
    }
}
