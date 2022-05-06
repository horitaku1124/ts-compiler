package com.github.horitaku1124.ts_compiler.nodes

data class OperationNode(
  val type: String,
  val leftNode: NodeBase,
  val rightNode: NodeBase,
): NodeBase() {
  override fun toString(): String {
    return "$leftNode $type $rightNode"
  }
}