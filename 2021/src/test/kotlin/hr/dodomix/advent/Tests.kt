package hr.dodomix.advent

import hr.dodomix.advent.solutions.Day1
import hr.dodomix.advent.solutions.Day10
import hr.dodomix.advent.solutions.Day11
import hr.dodomix.advent.solutions.Day12
import hr.dodomix.advent.solutions.Day2
import hr.dodomix.advent.solutions.Day3
import hr.dodomix.advent.solutions.Day4
import hr.dodomix.advent.solutions.Day5
import hr.dodomix.advent.solutions.Day6
import hr.dodomix.advent.solutions.Day7
import hr.dodomix.advent.solutions.Day8
import hr.dodomix.advent.solutions.Day9
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
                .isEqualTo(7)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(5)
        }
    }

    @Nested
    inner class Day2Test {
        private val day = Day2()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(150)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(900)
        }
    }

    @Nested
    inner class Day3Test {
        private val day = Day3()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(198)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(230)
        }
    }

    @Nested
    inner class Day4Test {
        private val day = Day4()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(4512)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(1924)
        }
    }

    @Nested
    inner class Day5Test {
        private val day = Day5()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(5)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(12)
        }
    }

    @Nested
    inner class Day6Test {
        private val day = Day6()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(5934)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(26984457539)
        }
    }

    @Nested
    inner class Day7Test {
        private val day = Day7()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(37)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(168)
        }
    }

    @Nested
    inner class Day8Test {
        private val day = Day8()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(26)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(61229)
        }
    }

    @Nested
    inner class Day9Test {
        private val day = Day9()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(15)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(1134)
        }
    }

    @Nested
    inner class Day10Test {
        private val day = Day10()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(26397)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(288957)
        }
    }

    @Nested
    inner class Day11Test {
        private val day = Day11()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(1656)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(195)
        }
    }

    @Nested
    inner class Day12Test {
        private val day = Day12()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(226)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(3509)
        }
    }
}
