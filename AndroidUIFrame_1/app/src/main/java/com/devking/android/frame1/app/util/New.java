package com.devking.android.frame1.app.util;

import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过杠杆推断创建相应的容器 Created by Yu on 14-2-25.
 */
public class New {
	public static <K, V> Map<K, V> map() {
		return new HashMap<K, V>();
	}

	public static <T> List<T> list() {
		return new ArrayList<T>();
	}

	private static Gson gson = new Gson();

	/**
	 * 预留方法通过gson解析json，且赋值给bean
	 * @param map
	 * @return
	 */
//	public static <T extends BaseBean> T parse(String json, Class<T> cls) {
//		T t = null;
//		try {
//			t = gson.fromJson(json, cls);
//		} catch (Exception e) {
//			L.e("解析出错:" + json);
//			L.e("错误信息:" + e.getLocalizedMessage());
//		}
//		if (t == null) {
//			try {
//				t = cls.newInstance();
//				t.setMsg("数据解析出错");
//			} catch (InstantiationException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
//		return t;
//	}
	public static RequestParams params (Map...map){
		
		return null;
	}
	public static <T> T parseInfo(String json, Class<T> cls) {
		T t = null;
		try {
			t = gson.fromJson(json, cls);
		} catch (IllegalStateException ignored) {
		}
		if (t == null) {
			try {
				t = cls.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return t;
	}
}
