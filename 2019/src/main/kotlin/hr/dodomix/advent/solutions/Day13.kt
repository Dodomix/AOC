package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf

class Day13 {

    fun dayDirectory() = "day13"

    fun part1(input: List<String>): Any {
        val output = mutableListOf<BigInteger>()
        val grid = mutableMapOf<Pair<BigInteger, BigInteger>, BigInteger>()
        runProgram(constructMemory(input), output = output)
        var i = 0
        while (i < output.size) {
            grid[Pair(output[i], output[i + 1])] = output[i + 2]
            i += 3
        }
        return grid.values.filter { it == valueOf(2) }.count()
    }

    fun part2(input: List<String>): Any {
        val output = mutableListOf<BigInteger>()
        val grid = mutableMapOf<Pair<BigInteger, BigInteger>, BigInteger>().withDefault { ZERO }
        val memory = constructMemory(input)
        memory[ZERO] = valueOf(2)
        val programInput = mutableListOf<BigInteger>()
        var score = 0
        while (true) {
            runProgram(memory, output = output, input = programInput, waitForInput = true)
            var ballXPosition = ZERO
            var paddleXPosition = ZERO
            var i = 0
            while (i < output.size) {
                if (output[i] == valueOf(-1) && output[i + 1] == valueOf(0)) {
                    score = output[i + 2].toInt()
                } else {
                    grid[Pair(output[i], output[i + 1])] = output[i + 2]
                    if (output[i + 2].toInt() == 4) {
                        ballXPosition = output[i]
                    } else if (output[i + 2].toInt() == 3) {
                        paddleXPosition = output[i]
                    }
                }
                i += 3
            }

            if (grid.values.filter { it == valueOf(2) }.count() == 0) {
                return score
            }

            output.clear()
            programInput.clear()

            when {
                ballXPosition > paddleXPosition -> programInput.add(valueOf(1))
                ballXPosition < paddleXPosition -> programInput.add(valueOf(-1))
                else -> programInput.add(ZERO)
            }
        }
    }

    private fun drawGrid(grid: MutableMap<Pair<BigInteger, BigInteger>, BigInteger>) {
        for (row in 0..grid.keys.map { it.second }.max()!!.toInt()) {
            for (column in 0..grid.keys.map { it.first }.max()!!.toInt()) {
                print(grid[Pair(valueOf(column.toLong()), valueOf(row.toLong()))])
            }
            println()
        }
    }
}
