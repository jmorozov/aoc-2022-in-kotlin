fun main() {
    val inputData = readInput("Day03")

    part1(inputData)
    part2(inputData)
}

private const val ASCII_DIFF_UPPER_CASE = 38
private const val ASCII_DIFF_LOWER_CASE = 96
fun Char.priority(): Int = when {
    this.isLowerCase() -> this.code - ASCII_DIFF_LOWER_CASE
    this.isUpperCase() -> this.code - ASCII_DIFF_UPPER_CASE
    else -> 0
}

private fun part1(inputData: List<String>) {
    var prioritiesSum = 0
    for (line in inputData) {
        val trimmedLine = line.trim()
        val partSize = trimmedLine.length / 2
        val part1: Set<Char> = trimmedLine.take(partSize).toSet()
        val part2: Set<Char> = trimmedLine.takeLast(partSize).toSet()
        val priority: Int = part1.intersect(part2).first().priority()
        prioritiesSum += priority
    }

    println("The sum of the priorities: $prioritiesSum")
}

private fun part2(inputData: List<String>) {
    var elf1Items: Set<Char> = setOf()
    var elf2Items: Set<Char> = setOf()
    var elf3Items: Set<Char> = setOf()

    var groupPrioritiesSum = 0
    for ((index, line) in inputData.withIndex()) {
        val trimmedLine = line.trim()
        val idx = index + 1
        val isLastInGroup = idx % 3 == 0
        val isSecondInGroup = idx % 2 == 0
        when {
            isLastInGroup -> elf3Items = trimmedLine.toSet()
            isSecondInGroup -> elf2Items = trimmedLine.toSet()
            else -> elf1Items = trimmedLine.toSet()
        }
        if (isLastInGroup) {
            val priority: Int = elf1Items
                .intersect(elf2Items)
                .intersect(elf3Items)
                .first()
                .priority()

            groupPrioritiesSum += priority
        }
    }
    println("The sum of the groups priorities: $groupPrioritiesSum")
}