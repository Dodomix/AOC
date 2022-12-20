package hr.dodomix.advent.solutions


class Day20 {

    fun dayDirectory() = "day20"

    fun part1(input: List<String>): Long {
        val numbers = input.mapIndexed { index, line -> Pair(index, line.toLong()) }.toMutableList()
        return mixList(numbers)
    }

    fun part2(input: List<String>): Long {
        val numbers = input.mapIndexed { index, line -> Pair(index, line.toLong() * 811589153L) }.toMutableList()
        return mixList(numbers, 10)
    }

    private fun mixList(queue: MutableList<Pair<Int, Long>>, repeat: Int = 1): Long {
        repeat(repeat) {
            queue.indices.forEach { index ->
                val currentIndex = queue.indexOfFirst { it.first == index }
                val currentValue = queue[currentIndex]
                queue.removeAt(currentIndex)
                queue.add((currentIndex + currentValue.second).mod(queue.size), currentValue)
            }
        }
        val zeroPosition = queue.indexOfFirst { it.second == 0L }
        return queue[(zeroPosition + 1000) % queue.size].second +
                queue[(zeroPosition + 2000) % queue.size].second +
                queue[(zeroPosition + 3000) % queue.size].second
    }
}
