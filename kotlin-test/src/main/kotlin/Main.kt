import io.eniflee.expression.ExprBaseVisitor
import io.eniflee.expression.ExprLexer
import io.eniflee.expression.ExprParser
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream

fun main(args: Array<String>) {
    val expression = "5*4*3*2"

    val stream = CharStreams.fromString(expression)

    val exprLexer = ExprLexer(stream)

    val commonTokenStream = CommonTokenStream(exprLexer)

    val parser = ExprParser(commonTokenStream)

    val visitor = Visitor()
    val result = visitor.visit(parser.prog())
    println(result)
}

class Visitor : ExprBaseVisitor<Int>() {
    override fun visitProg(ctx: ExprParser.ProgContext): Int {
        return visit(ctx.findExpr()!!) ?: 0
    }


    override fun visitExpr(ctx: ExprParser.ExprContext): Int {
        if (ctx.INT() != null) {
            return ctx.INT()!!.text.toInt()
        }

        if (ctx.findExpr().size == 1) {
            return visit(ctx.findExpr(0)!!) ?: 0
        }

        val right = visit(ctx.findExpr(0)!!)!!
        val left = visit(ctx.findExpr(1)!!)!!

        return when {
            ctx.PLUS() != null -> right + left
            ctx.MINUS() != null -> right - left
            ctx.DIV() != null -> right / left
            ctx.TIMES() != null -> right * left
            else -> 0
        }
    }
}
