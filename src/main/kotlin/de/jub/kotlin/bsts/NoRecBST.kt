package de.jub.kotlin.bsts

import java.util.*

class NoRecBST(val value: Int, var left: NoRecBST? = null, var right: NoRecBST? = null) : IntBST {
    override var size: Int = 1
    override fun getMin(): Int {
        var node = this
        var leftNode = node.left
        while(leftNode != null) {
            node = leftNode
            leftNode = node.left
        }
        return node.value
    }
    override fun getMax(): Int {
        var node = this
        var rightNode = node.right
        while(rightNode != null) {
            node = rightNode
            rightNode = node.right
        }
        return node.value
    }

    private fun update() {
        size = 1
        size += left?.size ?: 0
        size += right?.size ?: 0
    }

    override fun insert(newValue: Int): Boolean {
        val pathFromRoot = Stack<NoRecBST>()
        pathFromRoot.add(this)
        while(true) {
            val currNode = pathFromRoot.peek()
            val leftChild = currNode.left
            val rightChild = currNode.right
            when {
                newValue < currNode.value && leftChild != null -> {
                    pathFromRoot.add(leftChild)
                }

                newValue < currNode.value -> {
                    currNode.left = NoRecBST(newValue)
                    pathFromRoot.add(currNode.left)
                    break
                }

                currNode.value < newValue && rightChild != null -> {
                    pathFromRoot.add(rightChild)
                }

                currNode.value < newValue -> {
                    currNode.right = NoRecBST(newValue)
                    pathFromRoot.add(currNode.right)
                    break
                }

                else -> return false
            }
        }

        while(pathFromRoot.isNotEmpty()) {
            val node = pathFromRoot.pop()
            node.update()
        }

        return true
    }

    override fun contains(someValue: Int): Boolean {
        var currNode: NoRecBST? = this
        while (currNode != null) {
            currNode = when {
                someValue < currNode.value  -> currNode.left
                currNode.value < someValue -> currNode.right
                else -> return true
            }
        }
        return false
    }
}
