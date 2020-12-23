package hr.dodomix.advent.solutions

import java.math.BigInteger


class Day23 {
    fun dayDirectory() = "day23"

    fun part1(input: List<String>): String {
        val cups = (input[0].map { it.toString().toInt() }).map { LinkedListNode(it) }
        cups.forEachIndexed { index, node ->
            node.next = cups[(index + 1) % cups.size]
        }

        var cup = moveCups(cups, 100)

        while (cup.value != 1) {
            cup = cup.next
        }
        cup = cup.next
        var cupOrder = ""
        while (cup.value != 1) {
            cupOrder += cup.value
            cup = cup.next
        }
        return cupOrder
    }

    fun part2(input: List<String>): BigInteger {
        val cups = (input[0].map { it.toString().toInt() } + (10..1_000_000)).map { LinkedListNode(it) }
        cups.forEachIndexed { index, node ->
            node.next = cups[(index + 1) % cups.size]
        }

        var cup = moveCups(cups, 10_000_000)

        while (cup.value != 1) {
            cup = cup.next
        }
        return cup.next.value.toBigInteger() * cup.next.next.value.toBigInteger()
    }

    private fun moveCups(cups: List<LinkedListNode>, repetitionCount: Int): LinkedListNode {
        val cupsSize = cups.size
        val cupValueMap = cups.map {
            Pair(it.value, it)
        }.toMap()
        return (0 until repetitionCount).fold(cups[0]) { cupHead, _ ->
            val movedCups = listOf(cupHead.next, cupHead.next.next, cupHead.next.next.next)
            var newCupValue = cupHead.value
            do {
                newCupValue -= 1
                if (newCupValue <= 0) {
                    newCupValue += cupsSize
                }
            } while (movedCups.any { it.value == newCupValue })
            val newCup = cupValueMap.getValue(newCupValue)
            val oldNewCupNext = newCup.next
            newCup.next = movedCups[0]
            cupHead.next = movedCups[2].next
            movedCups[2].next = oldNewCupNext

            cupHead.next
        }
    }

    data class LinkedListNode(val value: Int) {
        lateinit var next: LinkedListNode
    }
}
