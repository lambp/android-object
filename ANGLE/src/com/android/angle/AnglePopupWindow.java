package com.android.angle;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class AnglePopupWindow extends PopupWindow{
	    private View mMenuView;  
	    private  LayoutInflater inflater;
	    private int Width,Height;
	    public AnglePopupWindow(Activity context,int viewid) {  
	        super(context);  
	        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	        mMenuView = inflater.inflate(viewid, null);  
	        Width = LayoutParams.FILL_PARENT;
	        Height = LayoutParams.WRAP_CONTENT;
	        //设置的View  
	        this.setContentView(mMenuView);  
	        //设置弹出窗体的宽  
	        this.setWidth(Width);  
	        //设置弹出窗体的高  
	        this.setHeight(Height);  
	        //设置弹出窗体可点击  
	        this.setFocusable(true);  
	        //设置弹出窗体动画效果  
	       // this.setAnimationStyle(R.style.AnimBottom);  
	        //实例化一个ColorDrawable颜色为半透明  
	        ColorDrawable dw = new ColorDrawable(0xb0000000);  
	        //设置弹出窗体的背景  
	        this.setBackgroundDrawable(dw);  
	        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
	        mMenuView.setOnTouchListener(new OnTouchListener() {  
	            public boolean onTouch(View v, MotionEvent event) {  
	            	  dismiss();
	                return true;  
	            }  
	        });  
	  
	    }  
	    public void show(View pview,int x,int y){
	    	this.showAtLocation(pview, Gravity.TOP|Gravity.CENTER_HORIZONTAL, x, y);
	    }
	    public void setWH(int width,int height){
	    	Width = width;
	    	Height = height;
	    	this.setWidth(width);
	    	this.setHeight(height);
	    }
	    
	    public void setBg(Drawable bg){
	    	this.setBackgroundDrawable(bg);
	    }
	    
	    public void setOnclick(OnClickListener itemsOnClick){
	    	
	    }
}
