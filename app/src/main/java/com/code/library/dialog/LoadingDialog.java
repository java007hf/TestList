package com.code.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.code.library.utils.SystemUtils;
import com.code.library.view.RotateImageView;

import ju.xposed.com.jumodle.R;

/**
 * Created by Wxcily on 15/10/29.
 * 自定义加载动画
 */
public class LoadingDialog extends Dialog {
	public static final int STYLE_ROTATE = 1; // 默认旋转样式
	public static final int STYLE_FLIP = 2; // 翻转样式

	protected LoadingDialog(Context context) {
		super(context, R.style.PromptDialogStyle);
	}

	public static class Builder {
		private int STYLE;

		private LoadingDialog dialog;
		private Context context;

		private boolean cancelable;
		private boolean canceledOnTouchOutside;
		private int padding;
		private int margin;

		// 图片
		private Drawable icon;
		// title
		private CharSequence titleText;
		private int titleColor;
		private ColorStateList titleColorStateList;
		private float titleSize;
		private boolean titleBold;
		private int titleGravity;
		// message
		private CharSequence messageText;
		private int messageColor;
		private ColorStateList messageColorStateList;
		private float messageSize;
		private boolean messageBold;
		private int messageGravity;

		private OnCancelListener onCancelListener;

		public Builder(Context context) {
			dialog = new LoadingDialog(context);
			this.context = context;
			initData();
		}

		private void initData() {
			this.STYLE = STYLE_ROTATE;// 默认为旋转动画
			this.padding = 13;
			this.margin = 13;
			this.messageColor = context.getResources().getColor(
					R.color.bg_dialog_msg_text);
			this.titleColor = context.getResources().getColor(
					R.color.bg_dialog_msg_text);
			this.messageSize = 12;
			this.titleSize = 14;
			this.titleGravity = Gravity.LEFT;
			this.messageGravity = Gravity.LEFT;
			this.titleBold = false;
			this.messageBold = false;
			this.cancelable = false;
			this.canceledOnTouchOutside = false;
		}

		public Context getContext() {
			return context;
		}

		public Builder setStyle(int style) {
			this.STYLE = style;
			return this;
		}

		public Builder setTitleBarGravity(int titlebarGravity) {
			this.titleGravity = titlebarGravity;
			return this;
		}

		public Builder setMessageGravity(int messageGravity) {
			this.messageGravity = messageGravity;
			return this;
		}

		public Builder setTitle(CharSequence title) {
			this.titleText = title;
			return this;
		}

		public Builder setTitle(int titleResId) {
			this.titleText = context.getResources().getString(titleResId);
			return this;
		}

		public Builder setTitleColor(int titleColor) {
			this.titleColor = titleColor;
			return this;
		}

		public Builder setTitleColor(ColorStateList titleColor) {
			this.titleColorStateList = titleColor;
			return this;
		}

		public Builder setTitleSize(float titleSize) {
			this.titleSize = titleSize;
			return this;
		}

		public Builder setIcon(Drawable icon) {
			this.icon = icon;
			return this;
		}

		@SuppressWarnings("deprecation")
		public Builder setIcon(int iconResId) {
			this.icon = context.getResources().getDrawable(iconResId);
			return this;
		}

		public Builder setMessage(CharSequence message) {
			this.messageText = message;
			return this;
		}

		public Builder setMessage(int messageResId) {
			this.messageText = context.getResources().getString(messageResId);
			return this;
		}

		public Builder setMessageColor(int color) {
			this.messageColor = color;
			return this;
		}

		public Builder setMessageColor(ColorStateList color) {
			this.messageColorStateList = color;
			return this;
		}

		public Builder setMessageSize(float size) {
			this.messageSize = size;
			return this;
		}

		public Builder setCancelable(boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		public Builder setCanceledOnTouchOutside(boolean canceled) {
			this.canceledOnTouchOutside = canceled;
			return this;
		}

		public Builder setTitleBold(boolean bold) {
			this.titleBold = bold;
			return this;
		}

		public Builder setMessageBold(boolean bold) {
			this.messageBold = bold;
			return this;
		}

		public Builder setPadding(int padding) {
			this.padding = padding;
			return this;
		}

		public Builder setMargin(int margin) {
			this.margin = margin;
			return this;
		}

		public Builder setOnCancelListener(OnCancelListener onCancelListener) {
			this.onCancelListener = onCancelListener;
			return this;
		}


		public LoadingDialog create() {
			if (dialog == null) {
				return null;
			}
			// 获取布局实例
			View view = LayoutInflater.from(context).inflate(
					R.layout.framework_dialog_rotate, null);
			RotateImageView imageView = (RotateImageView) view
					.findViewById(R.id.rotateImageView);
			LinearLayout textLayout = (LinearLayout) view
					.findViewById(R.id.textLayout);
			TextView title = (TextView) view.findViewById(R.id.title);
			TextView message = (TextView) view.findViewById(R.id.message);
			// 初始化布局
			int p = SystemUtils.dp2px(context, padding);
			view.setPadding(p, p, p, p);
			LayoutParams layoutParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutParams
					.setMargins(SystemUtils.dp2px(context, margin), 0, 0, 0);
			layoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.rotateImageView);
			layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
			textLayout.setLayoutParams(layoutParams);
			// 设置默认的图片
			if (icon == null) {
				if (STYLE == STYLE_FLIP) {
					this.icon = context.getResources().getDrawable(
							R.drawable.framework_dialog_loading_flip);
				} else {
					this.icon = context.getResources().getDrawable(
							R.drawable.framework_dialog_loading_rotate);
				}
			}
			imageView.setImageDrawable(icon);
			imageView.setStyle(STYLE);

			if (titleText != null) {
				title.setText(titleText);
			}
			title.setTextSize(titleSize);
			title.setTextColor(titleColor);
			if (titleColorStateList != null) {
				title.setTextColor(titleColorStateList);
			}
			if (titleBold) {
				TextPaint textPaint = title.getPaint();
				textPaint.setFakeBoldText(true);
			}
			title.setGravity(titleGravity);

			if (messageText != null) {
				message.setVisibility(View.VISIBLE);
				message.setText(messageText);
				message.setTextSize(messageSize);
				message.setTextColor(messageColor);
				message.setGravity(messageGravity);
				if (messageColorStateList != null) {
					message.setTextColor(messageColorStateList);
				}
				if (messageBold) {
					TextPaint textPaint = message.getPaint();
					textPaint.setFakeBoldText(true);
				}
			}

			if (onCancelListener != null) {
				dialog.setOnCancelListener(onCancelListener);
			}

			dialog.setCancelable(cancelable);
			dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
			dialog.setContentView(view);
			return dialog;
		}

		public LoadingDialog show() {
			create().show();
			return dialog;
		}
	}

}
