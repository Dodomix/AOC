package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.*


class Day25 {
    fun dayDirectory() = "day25"

    fun part1(input: List<String>): BigInteger {
        val cardPublicKey = input[0].toBigInteger()
        val cardLoopSize = calculateLoopSize(cardPublicKey)
        val doorPublicKey = input[1].toBigInteger()
        return transformValue(doorPublicKey, cardLoopSize)
    }

    fun part2(input: List<String>): BigInteger {
        return ZERO
    }

    private fun runLoop(subjectNumber: BigInteger, value: BigInteger) = (subjectNumber * value) % MODULO

    private fun calculateLoopSize(value: BigInteger): Int {
        generateSequence(0) { it + 1 }.fold(ONE) { currentValue, loopCount ->
            if (currentValue == value) {
                return loopCount
            }
            runLoop(INITIAL_SUBJECT, currentValue)
        }
        return -1
    }

    private fun transformValue(value: BigInteger, loopSize: Int) =
        (0 until loopSize).fold(ONE) { currentValue, _ -> runLoop(value, currentValue) }

    companion object {
        private val MODULO = valueOf(20201227)
        private val INITIAL_SUBJECT = valueOf(7)
    }
}
