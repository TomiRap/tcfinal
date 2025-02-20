// Generated from c:/Users/torus/Desktop/tcfinal/src/main/java/tc/compilador.g4 by ANTLR 4.13.1

package tc;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link compiladorParser}.
 */
public interface compiladorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link compiladorParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(compiladorParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(compiladorParser.TipoContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#incrementoDecremento}.
	 * @param ctx the parse tree
	 */
	void enterIncrementoDecremento(compiladorParser.IncrementoDecrementoContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#incrementoDecremento}.
	 * @param ctx the parse tree
	 */
	void exitIncrementoDecremento(compiladorParser.IncrementoDecrementoContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(compiladorParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(compiladorParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion(compiladorParser.InstruccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion(compiladorParser.InstruccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#bloque}.
	 * @param ctx the parse tree
	 */
	void enterBloque(compiladorParser.BloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#bloque}.
	 * @param ctx the parse tree
	 */
	void exitBloque(compiladorParser.BloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#declaracion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracion(compiladorParser.DeclaracionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#declaracion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracion(compiladorParser.DeclaracionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(compiladorParser.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(compiladorParser.AsignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(compiladorParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(compiladorParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(compiladorParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(compiladorParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(compiladorParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(compiladorParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(compiladorParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(compiladorParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(compiladorParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#returnStmt}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(compiladorParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#funcionDecl}.
	 * @param ctx the parse tree
	 */
	void enterFuncionDecl(compiladorParser.FuncionDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#funcionDecl}.
	 * @param ctx the parse tree
	 */
	void exitFuncionDecl(compiladorParser.FuncionDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#funcionCall}.
	 * @param ctx the parse tree
	 */
	void enterFuncionCall(compiladorParser.FuncionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#funcionCall}.
	 * @param ctx the parse tree
	 */
	void exitFuncionCall(compiladorParser.FuncionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(compiladorParser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(compiladorParser.ParametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(compiladorParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(compiladorParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void enterArgumentos(compiladorParser.ArgumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void exitArgumentos(compiladorParser.ArgumentosContext ctx);
}