package advent.solutions

import advent.util.Util

object Day16 {

  class MapUnit(var unitType: Char, var hp: Int, var damage: Int, var i: Int, var j: Int)

  def part1(lines: Array[String]): Int = {
    val operations = List("addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori", "setr",
      "seti", "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr")
    (lines :+ "").foldLeft((Array[Int](), Array[Int](), Array[Int](), 0))({
      case ((before, op, after, count), line) =>
        if (line.startsWith("Before:")) {
          (line.split("Before: *\\[")(1).dropRight(1).split(", ").map(_.toInt), op, after, count)
        } else if (line.startsWith("After:")) {
          (before, op, line.split("After: *\\[")(1).dropRight(1).split(", ").map(_.toInt), count)
        } else if (!line.isEmpty) {
          (before, line.split(" ").map(_.toInt), after, count)
        } else {
          (before, op, after, if (operations.foldLeft(0)((count, operation) => {
            if (runInstruction(operation, op(1), op(2), op(3), asMap(before)).equals(asMap(after))) count + 1
            else count
          }) >= 3) count + 1
          else count)
        }
    })._4
  }

  def part2(lines: Array[String]): Int = {
    val operations = Map((3, "eqir"), (7, "gtrr"), (8, "eqrr"), (6, "eqri"),
      (11, "gtri"), (10, "gtir"), (13, "bani"), (5, "setr"),
      (2, "banr"), (4, "muli"), (14, "seti"), (0, "mulr"),
      (1, "addr"), (15, "bori"), (12, "borr"), (9, "addi"))
    lines.map(line => {
      val split = line.split(" ")
      (split(0).toInt, split(1).toInt, split(2).toInt, split(3).toInt)
    }).foldLeft(Map[Int, Int]().withDefaultValue(0))({
      case (registers, (opCode, a, b, c)) => runInstruction(operations(opCode), a, b, c, registers)
    })(0)
  }

  def asMap(arr: Array[Int]): Map[Int, Int] = {
    arr.zipWithIndex.foldLeft(Map[Int, Int]())((map, zipped) => map.updated(zipped._2, zipped._1))
  }

  def runInstruction(op_code: String, a: Int, b: Int, c: Int, registers: Map[Int, Int]): Map[Int, Int] = {
    op_code match {
      case "addr" => registers.updated(c, registers(a) + registers(b))
      case "addi" => registers.updated(c, registers(a) + b)
      case "mulr" => registers.updated(c, registers(a) * registers(b))
      case "muli" => registers.updated(c, registers(a) * b)
      case "banr" => registers.updated(c, registers(a) & registers(b))
      case "bani" => registers.updated(c, registers(a) & b)
      case "borr" => registers.updated(c, registers(a) | registers(b))
      case "bori" => registers.updated(c, registers(a) | b)
      case "setr" => registers.updated(c, registers(a))
      case "seti" => registers.updated(c, a)
      case "gtir" => registers.updated(c, if (a > registers(b)) 1 else 0)
      case "gtri" => registers.updated(c, if (registers(a) > registers(b)) 1 else 0)
      case "gtrr" => registers.updated(c, if (registers(a) > b) 1 else 0)
      case "eqir" => registers.updated(c, if (a == registers(b)) 1 else 0)
      case "eqri" => registers.updated(c, if (registers(a) == b) 1 else 0)
      case "eqrr" => registers.updated(c, if (registers(a) == registers(b)) 1 else 0)
      case _ => registers
    }
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
