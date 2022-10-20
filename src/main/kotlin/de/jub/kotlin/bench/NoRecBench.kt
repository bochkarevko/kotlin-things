package de.jub.kotlin.bench

import de.jub.kotlin.bsts.NoRecBST
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 10)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.MILLISECONDS)
open class NoRecBench : BaseBench {
    override val bst = NoRecBST(M)

    @Setup
    override fun setUp() {
        super.setUp()
    }

    @Benchmark
    override fun contains(): Boolean {
        return super.contains()
    }

    @Benchmark
    override fun insert(): Boolean {
        return super.insert()
    }

    @Benchmark
    override fun run() {
        super.run()
    }
}
