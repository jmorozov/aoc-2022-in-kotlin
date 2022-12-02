import java.util.EnumMap

fun main() {
    val inputData = readInput("Day02")
    var myTotalScore = 0
    for (line in inputData) {
        val trimmedLine = line.trim()
        val opponent = Shape.from(trimmedLine.take(1))
        val roundOutcome = RoundOutcome.from(trimmedLine.takeLast(1))

        val roundOutcomePoints = roundOutcome.point
        val shapePoints = Shape.chooseForOpponentAndRoundOutcome(opponent, roundOutcome).point
        myTotalScore += shapePoints + roundOutcomePoints
    }

    println("My total score: $myTotalScore")
}

enum class Shape(val point: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3),
    UNKNOWN(0);

    companion object {
        private val defeat: EnumMap<Shape, Shape> = EnumMap(mapOf(
            ROCK to SCISSORS,
            PAPER to ROCK,
            SCISSORS to PAPER,
            UNKNOWN to UNKNOWN
        ))
        private val win: EnumMap<Shape, Shape> = EnumMap(mapOf(
            ROCK to PAPER,
            PAPER to SCISSORS,
            SCISSORS to ROCK,
            UNKNOWN to UNKNOWN
        ))

        fun from(str: String): Shape = when (str) {
            "A" -> ROCK
            "B" -> PAPER
            "C" -> SCISSORS
            else -> UNKNOWN
        }

        fun chooseForOpponentAndRoundOutcome(opponent: Shape, roundOutcome: RoundOutcome): Shape =
            when (roundOutcome) {
                RoundOutcome.LOST -> defeat[opponent] ?: UNKNOWN
                RoundOutcome.DRAW -> opponent
                RoundOutcome.WON -> win[opponent] ?: UNKNOWN
            }
    }
}

enum class RoundOutcome(val point: Int) {
    LOST(0),
    DRAW(3),
    WON(6);

    companion object {
        fun from(str: String): RoundOutcome = when (str) {
            "X" -> LOST
            "Y" -> DRAW
            "Z" -> WON
            else -> LOST
        }
    }
}