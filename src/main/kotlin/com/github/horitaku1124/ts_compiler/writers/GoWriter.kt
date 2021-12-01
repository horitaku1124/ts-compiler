package com.github.horitaku1124.ts_compiler.writers

import com.github.horitaku1124.ts_compiler.nodes.FileNode
import com.github.horitaku1124.ts_compiler.nodes.FunctionCallNode
import com.github.horitaku1124.ts_compiler.nodes.StringNode
import java.nio.file.Path
import kotlin.io.path.writer

class GoWriter {
  fun write(node: FileNode, saveTo: Path) {
    saveTo.writer().use { writer ->
      writer.write("package main\n\n")
      writer.write("import \"fmt\"\n\n")
      writer.write("func main() {\n")
      var goExpression = StringBuffer()
      for (expression in node.expressions) {
        if (expression is FunctionCallNode) {
          var funcName = expression.functionName
          var params = expression.param
          goExpression.append("\t")
          if (funcName == "console.log") {
            goExpression.append("fmt.Println(")
            var goParams = arrayListOf<String>()
            for (param in params) {
              if (param is StringNode) {
                goParams.add("\"" + param.value + "\"")
              }
            }
            goExpression.append(goParams.joinToString(","))
            goExpression.append(")\n")
          }
        }
      }
      writer.write(goExpression.toString())
      writer.write("}\n")
    }
  }
}