package fel;

import java.util.HashMap;
import java.util.Map;

import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.context.MapContext;

import model.LoginUser;

public class FelHello {
	public static void main(String[] args) {
		final Map<String, Object> vars = new HashMap<String, Object>();
		LoginUser user = new LoginUser();
		user.setUserId("ssss");
		vars.put("loginUser", user);
		
		FelContext ctx = new MapContext(vars);
//		ctx = new ArrayCtxImpl(vars);
		FelEngine engine = new FelEngineImpl();
		Expression expObj = engine.compile("loginUser.userId", ctx);
		System.out.println(expObj.eval(ctx));
	}
}
