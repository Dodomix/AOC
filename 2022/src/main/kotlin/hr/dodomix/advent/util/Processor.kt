package hr.dodomix.advent.util

class Processor {
    fun process(program: List<String>): Map<Int, Int> =
        program.fold(State(0, 1, emptyMap())) { (clock, register, history), command ->
            when {
                command == "noop" -> State(clock + 1, register, history + Pair(clock, register))
                command.startsWith("addx") -> State(
                    clock + 2,
                    register + command.split(" ")[1].toInt(),
                    history + Pair(clock, register) + Pair(clock + 1, register)
                )

                else -> throw RuntimeException("Invalid command")
            }
        }.history

    private data class State(val clock: Int, val register: Int, val history: Map<Int, Int>)
}