package advent.solutions

import advent.util.Util

import scala.collection.immutable.{TreeMap, TreeSet}

object Day8 {

  def part1(lines: Array[String]): Int = {
    calculateNodeMetadata(lines(0).split(" ").map(_.toInt).iterator)
  }

  def part2(lines: Array[String]): Int = {
    calculateNodeValue(lines(0).split(" ").map(_.toInt).iterator)
  }

  def calculateNodeMetadata(nodes: Iterator[Int]): Int = {
    val children = nodes.next()
    val metadataCount = nodes.next()
    Range(0, children).foldLeft(0)((acc, _) => acc + calculateNodeMetadata(nodes)) +
      Range(0, metadataCount).foldLeft(0)((acc, _) => acc + nodes.next())
  }

  def calculateNodeValue(nodes: Iterator[Int]): Int = {
    val childrenCount = nodes.next()
    val metadataCount = nodes.next()
    val children = Range(0, childrenCount).foldLeft(Array[Int]())((acc, _) => acc :+ calculateNodeValue(nodes))
    val metadata = Range(0, metadataCount).foldLeft(Array[Int]())((acc, _) => acc :+ nodes.next())

    if (childrenCount == 0) metadata.sum
    else {
      metadata.foldLeft(0)((result, metadataValue) =>
        if (children.length >= metadataValue) result + children(metadataValue - 1) else result)
    }
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
