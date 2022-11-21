package de.jub.kotlin

var count = 0

fun <A1, A2, R> bind(func: (A1, A2) -> R, arg1: A1): (A2) -> R =
    { arg2: A2 -> func(arg1, arg2) }.also { count++ }

fun <T, S, R> composition(first: (T) -> S, then: (S) -> R) =
    { arg: T -> then(first(arg)) }.also { count++ }

fun <T, S, R> thenApply(init: T, first: (T) -> S, then: (S) -> R) =
    then(first(init)).also { count++ }

fun <R> cond(clause: Boolean, ifTrue: () -> R, ifFalse: () -> R) =
    (if (clause) ifTrue() else ifFalse()).also { count++ }

fun what() {
    val f1 = { x: Double, y: Double ->
        x + y
    }
    val f2 = { x: Double, y: Double ->
        y - x
    }
    val func = { x: Double, y: Double, z: Double ->
        cond(x > y,
            { thenApply(z, bind(f1, x), bind(f2, y)) },
            { composition(bind(f1, y), bind(f2, x)) }
        )
    }
    val res = func(3.14, 2.71, 1.57)
    print("$res, ")
    println(count)
}
