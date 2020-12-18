package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.util.Stack


class Day18 {
    fun dayDirectory() = "day18"

    fun part1(input: List<String>): BigInteger {
        return input.map { infixToPostfix(it, false) }.map { line ->
            line.fold(emptyList<BigInteger>()) { values, c ->
                when {
                    c.isDigit() -> values + c.toString().toBigInteger()
                    c == '+' -> values.dropLast(2) + (values.last() + values[values.size - 2])
                    c == '*' -> values.dropLast(2) + (values.last() * values[values.size - 2])
                    else -> values
                }
            }.last()
        }.sumOf { it }
    }

    fun part2(input: List<String>): BigInteger {
        return input.map { infixToPostfix(it, true) }.map { line ->
            line.fold(emptyList<BigInteger>()) { values, c ->
                when {
                    c.isDigit() -> values + c.toString().toBigInteger()
                    c == '+' -> values.dropLast(2) + (values.last() + values[values.size - 2])
                    c == '*' -> values.dropLast(2) + (values.last() * values[values.size - 2])
                    else -> values
                }
            }.last()
        }.sumOf { it }
    }

    private fun infixToPostfix(exp: String, usePrecedance: Boolean): String {
        var result = ""
        val stack = Stack<Char>()

        for (c in exp) {
            when {
                c.isDigit() -> result += c
                c == '(' -> stack.push(c)
                c == ')' -> {
                    while (stack.peek() != '(') {
                        result += stack.pop()
                    }
                    stack.pop()
                }
                c != ' ' -> {
                    while (!stack.isEmpty() && stack.peek() != '(' &&
                        (!usePrecedance || (c != '+' || stack.peek() != '*'))
                    ) {
                        result += stack.pop()
                    }
                    stack.push(c)
                }
            }
        }

        while (!stack.isEmpty()) {
            result += stack.pop()
        }
        return result
    }
}
