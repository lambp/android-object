package game.sprite;

import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;
//通用按钮
public class combtn extends AnglePhysicsEngine{
	AngleVector moveTo;
	int step =5;
	int sp;
	AngleSpriteLayout roLayout,titlelayout;
	AngleSprite bgSprite,titleSprite;
	AngleVector pos;
	double starttime;
	public combtn(AngleVector _Pos,AngleSpriteLayout _bglayout,AngleSpriteLayout _titlely,int _frame) {
		super(3);
		roLayout = _bglayout;
		titlelayout = _titlely;
		bgSprite = new AngleSprite((int)_Pos.mX,(int)_Pos.mY,_bglayout);
		titleSprite = new AngleSprite((int)_Pos.mX,(int)_Pos.mY,_titlely);
		titleSprite.setFrame(_frame);
		this.addObject(bgSprite);
		this.addObject(titleSprite);
		pos = new AngleVector();
		pos.set(_Pos);
		moveTo=new AngleVector();
		moveTo.set(_Pos);
	}
	public void setAp(float _val){
		bgSprite.mAlpha=_val;
	}
	public boolean test(float x, float y) {
		if(mDie) return false;
		float minx = bgSprite.mPosition.mX - roLayout.roWidth / 2;
		float miny = bgSprite.mPosition.mY - roLayout.roHeight / 2;
		float maxx = bgSprite.mPosition.mX + roLayout.roWidth / 2;
		float maxy = bgSprite.mPosition.mY + roLayout.roHeight / 2;
		
		if (x > minx && x < maxx && y > miny && y < maxy){
			
			return true;
		}
		return false;
	}
	public void Moveto(AngleVector _to,int _sp){
		moveTo.set(_to);
		sp = _sp;
		starttime = System.currentTimeMillis();
	}
	
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if((System.currentTimeMillis()-starttime)>50){
			starttime = System.currentTimeMillis();
			if(pos.mX!=moveTo.mX){
				pos.mX += secondsElapsed*sp*step;
				if(sp>0&&pos.mX>moveTo.mX){
					pos.mX=moveTo.mX;
					step=5;
				}
				if(sp<0&&pos.mX<moveTo.mX){
					pos.mX=moveTo.mX;
					step=5;
				}
				step+=5;
			}
			if(pos.mY!=moveTo.mY){
				pos.mY += secondsElapsed*sp*step;
				if(sp>0&&pos.mY>moveTo.mY){
					pos.mY=moveTo.mY;
					step=5;
				}
				if(sp<0&&pos.mY<moveTo.mY){
					pos.mY=moveTo.mY;
					step=5;
				}
				step+=5;
			}
			bgSprite.mPosition.set(pos);
			titleSprite.mPosition.set(pos);
		}
	}
}
