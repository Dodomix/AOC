package hr.dodomix.advent.solutions

class Day4 {
    fun dayDirectory() = "day4"

    fun part1(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }
        val boards = getBoards(input.drop(2), emptyList())
        numbers.fold(boards) { currentBoards, number ->
            currentBoards.map { board ->
                val newBoard = if (board.containsKey(number)) {
                    board + Pair(number, Triple(board.getValue(number).first, board.getValue(number).second, true))
                } else {
                    board
                }
                val positionMap = newBoard.values.associate { Pair(Pair(it.first, it.second), it.third) }
                for (i in 0 until 5) {
                    if ((positionMap.getValue(Pair(i, 0)) && positionMap.getValue(Pair(i, 1)) &&
                                positionMap.getValue(Pair(i, 2)) && positionMap.getValue(Pair(i, 3)) &&
                                positionMap.getValue(Pair(i, 4))) || (positionMap.getValue(Pair(0, i)) && positionMap.getValue(Pair(1, i)) &&
                                positionMap.getValue(Pair(2, i)) && positionMap.getValue(Pair(3, i)) &&
                                positionMap.getValue(Pair(4, i)))
                    ) {
                        return newBoard.filter { !it.value.third }.map { it.key }.sum() * number
                    }
                }
                newBoard
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }
        val boards = getBoards(input.drop(2), emptyList())
        numbers.fold(boards) { currentBoards, number ->
            val newBoards = currentBoards.map { board ->
                if (board.containsKey(number)) {
                    board + Pair(number, Triple(board.getValue(number).first, board.getValue(number).second, true))
                } else {
                    board
                }
            }
            val filteredBoards = newBoards.filter { board ->
                val positionMap = board.values.associate { Pair(Pair(it.first, it.second), it.third) }
                (0 until 5).map { i ->
                    (positionMap.getValue(Pair(i, 0)) && positionMap.getValue(Pair(i, 1)) &&
                            positionMap.getValue(Pair(i, 2)) && positionMap.getValue(Pair(i, 3)) &&
                            positionMap.getValue(Pair(i, 4))) || (positionMap.getValue(Pair(0, i)) && positionMap.getValue(Pair(1, i)) &&
                            positionMap.getValue(Pair(2, i)) && positionMap.getValue(Pair(3, i)) &&
                            positionMap.getValue(Pair(4, i)))
                }.none { it }
            }
            if (filteredBoards.isEmpty() && newBoards.size == 1) {
                return newBoards[0].filter { !it.value.third }.map { it.key }.sum() * number
            }
            filteredBoards
        }
        return -1
    }

    private fun getBoards(input: List<String>, boards: List<Map<Int, Triple<Int, Int, Boolean>>>): List<Map<Int, Triple<Int, Int, Boolean>>> {
        if (input.isEmpty()) {
            return boards
        }

        return getBoards(input.drop(6), boards.plus(input.take(5).mapIndexed { row, rowValue ->
            rowValue.trim().split(Regex(" +")).mapIndexed { column, columnValue ->
                Pair(columnValue.toInt(), Triple(row, column, false))
            }
        }.flatten().toMap()))
    }
}
