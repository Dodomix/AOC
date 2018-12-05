package advent.day5

import advent.util.Util

object Day5 {

  def part1(input: Array[String]): Any = {
    react(input(0)).length
  }

  def part2(input: Array[String]): Int = {
    val initial = react(input(0))
    initial.foldLeft((initial.length, Set[Char]()))({
      case ((minLength, usedChars), c) =>
        if (!usedChars.contains(c.toLower))
          (Math.min(react(initial.filter(c1 => !c1.equals(c) && !c1.toLower.equals(c))).length, minLength),
            usedChars + c.toLower)
        else (minLength, usedChars)
    })._1
  }

  def react(input: String): String = {
    var inputString = input
    do {
      val result = inputString.foldLeft("")({
        case (acc, c) =>
          if (acc.isEmpty) acc :+ c
          else if (!acc.last.equals(c) && (acc.last.equals(c.toLower) || acc.last.toLower.equals(c))) acc.dropRight(1)
          else acc :+ c
      })
      if (result.length == inputString.length) return result
      else inputString = result
    } while (true)
    inputString
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
