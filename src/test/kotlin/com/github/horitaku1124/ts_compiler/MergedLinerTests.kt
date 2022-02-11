package com.github.horitaku1124.ts_compiler

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MergedLinerTests {
  private lateinit var analyzer: LexicalAnalyzer
  private lateinit var mergedLiner: MergedLiner

  @BeforeEach
  fun before() {
    analyzer = LexicalAnalyzer()
    mergedLiner = MergedLiner()
  }

  @Test
  fun mergeTest1() {
    val lines = mergedLiner.mergeLine(analyzer.parse("fun1()"))
    assertEquals(1, lines.size)
    lines[0].let { line1 ->
      assertEquals(1, line1.lineNumber)
      line1.tokens.let { tokens ->
        assertEquals(3, tokens.size)
        assertEquals("fun1", tokens[0])
        assertEquals("(", tokens[1])
        assertEquals(")", tokens[2])
      }
    }
  }

  @Test
  fun mergeTest2() {
    val lines = mergedLiner.mergeLine(analyzer.parse("var  a = 1;"))
    assertEquals(1, lines.size)
    lines[0].let { line1 ->
      assertEquals(1, line1.lineNumber)
      line1.tokens.let { tokens ->
        assertEquals(4, tokens.size)
        assertEquals("var", tokens[0])
        assertEquals("a", tokens[1])
        assertEquals("=", tokens[2])
        assertEquals("1", tokens[3])
      }
    }
  }
}