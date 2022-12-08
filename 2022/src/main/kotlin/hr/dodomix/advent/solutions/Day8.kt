package hr.dodomix.advent.solutions

class Day8 {
    fun dayDirectory() = "day8"

    fun part1(input: List<String>): Int {
        val (grid, lastCoordinate) = extractGrid(input)
        val lastIndex = lastCoordinate.row - 1
        return (0..lastIndex).fold(0) { count, row ->
            (0..lastIndex).fold(count) { currentCount, column ->
                val height = grid.getValue(Coordinate(row, column))
                if ((0 until column).all { grid.getValue(Coordinate(row, it)) < height } ||
                    (column + 1..lastIndex).all { grid.getValue(Coordinate(row, it)) < height } ||
                    (0 until row).all { grid.getValue(Coordinate(it, column)) < height } ||
                    (row + 1..lastIndex).all { grid.getValue(Coordinate(it, column)) < height }
                ) {
                    currentCount + 1
                } else {
                    currentCount
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val (grid, lastCoordinate) = extractGrid(input)
        val lastIndex = lastCoordinate.row - 1
        return (0..lastIndex).maxOf { row ->
            (0..lastIndex).maxOf { column ->
                val height = grid.getValue(Coordinate(row, column))
                calculateVisibleTrees(grid, (0 until column).reversed(), height) { Coordinate(row, it) } *
                        calculateVisibleTrees(grid, (column + 1..lastIndex), height) { Coordinate(row, it) } *
                        calculateVisibleTrees(grid, (0 until row).reversed(), height) { Coordinate(it, column) } *
                        calculateVisibleTrees(grid, (row + 1..lastIndex), height) { Coordinate(it, column) }
            }
        }
    }

    private fun calculateVisibleTrees(
        grid: Map<Coordinate, Int>,
        indices: IntProgression,
        height: Int,
        coordinateConstructor: (Int) -> Coordinate
    ): Int {
        val lowerTrees = indices.takeWhile { grid.getValue(coordinateConstructor(it)) < height }.count()
        return if (lowerTrees == indices.count()) lowerTrees
        else lowerTrees + 1
    }

    private fun extractGrid(input: List<String>) = input
        .fold(Pair(mapOf<Coordinate, Int>(), Coordinate(0, 0))) { (currentGrid, coordinate), line ->
            val (currentGridLine, coordinateLine) = line.fold(
                Pair(
                    currentGrid,
                    coordinate
                )
            ) { (currentGridLine, coordinateLine), c ->
                Pair(
                    currentGridLine + Pair(coordinateLine, c.digitToInt()),
                    Coordinate(coordinateLine.row, coordinateLine.column + 1)
                )
            }
            Pair(currentGridLine, Coordinate(coordinateLine.row + 1, 0))
        }

    private data class Coordinate(val row: Int, val column: Int)
}
