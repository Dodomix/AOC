package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class Day10 {
    fun dayDirectory() = "day10"

    fun part1(input: List<String>): Int {
        val values = input.map { it.toInt() }.sorted()
        var differenceOneCount = 0
        var differenceThreeCount = 1
        if (values[0] == 1) {
            differenceOneCount++
        } else if (values[0] == 3) {
            differenceThreeCount++
        }
        for (i in values.dropLast(1).indices) {
            val difference = values[i + 1] - values[i]
            if (difference == 1) {
                differenceOneCount++
            } else if (difference == 3) {
                differenceThreeCount++
            }
        }
        return differenceOneCount * differenceThreeCount
    }

    fun part2(input: List<String>): BigInteger {
        val values = input.map { it.toInt() }.sorted()
        return countArrangements(0, values)
    }

    private val memory: MutableMap<Int, BigInteger> = HashMap()

    private fun countArrangements(currentValue: Int, restOfTheList: List<Int>): BigInteger {
        if (memory.containsKey(currentValue)) {
            return memory.getValue(currentValue)
        }
        if (restOfTheList.isEmpty()) {
            return ONE
        }
        return (countArrangements(restOfTheList[0], restOfTheList.drop(1)) +
            if (restOfTheList.size >= 2 && restOfTheList[1] - currentValue <= 3)
                countArrangements(restOfTheList[1], restOfTheList.drop(2))
            else {
                ZERO
            } +
            if (restOfTheList.size >= 3 && restOfTheList[2] - currentValue <= 3)
                countArrangements(restOfTheList[2], restOfTheList.drop(3))
            else {
                ZERO
            })
            .also {
                memory[currentValue] = it
            }
    }
}
