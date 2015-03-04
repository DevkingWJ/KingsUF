package com.devking.android.frame1.app.util;

import android.util.Log;
import com.devking.android.frame1.app.App;


/**
 * Log封装 增加打印行数，可以直接在ide中点击进入相应打印log的位置
 * @author Kings
 *
 */
public class L {
	private static final String PACKAGE_NAME = App.getInstance().getPackageName();
	private static final boolean DEBUG = true;

	public static void v(String msg) {
		if (DEBUG) {
			Log.v(PACKAGE_NAME, getStackTraceMessage(msg));
		}
	}

	public static void d(String msg) {
		if (DEBUG) {
			Log.d(PACKAGE_NAME, getStackTraceMessage(msg));
		}
	}

	public static void i(String msg) {
		if (DEBUG) {
			Log.i(PACKAGE_NAME, getStackTraceMessage(msg));
		}
	}

	public static void w(String msg) {
		if (DEBUG) {
			Log.w(PACKAGE_NAME, getStackTraceMessage(msg));
		}
	}

	public static void e(String msg) {
		if (DEBUG) {
			Log.e(PACKAGE_NAME, getStackTraceMessage(msg));
		}
	}

	private static StackTraceElement get() {
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		if (stack.length < 6)
			return null;
		return stack[5];
	}

	private static String getSimpleClassName(String className) {
		return className.substring(className.lastIndexOf(".") + 1);
	}

	private static String getStackTraceMessage(String msg) {
		StackTraceElement element = get();

		if (element == null)
			return "";

		StringBuilder builder = new StringBuilder();
		builder.append(">>\n");
		builder.append(element.getClassName());
		builder.append(".");
		builder.append(element.getMethodName());
		builder.append("(");
		builder.append(getSimpleClassName(element.getClassName()));
		// 模拟系统打印错误日志方式
		builder.append(".java:");
		builder.append(element.getLineNumber());
		builder.append(")");
		builder.append(":");
		builder.append(msg);
		return builder.toString();
	}
}
