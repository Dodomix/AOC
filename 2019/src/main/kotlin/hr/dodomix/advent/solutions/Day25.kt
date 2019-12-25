package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.hasProgramFinished
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger
import java.math.BigInteger.valueOf

class Day25 {

    fun dayDirectory() = "day25"

    fun part1(input: List<String>): Any {
        var memory = constructMemory(input)
        var inputs = listOf<BigInteger>()
        while (!hasProgramFinished(memory)) {
            val output = mutableListOf<BigInteger>()
            memory = runProgram(memory, output = output, input = inputs, waitForInput = true)
            println(output.map { it.toInt().toChar() }.joinToString(""))
            inputs = readLine()!!.map { valueOf(it.toLong()) } + valueOf('\n'.toLong())
        }
        return 0
    }

    fun part2(input: List<String>): Any {
        return 0
    }
}
