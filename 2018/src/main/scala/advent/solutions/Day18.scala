package advent.solutions

import advent.util.Util

object Day18 {

  def part1(lines: Array[String]): Int = {
    val (areaMap, size) = lines.foldLeft((Map[(Int, Int), Char]().withDefaultValue('.'), 0))({
      case ((map, i), line) =>
        (line.foldLeft((map, 0))({
          case ((innerMap, j), c) => (innerMap.updated((i, j), c), j + 1)
        })._1, i + 1)
    })
    val finalMap = Range(0, 10).foldLeft(areaMap)((areaMap, _) => {
      Range(0, size).foldLeft(areaMap)((partialMap, i) => {
        Range(0, size).foldLeft(partialMap)((partialMap, j) => {
          if (areaMap((i, j)) == '.') {
            if (List(areaMap((i - 1, j - 1)), areaMap((i - 1, j)),
              areaMap((i - 1, j + 1)), areaMap((i, j - 1)),
              areaMap((i, j + 1)), areaMap((i + 1, j - 1)),
              areaMap((i + 1, j)), areaMap((i + 1, j + 1))).count(_ == '|') >= 3) partialMap.updated((i, j), '|')
            else partialMap
          } else if (areaMap((i, j)) == '|') {
            if (List(areaMap((i - 1, j - 1)), areaMap((i - 1, j)),
              areaMap((i - 1, j + 1)), areaMap((i, j - 1)),
              areaMap((i, j + 1)), areaMap((i + 1, j - 1)),
              areaMap((i + 1, j)), areaMap((i + 1, j + 1))).count(_ == '#') >= 3) partialMap.updated((i, j), '#')
            else partialMap
          } else {
            val adjacent = List(areaMap((i - 1, j - 1)), areaMap((i - 1, j)),
              areaMap((i - 1, j + 1)), areaMap((i, j - 1)),
              areaMap((i, j + 1)), areaMap((i + 1, j - 1)),
              areaMap((i + 1, j)), areaMap((i + 1, j + 1)))
            if (adjacent.count(_ == '|') >= 1 && adjacent.count(_ == '#') >= 1) partialMap
            else partialMap.updated((i, j), '.')
          }
        })
      })
    })
    finalMap.count(_._2 == '|') * finalMap.count(_._2 == '#')
  }

  def part2(lines: Array[String]): Int = {
    val (initialMap, size) = lines.foldLeft((Map[(Int, Int), Char]().withDefaultValue('.'), 0))({
      case ((map, i), line) =>
        (line.foldLeft((map, 0))({
          case ((innerMap, j), c) => (innerMap.updated((i, j), c), j + 1)
        })._1, i + 1)
    })
    Range(0, 1000000000).foldLeft(initialMap, Map[Map[(Int, Int), Char], Int]())({
      case ((areaMap, allMaps), iteration) =>
        val resultMap = Range(0, size).foldLeft(areaMap)((partialMap, i) => {
          Range(0, size).foldLeft(partialMap)((partialMap, j) => {
            if (areaMap((i, j)) == '.') {
              if (List(areaMap((i - 1, j - 1)), areaMap((i - 1, j)),
                areaMap((i - 1, j + 1)), areaMap((i, j - 1)),
                areaMap((i, j + 1)), areaMap((i + 1, j - 1)),
                areaMap((i + 1, j)), areaMap((i + 1, j + 1))).count(_ == '|') >= 3) partialMap.updated((i, j), '|')
              else partialMap
            } else if (areaMap((i, j)) == '|') {
              if (List(areaMap((i - 1, j - 1)), areaMap((i - 1, j)),
                areaMap((i - 1, j + 1)), areaMap((i, j - 1)),
                areaMap((i, j + 1)), areaMap((i + 1, j - 1)),
                areaMap((i + 1, j)), areaMap((i + 1, j + 1))).count(_ == '#') >= 3) partialMap.updated((i, j), '#')
              else partialMap
            } else {
              val adjacent = List(areaMap((i - 1, j - 1)), areaMap((i - 1, j)),
                areaMap((i - 1, j + 1)), areaMap((i, j - 1)),
                areaMap((i, j + 1)), areaMap((i + 1, j - 1)),
                areaMap((i + 1, j)), areaMap((i + 1, j + 1)))
              if (adjacent.count(_ == '|') >= 1 && adjacent.count(_ == '#') >= 1) partialMap
              else partialMap.updated((i, j), '.')
            }
          })
        })
        if (allMaps.contains(resultMap)) {
          println(allMaps.get(resultMap))
          println(iteration)
        }
        if (iteration == 579) {
          return resultMap.count(_._2 == '|') * resultMap.count(_._2 == '#')
        }
        (resultMap, allMaps.updated(resultMap, iteration))
    })
    0
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
