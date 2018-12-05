package advent.solutions

import advent.util.Util

import scala.collection.immutable.TreeMap

object Day4 {

  def part1(input: Array[String]): Int = {
    val (guard, (times, _)) = processGuards(input).maxBy(_._2._2)
    guard.substring(1).toInt * times.maxBy(_._2)._1
  }

  def part2(input: Array[String]): Any = {
    val (guard, (times, _)) = processGuards(input).maxBy(_._2._1.maxBy(_._2)._2)
    guard.substring(1).toInt * times.maxBy(_._2)._1
  }

  def processGuards(input: Array[String]) : Map[String, (Map[Int, Int], Int)] = {
    input.map(line => {
      val split = line.split(" ", 3)
      val time = split(1).split(":")
      (split(0).substring(1), time(0).toInt, time(1).substring(0, time(1).length - 1).toInt, split(2))
    }).foldLeft(TreeMap[String, List[(Int, Int, String)]]().withDefaultValue(List()))({
      case (acc, (date, hours, minutes, action)) =>
        acc.updated(date, acc(date) :+ (hours, minutes, action))
    }).foldLeft((Map[String, List[(Int, Int, String)]](), List[(Int, Int, String)]()))({
      case ((acc, prev), (key, cur)) =>
        (acc.updated(key, (cur.filter({ case (hours, _, _) => hours != 23 }) :::
          prev.filter({ case (hours, _, _) => hours == 23 }))
          .sortWith({
            case ((hours1, minutes1, _), (hours2, minutes2, _)) =>
              if (hours1 == 23) true
              else if (hours2 == 23) false
              else minutes1 < minutes2
          })), cur)
    })._1.foldLeft(Map[String, List[Int]]().withDefaultValue(List()))({
      case (acc, (_, times)) =>
        var updatedAcc = acc
        times.foldLeft("")({ case (currentGuard, (_, minutes, action)) =>
          if (action.startsWith("Guard")) action.split(" ")(1)
          else {
            updatedAcc = updatedAcc.updated(currentGuard, updatedAcc(currentGuard) :+ minutes)
            currentGuard
          }
        })
        updatedAcc
    }).foldLeft(Map[String, (Map[Int, Int], Int)]().withDefaultValue((Map[Int, Int]().withDefaultValue(0), 0)))({
      case (acc, (key, times)) =>
        var updatedAcc = acc
        times.foldLeft((0, false))({ case ((timeStarted, sleeping), time) =>
          if (sleeping) {
            var sleepingTimeMap = updatedAcc(key)._1
            for (i <- timeStarted to time) {
              sleepingTimeMap = sleepingTimeMap.updated(i, sleepingTimeMap(i) + 1)
            }
            updatedAcc = updatedAcc.updated(key, (sleepingTimeMap, updatedAcc(key)._2 + (time - timeStarted)))
          }
          (time, !sleeping)
        })
        updatedAcc
    })
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
