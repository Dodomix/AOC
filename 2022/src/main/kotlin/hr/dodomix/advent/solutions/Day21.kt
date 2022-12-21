package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO


class Day21 {

    fun dayDirectory() = "day21"

    fun part1(input: List<String>): Long {
        val monkeys = input.associate { line ->
            val name = line.substring(0, 4)
            val restOfString = line.substring(6)
            if (restOfString.length <= 4) {
                Pair(name, SimpleMonkey(BigInteger.valueOf(restOfString.toLong())))
            } else {
                val (monkey1, operator, monkey2) = "(....) (.) (....)".toRegex()
                    .find(line)!!
                    .groupValues
                    .drop(1)
                Pair(name, OperationMonkey(monkey1, monkey2, operator.first()))
            }
        }
        return monkeys.getValue("root").getValue(monkeys).longValueExact()
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.associate { line ->
            val name = line.substring(0, 4)
            val restOfString = line.substring(6)
            if (name == "humn") {
                Pair(name, Human())
            } else if (restOfString.length <= 4) {
                Pair(name, SimpleMonkey(BigInteger.valueOf(restOfString.toLong())))
            } else {
                val (monkey1, operator, monkey2) = "(....) (.) (....)".toRegex()
                    .find(line)!!
                    .groupValues
                    .drop(1)
                if (name == "root") {
                    Pair(name, OperationMonkey(monkey1, monkey2, '='))
                } else {
                    Pair(name, OperationMonkey(monkey1, monkey2, operator.first()))
                }
            }
        }
        val human = monkeys.getValue("humn") as Human
        val root = monkeys.getValue("root")
        var min = ZERO
        var max = BigInteger.valueOf(Long.MAX_VALUE)
        while (true) {
            var mid = (max + min) / BigInteger.TWO
            human.value = mid
            val resultMid = root.getValue(monkeys)
            if (resultMid == ZERO) {
                while (root.getValue(monkeys) == ZERO) {
                    mid -= ONE
                    human.value = mid
                }
                return (mid + ONE).longValueExact()
            }

            if (resultMid > ZERO) {
                min = mid
            } else {
                max = mid
            }
        }
    }

    private interface Monkey {
        fun getValue(monkeys: Map<String, Monkey>): BigInteger

        fun toString(monkeys: Map<String, Monkey>): String
    }

    private data class SimpleMonkey(val value: BigInteger) : Monkey {
        override fun getValue(monkeys: Map<String, Monkey>): BigInteger = value

        override fun toString(monkeys: Map<String, Monkey>): String {
            return value.toString()
        }
    }

    private data class OperationMonkey(val monkey1: String, val monkey2: String, val operator: Char) : Monkey {
        override fun getValue(monkeys: Map<String, Monkey>): BigInteger {
            val value1 = monkeys.getValue(monkey1).getValue(monkeys)
            val value2 = monkeys.getValue(monkey2).getValue(monkeys)
            return when (operator) {
                '+' -> value1 + value2
                '-' -> value1 - value2
                '/' -> value1 / value2
                '*' -> value1 * value2
                '=' -> value1 - value2
                else -> throw RuntimeException("Unknown operator")
            }
        }

        override fun toString(monkeys: Map<String, Monkey>): String {
            return "${monkeys.getValue(monkey1).toString(monkeys)} $operator ${
                monkeys.getValue(monkey2).toString(monkeys)
            }"
        }
    }

    private data class Human(var value: BigInteger = ZERO) : Monkey {
        override fun getValue(monkeys: Map<String, Monkey>): BigInteger = value

        override fun toString(monkeys: Map<String, Monkey>): String {
            return "humn"
        }
    }
}
