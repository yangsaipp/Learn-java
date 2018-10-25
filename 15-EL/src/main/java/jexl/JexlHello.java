package jexl;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import model.LoginUser;

public class JexlHello {
	public static void main(String[] args) {
		// Create or retrieve an engine
		JexlEngine jexl = new JexlBuilder().create();

		// Create an expression
		String jexlExp = "loginUser.userId";
		JexlExpression e = jexl.createExpression(jexlExp);

		// Create a context and add data
		JexlContext jc = new MapContext();
		jc.set("loginUser", new LoginUser());

		// Now evaluate the expression, getting the result
		Object o = e.evaluate(jc);
		System.out.println(o);
	}
}
