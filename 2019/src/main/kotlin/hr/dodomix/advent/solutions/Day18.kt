package hr.dodomix.advent.solutions

class Day18 {

    fun dayDirectory() = "day18"

    fun part1(input: List<String>): Any {
        val grid = mutableMapOf<Pair<Int, Int>, Char>()
        var row = 0
        var column = 0
        input.forEach { line ->
            line.forEach { grid[Pair(row, column++)] = it }
            row++
            column = 0
        }
        val initialPosition = grid.filterValues { it == '@' }.keys.random()
        grid[initialPosition] = '.'
        return collectKeys(grid, initialPosition)
    }

    private fun collectKeys(grid: MutableMap<Pair<Int, Int>, Char>, initialPosition: Pair<Int, Int>): Int {
        val keys = emptySet<Char>()
        val positions = sortedSetOf(
            Comparator { a, b -> a.third - b.third },
            Triple(initialPosition, keys, 0)
        ).toMutableSet()
        positions.first()
        val seenPositions = mutableMapOf(Pair(Pair(initialPosition, keys), 0))
        val totalKeys = grid.values.filter { it.isLowerCase() }.count()
        println(grid.values.filter { it.isLowerCase() })

        while (positions.isNotEmpty()) {
            val element = positions.first()
            positions.remove(element)
            val position = element.first
            val positionKeys = element.second
            val distance = element.third
            seenPositions[Pair(position, positionKeys)] = distance
            val newPositions = grid[position]?.let {
                if (it.isUpperCase() && !positionKeys.contains(it.toLowerCase())) emptyList()
                else if (it == '#') emptyList()
                else if (it.isLowerCase()) {
                    val newKeys = positionKeys + it
                    if (positionKeys.size == totalKeys) return distance
                    else {
                        listOf(
                            Triple(Pair(position.first + 1, position.second), newKeys, distance + 1),
                            Triple(Pair(position.first - 1, position.second), newKeys, distance + 1),
                            Triple(Pair(position.first, position.second + 1), newKeys, distance + 1),
                            Triple(Pair(position.first, position.second - 1), newKeys, distance + 1)
                        )
                    }
                } else {
                    listOf(
                        Triple(Pair(position.first + 1, position.second), positionKeys, distance + 1),
                        Triple(Pair(position.first - 1, position.second), positionKeys, distance + 1),
                        Triple(Pair(position.first, position.second + 1), positionKeys, distance + 1),
                        Triple(Pair(position.first, position.second - 1), positionKeys, distance + 1)
                    )
                }
            } ?: emptyList()
            positions.addAll(newPositions.filter {
                seenPositions[Pair(it.first, it.second)]?.let { seenPositionDistance ->
                    seenPositionDistance >= it.third
                } ?: true
            }.filter {
                grid[it.first]?.let { gridValue ->
                    gridValue != '#'
                } ?: false
            })
        }
        return -1
    }

    fun part2(input: List<String>): Any {
        val grid = mutableMapOf<Pair<Int, Int>, Char>()
        var row = 0
        var column = 0
        input.forEach { line ->
            line.forEach { grid[Pair(row, column++)] = it }
            row++
            column = 0
        }
        val initialPositions = grid.filterValues { it == '@' }.keys
        initialPositions.forEach { grid[it] = '.' }
        return collectKeys(grid, initialPositions)
    }

    private fun collectKeys(grid: MutableMap<Pair<Int, Int>, Char>, initialPosition: Set<Pair<Int, Int>>): Int {
        val keys = emptySet<Char>()
        val positions = sortedSetOf(
            Comparator { a, b -> a.third - b.third },
            Triple(initialPosition, keys, 0)
        ).toMutableSet()
        positions.first()
        val seenPositions = mutableMapOf<Pair<Pair<Int, Int>, Set<Char>>, Int>()
        val totalKeys = grid.values.filter { it.isLowerCase() }.count()
        println(grid.values.filter { it.isLowerCase() })

        while (positions.isNotEmpty()) {
            val element = positions.first()
            positions.remove(element)
            val robotPositions = element.first
            val positionKeys = element.second
            val distance = element.third
            robotPositions.forEach {
                seenPositions[Pair(it, positionKeys)] = distance
            }
            val newPositions = robotPositions.map { position ->
                grid[position]?.let {
                    if (it.isUpperCase() && !positionKeys.contains(it.toLowerCase())) emptyList()
                    else if (it == '#') emptyList()
                    else if (it.isLowerCase()) {
                        val newKeys = positionKeys + it
                        if (positionKeys.size == totalKeys) return distance
                        else {
                            listOf(
                                Triple(Pair(position.first + 1, position.second), newKeys, distance + 1),
                                Triple(Pair(position.first - 1, position.second), newKeys, distance + 1),
                                Triple(Pair(position.first, position.second + 1), newKeys, distance + 1),
                                Triple(Pair(position.first, position.second - 1), newKeys, distance + 1)
                            )
                        }
                    } else {
                        listOf(
                            Triple(Pair(position.first + 1, position.second), positionKeys, distance + 1),
                            Triple(Pair(position.first - 1, position.second), positionKeys, distance + 1),
                            Triple(Pair(position.first, position.second + 1), positionKeys, distance + 1),
                            Triple(Pair(position.first, position.second - 1), positionKeys, distance + 1)
                        )
                    }
                } ?: emptyList()
            }.mapIndexed { index, newIndexPositions ->
                val allPositions = newIndexPositions.filter {
                    seenPositions[Pair(it.first, it.second)]?.let { seenPositionDistance ->
                        seenPositionDistance >= it.third
                    } ?: true
                }.filter {
                    grid[it.first]?.let { gridValue ->
                        gridValue != '#'
                    } ?: false
                }
                val otherPositions = robotPositions.filterIndexed { robotIndex, _ -> robotIndex != index }.toSet()
                allPositions.map { Triple(otherPositions + it.first, it.second, it.third) }
            }.flatten()
            positions.addAll(newPositions)
        }
        return -1
    }
}
