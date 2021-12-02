import hr.dodomix.advent.solutions.Day1
import hr.dodomix.advent.solutions.Day2
import hr.dodomix.advent.util.Util
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Tests {
    internal class Day1Test {
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

    internal class Day2Test {
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
}
