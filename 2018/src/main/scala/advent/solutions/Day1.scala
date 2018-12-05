package advent.solutions

import advent.util.Util

object Day1 {

  def part1(input: Array[String]): Int = input.map(_.toInt).sum

  def part2(input: Array[String]): Option[Int] = {
    Stream.continually(input.toStream.map(_.toInt)).flatten.foldLeft((Set[Int](), 0)) { case ((results, prev), x) =>
      val result = prev + x
      if (results(result)) return Some(result) else (results + result, result)
    }
    None
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))).get)
  }

}
