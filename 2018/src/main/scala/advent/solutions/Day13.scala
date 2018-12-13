package advent.solutions

import advent.util.Util

import scala.collection.immutable.TreeMap

object Day13 {

  def part1(lines: Array[String]): (Int, Int) = {
    val (trackMap, cartPositions, _) = lines.foldLeft((Map[(Int, Int), Char]().withDefaultValue(' '),
      TreeMap[(Int, Int), (Int, Char)](), 0))({
      case ((map, carts, i), line) =>
        val lineResult = line.foldLeft((map, carts, 0))({ case ((lineMap, lineCarts, j), char) =>
          if (char == '>') (lineMap.updated((i, j), '-'), lineCarts.updated((i, j), (0, '>')), j + 1)
          else if (char == '<') (lineMap.updated((i, j), '-'), lineCarts.updated((i, j), (0, '<')), j + 1)
          else if (char == '^') (lineMap.updated((i, j), '|'), lineCarts.updated((i, j), (0, '^')), j + 1)
          else if (char == 'v') (lineMap.updated((i, j), '|'), lineCarts.updated((i, j), (0, 'v')), j + 1)
          else if (char == ' ') (lineMap, lineCarts, j + 1)
          else (lineMap.updated((i, j), char), lineCarts, j + 1)
        })
        (lineResult._1, lineResult._2, i + 1)
    })
    Stream.continually().foldLeft((cartPositions, trackMap))({ case ((carts, map), _) =>
      (carts.foldLeft(TreeMap[(Int, Int), (Int, Char)]())({
        case (updatedCarts, ((i, j), (intersections, direction))) =>
          val nextPosition = if (direction == '>') (i, j + 1)
          else if (direction == '<') (i, j - 1)
          else if (direction == '^') (i - 1, j)
          else (i + 1, j)
          val nextLocation = map(nextPosition)
          if (updatedCarts.contains(nextPosition)) return (nextPosition._2, nextPosition._1)
          else if (nextLocation == '+') {
            if (direction == '>') {
              if (intersections % 3 == 0) updatedCarts.updated(nextPosition, (intersections + 1, '^'))
              else if (intersections % 3 == 1) updatedCarts.updated(nextPosition, (intersections + 1, '>'))
              else updatedCarts.updated(nextPosition, (intersections + 1, 'v'))
            } else if (direction == 'v') {
              if (intersections % 3 == 0) updatedCarts.updated(nextPosition, (intersections + 1, '>'))
              else if (intersections % 3 == 1) updatedCarts.updated(nextPosition, (intersections + 1, 'v'))
              else updatedCarts.updated(nextPosition, (intersections + 1, '<'))
            } else if (direction == '<') {
              if (intersections % 3 == 0) updatedCarts.updated(nextPosition, (intersections + 1, 'v'))
              else if (intersections % 3 == 1) updatedCarts.updated(nextPosition, (intersections + 1, '<'))
              else updatedCarts.updated(nextPosition, (intersections + 1, '^'))
            } else {
              if (intersections % 3 == 0) updatedCarts.updated(nextPosition, (intersections + 1, '<'))
              else if (intersections % 3 == 1) updatedCarts.updated(nextPosition, (intersections + 1, '^'))
              else updatedCarts.updated(nextPosition, (intersections + 1, '>'))
            }
          } else if (nextLocation == '/') {
            if (direction == '>') updatedCarts.updated(nextPosition, (intersections, '^'))
            else if (direction == 'v') updatedCarts.updated(nextPosition, (intersections, '<'))
            else if (direction == '<') updatedCarts.updated(nextPosition, (intersections, 'v'))
            else updatedCarts.updated(nextPosition, (intersections, '>'))
          } else if (nextLocation == '\\') {
            if (direction == '>') updatedCarts.updated(nextPosition, (intersections, 'v'))
            else if (direction == 'v') updatedCarts.updated(nextPosition, (intersections, '>'))
            else if (direction == '<') updatedCarts.updated(nextPosition, (intersections, '^'))
            else updatedCarts.updated(nextPosition, (intersections, '<'))
          } else {
            updatedCarts.updated(nextPosition, (intersections, direction))
          }
      }), map)
    })
    (0, 0)
  }

  def part2(lines: Array[String]): Any = {
    0
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
