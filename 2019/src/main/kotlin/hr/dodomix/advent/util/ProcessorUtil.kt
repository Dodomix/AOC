package hr.dodomix.advent.util

object ProcessorUtil {
    fun constructMemory(input: List<String>): MutableMap<Int, Int> =
        input[0]
            .split(",")
            .map { it.toInt() }
            .foldRightIndexed(mutableMapOf()) { index, s, acc ->
                acc[index] = s
                acc
            }


    fun runProgram(
        memory: MutableMap<Int, Int>,
        input: List<Int> = emptyList(),
        output: MutableList<Int> = mutableListOf(),
        waitForInput: Boolean = false
    ): MutableMap<Int, Int> {
        var operationPointer = memory.getOrDefault(-1, 0)
        var inputIndex = 0
        while (true) {
            val value = memory.getValue(operationPointer)
            val modes = (value / 100).toString()
            when (value % 100) {
                1 -> {
                    operate3(memory, modes, operationPointer) { value1, value2 -> value1 + value2 }
                    operationPointer += 4
                }
                2 -> {
                    operate3(memory, modes, operationPointer) { value1, value2 -> value1 * value2 }
                    operationPointer += 4
                }
                3 -> {
                    println("Please input a value:")
                    if (waitForInput && inputIndex == input.size) {
                        memory[-1] = operationPointer
                        return memory
                    }
                    val readValue = input.getOrElse(inputIndex++) { readLine()!!.toInt() }
                    memory[read1(memory, operationPointer)] = readValue
                    operationPointer += 2
                }
                4 -> {
                    val currentModes = processModes(modes, 1)
                    val result = readValue(memory, currentModes[0], read1(memory, operationPointer))
                    output.add(result)
                    println(result)
                    operationPointer += 2
                }
                5 -> {
                    operationPointer = jumpIf(memory, modes, operationPointer) { it != 0 }
                }
                6 -> {
                    operationPointer = jumpIf(memory, modes, operationPointer) { it == 0 }
                }
                7 -> {
                    operate3(memory, modes, operationPointer) { value1, value2 -> if (value1 < value2) 1 else 0 }
                    operationPointer += 4
                }
                8 -> {
                    operate3(memory, modes, operationPointer) { value1, value2 -> if (value1 == value2) 1 else 0 }
                    operationPointer += 4
                }
                99 -> {
                    memory[-1] = -1 // denotes program exited fully
                    return memory
                }
                else -> {
                    throw RuntimeException("Invalid code ${value % 100} at $operationPointer")
                }
            }
        }
    }

    fun hasProgramFinished(memory: MutableMap<Int, Int>) = memory[-1] == -1

    private fun processModes(modes: String, valueCount: Int): String {
        var newModes = modes
        for (i in 0 until valueCount) {
            if (modes.length <= i) {
                newModes = "0$newModes"
            }
        }
        return newModes.reversed()
    }

    private fun read1(memory: MutableMap<Int, Int>, currentIndex: Int) =
        memory.getValue(currentIndex + 1)

    private fun read2(memory: MutableMap<Int, Int>, currentIndex: Int) =
        Pair(
            memory.getValue(currentIndex + 1),
            memory.getValue(currentIndex + 2)
        )

    private fun read3(memory: MutableMap<Int, Int>, currentIndex: Int) =
        Triple(
            memory.getValue(currentIndex + 1),
            memory.getValue(currentIndex + 2),
            memory.getValue(currentIndex + 3)
        )

    private fun readValue(memory: MutableMap<Int, Int>, mode: Char, value: Int): Int = if (mode == '0') {
        memory.getValue(value)
    } else {
        value
    }

    private fun operate3(
        memory: MutableMap<Int, Int>,
        modes: String,
        operationPointer: Int,
        operation: (firstValue: Int, secondValue: Int) -> Int
    ) {
        val currentModes = processModes(modes, 3)
        val values = read3(memory, operationPointer)
        memory[values.third] = operation(
            readValue(memory, currentModes[0], values.first),
            readValue(memory, currentModes[1], values.second)
        )
    }

    private fun jumpIf(
        memory: MutableMap<Int, Int>,
        modes: String,
        operationPointer: Int,
        condition: (firstValue: Int) -> Boolean
    ): Int {
        val currentModes = processModes(modes, 3)
        val values = read2(memory, operationPointer)
        val firstValue = readValue(memory, currentModes[0], values.first)
        return if (condition(firstValue)) {
            readValue(memory, currentModes[1], values.second)
        } else {
            operationPointer + 3
        }
    }
}
