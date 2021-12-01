package com.github.horitaku1124.ts_compiler

import com.github.horitaku1124.ts_compiler.nodes.*

class TreeBuilder {
  fun buildAst(tokens: List<String>): List<NodeBase> {
    return buildInner(tokens, 0).first
  }
  private fun buildInner(tokens: List<String>, start: Int): Pair<List<NodeBase>, Int> {
    var index = start
    val temp = arrayListOf<String>()
    val expression = arrayListOf<NodeBase>()
    while(index < tokens.size) {
      val token = tokens[index]
      println("$index, $token")
      index++

      if (token == "(") {
        val (list, endIndex) = buildInner(tokens, index)
        index = endIndex + 1
        expression.add(FunctionCallNode(temp.joinToString(""), list))
        temp.clear()
      } else if (token == ")") {
        return Pair(expression, index)
      } else if (token.startsWith("\"")) {
        expression.add(StringNode(token.substring(1, token.length - 1)))
      } else {
        temp.add(token)
      }

    }
    return Pair(expression, index)
  }
}