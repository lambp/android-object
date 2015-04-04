package game.sprite;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;
//按钮
public class BtnSprite extends AngleSprite{
	public boolean hidden;
	public int fid;
	
	public BtnSprite(AngleSpriteLayout layout,AngleVector pos,int frameid) {
		super(layout);
		mPosition.set(pos);
		setFrame(frameid);
		fid = frameid;
		hidden = false;
	}
	public boolean test(float x, float y) {
		if(mDie||hidden) return false;
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		if (x > minx && x < maxx && y > miny && y < maxy)
			return true;
		return false;
	}
	public void sethidden(boolean _hidden){
		if(_hidden){
			mAlpha = 0;
		}else{
			mAlpha = 1;
		}
		hidden = _hidden;
	}
}
