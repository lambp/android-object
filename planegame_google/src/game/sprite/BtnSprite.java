package game.sprite;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;
//按钮
public class BtnSprite extends AngleSprite{
	double start;
	public BtnSprite(int x,int y,AngleSpriteLayout layout) {
		super(x,y,layout);
	}
	public BtnSprite(int x,int y,AngleSpriteLayout layout,int frame) {
		super(x,y,layout);
		setFrame(frame);
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
	public void setPos(int frameid,AngleVector pos){
		setFrame(frameid);
		mPosition.set(pos);
	}
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		
		if((System.currentTimeMillis()-start)>10){
			start=System.currentTimeMillis();
		}
	}
	public void setfid(int id){
		setFrame(id);
	}
	public void setmpos(AngleVector pos){
		mPosition.set(pos);
	}
}
