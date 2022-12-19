package hr.dodomix.advent.solutions


class Day19 {

    fun dayDirectory() = "day19"

    fun part1(input: List<String>): Int {
        val blueprints = input.map { Blueprint.fromString(it) }

        return blueprints.sumOf { it.blueprintId * it.calculateMaximumScore(24) }
    }

    fun part2(input: List<String>): Int {
        val blueprints = input.map { Blueprint.fromString(it) }

        return blueprints.take(3).fold(1) { score, blueprint ->
            score * blueprint.calculateMaximumScore(32)
        }
    }

    private data class Blueprint(
        val blueprintId: Int,
        val oreRobotPriceOre: Int, val clayRobotPriceOre: Int,
        val obsidianRobotoPriceOre: Int, val obsidianRobotoPriceClay: Int,
        val geodeRobotoPriceOre: Int, val geodeRobotoPriceObsidian: Int
    ) {
        private var maxResult = 0

        fun calculateMaximumScore(time: Int): Int = calculateMaximumScoreRecursive(State(), time)

        private fun calculateMaximumScoreRecursive(state: State, time: Int): Int {
            if (time == 0) {
                if (state.geodes > maxResult) {
                    maxResult = state.geodes
                }
                return state.geodes
            }
            if (state.geodes + state.geodeRobots * time + (time * (time - 1)) / 2 < maxResult) {
                return 0
            }

            val newState = state.mine()

            val futures = mutableListOf(newState)
            if (state.canBuildGeodeRobot()) {
                futures += newState.buildGeodeRobot()
            } else {
                if (state.canBuildObsidianRobot() && state.geodeRobots < geodeRobotoPriceObsidian) {
                    futures += newState.buildObsidianRobot()
                }
                if (state.canBuildClayRobot() && state.clayRobots < obsidianRobotoPriceClay) {
                    futures += newState.buildClayRobot()
                }
                if (state.canBuildOreRobot() && state.oreRobots < maxOf(
                        oreRobotPriceOre, clayRobotPriceOre,
                        obsidianRobotoPriceOre, geodeRobotoPriceOre
                    )
                ) {
                    futures += newState.buildOreRobot()
                }
            }

            return futures.maxOf { calculateMaximumScoreRecursive(it, time - 1) }
        }

        companion object {
            fun fromString(line: String): Blueprint {
                val parsedInput =
                    ("Blueprint (\\d+): Each ore robot costs (\\d+) ore. " +
                            "Each clay robot costs (\\d+) ore. " +
                            "Each obsidian robot costs (\\d+) ore and (\\d+) clay. " +
                            "Each geode robot costs (\\d+) ore and (\\d+) obsidian\\.").toRegex()
                        .find(line)!!
                        .groupValues
                        .drop(1)
                        .map { it.toInt() }
                return Blueprint(
                    parsedInput[0],
                    parsedInput[1], parsedInput[2], parsedInput[3],
                    parsedInput[4], parsedInput[5], parsedInput[6]
                )
            }
        }

        inner class State(
            val ore: Int = 0, val clay: Int = 0, val obsidian: Int = 0, val geodes: Int = 0,
            val oreRobots: Int = 1, val clayRobots: Int = 0, val obsidianRobots: Int = 0, val geodeRobots: Int = 0
        ) {
            fun mine(): State {
                return State(
                    ore + oreRobots,
                    clay + clayRobots,
                    obsidian + obsidianRobots,
                    geodes + geodeRobots,
                    oreRobots,
                    clayRobots,
                    obsidianRobots,
                    geodeRobots
                )
            }


            fun canBuildOreRobot(): Boolean = ore >= oreRobotPriceOre

            fun buildOreRobot(): State {
                return State(
                    ore - oreRobotPriceOre,
                    clay,
                    obsidian,
                    geodes,
                    oreRobots + 1,
                    clayRobots,
                    obsidianRobots,
                    geodeRobots
                )
            }

            fun canBuildClayRobot(): Boolean = ore >= clayRobotPriceOre

            fun buildClayRobot(): State {
                return State(
                    ore - clayRobotPriceOre,
                    clay,
                    obsidian,
                    geodes,
                    oreRobots,
                    clayRobots + 1,
                    obsidianRobots,
                    geodeRobots
                )
            }

            fun canBuildObsidianRobot(): Boolean = ore >= obsidianRobotoPriceOre && clay >= obsidianRobotoPriceClay

            fun buildObsidianRobot(): State {
                return State(
                    ore - obsidianRobotoPriceOre,
                    clay - obsidianRobotoPriceClay,
                    obsidian,
                    geodes,
                    oreRobots,
                    clayRobots,
                    obsidianRobots + 1,
                    geodeRobots
                )
            }

            fun canBuildGeodeRobot(): Boolean = ore >= geodeRobotoPriceOre && obsidian >= geodeRobotoPriceObsidian

            fun buildGeodeRobot(): State {
                return State(
                    ore - geodeRobotoPriceOre,
                    clay,
                    obsidian - geodeRobotoPriceObsidian,
                    geodes,
                    oreRobots,
                    clayRobots,
                    obsidianRobots,
                    geodeRobots + 1
                )
            }
        }
    }
}
