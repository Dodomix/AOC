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


    fun runProgram(memory: MutableMap<Int, Int>): MutableMap<Int, Int> {
        var operationPointer = 0
        while (true) {
            when (memory.getValue(operationPointer)) {
                1 -> {
                    val indices = read3(memory, operationPointer)
                    memory[indices.third] = memory.getValue(indices.first) + memory.getValue(indices.second)
                    operationPointer += 4
                }
                2 -> {
                    val indices = read3(memory, operationPointer)
                    memory[indices.third] = memory.getValue(indices.first) * memory.getValue(indices.second)
                    operationPointer += 4
                }
                99 -> return memory
                else -> {
                    throw RuntimeException("Invalid code")
                }
            }
        }
    }

    private fun read3(memory: MutableMap<Int, Int>, currentIndex: Int) =
        Triple(memory.getValue(currentIndex + 1),
            memory.getValue(currentIndex + 2),
            memory.getValue(currentIndex + 3))
}
