package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.splitIgnoreEmpty

class Day9 {
    fun dayDirectory() = "day9"

    fun part1(input: List<String>): Int {
        val map = input.flatMapIndexed { i, line ->
            line.splitIgnoreEmpty("").mapIndexed { j, c ->
                Pair(Pair(i, j), c.toInt())
            }
        }.toMap()
        return (0..map.maxOf { it.key.first }).sumOf { i ->
            (0..map.maxOf { it.key.second }).sumOf { j ->
                val value = map.getValue(Pair(i, j))
                if (value < map.getOrDefault(Pair(i, j - 1), 10) && value < map.getOrDefault(Pair(i - 1, j), 10) &&
                    value < map.getOrDefault(Pair(i, j + 1), 10) && value < map.getOrDefault(Pair(i + 1, j), 10)
                ) {
                    value + 1
                } else {
                    0
                }
            }
        }
    }

    fun part2(input: List<String>): Int {
        val map = input.flatMapIndexed { i, line ->
            line.splitIgnoreEmpty("").mapIndexed { j, c ->
                Pair(Pair(i, j), c.toInt())
            }
        }.toMap()
        val basins = mutableListOf<MutableSet<Pair<Int, Int>>>()
        for (i in 0..map.maxOf { it.key.first }) {
            for (j in 0..map.maxOf { it.key.second }) {
                if (map.getValue(Pair(i, j)) == 9) {
                    continue
                }
                var foundBasin = false
                if (map.containsKey(Pair(i, j - 1)) && map.getValue(Pair(i, j - 1)) != 9) { // same basin
                    basins.find { it.contains(Pair(i, j - 1)) }!!.add(Pair(i, j))
                    continue
                } else if (map.containsKey(Pair(i - 1, j)) && map.getValue(Pair(i - 1, j)) != 9) { // same basin
                    basins.find { it.contains(Pair(i - 1, j)) }!!.add(Pair(i, j))
                    foundBasin = true
                }
                for (jCheck in (j + 1..map.maxOf { it.key.second })) {
                    if (!map.containsKey(Pair(i, jCheck)) || map.getValue(Pair(i, jCheck)) == 9) {
                        break
                    }
                    if (map.containsKey(Pair(i - 1, jCheck)) && map.getValue(Pair(i - 1, jCheck)) != 9) { // same basin
                        basins.find { it.contains(Pair(i - 1, jCheck)) }!!.add(Pair(i, j))
                        foundBasin = true
                    }
                }
                if (foundBasin) {
                    val containingBasins = basins.filter { it.contains(Pair(i, j)) }
                    basins.removeAll(containingBasins)
                    val newBasin = mutableSetOf<Pair<Int, Int>>()
                    containingBasins.forEach {
                        newBasin.addAll(it)
                    }
                    basins.add(newBasin)
                } else { // new basin
                    basins.add(mutableSetOf(Pair(i, j)))
                }
            }
        }
        return basins.map { it.size }.sorted().takeLast(3).fold(1) { acc, i -> acc * i }
    }
}
