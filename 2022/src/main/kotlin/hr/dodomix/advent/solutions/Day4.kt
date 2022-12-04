package hr.dodomix.advent.solutions

class Day4 {
    fun dayDirectory() = "day4"

    fun part1(input: List<String>): Int =
        input.sumOf { line ->
            val (section1, section2) = line.split(",")
            val (start1, end1) = section1.split("-").map { it.toInt() }
            val (start2, end2) = section2.split("-").map { it.toInt() }
            (if ((start1 >= start2 && end1 <= end2) || (start2 >= start1 && end2 <= end1)) 1 else 0) as Int
        }

    fun part2(input: List<String>): Int =
        input.sumOf { line ->
            val (section1, section2) = line.split(",")
            val (start1, end1) = section1.split("-").map { it.toInt() }
            val (start2, end2) = section2.split("-").map { it.toInt() }
            (if ((start1 in start2..end2) || (start2 in start1..end1)) 1 else 0) as Int
        }
}
