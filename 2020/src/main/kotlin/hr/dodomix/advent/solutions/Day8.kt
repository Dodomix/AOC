package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.ProcessorUtil.constructMemory
import hr.dodomix.advent.util.ProcessorUtil.runProgram

class Day8 {
    fun dayDirectory() = "day8"

    fun part1(input: List<String>): Int {
        val memory = constructMemory(input)
        return runProgram(memory, true).accumulator.toInt()
    }

    fun part2(input: List<String>): Int {
        input.indices.forEach { index ->
            var modifiedInput: List<String>? = null
            if (input[index].contains("nop")) {
                modifiedInput = ArrayList(input)
                modifiedInput[index] = modifiedInput[index].replace("nop", "jmp")
            } else if (input[index].contains("jmp")) {
                modifiedInput = ArrayList(input)
                modifiedInput[index] = modifiedInput[index].replace("jmp", "nop")
            }
            if (!modifiedInput.isNullOrEmpty()) {
                val memory = constructMemory(modifiedInput)
                val (resultPointer, resultAccumulator) = runProgram(memory, true)
                if (resultPointer == input.size) {
                    return resultAccumulator.toInt()
                }
            }
        }
        return -1
    }
}
