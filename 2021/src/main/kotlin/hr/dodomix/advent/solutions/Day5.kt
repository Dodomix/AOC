package hr.dodomix.advent.solutions

class Day5 {
    fun dayDirectory() = "day5"

    private infix fun Int.toward(to: Int): IntProgression {
        val step = if (this > to) -1 else 1
        return IntProgression.fromClosedRange(this, to, step)
    }

    fun part1(input: List<String>): Int {
        return parseInput(input).filter { it.first.first == it.second.first || it.first.second == it.second.second }
            .fold(emptyMap<Pair<Int, Int>, Int>()) { ventMap, vent ->
                ventMap + if (vent.first.first == vent.second.first) {
                    (vent.first.second toward vent.second.second).map { i ->
                        Pair(Pair(vent.first.first, i), ventMap.getOrDefault(Pair(vent.first.first, i), 0) + 1)
                    }
                } else {
                    (vent.first.first toward vent.second.first).map { i ->
                        Pair(Pair(i, vent.first.second), ventMap.getOrDefault(Pair(i, vent.first.second), 0) + 1)
                    }
                }
            }.values.count { it > 1 }
    }

    fun part2(input: List<String>): Int {
        return parseInput(input)
            .fold(emptyMap<Pair<Int, Int>, Int>()) { ventMap, vent ->
                ventMap + if (vent.first.first == vent.second.first) {
                    (vent.first.second toward vent.second.second).map { i ->
                        Pair(Pair(vent.first.first, i), ventMap.getOrDefault(Pair(vent.first.first, i), 0) + 1)
                    }
                } else if (vent.first.second == vent.second.second) {
                    (vent.first.first toward vent.second.first).map { i ->
                        Pair(Pair(i, vent.first.second), ventMap.getOrDefault(Pair(i, vent.first.second), 0) + 1)
                    }
                } else {
                    (vent.first.first toward vent.second.first).zip(vent.first.second toward vent.second.second)
                        .map {
                            Pair(it, ventMap.getOrDefault(it, 0) + 1)
                        }
                }
            }.values.count { it > 1 }
    }

    private fun parseInput(input: List<String>) = input.map { line ->
        val groupValues = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)").matchEntire(line)!!.groups.drop(1).map { it!!.value.toInt() }
        Pair(Pair(groupValues[0], groupValues[1]), Pair(groupValues[2], groupValues[3]))
    }
}
