package advent.solutions

import advent.util.Util

import scala.collection.immutable.{TreeMap, TreeSet}

object Day7 {

  private val WORKER_COUNT = 5

  def part1(lines: Array[String]): Any = {
    val (input, letters) = parseInput(lines)
    Stream.continually().foldLeft("")((result, _) => {
      result + letters.filter(!result.contains(_))
        .find(letter => input.filter(_._2.equals(letter)).forall(letters => result.contains(letters._1)))
        .orElse(return result)
        .get
    })
  }

  def part2(lines: Array[String]): Any = {
    val (input, letters) = parseInput(lines)
    Stream.from(0).foldLeft(("", TreeMap[String, Int]()))({ case ((result, beingDone), time) =>
      if (result.length == letters.size) return time
      (beingDone.filter(_._2 == time).foldLeft(result)(_ + _._1),
        letters.filter(!result.contains(_))
          .filter(!beingDone.contains(_))
          .filter(letter => input.filter(_._2.equals(letter)).forall(letters => result.contains(letters._1)))
          .foldLeft(beingDone)((acc, letter) =>
            if (beingDone.size < WORKER_COUNT) acc.updated(letter, time + 60 + letter.charAt(0).compareTo('A'))
            else acc).filter(_._2 != time))
    })
  }

  def parseInput(lines: Array[String]): (Array[(String, String)], TreeSet[String]) = {
    val input = lines.map(line => {
      val split = line.split(" ")
      (split(1), split(7))
    })
    (input, input.foldLeft(TreeSet[String]())((acc, letters) => acc + letters._1 + letters._2))
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
