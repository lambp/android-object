package game.sprite;

import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class linesp extends AngleRotatingSprite{
	AngleVector end;
	public linesp(int x, int y, AngleSpriteLayout layout) {
		super(x, y, layout);
		end = new AngleVector();
		mAlpha = 1.0f;
		mRotation = 90;
	}
	public void setRotation(AngleVector _end){
		end.set(_end);
		mAlpha = 1f;
		float dy = mPosition.mY-_end.mY;
		float dx = _end.mX-mPosition.mX;
		float dr = (float) Math.sqrt(dy*dy+dx*dx);
		float cos = (float)dx/(float)dr; 
		mRotation = ((float)180/(float)Math.PI)*((float) Math.acos(cos));
	}
	public AngleVector getend(){
		return end;
	}
	public void hidden(boolean show){
		if(show){
			mAlpha = 0.0f;
		}else{
			mAlpha = 1f;
		}
	}
	public float getRot(){
		
		return mRotation;
	}
}
