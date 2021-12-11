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

    fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> =
        this.split(*delimiters).filter {
            it.isNotEmpty()
        }

    data class Location(val row: Int, val column: Int)
}
