package de.jub.kotlin.bsts

import kotlin.random.Random

internal const val TEST_ITERATIONS = 3
internal const val TEST_SET_SIZE = 100

internal fun getSetOfRandomValues(size: Int = TEST_SET_SIZE) = IntArray(size) { Random.nextInt() }.toSet()