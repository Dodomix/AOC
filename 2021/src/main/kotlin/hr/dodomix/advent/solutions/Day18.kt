package hr.dodomix.advent.solutions

import kotlin.math.max

class Day18 {
    fun dayDirectory() = "day18"

    fun part1(input: List<String>): Int {
        val snailPairs = input.map { it.parseToSnailPair() }
        val first = snailPairs[0]
        return snailPairs.drop(1).fold(first) { acc, number -> acc + number }.calculateMagnitude()
    }

    fun part2(input: List<String>): Int {
        val snailPairs = input.map { it.parseToSnailPair() }
        // should have made them immutable :)
        return snailPairs.indices.maxOf { i ->
            snailPairs.indices.maxOf { j ->
                if (i == j) {
                    0
                } else {
                    max(
                        (input[i].parseToSnailPair() + input[j].parseToSnailPair()).calculateMagnitude(),
                        (input[j].parseToSnailPair() + input[i].parseToSnailPair()).calculateMagnitude()
                    )
                }
            }
        }
    }

    private fun String.parseToSnailPair(): SnailNumber {
        this.fold(listOf<Pair<SnailNumber, Boolean>>()) { stack, c ->
            when (c) {
                '[' -> stack + Pair(SnailPair(if (stack.isEmpty()) null else stack.last().first, null, null), false)
                ',' -> stack.dropLast(1) + Pair(stack.last().first, true)
                ']' -> {
                    if (stack.size == 1) {
                        return stack[0].first
                    } else {
                        val newStack = stack.dropLast(1)
                        val (last, _) = stack.last()
                        val (previousValue, right) = newStack.last()
                        if (right) {
                            previousValue.right = last
                        } else {
                            previousValue.left = last
                        }
                        newStack
                    }
                }
                else -> {
                    val (previousValue, right) = stack.last()
                    val value = BasicValue(previousValue, "$c".toInt())
                    if (right) {
                        previousValue.right = value
                    } else {
                        previousValue.left = value
                    }
                    stack
                }
            }
        }
        throw RuntimeException("Invalid snail number $this")
    }

    private operator fun SnailNumber.plus(other: SnailNumber): SnailNumber {
        val result = SnailPair(null, this, other)
        this.parent = result
        other.parent = result
        return result.reduce()
    }

    private fun SnailNumber.reduce(): SnailNumber {
        if (this.left == null && this.right == null) {
            return this
        }

        while (true) {
            var explosionDone = false
            val valuesAsList = this.addToList()
            for (index in valuesAsList.indices) {
                val value = valuesAsList[index]
                if (value.depth() == 5) {
                    explosionDone = true
                    val parent = value.parent!!
                    if (index != 0) {
                        valuesAsList[index - 1].value += (parent.left!! as BasicValue).value
                    }
                    if (index != valuesAsList.lastIndex - 1) {
                        valuesAsList[index + 2].value += (parent.right!! as BasicValue).value
                    }
                    val grandParent = parent.parent!!
                    if (grandParent.left == parent) {
                        grandParent.left = BasicValue(grandParent, 0)
                    } else {
                        grandParent.right = BasicValue(grandParent, 0)
                    }
                    break
                }
            }
            var splitDone = false
            if (!explosionDone) {
                splitDone = this.split() != null
            }
            if (!explosionDone && !splitDone) {
                return this
            }
        }
    }

    private abstract class SnailNumber(var parent: SnailNumber?, var left: SnailNumber?, var right: SnailNumber?) {
        abstract fun calculateMagnitude(): Int

        abstract fun split(): SnailPair?

        abstract fun addToList(): List<BasicValue>

        fun depth(): Int = if (parent != null) 1 + parent!!.depth() else 0
    }

    private class BasicValue(parent: SnailNumber?, var value: Int) : SnailNumber(parent, null, null) {
        override fun calculateMagnitude() = value

        override fun split(): SnailPair? {
            return if (value >= 10) {
                val leftSplitValue = BasicValue(null, value / 2)
                val rightSplitValue = BasicValue(null, (value + 1) / 2)
                val executedSplit = SnailPair(parent, leftSplitValue, rightSplitValue)
                leftSplitValue.parent = executedSplit
                rightSplitValue.parent = executedSplit
                if (parent!!.left == this) {
                    parent!!.left = executedSplit
                } else {
                    parent!!.right = executedSplit
                }
                executedSplit
            } else {
                null
            }
        }

        override fun addToList() = listOf(this)

        override fun toString(): String {
            return value.toString()
        }
    }

    private class SnailPair(parent: SnailNumber?, left: SnailNumber?, right: SnailNumber?) : SnailNumber(parent, left, right) {
        override fun calculateMagnitude() = 3 * left!!.calculateMagnitude() + 2 * right!!.calculateMagnitude()

        override fun split(): SnailPair? = left!!.split() ?: right!!.split()

        override fun addToList() = left!!.addToList() + right!!.addToList()

        override fun toString(): String {
            return "[${left.toString()},${right.toString()}]"
        }
    }
}
