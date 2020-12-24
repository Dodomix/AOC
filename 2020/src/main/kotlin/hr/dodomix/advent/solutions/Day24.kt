package hr.dodomix.advent.solutions


class Day24 {
    fun dayDirectory() = "day24"

    fun part1(input: List<String>): Int {
        return constructTiles(input).values.count { it }
    }

    fun part2(input: List<String>): Int {
        return (0 until 100).fold(constructTiles(input)) { currentTiles, i ->
            (currentTiles.minOf { it.key.first } - 2..currentTiles.maxOf { it.key.first } + 2).flatMap { x ->
                (currentTiles.minOf { it.key.second } - 1..currentTiles.maxOf { it.key.second } + 1).map { y ->
                    val value = currentTiles.getOrDefault(Pair(x, y), false)
                    val blackCount = listOf(
                        currentTiles.getOrDefault(Pair(x - 2, y), false),
                        currentTiles.getOrDefault(Pair(x + 2, y), false),
                        currentTiles.getOrDefault(Pair(x - 1, y - 1), false),
                        currentTiles.getOrDefault(Pair(x - 1, y + 1), false),
                        currentTiles.getOrDefault(Pair(x + 1, y - 1), false),
                        currentTiles.getOrDefault(Pair(x + 1, y + 1), false),
                    ).count { it }
                    Pair(
                        Pair(x, y), when (value) {
                            true -> when {
                                blackCount == 0 -> false
                                blackCount > 2 -> false
                                else -> true
                            }
                            false -> when (blackCount) {
                                2 -> true
                                else -> false
                            }
                        }
                    )
                }
            }.toMap()
        }.values.count { it }
    }

    private fun constructTiles(input: List<String>) =
        input.fold(mapOf<Pair<Int, Int>, Boolean>()) { tiles, line ->
            val position = line.fold(Pair(Pair(0, 0), "")) { (position, direction), letter ->
                when (direction + letter) {
                    "s" -> Pair(position, letter.toString())
                    "n" -> Pair(position, letter.toString())
                    "w" -> Pair(Pair(position.first - 2, position.second), "")
                    "e" -> Pair(Pair(position.first + 2, position.second), "")
                    "sw" -> Pair(Pair(position.first - 1, position.second - 1), "")
                    "se" -> Pair(Pair(position.first + 1, position.second - 1), "")
                    "nw" -> Pair(Pair(position.first - 1, position.second + 1), "")
                    "ne" -> Pair(Pair(position.first + 1, position.second + 1), "")
                    else -> throw RuntimeException("Invalid input")
                }
            }.first
            tiles + Pair(position, !tiles.getOrDefault(position, false))
        }
}
