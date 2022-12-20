data class Point(val x: Int, val y: Int) {
    fun left() = copy(x = x - 1)

    fun right() = copy(x = x + 1)

    fun up() = copy(y = y + 1)

    fun down() = copy(y = y - 1)

    companion object {
        fun start() = Point(0, 0)
    }
}

fun parseMovement(line: String): Pair<String, Int>? {
    val regex = """(?<direction>[LRUD]) (?<steps>\d+)""".toRegex()
    val matchResult = regex.find(line) ?: return null
    val (direction, steps) = matchResult.destructured

    return Pair(direction, steps.toInt())
}