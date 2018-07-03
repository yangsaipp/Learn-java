/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package exp;

import java.util.Scanner;

import org.junit.Test;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年6月14日 杨赛
 */
public class ExpTest {
	
	@Test
	public void testInfixToSuffix(){
	    String input;
	    System.out.println("Enter infix:");
	    Scanner scanner = new Scanner(System.in);
	    input = scanner.nextLine();
	    InfixToSuffix in = new InfixToSuffix(input);
	    MyCharStack my = in.doTrans();
	    my.displayStack();
	}
}
