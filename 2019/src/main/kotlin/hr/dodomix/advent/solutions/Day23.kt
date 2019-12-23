package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.isWaitingForInput
import hr.dodomix.advent.util.ProcessorUtil.runProgram
import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf

class Day23 {

    fun dayDirectory() = "day23"

    fun part1(input: List<String>): BigInteger {
        return runComputers(input, false)
    }

    fun part2(input: List<String>): BigInteger {
        return runComputers(input, true)
    }

    private fun runComputers(input: List<String>, useNat: Boolean): BigInteger {
        var inputs = (0 until 50).map { valueOf(it.toLong()) }
            .map {
                val output = mutableListOf<BigInteger>()
                val memory = runProgram(constructMemory(input), input = listOf(), output = output, waitForInput = true)
                Triple(
                    it,
                    runProgram(memory, input = listOf(it), output = output, stepByStep = true),
                    Pair(null as BigInteger?, null as Pair<BigInteger, BigInteger?>?)
                )
            }
        val messages =
            mapOf<BigInteger, MutableList<Pair<BigInteger, BigInteger>>>()
                .toMutableMap()
                .withDefault { mutableListOf() }
        var nat = Pair(NEGATIVE_ONE, NEGATIVE_ONE)
        val natDeliveredY = mutableListOf<BigInteger>()
        var idle = 0
        while (true) {
            inputs = inputs.map { computer ->
                val location = computer.first
                val inProgress = computer.third
                val output = mutableListOf<BigInteger>()

                var memory = runProgram(
                    computer.second,
                    output = output,
                    input = listOf(),
                    stepByStep = true,
                    waitForInput = true
                )
                var nextValue = inProgress.first
                var inProgressPacket = inProgress.second
                if (isWaitingForInput(memory)) {
                    if (location == ZERO &&
                        inputs.map { it.third.first }.all { it == null || it == NEGATIVE_ONE } &&
                        inputs.map { it.third.second }.none { it != null } &&
                        messages.values.all { it.isEmpty() }
                    ) {
                        if (idle == 1000) {
                            messages.putIfAbsent(location, mutableListOf())
                            messages.getValue(location).add(nat)
                            if (natDeliveredY.isNotEmpty() && nat.second != null && natDeliveredY.last() == nat.second) {
                                return nat.second
                            }
                            natDeliveredY.add(nat.second)
                            idle = 0
                        } else {
                            idle++
                        }
                    } else if (location == ZERO) {
                        idle = 0
                    }
                    val computerInput = if (nextValue == null) {
                        val computerInput = messages.getValue(location).firstOrNull()
                        if (computerInput != null) {
                            messages.getValue(location).removeAt(0)
                        }
                        nextValue = computerInput?.second ?: NEGATIVE_ONE
                        computerInput?.first ?: NEGATIVE_ONE
                    } else {
                        val previousNextValue = nextValue
                        nextValue = null
                        previousNextValue
                    }
                    memory = runProgram(
                        memory,
                        output = output,
                        input = listOf(computerInput),
                        stepByStep = true
                    )
                }
                if (output.isNotEmpty()) {
                    inProgressPacket = when {
                        inProgressPacket == null -> Pair(output.first(), null)
                        inProgressPacket.second == null -> Pair(inProgressPacket.first, output.first())
                        else -> {
                            if (inProgressPacket.first == NAT_LOCATION) {
                                if (useNat) {
                                    nat = Pair(inProgressPacket.second, output.first())
                                } else {
                                    return output.first()
                                }
                            } else {
                                if (!messages.containsKey(inProgressPacket.first)) {
                                    messages[inProgressPacket.first] = mutableListOf()
                                }
                                messages.getValue(inProgressPacket.first)
                                    .add(Pair(inProgressPacket.second!!, output.first()))
                            }
                            null
                        }
                    }
                }

                Triple(location, memory, Pair(nextValue, inProgressPacket))
            }
        }
    }

    companion object {
        private val NEGATIVE_ONE = valueOf(-1)
        private val NAT_LOCATION = valueOf(255)
    }
}
