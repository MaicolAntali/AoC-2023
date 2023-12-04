private val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val first = it.first { c -> c.isDigit() }
            val last = it.last { c -> c.isDigit() }

            (first.digitToInt() * 10) + last.digitToInt()
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            // Matches `words` or `digits`
            val fistNumberRegex = Regex("(${words.joinToString("|")}|[1-9])")
            val lastNumberRegex = Regex("(${words.joinToString("|") { it.reversed() }}|[1-9])")

            val first = fistNumberRegex.find(line)?.value ?: "Not Found"
            val last = lastNumberRegex.find(line.reversed())?.value ?: "Not Found"

            (first.wordToInt() * 10) + last.reversed().wordToInt()
        }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

private fun String.wordToInt(): Int {
    return if (this.length == 1) {
        this.toInt()
    } else {
        val indexOf = words.indexOf(this)
        if (indexOf == -1) error("Unable to convert: $this into a number.")
        indexOf + 1
    }
}