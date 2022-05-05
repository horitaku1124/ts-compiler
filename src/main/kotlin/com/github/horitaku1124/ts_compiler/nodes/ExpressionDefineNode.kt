package com.github.horitaku1124.ts_compiler.nodes

import com.github.horitaku1124.ts_compiler.nodes.values.ValueNode

data class ExpressionDefineNode (
  var type: String,
  var leftNode: ValueNode,
  var rightNode: ValueNode,
): NodeBase() {
  override fun toString(): String {
    return "$type $leftNode $rightNode"
  }
}