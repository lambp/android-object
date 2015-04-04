package game.sprite;

import game.ui.GameUI;

import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class bombsp extends AngleSprite{
	double start;
	int max,min;
	boolean shownum;//是否调用显示
	public bombsp(AngleSpriteLayout layout,AngleVector pos,boolean shown) {
		super(layout);
		mPosition.set(pos);
		start=System.currentTimeMillis();
		max = layout.roFrameCount;
		min = 0;
		shownum=shown;
	}

	@Override
	public void step(float secondsElapsed) {

		if(System.currentTimeMillis()-start>80){
			start=System.currentTimeMillis();
			this.setFrame(min);
			min++;
			if(min>max){
				this.mDie=true;
				if(shownum){
					((GameUI)((AnglePhysicsEngine)mParent).mParent).showNums(mPosition);
				}
			}
		}
		super.step(secondsElapsed);
	}
	
}
