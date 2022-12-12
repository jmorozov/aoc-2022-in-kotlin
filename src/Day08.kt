fun main() {
    val inputData = readInput("Day08")
    part1(inputData)
    part2(inputData)
}

private fun part1(inputData: List<String>) {
    var visibleTrees = 0
    val (rows, cols) = buildGrid(inputData)
    for ((rowIdx, row) in rows.withIndex()) {
        if (rowIdx == 0 || rowIdx == rows.lastIndex) {
            visibleTrees += row.size
            continue
        }

        for ((colIdx, treeHigh) in row.withIndex()) {
            val isVisible = colIdx == 0 ||
                    colIdx == row.lastIndex ||
                    isVisibleFromLeft(row, colIdx, treeHigh) ||
                    isVisibleFromRight(row, colIdx, treeHigh) ||
                    isVisibleFromLeft(cols[colIdx], rowIdx, treeHigh) ||
                    isVisibleFromRight(cols[colIdx], rowIdx, treeHigh)

            if (isVisible) {
                visibleTrees++
            }
        }
    }

    println("Trees are visible from outside the grid: $visibleTrees")
}

private fun buildGrid(inputData: List<String>): Pair<Array<IntArray>, Array<IntArray>> {
    val rows = Array(inputData.size) { IntArray(0) }
    val cols = Array(inputData.first().length) { IntArray(inputData.size) }
    for ((rowIdx, line) in inputData.withIndex()) {
        val row = line.trim().toCharArray().map { it.digitToInt() }.toIntArray()
        rows[rowIdx] = row
        for ((colIdx, col) in row.withIndex()) {
            cols[colIdx][rowIdx] = col
        }
    }
    return Pair(rows, cols)
}

private fun isVisibleFromLeft(row: IntArray, colIdx: Int, treeHigh: Int): Boolean =
    row.sliceArray(0 until colIdx).all { it < treeHigh }

private fun isVisibleFromRight(row: IntArray, colIdx: Int, treeHigh: Int): Boolean =
    row.sliceArray(colIdx + 1 .. row.lastIndex).all { it < treeHigh }

private fun part2(inputData: List<String>) {
    var highestScenicScore = 0
    val (rows, cols) = buildGrid(inputData)

    for ((rowIdx, row) in rows.withIndex()) {
        if (rowIdx == 0 || rowIdx == rows.lastIndex) {
            continue
        }

        for ((colIdx, treeHigh) in row.withIndex()) {
            if (colIdx == 0 || colIdx == row.lastIndex) {
                continue
            }
            
            val scenicScore = leftScenicScore(row, colIdx, treeHigh) *
                    rightScenicScore(row, colIdx, treeHigh) *
                    leftScenicScore(cols[colIdx], rowIdx, treeHigh) *
                    rightScenicScore(cols[colIdx], rowIdx, treeHigh)

            if (scenicScore > highestScenicScore) {
                highestScenicScore = scenicScore
            }
        }
    }
    
    println("The highest scenic score possible for any tree: $highestScenicScore")
}

private fun leftScenicScore(row: IntArray, colIdx: Int, treeHigh: Int): Int {
    var visibleTrees = 0
    for (idx in colIdx - 1 downTo 0) {
        visibleTrees++
        if (row[idx] >= treeHigh) {
            break
        }
    }
    return visibleTrees
}

private fun rightScenicScore(row: IntArray, colIdx: Int, treeHigh: Int): Int {
    var visibleTrees = 0
    for (idx in colIdx + 1 .. row.lastIndex) {
        visibleTrees++
        if (row[idx] >= treeHigh) {
            break
        }
    }
    return visibleTrees
}
