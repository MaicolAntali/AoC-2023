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
        return input.size
    }

    val input = readInput("Day03")

    val part1Result = part1(input)
    part1Result.println()
    check(part1Result == 527364)

    part2(input).println()
}