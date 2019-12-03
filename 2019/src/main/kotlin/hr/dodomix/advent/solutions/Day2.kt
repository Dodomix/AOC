package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.runProgram

class Day2 {
    fun dayDirectory() = "day2"

    fun part1(input: List<String>): Int =
        runProgram(constructMemory(input)).getValue(0)

    fun part2(input: List<String>): Int {
        for (i in 0..100) {
            for (j in 0..100) {
                val indexedProgram = constructMemory(input)
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
