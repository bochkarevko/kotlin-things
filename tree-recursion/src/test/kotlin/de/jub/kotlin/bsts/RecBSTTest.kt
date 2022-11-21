package de.jub.kotlin.bsts

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest

class RecBSTTest {

    @RepeatedTest(TEST_ITERATIONS)
    fun getMinMax() {
        val randomValues = getSetOfRandomValues()
        val bst = RecBST(randomValues.first())
        randomValues.forEach { bst.insert(it) }
        assertEquals(randomValues.min(), bst.getMin())
        assertEquals(randomValues.max(), bst.getMax())
    }

    @RepeatedTest(TEST_ITERATIONS)
    fun insert() {
        val randomValues = getSetOfRandomValues()
        val bst = RecBST(randomValues.first())
        assertEquals(1, bst.size)
        randomValues.drop(1).forEachIndexed { index, value ->
            assertEquals(index + 1, bst.size)
            bst.insert(value)
            assertEquals(index + 2, bst.size)
        }
        assertEquals(randomValues.min(), bst.getMin())
        assertEquals(randomValues.max(), bst.getMax())
    }

    @RepeatedTest(TEST_ITERATIONS)
    fun contains() {
        val randomValues = getSetOfRandomValues()
        val bst = RecBST(randomValues.first())
        assertTrue(bst.contains(randomValues.first()))
        randomValues.drop(1).forEach { value ->
            assertFalse(bst.contains(value))
            bst.insert(value)
            assertTrue(bst.contains(value))
        }
    }
}
