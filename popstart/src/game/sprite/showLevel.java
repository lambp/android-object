package game.sprite;

import com.android.angle.AngleFont;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;
import com.android.angle.AngleVector;

public class showLevel extends AnglePhysicsEngine{
	AngleSprite bg;
	AngleString level;
	AngleString scores;
	AngleVector endPos;
	int move=5;
	boolean isalpha;
	int swidth,sheight;
	float bili;
	public showLevel(AngleSpriteLayout _bg,AngleFont font,int width,int height,int frameid,float _bili) {
		super(5);
		swidth=width;
		sheight=height;
		bg = new AngleSprite(_bg);
		bili = _bili;
		level = new AngleString(font,"",-width,height/2-10,AngleString.aCenter);
		scores = new AngleString(font,"",-width,height/2+(int)(50*bili),AngleString.aLeft);
		endPos = new  AngleVector(-width, height/2);
		bg.mPosition.set(endPos);
		level.mPosition.set(endPos);
		scores.mPosition.set(endPos);
		addObject(bg);
		addObject(level);
		addObject(scores);
		bg.setFrame(frameid);
	}
	public void setmove(AngleVector end,int _level,int _scores){
		move=5;
		bg.mPosition.set(2*swidth, sheight/2);
		level.mPosition.set(2*swidth,sheight/2-10);
		scores.mPosition.set(2*swidth,sheight/2+50*bili);
		endPos.set(end);
		bg.mAlpha=1;
		level.mAlpha=1;
		scores.mAlpha=1;
		level.set(""+_level);
		scores.set(""+_scores);
		
	}
	
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(bg.mPosition.mX!=endPos.mX){
			bg.mPosition.mX -= (float)secondsElapsed*move;
			level.mPosition.mX-= (float)secondsElapsed*move;
			scores.mPosition.mX-= (float)secondsElapsed*move;
			if(bg.mPosition.mX<endPos.mX){
				bg.mPosition.mX=endPos.mX;
				level.mPosition.mX=endPos.mX;
				scores.mPosition.mX=endPos.mX;
			}
		}
		move+=10;
		if(bg.mAlpha>0  && (bg.mPosition.mX==endPos.mX)){
			bg.mAlpha-=0.01;
			level.mAlpha-=0.01;
			scores.mAlpha-=0.01;
		}
	}
	public boolean checkmove(){
		if(bg.mPosition.mX==endPos.mX&&bg.mAlpha<0.5){
			
			return true;
		}
		return false;
	}
	
}
