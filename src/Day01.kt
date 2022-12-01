import java.util.PriorityQueue

fun main() {
    val inputData = readInput("Day01")
    part1(inputData)
    part2(inputData)
}

fun part1(inputData: List<String>) {
    var theMostTotalCalories = 0
    var currentElfCalories = 0
    for (line in inputData) {
        when(line.trim()) {
            "" -> {
                if (currentElfCalories > theMostTotalCalories) {
                    theMostTotalCalories = currentElfCalories
                }
                currentElfCalories = 0
            }
            else -> {
                val caloriesNumber = line.toInt()
                currentElfCalories += caloriesNumber
            }
        }
    }
    if (currentElfCalories > theMostTotalCalories) {
        theMostTotalCalories = currentElfCalories
    }

    println("The most total calories: $theMostTotalCalories")
}

private const val NUMBER_OF_TOPS = 3

fun part2(inputData: List<String>) {
    var currentElfCalories = 0
    val queue = PriorityQueue<Int>(NUMBER_OF_TOPS)

    for (line in inputData) {
        when(line.trim()) {
            "" -> {
                val minCaloriesNumber: Int? = queue.peek()
                when {
                    minCaloriesNumber == null || queue.size < NUMBER_OF_TOPS -> queue.add(currentElfCalories)
                    minCaloriesNumber < currentElfCalories -> {
                        queue.poll()
                        queue.add(currentElfCalories)
                    }
                }
                currentElfCalories = 0
            }
            else -> {
                val caloriesNumber = line.toInt()
                currentElfCalories += caloriesNumber
            }
        }
    }
    val minCaloriesNumber: Int? = queue.peek()
    if (minCaloriesNumber != null) {
        if (queue.size < NUMBER_OF_TOPS) {
            queue.add(currentElfCalories)
        } else if (minCaloriesNumber < currentElfCalories) {
            queue.poll()
            queue.add(currentElfCalories)
        }
    }

    val theMostNTotalCalories = queue.sum()
    println("The most $NUMBER_OF_TOPS total calories: $theMostNTotalCalories")
}
