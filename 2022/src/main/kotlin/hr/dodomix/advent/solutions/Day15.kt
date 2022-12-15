package hr.dodomix.advent.solutions

import hr.dodomix.advent.util.Util.Position
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class Day15 {
    fun dayDirectory() = "day15"

    fun part1(input: List<String>, resultRow: Int = 2000000): Int {
        val sensorBeacons = parseBeaconsAndSensors(input)

        return sensorBeacons.fold(setOf<Int>()) { positions, (sensor, beacon) ->
            val sensorBeaconDistance = abs(sensor.row - beacon.row) + abs(sensor.column - beacon.column)
            val resultRowDistance = abs(sensor.row - resultRow)
            val availableDistance = sensorBeaconDistance - resultRowDistance
            if (availableDistance >= 0) {
                positions + (sensor.column - availableDistance..sensor.column + availableDistance)
                    .filterNot { column ->
                        beacon.row == resultRow && beacon.column == column
                    }
            } else {
                positions
            }
        }.size
    }

    fun part2(input: List<String>, searchSpace: Int = 4000000): Long {
        val sensorBeacons = parseBeaconsAndSensors(input)

        val rangeMap = sensorBeacons.fold(mapOf<Int, List<Range>>()) { ranges, (sensor, beacon) ->
            val sensorBeaconDistance = abs(sensor.row - beacon.row) + abs(sensor.column - beacon.column)
            ranges + (max(0, sensor.row - sensorBeaconDistance)..min(searchSpace, sensor.row + sensorBeaconDistance))
                .associateWith { row ->
                    val resultRowDistance = abs(sensor.row - row)
                    val availableDistance = sensorBeaconDistance - resultRowDistance
                    Range(
                        max(0, sensor.column - availableDistance),
                        min(searchSpace, sensor.column + availableDistance)
                    )
                }.filterValues { it.start <= it.stop }
                .mapValues { (row, rangesForRow) ->
                    ranges.getOrDefault(row, emptyList()) + rangesForRow
                }
        }
        (0..searchSpace).forEach { row ->
            val ranges = rangeMap.getValue(row)
            val uncoveredRange = ranges.find { range ->
                val possibleColumn = min(searchSpace, max(0, range.start - 1))
                ranges.find { otherRange ->
                    possibleColumn >= otherRange.start && possibleColumn <= otherRange.stop
                } == null
            }
            if (uncoveredRange != null) {
                return row + (uncoveredRange.start - 1) * 4000000L
            }
        }
        return -1
    }

    private fun parseBeaconsAndSensors(input: List<String>): List<SensorBeacon> {
        val sensorBeacons = input.map { line ->
            val (sensorX, sensorY, beaconX, beaconY) =
                "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
                    .find(line)!!
                    .groupValues
                    .drop(1)
                    .map { it.toInt() }
            SensorBeacon(Position(sensorY, sensorX), Position(beaconY, beaconX))
        }
        return sensorBeacons
    }

    private data class SensorBeacon(val sensor: Position, val beacon: Position)

    private data class Range(val start: Int, val stop: Int)
}
