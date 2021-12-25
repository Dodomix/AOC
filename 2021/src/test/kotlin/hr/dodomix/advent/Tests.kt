package hr.dodomix.advent

import hr.dodomix.advent.solutions.Day1
import hr.dodomix.advent.solutions.Day10
import hr.dodomix.advent.solutions.Day11
import hr.dodomix.advent.solutions.Day12
import hr.dodomix.advent.solutions.Day13
import hr.dodomix.advent.solutions.Day14
import hr.dodomix.advent.solutions.Day15
import hr.dodomix.advent.solutions.Day16
import hr.dodomix.advent.solutions.Day17
import hr.dodomix.advent.solutions.Day18
import hr.dodomix.advent.solutions.Day19
import hr.dodomix.advent.solutions.Day2
import hr.dodomix.advent.solutions.Day20
import hr.dodomix.advent.solutions.Day21
import hr.dodomix.advent.solutions.Day22
import hr.dodomix.advent.solutions.Day25
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource


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

    @Nested
    inner class Day13Test {
        private val day = Day13()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(17)
        }
    }

    @Nested
    inner class Day14Test {
        private val day = Day14()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(1588)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(2188189693529)
        }
    }

    @Nested
    inner class Day15Test {
        private val day = Day15()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(40)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(315)
        }
    }

    @Nested
    inner class Day16Test {
        private val day = Day16()

        @ParameterizedTest
        @CsvSource(
            "8A004A801A8002F478,16",
            "620080001611562C8802118E34,12",
            "C0015000016115A2E0802F182340,23",
            "A0016C880162017C3686B18A3D4780,31"
        )
        fun part1(input: String, result: Int) {
            assertThat(day.part1(listOf(input)))
                .isEqualTo(result)
        }

        @ParameterizedTest
        @CsvSource(
            "C200B40A82,3",
            "04005AC33890,54",
            "880086C3E88112,7",
            "CE00C43D881120,9",
            "D8005AC2A8F0,1",
            "F600BC2D8F,0",
            "9C005AC2F8F0,0",
            "9C0141080250320F1802104A08,1"
        )
        fun part2(input: String, result: Int) {
            assertThat(day.part2(listOf(input)))
                .isEqualTo(result)
        }
    }

    @Nested
    inner class Day17Test {
        private val day = Day17()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(45)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(112)
        }
    }

    @Nested
    inner class Day18Test {
        private val day = Day18()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(4140)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(3993)
        }
    }

    @Nested
    inner class Day19Test {
        private val day = Day19()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(79)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(3621)
        }
    }

    @Nested
    inner class Day20Test {
        private val day = Day20()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(35)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(3351)
        }
    }

    @Nested
    inner class Day21Test {
        private val day = Day21()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(739785)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(444356092776315)
        }
    }

    @Nested
    inner class Day22Test {
        private val day = Day22()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(590784)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input2")))
                .isEqualTo(2758514936282235)
        }
    }

    @Nested
    inner class Day25Test {
        private val day = Day25()

        @Test
        fun part1() {
            assertThat(day.part1(Util.readFileLines("${day.dayDirectory()}/test-input1")))
                .isEqualTo(58)
        }

        @Test
        fun part2() {
            assertThat(day.part2(Util.readFileLines("${day.dayDirectory()}/test-input2")))
                .isEqualTo(0)
        }
    }
}
