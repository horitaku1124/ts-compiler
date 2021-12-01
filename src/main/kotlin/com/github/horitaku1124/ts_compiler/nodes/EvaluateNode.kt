package com.github.horitaku1124.ts_compiler.nodes

class EvaluateNode(var children: List<NodeBase>): NodeBase() {
  override fun toString(): String {
    return "EvaluateNode: $children"
  }
}