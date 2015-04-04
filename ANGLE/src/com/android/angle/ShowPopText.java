package com.android.angle;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;
//提示动画
public class ShowPopText extends AngleSprite{
	AngleVector endPos;
	int move=5;
	public boolean show;//是否可以消失
	float scales; 
	//不放大
	public ShowPopText(AngleSpriteLayout layout,AngleVector start,int fid,boolean _show) {
		super(layout);
		mPosition.set(start);
		endPos = new AngleVector(start);
		this.setFrame(fid);
		show=_show;
		scales = 1;
		mScale.set(scales,scales);
	}
	public ShowPopText(AngleSpriteLayout layout,AngleVector start,int fid,boolean _show,int scal) {
		super(layout);
		mPosition.set(start);
		endPos = new AngleVector(start);
		this.setFrame(fid);
		show=_show;
		scales = scal;
		mScale.set(scales,scales);
	}
	//可以消失
	public ShowPopText(AngleSpriteLayout layout,AngleVector start,int fid,float _scal) {
		super(layout);
		mPosition.set(start);
		endPos = new AngleVector(start);
		this.setFrame(fid);
		scales=_scal;
		show = true;
		mScale.set(scales,scales);
	}
	public void setmove(AngleVector start,AngleVector end){
		mScale.set(5,5);
		mPosition.set(start);
		endPos.set(end);
		scales=5;
	}
	public void setmove(AngleVector start,AngleVector end,boolean _isalpha){
		mScale.set(5,5);
		mPosition.set(start);
		endPos.set(end);
		scales=5;
		show = _isalpha;
		mAlpha=1;
	}
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(mPosition.mX!=endPos.mX){
			mPosition.mX -= (float)secondsElapsed*move;
			if(mPosition.mX<endPos.mX) mPosition.mX=endPos.mX;
			move+=5;
		}
		if(mPosition.mY!=endPos.mY){
			mPosition.mY -= (float)secondsElapsed*move;
			if(mPosition.mY<endPos.mY) mPosition.mY=endPos.mY;
			move+=5;
		}
		
		//变小后消失
		if(scales>0 && show){
			if(scales>1) {
				scales-=0.1;
				mScale.set(scales,scales);
			}
			if(scales<=1){
				mAlpha-=0.01;
				if(mAlpha<=0) mDie=true;
			}
		}
	}	
}
