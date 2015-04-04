package game.sprite;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class showTag extends AngleSprite{
	AngleVector endPos;
	int move=5;
	boolean isalpha;
	float scales; 
	public showTag(AngleSpriteLayout layout,AngleVector start,int fid) {
		super(layout);
		//StartPos = new AngleVector(start);
		mPosition.set(start);
		endPos = new AngleVector(start);
		//scales=0;
		this.setFrame(fid);
		isalpha=false;
	}
	public showTag(AngleSpriteLayout layout,AngleVector start,int fid,boolean show) {
		super(layout);
		//StartPos = new AngleVector(start);
		mPosition.set(start);
		endPos = new AngleVector(start);
		this.setFrame(fid);
		isalpha=show;
		scales=3;
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
		isalpha = _isalpha;
		mAlpha=1;
	}
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(mPosition.mX!=endPos.mX){
			mPosition.mX -= (float)secondsElapsed*move;
			if(mPosition.mX<endPos.mX) mPosition.mX=endPos.mX;
		}
		if(mPosition.mY!=endPos.mY){
			mPosition.mY -= (float)secondsElapsed*move;
			if(mPosition.mY<endPos.mY) mPosition.mY=endPos.mY;
		}
		move+=5;
		if(scales>1){
			scales-=0.1;
			mScale.set(scales,scales);
		}
		if(mAlpha>0 && isalpha && (scales<=1)){
			mAlpha-=0.03;
			if(mAlpha<=0) mDie=true;
		}
	}	
}
