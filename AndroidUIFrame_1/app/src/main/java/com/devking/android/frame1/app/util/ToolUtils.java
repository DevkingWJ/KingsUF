package com.devking.android.frame1.app.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import com.devking.android.frame1.app.App;

/**
 * Tools封装
 * @author Kings
 *
 */
public class ToolUtils {
	
	/**
	 * 判断外置存储是否存在
	 * @return boolean
	 */
	public static boolean externalStorageExist(){
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}
	
	/**
	 * 获得外置存储路径
	 * @return String
	 */
	public static String getExternalStoragePath(){
		if(externalStorageExist()){
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}
	
	/**
	 * 判断是否为wifi连接状态
	 * @return boolean
	 */
	public static boolean wifiIsConnected(){
		ConnectivityManager connManager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi != null && mWifi.isConnected();
	}
	
}
