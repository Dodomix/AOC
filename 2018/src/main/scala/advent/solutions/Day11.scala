package advent.solutions

import advent.util.Util

object Day11 {

  def part1(lines: Array[String]): (Int, Int) = {
    val serialNumber = lines(0).toInt
    val grid = Range(0, 301).foldLeft(Map[(Int, Int), Int]())((grid, i) =>
      Range(0, 301).foldLeft(grid)((grid, j) => grid.updated((i, j), calculatePower(i, j, serialNumber))))
    Range(0, 298).foldLeft(((0, 0), Int.MinValue))({ case ((position, max), i) =>
      Range(0, 298).foldLeft((position, max))((bestPosition, j) => {
        val positionValue = grid(i, j) + grid(i + 1, j) + grid(i + 2, j) +
          grid(i, j + 1) + grid(i + 1, j + 1) + grid(i + 2, j + 1) +
          grid(i, j + 2) + grid(i + 1, j + 2) + grid(i + 2, j + 2)
        if (positionValue > bestPosition._2) ((i, j), positionValue)
        else bestPosition
      })
    })._1
  }

  def part2(lines: Array[String]): (Int, Int, Int) = {
    val serialNumber = lines(0).toInt
    val grid = Range(0, 301).foldLeft(Map[(Int, Int), Int]())((grid, i) => {
      Range(0, 301).foldLeft(grid)((grid, j) => grid.updated((i, j), calculatePower(i, j, serialNumber)))
    })
    Range(0, 301).foldRight(Map[(Int, Int, Int), Int]())((i, results) =>
      Range(0, 301).foldRight(results)((j, results) =>
        Range(1, Math.min(300 - i, 300 - j) + 2).foldLeft(results)((results, size) => {
          var positionValue = grid(i, j)
          if (size != 1)
            positionValue += results(i + 1, j + 1, size - 1) +
              Range(1, size).foldLeft(0)((value, position) => value + grid(i + position, j) + grid(i, j + position))
          results.updated((i, j, size), positionValue)
        })
      )).maxBy(_._2)._1
  }

  def calculatePower(i: Int, j: Int, serialNumber: Int): Int = {
    val rackId = i + 10
    ((rackId * j + serialNumber) * rackId % 1000) / 100 - 5
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
