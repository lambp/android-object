package game.sprite;

import com.android.angle.AngleFont;
import com.android.angle.AngleString;

public class textFont extends AngleString{
	int value;
	int add;
	double start;
	public textFont(AngleFont font, int val, int x, int y, int alignment) {
		super(font, ""+val, x, y, alignment);
		// TODO 自动生成的构造函数存根
		value=val;
		add=0;
	}
	
	
	public void Addvalue(int _add){
		add=_add;
	}
	
	
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(add>0&&(System.currentTimeMillis()-start)>50){
			start=System.currentTimeMillis();
			value+=5;
			add-=5;
			this.set(""+value);
		}
	}
	
}
