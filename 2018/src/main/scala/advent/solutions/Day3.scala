package advent.solutions

import advent.util.Util

object Day3 {

  def part1(input: Array[String]): Int = {
    input.map(line => {
      val split = line.split(" ")
      val position = split(2).split(",")
      val size = split(3).split("x")
      (position(0).toInt, position(1).substring(0, position(1).length - 1).toInt, size(0).toInt - 1, size(1).toInt - 1)
    }).foldLeft(Map[String, Int]().withDefaultValue(0))({ case (squares, (x, y, width, height)) =>
      var newSquares = squares
      for (i <- x to (x + width)) {
        for (j <- y to (y + height)) {
          newSquares = newSquares.updated(i + " " + j, newSquares(i + " " + j) + 1)
        }
      }
      newSquares
    }).values.count(_ >= 2)
  }

  def part2(input: Array[String]): Any = {
    val squares = input.map(line => {
      val split = line.split(" ")
      val position = split(2).split(",")
      val size = split(3).split("x")
      (split(0), position(0).toInt, position(1).substring(0, position(1).length - 1).toInt, size(0).toInt - 1, size(1).toInt - 1)
    })
    squares.foreach({ case (id1, x1, y1, width1, height1) =>
      if (!squares.filter(_._1 != id1).foldLeft(false)({ case (overlaps, (_, x2, y2, width2, height2)) =>
        if ((x1 > x2 && x1 > x2 + width2) || (x2 > x1 && x2 > x1 + width1)) overlaps
        else if ((y1 > y2 && y1 > y2 + height2) || (y2 > y1 && y2 > y1 + height1)) overlaps
        else true
      })) return id1
    })
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
