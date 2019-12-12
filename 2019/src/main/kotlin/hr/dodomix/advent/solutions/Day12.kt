package hr.dodomix.advent.solutions

import kotlin.math.abs

class Day12 {

    fun dayDirectory() = "day12"

    fun part1(input: List<String>): Any {
        var moons = input.map {
            val inputs = it.split(", ")
            Triple(
                inputs[0].drop(3).toInt(),
                inputs[1].drop(2).toInt(),
                inputs[2].drop(2).dropLast(1).toInt()
            )
        }
        var velocities = moons.map { Triple(0, 0, 0) }
        for (step in 0 until 1000) {
            val newVelocities = velocities.toMutableList()
            moons.forEachIndexed { index1, moon1 ->
                var currentVelocity = velocities[index1]
                moons.forEachIndexed { index2, moon2 ->
                    if (index1 != index2) {
                        currentVelocity = Triple(
                            currentVelocity.first +
                                    when {
                                        moon2.first > moon1.first -> 1
                                        moon2.first == moon1.first -> 0
                                        else -> -1
                                    },
                            currentVelocity.second +
                                    when {
                                        moon2.second > moon1.second -> 1
                                        moon2.second == moon1.second -> 0
                                        else -> -1
                                    },
                            currentVelocity.third +
                                    when {
                                        moon2.third > moon1.third -> 1
                                        moon2.third == moon1.third -> 0
                                        else -> -1
                                    }
                        )
                    }
                }
                newVelocities[index1] = currentVelocity
            }
            moons = moons.mapIndexed { index, moon ->
                val velocity = newVelocities[index]
                Triple(moon.first + velocity.first, moon.second + velocity.second, moon.third + velocity.third)
            }
            velocities = newVelocities
        }
        return moons.zip(velocities).map {
            (abs(it.first.first) + abs(it.first.second) + abs(it.first.third)) *
                    (abs(it.second.first) + abs(it.second.second) + abs(it.second.third))
        }.sum()
    }

    fun part2(input: List<String>): Any {
        var moons = input.map {
            val inputs = it.split(", ")
            Triple(
                inputs[0].drop(3).toLong(),
                inputs[1].drop(2).toLong(),
                inputs[2].drop(2).dropLast(1).toLong()
            )
        }
        val previousX = moons.map { it.first }
        val previousY = moons.map { it.second }
        val previousZ = moons.map { it.third }
        var xStep = -1
        var yStep = -1
        var zStep = -1
        var velocities = moons.map { Triple(0, 0, 0) }
        for (step in 0 until 1000000) {
            val newVelocities = velocities.toMutableList()
            moons.forEachIndexed { index1, moon1 ->
                var currentVelocity = velocities[index1]
                moons.forEachIndexed { index2, moon2 ->
                    if (index1 != index2) {
                        currentVelocity = Triple(
                            currentVelocity.first +
                                    when {
                                        moon2.first > moon1.first -> 1
                                        moon2.first == moon1.first -> 0
                                        else -> -1
                                    },
                            currentVelocity.second +
                                    when {
                                        moon2.second > moon1.second -> 1
                                        moon2.second == moon1.second -> 0
                                        else -> -1
                                    },
                            currentVelocity.third +
                                    when {
                                        moon2.third > moon1.third -> 1
                                        moon2.third == moon1.third -> 0
                                        else -> -1
                                    }
                        )
                    }
                }
                newVelocities[index1] = currentVelocity
            }
            moons = moons.mapIndexed { index, moon ->
                val velocity = newVelocities[index]
                Triple(moon.first + velocity.first, moon.second + velocity.second, moon.third + velocity.third)
            }
            if (xStep == -1 && moons.map { it.first } == previousX) {
                xStep = step + 2
                println("xStep = $xStep")
            }
            if (yStep == -1 && moons.map { it.second } == previousY) {
                yStep = step + 2
                println("yStep = $yStep")
            }
            if (zStep == -1 && moons.map { it.third } == previousZ) {
                zStep = step + 2
                println("zStep = $zStep")
            }
            velocities = newVelocities
        }
        return moons.zip(velocities).map {
            (abs(it.first.first) + abs(it.first.second) + abs(it.first.third)) *
                    (abs(it.second.first) + abs(it.second.second) + abs(it.second.third))
        }.sum()
    }
}
