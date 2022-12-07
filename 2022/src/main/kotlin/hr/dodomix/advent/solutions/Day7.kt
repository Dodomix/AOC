package hr.dodomix.advent.solutions

class Day7 {
    fun dayDirectory() = "day7"
    fun part1(input: List<String>): Long {
        return constructRootOfFilesystem(input).getSubdirectorySizes().filter { it < 100000 }.sum()
    }

    fun part2(input: List<String>): Long {
        val root = constructRootOfFilesystem(input)
        val totalSize = root.calculateTotalSize()
        val emptySpace = 70000000 - totalSize
        val requiredSpace = 30000000 - emptySpace
        return root.getSubdirectorySizes().filter { it > requiredSpace }.sorted().first()
    }

    private fun constructRootOfFilesystem(input: List<String>): Directory {
        val parent = Directory("/")
        input.fold(parent) { currentDirectory, line ->
            if (line.startsWith("$ cd")) {
                if (line.endsWith("/")) {
                    currentDirectory
                } else if (line.endsWith("..")) {
                    currentDirectory.parent!!
                } else {
                    val newDirectory = Directory(line.substring(5), currentDirectory)
                    currentDirectory.subdirectories.add(newDirectory)
                    newDirectory
                }
            } else if (line.startsWith("$ ls") || line.startsWith("dir")) {
                currentDirectory
            } else if (line.startsWith("dir")) {
                currentDirectory
            } else {
                val (fileSize, fileName) = line.split(" ")
                currentDirectory.files.add(File(fileSize.toLong(), fileName))
                currentDirectory
            }
        }
        return parent
    }

    private data class Directory(
        val name: String,
        val parent: Directory? = null,
        val subdirectories: MutableList<Directory> = mutableListOf(),
        val files: MutableList<File> = mutableListOf(),
        private var totalSize: Long? = null
    ) {
        fun calculateTotalSize(): Long {
            if (totalSize == null) {
                totalSize = files.sumOf { it.size } + subdirectories.sumOf { it.calculateTotalSize() }
            }
            return totalSize!!
        }

        fun getSubdirectorySizes(): List<Long> =
            subdirectories.flatMap { it.getSubdirectorySizes() } + calculateTotalSize()
    }

    private data class File(val size: Long, val name: String)
}
