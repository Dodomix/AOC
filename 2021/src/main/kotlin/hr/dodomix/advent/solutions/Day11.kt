package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Location

class Day11 {
    fun dayDirectory() = "day11"

    fun part1(input: List<String>): Int {
        val octopi = parseOctopi(input).toMap()
        val maxRow = octopi.maxOf { it.key.row }
        val maxColumn = octopi.maxOf { it.key.column }
        return (0 until 100).fold(Pair(octopi, 0)) { (currentOctopi, flashes), _ ->
            val mutableOctopi = currentOctopi.toMutableMap()
            val flashesInStep = handleOctopiStep(mutableOctopi, maxRow, maxColumn)

            Pair(mutableOctopi.toMap(), flashes + flashesInStep)
        }.second
    }

    fun part2(input: List<String>): Int {
        val octopi = parseOctopi(input).toMap()
        val maxRow = octopi.maxOf { it.key.row }
        val maxColumn = octopi.maxOf { it.key.column }
        generateSequence(1) { it + 1 }.fold(octopi) { currentOctopi, step ->
            val mutableOctopi = currentOctopi.toMutableMap()
            val flashesInStep = handleOctopiStep(mutableOctopi, maxRow, maxColumn)
            if (flashesInStep == currentOctopi.size) {
                return step
            }

            mutableOctopi.toMap()
        }
        return -1
    }

    private fun parseOctopi(input: List<String>) = input.flatMapIndexed { row, line ->
        line.split("").filter { it.isNotEmpty() }.mapIndexed { column, c ->
            Pair(Location(row, column), Octopus(c.toInt()))
        }
    }

    private fun handleOctopiStep(octopi: MutableMap<Location, Octopus>, maxRow: Int, maxColumn: Int): Int {
        (0..maxRow).forEach { row ->
            (0..maxColumn).forEach { column ->
                updateOctopusEnergy(octopi, Location(row, column))
            }
        }

        val flashesInStep = octopi.count { it.value.flashed }

        (0..maxRow).forEach { row ->
            (0..maxColumn).forEach { column ->
                val location = Location(row, column)
                val octopus = octopi.getValue(location)
                if (octopus.flashed) {
                    octopi[location] = Octopus(0)
                }
            }
        }
        return flashesInStep
    }

    private fun octopusFlashed(octopi: MutableMap<Location, Octopus>, location: Location) {
        val octopus = octopi.getValue(location)
        octopi[location] = Octopus(octopus.energy, true)
        updateOctopusEnergy(octopi, Location(location.row - 1, location.column - 1))
        updateOctopusEnergy(octopi, Location(location.row - 1, location.column))
        updateOctopusEnergy(octopi, Location(location.row - 1, location.column + 1))
        updateOctopusEnergy(octopi, Location(location.row, location.column - 1))
        updateOctopusEnergy(octopi, Location(location.row, location.column + 1))
        updateOctopusEnergy(octopi, Location(location.row + 1, location.column - 1))
        updateOctopusEnergy(octopi, Location(location.row + 1, location.column))
        updateOctopusEnergy(octopi, Location(location.row + 1, location.column + 1))
    }

    private fun updateOctopusEnergy(octopi: MutableMap<Location, Octopus>, location: Location) {
        if (octopi.containsKey(location)) {
            val octopus = octopi.getValue(location)
            octopi[location] = Octopus(octopus.energy + 1, octopus.flashed)
            if (octopus.energy >= 9 && !octopus.flashed) {
                octopusFlashed(octopi, location)
            }
        }
    }

    data class Octopus(val energy: Int, val flashed: Boolean = false)
}
