package hr.dodomix.advent.solutions


class Day22 {
    fun dayDirectory() = "day22"

    fun part1(input: List<String>): Int {
        var decks = constructDecks(input)
        while (true) {
            val (deck1, deck2) = decks
            val deck1Card = deck1.first()
            val deck2Card = deck2.first()
            decks = if (deck1Card > deck2Card) {
                Pair(deck1.drop(1) + listOf(deck1Card, deck2Card), deck2.drop(1))
            } else {
                Pair(deck1.drop(1), deck2.drop(1) + listOf(deck2Card, deck1Card))
            }
            if (decks.first.isEmpty()) {
                return decks.second.reversed().mapIndexed { index, card -> card * (index + 1) }.sum()
            } else if (decks.second.isEmpty()) {
                return decks.first.reversed().mapIndexed { index, card -> card * (index + 1) }.sum()
            }
        }
    }

    fun part2(input: List<String>): Int {
        val decks = playCombat(constructDecks(input))
        if (decks.first.isEmpty()) {
            return decks.second.reversed().mapIndexed { index, card -> card * (index + 1) }.sum()
        } else if (decks.second.isEmpty()) {
            return decks.first.reversed().mapIndexed { index, card -> card * (index + 1) }.sum()
        }
        return -1
    }

    private fun constructDecks(input: List<String>) = input.fold(Pair(Pair(emptyList<Int>(), emptyList<Int>()), false)) { (currentDecks, constructingPlayer2), line ->
        when {
            line.isEmpty() -> Pair(currentDecks, constructingPlayer2)
            line.contains("Player 1") -> Pair(currentDecks, false)
            line.contains("Player 2") -> Pair(currentDecks, true)
            !constructingPlayer2 -> Pair(Pair(currentDecks.first + line.toInt(), currentDecks.second), constructingPlayer2)
            else -> Pair(Pair(currentDecks.first, currentDecks.second + line.toInt()), constructingPlayer2)
        }
    }.first

    private fun playCombat(decks: Pair<List<Int>, List<Int>>): Pair<List<Int>, List<Int>> {
        val previousDecks = emptySet<Pair<List<Int>, List<Int>>>().toMutableSet()
        var playedDecks = decks

        while (true) {
            if (previousDecks.contains(playedDecks)) {
                return playedDecks
            }
            previousDecks.add(playedDecks)
            val (deck1, deck2) = playedDecks
            if (deck1.isEmpty() || deck2.isEmpty()) {
                return playedDecks
            }
            val deck1Card = deck1.first()
            val deck2Card = deck2.first()
            val winnerPlayer1 = if (deck1.size > deck1Card && deck2.size > deck2Card) {
                val subGameDecks = playCombat(Pair(deck1.drop(1).take(deck1Card), deck2.drop(1).take(deck2Card)))
                when {
                    subGameDecks.first.isEmpty() -> false
                    subGameDecks.second.isEmpty() -> true
                    else -> true
                }
            } else {
                deck1Card > deck2Card
            }
            playedDecks = if (winnerPlayer1) {
                Pair(deck1.drop(1) + listOf(deck1Card, deck2Card), deck2.drop(1))
            } else {
                Pair(deck1.drop(1), deck2.drop(1) + listOf(deck2Card, deck1Card))
            }
        }
    }
}
