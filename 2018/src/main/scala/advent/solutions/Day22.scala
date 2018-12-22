package advent.solutions

import advent.util.Util

import scala.collection.immutable.{TreeMap, TreeSet}

object Day22 {

  private var bestTimes = Map[(Int, Int, Char), Int](((0, 0, 'T'), 0)).withDefaultValue(Int.MaxValue)

  def part1(lines: Array[String]): Long = {
    val depth = lines(0).split(" ")(1).toInt
    val target = lines(1).split(" ")(1).split(",")
    val targetX = target(0).toInt
    val targetY = target(1).toInt

    Range(0, targetX + 1).foldLeft(Map[(Int, Int), Long]())((map, x) => {
      Range(0, targetY + 1).foldLeft(map)((map, y) => {
        if (x == 0 && y == 0) map.updated((x, y), 0)
        else if (x == targetX && y == targetY) map.updated((x, y), 0)
        else if (y == 0) map.updated((x, y), (x * 16807 + depth) % 20183)
        else if (x == 0) map.updated((x, y), (y * 48271 + depth) % 20183)
        else map.updated((x, y), (map((x - 1, y)) * map((x, y - 1)) + depth) % 20183)
      })
    }).filter({
      case ((x, y), _) => x <= targetX && y <= targetY
    }).map(x => (x._1, x._2 % 3)).values.sum
  }

  def part2(lines: Array[String]): Int = {
    val depth = lines(0).split(" ")(1).toInt
    val target = lines(1).split(" ")(1).split(",")
    val targetX = target(0).toInt
    val targetY = target(1).toInt

    val map = Range(0, targetX + 1000).foldLeft(Map[(Int, Int), Long]())((map, x) => {
      Range(0, targetY + 1000).foldLeft(map)((map, y) => {
        if (x == 0 && y == 0) map.updated((x, y), 0)
        else if (x == targetX && y == targetY) map.updated((x, y), 0)
        else if (y == 0) map.updated((x, y), (x * 16807 + depth) % 20183)
        else if (x == 0) map.updated((x, y), (y * 48271 + depth) % 20183)
        else map.updated((x, y), (map((x - 1, y)) * map((x, y - 1)) + depth) % 20183)
      })
    }).map(x => (x._1, x._2 % 3))

    Stream.continually().foldLeft(List((0, 0, 'T')))(
      (positions, _) => {
        val sorted = positions.distinct.sortBy(bestTimes(_))
        val (x, y, tool) = sorted.head
        if (x == targetX && y == targetY) {
          return bestTimes((x, y, tool))
        }
        moves(map, targetX, targetY, x, y, tool).foldLeft(sorted.tail)({
          case (acc, (newX, newY, newTool, _)) => acc :+ ((newX, newY, newTool))
        })
      })
    0
  }

  def moves(map: Map[(Int, Int), Long],
            targetX: Int,
            targetY: Int,
            x: Int,
            y: Int,
            tool: Char): List[(Int, Int, Char, Int)] = {
    val time = bestTimes((x, y, tool))
    tryMove(map, targetX, targetY, x, y, x - 1, y, tool, time) ++
      tryMove(map, targetX, targetY, x, y, x + 1, y, tool, time) ++
      tryMove(map, targetX, targetY, x, y, x, y - 1, tool, time) ++
      tryMove(map, targetX, targetY, x, y, x, y + 1, tool, time)
  }

  def tryMove(map: Map[(Int, Int), Long],
              targetX: Int,
              targetY: Int,
              previousX: Int,
              previousY: Int,
              x: Int,
              y: Int,
              tool: Char,
              time: Int): List[(Int, Int, Char, Int)] = {
    if (map.contains((x, y))) {
      var newTool = tool
      var newTime = time
      if (x == targetX && y == targetY && tool != 'T') newTime += 7
      if (validTool(tool, map((x, y)))) {
        newTime += 1
      } else {
        newTool = otherValidTool(tool, map((previousX, previousY)))
        newTime += 8
      }
      if (newTime < bestTimes((x, y, newTool))) {
        bestTimes = bestTimes.updated((x, y, newTool), newTime)
        List((x, y, newTool, newTime))
      } else List()
    } else {
      List()
    }
  }

  def validTool(tool: Char, danger: Long): Boolean = {
    (tool == 'C' && (danger == 0 || danger == 1)) ||
      (tool == 'T' && (danger == 0 || danger == 2)) ||
      (tool == 'N' && (danger == 1 || danger == 2))
  }

  def otherValidTool(tool: Char, danger: Long): Char = {
    if (danger == 0) {
      if (tool == 'C') 'T'
      else 'C'
    } else if (danger == 1) {
      if (tool == 'C') 'N'
      else 'C'
    } else {
      if (tool == 'T') 'N'
      else 'T'
    }
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
