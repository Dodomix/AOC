package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf

class Day17 {

    fun dayDirectory() = "day17"

    fun part1(input: List<String>): Int {
        val output = mutableListOf<BigInteger>()
        runProgram(constructMemory(input), output = output)
        val grid = mutableMapOf<Pair<Int, Int>, Char>()
        var maxJ = 0
        var maxI = 0
        var row = 0
        var column = 0
        output.map { it.toInt().toChar() }.forEach {
            if (row > maxI) {
                maxI = row
            }
            if (column > maxJ) {
                maxJ = column
            }
            if (it == '\n') {
                row++
                column = 0
            } else {
                grid[Pair(row, column++)] = it
            }
        }
        var result = 0
        for (i in 0 until maxI) {
            for (j in 0 until maxJ) {
                if (grid[Pair(i, j)] == '#' &&
                    grid[Pair(i - 1, j)] == '#' &&
                    grid[Pair(i + 1, j)] == '#' &&
                    grid[Pair(i, j - 1)] == '#' &&
                    grid[Pair(i, j + 1)] == '#'
                ) {
                    result += i * j
                }
                print(grid[Pair(i, j)])
            }
            println()
        }
        return result
    }

    fun part2(input: List<String>): Any {
        val output = mutableListOf<BigInteger>()
        val memory = constructMemory(input)
        memory[ZERO] = valueOf(2)
        runProgram(
            memory,
            input = "A,A,B,C,A,C,A,B,C,B\nR,12,L,8,R,6\nR,12,L,6,R,6,R,8,R,6\nL,8,R,8,R,6,R,12\nn\n"
                .toCharArray()
                .map {
                    valueOf(it.toLong())
                },
            output = output
        )
        return output.last().toInt()
    }
}
