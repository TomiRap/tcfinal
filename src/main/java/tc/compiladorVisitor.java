// Generated from c:/Users/torus/Desktop/tcfinal/src/main/java/tc/compilador.g4 by ANTLR 4.13.1

package tc;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link compiladorParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface compiladorVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link compiladorParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(compiladorParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#incrementoDecremento}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIncrementoDecremento(compiladorParser.IncrementoDecrementoContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(compiladorParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#instruccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion(compiladorParser.InstruccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#bloque}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloque(compiladorParser.BloqueContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#declaracion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracion(compiladorParser.DeclaracionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacion(compiladorParser.AsignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(compiladorParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(compiladorParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(compiladorParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(compiladorParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#returnStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(compiladorParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#funcionDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncionDecl(compiladorParser.FuncionDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#funcionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncionCall(compiladorParser.FuncionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros(compiladorParser.ParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#parametro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametro(compiladorParser.ParametroContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#argumentos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentos(compiladorParser.ArgumentosContext ctx);
}