import java.util.ArrayDeque
import java.util.Deque

fun main() {
    val inputData = readInput("Day05")
    part1(inputData)
    part2(inputData)
}

private fun part1(inputData: List<String>) {
    val stacks = mutableListOf<Deque<Char>>()
    
    for (line in inputData) {
        when {
            line.startsWith(" 1 ") || line.isBlank() -> continue
            line.startsWith("move") -> moveCrate(line, stacks)
            else -> parseStacksLine(line, stacks)
        }
    }
    
    println("PART 1. Crate ends up on top of each stack: ${getTop(stacks)}")
}

private fun parseStacksLine(line: String, stacks: MutableList<Deque<Char>>) {
    val regex = """(\s{3})\s?|(\[[A-Z]\])\s?""".toRegex()
    var matchResult = regex.find(line)
    var idx = 0
    while (matchResult != null) {
        val value = matchResult.value
        
        var stack: Deque<Char>? = stacks.getOrNull(idx)
        if (stack == null) {
            stack = ArrayDeque<Char>()
            stacks.add(stack)
        }
        
        if (value.isNotBlank()) {
            // TODO: add proper validation, of course
            val crate = value.trim()[1]
            stack.addFirst(crate)
        }

        idx++
        matchResult = matchResult.next()
    }
}

private fun moveCrate(line: String, stacks: MutableList<Deque<Char>>) {
    val regex = """move (?<timesStr>\d+) from (?<fromStackStr>\d+) to (?<toStackStr>\d+)""".toRegex()
    val matchResult = regex.find(line) ?: return
    val (timesStr, fromStackStr, toStackStr) = matchResult.destructured
    
    var times = timesStr.toInt()
    val fromStack = stacks.getOrNull(fromStackStr.toInt() - 1) ?: return
    val toStack = stacks.getOrNull(toStackStr.toInt() - 1) ?: return

    while (times != 0 && fromStack.isNotEmpty()) {
        val crate = fromStack.pollLast()
        toStack.addLast(crate)
        times--
    }
}

private fun getTop(stacks: MutableList<Deque<Char>>): String = stacks
    .filter { it.isNotEmpty() }
    .map { it.last }
    .joinToString(separator = "")

private fun part2(inputData: List<String>) {
    val stacks = mutableListOf<Deque<Char>>()

    for (line in inputData) {
        when {
            line.startsWith(" 1 ") || line.isBlank() -> continue
            line.startsWith("move") -> moveCreateByCrateMover9001(line, stacks)
            else -> parseStacksLine(line, stacks)
        }
    }

    println("PART 2. Crate ends up on top of each stack: ${getTop(stacks)}")
}

private fun moveCreateByCrateMover9001(line: String, stacks: MutableList<Deque<Char>>) {
    val regex = """move (?<createsStr>\d+) from (?<fromStackStr>\d+) to (?<toStackStr>\d+)""".toRegex()
    val matchResult = regex.find(line) ?: return
    val (createsStr, fromStackStr, toStackStr) = matchResult.destructured

    var creates = createsStr.toInt()
    val fromStack = stacks.getOrNull(fromStackStr.toInt() - 1) ?: return
    val toStack = stacks.getOrNull(toStackStr.toInt() - 1) ?: return

    val buffer = ArrayDeque<Char>(creates)
    while (creates != 0 && fromStack.isNotEmpty()) {
        val crate = fromStack.pollLast()
        buffer.addFirst(crate)
        creates--
    }
    toStack.addAll(buffer)
}