# JAVA - jaxb

（Java Architecture for XML Binding) 是一个业界的标准，即是一项可以根据XML Schema产生Java类的技术。该过程中，JAXB也提供了将XML实例文档反向生成Java对象树的方法，并能将Java对象树的内容重新写到XML实例文档。有多种实现。

## 疑问收集

记录学习过程中的疑问，方便后面归纳整理。

## 重点摘要

Marshaller接口，将Java对象序列化为XML数据。   
Unmarshaller接口，将XML数据反序列化为Java对象。  
```java
@XmlType   //将Java类或枚举类型映射到XML模式类型 
@XmlAccessorType(XmlAccessType.FIELD) 
制字段或属性的序列化。FIELD表示JAXB将自动绑定Java类中的每个非静态的（static）、非瞬态的（由@XmlTransient标 注）字段到XML。其他值还有XmlAccessType.PROPERTY和XmlAccessType.NONE。 
@XmlAccessorOrder  	// 控制JAXB 绑定类中属性和字段的排序。 
@XmlJavaTypeAdapter //使用定制的适配器（即扩展抽象类XmlAdapter并覆盖marshal()和unmarshal()方法），以序列化Java类为XML。 
@XmlElementWrapper  //对于数组或集合（即包含多个元素的成员变量），生成一个包装该数组或集合的XML元素（称为包装器）。 
@XmlRootElement 	//将Java类或枚举类型映射到XML元素。 
@XmlElement 		//将Java类的一个属性映射到与属性同名的一个XML元素。 
@XmlAttribute 		//将Java类的一个属性映射到与属性同名的一个XML属性。
@XmlTransient 		//忽略java类的属性与xml属性的映射
```
## 