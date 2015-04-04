package game.sprite;

import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class timelinesp extends AngleRotatingSprite{
	double start;
	int max,min;
	public boolean show;
	public timelinesp(AngleSpriteLayout layout,AngleVector pos) {
		super(layout);
		mPosition.set(pos);
		start=System.currentTimeMillis();
		max = layout.roFrameCount;
		min = 0;
		mRotation=90;
		show = false;
		mAlpha=0;
	}
	public void setshow(boolean _show){
		show = _show;
		if(_show){
			mAlpha=1;
		}else{
			mAlpha=0;
		}
	}
	@Override
	public void step(float secondsElapsed) {

		if(System.currentTimeMillis()-start>500&&show){
			start=System.currentTimeMillis();
			this.setFrame(min);
			min++;
			if(min>max){
				min%=max;
			}
		}
		super.step(secondsElapsed);
	}
	
}
