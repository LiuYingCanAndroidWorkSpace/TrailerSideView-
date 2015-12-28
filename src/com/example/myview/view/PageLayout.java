package com.example.myview.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myview.R;
import com.example.myview.anima.Rotate3dAnimation;

public class PageLayout extends LinearLayout {

	private Context mContext;
	private TextView tv_title, tv_content;
	private OnPageRemoveListener listener;

	public PageLayout(Context context, OnPageRemoveListener listener) {
		super(context);
		mContext = context;
		this.listener = listener;
		initView();
	}

	public void setTitle(String title) {
		if (title == null || tv_title == null)
			return;
		tv_title.setText(title);
	}

	public void setContent(String content) {
		if (content == null || tv_title == null)
			return;
		tv_content.setText(content);
	}

	private void initView() {
		LinearLayout view = (LinearLayout) View.inflate(mContext, R.layout.page_layout, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		view.setLayoutParams(params);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		addView(view);
	}

	private float lastDegree = 0;
	public void rotate(final float fromDegree, final float toDegree) {
		Rotate3dAnimation rotation = new Rotate3dAnimation(fromDegree, toDegree, getWidth()/2, getHeight(), 0, false);
		long duration = (long) Math.abs(3*(toDegree - fromDegree));
		rotation.setDuration(duration);  
		// 动画完成后保持完成的状态  
		rotation.setFillAfter(true); 
		rotation.setInterpolator(new AccelerateInterpolator());  
		rotation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				isAnimateAct = false;
				lastDegree = toDegree;
			}
		});
		isAnimateAct = true;
		startAnimation(rotation);
		
	}

	private boolean isAnimateAct = false;
	private float lastX = 0;
	private float lastY = 0;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float x = ev.getX();
			float y = ev.getY();
			//				float degree = ((float)(lastX - x));
			float centerX = x - getWidth()/2;
			float degree = countDegree(y, centerX);
			float centerLastX = lastX - getWidth()/2;
			float temp = countDegree(lastY, centerLastX);
			degree = (float) (degree - temp);
			if (Math.abs(degree) <= 3) {
				lastX = x;
				lastY = y;
				break;
			}
			if (!isAnimateAct) {
				rotate(lastDegree, lastDegree + degree);
				lastDegree = lastDegree + degree;
			}
			lastX = x;
			lastY = y;
			break;
		case MotionEvent.ACTION_UP:
			lastX = 0;
			lastY = 0;
			if (lastDegree>=30 || lastDegree<=-30) {
				listener.OnPageRemoved(this);
			} else {
				rotate(lastDegree, 0);
			}
			break;
		case MotionEvent.ACTION_CANCEL:	
			break;
		case MotionEvent.ACTION_DOWN:	
			lastX = ev.getX();
			lastY = ev.getY();
			break;	
		}
		return super.dispatchTouchEvent(ev);
	}


	private float countDegree(float y, float x) {
		float f = (float) Math.atan2(y, x);
		float temp = (float) (3.1415/f);
		f = 180 / temp;
		//		System.out.println(f);
		return f;
	}
}
