package hr.dodomix.advent.util

import java.math.BigInteger
import java.math.BigInteger.valueOf

object ProcessorUtil {
    private val ZERO = BigInteger.ZERO
    private val ONE = BigInteger.ONE
    private val TWO = valueOf(2)
    private val THREE = valueOf(3)
    private val FOUR = valueOf(4)
    private val OPERATION_POINTER_LOCATION = valueOf(Long.MIN_VALUE) - ONE
    private val RELATIVE_POINTER_LOCATION = valueOf(Long.MAX_VALUE) + ONE
    private val EXIT_REASON_LOCATION = valueOf(Long.MAX_VALUE) + TWO

    fun constructMemory(input: List<String>): MutableMap<BigInteger, BigInteger> =
        input[0]
            .split(",")
            .map { BigInteger(it) }
            .foldIndexed(mutableMapOf<BigInteger, BigInteger>().withDefault { ZERO }) { index, memory, s ->
                memory[valueOf(index.toLong())] = s
                memory
            }

    fun runProgram(
        memory: MutableMap<BigInteger, BigInteger>,
        input: List<BigInteger> = emptyList(),
        output: MutableList<BigInteger> = mutableListOf(),
        waitForInput: Boolean = false,
        stepByStep: Boolean = false,
        debug: Boolean = false
    ): MutableMap<BigInteger, BigInteger> {
        var operationPointer = memory.getValue(OPERATION_POINTER_LOCATION)
        var inputIndex = 0
        var relativeBase = memory.getValue(RELATIVE_POINTER_LOCATION)
        while (true) {
            val value = memory.getValue(operationPointer).toInt()
            val modes = (value / 100).toString()
            when (value % 100) {
                1 -> {
                    operate3(memory, modes, operationPointer, relativeBase) { value1, value2 -> value1 + value2 }
                    operationPointer += FOUR
                }
                2 -> {
                    operate3(memory, modes, operationPointer, relativeBase) { value1, value2 -> value1 * value2 }
                    operationPointer += FOUR
                }
                3 -> {
                    if (debug) {
                        println("Please input a value:")
                    }
                    if (waitForInput && inputIndex == input.size) {
                        memory[OPERATION_POINTER_LOCATION] = valueOf(operationPointer.toLong())
                        memory[RELATIVE_POINTER_LOCATION] = relativeBase
                        memory[EXIT_REASON_LOCATION] = ONE
                        return memory
                    }
                    val valueRead = input.getOrElse(inputIndex++) { BigInteger(readLine()!!) }
                    val currentModes = processModes(modes, 1)
                    memory[getPosition(currentModes[0], read1(memory, operationPointer), relativeBase)] = valueRead
                    operationPointer += TWO
                }
                4 -> {
                    val currentModes = processModes(modes, 1)
                    val result = readValue(memory, currentModes[0], read1(memory, operationPointer), relativeBase)
                    output.add(result)
                    if (debug) {
                        println(result)
                    }
                    operationPointer += TWO
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
                    operationPointer += FOUR
                }
                8 -> {
                    operate3(
                        memory,
                        modes,
                        operationPointer,
                        relativeBase
                    ) { value1, value2 -> if (value1 == value2) ONE else ZERO }
                    operationPointer += FOUR
                }
                9 -> {
                    val currentModes = processModes(modes, 1)
                    relativeBase +=
                        readValue(memory, currentModes[0], read1(memory, operationPointer), relativeBase)
                    operationPointer += TWO
                }
                99 -> {
                    memory[OPERATION_POINTER_LOCATION] = OPERATION_POINTER_LOCATION // denotes program exited fully
                    return memory
                }
                else -> {
                    throw RuntimeException("Invalid code ${value % 100} at $operationPointer")
                }
            }
            if (stepByStep) {
                memory[OPERATION_POINTER_LOCATION] = valueOf(operationPointer.toLong())
                memory[RELATIVE_POINTER_LOCATION] = relativeBase
                memory[EXIT_REASON_LOCATION] = TWO
                return memory
            }
        }
    }

    fun hasProgramFinished(memory: MutableMap<BigInteger, BigInteger>) =
        memory[OPERATION_POINTER_LOCATION] == OPERATION_POINTER_LOCATION

    fun isWaitingForInput(memory: MutableMap<BigInteger, BigInteger>) = memory[EXIT_REASON_LOCATION] == ONE

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
        memory.getValue(currentIndex + ONE)

    private fun read2(memory: MutableMap<BigInteger, BigInteger>, currentIndex: BigInteger) =
        Pair(
            memory.getValue(currentIndex + ONE),
            memory.getValue(currentIndex + TWO)
        )

    private fun read3(memory: MutableMap<BigInteger, BigInteger>, currentIndex: BigInteger) =
        Triple(
            memory.getValue(currentIndex + ONE),
            memory.getValue(currentIndex + TWO),
            memory.getValue(currentIndex + THREE)
        )

    private fun readValue(
        memory: MutableMap<BigInteger, BigInteger>,
        mode: Char,
        value: BigInteger,
        relativeBase: BigInteger
    ): BigInteger = when (mode) {
        '0' -> memory.getValue(value)
        '1' -> value
        else -> memory.getValue(relativeBase + value)
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
        val currentModes = processModes(modes, 2)
        val values = read2(memory, operationPointer)
        val firstValue = readValue(memory, currentModes[0], values.first, relativeBase)
        return if (condition(firstValue)) {
            readValue(memory, currentModes[1], values.second, relativeBase)
        } else {
            operationPointer + THREE
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
