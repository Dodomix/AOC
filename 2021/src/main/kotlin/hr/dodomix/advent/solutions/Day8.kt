package hr.dodomix.advent.solutions

class Day8 {
    fun dayDirectory() = "day8"

    fun part1(input: List<String>): Int {
        return input.sumOf {
            it.split(" | ")[1].split(" ").count { segment -> segment.length in setOf(2, 3, 4, 7) }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (numbers, values) = line.split(" | ").map { it.split(" ").map { chars -> chars.toSet() } }
            val one = numbers.find { it.size == 2 }!!
            val seven = numbers.find { it.size == 3 }!!
            val four = numbers.find { it.size == 4 }!!
            val eight = numbers.find { it.size == 7 }!!
            val three = numbers.find { it.size == 5 && it.containsAll(one) }!!
            val nine = numbers.find { it.size == 6 && it.containsAll(one) && it.containsAll(four) }!!
            val zero = numbers.find { it.size == 6 && it.containsAll(one) && it != nine }!!
            val six = numbers.find { it.size == 6 && it != zero && it != nine }!!
            val five = numbers.find { it.size == 5 && six.containsAll(it) }!!
            val two = numbers.find { it.size == 5 && it != five && it != three }!!
            val digits = mapOf(
                Pair(zero, "0"),
                Pair(one, "1"),
                Pair(two, "2"),
                Pair(three, "3"),
                Pair(four, "4"),
                Pair(five, "5"),
                Pair(six, "6"),
                Pair(seven, "7"),
                Pair(eight, "8"),
                Pair(nine, "9")
            )

            values.joinToString(separator = "") { digits.getValue(it) }.toInt()
        }
    }
}
