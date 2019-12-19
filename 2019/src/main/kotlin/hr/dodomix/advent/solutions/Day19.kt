package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger
import java.math.BigInteger.valueOf

class Day19 {

    fun dayDirectory() = "day19"

    fun part1(input: List<String>): Int {
        var counter = 0
        for (i in 0 until 50) {
            for (j in 0 until 50) {
                val inputs = listOf(i, j).map { valueOf(it.toLong()) }
                val output = mutableListOf<BigInteger>()
                runProgram(constructMemory(input), input = inputs, output = output)
                if (output[0] == valueOf(1)) {
                    counter++
                }
            }
            println()
        }
        return counter
    }

    fun part2(input: List<String>): Int {
        for (i in 0 until 10000) {
            var inBeam = false
            for (j in i until 10000) {
                val inputs = listOf(i, j).map { valueOf(it.toLong()) }
                val output = mutableListOf<BigInteger>()
                runProgram(constructMemory(input), input = inputs, output = output)
                if (output[0] == valueOf(1)) {
                    inBeam = true
                } else if (inBeam) {
                    break
                }
                for (k in 1..100) {
                    if (k == 100) {
                        return 10000 * i + j
                    }
                    val inputsI = listOf(i + k, j).map { valueOf(it.toLong()) }
                    val outputI = mutableListOf<BigInteger>()
                    runProgram(constructMemory(input), input = inputsI, output = outputI)
                    if (outputI[0] != valueOf(1)) {
                        break
                    }

                    val inputsJ = listOf(i, j + k).map { valueOf(it.toLong()) }
                    val outputJ = mutableListOf<BigInteger>()
                    runProgram(constructMemory(input), input = inputsJ, output = outputJ)
                    if (outputJ[0] != valueOf(1)) {
                        break
                    }

                    val inputsD = listOf(i + 99, j + k).map { valueOf(it.toLong()) }
                    val outputD = mutableListOf<BigInteger>()
                    runProgram(constructMemory(input), input = inputsD, output = outputD)
                    if (outputD[0] != valueOf(1)) {
                        break
                    }
                }
            }
        }
        return -1
    }
}
