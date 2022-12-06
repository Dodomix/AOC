package hr.dodomix.advent.solutions

class Day6 {
    fun dayDirectory() = "day6"
    fun part1(input: List<String>): Int = detectSequenceIndex(input[0], 4)

    fun part2(input: List<String>): Int = detectSequenceIndex(input[0], 14)

    private fun detectSequenceIndex(line: String, sequenceSize: Int) = line.indices.find { i ->
        line.substring(i, i + sequenceSize).toSet().size == sequenceSize
    }!! + sequenceSize
}
