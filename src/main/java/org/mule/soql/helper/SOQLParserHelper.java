package org.mule.soql.helper;

import org.antlr.runtime.*;
import org.antlr.runtime.tree.CommonTree;
import org.mule.soql.exception.SOQLParsingException;
import org.mule.soql.parser.SOQLLexer;
import org.mule.soql.parser.SOQLParser;

public class SOQLParserHelper {
	
	public CommonTree parseTextToTree(String text) throws SOQLParsingException {
		try {
			ANTLRStringStream input = new ANTLRStringStream(text);
			TokenStream tokens = new CommonTokenStream(new SOQLLexer(input));

			SOQLParser parser = new SOQLParser(tokens);
			SOQLParser.soql_query_return ret = parser.soql_query();

			CommonTree tree = ret.getTree();

			if(tree == null) {
				throw new SOQLParsingException("The SOQL tree could not be generated due to an unexpected error.");
			}

			return tree;

		} catch (NoViableAltException e) {
			throw new SOQLParsingException("Syntax error close to '" + e.token.getText() + "' (position: " + e.charPositionInLine + ").", e);
		}  catch (RecognitionException e) {
			throw new SOQLParsingException("Recognition exception is never thrown, only declared.", e);
		}
	}

}