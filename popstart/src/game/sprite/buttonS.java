package game.sprite;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class buttonS extends AngleSprite{
	AngleVector moveTo;
	int step =5;
	int sp;
	public buttonS(int x,int y,AngleSpriteLayout layout) {
		super(x,y,layout);
	}
	public buttonS(int x,int y,AngleSpriteLayout layout,int frame) {
		super(x,y,layout);
		setFrame(frame);
		moveTo=new AngleVector(x,y);
	}
	public boolean test(float x, float y) {
		if(mDie) return false;
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		if (x > minx && x < maxx && y > miny && y < maxy)
			return true;
		return false;
	}
	public void Moveto(AngleVector _to,int _sp){
		moveTo.set(_to);
		sp = _sp;
	}
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(mPosition.mX!=moveTo.mX){
			mPosition.mX += secondsElapsed*sp*step;
			if(sp>0&&mPosition.mX>moveTo.mX){
				mPosition.mX=moveTo.mX;
				step=5;
			}
			if(sp<0&&mPosition.mX<moveTo.mX){
				mPosition.mX=moveTo.mX;
				step=5;
			}
			step+=5;
		}
		if(mPosition.mY!=moveTo.mY){
			mPosition.mY += secondsElapsed*sp*step;
			if(sp>0&&mPosition.mY>moveTo.mY){
				mPosition.mY=moveTo.mY;
				step=5;
			}
			if(sp<0&&mPosition.mY<moveTo.mY){
				mPosition.mY=moveTo.mY;
				step=5;
			}
			step+=5;
		}
	}
}
