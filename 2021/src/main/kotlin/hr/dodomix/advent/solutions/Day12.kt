package hr.dodomix.advent.solutions

class Day12 {
    fun dayDirectory() = "day12"

    fun part1(input: List<String>): Int {
        val nodeMap = constructNodeMap(input)

        return visitNode(nodeMap.getValue("start"), emptySet())
    }

    fun part2(input: List<String>): Int {
        val nodeMap = constructNodeMap(input)

        return visitNodeMultiple(nodeMap.getValue("start"), emptyMap())
    }


    private fun constructNodeMap(input: List<String>): Map<String, Node> {
        val nodeMap = input.fold(mapOf<String, Node>()) { nodes, line ->
            val (nameNode1, nameNode2) = line.split("-")
            val node1 = if (nodes.containsKey(nameNode1)) {
                nodes.getValue(nameNode1)
            } else {
                Node(nameNode1)
            }
            val node2 = if (nodes.containsKey(nameNode2)) {
                nodes.getValue(nameNode2)
            } else {
                Node(nameNode2)
            }
            node1.adjacentNodes.add(node2)
            node2.adjacentNodes.add(node1)
            nodes + Pair(nameNode1, node1) + Pair(nameNode2, node2)
        }
        return nodeMap
    }

    private fun visitNode(node: Node, visitedNodes: Set<String>): Int {
        if (node.name.lowercase() == node.name && node.name in visitedNodes) {
            return 0
        }
        if (node.name == "end") {
            return 1
        }
        return node.adjacentNodes.sumOf {
            visitNode(it, visitedNodes + node.name)
        }
    }

    private fun visitNodeMultiple(node: Node, visitedNodes: Map<String, Int>): Int {
        if ((node.name == "start" && visitedNodes.isNotEmpty()) ||
            visitedNodes.any { it.value > 2 } ||
            visitedNodes.count { it.value > 1 } > 1
        ) {
            return 0
        }
        if (node.name == "end") {
            return 1
        }
        return node.adjacentNodes.sumOf {
            if (node.name == node.name.lowercase()) {
                visitNodeMultiple(it, visitedNodes + Pair(node.name, visitedNodes.getOrDefault(node.name, 0) + 1))
            } else {
                visitNodeMultiple(it, visitedNodes)
            }
        }
    }

    data class Node(val name: String) {
        val adjacentNodes: MutableSet<Node> = mutableSetOf()
    }
}
