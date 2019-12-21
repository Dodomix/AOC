package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger

class Day21 {

    fun dayDirectory() = "day21"

    fun part1(input: List<String>): Any {
        val inputs = "NOT A T\nNOT B J\nOR T J\nNOT C T\nOR T J\nAND D J\nWALK\n"
            .toCharArray()
            .map {
                BigInteger.valueOf(it.toLong())
            }
        val output = mutableListOf<BigInteger>()
        runProgram(constructMemory(input), input = inputs, output = output)
        output.forEach { print(it.toInt().toChar()) }

        return output.last()
    }

    fun part2(input: List<String>): Any {
        val inputs = "NOT A T\nNOT B J\nOR T J\nNOT C T\nAND H T\nOR T J\nAND D J\nRUN\n"
            .toCharArray()
            .map {
                BigInteger.valueOf(it.toLong())
            }
        val output = mutableListOf<BigInteger>()
        runProgram(constructMemory(input), input = inputs, output = output)
        output.forEach { print(it.toInt().toChar()) }

        return output.last()
    }
}
