package com.devking.android.frame1.app.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.devking.android.frame1.app.App;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 数据缓存,包括内存缓存/硬盘缓存/数据库缓存
 */
public class Cache {

	public static Bitmap getBitmap(String url) {
		BitmapUtils bitmapUtils = App.getInstance().getBitmapUtils();
		Bitmap bitmap = bitmapUtils.getBitmapFromMemCache(url, null);
		if (bitmap == null) {
			File file = bitmapUtils.getBitmapFileFromDiskCache(url);
			if (file != null && file.exists()) {
				try {
					bitmap = BitmapFactory.decodeStream(new FileInputStream(
							file));
				} catch (FileNotFoundException e) {
					// e.printStackTrace();
				}
			}
		}
		// 暂时不用
		if (bitmap != null && bitmap.isRecycled()) {
			// 被回收的资源不再去使用它
			return null;
		}
		return bitmap;
	}
}
