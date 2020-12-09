package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ZERO

class Day9 {
    fun dayDirectory() = "day9"

    fun part1(input: List<String>): BigInteger {
        val numbers = input.map { it.toBigInteger() }
        for (i in numbers.indices) {
            if (i > 25) {
                var foundNumbers = false
                for (j in (i - 25) until i) {
                    for (k in j until i) {
                        if (numbers[j] + numbers[k] == numbers[i]) {
                            foundNumbers = true
                            break
                        }
                    }
                }
                if (!foundNumbers) {
                    return numbers[i]
                }
            }
        }
        return ZERO
    }

    fun part2(input: List<String>): BigInteger {
        val expectedResult = part1(input)
        val numbers = input.map { it.toBigInteger() }
        numbers.forEachIndexed { i, _ ->
            numbers.subList(i, numbers.size)
                .foldIndexed(ZERO) { j, sum, value ->
                    if (sum > expectedResult) {
                        sum
                    } else {
                        if (sum + value == expectedResult) {
                            numbers.subList(i, i + j + 1).apply {
                                return minOrNull()!! + maxOrNull()!!
                            }
                        }
                        sum + value
                    }
                }
        }
        return ZERO
    }
}
