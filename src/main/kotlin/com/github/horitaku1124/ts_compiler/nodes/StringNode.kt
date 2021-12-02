package com.github.horitaku1124.ts_compiler.nodes

data class StringNode(var value: String): FirstClassObjectNode() {
  override fun toString(): String {
    return "String: \"$value\""
  }
}