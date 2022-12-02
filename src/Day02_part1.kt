import java.util.EnumMap

fun main() {
    val inputData = readInput("Day02")
    var myTotalScore = 0
    for (line in inputData) {
        val trimmedLine = line.trim()
        val opponent = ShapePart1.from(trimmedLine.take(1))
        val mine = ShapePart1.from(trimmedLine.takeLast(1))

        val shapePoints = mine.point
        val roundOutcomePoints = RoundOutcomePart1.get(opponent, mine).point
        myTotalScore += shapePoints + roundOutcomePoints
    }

    println("My total score: $myTotalScore")
}

enum class ShapePart1(val point: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
    UNKNOWN(0);

    companion object {
        fun from(str: String): ShapePart1 = when (str) {
            "A", "X" -> ROCK
            "B", "Y" -> PAPER
            "C", "Z" -> SCISSORS
            else -> UNKNOWN
        }
    }
}

enum class RoundOutcomePart1(val point: Int) {
    LOST(0),
    DRAW(3),
    WON(6);

    companion object {
        private val defeats: EnumMap<ShapePart1, ShapePart1> = EnumMap(mapOf(
            ShapePart1.ROCK to ShapePart1.SCISSORS,
            ShapePart1.PAPER to ShapePart1.ROCK,
            ShapePart1.SCISSORS to ShapePart1.PAPER,
            ShapePart1.UNKNOWN to ShapePart1.UNKNOWN
        ))

        fun get(opponent: ShapePart1, mine: ShapePart1): RoundOutcomePart1 = when {
            opponent == mine -> DRAW
            defeats[opponent] == mine -> LOST
            else -> WON
        }
    }
}