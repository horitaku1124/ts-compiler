package com.github.horitaku1124.ts_compiler.nodes

data class FunctionCallNode(
  val functionName: String,
  val param: List<NodeBase>): NodeBase()