package hr.dodomix.advent.solutions

import java.math.BigInteger


class Day20 {
    fun dayDirectory() = "day20"

    fun part1(input: List<String>): BigInteger {
        val tiles = input.fold(
            Pair(
                emptyList<Tile>(),
                null as Pair<Int, Map<Pair<Int, Int>, Char>>?
            )
        ) { (currentTiles, tileData), line ->
            when {
                line.isEmpty() -> Pair(currentTiles + Tile(tileData!!.first, tileData.second), null)
                line.contains("Tile") -> Pair(currentTiles, Pair(line.drop(5).dropLast(1).toInt(), emptyMap()))
                else -> {
                    val row = (tileData!!.second.maxOfOrNull { it.key.first } ?: -1) + 1
                    Pair(
                        currentTiles,
                        Pair(
                            tileData.first,
                            tileData.second + line.mapIndexed { index, c -> Pair(Pair(row, index), c) })
                    )
                }
            }
        }.first

        val cornerTiles = tiles.filter { tile ->
            listOf(
                extractSide(tile.values, 0, null),
                extractSide(tile.values, null, 0),
                extractSide(tile.values, tile.values.maxOf { value -> value.key.second }, null),
                extractSide(tile.values, null, tile.values.maxOf { value -> value.key.first })
            ).map { findMatchingTile(tile.id, it, tiles) }.count { it != null } == 2
        }

        return cornerTiles.fold(BigInteger.ONE) { acc, tile ->
            acc * tile.id.toBigInteger()
        }
    }

    fun part2(input: List<String>): Int {
        val tiles = input.fold(
            Pair(
                emptyList<Tile>(),
                null as Pair<Int, Map<Pair<Int, Int>, Char>>?
            )
        ) { (currentTiles, tileData), line ->
            when {
                line.isEmpty() -> Pair(currentTiles + Tile(tileData!!.first, tileData.second), null)
                line.contains("Tile") -> Pair(currentTiles, Pair(line.drop(5).dropLast(1).toInt(), emptyMap()))
                else -> {
                    val row = (tileData!!.second.maxOfOrNull { it.key.first } ?: -1) + 1
                    Pair(
                        currentTiles,
                        Pair(
                            tileData.first,
                            tileData.second + line.mapIndexed { index, c -> Pair(Pair(row, index), c) })
                    )
                }
            }
        }.first

        val matches = tiles.map { tile ->
            Pair(tile, listOf(
                extractSide(tile.values, 0, null),
                extractSide(tile.values, null, 0),
                extractSide(tile.values, tile.values.maxOf { value -> value.key.second }, null),
                extractSide(tile.values, null, tile.values.maxOf { value -> value.key.first })
            ).map { findMatchingTile(tile.id, it, tiles) })
        }.toMap()

        val tilesAtPositions = mapOf<Pair<Int, Int>, Tile>().toMutableMap()
        val initialCorner =
            matches.filter { (_, tileMatches) -> tileMatches.count { it != null } == 2 }.entries.first().key
        tilesAtPositions[Pair(0, 0)] = initialCorner
        tilesAtPositions[Pair(1, 0)] = matches.getValue(initialCorner).filterNotNull().first()
        tilesAtPositions[Pair(0, 1)] = matches.getValue(initialCorner).filterNotNull().last()
        var leftPosition = Pair(1, 0)
        var topPosition = Pair(0, 1)
        while (true) {
            while (true) {
                val previousTile = tilesAtPositions.getValue(Pair(topPosition.first, topPosition.second - 1))
                val leftTile = tilesAtPositions.getValue(leftPosition)
                val topTile = tilesAtPositions.getValue(topPosition)
                val newTile = matches.getValue(leftTile).intersect(matches.getValue(topTile))
                    .first { it != null && it != previousTile }!!
                tilesAtPositions[Pair(leftPosition.first, topPosition.second)] = newTile
                val potentialTopTileList = matches.getValue(topTile).filterNotNull().filter {
                    !tilesAtPositions.containsValue(it)
                }
                if (potentialTopTileList.isNotEmpty()) {
                    tilesAtPositions[Pair(topPosition.first, topPosition.second + 1)] = potentialTopTileList.first()
                } else {
                    break
                }
                leftPosition = Pair(leftPosition.first, leftPosition.second + 1)
                topPosition = Pair(topPosition.first, topPosition.second + 1)
            }
            val oldLeftTile = tilesAtPositions.getValue(Pair(leftPosition.first, 0))
            val topTile = tilesAtPositions.getValue(Pair(leftPosition.first - 1, 0))
            val rightTile = tilesAtPositions.getValue(Pair(leftPosition.first, 1))
            topPosition = Pair(leftPosition.first, 1)
            leftPosition = Pair(leftPosition.first + 1, 0)
            val potentialLeftTileList = matches.getValue(oldLeftTile).filterNotNull().filter {
                it != topTile && it != rightTile
            }
            if (potentialLeftTileList.isNotEmpty()) {
                tilesAtPositions[leftPosition] = potentialLeftTileList.first()
            } else {
                break
            }
        }

        tilesAtPositions.forEach { (row, column), tile ->
            val expectedValues = listOf(
                tilesAtPositions[Pair(row - 1, column)],
                tilesAtPositions[Pair(row, column - 1)],
                tilesAtPositions[Pair(row + 1, column)],
                tilesAtPositions[Pair(row, column + 1)]
            )
            for (i in 0 until 4) {
                tile.values = rotate(tile.values)
                if (getMatchingTilesForSide(tile, tiles) == expectedValues) {
                    return@forEach
                }
            }
            tile.values = flipHorizontal(tile.values)
            for (i in 0 until 4) {
                tile.values = rotate(tile.values)
                if (getMatchingTilesForSide(tile, tiles) == expectedValues) {
                    return@forEach
                }
            }
            tile.values = flipVertical(tile.values)
            for (i in 0 until 4) {
                tile.values = rotate(tile.values)
                if (getMatchingTilesForSide(tile, tiles) == expectedValues) {
                    return@forEach
                }
            }
            throw RuntimeException("Didn't find correct orientation")
        }

        val image = mutableMapOf<Pair<Int, Int>, Char>()
        for (row in 0..tilesAtPositions.maxOf { it.key.first }) {
            for (column in 0..tilesAtPositions.maxOf { it.key.second }) {
                val tile = tilesAtPositions.getValue(Pair(row, column))
                for (imageRow in 1 until tile.values.maxOf { it.key.first }) {
                    for (imageColumn in 1 until tile.values.maxOf { it.key.second }) {
                        image[Pair(row * (tile.values.maxOf { it.key.first } - 1) + imageRow - 1,
                            column * (tile.values.maxOf { it.key.second } - 1) + imageColumn - 1)] =
                            tile.values.getValue(Pair(imageRow, imageColumn))
                    }
                }
            }
        }

        val monsters = findMonsters(image)
        val monsterTiles = monsters.flatMap { (position, _) ->
            monsterPositions(position.first, position.second)
        }.toSet()
        return image.filter { !monsterTiles.contains(it.key) && it.value == '#' }.size
    }

    private fun findMonsters(image: MutableMap<Pair<Int, Int>, Char>) =
        image.filter { (position, _) ->
            monsterPositions(position.first, position.second).all { image.getOrDefault(it, '.') == '#' }
        }

    private fun monsterPositions(row: Int, column: Int) = listOf(
        Pair(row, column),
        Pair(row + 1, column - 18),
        Pair(row + 1, column - 13),
        Pair(row + 1, column - 12),
        Pair(row + 1, column - 7),
        Pair(row + 1, column - 6),
        Pair(row + 1, column - 1),
        Pair(row + 1, column),
        Pair(row + 1, column + 1),
        Pair(row + 2, column - 17),
        Pair(row + 2, column - 14),
        Pair(row + 2, column - 11),
        Pair(row + 2, column - 8),
        Pair(row + 2, column - 5),
        Pair(row + 2, column - 2)
    )

    private fun getMatchingTilesForSide(
        tile: Tile,
        tiles: List<Tile>
    ) = listOf(
        extractSide(tile.values, null, 0),
        extractSide(tile.values, 0, null),
        extractSide(tile.values, null, tile.values.maxOf { value -> value.key.first }),
        extractSide(tile.values, tile.values.maxOf { value -> value.key.second }, null)
    ).map { findMatchingTile(tile.id, it, tiles, true) }

    private fun extractSide(values: Map<Pair<Int, Int>, Char>, column: Int?, row: Int?): List<Char> {
        return values.filter { (column == null || it.key.second == column) && (row == null || it.key.first == row) }
            .map { it.value }
    }

    private fun findMatchingTile(
        tileId: Int,
        expectedSide: List<Char>,
        tiles: List<Tile>,
        searchFlipped: Boolean = true
    ): Tile? {
        return tiles.find {
            it.id != tileId &&
                (extractSide(
                    it.values,
                    0,
                    null
                ).let { side -> side == expectedSide || (searchFlipped && side.reversed() == expectedSide) } ||
                    extractSide(
                        it.values,
                        null,
                        0
                    ).let { side -> side == expectedSide || (searchFlipped && side.reversed() == expectedSide) } ||
                    extractSide(
                        it.values,
                        it.values.maxOf { value -> value.key.second },
                        null
                    ).let { side -> side == expectedSide || (searchFlipped && side.reversed() == expectedSide) } ||
                    extractSide(
                        it.values,
                        null,
                        it.values.maxOf { value -> value.key.first }
                    ).let { side -> side == expectedSide || (searchFlipped && side.reversed() == expectedSide) })
        }
    }

    private fun rotate(values: Map<Pair<Int, Int>, Char>): Map<Pair<Int, Int>, Char> {
        val maxPosition = values.maxOf { it.key.first }
        return values.mapKeys { (oldPosition, _) -> Pair(oldPosition.second, maxPosition - oldPosition.first) }
    }

    private fun flipHorizontal(values: Map<Pair<Int, Int>, Char>): Map<Pair<Int, Int>, Char> {
        val maxPosition = values.maxOf { it.key.first }
        return values.mapKeys { (oldPosition, _) -> Pair(oldPosition.first, maxPosition - oldPosition.second) }
    }

    private fun flipVertical(values: Map<Pair<Int, Int>, Char>): Map<Pair<Int, Int>, Char> {
        val maxPosition = values.maxOf { it.key.first }
        return values.mapKeys { (oldPosition, _) -> Pair(maxPosition - oldPosition.first, oldPosition.second) }
    }

    data class Tile(val id: Int, var values: Map<Pair<Int, Int>, Char>)
}
