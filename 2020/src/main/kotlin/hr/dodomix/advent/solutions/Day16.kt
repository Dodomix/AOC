package hr.dodomix.advent.solutions

class Day16 {
    fun dayDirectory() = "day16"

    fun part1(input: List<String>): Int {
        val (fields, _, tickets) = extractInputData(input)
        return tickets.map { ticket ->
            ticket.filter { ticketValue ->
                fields.none { field ->
                    field.value.any {
                        ticketValue >= it.min && ticketValue <= it.max
                    }
                }
            }.sum()
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val (fields, myTicket, tickets) = extractInputData(input)
        val validTickets = tickets.filter { ticket ->
            ticket.all { ticketValue ->
                fields.any { field ->
                    field.value.any {
                        ticketValue >= it.min && ticketValue <= it.max
                    }
                }
            }
        }
        val fieldValidIndices = fields.map { field ->
            Pair(field.key, myTicket.indices.filter { index ->
                validTickets.plusElement(myTicket).all { ticket ->
                    field.value.any { constraint ->
                        ticket[index] >= constraint.min && ticket[index] <= constraint.max
                    }
                }
            })
        }.toMap()
        fieldValidIndices.forEach {
            println(it)
        } // done manually
        return myTicket[1].toLong() * myTicket[2].toLong() * myTicket[5].toLong() * myTicket[8].toLong() * myTicket[11].toLong() * myTicket[15].toLong()
    }

    private fun extractInputData(input: List<String>) = input.fold(
        Triple(emptyMap<String, List<Constraint>>(), emptyList<Int>(), emptyList<List<Int>>())
    ) { (currentFields, myCurrentTicket, tickets), line ->
        if (line.contains(" or ")) {
            val fields = line.split(": ").let { lineSplit ->
                Pair(lineSplit[0], lineSplit[1].split(" or ").map { constraint ->
                    constraint.split("-").let {
                        Constraint(it[0].toInt(), it[1].toInt())
                    }
                })
            }
            Triple(currentFields + fields, myCurrentTicket, tickets)
        } else if (myCurrentTicket.isEmpty() && line.contains(",")) {
            Triple(currentFields, line.split(",").map { it.toInt() }, tickets)
        } else if (myCurrentTicket.isNotEmpty() && line.contains(",")) {
            Triple(
                currentFields, myCurrentTicket,
                tickets.plusElement(line.split(",").map { it.toInt() })
            )
        } else {
            Triple(currentFields, myCurrentTicket, tickets)
        }
    }

    class Constraint(val min: Int, val max: Int)
}
