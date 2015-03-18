package com.devking.android.frame1.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import com.devking.android.frame1.app.App;

import java.io.*;

/**
 * 类名称：SharedPreferencesHelper
 * 类描述：SharedPreferences缓存类
 * 创建人：Ken 创建时间：2014-3-19
 * 
 */
public class SharedPreferencesHelper {
	private final static String APP_SHP_KEY = "app_info";
	private final static String PERSONAL_SHP_KEY = "personal_info";

	public static Editor getEditor(boolean ispersonal) {
		return ispersonal ? App.getInstance()
				.getSharedPreferences(PERSONAL_SHP_KEY, Context.MODE_PRIVATE)
				.edit() : App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE).edit();
	}

	public static SharedPreferences getSharedPreferences(boolean ispersonal) {
		return ispersonal ? App.getInstance().getSharedPreferences(
				PERSONAL_SHP_KEY, Context.MODE_PRIVATE) : App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE);
	}

	public static boolean putString(String key, String value) {
		return getEditor(false).putString(key, value).commit();
	}

	public static String getString(String key, String defValue) {
		return getSharedPreferences(false).getString(key, defValue);
	}

	public static boolean putBoolean(String key, Boolean value) {
		return getEditor(false).putBoolean(key, value).commit();
	}

	public static Boolean getBoolean(String key, Boolean _default) {
		return getSharedPreferences(false).getBoolean(key, _default);
	}

	// TODO change ispersonal
	public static boolean remove(String key) {
		getEditor(true).remove(key).commit();
		return getEditor(false).remove(key).commit();
	}

	public static boolean clearall() {
		getEditor(true).clear().commit();
		return getEditor(false).clear().commit();
	}

	public static boolean clearall(boolean ispersonal) {
		return getEditor(ispersonal).clear().commit();
	}

	public static boolean putInt(String key, Integer value) {
		return App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE).edit()
				.putInt(key, value).commit();
	}

	public static boolean putInt(String key, Integer value, boolean ispersonal) {
		return ispersonal ? App.getInstance()
				.getSharedPreferences(PERSONAL_SHP_KEY, Context.MODE_PRIVATE)
				.edit().putInt(key, value).commit() : App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE).edit()
				.putInt(key, value).commit();
	}

	public static Integer getInt(String key, Integer value) {
		return App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE)
				.getInt(key, value);
	}

	public static Integer getInt(String key, Integer value, boolean ispersonal) {
		return ispersonal ? App.getInstance()
				.getSharedPreferences(PERSONAL_SHP_KEY, Context.MODE_PRIVATE)
				.getInt(key, value) : App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE)
				.getInt(key, value);
	}

	public static boolean putLong(String key, Long value) {
		return App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE).edit()
				.putLong(key, value).commit();
	}

	public static Long getLong(String key, Long value) {
		return App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE)
				.getLong(key, value);
	}

	public static boolean putObject(String key, Object object)
			throws IOException {
		return putObject(key, object, false);
	}

	public static boolean putObject(String key, Object object,
			boolean ispersonal) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		String objectBase64 = new String(Base64.encode(baos.toByteArray(),
				Base64.DEFAULT));
		return ispersonal ? App.getInstance()
				.getSharedPreferences(PERSONAL_SHP_KEY, Context.MODE_PRIVATE)
				.edit().putString(key, objectBase64).commit() : App
				.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE).edit()
				.putString(key, objectBase64).commit();
	}

	public static Object getObject(String key, Object _default)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		return getObject(key, _default, false);
	}

	public static Object getObject(String key, Object _default,
			boolean ispersonal) throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		String objectBase64 = ispersonal ? App.getInstance()
				.getSharedPreferences(PERSONAL_SHP_KEY, Context.MODE_PRIVATE)
				.getString(key, null) : App.getInstance()
				.getSharedPreferences(APP_SHP_KEY, Context.MODE_PRIVATE)
				.getString(key, null);
		if (objectBase64 != null) {
			byte[] bytearray = Base64.decode(objectBase64, Base64.DEFAULT);
			ByteArrayInputStream bis = new ByteArrayInputStream(bytearray);
			ObjectInputStream ois = new ObjectInputStream(bis);
			return ois.readObject();
		} else {
			return _default;
		}
	}

	public static Drawable getDrawable(String key) {
		Context context = App.getInstance();
		SharedPreferences shp = context.getSharedPreferences(APP_SHP_KEY,
				Context.MODE_PRIVATE);
		String imageBase64 = shp.getString(key, null);
		if (imageBase64 != null) {
			byte[] bytearray = Base64.decode(imageBase64, Base64.DEFAULT);
			ByteArrayInputStream bis = new ByteArrayInputStream(bytearray);
			Drawable drawable = Drawable.createFromStream(bis, "image");
			return drawable;
		} else {
			return null;
		}
	}

	public synchronized static void setDrawable(String key, Drawable headicon)
			throws IOException {
		Context context = App.getInstance();
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		((BitmapDrawable) headicon).getBitmap().compress(CompressFormat.JPEG,
				50, bo);
		byte[] bytearray = bo.toByteArray();
		String imageBase64 = new String(
				Base64.encode(bytearray, Base64.DEFAULT));
		Editor editor = context.getSharedPreferences(APP_SHP_KEY,
				Context.MODE_PRIVATE).edit();
		editor.putString(key, imageBase64);
		editor.commit();
		bo.close();
	}

}
