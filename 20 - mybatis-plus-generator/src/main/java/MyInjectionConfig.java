import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

public class MyInjectionConfig extends InjectionConfig {

	public PackageConfig packageConfig;

	private static String getDateTime() {
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDate.format(formatter);
	}
	
	

	public MyInjectionConfig(PackageConfig packageConfig) {
		super();
		this.packageConfig = packageConfig;
	}

	@Override
	public void initMap() {
		// 增加自定义参数和值
		Map<String, Object> map = new HashMap<>();
		map.put("dateTime", getDateTime());
		setMap(map);
		final String projectPath = System.getProperty("user.dir");
		// 增加自定义的模板文件和输出目录
		List<FileOutConfig> fileOutConfigList = new ArrayList<FileOutConfig>();
		// 自定义配置会被优先输出

		String strVoPackage = String.format("%s.model", packageConfig.getParent()); 
		map.put("voPackage", strVoPackage);
		// xxxVO.java
		fileOutConfigList.add(new FileOutConfig("/templates2/entityVO.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String voName = String.format("%sVO", tableInfo.getEntityName());
				String voClassName = String.format("%s.%s", strVoPackage,voName);
				map.put("voName", voName);
				map.put("voClassName", voClassName);
				// 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
				return projectPath + "/src/main/java/" + voClassName.replace(".", "/") + StringPool.DOT_JAVA;
			}
		});

		// xxxDTO.java
		fileOutConfigList.add(new FileOutConfig("/templates2/entityDTO.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String dtoName = String.format("%sDTO", tableInfo.getEntityName());
				String dtoClassName = String.format("%s.%s", strVoPackage,dtoName);
				map.put("dtoName", dtoName);
				map.put("dtoClassName", dtoClassName);
				// 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
				return projectPath + "/src/main/java/" + dtoClassName.replace(".", "/") + StringPool.DOT_JAVA;
			}
		});
        
        // xxxSQL.xml
		map.put("namespace", strVoPackage);
		fileOutConfigList.add(new FileOutConfig("/templates2/entitySQL.xml.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				TableField keyField = getKeyField(tableInfo);
				map.put("keyField", keyField);
				// 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
				String SQLPath = String.format("%s/dbconfig/%sSQL.xml", packageConfig.getParent().replace(".", "/"), tableInfo.getEntityName());
				return projectPath + "/src/main/java/" + SQLPath;
			}

			private TableField getKeyField(TableInfo tableInfo) {
				for(TableField f : tableInfo.getFields()){
					if(f.isKeyFlag()) {
						return f;
					}
				}
				throw new RuntimeException("未找到主键id字段");
			}
		});
        // xxxAppservice.java
		String strAppServicePackage = String.format("%s.appservice", packageConfig.getParent()); 
		map.put("appServicePackage", strAppServicePackage);
		// xxxVO.java
		fileOutConfigList.add(new FileOutConfig("/templates2/appservice.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String appserviceName = String.format("%sAppService", tableInfo.getEntityName());
				String appServiceClassName = String.format("%s.%s", strAppServicePackage,appserviceName);
				map.put("appserviceName", appserviceName);
				map.put("appServiceClassName", appServiceClassName);
				// 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
				return projectPath + "/src/main/java/" + appServiceClassName.replace(".", "/") + StringPool.DOT_JAVA;
			}
		});
		
		
        // xxxFacade.java
		String strFacadePackage = String.format("%s.facade", packageConfig.getParent()); 
		map.put("facadePackage", strFacadePackage);
		// xxxVO.java
		fileOutConfigList.add(new FileOutConfig("/templates2/facade.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String facadeName = String.format("%sFacade", tableInfo.getEntityName());
				String facadeClassName = String.format("%s.%s", strFacadePackage,facadeName);
				map.put("FacadeName", facadeName);
				map.put("FacadeClassName", facadeClassName);
				// 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
				return projectPath + "/src/main/java/" + facadeClassName.replace(".", "/") + StringPool.DOT_JAVA;
			}
		});
		
        // xxxController.java
		String strControllerPackage = String.format("%s.controller", packageConfig.getParent()); 
		map.put("controllerPackage", strControllerPackage);
		// xxxVO.java
		fileOutConfigList.add(new FileOutConfig("/templates2/controller.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				String controllerName = String.format("%sController", tableInfo.getEntityName());
				String controllerClassName = String.format("%s.%s", strControllerPackage,controllerName);
				map.put("controllerName", controllerName);
				map.put("controllerClassName", controllerClassName);
				// 自定义输出文件名，如果entity设置了前后缀，此次注意xml的名称也会跟着发生变化
				return projectPath + "/src/main/java/" + controllerClassName.replace(".", "/") + StringPool.DOT_JAVA;
			}
		});
		
		
		setFileOutConfigList(fileOutConfigList);
	}

}
