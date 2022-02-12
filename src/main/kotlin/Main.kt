import com.github.horitaku1124.ts_compiler.LexicalAnalyzer
import com.github.horitaku1124.ts_compiler.MergedLiner
import com.github.horitaku1124.ts_compiler.TreeBuilder
import com.github.horitaku1124.ts_compiler.nodes.FileNode
import com.github.horitaku1124.ts_compiler.writers.GoWriter
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.nameWithoutExtension

fun main(args: Array<String>) {
  val path = Path.of(args[0])
  val codeText = Files.readString(path)
  val tokens = LexicalAnalyzer().parse(codeText)
  println(tokens)
  val lines = MergedLiner().mergeLine(tokens)
  val ast = TreeBuilder().buildAst(lines)
  val fileAst = FileNode(path.fileName.nameWithoutExtension, ast)
  GoWriter().write(fileAst, Path.of("out/" + path.fileName.nameWithoutExtension + ".go"))
  println(fileAst)
}