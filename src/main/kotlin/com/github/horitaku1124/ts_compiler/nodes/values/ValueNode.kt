package com.github.horitaku1124.ts_compiler.nodes.values

import com.github.horitaku1124.ts_compiler.nodes.FirstClassObjectNode

open class ValueNode(var value: String): FirstClassObjectNode() {
  override fun toString(): String {
    return "ValueNode($value)"
  }
}