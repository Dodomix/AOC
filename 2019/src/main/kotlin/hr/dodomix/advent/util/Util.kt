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

    fun <T> permute(input: List<T>): List<List<T>> {
        if (input.size == 1) return listOf(input)
        val perms = mutableListOf<List<T>>()
        val toInsert = input[0]
        for (perm in permute(input.drop(1))) {
            for (i in 0..perm.size) {
                val newPerm = perm.toMutableList()
                newPerm.add(i, toInsert)
                perms.add(newPerm)
            }
        }
        return perms
    }
}
