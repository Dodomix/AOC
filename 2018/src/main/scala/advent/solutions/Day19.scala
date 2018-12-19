package advent.solutions

import advent.util.Util

import scala.collection.immutable.TreeMap

object Day19 {

  class MapUnit(var unitType: Char, var hp: Int, var damage: Int, var i: Int, var j: Int)

  def part1(lines: Array[String]): Int = {
    val (instructions, ip) = lines.foldLeft((List[(String, Int, Int, Int)](), 0))((acc, line) => {
      val split = line.split(" ")
      if (split(0) == "#ip") (acc._1, split(1).toInt)
      else ((acc._1) :+ (split(0), split(1).toInt, split(2).toInt, split(3).toInt), acc._2)
    })
    Stream.continually().foldLeft(Map[Int, Int]().withDefaultValue(0))((registers, _) => {
      if (registers(ip) >= instructions.size) return registers(0)
      val instruction = instructions(registers(ip))
      val updatedRegisters = runInstruction(instruction._1, instruction._2, instruction._3, instruction._4, registers)
      updatedRegisters.updated(ip, updatedRegisters(ip) + 1)
    })
    0
  }

  def part2(lines: Array[String]): Int = {
    val (instructions, ip) = lines.foldLeft((List[(String, Int, Int, Int)](), 0))((acc, line) => {
      val split = line.split(" ")
      if (split(0) == "#ip") (acc._1, split(1).toInt)
      else ((acc._1) :+ (split(0), split(1).toInt, split(2).toInt, split(3).toInt), acc._2)
    })
    Stream.continually().foldLeft(TreeMap[Int, Int]((0, 1)).withDefaultValue(0))((registers, _) => {
      if (registers(ip) >= instructions.size) return registers(0)
      val instruction = instructions(registers(ip))
      println(instruction) // sum of factors of 10551296
      val updatedRegisters = runInstruction(instruction._1, instruction._2, instruction._3, instruction._4, registers)
      updatedRegisters.updated(ip, updatedRegisters(ip) + 1)
    })
    0
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
      case "gtrr" => registers.updated(c, if (registers(a) > registers(b)) 1 else 0)
      case "gtri" => registers.updated(c, if (registers(a) > b) 1 else 0)
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
