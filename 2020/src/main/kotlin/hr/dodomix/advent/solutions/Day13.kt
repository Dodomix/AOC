package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO
import kotlin.math.ceil

class Day13 {
    fun dayDirectory() = "day13"

    fun part1(input: List<String>): Int {
        val departureTime = input[0].toInt()
        val bus = input[1].split(",")
            .filter { it != "x" }
            .map { it.toInt() }
            .minByOrNull { ceil(departureTime / it.toDouble()) * it } ?: -1
        return (ceil(departureTime / bus.toDouble()).toInt() * bus - departureTime) * bus

    }

    fun part2(input: List<String>): BigInteger {
        val buses = input[1].split(",")
        return buses.foldIndexed(Pair(ONE, ONE)) { index, (initialValue, increment), bus ->
            if (bus == "x") {
                Pair(initialValue, increment)
            } else {
                val parsedBus = bus.toBigInteger()
                var value = initialValue
                while ((value + index.toBigInteger()) % parsedBus != ZERO) {
                    value += increment
                }
                Pair(value, increment * parsedBus)
            }
        }.first
    }
}
