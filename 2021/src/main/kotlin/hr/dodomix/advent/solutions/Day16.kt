package hr.dodomix.advent.solutions

import java.lang.Integer.toBinaryString
import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO

class Day16 {
    fun dayDirectory() = "day16"

    fun part1(input: List<String>): Int {
        val inputBits = input[0].map { toBinaryString("$it".toInt(16)).padStart(4, '0') }.joinToString("")
        return parsePacket(inputBits).calculateVersion()
    }

    fun part2(input: List<String>): BigInteger {
        val inputBits = input[0].map { toBinaryString("$it".toInt(16)).padStart(4, '0') }.joinToString("")
        return parsePacket(inputBits).calculateValue()
    }

    private fun parsePacket(bits: String): Packet {
        val type = bits.drop(3).take(3).toInt(2)

        return if (type == 4) {
            parseLiteralPacket(bits)
        } else {
            parseOperatorPacket(bits)
        }
    }

    private fun parseLiteralPacket(bits: String): LiteralPacket {
        var newBits = bits
        val version = bits.take(3).toInt(2)
        newBits = newBits.drop(3)
        val packetType = newBits.take(3).toInt(2)
        newBits = newBits.drop(3)
        var value = ""
        while (true) {
            val lastValue = newBits.take(1) == "0"
            newBits = newBits.drop(1)
            value += newBits.take(4)
            newBits = newBits.drop(4)
            if (lastValue) {
                return LiteralPacket(version, packetType, bits.length - newBits.length, value.toBigInteger(2))
            }
        }
    }

    private fun parseOperatorPacket(bits: String): OperatorPacket {
        var newBits = bits
        val version = bits.take(3).toInt(2)
        newBits = newBits.drop(3)
        val packetType = newBits.take(3).toInt(2)
        newBits = newBits.drop(3)
        val lengthTypeId = newBits.take(1).toInt(2)
        newBits = newBits.drop(1)

        val packets = mutableListOf<Packet>()
        if (lengthTypeId == 0) {
            val valuesLength = newBits.take(15).toInt(2)
            newBits = newBits.drop(15)
            var internalPacketBits = newBits.take(valuesLength)
            while (internalPacketBits.isNotEmpty()) {
                val packet = parsePacket(internalPacketBits)
                packets.add(packet)
                internalPacketBits = internalPacketBits.drop(packet.length)
            }
            newBits = newBits.drop(valuesLength)
        } else if (lengthTypeId == 1) {
            val valuesCount = newBits.take(11).toInt(2)
            newBits = newBits.drop(11)
            (0 until valuesCount).forEach { _ ->
                val packet = parsePacket(newBits)
                packets.add(packet)
                newBits = newBits.drop(packet.length)
            }
        }
        return OperatorPacket(version, packetType, bits.length - newBits.length, packets)
    }

    private abstract class Packet(val version: Int, val type: Int, val length: Int) {
        abstract fun calculateVersion(): Int
        abstract fun calculateValue(): BigInteger
    }

    private class LiteralPacket(
        version: Int, type: Int, length: Int,
        val value: BigInteger
    ) : Packet(version, type, length) {
        override fun calculateVersion(): Int = version
        override fun calculateValue(): BigInteger = value
    }

    private class OperatorPacket(
        version: Int, type: Int, length: Int,
        val values: List<Packet>
    ) : Packet(version, type, length) {
        override fun calculateVersion(): Int = version + values.sumOf { it.calculateVersion() }
        override fun calculateValue(): BigInteger = when (type) {
            0 -> values.sumOf { it.calculateValue() }
            1 -> values.fold(ONE) { acc, packet -> acc * packet.calculateValue() }
            2 -> values.minOf { it.calculateValue() }
            3 -> values.maxOf { it.calculateValue() }
            5 -> if (values[0].calculateValue() > values[1].calculateValue()) ONE else ZERO
            6 -> if (values[0].calculateValue() < values[1].calculateValue()) ONE else ZERO
            7 -> if (values[0].calculateValue() == values[1].calculateValue()) ONE else ZERO
            else -> {
                throw RuntimeException("Invalid packet type $type")
            }
        }
    }
}
