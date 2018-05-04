package com.youzi.MyBlog.util;

import java.util.Arrays;

public class Servlets {
	public static String staticFileSuffix []=".css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.crx,.xpi,.exe,.ipa,.apk".split(",");
	
	public static boolean isStaticFile(String uri) {
		if(uri.lastIndexOf(".")!=-1) {
			return Arrays.asList(staticFileSuffix).contains(uri.substring(uri.lastIndexOf(".")));
		}
		return false;
	}
}
