import kotlin.math.absoluteValue

fun main() {
    val inputData = readInput("Day09")
    part2(inputData)
}

private fun part2(inputData: List<String>) {
    val visitedByTail = mutableSetOf<Point>()
    
    val startPoint = Point.start()
    
    var state = MutableList(ROPE_LENGTH) { _ -> startPoint }
    for (line in inputData) {
        val (direction, steps) = parseMovement(line) ?: continue

        state = when (direction) {
            "L" -> goLeft(state, visitedByTail, steps)
            "R" -> goRight(state, visitedByTail, steps)
            "U" -> goUp(state, visitedByTail, steps)
            "D" -> goDown(state, visitedByTail, steps)
            else -> state
        }
        println("STATE: $state")
    }
    
    println("Positions, which the tail of the rope visit at least once: ${visitedByTail.size}")
}

private const val ROPE_LENGTH = 10

private fun goLeft(rope: MutableList<Point>, visitedByTail: MutableSet<Point>, steps: Int): MutableList<Point> {
    var currentState = rope
    repeat(steps) {
        val newState = mutableListOf<Point>()
        var head = currentState.first().left()
        newState.add(head)
        for ((idx, tail) in currentState.withIndex()) {
            if (idx == 0) continue

            val newTail = if (idx == 1) goLeft(head, tail) else nextMove(head, tail)
            newState.add(newTail)

            head = newTail
        }
        currentState = newState
        visitedByTail.add(newState.last())
    }

    return currentState
}

private fun goLeft(head: Point, tail: Point): Point {
    if (noNeedHorizontalMoving(head, tail)) {
        return tail
    }

    return if (head.y == tail.y) {
        tail.left()
    } else {
        if (head.y > tail.y) {
            tail.up().left()
        } else {
            tail.down().left()
        }
    }
}

private fun noNeedHorizontalMoving(head: Point, tail: Point): Boolean = (tail.x - head.x).absoluteValue <= 1

private fun goRight(rope: MutableList<Point>, visitedByTail: MutableSet<Point>, steps: Int): MutableList<Point> {
    var currentState = rope
    repeat(steps) {
        val newState = mutableListOf<Point>()
        var head = currentState.first().right()
        newState.add(head)
        for ((idx, tail) in currentState.withIndex()) {
            if (idx == 0) continue

            val newTail = if (idx == 1) goRight(head, tail) else nextMove(head, tail)
            newState.add(newTail)

            head = newTail
        }
        currentState = newState
        visitedByTail.add(newState.last())
    }

    return currentState
}

private fun goRight(head: Point, tail: Point): Point {
    if (noNeedHorizontalMoving(head, tail)) {
        return tail
    }

    return if (head.y == tail.y) {
        tail.right()
    } else {
        if (head.y > tail.y) {
            tail.up().right()
        } else {
            tail.down().right()
        }
    }
}

private fun goUp(rope: MutableList<Point>, visitedByTail: MutableSet<Point>, steps: Int): MutableList<Point> {
    var currentState = rope
    repeat(steps) {
        val newState = mutableListOf<Point>()
        var head = currentState.first().up()
        newState.add(head)
        for ((idx, tail) in currentState.withIndex()) {
            if (idx == 0) continue

            val newTail = if (idx == 1) goUp(head, tail) else nextMove(head, tail)
            newState.add(newTail)

            head = newTail
        }
        currentState = newState
        visitedByTail.add(newState.last())
    }

    return currentState
}

private fun goUp(head: Point, tail: Point): Point {
    if (noNeedVerticalMoving(head, tail)) {
        return tail
    }

    return if (head.x == tail.x) {
        tail.up()
    } else {
        if (head.x > tail.x) {
            tail.right().up()
        } else {
            tail.left().up()
        }
    }
}

private fun noNeedVerticalMoving(head: Point, tail: Point): Boolean = (tail.y - head.y).absoluteValue <= 1

private fun goDown(rope: MutableList<Point>, visitedByTail: MutableSet<Point>, steps: Int): MutableList<Point> {
    var currentState = rope
    repeat(steps) {
        val newState = mutableListOf<Point>()
        var head = currentState.first().down()
        newState.add(head)
        for ((idx, tail) in currentState.withIndex()) {
            if (idx == 0) continue

            val newTail = if (idx == 1) goDown(head, tail) else nextMove(head, tail)
            newState.add(newTail)

            head = newTail
        }
        currentState = newState
        visitedByTail.add(newState.last())
    }

    return currentState
}

private fun goDown(head: Point, tail: Point): Point {
    if ((tail.y - head.y).absoluteValue <= 1) {
        return tail
    }

    return if (head.x == tail.x) {
        tail.down()
    } else {
        if (head.x > tail.x) {
            tail.right().down()
        } else {
            tail.left().down()
        }
    }
}

private fun nextMove(head: Point, tail: Point): Point {
    return when {
        tail.y - head.y > 1 -> goDown(head, tail)
        head.y - tail.y > 1 -> goUp(head, tail)
        head.x - tail.x > 1 -> goRight(head, tail)
        tail.x - head.x > 1 -> goLeft(head, tail)
        else -> tail
    }
}