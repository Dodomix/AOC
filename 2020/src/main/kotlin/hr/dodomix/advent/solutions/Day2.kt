package hr.dodomix.advent.solutions

class Day2 {
    fun dayDirectory() = "day2"

    fun part1(input: List<String>): Int {
        return input.count {
            val passwordSplit = it.split(": ")
            val password = passwordSplit[1]
            val letterSplit = passwordSplit[0].split(" ")
            val letter = letterSplit[1][0]
            val numberSplit = letterSplit[0].split("-")
            val minValue = numberSplit[0].toInt()
            val maxValue = numberSplit[1].toInt()
            val letterCount = password.count { passwordLetter -> passwordLetter == letter }
            letterCount >= minValue && letterCount <= maxValue
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val passwordSplit = it.split(": ")
            val password = passwordSplit[1]
            val letterSplit = passwordSplit[0].split(" ")
            val letter = letterSplit[1][0]
            val numberSplit = letterSplit[0].split("-")
            val firstPosition = numberSplit[0].toInt() - 1
            val secondPosition = numberSplit[1].toInt() - 1
            (password[firstPosition] == letter && password[secondPosition] != letter) ||
                (password[firstPosition] != letter && password[secondPosition] == letter)
        }
    }
}
