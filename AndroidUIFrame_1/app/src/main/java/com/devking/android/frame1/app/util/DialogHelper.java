package com.devking.android.frame1.app.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import com.devking.android.frame1.app.R;


public class DialogHelper {
	private static Dialog mDialog;
	private Context ctx;

	public DialogHelper(Context ctx) {
		this(ctx, R.style.transparentDialog);
		this.ctx = ctx;
	}

	public DialogHelper(Context ctx, int style) {
		setmDialog(new Dialog(ctx, style));
		this.ctx = ctx;
	}

	public static void showPbarDialog(Activity act) {
		if (mDialog != null && mDialog.isShowing()) {
			return;
		}
		DialogHelper dh = new DialogHelper(act, R.style.Mydialog);
		dh.setView(R.layout.dialog_progress_layout);
		dh.setCancel(false);
		dh.show();
		
	}
	
	public static Dialog getmDialog() {
		return mDialog;
	}

	public static void setmDialog(Dialog mDialog) {
		DialogHelper.mDialog = mDialog;
	}

	public static void hidePbarDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}

	/**
	 * 生成默认的简易对话框，只需要传入title和content
	 * 
	 * @param act
	 * @param title
	 * @param content
	 */
	public DialogHelper(Activity act, String title, String content) {
		this(act, R.style.Mydialog);
		setView(R.layout.dialog_common_layout);
		setViewValue(R.id.dialog_title_tv, title);
		setViewValue(R.id.dialog_content_tv, content);
		setCancel(true);
		show();
//		DisplayMetrics dm2 = act.getResources().getDisplayMetrics();
//		WindowManager.LayoutParams p = act.getWindow().getAttributes();
//		p.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//		p.width = (int) (dm2.widthPixels * 0.95);
//		p.dimAmount = 0.7f;
//		T.setDialogParameter(this, act, p);
	}
	
	
	

//	/**
//	 * 生成新手引导
//	 *
//	 * @param act
//	 * @param layout
//	 * @param isDefault
//	 */
//	public DialogHelper(Activity act, int layout, boolean isDefault) {
//		this(act, R.style.Mydialog);
//		setView(App.getView(layout));
//		show();
//		WindowManager.LayoutParams p = act.getWindow().getAttributes();
//		p.height = LinearLayout.LayoutParams.MATCH_PARENT;
//		p.width = LinearLayout.LayoutParams.MATCH_PARENT;
//		p.dimAmount = 0.7f;
//		T.setDialogParameter(this, act, p);
//		setOnClickClose(R.id.default_layout);
//	}

	public DialogHelper setViewValue(int id, Object value) {
		View v = findViewById(id);
		ViewHelper.setViewValue(v, value);
		return this;
	}
	

	public String getViewValue(int id) {
		View v = findViewById(id);
		if (v instanceof TextView) {
			return ((TextView) v).getText().toString();
		}
		return null;
	}

	public DialogHelper setOnClickClose(View v) {
		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		return this;
	}

	public DialogHelper setOnClickClose(int resId) {
		return setOnClickClose(findViewById(resId));
	}

	public Dialog show() {
		mDialog.show();
		return mDialog;
	}

	public static Dialog getDialog() {
		return mDialog;
	}

	public void dismiss() {
		mDialog.dismiss();
	}

	public DialogHelper setCancel(boolean cancel) {
		mDialog.setCancelable(cancel);
		mDialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findViewById(int id) {
		return (T) (mDialog.findViewById(id));
	}

	public DialogHelper setAnimation(int animStyleId) {
		mDialog.getWindow().getAttributes().windowAnimations = animStyleId;
		return this;
	}

	public DialogHelper setGravity(int gravity) {
		mDialog.getWindow().getAttributes().gravity = gravity;
		return this;
	}

	public DialogHelper setView(int resId) {
		mDialog.setContentView(resId);
		return this;
	}

	public DialogHelper setView(View view) {
		mDialog.setContentView(view);

		return this;
	}

	public void scaleWidth(float scale) {
		Display d = getDialog().getWindow().getWindowManager().getDefaultDisplay();
		WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
		params.width = (int) (d.getWidth() * scale);
		getDialog().getWindow().setAttributes(params);
	}
	
	
	
	/**
	 * 带有三个按钮的Dialog
	 */
	public static class ThreeButtonDialog extends DialogHelper {

		private TextView tv_name;
		private TextView tv_content;
		private TextView tv_text01;
		private TextView tv_text02;
		private TextView tv_text03;
		private OnButtonClickListener mListener;
		private OnClickListener mClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tv_text01:
					if (mListener != null) {
						mListener.onClick(v, 0);
					}
					break;
				case R.id.tv_text02:
					if (mListener != null) {
						mListener.onClick(v, 1);
					}
					break;
				case R.id.tv_text03:
					if (mListener != null) {
						mListener.onClick(v, 2);
					}
					break;
				}
			}
		};

		public ThreeButtonDialog(Context ctx) {
			this(ctx, null, null);
		}

		public ThreeButtonDialog(Context ctx, String title, String... label) {
			super(ctx);
			setView(R.layout.dialog_three_button);
			initViews();
			setTitle(title);
			setButtonText(label);
			scaleWidth(0.9f);
			setCancel(true);
		}

		public void setButtonText(String... label) {
			int num = label == null ? 0 : label.length;
			switch (num) {
			case 3:
				tv_text03.setVisibility(View.VISIBLE);
				tv_text03.setText(label[2]);
				tv_text03.setOnClickListener(mClickListener);
			case 2:
				tv_text02.setVisibility(View.VISIBLE);
				tv_text02.setText(label[1]);
				tv_text02.setOnClickListener(mClickListener);
			case 1:
				tv_text01.setVisibility(View.VISIBLE);
				tv_text01.setText(label[0]);
				tv_text01.setOnClickListener(mClickListener);
			}
		}

		public ThreeButtonDialog setClosePosition(int p) {
			switch (p) {
			case 0:
				setOnClickClose(tv_text01);
				break;
			case 1:
				setOnClickClose(tv_text02);
				break;
			case 2:
				setOnClickClose(tv_text03);
				break;
			}
			return this;
		}

		public void setTitle(String title) {
			setViewValue(R.id.tv_name, title);
		}

		public ThreeButtonDialog setMessage(CharSequence text) {
			tv_content.setText(text);
			return this;
		}

		private void initViews() {
			tv_name = findViewById(R.id.tv_name);
			tv_content = findViewById(R.id.tv_content);
			tv_text01 = findViewById(R.id.tv_text01);
			tv_text02 = findViewById(R.id.tv_text02);
			tv_text03 = findViewById(R.id.tv_text03);
		}

		public void setOnButtonClickListener(OnButtonClickListener l) {
			mListener = l;
		}

		public static interface OnButtonClickListener {
			void onClick(View v, int position);
		}
		
	}
}
