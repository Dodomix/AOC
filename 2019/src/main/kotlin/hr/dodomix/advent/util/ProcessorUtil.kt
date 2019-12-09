package hr.dodomix.advent.util

import java.math.BigInteger
import java.math.BigInteger.*

object ProcessorUtil {
    fun constructMemory(input: List<String>): MutableMap<BigInteger, BigInteger> =
        input[0]
            .split(",")
            .map { BigInteger(it) }
            .foldRightIndexed(mutableMapOf()) { index, s, acc ->
                acc[valueOf(index.toLong())] = s
                acc
            }

    fun runProgram(
        memory: MutableMap<BigInteger, BigInteger>,
        input: List<BigInteger> = emptyList(),
        output: MutableList<BigInteger> = mutableListOf(),
        waitForInput: Boolean = false
    ): MutableMap<BigInteger, BigInteger> {
        var operationPointer = memory.getOrDefault(valueOf(-1), ZERO)
        var inputIndex = 0
        var relativeBase = ZERO
        while (true) {
            val value = memory.getOrDefault(operationPointer, ZERO).toInt()
            val modes = (value / 100).toString()
            when (value % 100) {
                1 -> {
                    operate3(memory, modes, operationPointer, relativeBase) { value1, value2 -> value1 + value2 }
                    operationPointer += valueOf(4)
                }
                2 -> {
                    operate3(memory, modes, operationPointer, relativeBase) { value1, value2 -> value1 * value2 }
                    operationPointer += valueOf(4)
                }
                3 -> {
                    println("Please input a value:")
                    if (waitForInput && inputIndex == input.size) {
                        memory[valueOf(-1)] = valueOf(operationPointer.toLong())
                        return memory
                    }
                    val valueRead = input.getOrElse(inputIndex++) { BigInteger(readLine()!!) }
                    val currentModes = processModes(modes, 1)
                    memory[getPosition(currentModes[0], read1(memory, operationPointer), relativeBase)] = valueRead
                    operationPointer += valueOf(2)
                }
                4 -> {
                    val currentModes = processModes(modes, 1)
                    val result = readValue(memory, currentModes[0], read1(memory, operationPointer), relativeBase)
                    output.add(result)
                    println(result)
                    operationPointer += valueOf(2)
                }
                5 -> {
                    operationPointer = jumpIf(memory, modes, operationPointer, relativeBase) { it != ZERO }
                }
                6 -> {
                    operationPointer = jumpIf(memory, modes, operationPointer, relativeBase) { it == ZERO }
                }
                7 -> {
                    operate3(
                        memory,
                        modes,
                        operationPointer,
                        relativeBase
                    ) { value1, value2 -> if (value1 < value2) ONE else ZERO }
                    operationPointer += valueOf(4)
                }
                8 -> {
                    operate3(
                        memory,
                        modes,
                        operationPointer,
                        relativeBase
                    ) { value1, value2 -> if (value1 == value2) ONE else ZERO }
                    operationPointer += valueOf(4)
                }
                9 -> {
                    val currentModes = processModes(modes, 1)
                    relativeBase +=
                        readValue(memory, currentModes[0], read1(memory, operationPointer), relativeBase)
                    operationPointer += valueOf(2)
                }
                99 -> {
                    memory[valueOf(-1)] = valueOf(-1) // denotes program exited fully
                    return memory
                }
                else -> {
                    throw RuntimeException("Invalid code ${value % 100} at $operationPointer")
                }
            }
        }
    }

    fun hasProgramFinished(memory: MutableMap<BigInteger, BigInteger>) = memory[valueOf(-1)] == valueOf(-1)

    private fun processModes(modes: String, valueCount: Int): String {
        var newModes = modes
        for (i in 0 until valueCount) {
            if (modes.length <= i) {
                newModes = "0$newModes"
            }
        }
        return newModes.reversed()
    }

    private fun read1(memory: MutableMap<BigInteger, BigInteger>, currentIndex: BigInteger) =
        memory.getOrDefault(currentIndex + valueOf(1), ZERO)

    private fun read2(memory: MutableMap<BigInteger, BigInteger>, currentIndex: BigInteger) =
        Pair(
            memory.getOrDefault(currentIndex + valueOf(1), ZERO),
            memory.getOrDefault(currentIndex + valueOf(2), ZERO)
        )

    private fun read3(memory: MutableMap<BigInteger, BigInteger>, currentIndex: BigInteger) =
        Triple(
            memory.getOrDefault(currentIndex + valueOf(1), ZERO),
            memory.getOrDefault(currentIndex + valueOf(2), ZERO),
            memory.getOrDefault(currentIndex + valueOf(3), ZERO)
        )

    private fun readValue(
        memory: MutableMap<BigInteger, BigInteger>,
        mode: Char,
        value: BigInteger,
        relativeBase: BigInteger
    ): BigInteger = when (mode) {
        '0' -> memory.getOrDefault(value, ZERO)
        '1' -> value
        else -> memory.getOrDefault(relativeBase + value, ZERO)
    }

    private fun operate3(
        memory: MutableMap<BigInteger, BigInteger>,
        modes: String,
        operationPointer: BigInteger,
        relativeBase: BigInteger,
        operation: (firstValue: BigInteger, secondValue: BigInteger) -> BigInteger
    ) {
        val currentModes = processModes(modes, 3)
        val values = read3(memory, operationPointer)
        memory[getPosition(currentModes[2], values.third, relativeBase)] = operation(
            readValue(memory, currentModes[0], values.first, relativeBase),
            readValue(memory, currentModes[1], values.second, relativeBase)
        )
    }

    private fun jumpIf(
        memory: MutableMap<BigInteger, BigInteger>,
        modes: String,
        operationPointer: BigInteger,
        relativeBase: BigInteger,
        condition: (firstValue: BigInteger) -> Boolean
    ): BigInteger {
        val currentModes = processModes(modes, 3)
        val values = read2(memory, operationPointer)
        val firstValue = readValue(memory, currentModes[0], values.first, relativeBase)
        return if (condition(firstValue)) {
            readValue(memory, currentModes[1], values.second, relativeBase)
        } else {
            operationPointer + valueOf(3)
        }
    }

    private fun getPosition(mode: Char, value: BigInteger, relativeBase: BigInteger): BigInteger = when (mode) {
        '0' -> {
            value
        }
        '2' -> {
            relativeBase + value
        }
        else -> {
            throw RuntimeException("Invalid mode $mode")
        }
    }
}
