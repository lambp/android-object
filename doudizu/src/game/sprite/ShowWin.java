package game.sprite;

import com.android.angle.AngleFont;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;
import com.android.angle.AngleVector;
//显示结果
public class ShowWin extends AnglePhysicsEngine{
	AngleSprite bg;
	BtnSprite Okbtn,Exitbtn;
	AngleString score_1,score_2,score_3;
	float mApha;
	boolean isshow;
	public ShowWin(AngleSpriteLayout _bg,AngleSpriteLayout _btns,AngleVector WH,AngleFont font) {
		super(20);
		bg = new AngleSprite((int)(WH.mX/2),(int)(WH.mY/2),_bg);
		
		Okbtn = new BtnSprite(_btns, new AngleVector((int)(WH.mX/2)+_btns.roWidth*2/3,(int)(WH.mY/2)+_bg.roHeight/2-_btns.roHeight/2), 2);
		Exitbtn = new BtnSprite(_btns, new AngleVector((int)(WH.mX/2)-_btns.roWidth*2/3,(int)(WH.mY/2)+_bg.roHeight/2-_btns.roHeight/2), 4);
		score_1 = new AngleString(font, "-100", (int)((WH.mX/2)+(int)(WH.mX/8)),(int)(WH.mY/2),AngleString.aCenter);
		score_2 = new AngleString(font, "-100", (int)((WH.mX/2)+(int)(WH.mX/8)),(int)((WH.mY/2)+WH.mY/13),AngleString.aCenter);
		score_3 = new AngleString(font, "-100", (int)((WH.mX/2)+(int)(WH.mX/8)),(int)((WH.mY/2)+2*(WH.mY/13)),AngleString.aCenter);
		
		addObject(bg);
		addObject(Okbtn);
		addObject(Exitbtn);
		addObject(score_1);
		addObject(score_2);
		addObject(score_3);
		mApha = 0;
		score_1.mAlpha =mApha;
		score_2.mAlpha=mApha;
		score_3.mAlpha =mApha;
		Okbtn.mAlpha  = mApha;
		bg.mAlpha = mApha;
		Exitbtn.mAlpha=mApha;
		isshow = false;
	}
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(isshow){
			if(mApha<1){
				mApha += secondsElapsed;
				update();
			}
		}else{
			if(mApha>0){
				mApha -= secondsElapsed;
				update();
			}
		}
	}
	void update(){
		score_1.mAlpha =mApha;
		score_2.mAlpha=mApha;
		score_3.mAlpha =mApha;
		Okbtn.mAlpha  = mApha;
		bg.mAlpha = mApha;
		Exitbtn.mAlpha=mApha;
	}
	
	public void show(boolean show,int[] scores){
		isshow=show;
		score_1.set(""+scores[0]);
		score_2.set(""+scores[1]);
		score_3.set(""+scores[2]);
		
	}
	public int test(float ex,float ey){
		int res =0;
		if(isshow&&mApha>0.9){
			if(Okbtn.test(ex, ey)){
				res = 1;
			}
			if(Exitbtn.test(ex, ey)){
				res = 2;
			}
		}
		return res;
	}
	
}
