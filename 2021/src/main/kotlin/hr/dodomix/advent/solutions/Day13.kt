package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Location

class Day13 {
    fun dayDirectory() = "day13"

    fun part1(input: List<String>): Int {
        val (dotList, folds) = parseInput(input)
        val (foldAxis, foldLineString) = folds[0].split("=")
        val foldLine = foldLineString.toInt()
        return dotList.map { location ->
            if (foldAxis == "x") {
                if (location.column > foldLine) {
                    Location(location.row, 2 * foldLine - location.column)
                } else {
                    location
                }
            } else {
                if (location.row > foldLine) {
                    Location(2 * foldLine - location.row, location.column)
                } else {
                    location
                }
            }
        }.toSet().size
    }

    fun part2(input: List<String>): String {
        val (dotList, folds) = parseInput(input)
        val finalDotList = folds.fold(dotList) { currentDotList, fold ->
            val (foldAxis, foldLineString) = fold.split("=")
            val foldLine = foldLineString.toInt()
            currentDotList.map { location ->
                if (foldAxis == "x") {
                    if (location.column > foldLine) {
                        Location(location.row, 2 * foldLine - location.column)
                    } else {
                        location
                    }
                } else {
                    if (location.row > foldLine) {
                        Location(2 * foldLine - location.row, location.column)
                    } else {
                        location
                    }
                }
            }.toSet()
        }

        val result = "\n" + (0..finalDotList.maxOf { it.row }).fold("") { currentString, row ->
            (0..finalDotList.maxOf { it.column }).fold(currentString) { resultString, column ->
                if (finalDotList.contains(Location(row, column))) {
                    "$resultString#"
                } else {
                    "$resultString."
                }
            } + "\n"
        }
        return result
    }

    private fun parseInput(input: List<String>) = input.fold(Pair(emptySet<Location>(), emptyList<String>())) { (currentDotList, currentFolds), line ->
        if (line.isEmpty()) {
            Pair(currentDotList, currentFolds)
        } else if (line.startsWith("fold along")) {
            Pair(currentDotList, currentFolds + line.takeLastWhile { it != ' ' })
        } else {
            val (column, row) = line.split(",").map { it.toInt() }
            Pair(currentDotList + Location(row, column), currentFolds)
        }
    }

}
