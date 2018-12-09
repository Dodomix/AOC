package advent.solutions

import advent.util.Util

object Day9 {

  class LinkedNode[T](val value: T, var prev: LinkedNode[T], var next: LinkedNode[T])

  def part1(lines: Array[String]): Long = {
    val split = lines(0).split(" ")
    val (players, lastMarble) = (split(0).toInt, split(1).toInt)
    val initialNode = new LinkedNode[Long](0L, null, null)
    initialNode.prev = initialNode
    initialNode.next = initialNode
    Range(1, lastMarble + 1).foldLeft((initialNode, Map[Int, Long]().withDefaultValue(0L)))({
      case ((currentNode, scores), marble) =>
        if (marble % 23 != 0) {
          val nextNode = currentNode.next
          val newNode = new LinkedNode(marble.toLong, nextNode, nextNode.next)
          nextNode.next = newNode
          newNode.next.prev = newNode
          (newNode, scores)
        } else {
          val player = marble % players
          val takenNode = currentNode.prev.prev.prev.prev.prev.prev.prev
          takenNode.prev.next = takenNode.next
          takenNode.next.prev = takenNode.prev
          (takenNode.next, scores.updated(player, scores(player) + marble.toLong + takenNode.value.toLong))
        }
    })._2.values.max
  }

  def part2(lines: Array[String]): Long = {
    part1(lines)
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
