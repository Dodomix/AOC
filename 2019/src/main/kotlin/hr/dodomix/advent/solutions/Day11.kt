package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.hasProgramFinished
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger

class Day11 {

    fun dayDirectory() = "day11"

    fun part1(input: List<String>): Int {
        val memory = constructMemory(input)
        val grid = mutableMapOf<Pair<Int, Int>, Int>().withDefault { 0 }
        var position = Pair(0, 0)
        val coloredPositions = mutableSetOf<Pair<Int, Int>>()
        var direction = 0
        while (!hasProgramFinished(memory)) {
            val output = mutableListOf<BigInteger>()
            runProgram(
                memory,
                input = listOf(BigInteger.valueOf(grid.getValue(position).toLong())),
                output = output,
                waitForInput = true
            )
            val newColor = output[0].toInt()
            if (newColor != grid.getValue(position)) {
                grid[position] = newColor
                coloredPositions.add(position)
            }
            val directionChange = output.get(1).toInt()
            if (directionChange == 0) {
                direction--
                direction += 4
                direction %= 4
            } else {
                direction++
                direction += 4
                direction %= 4
            }
            position = when (direction) {
                0 -> Pair(position.first - 1, position.second)
                1 -> Pair(position.first, position.second + 1)
                2 -> Pair(position.first + 1, position.second)
                3 -> Pair(position.first, position.second - 1)
                else -> throw RuntimeException("Invalid direction")
            }
        }
        return coloredPositions.size
    }

    fun part2(input: List<String>): Int {
        val memory = constructMemory(input)
        val grid = mutableMapOf(Pair(Pair(0, 0), 1)).withDefault { 0 }
        var position = Pair(0, 0)
        var direction = 0
        while (!hasProgramFinished(memory)) {
            val output = mutableListOf<BigInteger>()
            runProgram(
                memory,
                input = listOf(BigInteger.valueOf(grid.getValue(position).toLong())),
                output = output,
                waitForInput = true
            )
            grid[position] = output[0].toInt()
            val directionChange = output.get(1).toInt()
            if (directionChange == 0) {
                direction--
                direction += 4
                direction %= 4
            } else {
                direction++
                direction += 4
                direction %= 4
            }
            position = when (direction) {
                0 -> Pair(position.first - 1, position.second)
                1 -> Pair(position.first, position.second + 1)
                2 -> Pair(position.first + 1, position.second)
                3 -> Pair(position.first, position.second - 1)
                else -> throw RuntimeException("Invalid direction")
            }
        }

        for (row in grid.keys.map { it.first }.min()!!..grid.keys.map { it.first }.max()!!) {
            for (column in grid.keys.map { it.second }.min()!!..grid.keys.map { it.second }.max()!!) {
                print(if (grid.getValue(Pair(row, column)) == 1) '#' else ' ')
            }
            println()
        }

        return 0
    }
}
