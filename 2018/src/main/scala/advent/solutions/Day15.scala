package advent.solutions

import advent.util.Util

object Day15 {

  class MapUnit(var unitType: Char, var hp: Int, var damage: Int, var i: Int, var j: Int)

  def part1(lines: Array[String]): Int = {
    val (round, units) = runRound(lines, 3)
    round * units.filter(_.hp > 0).foldLeft(0)(_ + _.hp)
  }

  def part2(lines: Array[String]): Int = {
    Stream.continually().foldLeft(4)((damage, _) => {
      val (round, units) = runRound(lines, damage)
      if (!units.exists(unit => unit.unitType == 'E' && unit.hp <= 0))
        return round * units.filter(_.hp > 0).foldLeft(0)(_ + _.hp)
      damage + 1
    })
    0
  }

  def runRound(lines: Array[String], elfDamage: Int): (Int, List[MapUnit]) = {
    val (map, units) = parseInput(lines, elfDamage)
    Stream.continually().foldLeft((0, units))({
      case ((round, currentUnits), _) =>
        currentUnits.foreach(unit => {
          if (unit.hp > 0) {
            val hostiles = currentUnits.filter(currentUnit => currentUnit.hp > 0 && currentUnit.unitType != unit.unitType)
            if (hostiles.isEmpty) {
              return (round, currentUnits)
            }
            if (!tryAttack(unit, hostiles)) {
              val (closestHostiles, steps) = getClosestHostiles(unit, units, map)
              if (closestHostiles.nonEmpty) {
                val step = computeNextStep(unit, closestHostiles, steps)
                unit.i = step._1
                unit.j = step._2
                tryAttack(unit, hostiles)
              }
            }
          }
        })
        (round + 1, currentUnits.sortWith((u1, u2) => u1.i < u2.i || (u1.i == u2.i && u1.j < u2.j)))
    })
  }

  def parseInput(lines: Array[String], elfDamage: Int): (Map[(Int, Int), Char], List[MapUnit]) = {
    val parsedInput = lines.foldLeft((Map[(Int, Int), Char]().withDefaultValue('#'),
      List[MapUnit](), 0))({
      case ((currentMap1, currentUnits1, i), line) =>
        val lineResult = line.foldLeft((currentMap1, currentUnits1, 0))({
          case ((currentMap2, currentUnits2, j), char) =>
            if (char == '.') (currentMap2.updated((i, j), '.'), currentUnits2, j + 1)
            else if (char == 'G') {
              (currentMap2.updated((i, j), '.'),
                currentUnits2 :+ new MapUnit(char, 200, 3, i, j),
                j + 1)
            } else if (char == 'E') {
              (currentMap2.updated((i, j), '.'),
                currentUnits2 :+ new MapUnit(char, 200, elfDamage, i, j),
                j + 1)
            } else (currentMap2, currentUnits2, j + 1)
        })
        (lineResult._1, lineResult._2, i + 1)
    })
    (parsedInput._1, parsedInput._2)
  }

  def tryAttack(unit: MapUnit, hostiles: List[MapUnit]): Boolean = {
    val attackTargets = hostiles.filter(hostile =>
      (hostile.i == unit.i + 1 && hostile.j == unit.j) ||
        (hostile.i == unit.i && hostile.j == unit.j + 1) ||
        (hostile.i == unit.i && hostile.j == unit.j - 1) ||
        (hostile.i == unit.i - 1 && hostile.j == unit.j))
    if (attackTargets.nonEmpty) {
      val minHp = attackTargets.minBy(_.hp).hp
      val lowHpTargets = attackTargets.filter(_.hp == minHp)
        .sortWith((u1, u2) => u1.i < u2.i || (u1.i == u2.i && u1.j < u2.j))
      lowHpTargets.head.hp -= unit.damage
      true
    } else false
  }

  def getClosestHostiles(unit: MapUnit,
                         currentUnits: List[MapUnit],
                         map: Map[(Int, Int), Char]): (List[(Int, Int, Int)], Map[(Int, Int), (Int, Int)]) = {
    val hostiles = currentUnits.filter(currentUnit => currentUnit.hp > 0 && currentUnit.unitType != unit.unitType)
    var units = List((unit.i, unit.j, 0))
    var steps = Map[(Int, Int), (Int, Int)]()
    var closestHostiles = List[(Int, Int, Int)]()
    while (units.nonEmpty) {
      val (i, j, distance) = units.head
      units = units.tail
      if (closestHostiles.isEmpty || distance < closestHostiles.head._3) {
        List[(Int, Int)]((i - 1, j), (i, j - 1), (i, j + 1), (i + 1, j))
          .filter(x => map(x) != '#' && !steps.contains(x))
          .foreach(x => {
            if (hostiles.exists(hostile => hostile.i == x._1 && hostile.j == x._2)) {
              if (closestHostiles.isEmpty || closestHostiles.head._3 == distance + 1) {
                closestHostiles :+= (x._1, x._2, distance + 1)
              }
            } else if (!currentUnits.exists(unit => unit.hp > 0 && unit.i == x._1 && unit.j == x._2)) {
              units :+= (x._1, x._2, distance + 1)
            }
            steps = steps.updated(x, (i, j))
          })
      }
    }
    (closestHostiles, steps)
  }

  def computeNextStep(unit: MapUnit,
                      closestHostiles: List[(Int, Int, Int)],
                      steps: Map[(Int, Int), (Int, Int)]): (Int, Int) = {
    val closestHostile = closestHostiles.min
    var step = (closestHostile._1, closestHostile._2)
    var previousStep = steps(step)
    while (previousStep._1 != unit.i || previousStep._2 != unit.j) {
      step = previousStep
      previousStep = steps(step)
    }
    step
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
