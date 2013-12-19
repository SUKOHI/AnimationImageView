package com.sukohi.lib;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AnimationImageView extends ImageView {

	private ArrayList<Integer> resourceList = new ArrayList<Integer>();
	private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
	private String setMode = "resource";
	private int intervalMilliSeconds = 1000;
	private int currentItemNumber = 0;
	private Handler handler = new Handler();
	private Runnable runnable;
	private boolean initialFlag = true;
	private boolean runFlag = false;
	
	public AnimationImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public AnimationImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public AnimationImageView(Context context) {
		super(context);
	}
	
	public void setInterval(int milliSeconds) {
		
		intervalMilliSeconds = milliSeconds;
		
	}
	
	public void setArrayImageBitmaps(ArrayList<Bitmap> list) {
		
		bitmapList = list;
		setMode = "bitmap";
		setImage(0);
		
	}
	
	public void setArrayImageResources(ArrayList<Integer> list) {
		
		resourceList = list;
		setMode = "resource";
		setImage(0);
		
	}
	
	public int imageCount() {
		
		if(isResource()) {
			
			return resourceList.size();
			
		}
		
		return bitmapList.size();
		
	}
	
	private void start() {
		
		runFlag = true;
		
		runnable = new Runnable() {
			public void run() {
				
				if(runFlag && getItemCount() > 0) {
					
					handler.postDelayed(runnable, intervalMilliSeconds);
					setImage(currentItemNumber);
			  		currentItemNumber++;
			  		
			  		if(isOverMax()) {
			  			currentItemNumber = 0;
			  		}
		  		
				}
				
			}
		};
		handler.postDelayed(runnable, intervalMilliSeconds);
		
	}
	
	private void setImage(int item) {
		
		if(isResource()) {
			
			setImageResource(resourceList.get(item));
				
		} else {
				
			setImageBitmap(bitmapList.get(item));
				
		}
		
	}
	
	private int getItemCount() {
		
		if(isResource()) {
			
			return resourceList.size();
				
		}
				
		return bitmapList.size();
		
	}
	
	private Boolean isResource() {
		
		return setMode.equals("resource");
		
	}
	
	private Boolean isOverMax() {
		
		return currentItemNumber >= imageCount();
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(initialFlag) {
			
			initialFlag = false;
			start();
			
		}
		
	}
	
	@Override
    protected void onDetachedFromWindow() {
		runFlag = false;
		handler.removeCallbacks(runnable);
    	super.onDetachedFromWindow();
    }
	
}
/***Sample

	AnimationImageView animationImageView = (AnimationImageView) findViewById(R.id.animation_image_view);
	ArrayList<Integer> resourceList = new ArrayList<Integer>();
	resourceList.add(R.drawable.having_0);
	resourceList.add(R.drawable.having_1);
	resourceList.add(R.drawable.having_2);
	animationImageView.setInterval(1000);	// You can omit.
	animationImageView.setArrayImageResources(resourceList);	// or animationImageView.setArrayImageBitmap(bitmapList)
	
***/
