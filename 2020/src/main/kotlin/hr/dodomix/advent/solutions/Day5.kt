package hr.dodomix.advent.solutions

class Day5 {
    fun dayDirectory() = "day5"

    fun part1(input: List<String>): Int {
        return calculateIds(input).maxOrNull() ?: -1
    }

    fun part2(input: List<String>): Int {
        val ids = calculateIds(input).toHashSet()
        val seatNumbers = 0..(ids.maxOrNull() ?: -1)
        return seatNumbers.find { seat ->
            !ids.contains(seat) && ids.contains(seat - 1) && ids.contains(seat + 1)
        } ?: -1
    }

    private fun calculateIds(seats: List<String>): List<Int> {
        return seats.map { line ->
            val row = line.take(7).fold(Pair(0, 127)) { (start, end), letter ->
                val mid = (start + end) / 2
                if (letter == 'F') {
                    Pair(start, mid)
                } else {
                    Pair(mid + 1, end)
                }
            }.first
            val column = line.takeLast(3).fold(Pair(0, 7)) { (start, end), letter ->
                val mid = (start + end) / 2
                if (letter == 'L') {
                    Pair(start, mid)
                } else {
                    Pair(mid + 1, end)
                }
            }.first
            row * 8 + column
        }
    }
}
