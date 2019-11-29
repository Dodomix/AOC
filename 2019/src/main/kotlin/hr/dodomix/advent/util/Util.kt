package hr.dodomix.advent.util

import java.io.FileNotFoundException

object Util {
  fun readFileLines(file: String): List<String> {
      return Util.javaClass.classLoader.getResource("inputs/$file")?.readText()?.lines()
          ?: throw FileNotFoundException("Cannot find file $file in inputs")
  }
}
