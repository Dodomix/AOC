package hr.dodomix.advent.solutions

import hr.dodomix.advent.solutions.Day2.Shape.Companion.PAPER
import hr.dodomix.advent.solutions.Day2.Shape.Companion.ROCK
import hr.dodomix.advent.solutions.Day2.Shape.Companion.SCISSORS

class Day2 {
    fun dayDirectory() = "day2"

    fun part1(input: List<String>): Int {
        val shapeMap = mapOf(
            Pair("A", ROCK),
            Pair("B", PAPER),
            Pair("C", SCISSORS),
            Pair("X", ROCK),
            Pair("Y", PAPER),
            Pair("Z", SCISSORS),
        )
        return input.fold(0) { score, line ->
            val (opponent, you) = line.split(" ")
                .map { shapeMap.getValue(it) }
            score + scoreRockPaperScissors(opponent, you)
        }
    }

    fun part2(input: List<String>): Int {
        val shapeMap = mapOf(
            Pair("A", ROCK),
            Pair("B", PAPER),
            Pair("C", SCISSORS)
        )
        return input.fold(0) { score, line ->
            val (opponent, result) = line.split(" ")
            val opponentShape = shapeMap.getValue(opponent)
            val myShape = when (result) {
                "X" ->
                    if (opponentShape.defeats(ROCK)) ROCK else if (opponentShape.defeats(SCISSORS)) SCISSORS else PAPER

                "Y" ->
                    if (opponentShape == ROCK) ROCK else if (opponentShape == SCISSORS) SCISSORS else PAPER

                "Z" ->
                    if (ROCK.defeats(opponentShape)) ROCK else if (SCISSORS.defeats(opponentShape)) SCISSORS else PAPER

                else -> throw IllegalArgumentException("Unknown value: $result")
            }
            score + scoreRockPaperScissors(opponentShape, myShape)
        }
    }

    private fun scoreRockPaperScissors(opponentShape: Shape, myShape: Shape) = myShape.score +
            if (myShape == opponentShape) 3
            else if (myShape.defeats(opponentShape)) 6
            else 0

    private data class Shape(val score: Int, var shapeItDefeats: Shape?) {

        fun defeats(otherShape: Shape) = otherShape == shapeItDefeats

        companion object {
            val ROCK = Shape(1, null)
            val PAPER = Shape(2, ROCK)
            val SCISSORS = Shape(3, PAPER)

            init {
                ROCK.shapeItDefeats = SCISSORS
            }
        }
    }
}
