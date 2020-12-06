package hr.dodomix.advent.solutions

class Day6 {
    fun dayDirectory() = "day6"

    fun part1(input: List<String>): Int {
        return input.fold(Pair<Set<Char>, Int>(HashSet(), 0)) { (answers, count), line ->
            if (line.isEmpty()) {
                Pair(HashSet(), count + answers.size)
            } else {
                Pair(answers + line.toCharArray().asList(), count)
            }
        }.second
    }

    fun part2(input: List<String>): Int {
        return input.fold(Triple<List<Char>, Int, String>(ArrayList(), 0, "")) { (answers, count, previousLine), line ->
            when {
                line.isEmpty() -> Triple(emptyList(), count + answers.size, line)
                previousLine.isEmpty() -> Triple(answers + line.toCharArray().asList(), count, line)
                else -> Triple(answers.filter { line.contains(it) }, count, line)
            }
        }.second
    }
}
