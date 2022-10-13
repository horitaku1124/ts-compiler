package com.github.horitaku1124.ts_compiler.nodes

import com.github.horitaku1124.ts_compiler.nodes.values.ValueNode

data class VariableDefineNode(
  var name: String,
  var type: String? = null,
  var hasAssignment: Boolean = false,
  var withValue: ValueNode? = null,
  ): NodeBase() {

  override fun toString(): String {
    return "val $name:$type=$withValue"
  }
}