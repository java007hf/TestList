package com.code.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.code.library.dialog.LoadingDialog;

import ju.xposed.com.jumodle.R;


/**
 * Created by Wxcily on 15/10/29.
 * 可以旋转和翻转图片的Imageview
 */
public class RotateImageView extends ImageView {
	private Context context;
	private int style = LoadingDialog.STYLE_ROTATE;
	private boolean mIsHasAnimation;

	public RotateImageView(Context context) {
		super(context);
		this.context = context;
	}

	public RotateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public RotateImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	private void setFlipAnimation() {
		RotateAnimation anim = new RotateAnimation(getWidth() / 2.0F,
				getHeight() / 2.0F, RotateAnimation.Mode.Y);
		anim.setDuration(1000L);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(-1);
		anim.setRepeatMode(Animation.RESTART);
		setAnimation(anim);
	}

	private void setRotateAnimation() {
		Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate);
		anim.setInterpolator(new LinearInterpolator());
		setAnimation(anim);
	}

	public void setStyle(int style) {
		this.style = style;
	}

	private void setAnim() {
		if (mIsHasAnimation == false && getWidth() > 0
				&& getVisibility() == View.VISIBLE) {
			if (style == LoadingDialog.STYLE_FLIP) {
				setFlipAnimation();
			} else {
				setRotateAnimation();
			}
		}
	}

	private void clearRotateAnimation() {
		if (mIsHasAnimation) {
			mIsHasAnimation = false;
			setAnimation(null);
		}
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (visibility == View.INVISIBLE || visibility == View.GONE) {
			clearRotateAnimation();
		} else {
			setAnim();
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		setAnim();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		clearRotateAnimation();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (w > 0) {
			setAnim();
		}
	}

}
