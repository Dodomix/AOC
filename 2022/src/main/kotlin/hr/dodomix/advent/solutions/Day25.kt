package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.*

class Day25 {

    fun dayDirectory() = "day25"

    fun part1(input: List<String>): String =
        numberToSnafu(input.sumOf { snafuToNumber(it) })

    private fun snafuToNumber(value: String) = value.reversed().mapIndexed { index, c ->
        valueOf(5).pow(index) * numbersInSnafu.getValue(c)
    }.sumOf { it }

    private fun numberToSnafu(value: BigInteger): String {
        val maxPlace = calculateMaxPlaceInSnafu(value)
        var currentValue = ZERO
        return (0..maxPlace).reversed().map { place ->
            val exponentialMultiplier = valueOf(5).pow(place)
            val numberEntry = numbersInSnafu.minBy { (_, number) ->
                (value - (currentValue + number * exponentialMultiplier)).abs()
            }
            currentValue += numberEntry.value * exponentialMultiplier
            numberEntry.key
        }.joinToString("")
    }

    private fun calculateMaxPlaceInSnafu(value: BigInteger): Int {
        var maxPlace = 0
        while (true) {
            val exponentialMultiplier = valueOf(5).pow(maxPlace)
            val maxValue = TWO * exponentialMultiplier
            if ((maxValue - value).abs() <= exponentialMultiplier) {
                return maxPlace
            }
            maxPlace++
        }
    }

    companion object {
        val numbersInSnafu = mapOf(
            '2' to TWO,
            '1' to ONE,
            '0' to ZERO,
            '-' to -ONE,
            '=' to -TWO
        )
    }
}