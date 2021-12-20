package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Location

class Day20 {
    fun dayDirectory() = "day20"

    fun part1(input: List<String>): Int {
        val imageEnhancement = input[0]
        val image = parseImage(input.drop(2))
        return (0 until 2).fold(image) { currentImage, _ -> enhance(currentImage, imageEnhancement) }.count { it.value == '#' }
    }

    fun part2(input: List<String>): Int {
        val imageEnhancement = input[0]
        val image = parseImage(input.drop(2))
        return (0 until 50).fold(image) { currentImage, _ -> enhance(currentImage, imageEnhancement) }.count { it.value == '#' }
    }

    private fun parseImage(input: List<String>): Map<Location, Char> = input.foldIndexed<String, Map<Location, Char>>(emptyMap()) { row, partialResult, line ->
        line.foldIndexed(partialResult) { column, result, c ->
            result + Pair(Location(row, column), c)
        }
    }.withDefault { '.' }

    private fun enhance(image: Map<Location, Char>, imageEnhancement: String): Map<Location, Char> {
        val defaultValue = if (image.getValue(Location(image.minOf { it.key.row } - 2, 0)) == '#') {
            imageEnhancement.last()
        } else {
            imageEnhancement.first()
        }
        val resultMap = mutableMapOf<Location, Char>().withDefault { defaultValue }
        (image.minOf { it.key.row } - 1..image.maxOf { it.key.row } + 1).forEach { row ->
            (image.minOf { it.key.column } - 1..image.maxOf { it.key.column } + 1).forEach { column ->
                resultMap[Location(row, column)] = imageEnhancement[getBinary(image, row, column).toInt(2)]
            }
        }
        return resultMap
    }

    private fun getBinary(image: Map<Location, Char>, row: Int, column: Int) =
        ("${image.getValue(Location(row - 1, column - 1))}${image.getValue(Location(row - 1, column))}" +
                "${image.getValue(Location(row - 1, column + 1))}${image.getValue(Location(row, column - 1))}" +
                "${image.getValue(Location(row, column))}${image.getValue(Location(row, column + 1))}" +
                "${image.getValue(Location(row + 1, column - 1))}${image.getValue(Location(row + 1, column))}" +
                "${image.getValue(Location(row + 1, column + 1))}")
            .map { c -> if (c == '#') '1' else '0' }.joinToString("")
}
