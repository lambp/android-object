package com.android.angle;

/**时间数字**/
public class shownum extends AnglePhysicsEngine{
	AngleSprite[] numsp;
	AngleSpriteLayout numlay;
	AngleVector pos;
	String value;
	int length;
	boolean hidden;
	public shownum(String _value,AngleSpriteLayout numl,AngleVector start,int duiqi,boolean hiddens) {
		super(18);
		//duiqi 0-左对齐 1-居中,2-右对齐
		numsp = new AngleSprite[18];
		length = _value.length();
		int wd = numl.roWidth;
		value = _value;
		numlay =numl;
		pos = start;
		hidden = hiddens;
		for(int i=0;i<length;i++){
			int tval = Integer.parseInt(value.substring(i,i));
			numsp[i].setLayout(numl);
			numsp[i].setFrame(tval);
			if(duiqi==0){
				numsp[i].mPosition.set(pos.mX+i*wd,pos.mY);
			}else if(duiqi==1){
				numsp[i].mPosition.set(pos.mX+(i-length/2)*wd,pos.mY);
			}else{
				numsp[i].mPosition.set(pos.mX-(length-i)*wd,pos.mY);
			}
			addObject(numsp[i]);
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
					numsp[i].mAlpha-=0.01;
					if(numsp[i].mAlpha<0) numsp[i].mDie = true;
					mDie=true;
				}
			}
		}
	}
	
	
	
}
