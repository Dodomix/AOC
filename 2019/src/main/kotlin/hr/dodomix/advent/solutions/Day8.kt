package hr.dodomix.advent.solutions

import java.lang.Character.getNumericValue

class Day8 {

    fun dayDirectory() = "day8"

    fun part1(input: List<String>): Int {
        val layers = constructLayers(input[0].toCharArray().map { getNumericValue(it) })

        return layers.map { layer ->
            Pair(
                layer.values.filter { it == 0 }.count(),
                layer.values.filter { it == 1 }.count() * layer.values.filter { it == 2 }.count()
            )
        }.minBy { it.first }?.second ?: -1
    }

    private fun constructLayers(pixels: List<Int>): List<Map<Pair<Int, Int>, Int>> =
        (0 until pixels.size / PIXELS_IN_LAYER).fold(listOf()) { resultList, count ->
            resultList + pixels
                .drop(count * PIXELS_IN_LAYER)
                .take(PIXELS_IN_LAYER)
                .fold(Pair(mapOf<Pair<Int, Int>, Int>(), Pair(0, 0))) { layerPoint, pixel ->
                    val point = layerPoint.second

                    Pair(
                        layerPoint.first + Pair(point, pixel),
                        Pair((point.first + 1) % 25, point.second + if (point.first != 24) 0 else 1)
                    )
                }.first
        }

    fun part2(input: List<String>): String {
        val layers = constructLayers(input[0].toCharArray().map { getNumericValue(it) })

        return (0 until ROWS).fold("") { rowString, row ->
            "$rowString\n" + (0 until COLUMNS).fold("") { columnString, column ->
                columnString + layers.fold("") { nextChar, layer ->
                    if (nextChar.isEmpty()) {
                        when (layer[Pair(column, row)]) {
                            0 -> "."
                            1 -> "|"
                            2 -> ""
                            else -> ""
                        }
                    } else {
                        nextChar
                    }
                }
            }
        }
    }

    companion object {
        private const val ROWS = 6
        private const val COLUMNS = 25
        private const val PIXELS_IN_LAYER = ROWS * COLUMNS
    }
}
