package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf

class Day2 {
    fun dayDirectory() = "day2"

    fun part1(input: List<String>): Int =
        runProgram(constructMemory(input)).getValue(ZERO).toInt()

    fun part2(input: List<String>): Int {
        for (i in 0..100) {
            for (j in 0..100) {
                val indexedProgram = constructMemory(input)
                indexedProgram[valueOf(1)] = valueOf(i.toLong())
                indexedProgram[valueOf(2)] = valueOf(j.toLong())
                runProgram(indexedProgram)
                if (indexedProgram[ZERO] == valueOf(19690720)) {
                    return 100 * i + j
                }
            }
        }
        return 0
    }
}
