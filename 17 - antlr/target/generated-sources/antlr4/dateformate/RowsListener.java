// Generated from Rows.g4 by ANTLR 4.7
package dateformate;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RowsParser}.
 */
public interface RowsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RowsParser#df}.
	 * @param ctx the parse tree
	 */
	void enterDf(RowsParser.DfContext ctx);
	/**
	 * Exit a parse tree produced by {@link RowsParser#df}.
	 * @param ctx the parse tree
	 */
	void exitDf(RowsParser.DfContext ctx);
	/**
	 * Enter a parse tree produced by {@link RowsParser#item}.
	 * @param ctx the parse tree
	 */
	void enterItem(RowsParser.ItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link RowsParser#item}.
	 * @param ctx the parse tree
	 */
	void exitItem(RowsParser.ItemContext ctx);
}