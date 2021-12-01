package com.github.horitaku1124.ts_compiler

import java.util.ArrayList

class LexicalAnalyzer {
  private val spaces = " \t"
  private val cr_lf = "\r\n"
  private val singleOperator = "()+-.*/=;,><?&!|[]@{}"
  private val allClears = singleOperator + spaces + cr_lf
  fun parse(line: String): List<String>{
    var quote: Char? = null
    var verse = ""
    val tokens: MutableList<String> = ArrayList()
    var inLineComment = false
    var inMultiLineComment = false
    var i = 0
    while (i < line.length) {
      val c = line[i]
      if (inLineComment) {
        if (c == '\n') {
          inLineComment = false
          tokens.add(verse)
          tokens.add("\n")
          verse = ""
        } else {
          verse += c
        }
        i++
        continue
      } else if (inMultiLineComment) {
        verse += c
        if (c == '*' && line[i + 1] == '/') {
          verse += "/"
          inMultiLineComment = false
          if (verse.isNotEmpty()) {
            tokens.add(verse)
          }
          verse = ""
          i++
        }
        i++
        continue
      } else if (quote != null) {
        if (c == quote) {
          verse += quote
          if (verse.isNotEmpty()) {
            tokens.add(verse)
          }
          quote = null
          verse = ""
        } else if (c == '\\') {
          val escapeTarget = line[i + 1]
          verse += c
          if (escapeTarget == quote) {
            verse += quote
            i++
          } else if ("nt\\".indexOf(escapeTarget) >= 0) {
            verse += escapeTarget
            i++
          }
        } else {
          verse += c
        }
        i++
        continue
      }
      if (c == '\'' || c == '"' || c == '`') {
        if (verse.isNotEmpty()) {
          tokens.add(verse)
        }
        verse = c.toString()
        quote = c
      } else if (c == '/') {
        val nextChar = line[++i]
        if (nextChar == '/') {
          inLineComment = true
        } else if (nextChar == '*') {
          inMultiLineComment = true
        } else {
          // Divide operator
          if (verse.isNotEmpty()) {
            tokens.add(verse)
          }
          tokens.add(c.toString())
          verse = ""
          i--
        }
        if (verse.isNotEmpty()) {
          tokens.add(verse)
        }
        if (inLineComment || inMultiLineComment) {
          verse = c.toString() + nextChar
        }
      } else if (allClears.contains(c.toString())) {
        if (verse.isNotEmpty()) {
          tokens.add(verse)
        }
        if (cr_lf.indexOf(c) >= 0 || singleOperator.indexOf(c) >= 0) {
          // tokens.push(new CodeVal(char));
        }
        tokens.add(c.toString())
        verse = ""
      } else {
        verse += c
      }
      i++
    }
    return tokens
  }
}