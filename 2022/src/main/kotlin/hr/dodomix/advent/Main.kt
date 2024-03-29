package hr.dodomix.advent

import hr.dodomix.advent.solutions.Day25
import hr.dodomix.advent.util.Util

fun main() {
    val day = Day25()
    val dayDirectory = day.dayDirectory()
    println("Part 1 result: " + day.part1(Util.readFileLines("$dayDirectory/input1")))
    // println("Part 2 result: " + day.part2(Util.readFileLines("$dayDirectory/input1")))
}
