package advent.solutions

import advent.util.Util

object Day6 {

  private val MAX_DISTANCE = 10000

  def part1(lines: Array[String]): Int = {
    val input = lines.map(line => {
      val split = line.split(", ")
      (split(0).toInt, split(1).toInt)
    })
    val (maxX, maxY) = input.foldLeft((0, 0))({ case ((currX, currY), (x, y)) =>
      (Math.max(x, currX), Math.max(y, currY))
    })
    Range(-1, maxX + 2).foldLeft(Map[(Int, Int), Int]().withDefaultValue(0))((acc, i) => {
      Range(-1, maxY + 2).foldLeft(acc)((acc, j) => {
        val (_, point) = input.foldLeft((-1, None : Option[(Int, Int)]))({ case ((distance, closestPoint), (x, y)) =>
          val currentDistance = calculateDistance(i, j, x, y)
          if (distance == -1) (currentDistance, Some((x, y)))
          else if (currentDistance < distance) (currentDistance, Some((x, y)))
          else if (currentDistance == distance) (distance, None)
          else (distance, closestPoint)
        })
        if (point.nonEmpty) {
          if (i == -1 || j == -1 || i == maxX + 1 || j == maxY + 1) acc.updated(point.get, Int.MaxValue)
          else if (acc(point.get) != Int.MaxValue) acc.updated(point.get, acc(point.get) + 1)
          else acc
        } else acc
      })
    }).values.filter(x => x != Int.MaxValue).max
  }

  def part2(lines: Array[String]): Int = {
    val input = lines.map(line => {
      val split = line.split(", ")
      (split(0).toInt, split(1).toInt)
    })
    val (maxX, maxY) = input.foldLeft((0, 0))({ case ((currX, currY), (x, y)) =>
      (Math.max(x, currX), Math.max(y, currY))
    })
    Range(0, maxX + 1).foldLeft(0)((close, i) => {
      Range(0, maxY + 1).foldLeft(close)((close, j) => {
        if (input.foldLeft(0)((distance, point) => distance + calculateDistance(i, j, point._1, point._2)) < MAX_DISTANCE) close + 1
        else close
      })
    })
  }

  def calculateDistance(x1: Int, y1: Int, x2: Int, y2: Int): Int = {
    Math.abs(x2 - x1) + Math.abs(y2 - y1)
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
