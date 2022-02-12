package com.github.horitaku1124.ts_compiler

import com.github.horitaku1124.ts_compiler.nodes.*
import com.github.horitaku1124.ts_compiler.nodes.values.StringNode
import com.github.horitaku1124.ts_compiler.nodes.values.ValueNode

class TreeBuilder {
  fun buildAst(tokens: List<MergedLiner.LogicalLine>): List<List<NodeBase>> {
    val lines = arrayListOf<List<NodeBase>>()
    for (line in tokens) {
      lines.add(buildInner(line.tokens, 0).first)
    }

    return lines
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
        for (token2 in temp) {
          if (token2.matches("\\d+".toRegex())) {

          } else if (token2.matches("[a-zA-Z\\d]+".toRegex())) {
            expression.add(VariableCallNode(token2))
          }
        }

        return Pair(expression, index)
      } else if (token.startsWith("\"")) {
        expression.add(StringNode(token.substring(1, token.length - 1)))
      } else if (token == "var" || token == "let" || token == "const") {
        val nextToken = tokens[index++]
        val variable = VariableDefineNode(nextToken)

        if (tokens[index] == "=") {
          variable.withValue = ValueNode(tokens[index + 1])
          index += 2
        }

        expression.add(variable)
      } else {
        temp.add(token)
      }
    }
    return Pair(expression, index)
  }
}