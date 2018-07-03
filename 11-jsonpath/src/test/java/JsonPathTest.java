import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;


/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年6月11日 杨赛
 */
public class JsonPathTest {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private String strJsonArray = null;
	
	private String strJsonObject = null;
	
	@Before
	public void init() throws JsonProcessingException, ParseException {
		List<OrderVO> lstOrder = new ArrayList<OrderVO>();
		OrderVO o = new OrderVO();
		o.setOrgCode("11");
		o.setCreateTime(new Timestamp(DateFormat.parse("2018-06-11").getTime()));
		o.setAmount(800d);
		lstOrder.add(o);
		
		OrderVO o1 = new OrderVO();
		o1.setOrgCode("11aa");
		o1.setCreateTime(new Timestamp(DateFormat.parse("2018-06-12").getTime()));
		o1.setAmount(900d);
		lstOrder.add(o1);
		
		OrderVO o11 = new OrderVO();
		o11.setOrgCode("11aabb");
		o11.setCreateTime(new Timestamp(DateFormat.parse("2018-06-13").getTime()));
		o11.setAmount(1000d);
		lstOrder.add(o11);
		
		OrderVO o2 = new OrderVO();
		o2.setOrgCode("22");
		o2.setCreateTime(new Timestamp(DateFormat.parse("2018-06-14").getTime()));
		o2.setAmount(1100d);
		lstOrder.add(o2);
		
		OrderVO o3 = new OrderVO();
		o3.setOrgCode("22aa");
		o3.setCreateTime(new Timestamp(DateFormat.parse("2018-06-16").getTime()));
		o3.setAmount(1200d);
		lstOrder.add(o3);
		strJsonArray = objectMapper.writeValueAsString(lstOrder);

		OrderVO o4 = new OrderVO();
		o4.setOrgCode("bb");
		o4.setCreateTime(new Timestamp(DateFormat.parse("2018-06-16").getTime()));
		o4.setAmount(2000d);
		lstOrder.add(o4);

		OrderVO o5 = new OrderVO();
		o5.setOrgCode("aa");
		o5.setCreateTime(new Timestamp(DateFormat.parse("2018-06-16").getTime()));
		o5.setAmount(3000d);
		lstOrder.add(o5);
		

		strJsonArray = objectMapper.writeValueAsString(lstOrder);
		strJsonObject = objectMapper.writeValueAsString(o4);
	}
	
	@Test
	public void testParseLike() throws JsonProcessingException {
		List<?> list = JsonPath.parse(strJsonArray).read("$[?(@.orgCode=~/11.*?/i)]", List.class);
		Assert.assertEquals(3, list.size());
		Map<String, Object> map = (Map<String, Object>) list.get(0);
		Assert.assertEquals("11", map.get("orgCode"));

		map = (Map<String, Object>) list.get(1);
		Assert.assertEquals("11aa", map.get("orgCode"));
		
		map = (Map<String, Object>) list.get(2);
		Assert.assertEquals("11aabb", map.get("orgCode"));
		
		list = JsonPath.parse(strJsonArray).read("$[?(@.orgCode=~/.*?aa.*?/i && (@.orgCode == '11aa' || @.orgCode == '11aabb'))]", List.class);
		Assert.assertEquals(2, list.size());
		map = (Map<String, Object>) list.get(0);
		Assert.assertEquals("11aa", map.get("orgCode"));

		map = (Map<String, Object>) list.get(1);
		Assert.assertEquals("11aabb", map.get("orgCode"));
		
//		map = (Map<String, Object>) list.get(2);
//		Assert.assertEquals("22aa", map.get("orgCode"));
//		
//		map = (Map<String, Object>) list.get(3);
//		Assert.assertEquals("aa", map.get("orgCode"));
		
	}

	@Test
	public void testBetween() throws JsonProcessingException, ParseException {
		String path = String.format("$[?(@.createTime >= %s && @.createTime <= %s)]", DateFormat.parse("2018-06-12").getTime(), DateFormat.parse("2018-06-13").getTime());
		List<?> list = JsonPath.parse(strJsonArray).read(path, List.class);
		Assert.assertEquals(2, list.size());
		Map<String, Object> map = (Map<String, Object>) list.get(0);
		Assert.assertEquals(DateFormat.parse("2018-06-12").getTime(), map.get("createTime"));
		
		map = (Map<String, Object>) list.get(1);
		Assert.assertEquals(DateFormat.parse("2018-06-13").getTime(), map.get("createTime"));
	}
	
	@Test
	public void testParse() throws IOException {
		List<?> list = JsonPath.parse(strJsonObject).read("$[?(@.orgCode=='bb' && @.amount==2000)]");
		Assert.assertEquals(1, list.size());

		list = JsonPath.parse(strJsonArray).read("$[?((@.orgCode==11) && @.amount=='800')]");
		Assert.assertEquals(1, list.size());

		list = JsonPath.parse(strJsonArray).read("$[?((@.orgCode=='bb' || (@.orgCode>='bb' && @.orgCode<='bb')) && ((@.amount>=1000 && @.amount<=2000) || @.amount==1000))]");
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 
	 * 测试数字和数字字符是否有区别 
	 * @throws IOException
	 */
	@Test
	public void testParse2() throws IOException {
		List<?> list = JsonPath.parse(strJsonArray).read("$[?((@.orgCode==11) && @.amount=='800')]");
		Assert.assertEquals(1, list.size());
		
	}
	
	
}
