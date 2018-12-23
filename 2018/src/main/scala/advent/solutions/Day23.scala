package advent.solutions

import advent.util.Util

object Day23 {

  def part1(lines: Array[String]): Long = {
    val nanobots = lines.map(line => {
      val split = line.split(", ")
      val positions = split(0).split("pos=<")(1).dropRight(1).split(",")
      (positions(0).toInt, positions(1).toInt, positions(2).toInt, split(1).drop(2).toInt)
    })
    val (maxX, maxY, maxZ, range) = nanobots.maxBy(_._4)
    nanobots.count(nanobot => manhattanDistance(maxX, maxY, maxZ, nanobot._1, nanobot._2, nanobot._3) <= range)
  }

  def part2(lines: Array[String]): Int = {
    val nanobots = lines.map(line => {
      val split = line.split(", ")
      val positions = split(0).split("pos=<")(1).dropRight(1).split(",")
      (positions(0).toInt, positions(1).toInt, positions(2).toInt, split(1).drop(2).toInt)
    })
    val xs = nanobots.map(_._1)
    val ys = nanobots.map(_._2)
    val zs = nanobots.map(_._3)
    var step = 2
    while (step < xs.max - xs.min) step *= 2
    Stream.continually().foldLeft((xs.min, xs.max, ys.min, ys.max, zs.min, zs.max, step))({
      case ((minX, maxX, minY, maxY, minZ, maxZ, currentStep), _) =>
        val (_, distance, point) =
          Range(minX, maxX, currentStep).foldLeft((0, 0, (0, 0, 0)))((best, x) =>
            Range(minY, maxY, currentStep).foldLeft(best)((best, y) =>
              Range(minZ, maxZ, currentStep).foldLeft(best)((best, z) => {
                val distance = manhattanDistance(x, y, z, 0, 0, 0)
                val nanobotCount = nanobots.count(nanobot =>
                  (manhattanDistance(x, y, z, nanobot._1, nanobot._2, nanobot._3) - nanobot._4) / currentStep <= 0)
                if (nanobotCount > best._1) (nanobotCount, distance, (x, y, z))
                else if (nanobotCount == best._1 && distance < best._2) (nanobotCount, distance, (x, y, z))
                else best
              })))
        if (currentStep == 1) return distance
        else {
          (point._1 - currentStep, point._1 + currentStep,
            point._2 - currentStep, point._2 + currentStep,
            point._3 - currentStep, point._3 + currentStep,
            currentStep / 2)
        }
    })
    0
  }

  def manhattanDistance(x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int): Int =
    Math.abs(x2 - x1) + Math.abs(y2 - y1) + Math.abs(z2 - z1)

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
