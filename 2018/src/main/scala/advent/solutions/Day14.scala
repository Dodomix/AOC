package advent.solutions

import advent.util.Util

import scala.collection.immutable.TreeMap

object Day14 {

  def part1(lines: Array[String]): String = {
    val input = lines(0).toInt
    val initialGrades = Array(3, 7)

    Stream.continually().foldLeft((initialGrades, 0, 1))({
      case ((grades, first, second), _) =>
        if (grades.length == input + 10) {
          return grades.drop(grades.length - 10).mkString
        } else if (grades.length > input + 10) {
          return grades.drop(grades.length - 11).dropRight(1).mkString
        }
        val newGrade = grades(first) + grades(second)
        if (newGrade < 10)
          (grades :+ newGrade,
            (first + grades(first) + 1) % (grades.length + 1),
            (second + grades(second) + 1) % (grades.length + 1))
        else
          (grades :+ (newGrade / 10) :+ (newGrade % 10),
            (first + grades(first) + 1) % (grades.length + 2),
            (second + grades(second) + 1) % (grades.length + 2))
    })
    ""
  }

  def part2(lines: Array[String]): Int = {
    val input = lines(0).split("").map(_.toInt)
    val initialGrades = Map[Int, Int]((0, 3), (1, 7))

    Stream.continually().foldLeft((initialGrades, 0, 1))({
      case ((grades, first, second), _) =>
        if (grades.size >= 6 &&
          ((grades(grades.size - 1) == input(5) &&
            grades(grades.size - 2) == input(4) &&
            grades(grades.size - 3) == input(3) &&
            grades(grades.size - 4) == input(2) &&
            grades(grades.size - 5) == input(1) &&
            grades(grades.size - 6) == input(0)) ||
            (grades(grades.size - 2) == input(5) &&
              grades(grades.size - 3) == input(4) &&
              grades(grades.size - 4) == input(3) &&
              grades(grades.size - 5) == input(2) &&
              grades(grades.size - 6) == input(1) &&
              grades(grades.size - 7) == input(0))))
          return grades.size - 6
        val newGrade = grades(first) + grades(second)
        if (newGrade < 10)
          (grades.updated(grades.size, newGrade),
            (first + grades(first) + 1) % (grades.size + 1),
            (second + grades(second) + 1) % (grades.size + 1))
        else
          (grades.updated(grades.size, newGrade / 10).updated(grades.size + 1, newGrade % 10),
            (first + grades(first) + 1) % (grades.size + 2),
            (second + grades(second) + 1) % (grades.size + 2))
    })
    0
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
