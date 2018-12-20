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

  def findAllPathLengths(line: String): (Int, List[Int]) = {
    Stream.continually().foldLeft((0, List(0), List[Int]()))({
      case ((i, currentPaths, paths), _) =>
        val char = line(i)
        if (char == '|') (i + 1, List(0), paths ++ currentPaths)
        else if (char == ')') {
          return (i + 2, paths ++ currentPaths)
        }
        else if (char != '(') (i + 1, currentPaths.map(_ + 1), paths)
        else {
          val (newI, newPaths) = findAllPathLengths(line.substring(i + 1))
          (i + newI, newPaths.foldLeft(List[Int]())((acc, path) => acc ++ currentPaths.map(_ + path)), paths)
        }
    })
    (0, List[Int]())
  }

  def part2(lines: Array[String]): Int = {
    val line = lines(0).drop(1).dropRight(1)
    Stream.continually().foldLeft((0, List(0)))({
      case ((i, paths), _) =>
        if (i >= line.length) {
          return paths.count(_ >= 1000)
        }
        val char = line(i)
        if (char != '(') (i + 1, paths.map(_ + 1))
        else {
          val (newI, newPaths) = findAllPathLengths(line.substring(i + 1))
          (i + newI, newPaths.foldLeft(List[Int]())((acc, path) => acc ++ paths.map(_ + path)))
        }
    })
    0
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
