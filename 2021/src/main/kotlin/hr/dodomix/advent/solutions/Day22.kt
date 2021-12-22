package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.valueOf
import kotlin.math.max
import kotlin.math.min

class Day22 {
    fun dayDirectory() = "day22"

    fun part1(input: List<String>): BigInteger {
        val cuboids = input.map { parse(it) }.mapNotNull {
            it.calculateOverlap(Cuboid(false, -50, 50, -50, 50, -50, 50))
        }
        return cuboids.fold(listOf<Cuboid>()) { cuboidsToCount, newCuboid ->
            if (newCuboid.turningOn) {
                cuboidsToCount + cuboidsToCount.mapNotNull { cuboid ->
                    cuboid.calculateOverlap(newCuboid, true)
                } + newCuboid
            } else {
                cuboidsToCount + cuboidsToCount.mapNotNull { cuboid ->
                    cuboid.calculateOverlap(newCuboid, true)
                }
            }
        }.sumOf { it.countCubes() }
    }

    fun part2(input: List<String>): BigInteger {
        val cuboids = input.map { parse(it) }
        return cuboids.fold(listOf<Cuboid>()) { cuboidsToCount, newCuboid ->
            if (newCuboid.turningOn) {
                cuboidsToCount + cuboidsToCount.mapNotNull { cuboid ->
                    cuboid.calculateOverlap(newCuboid, true)
                } + newCuboid
            } else {
                cuboidsToCount + cuboidsToCount.mapNotNull { cuboid ->
                    cuboid.calculateOverlap(newCuboid, true)
                }
            }
        }.sumOf { it.countCubes() }
    }

    private fun parse(line: String) =
        "(on|off) x=(.*)\\.\\.(.*),y=(.*)\\.\\.(.*),z=(.*)\\.\\.(.*)".toRegex().matchEntire(line)!!.groupValues
            .drop(1).let {
                Cuboid(
                    it[0] == "on", it[1].toInt(), it[2].toInt(), it[3].toInt(),
                    it[4].toInt(), it[5].toInt(), it[6].toInt()
                )
            }

    private data class Cuboid(val turningOn: Boolean, val x1: Int, val x2: Int, val y1: Int, val y2: Int, val z1: Int, val z2: Int) {
        fun calculateOverlap(cuboid: Cuboid, negated: Boolean = false): Cuboid? {
            if (isOutside(this.x1, this.x2, cuboid.x1, cuboid.x2) ||
                isOutside(this.y1, this.y2, cuboid.y1, cuboid.y2) ||
                isOutside(this.z1, this.z2, cuboid.z1, cuboid.z2)
            ) {
                return null
            }
            return Cuboid(
                if (negated) !this.turningOn else this.turningOn,
                max(this.x1, cuboid.x1), min(this.x2, cuboid.x2),
                max(this.y1, cuboid.y1), min(this.y2, cuboid.y2),
                max(this.z1, cuboid.z1), min(this.z2, cuboid.z2)
            )
        }

        fun countCubes(): BigInteger {
            return (if (turningOn) ONE else -ONE) * valueOf(x2 - x1 + 1L) * valueOf(y2 - y1 + 1L) * valueOf(z2 - z1 + 1L)
        }

        private fun isOutside(x1: Int, x2: Int, x3: Int, x4: Int): Boolean {
            return (x1 < x3 && x2 < x3) || (x1 > x4 && x2 > x4)
        }
    }
}
