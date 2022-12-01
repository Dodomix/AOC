package hr.dodomix.advent.solutions

class Day1 {
    fun dayDirectory() = "day1"

    fun part1(input: List<String>): Int = input.fold(listOf(0)) { calories, currentMealCalories ->
        if (currentMealCalories.isEmpty()) {
            calories + 0
        } else {
            calories.dropLast(1) + (calories.last() + currentMealCalories.toInt())
        }
    }.max()

    fun part2(input: List<String>): Int = input.fold(listOf(0)) { calories, currentMealCalories ->
        if (currentMealCalories.isEmpty()) {
            calories + 0
        } else {
            calories.dropLast(1) + (calories.last() + currentMealCalories.toInt())
        }
    }.sorted().takeLast(3).sum()
}
