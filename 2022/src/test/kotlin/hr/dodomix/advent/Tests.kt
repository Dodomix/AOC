package hr.dodomix.advent

import hr.dodomix.advent.solutions.Day1
import hr.dodomix.advent.util.Util
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


class Tests {
    @Nested
    inner class Day1Test {
        private val day = Day1()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(24000)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(45000)
        }
    }
}
