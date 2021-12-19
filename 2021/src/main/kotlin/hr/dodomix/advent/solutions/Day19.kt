package hr.dodomix.advent.solutions

import kotlin.math.abs

class Day19 {
    fun dayDirectory() = "day19"

    fun part1(input: List<String>): Int {
        val scanners = parseScanners(input)

        val firstScanner = scanners[0]
        val fixedScanners = mutableSetOf(firstScanner)
        firstScanner.fixedBeacons = firstScanner.beacons
        while (fixedScanners.size != scanners.size) {
            for (scanner in scanners.filter { currentScanner -> fixedScanners.none { it == currentScanner } }) {
                tryToFixScanner(fixedScanners, scanner)
            }
        }

        return fixedScanners.flatMap { it.fixedBeacons!! }.toSet().size
    }

    fun part2(input: List<String>): Int {
        val scanners = parseScanners(input)

        val firstScanner = scanners[0]
        val fixedScanners = mutableSetOf(firstScanner)
        firstScanner.fixedBeacons = firstScanner.beacons
        firstScanner.translation = Triple(0, 0, 0)
        while (fixedScanners.size != scanners.size) {
            for (scanner in scanners.filter { currentScanner -> fixedScanners.none { it == currentScanner } }) {
                tryToFixScanner(fixedScanners, scanner)
            }
        }

        return fixedScanners.maxOf { scanner ->
            fixedScanners.maxOf { scanner2 ->
                val translation1 = scanner.translation!!
                val translation2 = scanner2.translation!!
                abs(translation2.first - translation1.first) + abs(translation2.second - translation1.second) +
                        abs(translation2.third - translation1.third)
            }
        }
    }

    private fun parseScanners(input: List<String>): List<Scanner> {
        val scanners = input.fold(Pair(listOf<Scanner>(), null as List<Beacon>?)) { (scanners, currentBeacons), line ->
            if (line.startsWith("---")) {
                Pair(scanners, emptyList())
            } else if (line.isEmpty()) {
                Pair(scanners + Scanner(currentBeacons!!), null)
            } else {
                Pair(scanners, currentBeacons!! + Beacon(line))
            }
        }.first
        return scanners
    }

    private fun tryToFixScanner(fixedScanners: MutableSet<Scanner>, scanner: Scanner) {
        val fixedBeacons = fixedScanners.flatMap { it.fixedBeacons!! }.toSet()
        scanner.beaconRotations().forEach { rotatedBeacons ->
            fixedBeacons.forEach { fixedBeacon ->
                rotatedBeacons.forEach { beaconRotation ->
                    val translation = Triple(
                        fixedBeacon.x - beaconRotation.x,
                        fixedBeacon.y - beaconRotation.y,
                        fixedBeacon.z - beaconRotation.z
                    )
                    val translatedBeacons = translate(rotatedBeacons, translation)
                    if (translatedBeacons.map { fixedBeacons.contains(it) }.count { it } >= 12) {
                        scanner.fixedBeacons = translatedBeacons
                        scanner.translation = translation
                        fixedScanners.add(scanner)
                        return
                    }
                }
            }
        }
    }

    private fun translate(beacons: List<Beacon>, translation: Triple<Int, Int, Int>): List<Beacon> {
        return beacons.map { Beacon(it.x + translation.first, it.y + translation.second, it.z + translation.third) }
    }

    private data class Beacon(val x: Int, val y: Int, val z: Int) {
        constructor(line: String) : this(
            line.split(",")[0].toInt(),
            line.split(",")[1].toInt(), line.split(",")[2].toInt()
        )

        fun rotations(): List<Beacon> = listOf(
            Beacon(x, y, z),
            Beacon(x, y, -z),
            Beacon(x, -y, z),
            Beacon(x, -y, -z),
            Beacon(-x, y, z),
            Beacon(-x, y, -z),
            Beacon(-x, -y, z),
            Beacon(-x, -y, -z),
            Beacon(x, z, y),
            Beacon(x, z, -y),
            Beacon(x, -z, y),
            Beacon(x, -z, -y),
            Beacon(-x, z, y),
            Beacon(-x, z, -y),
            Beacon(-x, -z, y),
            Beacon(-x, -z, -y),
            Beacon(y, x, z),
            Beacon(y, x, -z),
            Beacon(y, -x, z),
            Beacon(y, -x, -z),
            Beacon(-y, x, z),
            Beacon(-y, x, -z),
            Beacon(-y, -x, z),
            Beacon(-y, -x, -z),
            Beacon(y, z, x),
            Beacon(y, z, -x),
            Beacon(y, -z, x),
            Beacon(y, -z, -x),
            Beacon(-y, z, x),
            Beacon(-y, z, -x),
            Beacon(-y, -z, x),
            Beacon(-y, -z, -x),
            Beacon(z, x, y),
            Beacon(z, x, -y),
            Beacon(z, -x, y),
            Beacon(z, -x, -y),
            Beacon(-z, x, y),
            Beacon(-z, x, -y),
            Beacon(-z, -x, y),
            Beacon(-z, -x, -y),
            Beacon(z, y, x),
            Beacon(z, y, -x),
            Beacon(z, -y, x),
            Beacon(z, -y, -x),
            Beacon(-z, y, x),
            Beacon(-z, y, -x),
            Beacon(-z, -y, x),
            Beacon(-z, -y, -x)
        )

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Beacon) return false

            if (x != other.x) return false
            if (y != other.y) return false
            if (z != other.z) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            result = 31 * result + z
            return result
        }
    }

    private data class Scanner(
        val beacons: List<Beacon>, var fixedBeacons: List<Beacon>? = null,
        var translation: Triple<Int, Int, Int>? = null
    ) {
        fun beaconRotations(): List<List<Beacon>> {
            val rotations = beacons.map { it.rotations() }
            return rotations.drop(1)
                .fold(rotations[0].map { listOf(it) }) { acc, currentRotations ->
                    acc.zip(currentRotations).map { (accumulatedRotations, rotation) -> accumulatedRotations + rotation }
                }
        }
    }
}
