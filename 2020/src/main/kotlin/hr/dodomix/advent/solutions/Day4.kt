package hr.dodomix.advent.solutions

class Day4 {
    fun dayDirectory() = "day4"

    fun part1(input: List<String>): Int {
        val requiredFields = listOf("byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:")
        var passport = ""
        var count = 0
        input.forEach {
            if (it.isEmpty()) {
                if (requiredFields.all { field -> passport.contains(field) }) {
                    count++
                    println(1)
                } else {
                    println(0)
                }
                passport = ""
            } else {
                passport += it
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val requiredFieldValidators = mapOf(
            Pair("byr", { value: String -> value.toInt() in 1920..2002 }),
            Pair("iyr", { value: String -> value.toInt() in 2010..2020 }),
            Pair("eyr", { value: String -> value.toInt() in 2020..2030 }),
            Pair("hgt", { value: String ->
                val numericalValue = value.dropLast(2)
                when {
                    value.endsWith("cm") -> numericalValue.toInt() in 150..193
                    value.endsWith("in") -> numericalValue.toInt() in 59..76
                    else -> false
                }
            }),
            Pair("hcl", { value: String -> value.matches("#[0-9a-f]{6}".toRegex()) }),
            Pair("ecl", { value: String -> value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") }),
            Pair("pid", { value: String -> value.matches("[0-9]{9}".toRegex()) })
        )
        val passport = mutableMapOf<String, String>()
        var count = 0
        input.forEach {
            if (it.isEmpty()) {
                if (requiredFieldValidators.all { entry ->
                        passport.containsKey(entry.key) &&
                            entry.value(passport.getValue(entry.key))
                    }) {
                    count++
                }
                passport.clear()
            } else {
                it.split(" ").forEach { fieldValue ->
                    val fieldValueSplit = fieldValue.split(":")
                    passport += Pair(fieldValueSplit[0], fieldValueSplit[1])
                }
            }
        }
        return count
    }
}
