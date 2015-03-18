package com.devking.android.frame1.app.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

import com.devking.android.frame1.app.App;
import org.apache.http.conn.util.InetAddressUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Tools封装
 */
public class T {

	/**
	 * 外置存储是否存在
	 * 
	 * @return boolean
	 */
	public static boolean externalStorageExist() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	/**
	 * 外置存储路径
	 * 
	 * @return String
	 */
	public static String getExternalStoragePath() {
		if (externalStorageExist()) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	public static int rand(int min, int max) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	public static boolean saveBitmap(String fileName, Bitmap bitmap) {
		Bitmap bmap = bitmap.copy(bitmap.getConfig(), true);
		File f = new File(fileName);
		FileOutputStream fOut = null;
		try {
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (IOException e) {
			L.e("保存Bitmap失败:" + e.getLocalizedMessage());
			return false;
		}
		bmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
		}
		return true;
	}

	/**
	 * 重新计算ListView的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if (listItem.getLayoutParams() == null) {
				listItem.setLayoutParams(new ViewGroup.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
	
	/**
	 * 获取IP地址
	 * 
	 * @return
	 */
	public static String getLocalIpAddress() {
		String ip = null;
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()&& InetAddressUtils.isIPv4Address(inetAddress.getHostAddress())) {
						ip = inetAddress.getHostAddress().toString();
						L.i("ip地址:" + ip);
					}
				}
			}
		} catch (SocketException ex) {
		}
		return ip;
	}

	public static Toast toast(String message) {
		Toast toast = Toast.makeText(App.getInstance(), message,
				Toast.LENGTH_SHORT);
		toast.show();
		return toast;
	}

	/**
	 * 匹配url
	 * 
	 * @param url
	 * @return
	 */
	public static boolean matchesUrl(CharSequence url) {
		boolean result = false;
		try {
			result = android.util.Patterns.WEB_URL.matcher(url).matches();
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int dip2px(float dpValue) {
		return dip2px(App.getInstance(), dpValue);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2dip(float pxValue) {
		return px2dip(App.getInstance(), pxValue);
	}

	/**
	 * md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	public static long parseLong(String s) {
		long l = 0;
		try {
			l = Long.parseLong(s);
		} catch (NumberFormatException e) {

		}
		return l;
	}

	/**
	 * 毫秒转换为时间,不报错
	 * 
	 * @param m
	 * @return
	 */
	public static String millisecondToTime(long m, String reg) {
		String time = "";
		SimpleDateFormat sdf = new SimpleDateFormat(reg);
		try {
			time = sdf.format(new Date(m));
		} catch (Exception e) {
		}
		return time;
	}

	public static long secondToTime(String sTime) {
		long time = 0;
		try {
			time = Long.parseLong(sTime) * 1000;
		} catch (NumberFormatException e) {
		}
		return time;
	}

	public static String roundFloat(float value) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.00");
		return decimalFormat.format(value);
	}

	/**
	 * 安装应用
	 * 
	 * @param context
	 * @param filePath
	 */
	public static void install(Context context, String filePath) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + filePath),
				"application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	/**
	 * 打开某个应用Activity
	 * 
	 * @param context
	 * @param pkn
	 */
	public static void openApplication(Context context, String pkn) {
		try {
			Intent intent = context.getPackageManager()
					.getLaunchIntentForPackage(pkn);
			context.startActivity(intent);
		} catch (Exception e) {
			// empty
		}
	}

	/**
	 * 卸载应用
	 * 
	 * @param context
	 * @param pkn
	 */
	public static void uninstall(Context context, String pkn) {
		Uri packageURI = Uri.parse("package:" + pkn);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		context.startActivity(uninstallIntent);
	}

	/**
	 * 加密uid
	 * 
	 * @param uid
	 * @return
	 */
	public static String addKey(String uid) {
		Random random = new Random();
		// 干扰字符
		String key = "youxi.com";
		int start = random.nextInt(21);
		int rand = random.nextInt(7) + 5;
		String md5str = md5(uid + key);
		// 123456
		String str = md5str.substring(start, start + rand);
		return str + "," + start + "," + rand;
	}

	/**
	 * 校验apk文件是否完整
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static boolean checkApkIntegrity(Context context, String path) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
			if (pi != null && pi.applicationInfo != null) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

	public static String getApplicationPackageName(Context context, String path) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
			if (pi != null && pi.applicationInfo != null) {
				return pi.applicationInfo.packageName;
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取指定应用的VersionName
	 * 
	 * @param context
	 * @param pkn
	 * @return
	 */
	public static String getVersionName(Context context, String pkn) {
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(pkn, 0);
			return info.versionName;
		} catch (PackageManager.NameNotFoundException e) {
		}

		return null;
	}

	public static String getVersionName() {
		return getVersionName(App.getInstance(), App.getInstance()
				.getPackageName());
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static void copyToClipboard(CharSequence cs) {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
			// api 11以上
			try {
				ClipboardManager clipboard = (ClipboardManager) App
						.getInstance().getSystemService(
								Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("key", cs);
				clipboard.setPrimaryClip(clip);
				T.toast("已成功复制到剪贴板!");
			} catch (Exception e) {
				T.toast("抱歉,复制失败!");
			}
		} else {
			// api 11以下
			try {
				android.text.ClipboardManager clipboard = (android.text.ClipboardManager) App
						.getInstance().getSystemService(App.CLIPBOARD_SERVICE);
				clipboard.setText(cs);
				T.toast("已成功复制到剪贴板!");
			} catch (Exception e) {
				T.toast("抱歉,复制失败!");
			}
		}
	}

	// 将毫秒数换算成x时x分x秒
	public static String milliSecondFormat(long ms) {
		// 1秒
		int ss = 1000;
		// 1分
		int mm = ss * 60;
		// 1小时
		int hh = mm * 60;
		// 多少个小时
		long hour = ms / hh;
		// 多少分钟
		long minute = (ms - hour * hh) / mm;
		// 多少秒
		long second = (ms - hour * hh - minute * mm) / ss;
		String shour = hour < 10 ? "0" + hour : hour + "";
		String sminute = minute < 10 ? "0" + minute : minute + "";
		String ssecond = second < 10 ? "0" + second : second + "";
		return shour + ":" + sminute + ":" + ssecond;
	}

	/**
	 * 判断是不是wifi连接状态
	 * 
	 * @return
	 */
	public static boolean wifiIsConnected() {
		ConnectivityManager connManager = (ConnectivityManager) App
				.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mWifi != null && mWifi.isConnected();
	}

	/**
	 * 匹配手机号码
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean matchesPhone(String phone) {
		String m = "^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\\d{8}$";
		return phone == null ? false : phone.matches(m);
	}
	
	public static String encryptPhone(String phone){
		phone = phone.replaceAll("(\\d{3})\\d{5}(\\d{3})", "$1*****$2");
		return phone;
	}

	/**
	 * 取SD卡路径
	 */
	public static String getSDCard() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory(); // 获取根目录
		}
		if (sdDir != null) {
			return sdDir.toString();
		} else {
			return null;
		}
	}

	/**
	 * 设置dialog全屏、阴影、全屏等属性
	 * @param dh
	 * @param act
	 * @param lp
	 */
	public static void setDialogParameter(DialogHelper dh, Activity act,LayoutParams lp){
		Window dialogWindow = dh.getDialog().getWindow();
		WindowManager m = act.getWindowManager();
		Display d = act.getWindowManager().getDefaultDisplay();
		dialogWindow.setAttributes(lp);
		dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
		dialogWindow.addFlags(LayoutParams.FLAG_DIM_BEHIND);
		dialogWindow.setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}

	
}
