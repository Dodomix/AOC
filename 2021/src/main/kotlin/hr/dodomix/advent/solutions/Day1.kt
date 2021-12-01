package hr.dodomix.advent.solutions

class Day1 {
    fun dayDirectory() = "day1"

    fun part1(input: List<String>): Int {
        return countIncreases(input.map { it.toInt() })
    }

    fun part2(input: List<String>): Int {
        val inputAsInteger = input.map { it.toInt() }
        return countIncreases(inputAsInteger.zip(inputAsInteger.drop(1)).zip(inputAsInteger.drop(2)).map {
            it.first.first + it.first.second + it.second
        })
    }

    private fun countIncreases(input: List<Int>): Int = input.zipWithNext().count { it.first < it.second }
}
