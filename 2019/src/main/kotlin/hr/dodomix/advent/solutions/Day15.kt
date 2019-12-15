package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf
import kotlin.random.Random.Default.nextInt

class Day15 {

    fun dayDirectory() = "day15"

    fun part1(input: List<String>): Int {
        val grid = constructGrid(input)
        return bfs(grid, Pair(0, 0))
    }

    fun part2(input: List<String>): Int {
        val grid = constructGrid(input)
        grid[Pair(-12, -12)] = 1
        return bfs(grid, Pair(-12, -12))
    }

    private fun bfs(grid: MutableMap<Pair<Int, Int>, Int>, position: Pair<Int, Int>): Int {
        val queue = mutableListOf(Pair(position, 0))
        val finishedStates = mutableSetOf<Pair<Int, Int>>()
        var maxValue = 0
        while (true) {
            if (queue.isEmpty()) {
                return maxValue
            }
            val nextPosition = queue.removeAt(0)
            val nextPositionPosition = nextPosition.first
            if (grid.getValue(nextPositionPosition) == 1) {
                maxValue = nextPosition.second
            }
            finishedStates.add(nextPosition.first)
            when (grid.getValue(nextPosition.first)) {
                2 -> return nextPosition.second
                1 -> queue.addAll(listOf(
                    Pair(nextPositionPosition.first + 1, nextPositionPosition.second),
                    Pair(nextPositionPosition.first - 1, nextPositionPosition.second),
                    Pair(nextPositionPosition.first, nextPositionPosition.second - 1),
                    Pair(nextPositionPosition.first, nextPositionPosition.second + 1)
                ).filter {
                    !finishedStates.contains(it)
                }.map { Pair(it, nextPosition.second + 1) })
            }
        }
    }

    private fun constructGrid(input: List<String>): MutableMap<Pair<Int, Int>, Int> {
        val grid = mutableMapOf<Pair<Int, Int>, Int>().withDefault { -1 }
        var position = Pair(0, 0)
        grid[position] = 1
        for (j in 0 until 200) {
            var memory = constructMemory(input)
            position = Pair(0, 0)
            for (i in 0 until 10000) {
                val move = when {
                    grid.getValue(Pair(position.first, position.second - 1)) == -1 -> 1
                    grid.getValue(Pair(position.first, position.second + 1)) == -1 -> 2
                    grid.getValue(Pair(position.first - 1, position.second)) == -1 -> 3
                    grid.getValue(Pair(position.first + 1, position.second)) == -1 -> 4
                    else -> nextInt(1, 5)
                }
                val processorInput = listOf(valueOf(move.toLong()))
                val output = mutableListOf<BigInteger>()
                memory = runProgram(memory, input = processorInput, output = output, waitForInput = true)
                val outputValue = output[0]
                val nextPosition = when (move) {
                    1 -> Pair(position.first, position.second - 1)
                    2 -> Pair(position.first, position.second + 1)
                    3 -> Pair(position.first - 1, position.second)
                    4 -> Pair(position.first + 1, position.second)
                    else -> throw RuntimeException("Invalid move order")
                }
                grid.putIfAbsent(nextPosition, outputValue.toInt())
                if (outputValue != ZERO) {
                    position = nextPosition
                }
            }
        }
        return grid
    }

    private fun printGrid(grid: MutableMap<Pair<Int, Int>, Int>) {
        for (y in grid.keys.minBy { it.second }!!.second..grid.keys.maxBy { it.second }!!.second) {
            for (x in grid.keys.minBy { it.first }!!.first..grid.keys.maxBy { it.first }!!.first) {
                if (x == 0 && y == 0) {
                    print("S")
                } else {
                    when (grid.getValue(Pair(x, y))) {
                        -1 -> print('?')
                        0 -> print('#')
                        1 -> print('.')
                        2 -> print('O')
                    }
                }
            }
            println()
        }
    }
}
