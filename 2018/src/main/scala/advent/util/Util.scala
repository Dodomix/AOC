package advent.util

import scala.io.Source

object Util {
  def readFileLines(file: String): Array[String] = Source.fromFile(file).mkString.split("\n")
}
