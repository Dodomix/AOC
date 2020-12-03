package hr.dodomix.advent.solutions

class Day3 {
    fun dayDirectory() = "day3"

    fun part1(input: List<String>): Int {
        val map = input.map { it.toList() }
        val totalColumns = map[0].size
        var column = 0
        var row = 0
        var trees = 0
        while (true) {
            column = (column + 3) % totalColumns
            row += 1
            if (row >= map.size) {
                return trees
            }
            if (map[row][column] == '#') {
                trees++
            }
        }
    }

    fun part2(input: List<String>): Int {
        val map = input.map { it.toList() }
        val totalColumns = map[0].size
        val slopes = listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))
        return slopes.map { slope ->
            var column = 0
            var row = 0
            var trees = 0
            while (true) {
                column = (column + slope.first) % totalColumns
                row += slope.second
                if (row >= map.size) {
                    break
                }
                if (map[row][column] == '#') {
                    trees++
                }
            }
            trees
        }.foldRight(1) { i, acc -> i * acc }
    }
}
