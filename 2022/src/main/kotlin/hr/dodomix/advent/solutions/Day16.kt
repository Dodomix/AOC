package hr.dodomix.advent.solutions

import kotlin.math.max


class Day16 {
    fun dayDirectory() = "day16"

    fun part1(input: List<String>): Int {
        val valves = input.map { line -> Valve.fromString(line) }.associateBy { it.id }
        val shortestPaths: Map<String, Map<String, Int>> = calculateShortestPaths(valves)
        return calculateScore(valves, shortestPaths, 0, "AA", emptySet(), 30, 0)
    }

    fun part2(input: List<String>): Int {
        val valves = input.map { line -> Valve.fromString(line) }.associateBy { it.id }
        val shortestPaths: Map<String, Map<String, Int>> = calculateShortestPaths(valves)
        return calculateScore(valves, shortestPaths, 0, "AA", emptySet(), 26, 0, true)
    }

    private fun calculateScore(
        valves: Map<String, Valve>,
        shortestPaths: Map<String, Map<String, Int>>,
        score: Int,
        currentValve: String,
        visited: Set<String>,
        totalTime: Int,
        time: Int,
        doubleCalculation: Boolean = false
    ): Int {
        val maxScore = max(
            score, shortestPaths.getValue(currentValve).maxOf { (valve, distance) ->
                if (!visited.contains(valve) && time + distance + 1 < totalTime) {
                    calculateScore(
                        valves,
                        shortestPaths,
                        score + (totalTime - time - distance - 1) * valves.getValue(valve).flowRate,
                        valve,
                        visited + valve,
                        totalTime,
                        time + distance + 1,
                        doubleCalculation
                    )
                } else {
                    0
                }
            }
        )
        return if (doubleCalculation) {
            max(
                maxScore, calculateScore(
                    valves,
                    shortestPaths,
                    score,
                    "AA",
                    visited,
                    totalTime,
                    0,
                    false
                )
            )
        } else {
            maxScore
        }
    }

    private fun calculateShortestPaths(valves: Map<String, Valve>) =
        valves.keys.fold(valves.values.associate { it.id to it.neighbors.associateWith { 1 } }) { temporaryShortestPaths1, key1 ->
            valves.keys.fold(temporaryShortestPaths1) { temporaryShortestPaths2, key2 ->
                valves.keys.fold(temporaryShortestPaths2) { shortestPaths, key3 ->
                    val distance21 = shortestPaths.getValue(key2)[key1] ?: 1000000
                    val distance13 = shortestPaths.getValue(key1)[key3] ?: 1000000
                    val distance23 = shortestPaths.getValue(key2)[key3] ?: 1000000
                    if (distance21 + distance13 < distance23) {
                        shortestPaths + Pair(key2, shortestPaths.getValue(key2) + Pair(key3, distance21 + distance13))
                    } else {
                        shortestPaths
                    }
                }
            }
        }.mapValues { (_, shortestPaths) ->
            shortestPaths.filterKeys { key -> valves.getValue(key).flowRate != 0 }
        }


    data class Valve(val id: String, val flowRate: Int, val neighbors: List<String>) {
        companion object {
            fun fromString(line: String): Valve {
                val (id, flowRate, neighbors) =
                    "Valve (..) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex()
                        .find(line)!!
                        .groupValues
                        .drop(1)
                return Valve(id, flowRate.toInt(), neighbors.split(", "))
            }
        }
    }
}
