package hr.dodomix.advent.solutions

import java.lang.RuntimeException

class Day2 {
    fun dayDirectory() = "day2"

    fun part1(input: List<String>): Int =
        runProgram(constructInputMap(input)).getValue(0)

    private fun constructInputMap(input: List<String>): MutableMap<Int, Int> =
        input[0]
        .split(",")
        .map { it.toInt() }
        .foldRightIndexed(mutableMapOf()) { index, s, acc ->
            acc[index] = s
            acc
        }


    private fun runProgram(indexedProgram: MutableMap<Int, Int>): MutableMap<Int, Int> {
        var operationPointer = 0
        while (true) {
            when (indexedProgram.getValue(operationPointer)) {
                1 -> {
                    val indices = read3(indexedProgram, operationPointer)
                    indexedProgram[indices.third] =
                        indexedProgram.getValue(indices.first) +
                                indexedProgram.getValue(indices.second)
                    operationPointer += 4
                }
                2 -> {
                    val indices = read3(indexedProgram, operationPointer)
                    indexedProgram[indices.third] =
                        indexedProgram.getValue(indices.first) *
                                indexedProgram.getValue(indices.second)
                    operationPointer += 4
                }
                99 -> return indexedProgram
                else -> {
                    throw RuntimeException("Invalid code")
                }
            }
        }
    }

    private fun read3(indexedProgram: MutableMap<Int, Int>, currentIndex: Int) =
        Triple(indexedProgram.getValue(currentIndex + 1),
            indexedProgram.getValue(currentIndex + 2),
            indexedProgram.getValue(currentIndex + 3))

    fun part2(input: List<String>): Int {
        for (i in 0..100) {
            for (j in 0..100) {
                val indexedProgram = constructInputMap(input)
                indexedProgram[1] = i
                indexedProgram[2] = j
                runProgram(indexedProgram)
                if (indexedProgram[0] == 19690720) {
                    return 100 * i + j
                }
            }
        }
        return 0
    }
}