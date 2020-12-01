package hr.dodomix.advent.solutions

class Day1 {
    fun dayDirectory() = "day1"

    fun part1(input: List<String>): Int? {
        val expenses = input.map { it.toInt() }
        expenses.forEach { expense1 ->
            expenses.find { expense2 -> expense1 + expense2 == 2020 }
                ?.also { expense2 ->
                    return expense1 * expense2
                }
        }
        return null
    }

    fun part2(input: List<String>): Int? {
        val expenses = input.map { it.toInt() }
        expenses.forEach { expense1 ->
            expenses.forEach { expense2 ->
                expenses.find { expense3 -> expense1 + expense2 + expense3 == 2020 }
                    ?.also { expense3 ->
                        return expense1 * expense2 * expense3
                    }
            }
        }
        return null
    }
}
