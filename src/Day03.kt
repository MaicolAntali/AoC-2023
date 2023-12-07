private data class Position(val row: Int, val column: Int)

fun main() {
    fun getSymbols(grid: List<String>) = buildMap {
        grid.forEachIndexed { row, line ->
            line.forEachIndexed { column, c ->
                if (!c.isDigit() && c != '.') {
                    put(Position(row, column), c)
                }
            }
        }
    }

    fun getPositionsAroundNumber(row: Int, colRange: IntRange, grid: List<String>) = buildList {
        for (r in -1..1) {
            for (c in colRange.first - 1..colRange.last + 1) {
                val possibleRow = row + r
                if (possibleRow in grid.indices && c in grid[possibleRow].indices) {
                    if (possibleRow != row || c !in colRange) {
                        add(Position(possibleRow, c))
                    }
                }

            }
        }
    }

    fun part1(input: List<String>): Int {
        val symbols = getSymbols(input)
        val numberRegex = Regex("[0-9]+")

        return input.flatMapIndexed { row, line ->
            numberRegex.findAll(line).flatMap { match ->
                getPositionsAroundNumber(row, match.range, input).mapNotNull { position ->
                    symbols[position]?.let { match.value.toInt() }
                }
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val symbols = getSymbols(input)
        val numberRegex = Regex("[0-9]+")

        val gears = buildMap<Position, MutableList<Int>> {
            input.forEachIndexed { row, line ->
                numberRegex.findAll(line).forEach { match ->
                    getPositionsAroundNumber(row, match.range, input).forEach { position ->
                        if (symbols[position] == '*') {
                            computeIfAbsent(position) { mutableListOf() }.add(match.value.toInt())
                        }
                    }
                }
            }
        }

        return gears.values
            .filter { it.size >= 2 }
            .sumOf { it.reduce { acc, i -> acc * i } }
    }

    val input = readInput("Day03")

    val part1Result = part1(input)
    check(part1Result == 527364)
    part1Result.println()

    val part2Result = part2(input)
    check(part2Result == 79026871)
    part2Result.println()
}