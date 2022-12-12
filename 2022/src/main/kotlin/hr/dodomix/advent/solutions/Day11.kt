package hr.dodomix.advent.solutions


class Day11 {
    fun dayDirectory() = "day11"

    fun test1(input: List<String>): Long {
        val monkeys = getTestMonkeys()
        val inspections = executeMonkeys(monkeys, 20, 23 * 19 * 13 * 17).sorted().reversed()
        return inspections[0] * inspections[1]
    }

    fun part1(input: List<String>): Long {
        val monkeys = getProdMonkeys()
        val inspections = executeMonkeys(monkeys, 20, 2 * 7 * 3 * 17 * 11 * 19 * 5 * 13).sorted().reversed()
        return inspections[0] * inspections[1]
    }

    fun test2(input: List<String>): Long {
        val monkeys = getTestMonkeys()
        val inspections = executeMonkeys(monkeys, 10000, 23 * 19 * 13 * 17, false).sorted().reversed()
        return inspections[0] * inspections[1]
    }

    fun part2(input: List<String>): Long {
        val monkeys = getProdMonkeys()
        val inspections =
            executeMonkeys(monkeys, 10000, 2 * 7 * 3 * 17 * 11 * 19 * 5 * 13, false).sorted().reversed()
        return inspections[0] * inspections[1]
    }

    private fun getTestMonkeys() = listOf(
        Monkey(
            listOf(79, 98).map { it.toLong() }.toMutableList(),
            { old -> old * 19L },
            { if (it % 23L == 0L) 2 else 3 }),
        Monkey(
            listOf(54, 65, 75, 74).map { it.toLong() }.toMutableList(),
            { old -> old + 6L },
            { if (it % 19L == 0L) 2 else 0 }),
        Monkey(
            listOf(79, 60, 97).map { it.toLong() }.toMutableList(),
            { old -> old * old },
            { if (it % 13L == 0L) 1 else 3 }),
        Monkey(
            listOf(74).map { it.toLong() }.toMutableList(),
            { old -> old + 3L },
            { if (it % 17L == 0L) 0 else 1 })
    )

    private fun getProdMonkeys() = listOf(
        Monkey(
            listOf(80).map { it.toLong() }.toMutableList(),
            { old -> old * 5L },
            { if (it % 2L == 0L) 4 else 3 }),
        Monkey(
            listOf(75, 83, 74).map { it.toLong() }.toMutableList(),
            { old -> old + 7L },
            { if (it % 7L == 0L) 5 else 6 }),
        Monkey(
            listOf(86, 67, 61, 96, 52, 63, 73).map { it.toLong() }.toMutableList(),
            { old -> old + 5L },
            { if (it % 3L == 0L) 7 else 0 }),
        Monkey(
            listOf(85, 83, 55, 85, 57, 70, 85, 52).map { it.toLong() }.toMutableList(),
            { old -> old + 8L },
            { if (it % 17L == 0L) 1 else 5 }),
        Monkey(
            listOf(67, 75, 91, 72, 89).map { it.toLong() }.toMutableList(),
            { old -> old + 4L },
            { if (it % 11L == 0L) 3 else 1 }),
        Monkey(
            listOf(66, 64, 68, 92, 68, 77).map { it.toLong() }.toMutableList(),
            { old -> old * 2L },
            { if (it % 19L == 0L) 6 else 2 }),
        Monkey(
            listOf(97, 94, 79, 88).map { it.toLong() }.toMutableList(),
            { old -> old * old },
            { if (it % 5L == 0L) 2 else 7 }),
        Monkey(
            listOf(77, 85).map { it.toLong() }.toMutableList(),
            { old -> old + 6L },
            { if (it % 13L == 0L) 4 else 0 })
    )

    private fun executeMonkeys(monkeys: List<Monkey>, rounds: Int, modulo: Long, divideBy3: Boolean = true) =
        (0 until rounds).fold(monkeys.map { 0L }) { inspections, _ ->
            monkeys.mapIndexed { index, monkey ->
                val itemsCount = monkey.items.count()
                monkey.items.forEach { itemWorry ->
                    val newWorry = (monkey.operation(itemWorry) / if (divideBy3) 3L else 1L) % modulo
                    monkeys[monkey.newMonkeyIndex(newWorry)].items.add(newWorry)
                }
                monkey.items.clear()
                inspections[index] + itemsCount
            }
        }

    private data class Monkey(
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val newMonkeyIndex: (Long) -> Int
    )
}
