package game.sprite;

import android.util.Log;

import com.android.angle.AngleFont;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleString;
import com.android.angle.AngleVector;

public class font_numbers extends AnglePhysicsEngine{
	public int value,add,step=1;
	double start;
    AngleString[]  Astring = new AngleString[10];
    AngleFont font;
    AngleVector[] pos = new AngleVector[10];
	public font_numbers(int _num,AngleFont _font,AngleVector _pos,float _dx) {
		super(20);
		start = System.currentTimeMillis();;
		font = _font;
	
		this.initpos(_pos, _dx);
		
		char[] num = (""+_num).toCharArray();
		
		int len = num.length;
		for(int i=0;i<len;i++){
			Log.v("log",""+num[i]);
			Astring[len-i-1].set(""+num[i]);
		}		
	}
	
	//初始化位置
	private void initpos(AngleVector _pos,float dx){
		for(int i=0;i<10;i++){
			pos[i] = new AngleVector(_pos.mX-dx*i,_pos.mY);
			Astring[i] = new  AngleString(this.font,"",(int)pos[i].mX,(int)pos[i].mY,AngleString.aCenter);
			this.addObject(Astring[i]);
		}		
	}
	
	//更新变化的数字
	private void update_num(int _num){
		
		char[] num = (""+_num).toCharArray();		
		int len = num.length;
		for(int i=0;i<len;i++){
			int old =0;
			if(Astring[len-i-1].getString()!=""){
				old = Integer.parseInt(Astring[len-i-1].getString());
			}
			if(old!=Integer.parseInt(""+num[i])){
			 Astring[len-i-1].set(""+num[i]);
			}
		}	
	}
	
	
	public void reset_num(){
		for(int i=0;i<10;i++){
			Astring[i].set("");
		}
	}
	
	//初始化字体
	
	public void add_num(int _num){
		
		add += _num;
		
	}	
	public void add_num(int _num,int _step){
		
		add += _num;
		step = _step;
		
	}
	
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(add>0&&(System.currentTimeMillis()-start)>30){
			start=System.currentTimeMillis();
			value+=step;
			add-=step;
			update_num(value);
		}
	}
	
}
