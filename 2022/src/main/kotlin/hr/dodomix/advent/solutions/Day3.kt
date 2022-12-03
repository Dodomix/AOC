package hr.dodomix.advent.solutions

class Day3 {
    fun dayDirectory() = "day3"

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val firstCompartment = line.substring(0, line.length / 2).toSet()
            val secondCompartment = line.substring(line.length / 2).toSet()
            firstCompartment.single { secondCompartment.contains(it) }.let {
                calculatePriority(it)
            }
        }
    }

    fun part2(input: List<String>): Int {
        generateSequence(1) { it + 1 }.fold(Pair(input, 0)) { (currentInput, result), _ ->
            if (currentInput.isEmpty()) {
                return result
            }
            val lines = currentInput.take(3).map { it.toSet() }
            Pair(currentInput.drop(3), result + calculatePriority(lines[0].single {
                lines[1].contains(it) && lines[2].contains(it)
            }))
        }
        return -1
    }

    private fun calculatePriority(it: Char) =
        if (it.isLowerCase()) it.minus('a') + 1
        else it.minus('A') + 27
}
