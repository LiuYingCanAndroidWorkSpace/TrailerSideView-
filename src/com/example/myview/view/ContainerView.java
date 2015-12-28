package com.example.myview.view;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.FrameLayout;

public class ContainerView extends FrameLayout implements OnPageRemoveListener{

	private List<PageLayout> list;
	private Context mContext;
	
	
	public ContainerView(Context context) {
		super(context);
		mContext = context;
		list = new LinkedList<PageLayout>();
		setBackgroundColor(Color.BLUE);
	}

	public void addPage(String title, String content) {
		PageLayout pageLayout = new PageLayout(mContext, this);
		android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(600, 1000);
		params.gravity = Gravity.CENTER;
		pageLayout.setLayoutParams(params);
		pageLayout.setTitle(title);
		pageLayout.setContent(content);
		addView(pageLayout);
		list.add(pageLayout);
	}

	@Override
	public void OnPageRemoved(PageLayout layout) {
		if (layout != null) {
			list.remove(layout);
			removeView(layout);
		}
	}
	

}
