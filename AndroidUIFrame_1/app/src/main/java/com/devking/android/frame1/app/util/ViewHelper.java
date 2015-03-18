package com.devking.android.frame1.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.devking.android.frame1.app.App;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by Yu on 14-3-3.
 */
public class ViewHelper {
	public static BitmapUtils mBitmapUtils;
	public static boolean setViewValue(View v, Object value) {
		return setViewValue(v, value,getBitmapUtils());
	}

	public static AlphaAnimation getAlphaAnimation() {
		AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(800);
		return animation;
	}

	public static boolean setViewValue(View v, Object value,
			BitmapUtils bitmapUtils) {
		if (v == null) {
			return false;
		}
		bitmapUtils.configDefaultImageLoadAnimation(getAlphaAnimation());
		if (value instanceof View.OnClickListener) {
			v.setOnClickListener((View.OnClickListener) value);
			return true;
		}
		String text = value == null ? "" : value.toString();
		if (v instanceof Checkable) {
			if (value instanceof Boolean) {
				((Checkable) v).setChecked((Boolean) value);
			} else if (v instanceof TextView) {
				// 这里再次检查,是为了防止CheckBox等继承自TextView的控件未检查到
				if (value instanceof CharSequence) {
					((TextView) v).setText((CharSequence) value);
				} else {
					((TextView) v).setText(text);
				}
			}
		} else if (v instanceof TextView) {
			TextView tv = (TextView) v;
			if (value instanceof CharSequence) {
				CharSequence cs = (CharSequence) value;
				if (T.matchesUrl(cs.toString().trim())) {
					// FIXME 加载网络背景图片
					Bitmap bitmap = Cache.getBitmap(text);
					if (bitmap != null) {
						tv.setBackgroundDrawable(new BitmapDrawable(bitmap));
					} else {
						bitmapUtils.display(tv, text);
					}
				} else {
					tv.setText((CharSequence) value);
				}
			} else if (value instanceof Integer) {
				// 资源id
				Integer resId = (Integer) value;
				try {
					// 文字
					tv.setText(resId);
				} catch (Exception e) {
					try {
						// 背景
						tv.setBackgroundResource(resId);
					} catch (Exception e1) {
						// 资源id不存在
						tv.setText(text);
					}
				}
			} else {
				// 不知道什么类型
				tv.setText(text);
			}
		} else if (v instanceof ImageView) {
			ImageView img = (ImageView) v;
			if (value instanceof Integer) {
				// 资源id
				Integer resId = (Integer) value;
				img.setImageResource(resId);
			} else {
				if (T.matchesUrl(text.trim())) {
					// FIXME 加载网络背景图片
					Bitmap bitmap = Cache.getBitmap(text);
					if (bitmap != null) {
						img.setImageBitmap(bitmap);
					} else {
						bitmapUtils.display(img, text);
					}
				} else {
					if (value instanceof Uri) {
						img.setImageURI((Uri) value);
					} else {
						try {
							img.setImageResource(Integer.parseInt(text));
						} catch (NumberFormatException nfe) {
							// FIXME 加载网络背景图片
							Bitmap bitmap = Cache.getBitmap(text);
							if (bitmap != null) {
								img.setImageBitmap(bitmap);
							} else {
								bitmapUtils.display(img, text);
							}
						}
					}
				}
			}
		} else if (v instanceof ProgressBar) {
			ProgressBar progress = (ProgressBar) v;
			if (value instanceof Number) {
				Number number = (Number) value;
				if (v instanceof RatingBar) {
					// RatingBar和ProgressBar设置值不同
					((RatingBar) v).setRating(number.floatValue());
				} else {
					progress.setProgress(number.intValue());
				}
			}
		} else {
			L.e(v.getClass() + " 该类型不被支持,value=" + value);
			return false;
		}
		return true;
	}

	/**
	 * @param context
	 * @param height
	 * @return
	 */
	public static View buildDividerView(Context context, int height) {
		View view = new View(context);
		view.setMinimumHeight(height);
		return view;
	}

	/**
	 * 需保证srcBmap的高宽大于width,height
	 * 
	 * @param srcBmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap clipCenterBitmap(Bitmap srcBmap, int width, int height) {
		int sw = srcBmap.getWidth();
		int sh = srcBmap.getHeight();
		int offsetX = Math.abs((sw - width) / 2);
		int offsetY = Math.abs((sh - height) / 2);
		L.e("src宽度:" + sw + ",src高度:" + sh + ",偏移x:" + offsetX + ",偏移y:"
				+ offsetY);
		return Bitmap.createBitmap(srcBmap, offsetX, offsetY, width, height,
				null, false);
	}

	/**
	 * 按照宽高缩放比例剪切 活动界面等缩放比例为0.92f, 0.58f
	 * 
	 * @param srcBmap
	 * @param xScale
	 * @param yScale
	 * @return
	 */
	public static Bitmap clipScaleAndClipBitmap(Bitmap srcBmap, float xScale,
			float yScale) {
		if (xScale == 1.0f && yScale == 1.0f)
			return srcBmap;
		int sw = srcBmap.getWidth();
		int sh = srcBmap.getHeight();
		/**/
		int width = (int) (sw * xScale);
		int height = (int) (sw * yScale);
		int offsetX = Math.abs((sw - width) / 2);
		int offsetY = Math.abs((sh - height) / 2);
		return Bitmap.createBitmap(srcBmap, offsetX, offsetY, width, height,
				null, false);
	}

	public static Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}
	public static BitmapUtils getBitmapUtils() {
		if(mBitmapUtils != null){
			mBitmapUtils.clearMemoryCache();
			mBitmapUtils = null;
		}
		mBitmapUtils = App.getInstance().getBitmapUtils();
		return mBitmapUtils;
	}
	public static void display(View view, String url, int defaultBitmap) {
		mBitmapUtils = getBitmapUtils();
		mBitmapUtils.configDefaultImageLoadAnimation(getAlphaAnimation());
		setViewValue(view, url, mBitmapUtils);
	}
	public static void display(View view, String url) {
		display(view, url, 0);
//		mBitmapUtils = getBitmapUtils();
//		mBitmapUtils.configDefaultBitmapMaxSize(new BitmapSize(480, 800));
//		mBitmapUtils.configDefaultImageLoadAnimation(getAlphaAnimation());
//		setViewValue(view, url, mBitmapUtils);
	}
	
}