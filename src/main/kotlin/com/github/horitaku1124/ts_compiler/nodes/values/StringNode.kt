package com.github.horitaku1124.ts_compiler.nodes.values

data class StringNode(var value1: String): ValueNode(value1) {
  override fun toString(): String {
    return "String: \"$value1\""
  }
}