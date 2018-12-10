package advent.solutions

import advent.util.Util

object Day10 {

  def part1(lines: Array[String]): Unit = {
    val parsedInput = lines.map(line => {
      val split1 = line.split("<")
      val split2 = split1(1).split(", ")
      val split3 = split1(2).split(", ")
      (split2(0).trim.toInt,
        split2(1).trim.split(">")(0).toInt,
        split3(0).trim.toInt,
        split3(1).trim.split(">")(0).toInt)
    })
    Stream.continually().foldLeft((parsedInput, 0))({ case ((input, time), _) => {
      if (input.maxBy(_._1)._1 - input.minBy(_._1)._1 < 100) { // heuristic
        println(time)
        printMessage(input)
      }
      (input.map({
        case (x, y, velX, velY) => (x + velX, y + velY, velX, velY)
      }), time + 1)
    }
    })
  }

  def printMessage(input: Array[(Int, Int, Int, Int)]): Unit = {
    Range(input.minBy(_._2)._2 - 1, input.maxBy(_._2)._2 + 1).foreach(j => {
      Range(input.minBy(_._1)._1 - 1, input.maxBy(_._1)._1 + 1).foreach(i => {
        if (input.exists(point => point._1 == i && point._2 == j)) print("#")
        else print(".")
      })
      println()
    })
    println()
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
  }

}
