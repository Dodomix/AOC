package advent.solutions

import advent.util.Util

object Day17 {

  class MapUnit(var unitType: Char, var hp: Int, var damage: Int, var i: Int, var j: Int)

  def part1(lines: Array[String]): Int = {
    val result = run(lines)
    result._1.count(position => position._2 >= result._3 && position._2 < result._4)
  }

  def part2(lines: Array[String]): Int = {
    run(lines)._2.count({ case ((_, _), c) => c == '~' })
  }

  def run(lines: Array[String]): (Set[(Int, Int)], Map[(Int, Int), Char], Int, Int) = {
    val map = lines.map(line => {
      val x = (("x=\\d+\\.?\\.?\\d*" r) findFirstIn line).get.substring(2)
      val y = (("y=\\d+\\.?\\.?\\d*" r) findFirstIn line).get.substring(2)
      if (x.contains(".")) {
        val xSplit = x.split("\\.\\.")
        (Range(xSplit(0).toInt, xSplit(1).toInt + 1), Range(y.toInt, y.toInt + 1))
      } else if (y.contains(".")) {
        val ySplit = y.split("\\.\\.")
        (Range(x.toInt, x.toInt + 1), Range(ySplit(0).toInt, ySplit(1).toInt + 1))
      } else {
        (Range(x.toInt, x.toInt + 1), Range(y.toInt, y.toInt + 1))
      }
    }).foldLeft(Map[(Int, Int), Char]().withDefaultValue('.'))({
      case (worldMap, (xRange, yRange)) =>
        xRange.foldLeft(worldMap)((worldMap, x) =>
          yRange.foldLeft(worldMap)((worldMap, y) =>
            worldMap.updated((x, y), '#')))
    }).updated((500, 0), '+')
    val minY = map.minBy(_._1._2)._1._2
    val maxY = map.maxBy(_._1._2)._1._2
    var filledMap = map
    Stream.continually().foldLeft((Set((500, 0, 'f')), Set[(Int, Int)]()))({
      case ((positions, usedPositions), _) =>
        if (positions.isEmpty) {
          return (usedPositions, filledMap, minY, maxY)
        }
        positions.foldLeft((Set[(Int, Int, Char)](), usedPositions))({
          case ((openPositions, used), (i, j, action)) =>
            if (j < minY || j > maxY) (openPositions, used)
            else if (action == 'f') {
              if (filledMap((i, j + 1)) == '.') (openPositions + ((i, j + 1, 'f')), used + ((i, j + 1)))
              else (openPositions + ((i, j, 'r')), used)
            } else {
              var usedPositions = used
              var newOpenPositions = List((i, j - 1, 'r'))

              var left = i - 1
              while (filledMap((left, j)) == '.' && filledMap((left, j + 1)) != '.') {
                usedPositions += ((left, j))
                filledMap = filledMap.updated((left, j), '~')
                left -= 1
              }
              var right = i + 1
              while (filledMap((right, j)) == '.' && filledMap((right, j + 1)) != '.') {
                usedPositions += ((right, j))
                filledMap = filledMap.updated((right, j), '~')
                right += 1
              }

              if (filledMap((left, j + 1)) == '.' || filledMap((right, j + 1)) == '.') {
                Range(left + 1, right).foreach(x => filledMap = filledMap.updated((x, j), '.'))
                newOpenPositions = List()
                if (filledMap((left, j + 1)) == '.') {
                  newOpenPositions :+= (left, j + 1, 'f')
                  usedPositions += ((left, j + 1))
                  usedPositions += ((left, j))
                }
                if (filledMap((right, j + 1)) == '.') {
                  newOpenPositions :+= (right, j + 1, 'f')
                  usedPositions += ((right, j + 1))
                  usedPositions += ((right, j))
                }
              } else {
                filledMap = filledMap.updated((i, j), '~')
                usedPositions += ((i, j))
                usedPositions += ((i, j - 1))
              }
              (openPositions ++ newOpenPositions, usedPositions)
            }
        })
    })
    (Set(), filledMap, minY, maxY)
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
