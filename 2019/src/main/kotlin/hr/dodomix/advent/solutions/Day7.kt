package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.hasProgramFinished
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import hr.dodomix.advent.util.Util.permute
import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf
import kotlin.math.max


class Day7 {
    fun dayDirectory() = "day7"

    data class Amplifier(val memory: MutableMap<BigInteger, BigInteger>, val output: MutableList<BigInteger>)

    fun part1(input: List<String>): Int {
        val permutations = permute(listOf(0, 1, 2, 3, 4))
        return permutations.fold(0) { currentValue, permutation ->
            max(permutation.fold(0) { value, phase ->
                val result = mutableListOf<BigInteger>()
                runProgram(constructMemory(input), listOf(valueOf(phase.toLong()), valueOf(value.toLong())), result)
                result[0].toInt()
            }, currentValue)
        }
    }

    fun part2(input: List<String>): Int {
        val permutations = permute(listOf(5, 6, 7, 8, 9))
        return permutations.fold(0) { currentValue, permutation ->
            var amplifierIndex = 1
            val amplifiers = permutation.map { phase ->
                val amplifierOutput = mutableListOf<BigInteger>()
                Amplifier(
                    runProgram(
                        constructMemory(input),
                        input = listOf(phase.toBigInteger()),
                        output = amplifierOutput,
                        waitForInput = true
                    ), amplifierOutput
                )
            }
            runAmplifier(amplifiers[0], listOf(ZERO))
            while (!hasProgramFinished(amplifiers[4].memory)) {
                val previousAmplifier = amplifiers[(amplifierIndex - 1 + amplifiers.size) % amplifiers.size]
                val amplifier = amplifiers[(amplifierIndex++) % amplifiers.size]
                amplifier.output.clear() // clear previous output
                runAmplifier(amplifier, previousAmplifier.output)
            }
            max(amplifiers[4].output.last().toInt(), currentValue)
        }
    }

    private fun runAmplifier(amplifier: Amplifier, input: List<BigInteger>) =
        runProgram(
            amplifier.memory,
            input = input,
            output = amplifier.output,
            waitForInput = true
        )
}
