package hr.dodomix.advent.solutions

class Day5 {
    fun dayDirectory() = "day5"
    fun part1(input: List<String>): String {
        return parseAndMoveBoxes(input)
    }

    fun part2(input: List<String>): String {
        return parseAndMoveBoxes(input, true)
    }

    private fun parseAndMoveBoxes(input: List<String>, moveMultipleBoxes: Boolean = false): String {
        val stackLines = input.takeWhile { it.isNotBlank() }
        val stacks = stackLines.last().mapIndexed { index, value ->
            if (value == ' ') null
            else {
                stackLines.dropLast(1)
                    .map { line -> line.getOrNull(index) }
                    .filter { it != null && it != ' ' }
            }
        }.filterNotNull()
        val afterMoveStacks = input
            .dropWhile { it.isNotBlank() }.drop(1)
            .fold(stacks) { currentStacks, line ->
                val split = line.split(" ")
                val moveNumber = split[1].toInt()
                val from = split[3].toInt() - 1
                val to = split[5].toInt() - 1
                currentStacks.mapIndexed { index, stack ->
                    when (index) {
                        from -> stack.drop(moveNumber)
                        to ->
                            (if (moveMultipleBoxes) currentStacks[from].take(moveNumber)
                            else currentStacks[from].take(moveNumber).reversed()) + stack

                        else -> stack
                    }
                }
            }
        return afterMoveStacks.map { it.first() }.joinToString("")
    }
}
