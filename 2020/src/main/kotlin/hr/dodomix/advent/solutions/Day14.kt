package hr.dodomix.advent.solutions

class Day14 {
    fun dayDirectory() = "day14"

    fun part1(input: List<String>): Long {
        val memory = mutableMapOf<Int, Long>()
        var mask = ""
        input.forEach { line ->
            if (line.startsWith("mask = ")) {
                mask = line.removePrefix("mask = ")
            } else {
                val lineValues = line.split(" = ")
                val location = lineValues[0].substring(4).dropLast(1).toInt()
                val value = mask.reversed().foldIndexed(lineValues[1].toLong()) { index, currentValue, c ->
                    val bitMask = (1.toLong() shl index)
                    when (c) {
                        '0' -> currentValue.and((1.toLong() shl 36) - 1.toLong() - bitMask)
                        '1' -> currentValue.or(bitMask)
                        else -> currentValue
                    }
                }
                memory[location] = value
            }
        }
        return memory.values.sumOf { it }
    }

    fun part2(input: List<String>): Long {
        val memory = mutableMapOf<Long, Long>()
        var mask = ""
        input.forEach { line ->
            if (line.startsWith("mask = ")) {
                mask = line.removePrefix("mask = ")
            } else {
                val lineValues = line.split(" = ")
                val value = lineValues[1].toLong()
                val locations = mask.reversed()
                    .foldIndexed(listOf(lineValues[0].substring(4).dropLast(1).toLong())) { index, locations, c ->
                        val bitMask = (1.toLong() shl index)
                        when (c) {
                            '0' -> locations
                            '1' -> locations.map { it or bitMask }
                            else -> locations.flatMap { listOf(it or bitMask, it and ((1.toLong() shl 36) - 1.toLong() - bitMask)) }
                        }
                    }
                locations.forEach { location ->
                    memory[location] = value
                }
            }
        }
        return memory.values.sumOf { it }
    }
}
