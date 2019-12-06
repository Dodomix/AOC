package hr.dodomix.advent.solutions

class Day6 {
    fun dayDirectory() = "day6"

    data class OrbitingObject(
        val name: String,
        val orbitingAround: MutableList<OrbitingObject>
    )

    fun part1(input: List<String>): Int {
        val objects = constructOrbitingObjectList(input)

        return objects.values.fold(0) { counter, orbitingObject -> counter + countOrbits(orbitingObject) }
    }

    private fun constructOrbitingObjectList(input: List<String>): MutableMap<String, OrbitingObject> {
        val objects = mutableMapOf<String, OrbitingObject>()
        input.forEach { line ->
            val values = line.split(')')
            objects.putIfAbsent(values[0], OrbitingObject(values[0], mutableListOf()))

            if (objects.containsKey(values[1])) {
                objects.getValue(values[1]).orbitingAround.add(objects.getValue(values[0]))
            }
            objects.putIfAbsent(values[1], OrbitingObject(values[1], mutableListOf(objects.getValue(values[0]))))
        }
        return objects
    }

    private fun countOrbits(orbitingObject: OrbitingObject): Int =
        if (orbitingObject.orbitingAround.isNotEmpty()) orbitingObject.orbitingAround.fold(0) { counter, orbitingAround ->
            counter + 1 + countOrbits(orbitingAround)
        } else 0

    private fun findOrbit(currentObject: OrbitingObject, goalObject: OrbitingObject): Int {
        if (currentObject == goalObject) return 0
        if (currentObject.orbitingAround.isEmpty()) return -1
        return 1 + (currentObject.orbitingAround
            .map { findOrbit(it, goalObject) }
            .filter { it != -1 }
            .min() ?: -2)
    }

    fun part2(input: List<String>): Int {
        val objects = constructOrbitingObjectList(input)

        val youOrbiting = objects.getValue("YOU").orbitingAround[0]
        val sanOrbiting = objects.getValue("SAN").orbitingAround[0]

        var currentOrbitingObjects = listOf(sanOrbiting)
        var steps = 0
        while (true) {
            val distances = currentOrbitingObjects.map { findOrbit(youOrbiting, it) }.filter { it != -1 }
            if (distances.isEmpty()) {
                steps += 1
                currentOrbitingObjects = currentOrbitingObjects.flatMap { it.orbitingAround }
            } else {
                return steps + (distances.min() ?: -1)
            }
        }
    }
}
