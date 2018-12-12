package advent.solutions

import advent.util.Util

object Day12 {

  def part1(lines: Array[String]): Int = {
    val parsedInput = lines.foldLeft(("", Map[String, String]().withDefaultValue(".")))({ case ((initialState, actions), line) =>
      if (line.startsWith("initial state: ")) (line.split("initial state: ")(1), actions)
      else if (line.isEmpty) {
        (initialState, actions)
      }
      else {
        val split = line.split(" => ")
        (initialState, actions.updated(split(0), split(1)))
      }
    })
    val resultState = Range(0, 20).foldLeft(parsedInput._1)((state, _) =>
      Range(-2, state.length + 2).foldLeft(List[String]())((acc, index) => {
        val key = new StringBuilder()
        if (index - 2 < 0 || index - 2 >= state.length) key.append(".")
        else key.append(state.charAt(index - 2))
        if (index - 1 < 0 || index - 1 >= state.length) key.append(".")
        else key.append(state.charAt(index - 1))
        if (index < 0 || index >= state.length) key.append(".")
        else key.append(state.charAt(index))
        if (index + 1 < 0 || index + 1 >= state.length) key.append(".")
        else key.append(state.charAt(index + 1))
        if (index + 2 < 0 || index + 2 >= state.length) key.append(".")
        else key.append(state.charAt(index + 2))
        acc :+ parsedInput._2(key.mkString)
      }).mkString(""))
    resultState.zip(Range((parsedInput._1.length - resultState.length) / 2, resultState.length))
      .foldLeft(0)({ case (result, (char, index)) =>
        if (char == '#') result + index
        else result
      })
  }

  def part2(lines: Array[String]): Any = {
    val parsedInput = lines.foldLeft(("", Map[String, String]().withDefaultValue(".")))({ case ((initialState, actions), line) =>
      if (line.startsWith("initial state: ")) (line.split("initial state: ")(1), actions)
      else if (line.isEmpty) {
        (initialState, actions)
      }
      else {
        val split = line.split(" => ")
        (initialState, actions.updated(split(0), split(1)))
      }
    })

    Stream.continually().foldLeft((parsedInput._1, 0, 0, Map[String, Int]()))({
      case ((state, index, charIndex, oldStateIndices), _) =>
        if (index == 114) {
          return state.zip(Range(charIndex, charIndex + state.length))
            .foldLeft(0)({ case (result, (char, currentCharIndex)) =>
              if (char == '#') result + currentCharIndex
              else result
            })
        }
        val newState = Range(-2, state.length + 3).foldLeft(List[String]())((acc, index) => {
          val key = new StringBuilder()
          if (index - 2 < 0 || index - 2 >= state.length) key.append(".")
          else key.append(state.charAt(index - 2))
          if (index - 1 < 0 || index - 1 >= state.length) key.append(".")
          else key.append(state.charAt(index - 1))
          if (index < 0 || index >= state.length) key.append(".")
          else key.append(state.charAt(index))
          if (index + 1 < 0 || index + 1 >= state.length) key.append(".")
          else key.append(state.charAt(index + 1))
          if (index + 2 < 0 || index + 2 >= state.length) key.append(".")
          else key.append(state.charAt(index + 2))
          acc :+ parsedInput._2(key.mkString)
        }).mkString("").dropWhile(_ == '.')
        if (oldStateIndices.contains(newState)) {
          println(oldStateIndices(newState))
          println(index)
        }
        (newState.reverse.dropWhile(_ == '.').reverse,
          index + 1,
          charIndex + state.length - newState.length + 3,
          oldStateIndices.updated(newState, index))
    })
  }

  def main(args: Array[String]): Unit = {
    println("Part 1 result: " + part1(Util.readFileLines(args(0))))
    println("Part 2 result: " + part2(Util.readFileLines(args(1))))
  }

}
