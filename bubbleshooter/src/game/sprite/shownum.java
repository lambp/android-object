package game.sprite;

import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

/**时间数字**/
public class shownum extends AnglePhysicsEngine{
	AngleSprite[] numsp;
	AngleSpriteLayout numlay;
	AngleVector pos;
	String value;
	int length,wd,duiqi;
	public boolean hidden;
	public shownum(String _value,AngleSpriteLayout numl,AngleVector start,int _duiqi,boolean hiddens) {
		super(18);
		//duiqi 0-左对齐 1-居中,2-右对齐
		numsp = new AngleSprite[18];
		length = _value.length();
		wd = numl.roWidth;
		value = _value;
		numlay =numl;
		duiqi = _duiqi;
		pos = start;
		hidden = hiddens;
		
		for(int i=0;i<length;i++){
			int tval = Integer.parseInt(_value.substring(i, i+1));
			numsp[i] = new AngleSprite(numlay);
			numsp[i].setFrame(tval);
			numsp[i].mScale.set(0.6f,0.6f);
			if(duiqi==0){
				numsp[i].mPosition.set(pos.mX+i*wd/2,pos.mY);
			}else if(duiqi==1){
				numsp[i].mPosition.set(pos.mX+(i-length/2)*wd/2,pos.mY);
			}else{
				numsp[i].mPosition.set(pos.mX-(length-i)*wd/2,pos.mY);
			}
			addObject(numsp[i]);
		}
		
	}
	public void setpos(float x,float y){
		for(int i=0;i<length;i++){
			if(duiqi==0){
				numsp[i].mPosition.set(x+i*wd/2,y);
			}else if(duiqi==1){
				numsp[i].mPosition.set(x+(i-length/2)*wd/2,y);
			}else{
				numsp[i].mPosition.set(x-(length-i)*wd/2,y);
			}
		}
	}
	double start;
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(System.currentTimeMillis()-start>100){
			start = System.currentTimeMillis();
			if(hidden){
				for(int i=0;i<length;i++){
					numsp[i].mAlpha-=0.05;
					if(numsp[i].mAlpha<0) {
						numsp[i].mDie = true;
						mDie=true;
					}
				}
			}
		}
	}
	
	
	
}
