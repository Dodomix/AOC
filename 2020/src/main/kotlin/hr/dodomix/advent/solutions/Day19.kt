package hr.dodomix.advent.solutions


class Day19 {
    fun dayDirectory() = "day19"

    fun part1(input: List<String>): Int {
        val rules = constructRules(input)
        val allMatches = ruleMatches(rules.getValue(0), rules).toHashSet()
        return input.takeLastWhile { it.isNotEmpty() }.count { line ->
            allMatches.contains(line)
        }
    }

    fun part2(input: List<String>): Int {
        val rules = constructRules(input)
        val matches42 = ruleMatches(rules.getValue(42), rules)
        val matches31 = ruleMatches(rules.getValue(31), rules)

        return input.takeLastWhile { it.isNotEmpty() }.count { line ->
            if (line.length % 8 != 0) {
                false
            } else {
                var currentMessage = line
                var matches31Count = 0
                while (matches31.contains(currentMessage.takeLast(8))) {
                    matches31Count++
                    currentMessage = currentMessage.dropLast(8)
                }
                if (matches31Count == 0) {
                    false
                } else {
                    var matches42Count = 0
                    while (matches42.contains(currentMessage.take(8))) {
                        matches42Count++
                        currentMessage = currentMessage.drop(8)
                    }
                    currentMessage.isEmpty() && matches42Count > matches31Count
                }
            }
        }
    }

    private fun constructRules(input: List<String>) =
        input.takeWhile { it.isNotEmpty() }.fold(mapOf<Int, Rule>()) { currentRules, line ->
            val ruleId = line.takeWhile { it != ':' }.toInt()
            val subrules = line.dropWhile { it != ' ' }.drop(1).split(" | ")
            val rule = if (subrules.size == 1 && subrules[0].contains("\"")) {
                Rule(subrules[0][1], emptyList())
            } else {
                Rule(null, subrules.map { subrule -> subrule.split(" ").map { it.toInt() } })
            }
            currentRules + Pair(ruleId, rule)
        }

    private fun ruleMatches(rule: Rule, rules: Map<Int, Rule>): List<String> {
        if (rule.char != null) {
            return listOf(rule.char.toString())
        }
        return rule.subrules.flatMap { subrules ->
            val matchesPerRule = subrules.map { ruleMatches(rules.getValue(it), rules) }
            matchesPerRule.foldIndexed(matchesPerRule[0]) { index, allMatches, currentMatches ->
                if (index == 0) {
                    allMatches
                } else {
                    allMatches.flatMap { match -> currentMatches.map { match + it } }
                }
            }
        }
    }

    data class Rule(val char: Char?, val subrules: List<List<Int>>)
}
