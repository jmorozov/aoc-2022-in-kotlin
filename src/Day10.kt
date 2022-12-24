fun main() {
    val inputData = readInput("Day10")
    part1(inputData)
    part2(inputData)
}

private fun part1(inputData: List<String>) {
    var cycleCounter = 0
    var cycleStrengthCounter = FIRST_STRENGTH_MARK
    var x = 1
    var signalStrength = 0
    for (line in inputData) {
        val command = line.trim()
        when {
            command == "noop" -> {
                cycleCounter += 1
                val (strength, nextCycleStrengthStep) = computeSignalStrength(
                    cycleCounter,
                    x,
                    cycleStrengthCounter
                )
                signalStrength += strength
                cycleStrengthCounter += nextCycleStrengthStep
            }
            command.startsWith("addx ") -> {
                cycleCounter += 1
                val (strength1, nextCycleStrengthStep1) = computeSignalStrength(
                    cycleCounter,
                    x,
                    cycleStrengthCounter
                )
                signalStrength += strength1
                cycleStrengthCounter += nextCycleStrengthStep1
                
                cycleCounter += 1
                val (strength2, nextCycleStrengthStep2) = computeSignalStrength(
                    cycleCounter,
                    x,
                    cycleStrengthCounter
                )
                signalStrength += strength2
                cycleStrengthCounter += nextCycleStrengthStep2
                x += parseAddx(command)
            }
            else -> continue
        }
    }

    println("The sum of signal strengths: $signalStrength")
}

private const val FIRST_STRENGTH_MARK = 20

private fun parseAddx(command: String): Int {
    val regex = """addx (?<value>-?\d+)""".toRegex()
    val matchResult = regex.find(command) ?: return 0
    val (value) = matchResult.destructured

    return value.toInt()
}

private fun computeSignalStrength(cycleCounter: Int, x: Int, strengthCycle: Int): Pair<Int, Int> {
    if (strengthCycle != cycleCounter) {
        return Pair(0, 0)
    }
    
    return Pair(x * strengthCycle, CYCLE_STRENGTH_STEP)
}

private const val CYCLE_STRENGTH_STEP = 40

private fun part2(inputData: List<String>) {
    var x = 1
    var crt = ""
    for (line in inputData) {
        val command = line.trim()
        when {
            command == "noop" -> {
                crt = draw(crt, x)
            }
            command.startsWith("addx ") -> {
                crt = draw(crt, x)
                crt = draw(crt, x)
                
                x += parseAddx(command)
            }
            else -> continue
        }
    }
    displayRaw(crt)
}

private fun getSpritePosition(x: Int): IntRange = x - 1..x + 1

private fun draw(crt: String, x: Int): String {
    val nextPixel= drawPixel(crt, x)
    return displayRaw(crt + nextPixel)
}

private fun drawPixel(crt: String, x: Int): String {
    val crtPosition = crt.length
    val spritePosition = getSpritePosition(x)

    if (crtPosition in spritePosition) {
        return "#"
    }
    
    return "."
}

private fun displayRaw(crt: String): String {
    if (crt.length < DISPLAY_LENGTH) {
        return crt
    }
    println(crt)
    return ""
}

private const val DISPLAY_LENGTH = 40