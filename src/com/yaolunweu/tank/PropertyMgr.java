package com.yaolunweu.tank;

import java.io.IOException;
import java.util.Properties;
/**
 * 主要对配置文件的管理。
 * @author 小妖
 *
 */
public class PropertyMgr {
	/**
	 * Properties 类表示了一个持久的属性集。Properties 可保存在流中或从流中加载。属性列表中每个键及其对应值都是一个字符串。 
	 *一个属性列表可包含另一个属性列表作为它的“默认值”；如果未能在原有的属性列表中搜索到属性键，则搜索第二个属性列表。 
	 *因为 Properties 继承于 Hashtable，所以可对 Properties 对象应用 put 和 putAll 方法。但不建议使用这两个方法，因为它们允许调用者插入其键或值不是 String 的项。
	 *相反，应该使用 setProperty 方法。如果在“不安全”的 Properties 对象（即包含非 String 的键或值）上调用 store 或 save 方法，
	 *则该调用将失败。类似地，如果在“不安全”的 Properties 对象（即包含非 String 的键）上调用 propertyNames 或 list 方法，则该调用将失败。
	 *
	 *定义为成员变量不必每次调用都去new 节省内存。
	 */
	static Properties pro = new Properties();
	/**
	 * 读取配置文件。
	 */
	static {
		try {
			pro.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *private 修饰构造方法该类不能外部类实例化。
	 */
	private PropertyMgr(){};
	
	/**
	 * 调用此方法返回配置文件中对应的值：
	 * @param key	配置文件中的键值名String类型。
	 * @return	返回键值对应的值，int类型。
	 */
	public static int pros(String key){
		return Integer.parseInt(pro.getProperty(key));
	}
}
