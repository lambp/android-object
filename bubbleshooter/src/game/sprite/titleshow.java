package game.sprite;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class titleshow extends AngleSprite{
	AngleSprite sp;
	int langs;
	float maxy,miny;
	public titleshow(AngleSpriteLayout _layout,AngleVector pos,int lang) {
		super(_layout);
		langs = lang;
		this.mPosition.set(pos);
		this.setFrame(langs);
		maxy=pos.mY+20;
		miny=pos.mY-20;
	}
	boolean down=true;
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(down){
			this.mPosition.mY+=0.1;
			if(this.mPosition.mY>maxy) down=false;
		}else{
			this.mPosition.mY-=0.1;
			if(this.mPosition.mY<miny) down=true;
		}
	}
	

}
