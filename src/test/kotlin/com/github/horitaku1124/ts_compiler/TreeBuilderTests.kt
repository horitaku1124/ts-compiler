package com.github.horitaku1124.ts_compiler

import com.github.horitaku1124.ts_compiler.nodes.ExpressionDefineNode
import com.github.horitaku1124.ts_compiler.nodes.FunctionCallNode
import com.github.horitaku1124.ts_compiler.nodes.values.StringNode
import com.github.horitaku1124.ts_compiler.nodes.VariableDefineNode
import com.github.horitaku1124.ts_compiler.nodes.values.ValueNode
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

  @Test
  fun parseTest3() {
    val tokens = MergedLiner.LogicalLine(1, listOf("var", "a"))
    val ast = TreeBuilder().buildAst(listOf(tokens))
    println(ast)
    assertEquals(1, ast.size)

    ast[0].let { line1 ->
      assertEquals(1, line1.size)
      line1[0].let { node ->
        assertTrue(node is VariableDefineNode)
        assertEquals("a", node.name)
        assertEquals(false, node.hasAssignment)
      }
    }
  }

  @Test
  fun parseTest4() {
    val tokens = MergedLiner.LogicalLine(1, listOf("let", "b", "=", "200"))
    val ast = TreeBuilder().buildAst(listOf(tokens))
    println(ast)
    assertEquals(1, ast.size)

    ast[0].let { line1 ->
      assertEquals(1, line1.size)
      line1[0].let { node ->
        assertTrue(node is VariableDefineNode)
        assertEquals("b", node.name)
        assertEquals(true, node.hasAssignment)
        assertEquals("200", node.withValue?.value)
      }
    }
  }

  @Test
  fun parseTest5() {
    val tokens = MergedLiner.LogicalLine(1, listOf("let", "c", "=", "10", "+", "20"))
    val ast = TreeBuilder().buildAst(listOf(tokens))
    println(ast)
    assertEquals(1, ast.size)

    ast[0].let { line1 ->
      assertEquals(1, line1.size)
      line1[0].let { node ->
        assertTrue(node is ExpressionDefineNode)
        assertEquals("c", node.name)
        assertEquals("=", node.type)
        assertEquals("+", node.operationNode.type)
        assertEquals(true, node.operationNode.leftNode is ValueNode)
        assertEquals(true, node.operationNode.rightNode is ValueNode)
        assertEquals("10", (node.operationNode.leftNode as ValueNode).value)
        assertEquals("20", (node.operationNode.rightNode as ValueNode).value)
      }
    }
  }
}