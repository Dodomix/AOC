package hr.dodomix.advent.solutions

class Day4 {
    fun dayDirectory() = "day4"

    fun part1(input: List<String>): Int {
        val requiredFields = listOf("byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:")
        return input.fold(Pair("", 0)) { (passport, count), line ->
            if (line.isEmpty()) {
                Pair("", count + if (requiredFields.all { field -> passport.contains(field) }) 1 else 0)
            } else {
                Pair(passport + line, count)
            }
        }.second
    }

    fun part2(input: List<String>): Int {
        val requiredFieldValidators = mapOf(
            Pair("byr", { value: String -> value.toInt() in 1920..2002 }),
            Pair("iyr", { value: String -> value.toInt() in 2010..2020 }),
            Pair("eyr", { value: String -> value.toInt() in 2020..2030 }),
            Pair("hgt", { value: String ->
                value.dropLast(2).let {
                    when {
                        value.endsWith("cm") -> it.toInt() in 150..193
                        value.endsWith("in") -> it.toInt() in 59..76
                        else -> false
                    }
                }
            }),
            Pair("hcl", { value: String -> value.matches("#[0-9a-f]{6}".toRegex()) }),
            Pair("ecl", { value: String -> value in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth") }),
            Pair("pid", { value: String -> value.matches("[0-9]{9}".toRegex()) })
        )
        return input.fold(Pair(mapOf<String, String>(), 0)) { (passport, count), line ->
            if (line.isEmpty()) {
                Pair(mapOf(), count + if (requiredFieldValidators.all { (key, validator) ->
                        passport.containsKey(key) && validator(passport.getValue(key))
                    }) 1 else 0)
            } else {
                Pair(passport + line.split(" ").map { fieldValue ->
                    fieldValue.split(":").let { (key, value) ->
                        Pair(key, value)
                    }
                }, count)
            }
        }.second
    }
}
