package hr.dodomix.advent.util

import java.math.BigInteger
import java.math.BigInteger.valueOf

object ProcessorUtil {
    private val ZERO = BigInteger.ZERO
    private val NEGATIVE_ONE = valueOf(-1)

    fun constructMemory(input: List<String>): List<Pair<String, List<BigInteger>>> =
        input
            .map { line ->
                line.split(" ").let {
                    Pair(it[0], it.drop(1).map { value -> value.toBigInteger() })
                }
            }

    fun runProgram(
        memory: List<Pair<String, List<BigInteger>>>,
        detectInfiniteLoop: Boolean = false,
        debug: Boolean = false
    ): RunResult {
        generateSequence(0) { it + 1 }
            .fold(OperationContext(0, ZERO, emptySet())) { (operationPointer, accumulator, previousPointers), _ ->
                if (operationPointer >= memory.size) {
                    if (debug) {
                        println("Finished execution at pointer $operationPointer")
                    }
                    return RunResult(operationPointer, accumulator)
                } else if (detectInfiniteLoop && previousPointers.contains(operationPointer)) {
                    if (debug) {
                        println("Detected infinite loop at pointer $operationPointer")
                    }
                    return RunResult(operationPointer, accumulator)
                }

                val (operation, values) = memory[operationPointer]

                if (debug) {
                    println("operation=$operation and values=$values")
                }

                when (operation) {
                    "acc" -> OperationContext(
                        operationPointer + 1,
                        accumulator + values[0],
                        previousPointers + operationPointer
                    )
                    "jmp" -> OperationContext(
                        operationPointer + values[0].toInt(),
                        accumulator,
                        previousPointers + operationPointer
                    )
                    "nop" -> OperationContext(
                        operationPointer + 1,
                        accumulator,
                        previousPointers + operationPointer
                    )
                    else -> OperationContext(
                        operationPointer,
                        accumulator,
                        previousPointers + operationPointer
                    )
                }
            }
        return RunResult(-1, NEGATIVE_ONE)
    }

    internal class OperationContext(
        private val operationPointer: Int,
        private val accumulator: BigInteger,
        private val previousPointers: Set<Int>
    ) {
        operator fun component1(): Int {
            return operationPointer
        }

        operator fun component2(): BigInteger {
            return accumulator
        }

        operator fun component3(): Set<Int> {
            return previousPointers
        }
    }

    class RunResult(
        val operationPointer: Int,
        val accumulator: BigInteger
    ) {
        operator fun component1(): Int {
            return operationPointer
        }

        operator fun component2(): BigInteger {
            return accumulator
        }
    }
}
