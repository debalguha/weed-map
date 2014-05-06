package org.instant420.web;

public class JacksonUtil {
	private static JacksonUtil me;
	public static JacksonUtil getInstance(){
		if(me == null)
			me = new JacksonUtil();
		
		return me;
	}
}
