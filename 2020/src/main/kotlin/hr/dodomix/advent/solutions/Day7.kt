package hr.dodomix.advent.solutions

class Day7 {
    fun dayDirectory() = "day7"

    fun part1(input: List<String>): Int {
        var initialBags = emptySet<String>()
        var updatedBags = setOf("shiny gold")
        while (initialBags != updatedBags) {
            initialBags = HashSet(updatedBags)
            updatedBags = input.fold(updatedBags) { bags, line ->
                if (initialBags.any { bag -> line.matches(".* contain .* $bag .*".toRegex()) }) {
                    bags + line.split(" bags contain")[0]
                } else {
                    bags
                }
            }
        }
        return updatedBags.size - 1
    }

    fun part2(input: List<String>): Int {
        var bags = listOf(Pair(1, "shiny gold"))
        var totalCount = 0
        while (bags.isNotEmpty()) {
            bags = bags.fold(emptyList()) { newBags, (count, bag) ->
                val updatedBags = ArrayList(newBags)
                totalCount += count
                val description = input.find { line -> line.matches("$bag bags contain .*".toRegex()) }!!
                if (!description.contains("no other bags")) {
                    val bagCounts = description.split("contain ")[1]
                    val bagsByCount = bagCounts.split(", ")
                    bagsByCount.forEach { bagByCount ->
                        updatedBags.add(
                            Pair(
                                bagByCount[0].toString().toInt() * count,
                                bagByCount.substring(2).split(" bag")[0]
                            )
                        )
                    }
                }
                updatedBags
            }
        }
        return totalCount - 1
    }
}
