fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (winningString, elfString) = line.split(":")[1].split("|")

            val numberRegex = Regex("[0-9]+")

            val winningList = numberRegex.findAll(winningString).map { it.value.toInt() }
            val elfList = numberRegex.findAll(elfString).map { it.value.toInt() }

            var cardPoints = 0
            elfList.forEach { elfNum ->
                if (winningList.contains(elfNum)) {
                    if (cardPoints == 0) cardPoints = 1
                    else cardPoints *= 2
                }
            }
            cardPoints
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val (_, winningString, elfString) = line.split(":", "|")
            val numberRegex = Regex("[0-9]+")

            val winningList = numberRegex.findAll(winningString).map { it.value.toInt() }
            val elfList = numberRegex.findAll(elfString).map { it.value.toInt() }

            winningList.count { it in elfList }
        }.let { winningNumbers ->
            winningNumbers.reversed().fold(emptyList<Int>()) { acc, count ->
                val sum = 1 + (0..<count).sumOf { acc[it] }
                listOf(sum) + acc
            }
        }.sum()
    }


    val input = readInput("Day04")

    val part1Result = part1(input)
    check(part1Result == 25651)
    part1Result.println()

    val part2Result = part2(input)
    check(part2Result == 19499881)
    part2Result.println()
}