package advent.solutions

import advent.util.Util

import scala.collection.immutable.TreeMap

object Day20 {

  class MapUnit(var unitType: Char, var hp: Int, var damage: Int, var i: Int, var j: Int)

  def part1(lines: Array[String]): Int = {
    val line = lines(0).drop(1).dropRight(1)
    Stream.continually().foldLeft((0, 0))({
      case ((i, longestPathLength), _) =>
        if (i >= line.length) return longestPathLength
        val char = line(i)
        if (char != '(') (i + 1, longestPathLength + 1)
        else {
          val (newI, longestPath) = findLongestPath(line.substring(i + 1))
          (i + newI, longestPathLength + longestPath.length)
        }
    })
    0
  }

  def findLongestPath(line: String): (Int, String) = {
    Stream.continually().foldLeft((0, "", List[String]()))({
      case ((i, currentPath, paths), _) =>
        val char = line(i)
        if (char == '|') (i + 1, "", paths :+ currentPath)
        else if (char == ')') {
          if (paths.contains("") || currentPath == "") return (i + 2, "")
          else return (i + 2, (paths :+ currentPath).maxBy(_.length))
        }
        else if (char != '(') (i + 1, currentPath + char, paths)
        else {
          val (newI, longestPath) = findLongestPath(line.substring(i + 1))
          (i + newI, currentPath + longestPath, paths)
        }
    })
    (0, "")
  }

  def findAllPathLengths(line: String): Int = {
    line.foldLeft((Map[(Int, Int), Int](((0, 0), 0)).withDefaultValue(Int.MaxValue), List[(Int, Int)](), (0, 0)))({
      case ((distances, stack, (x, y)), c) =>
        if (c == 'N') {
          val newLocation = (x, y - 1)
          (distances.updated(newLocation, Math.min(distances(newLocation), distances((x, y)) + 1)), stack, newLocation)
        } else if (c == 'W') {
          val newLocation = (x - 1, y)
          (distances.updated(newLocation, Math.min(distances(newLocation), distances((x, y)) + 1)), stack, newLocation)
        } else if (c == 'E') {
          val newLocation = (x + 1, y)
          (distances.updated(newLocation, Math.min(distances(newLocation), distances((x, y)) + 1)), stack, newLocation)
        } else if (c == 'S') {
          val newLocation = (x, y + 1)
          (distances.updated(newLocation, Math.min(distances(newLocation), distances((x, y)) + 1)), stack, newLocation)
        } else if (c == '|') {
          (distances, stack, stack.head)
        } else if (c == '(') {
          (distances, (x, y) +: stack, (x, y))
        } else if (c == ')') {
          (distances, stack.tail, stack.head)
        } else {
          (distances, stack, (x, y))
        }
    })._1.count(_._2 >= 1000)
  }

  def part2(lines: Array[String]): Int = {
    val line = lines(0).drop(1).dropRight(1)
    findAllPathLengths(line)
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
