package com.github.horitaku1124.ts_compiler.nodes

data class ExpressionDefineNode (
  var name: String,
  var type: String,
  var operationNode: OperationNode,
): NodeBase() {
  override fun toString(): String {
    return "$type $operationNode"
  }
}