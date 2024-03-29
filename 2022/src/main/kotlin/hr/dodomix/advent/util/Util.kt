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

    data class Position3D(val x: Long, val y: Long, val z: Long) {
        companion object {
            fun fromString(line: String): Position3D {
                val (x, y, z) = line.split(",").map { it.toLong() }
                return Position3D(x, y, z)
            }
        }
    }
}
