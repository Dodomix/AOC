package hr.dodomix.advent.util

import java.io.FileNotFoundException

object Util {
    fun readFileLines(file: String): List<String> {
        return Util.javaClass.classLoader.getResource("inputs/$file")
            ?.readText()
            ?.trimIndent()
            ?.lines()
            ?: throw FileNotFoundException("Cannot find file $file in inputs")
    }

    data class Position(val row: Int, val column: Int) {
        fun translate(position: Position) = Position(row + position.row, column + position.column)
    }

    data class LongPosition(val row: Long, val column: Long) {
        fun translate(position: LongPosition) = LongPosition(row + position.row, column + position.column)
    }
}
