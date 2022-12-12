package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Processor

class Day10 {
    fun dayDirectory() = "day10"

    fun part1(input: List<String>): Int {
        val history = Processor().process(input)
        return history.multiplyValueWithKey(20) + history.multiplyValueWithKey(60) +
                history.multiplyValueWithKey(100) + history.multiplyValueWithKey(140) +
                history.multiplyValueWithKey(180) + history.multiplyValueWithKey(220)
    }

    fun part2(input: List<String>): Unit {
        val history = Processor().process(input)
        (0 until 6).forEach { row ->
            (0 until 40).forEach { column ->
                if (history.getValue(row * 40 + column) in listOf(column - 1, column, column + 1)) print("#")
                else print(".")
            }
            println()
        }
    }

    private fun Map<Int, Int>.multiplyValueWithKey(key: Int) = getValue(key - 1) * key
}
