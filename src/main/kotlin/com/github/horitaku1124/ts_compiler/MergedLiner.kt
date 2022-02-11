package com.github.horitaku1124.ts_compiler

class MergedLiner {
  data class LogicalLine(var lineNumber: Int, var tokens: List<String>)
  fun mergeLine(tokens: List<String>): List<LogicalLine> {
    val list = arrayListOf<LogicalLine>()
    var lineNum = 1
    var tokensOfLine = arrayListOf<String>()
    for(token in tokens) {
      if (token == ";" || token == "\n") {
        list.add(LogicalLine(lineNum, tokensOfLine))
        tokensOfLine = arrayListOf()
        lineNum++
      } else {
        if (token != " " && token != "\t") {
          tokensOfLine.add(token)
        }
      }
    }
    if (tokensOfLine.isNotEmpty()) {
      list.add(LogicalLine(lineNum, tokensOfLine))
    }
    return list
  }
}