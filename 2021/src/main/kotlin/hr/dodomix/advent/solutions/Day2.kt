package hr.dodomix.advent.solutions

class Day2 {
    fun dayDirectory() = "day2"

    fun part1(input: List<String>): Int = input.fold(Pair(0, 0)) { (depth, position), instruction ->
        val instructionDetails = instruction.split(" ")
        when (instructionDetails[0]) {
            "forward" -> Pair(depth, position + instructionDetails[1].toInt())
            "down" -> Pair(depth + instructionDetails[1].toInt(), position)
            "up" -> Pair(depth - instructionDetails[1].toInt(), position)
            else -> throw RuntimeException("Invalid instruction $instruction")
        }
    }.let { it.first * it.second }

    fun part2(input: List<String>): Int = input.fold(Triple(0, 0, 0)) { (aim, depth, position), instruction ->
        val instructionDetails = instruction.split(" ")
        when (instructionDetails[0]) {
            "forward" -> Triple(aim, depth + aim * instructionDetails[1].toInt(), position + instructionDetails[1].toInt())
            "down" -> Triple(aim + instructionDetails[1].toInt(), depth, position)
            "up" -> Triple(aim - instructionDetails[1].toInt(), depth, position)
            else -> throw RuntimeException("Invalid instruction $instruction")
        }
    }.let { it.second * it.third }
}
