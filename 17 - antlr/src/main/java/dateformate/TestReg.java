package dateformate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * oracle的日期格式化参数转换为MySQL或其他数据库的日期格式化参数
 * YYYY
 * 	YYYY-SM
	YYYY-SM-MM
 	YYYY-SMMM
	YYYY-MM-SM
	YYYY-MMSM
	YYYYMM
	YYYYMM-MM
	YYYYMMMM
	YYYYMM-SM
	YYYYMMSM
	YYYYSM
	-YYYY
 * 	-YYYY-SM
	-YYYY-SM-MM
 	-YYYY-SMMM
	-YYYY-MM-SM
	-YYYY-MMSM
	SMYYYY
 * 	SMYYYY-SM
	SMYYYY-SM-MM
 	SMYYYY-SMMM
	SMYYYY-MM-SM
	SMYYYY-MMSM
 * @author yangsai
 *
 */
public class TestReg {
	public static void main(String[] args) {
		StringBuffer resultString = new StringBuffer();
		try {
			Pattern regex = Pattern.compile("(YYYY|MM|DD|HH24|HH|mm|ss)");
			Matcher regexMatcher = regex.matcher("SS-YYYY-MM");
			while (regexMatcher.find()) {
				try {
					// You can vary the replacement text for each match on-the-fly
					regexMatcher.appendReplacement(resultString, getKey(regexMatcher.group()));
				} catch (IllegalStateException ex) {
					ex.printStackTrace();
					// appendReplacement() called without a prior successful call to find()
				} catch (IllegalArgumentException ex) {
					ex.printStackTrace();
					// Syntax error in the replacement text (unescaped $ signs?)
				} catch (IndexOutOfBoundsException ex) {
					// Non-existent backreference used the replacement text
					ex.printStackTrace();
				} 
			}
			regexMatcher.appendTail(resultString);
			System.out.println(resultString);
		} catch (PatternSyntaxException ex) {
			// Syntax error in the regular expression
		}
	}

	private static String getKey(String group) {
		switch (group) {
		case "YYYY":
			return "Y%";
		case "MM":
			return "M%";
		default:
			break;
		}
		return null;
	}
}

