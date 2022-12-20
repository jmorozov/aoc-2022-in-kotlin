import kotlin.math.absoluteValue

fun main() {
    val inputData = readInput("Day09")
    part1(inputData)
}

private fun part1(inputData: List<String>) {
    val visitedByTail = mutableSetOf<Point>()
    
    val startPoint = Point.start()
    visitedByTail.add(startPoint)
    
    var state = RopeState(
        head = startPoint,
        tail = startPoint
    )
    for (line in inputData) {
        val (direction, steps) = parseMovement(line) ?: continue

        state = when (direction) {
            "L" -> goLeft(state, visitedByTail, steps)
            "R" -> goRight(state, visitedByTail, steps)
            "U" -> goUp(state, visitedByTail, steps)
            "D" -> goDown(state, visitedByTail, steps)
            else -> state
        }
    }
    
    println("Positions, which the tail of the rope visit at least once: ${visitedByTail.size}")
}

private data class RopeState(
    val head: Point,
    val tail: Point
)

private fun goLeft(currentState: RopeState, visitedByTail: MutableSet<Point>, steps: Int): RopeState {
    var newState = currentState
    for (step in 1..steps) {
        val head = newState.head.left()
        var tail = newState.tail
        if ((tail.x - head.x).absoluteValue > 1) {
            tail = if (head.y == tail.y) {
                tail.left()
            } else {
                if (head.y > tail.y) {
                    tail.up().left()
                } else {
                    tail.down().left()
                }
            }
            visitedByTail.add(tail)
        }
        newState = RopeState(head, tail)
    }
    
    return newState
}

private fun goRight(currentState: RopeState, visitedByTail: MutableSet<Point>, steps: Int): RopeState {
    var newState = currentState
    for (step in 1..steps) {
        val head = newState.head.right()
        var tail = newState.tail
        if ((tail.x - head.x).absoluteValue > 1) {
            tail = if (head.y == tail.y) {
                tail.right()
            } else {
                if (head.y > tail.y) {
                    tail.up().right()
                } else {
                    tail.down().right()
                }
            }
            visitedByTail.add(tail)
        }
        newState = RopeState(head, tail)
    }

    return newState
}

private fun goUp(currentState: RopeState, visitedByTail: MutableSet<Point>, steps: Int): RopeState {
    var newState = currentState
    for (step in 1..steps) {
        val head = newState.head.up()
        var tail = newState.tail
        if ((tail.y - head.y).absoluteValue > 1) {
            tail = if (head.x == tail.x) {
                tail.up()
            } else {
                if (head.x > tail.x) {
                    tail.right().up()
                } else {
                    tail.left().up()
                }
            }
            visitedByTail.add(tail)
        }
        newState = RopeState(head, tail)
    }

    return newState
}

private fun goDown(currentState: RopeState, visitedByTail: MutableSet<Point>, steps: Int): RopeState {
    var newState = currentState
    for (step in 1..steps) {
        val head = newState.head.down()
        var tail = newState.tail
        if ((tail.y - head.y).absoluteValue > 1) {
            tail = if (head.x == tail.x) {
                tail.down()
            } else {
                if (head.x > tail.x) {
                    tail.right().down()
                } else {
                    tail.left().down()
                }
            }
            visitedByTail.add(tail)
        }
        newState = RopeState(head, tail)
    }

    return newState
}
