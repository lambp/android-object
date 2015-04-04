package com.android.angle;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
//开关按钮
public class openBtn extends AngleSprite{
	int start=0;//0开,1关
	public openBtn(int x,int y,AngleSpriteLayout layout) {
		super(x,y,layout);
	}
	public openBtn(int x,int y,AngleSpriteLayout layout,int frame) {
		super(x,y,layout);
		start=frame;
		setFrame(frame);
	}
	
	public int test(float x, float y) {
		if(mDie) return -1;
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		if (x > minx && x < maxx && y > miny && y < maxy){
			if(0==start){
				setFrame(1);
				start=1;
			}else{
				setFrame(0);
				start=0;
			}
			return start;
		}
		return -1;
	}
	
}
