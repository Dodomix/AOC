package advent.solutions

import advent.util.Util

object Day25 {

  def part1(lines: Array[String]): Int = {
    val points = lines.map(line => {
      val split = line.split(",")
      (split(0).toInt, split(1).toInt, split(2).toInt, split(3).toInt)
    })
    val constellations = points.foldLeft(List[List[(Int, Int, Int, Int)]]())((constellations, point) => {
      val constellation = constellations
        .find(constellation => constellation.exists(point2 => manhattanDistance(point, point2) <= 3))
      if (constellation.nonEmpty) constellations.map(c => if (c == constellation.get) c :+ point else c)
      else constellations :+ List(point)
    })
    Stream.continually().foldLeft(constellations)((constellations, _) => {
      val newConstellations = constellations.foldLeft(List[List[(Int, Int, Int, Int)]]())((allConstellations, currentConstellation) => {
          val constellation = allConstellations
            .find(constellation =>
              constellation.exists(point2 => currentConstellation.exists(point => manhattanDistance(point, point2) <= 3)))
          if (constellation.nonEmpty) allConstellations.map(c => if (c == constellation.get) c ++ currentConstellation else c)
          else allConstellations :+ currentConstellation
        })
      if (constellations == newConstellations) return constellations.length
      newConstellations
    })
    0
  }

  def manhattanDistance(x: (Int, Int, Int, Int), y: (Int, Int, Int, Int)): Int = {
    math.abs(x._1 - y._1) + math.abs(x._2 - y._2) + math.abs(x._3 - y._3) + math.abs(x._4 - y._4)
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
  }

}
