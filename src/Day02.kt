fun main() {
    data class Set(val red: Int, val green: Int, val blue: Int)
    data class Game(val id: Int, val sets: List<Set>)

    fun getGameID(s: String): Int {
        val value = Regex("(?!Game )[0-9]+").find(s)?.value ?: "Not Found"

        check(value != "Not Found") { "I have not found the game ID." }
        return value.toInt()
    }

    fun getGames(input: List<String>) = input.map { line ->
        val (game, sets) = line.split(":")

        sets.split(";").map { set ->
            val redCubes = Regex("[0-9]+(?= red)").find(set)?.value ?: "0"
            val greenCubes = Regex("[0-9]+(?= green)").find(set)?.value ?: "0"
            val blueCubes = Regex("[0-9]+(?= blue)").find(set)?.value ?: "0"

            Set(redCubes.toInt(), greenCubes.toInt(), blueCubes.toInt())
        }.let {
            Game(getGameID(game), it)
        }
    }


    fun part1(input: List<String>): Int {
        return getGames(input)
            .filter { game -> game.sets.all { it.red <= 12 && it.green <= 13 && it.blue <= 14 } }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}