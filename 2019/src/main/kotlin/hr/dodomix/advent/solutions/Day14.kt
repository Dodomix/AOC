package hr.dodomix.advent.solutions

import kotlin.math.ceil

class Day14 {

    fun dayDirectory() = "day14"

    data class Element(var count: Long, var name: String)

    fun part1(input: List<String>): Long {
        return getOreCount(constructReactions(input))
    }

    fun part2(input: List<String>): Long {
        var low = 1L
        var high = 10000000L
        val reactions = constructReactions(input)
        while (high - low > 1L) {
            val fuel = (high + low) / 2
            val oreCount = getOreCount(reactions, fuel)
            if (oreCount > 1000000000000) {
                high = fuel
            } else {
                low = fuel
            }
        }
        return low
    }

    private fun constructReactions(input: List<String>): List<Pair<List<Element>, List<Element>>> {
        return input
            .map { it.split(" => ") }
            .map {
                Pair(it[0].split(", ")
                    .map { value -> value.split(" ") }
                    .map { value -> Element(value[0].toLong(), value[1]) },
                    it[1].split(", ")
                        .map { value -> value.split(" ") }
                        .map { value -> Element(value[0].toLong(), value[1]) })
            }
    }

    private fun getOreCount(reactions: List<Pair<List<Element>, List<Element>>>, fuelCount: Long = 1L): Long {
        val elements = mutableListOf(Element(-fuelCount, "FUEL"))
        var oreCount = 0L
        while (elements.any { it.count < 0 }) {
            val requiredElement = elements.find { it.count < 0 }!!
            val reaction = reactions.find {
                it.second.any { element -> element.name == requiredElement.name }
            }!!
            val requiredElementProduction = reaction.second.find { it.name == requiredElement.name }!!
            val reactionCount = ceil(-requiredElement.count / requiredElementProduction.count.toDouble()).toInt()
            for (producedElement in reaction.second) {
                val producedCount = producedElement.count * reactionCount
                elements.find { it.name == producedElement.name }?.let {
                    it.count += producedCount
                } ?: elements.add(Element(producedCount, producedElement.name))
            }
            for (requirement in reaction.first) {
                val requirementCount = requirement.count * reactionCount
                if (requirement.name == "ORE") {
                    oreCount += requirementCount
                } else {
                    elements.find { it.name == requirement.name }?.let {
                        it.count -= requirementCount
                    } ?: elements.add(Element(-requirementCount, requirement.name))
                }
            }
        }
        return oreCount
    }
}
