package com.android.angle;

/**时间显示**/
public class timeSprite extends AnglePhysicsEngine{
	AngleSprite H_1,H_2,M_1,M_2,S_1,S_2,D_1,D_2;
	AngleSpriteLayout numlay;
	AngleVector pos;
	public int value,Hn_1,Hn_2,Mn_1,Mn_2,Sn_1,Sn_2;
	public boolean start=false;
	double reflash;
	public timeSprite(AngleSpriteLayout numl,AngleVector mid) {
		super(8);
		value = 0;
		numlay =numl;
		reflash = System.currentTimeMillis();
		int wd = numl.roWidth;
		M_1 = new AngleSprite((int)(mid.mX-wd/2),(int)mid.mY,numl);
		M_2 = new AngleSprite((int)(mid.mX+wd/2),(int)mid.mY,numl);
		D_1 = new AngleSprite((int)(mid.mX+wd+wd/4),(int)mid.mY,numl);
		S_1 = new AngleSprite((int)(mid.mX+2*wd),(int)mid.mY,numl);
		S_2 = new AngleSprite((int)(mid.mX+3*wd),(int)mid.mY,numl);
		D_2 = new AngleSprite((int)(mid.mX-wd-wd/4),(int)mid.mY,numl);
		H_1 = new AngleSprite((int)(mid.mX-3*wd),(int)mid.mY,numl);
		H_2 = new AngleSprite((int)(mid.mX-2*wd),(int)mid.mY,numl);
		addObject(M_1);
		addObject(M_2);
		addObject(S_1);
		addObject(S_2);
		addObject(D_1);
		addObject(D_2);
		addObject(H_1);
		addObject(H_2);
		D_1.setFrame(46);
		D_2.setFrame(46);
	}
	void jishuantime(){
		int Sumh = value/3600;
		int Summ = (value-Sumh*3600)/60;
		int Sums = (value-Sumh*3600-Summ*60);
		Hn_1 = Sumh/10;
		Hn_1 %=100;
		Hn_2 = Sumh%10;
		Mn_1 = Summ/10;
		Mn_2 = Summ%10;
		Sn_1 = Sums/10;
		Sn_2 = Sums%10;
		M_1.setFrame(Mn_1+10);
		M_2.setFrame(Mn_2+10);
		H_1.setFrame(Hn_1+10);
		H_2.setFrame(Hn_2+10);
		S_1.setFrame(Sn_1+10);
		S_2.setFrame(Sn_2+10);
	}
	//重置数值
	public void reset(int val){
		value = val;
		jishuantime();
	}
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if((System.currentTimeMillis()-reflash)>1000){
			reflash = System.currentTimeMillis();
			if(start){
				jishuantime();
				value++;
			}
		}
	}
	
}
