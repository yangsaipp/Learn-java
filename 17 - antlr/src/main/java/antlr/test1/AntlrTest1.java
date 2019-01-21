package antlr.test1;

/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
// import ANTLR's runtime libraries
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Antlr与java应用集成
 * @author  杨赛
 * @since   jdk1.7
 * @version 2019年1月18日 杨赛
 */
public class AntlrTest1 {
	
	/**
	 * @param args args
	 * @throws Exception Exception
	 */
    public static void main(String[] args) throws Exception {
    	
    	// 新建一个CharStream，从标准输入读取数据
        // create a CharStream that reads from standard input
        ANTLRInputStream input = new ANTLRInputStream(System.in);

        // 新建一个词法分析器，处理输入的CharStream
        // create a lexer that feeds off of input CharStream
        ArrayInitLexer lexer = new ArrayInitLexer(input);

        // 新建一个词法符号的缓存区，存储词法分析器生成的词法符号
        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 新建一个语法解析器，验证生成的词法符号
        // create a parser that feeds off the tokens buffer
        ArrayInitParser parser = new ArrayInitParser(tokens);

        ParseTree tree = parser.init(); // begin parsing at init rule 针对init规则，开始语法分析
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
        
    }
}
