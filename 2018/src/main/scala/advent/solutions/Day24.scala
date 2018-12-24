package advent.solutions

import advent.util.Util

object Day24 {

  class MapUnit(var unitType: String,
                var unitCount: Int,
                var unitHp: Int,
                var weaknesses: List[Char],
                var immunities: List[Char],
                var attack: (Int, Char),
                var initiative: Int,
                var target: MapUnit = null,
                var targeted: Boolean = false)

  def part1(lines: Array[String]): Int = {
    val immuneSystemGroups = List(
      new MapUnit("immune", 457, 4941, List(), List(), (98, 's'), 14),
      new MapUnit("immune", 35, 3755, List('c'), List('f', 'b'), (1021, 'b'), 1),
      new MapUnit("immune", 6563, 7155, List(), List('r', 'b'), (10, 'c'), 11),
      new MapUnit("immune", 5937, 1868, List('s'), List(), (2, 'f'), 2),
      new MapUnit("immune", 422, 7279, List(), List('r', 'c'), (170, 'b'), 16),
      new MapUnit("immune", 279, 9375, List('c'), List(), (333, 's'), 10),
      new MapUnit("immune", 4346, 3734, List('c'), List('f', 's'), (8, 'b'), 9),
      new MapUnit("immune", 1564, 1596, List(), List(), (8, 'b'), 19),
      new MapUnit("immune", 361, 1862, List('b', 's'), List(), (40, 'b'), 3),
      new MapUnit("immune", 427, 8025, List('c'), List('f', 's'), (139, 'b'), 7)
    ).sortBy(-_.initiative)
    val infectionGroups = List(
      new MapUnit("infection", 27, 24408, List('b', 'f'), List(), (1505, 'c'), 5),
      new MapUnit("infection", 137, 29784, List(), List('s'), (423, 'c'), 12),
      new MapUnit("infection", 1646, 15822, List('s', 'c'), List('f'), (16, 'f'), 6),
      new MapUnit("infection", 1803, 10386, List(), List('c', 'r'), (11, 'b'), 17),
      new MapUnit("infection", 2216, 39081, List(), List(), (28, 'b'), 20),
      new MapUnit("infection", 3192, 40190, List('r', 'c'), List('s'), (22, 'r'), 15),
      new MapUnit("infection", 1578, 69776, List(), List(), (63, 'c'), 4),
      new MapUnit("infection", 2950, 40891, List('f'), List(), (25, 's'), 8),
      new MapUnit("infection", 70, 56156, List('r'), List(), (1386, 'r'), 13),
      new MapUnit("infection", 1149, 48840, List('s'), List(), (63, 's'), 18)
    ).sortBy(-_.initiative)

    val (_, resultInfection) = executeCombat(immuneSystemGroups, infectionGroups)
    resultInfection.map(_.unitCount).sum
  }

  def chooseTarget(unit: MapUnit, enemies: List[MapUnit]): Unit = {
    val potentialEnemies = enemies.filter(enemy =>
      enemy.unitCount != 0 &&
      !enemy.targeted &&
      effectiveDamage(unit, enemy) != 0)
    if (potentialEnemies.isEmpty) unit.target = null
    else {
      val maxDamage = potentialEnemies.map(effectiveDamage(unit, _)).max
      unit.target = potentialEnemies.filter(effectiveDamage(unit, _) == maxDamage)
        .sortWith((enemy1, enemy2) => {
          val effective1 = enemy1.unitCount * enemy1.attack._1
          val effective2 = enemy2.unitCount * enemy2.attack._1
          if (effective1 != effective2) effective1 > effective2
          else enemy1.initiative > enemy2.initiative
        }).head
      unit.target.targeted = true
    }
  }

  def effectiveDamage(unit: MapUnit, enemy: MapUnit): Int = {
    if (enemy.weaknesses.contains(unit.attack._2))
      unit.unitCount * unit.attack._1 * 2
    else if (!enemy.immunities.contains(unit.attack._2))
      unit.unitCount * unit.attack._1
    else 0
  }

  def part2(lines: Array[String]): Int = {
    Stream.continually().foldLeft(1)((boost, _) => {
      val immuneSystemGroups = List(
        new MapUnit("immune", 457, 4941, List(), List(), (98, 's'), 14),
        new MapUnit("immune", 35, 3755, List('c'), List('f', 'b'), (1021, 'b'), 1),
        new MapUnit("immune", 6563, 7155, List(), List('r', 'b'), (10, 'c'), 11),
        new MapUnit("immune", 5937, 1868, List('s'), List(), (2, 'f'), 2),
        new MapUnit("immune", 422, 7279, List(), List('r', 'c'), (170, 'b'), 16),
        new MapUnit("immune", 279, 9375, List('c'), List(), (333, 's'), 10),
        new MapUnit("immune", 4346, 3734, List('c'), List('f', 's'), (8, 'b'), 9),
        new MapUnit("immune", 1564, 1596, List(), List(), (8, 'b'), 19),
        new MapUnit("immune", 361, 1862, List('b', 's'), List(), (40, 'b'), 3),
        new MapUnit("immune", 427, 8025, List('c'), List('f', 's'), (139, 'b'), 7)
      ).sortBy(-_.initiative)
      val infectionGroups = List(
        new MapUnit("infection", 27, 24408, List('b', 'f'), List(), (1505, 'c'), 5),
        new MapUnit("infection", 137, 29784, List(), List('s'), (423, 'c'), 12),
        new MapUnit("infection", 1646, 15822, List('s', 'c'), List('f'), (16, 'f'), 6),
        new MapUnit("infection", 1803, 10386, List(), List('c', 'r'), (11, 'b'), 17),
        new MapUnit("infection", 2216, 39081, List(), List(), (28, 'b'), 20),
        new MapUnit("infection", 3192, 40190, List('r', 'c'), List('s'), (22, 'r'), 15),
        new MapUnit("infection", 1578, 69776, List(), List(), (63, 'c'), 4),
        new MapUnit("infection", 2950, 40891, List('f'), List(), (25, 's'), 8),
        new MapUnit("infection", 70, 56156, List('r'), List(), (1386, 'r'), 13),
        new MapUnit("infection", 1149, 48840, List('s'), List(), (63, 's'), 18)
      ).sortBy(-_.initiative)
      immuneSystemGroups.foreach(unit => unit.attack = (unit.attack._1 + boost, unit.attack._2))
      val (resultImmune, resultInfection) = executeCombat(immuneSystemGroups, infectionGroups)
      if (resultInfection.isEmpty) return resultImmune.map(_.unitCount).sum
      boost + 1
    })
    0
  }

  def executeCombat(immuneSystemGroups: List[MapUnit], infectionGroups: List[MapUnit]): (List[MapUnit], List[MapUnit]) = {
    Stream.continually().foldLeft(immuneSystemGroups, infectionGroups)({
      case ((immunes, infections), _) =>
        if (immunes.isEmpty || infections.isEmpty) return (immunes, infections)
        val allUnits = immunes.union(infections)
        allUnits.sortBy(unit => -unit.unitCount * unit.attack._1)
          .foreach(unit => chooseTarget(unit, if (unit.unitType == "immune") infections else immunes))
        if (!allUnits.exists(_.targeted)) return (immunes, infections)
        allUnits.sortBy(-_.initiative).foreach(unit => {
          if (unit.target != null) {
            unit.target.unitCount = math.max(0, unit.target.unitCount -
              math.floor(effectiveDamage(unit, unit.target) / unit.target.unitHp).toInt)
            unit.target.targeted = false
          }
        })
        (immunes.filter(_.unitCount != 0), infections.filter(_.unitCount != 0))
    })
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
