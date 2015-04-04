package game.data;

import game.ui.GameUI;

import android.util.Log;

import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class tempball extends AngleSprite{
	double start;
	AngleVector topos;
	int mstep;
	public tempball(AngleSpriteLayout layout,AngleVector pos,int faid) {
		super(layout);
		setFrame(faid);
		mPosition.set(pos);
		topos = new AngleVector(pos);
	}
	public void moveto(AngleVector _pos){
		topos.set(_pos);
		mstep = 5;
	}
	public void setpos(AngleVector _pos){
		mPosition.set(_pos);
		topos.set(_pos);
		
	}
	@Override
	public void step(float secondsElapsed) {

		if(System.currentTimeMillis()-start>80){
			start=System.currentTimeMillis();
			if(mPosition.mX<topos.mX){
				mstep+=2;
				mPosition.mX+=mstep;
				if(mPosition.mX>=topos.mX) {
					mPosition.mX=topos.mX;
					((GameUI)((AnglePhysicsEngine)mParent).mParent).nextPop();
				}
				if(mScale.mX<1){
					mScale.set(1,1);
				}
			}
		}
		super.step(secondsElapsed);
	}
}
