fun main() {
    val inputData = readInput("Day04")
    part1(inputData)
    part2(inputData)
}

private fun part1(inputData: List<String>) {
    var oneRangeFullyContainTheOtherTimes = 0
    for (line in inputData) {
        val trimmedLine = line.trim()

        val parts = trimmedLine.split(",")

        val range1 = parts[0].toRange()
        val range2 = parts[1].toRange()

        if (range1.contains(range2) || range2.contains(range1)) {
            oneRangeFullyContainTheOtherTimes++
        }
    }

    println("One range fully contain the other: $oneRangeFullyContainTheOtherTimes")
}

fun String.toRange(): IntRange = this
    .split("-")
    .let { (a, b) -> a.toInt()..b.toInt() }

fun IntRange.contains(other: IntRange): Boolean = this.first <= other.first && this.last >= other.last

private fun part2(inputData: List<String>) {
    var rangesOverlapInPairs = 0
    for (line in inputData) {
        val trimmedLine = line.trim()

        val parts = trimmedLine.split(",")

        val range1 = parts[0].toRange()
        val range2 = parts[1].toRange()

        if (range1.overlaps(range2)) {
            rangesOverlapInPairs++
        }
    }

    println("The ranges overlap in pairs: $rangesOverlapInPairs")
}

fun IntRange.overlaps(other: IntRange) =
    this.first in other ||
            this.last in other ||
            other.first in this ||
            other.last in this