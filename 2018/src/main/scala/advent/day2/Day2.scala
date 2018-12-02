package advent.day2

import advent.util.Util

object Day2 {

  def part1(input: Array[String]): Int = {
    var twos = 0
    var threes = 0
    input.foreach(id => {
      val occurrences = id.groupBy(identity).mapValues(_.length)
      if (occurrences.values.exists(_ == 2)) twos += 1
      if (occurrences.values.exists(_ == 3)) threes += 1
    })
    twos * threes
  }

  def part2(input: Array[String]): Any = {
    input.foreach(id1 => {
      input.foreach(id2 => {
        if (id1.zip(id2).count(pair => pair._1 != pair._2) == 1)
          return id1.zip(id2).filter(pair => pair._1 == pair._2).map(_._1).mkString
      })
    })
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
