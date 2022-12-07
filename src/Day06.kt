fun main() {
    val line = readInput("Day06").first()
    part1(line)
    part2(line)
}

private fun part1(line: String) {
    val (isMarkerDetected, markerIdx) = detectMarker(MARKER_LENGTH, line)

    if (isMarkerDetected) {
        println("The first start-of-packet marker is detected: $markerIdx")
    } else {
        println("The first start-of-packet marker isn't detected")
    }
}

private fun detectMarker(markerSize: Int, line: String): Pair<Boolean, Int> {
    var fromIdx = 0
    var toIdx = markerSize
    var isMarkerDetected = false
    while (toIdx <= line.length) {
        val packetStarter = line.substring(fromIdx, toIdx)
        val characters = packetStarter.toCharArray().toHashSet()
        if (characters.size == markerSize) {
            isMarkerDetected = true
            break
        }
        fromIdx++
        toIdx++
    }
    
    return Pair(isMarkerDetected, toIdx)
}

private const val MARKER_LENGTH = 4

private fun part2(line: String) {
    val (isMarkerDetected, markerIdx) = detectMarker(MESSAGE_SIZE, line)

    if (isMarkerDetected) {
        println("The first start-of-message marker is detected: $markerIdx")
    } else {
        println("The first start-of-message marker isn't detected")
    }
}

private const val MESSAGE_SIZE = 14