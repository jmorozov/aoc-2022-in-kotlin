import java.util.ArrayDeque
import java.util.Deque

fun main() {
    val inputData = readInput("Day07")
    part1(inputData)
    part2(inputData)
}

private fun dirSizesMap(inputData: List<String>): MutableMap<String, Int> {
    val dirMap: MutableMap<String, Int> = mutableMapOf()
    val dirStack: Deque<String> = ArrayDeque<String>()
    for (line in inputData) {
        if (line.startsWith("$ cd ")) {
            handleCommand(line, dirStack)
        } else {
            handleFile(line, dirStack, dirMap)
        }
    }
    return dirMap
}

private fun part1(inputData: List<String>) {
    val totalDirSum = dirSizesMap(inputData)
        .values
        .filter { it <= MAX_DIR_SIZE_FOR_REMOVING }
        .sum()
    
    println("The sum of the total sizes of filtered directories: $totalDirSum")
}

private const val MAX_DIR_SIZE_FOR_REMOVING  = 100_000

private fun handleCommand(line: String, dirStack: Deque<String>) {
    val dir = line.substringAfter("$ cd ").trim()
    if (dir.isEmpty()) {
        return
    }
    
    if (dir == "..") {
        // move out
        dirStack.removeLast()
    } else {
        // move in
        dirStack.addLast(dir)
    }
}
private fun handleFile(line: String, dirStack: Deque<String>, dirMap: MutableMap<String, Int>) {
    val fileSize = parseFileSize(line) ?: return

    var path = ""
    for (dir in dirStack) {
        path += if (dir == ROOT_DIR) dir else "/$dir"
        dirMap[path] = (dirMap[path] ?: 0) + fileSize
    }
}

private const val ROOT_DIR = "/"

private fun parseFileSize(line: String): Int? {
    val regex = """(?<fileSize>\d+) .*""".toRegex()
    val matchResult = regex.find(line) ?: return null
    val (fileSizeStr) = matchResult.destructured
    
    return fileSizeStr.toInt()
}

private fun part2(inputData: List<String>) {
    val dirMap = dirSizesMap(inputData)
    
    val usedSpace = dirMap[ROOT_DIR] ?: 0
    
    val minNeededUnusedSpace = NEEDED_UNUSED_SPACE - (TOTAL_DISK_SPACE - usedSpace)
    val dirSizeForRemoving = dirMap.values.filter { it >= minNeededUnusedSpace }.min()
    
    println("""
        Size of the smallest directory that, if deleted, would free up enough space on the filesystem to run the update:
        $dirSizeForRemoving
        """.trimIndent()
    )
}

private const val TOTAL_DISK_SPACE = 70_000_000
private const val NEEDED_UNUSED_SPACE = 30_000_000