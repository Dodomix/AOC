package hr.dodomix.advent.solutions

class Day4 {
    fun dayDirectory() = "day4"

    fun part1(input: List<String>): Int {
        var validPasswordCount = 0
        for (i in 402328..864247) {
            val stringified = i.toString()
            var valid = true
            var hasDouble = false
            stringified.forEachIndexed { index, digit ->
                if (index != stringified.length - 1) {
                    val next = stringified[index + 1]
                    if (digit == next) {
                        hasDouble = true
                    }
                    if (digit > next) {
                        valid = false
                    }
                }
            }
            if (valid && hasDouble) validPasswordCount++
        }
        return validPasswordCount
    }

    fun part2(input: List<String>): Int {
        var validPasswordCount = 0
        for (i in 402328..864247) {
            val stringified = i.toString()
            var valid = true
            var hasDouble = false
            var inDoubles = false
            stringified.forEachIndexed { index, digit ->
                if (index != stringified.length - 1) {
                    val next = stringified[index + 1]
                    if (digit == next) {
                        if (!hasDouble || inDoubles) {
                            hasDouble = !inDoubles
                            inDoubles = true
                        }
                    } else {
                        inDoubles = false
                    }
                    if (digit > next) {
                        valid = false
                    }
                }
            }
            if (valid && hasDouble) {
                validPasswordCount++
            }
        }
        return validPasswordCount
    }
}
