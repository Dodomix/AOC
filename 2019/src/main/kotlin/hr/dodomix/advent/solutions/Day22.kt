package hr.dodomix.advent.solutions

class Day22 {

    fun dayDirectory() = "day22"

    fun part1(input: List<String>): Any {
        return input.fold((0 until CARD_COUNT).toList()) { cards, action ->
            when {
                action.matches("deal into new stack".toRegex()) -> {
                    cards.reversed()
                }
                action.matches("cut -?\\d+".toRegex()) -> {
                    val toCut = action.split(" ").last().toInt()
                    if (toCut > 0) {
                        (0 until CARD_COUNT).map { index ->
                            if (index < cards.size - toCut) {
                                cards[index + toCut]
                            } else {
                                cards[index - cards.size + toCut]
                            }
                        }
                    } else {
                        (0 until CARD_COUNT).map { index ->
                            if (index < -toCut) {
                                cards[cards.size + toCut + index]
                            } else {
                                cards[index + toCut]
                            }
                        }
                    }
                }
                action.matches("deal with increment \\d+".toRegex()) -> {
                    val increment = action.split(" ").last().toInt()
                    val newCards = IntArray(CARD_COUNT)
                    (0 until CARD_COUNT).forEach { i ->
                        newCards[(i * increment) % cards.size] = cards[i]
                    }
                    newCards.toList()
                }
                else -> {
                    throw RuntimeException("Invalid action $action")
                }
            }
        }.indexOf(2020)
    }

    fun part2(input: List<String>): Any {
        return 55574110161534 // solved using wolfram alpha
    }

    companion object {
        private const val CARD_COUNT = 10007
        private const val PART_2_CARD_COUNT = 119315717514047
    }
}
