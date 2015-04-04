package game.sprite;

import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;
//按钮
public class LockSprite extends AnglePhysicsEngine{
	public int step;
	public float newX;
	public boolean isLock;
	private AngleVector pos;
	private AngleSpriteLayout roLayout;
	private AngleSprite lock;
	private shownum stepnum;
	public LockSprite(AngleSpriteLayout layout,int step,int x,int y,boolean isLock,AngleSpriteLayout font) {
		super(3);
		newX = x;
		pos = new AngleVector(x,y);
		this.step=step;
		this.isLock=isLock;
		roLayout = layout;
		lock = new AngleSprite(x, y, layout);
		addObject(lock);
		//stepnum =new AngleString(font,""+step,x,y+layout.roWidth/3,AngleString.aCenter);
		stepnum = new shownum(""+step, font, new AngleVector(x,y), 1, false);
		if(!isLock){
			addObject(stepnum);
		}
		// TODO 自动生成的构造函数存根
	}
	//检查触摸状态
	public boolean test(float x, float y) {
		// wx,hy分别为显示的图片的大小，不是图片的真实大小
		
		float minx = pos.mX - roLayout.roWidth / 2;
		float miny = pos.mY - roLayout.roWidth / 2;
		float maxx = pos.mX + roLayout.roHeight / 2;
		float maxy = pos.mY + roLayout.roHeight / 2;
		if (x > minx && x < maxx && y > miny && y < maxy)
			return true;
		return false;
	}
	
	public void setXY(float x){
		x += pos.mX;
		pos.set(x, pos.mY);
		lock.mPosition.set(pos);
		stepnum.setpos(x, pos.mY);
	}

	
}
