package com.project.login.util;

public class StringUtils {
	
	private StringUtils() {
	}
	
	public static String ConverterObj(Object obj) {
		return String.valueOf(obj);
	}
	
	public static boolean isEmpty(String str) {
		str = String.valueOf(str);
		if ( str == null || str.length() == 0 || "null".equals(str) ) {
			return true;
		}
		return false;
	}
}	
