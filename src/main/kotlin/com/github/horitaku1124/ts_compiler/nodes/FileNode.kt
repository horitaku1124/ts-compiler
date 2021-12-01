package com.github.horitaku1124.ts_compiler.nodes

data class FileNode(var fileName: String,
                    var expressions: List<NodeBase>): NodeBase() {
  override fun toString(): String {
    val str = StringBuffer()
    str.append("[").append(fileName).append("]\n")
    for (line in expressions) {
      str.append(line).append("\n")
    }
    return str.toString()
  }
}