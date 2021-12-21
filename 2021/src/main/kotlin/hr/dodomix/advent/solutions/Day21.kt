package hr.dodomix.advent.solutions

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class Day21 {
    fun dayDirectory() = "day21"

    private val previousRuns = mutableMapOf<Triple<Player, Player, Boolean>, Pair<BigInteger, BigInteger>>()

    fun part1(input: List<String>): Int {
        val position1 = input[0].split(": ")[1].toInt()
        val position2 = input[1].split(": ")[1].toInt()
        (1..10000).map { ((it - 1) % 100) + 1 }.chunked(3).map { it.sum() }
            .foldIndexed(Triple(Player(0, position1), Player(0, position2), true))
            { i, (player1, player2, firstPlayer), diceResult ->
                if (player1.score >= 1000) {
                    return player2.score * i * 3
                } else if (player2.score >= 1000) {
                    return player1.score * i * 3
                }
                val newPosition = if (firstPlayer) {
                    ((player1.position - 1 + diceResult) % 10) + 1
                } else {
                    ((player2.position - 1 + diceResult) % 10) + 1
                }
                Triple(
                    if (firstPlayer) Player(player1.score + newPosition, newPosition) else player1,
                    if (firstPlayer) player2 else Player(player2.score + newPosition, newPosition),
                    !firstPlayer
                )
            }
        return -1
    }

    fun part2(input: List<String>): BigInteger {
        val position1 = input[0].split(": ")[1].toInt()
        val position2 = input[1].split(": ")[1].toInt()
        val result = playDirac(Player(0, position1), Player(0, position2), true)
        return if (result.first > result.second) {
            result.first
        } else {
            result.second
        }
    }

    private fun playDirac(player1: Player, player2: Player, firstPlayer: Boolean): Pair<BigInteger, BigInteger> {
        if (previousRuns.containsKey(Triple(player1, player2, firstPlayer))) {
            return previousRuns.getValue(Triple(player1, player2, firstPlayer))
        }
        if (player1.score >= 21) {
            return Pair(ONE, ZERO)
        } else if (player2.score >= 21) {
            return Pair(ZERO, ONE)
        }
        val result = (1..3).flatMap { diceResult1 ->
            (1..3).flatMap { diceResult2 ->
                (1..3).map { diceResult3 ->
                    val diceResult = diceResult1 + diceResult2 + diceResult3
                    val newPosition = if (firstPlayer) {
                        ((player1.position - 1 + diceResult) % 10) + 1
                    } else {
                        ((player2.position - 1 + diceResult) % 10) + 1
                    }
                    playDirac(
                        if (firstPlayer) Player(player1.score + newPosition, newPosition) else player1,
                        if (firstPlayer) player2 else Player(player2.score + newPosition, newPosition),
                        !firstPlayer
                    )
                }
            }
        }.fold(Pair(ZERO, ZERO)) { acc, result ->
            Pair(acc.first + result.first, acc.second + result.second)
        }
        previousRuns[Triple(player1, player2, firstPlayer)] = result
        return result
    }

    private data class Player(val score: Int, val position: Int)
}
