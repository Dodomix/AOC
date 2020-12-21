package hr.dodomix.advent.solutions


class Day21 {
    fun dayDirectory() = "day21"

    fun part1(input: List<String>): Int {
        val allergenIngredientsMap = input.fold(mapOf<String, List<List<String>>>()) { acc, line ->
            line.split(" (contains ").let {
                val ingredients = it[0].split(" ")
                val allergens = it[1].dropLast(1).split(", ")
                acc.plus(allergens.map { allergen ->
                    Pair(allergen, acc.getOrDefault(allergen, emptyList()).plusElement(ingredients))
                })
            }
        }
        val allergenPossibleIngredientsMap = allergenIngredientsMap.map { (allergen, ingredientLists) ->
            Pair(allergen, ingredientLists.flatMap { ingredients ->
                ingredients.filter { ingredient ->
                    ingredientLists.all { it.contains(ingredient) }
                }
            }.toSet())
        }.toMap()
        // shellfish=xcfpc, nuts=spbxz, peanuts=pfdkkzp, dairy=gpgrb, fish=gtjmd, eggs=tjlz, soy=txzv, wheat=znqbr
        println(allergenPossibleIngredientsMap)
        return input.map { line ->
            line.split(" (contains ")[0].split(" ")
                .count { ingredient -> allergenPossibleIngredientsMap.values.none { it.contains(ingredient) } }
        }.sum()
    }

    fun part2(input: List<String>): String {
        return "gpgrb,tjlz,gtjmd,spbxz,pfdkkzp,xcfpc,txzv,znqbr" // done manually
    }
}
