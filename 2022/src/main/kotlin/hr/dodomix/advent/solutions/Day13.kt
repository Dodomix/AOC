package hr.dodomix.advent.solutions


class Day13 {
    fun dayDirectory() = "day13"

    fun part1(input: List<String>): Int {
        var lines = input
        var index = 0
        var sum = 0
        while (lines.isNotEmpty()) {
            index++
            val line1 = lines.first()
            lines = lines.drop(1)
            val line2 = lines.first()
            lines = lines.drop(2)
            if (Packet.fromString(line1).compareTo(Packet.fromString(line2)) < 0) {
                sum += index
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val divider1 = Packet.fromString("[[2]]")
        val divider2 = Packet.fromString("[[6]]")
        val packets = sortedSetOf<Packet>({ p1, p2 -> p1.compareTo(p2) })
        input.filter { it.isNotEmpty() }
            .forEach { line ->
                packets.add(Packet.fromString(line))
            }
        packets.add(divider1)
        packets.add(divider2)
        return (packets.indexOf(divider1) + 1) * (packets.indexOf(divider2) + 1)
    }

    private data class Packet(var parent: Packet?, var packetValue: Int?, val subpackets: MutableList<Packet>) {
        companion object {
            fun fromString(line: String): Packet {
                val root = Packet(null, null, mutableListOf())
                line.fold(Pair(root, "")) { (currentRoot, value), c ->
                    if (c.isDigit()) {
                        Pair(currentRoot, value + c)
                    } else if (c == '[') {
                        val newPacket = Packet(currentRoot, null, mutableListOf())
                        currentRoot!!.subpackets.add(newPacket)
                        Pair(newPacket, "")
                    } else if (c == ']') {
                        if (value.isNotEmpty()) {
                            currentRoot!!.subpackets.add(Packet(currentRoot, value.toInt(), mutableListOf()))
                        }
                        if (currentRoot.parent != null) {
                            Pair(currentRoot.parent!!, "")
                        } else {
                            Pair(currentRoot, "")
                        }
                    } else {
                        if (value.isNotEmpty()) {
                            currentRoot!!.subpackets.add(Packet(currentRoot, value.toInt(), mutableListOf()))
                        }
                        Pair(currentRoot, "")
                    }
                }
                return root
            }
        }

        fun compareTo(packet: Packet): Int = if (packetValue != null && packet.packetValue != null) {
            packetValue!! - packet.packetValue!!
        } else if (packetValue == null && packet.packetValue == null) {
            comparePacketLists(subpackets, packet.subpackets)
        } else {
            if (packetValue != null) {
                comparePacketLists(listOf(Packet(this, packetValue!!, mutableListOf())), packet.subpackets)
            } else {
                comparePacketLists(subpackets, listOf(Packet(packet, packet.packetValue!!, mutableListOf())))
            }
        }

        private fun comparePacketLists(packetList1: List<Packet>, packetList2: List<Packet>) =
            packetList1.zip(packetList2).map { (p1, p2) ->
                p1.compareTo(p2)
            }.find { it != 0 } ?: (packetList1.size - packetList2.size)

        override fun toString(): String {
            return "packetValue=$packetValue&subpackets=$subpackets"
        }

        override fun equals(other: Any?): Boolean {
            return compareTo(other as Packet) == 0
        }
    }
}
