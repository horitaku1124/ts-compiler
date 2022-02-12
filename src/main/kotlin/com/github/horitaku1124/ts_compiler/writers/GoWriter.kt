package com.github.horitaku1124.ts_compiler.writers

import com.github.horitaku1124.ts_compiler.nodes.FileNode
import com.github.horitaku1124.ts_compiler.nodes.FunctionCallNode
import com.github.horitaku1124.ts_compiler.nodes.VariableCallNode
import com.github.horitaku1124.ts_compiler.nodes.values.StringNode
import com.github.horitaku1124.ts_compiler.nodes.VariableDefineNode
import java.nio.file.Path
import kotlin.io.path.writer

class GoWriter {
  fun write(node: FileNode, saveTo: Path) {
    val goExpression = StringBuffer()
    var usrFmt = false
    var indent = "\t"
    for(line in node.lines) {
      for (expression in line) {
        if (expression is FunctionCallNode) {
          val funcName = expression.functionName
          val params = expression.param
          goExpression.append(indent)
          if (funcName == "console.log") {
            goExpression.append("fmt.Println(")
            usrFmt = true
            val goParams = arrayListOf<String>()
            for (param in params) {
              if (param is StringNode) {
                goParams.add("\"" + param.value + "\"")
              }
              if (param is VariableCallNode) {
                goParams.add(param.name)
              }
            }
            goExpression.append(goParams.joinToString(","))
            goExpression.append(")\n")
          }
        }
        if (expression is VariableDefineNode) {
          goExpression
            .append(indent)
          if (expression.withValue == null) {
            if (expression.type == null) {
              throw RuntimeException("cant define")
            }
            val type = expression.type
            if (type == "number") {
              goExpression
                .append("var ")
                .append(expression.name)
                .append(" float")
                .append("\n")
            }
          } else {
            goExpression
              .append(expression.name)
              .append(" := ")
              .append(expression.withValue!!.value)
              .append("\n")
          }
        }
      }
    }
    saveTo.writer().use { writer ->
      writer.write("package main\n\n")
      if (usrFmt) {
        writer.write("import \"fmt\"\n\n")
      }
      writer.write("func main() {\n")
      writer.write(goExpression.toString())
      writer.write("}\n")
    }
  }
}