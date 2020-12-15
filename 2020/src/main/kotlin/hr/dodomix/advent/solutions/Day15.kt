package hr.dodomix.advent.solutions

class Day15 {
    fun dayDirectory() = "day15"

    fun part1(input: List<String>): Int {
        val inputs = input[0].split(",").map { it.toInt() }
        return (inputs.size + 1 until 2020).fold(Pair(inputs, 0)) { (currentInputs, lastInput), _ ->
            Pair(currentInputs + lastInput, currentInputs.lastIndexOf(lastInput).let {
                if (it == -1) 0 else currentInputs.size - it
            })
        }.second
    }

    fun part2(input: List<String>): Int {
        val inputs = input[0].split(",").map { it.toInt() }
        val inputMap = inputs.mapIndexed { i, value -> value to i }.toMap().toMutableMap()
        var nextInput = 0
        for (index in inputs.size until 30000000 - 1) {
            val lastInput = nextInput
            nextInput = inputMap.getOrDefault(nextInput, -1).let {
                if (it == -1) 0 else index - it
            }
            inputMap += lastInput to index
        }
        return nextInput
    }
}
