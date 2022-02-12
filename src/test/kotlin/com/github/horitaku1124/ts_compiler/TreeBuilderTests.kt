package com.github.horitaku1124.ts_compiler

import com.github.horitaku1124.ts_compiler.nodes.FunctionCallNode
import com.github.horitaku1124.ts_compiler.nodes.values.StringNode
import com.github.horitaku1124.ts_compiler.nodes.VariableDefineNode
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TreeBuilderTests {
  @Test
  fun parseTest1() {
    val tokens = MergedLiner.LogicalLine(1, listOf("console.log", "(", "\"hello\"",")"))
    val ast = TreeBuilder().buildAst(listOf(tokens))
    println(ast)
    assertEquals(1, ast.size)
    ast[0].let { line1 ->
      assertEquals(1, line1.size)
      line1[0].let { node ->
        assertTrue(node is FunctionCallNode)
        assertEquals("console.log", node.functionName)
        assertTrue(node.param[0] is StringNode)
        assertEquals("hello", (node.param[0] as StringNode).value)
      }
    }
  }

  @Test
  fun parseTest2() {
    val tokens = MergedLiner.LogicalLine(1, listOf("var", "a", "=", "100"))
    val ast = TreeBuilder().buildAst(listOf(tokens))
    println(ast)
    assertEquals(1, ast.size)

    ast[0].let { line1 ->
      assertEquals(1, line1.size)
      line1[0].let { node ->
        assertTrue(node is VariableDefineNode)
        assertEquals("a", node.name)
        assertEquals("100", node.withValue?.value)
      }
    }
  }
}