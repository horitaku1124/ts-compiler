package com.github.horitaku1124.ts_compiler

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LexicalAnalyzerTests {
  private lateinit var analyzer: LexicalAnalyzer

  @BeforeEach
  fun before() {
    analyzer = LexicalAnalyzer()
  }

  @Test
  fun parseTest1() {
    val tokens = analyzer.parse("fun1()")
    assertEquals(3, tokens.size)
    assertEquals("fun1", tokens[0])
    assertEquals("(", tokens[1])
    assertEquals(")", tokens[2])
  }

  @Test
  fun parseTest2() {
    val tokens = analyzer.parse("fun2(1,2)")
    assertEquals(6, tokens.size)
    assertEquals("fun2", tokens[0])
    assertEquals("(", tokens[1])
    assertEquals("1", tokens[2])
    assertEquals(",", tokens[3])
    assertEquals("2", tokens[4])
    assertEquals(")", tokens[5])
  }
}