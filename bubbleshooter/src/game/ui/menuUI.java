package game.ui;

import game.sprite.titleshow;
import android.view.MotionEvent;
import bubble.shooter.rcgame.v1.R;
import bubble.shooter.rcgame.v1.mainActivity;

import com.android.angle.AngleActivity;
import com.android.angle.AngleSound;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;
import com.android.angle.Buttons;
import com.android.angle.openBtn;

public class menuUI  extends AngleUI{
	
	float width,height,bili;
	AngleSurfaceView sview;
	Buttons playbtn,exitbtn,morebtn;
	AngleSpriteLayout btnbgly,btntitle,titlelay,soundimg;
    titleshow title;
	openBtn bgmusicbtn;
	public AngleSound bgsound;
	int lang=0;
	public menuUI(AngleActivity activity) {
		super(activity);
		
		 lang = ((mainActivity) mActivity).lagId;
		 width = mActivity.mGLSurfaceView.roWidth;
		 height = mActivity.mGLSurfaceView.roHeight;
		 sview = mActivity.mGLSurfaceView;
		 bili = (float)width/(float)344;
		 btnbgly = new AngleSpriteLayout(sview, (int)(266*bili), (int)(86*bili), R.drawable.menubtns, 0, 60, 266, 86,2,1);
		 btntitle = new AngleSpriteLayout(sview, (int)(172*bili), (int)(49*bili), R.drawable.menubtns, 0, 232, 172, 49,4,2);
		 titlelay = new AngleSpriteLayout(sview, (int)(294*bili), (int)(60*bili), R.drawable.menubtns, 0, 0, 294, 60);
		 playbtn = new Buttons(btnbgly,btntitle,new AngleVector(width/2, height/3+btnbgly.roHeight/2),0);
		 exitbtn = new Buttons(btnbgly,btntitle,new AngleVector(width/2, height/2+btnbgly.roHeight),2);
		 soundimg = new AngleSpriteLayout(sview, (int)(78*bili), (int)(78*bili), R.drawable.menubtns, 0, 322, 78, 78,2,2);
		addObject(new AngleSprite((int)width/2,(int)height/2,new AngleSpriteLayout(sview, (int)width, (int)height, R.drawable.menubg, 0, 0, 344, 512)));
		title = new titleshow(titlelay,new AngleVector(width/2, height/6),0);
		
		addObject(title);
		addObject(playbtn);
		addObject(exitbtn);
		bgmusicbtn = new openBtn((int)(width-soundimg.roWidth/2), (int)(height-1.2*soundimg.roHeight), soundimg,0);
		addObject(bgmusicbtn);
		bgsound = new AngleSound(mActivity,R.raw.play);
		bgsound.play(0.5f, true);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		float x=event.getX();
		float y=event.getY();
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			 x=event.getX();
			 y=event.getY();
			 playbtn.mouseDown(x, y);
			 exitbtn.mouseDown(x, y);
			 int res = bgmusicbtn.test(x, y);
			 if(res>=0){
				 if(res==0){
					 bgsound.play(0.5f, true);
				 }else{
					 bgsound.pause();
				 }
			 }
			break;
		case MotionEvent.ACTION_MOVE:
			 x=event.getX();
			 y=event.getY();
			 playbtn.mouseMove(x, y);
			 exitbtn.mouseMove(x, y);
			 break;
			case MotionEvent.ACTION_UP:
				 x=event.getX();
				 y=event.getY();
				if(playbtn.mouseUp(x, y)){
					((mainActivity)mActivity).setUI(((mainActivity)mActivity).mapui);
				}
				if(exitbtn.mouseUp(x, y)){
					((mainActivity)mActivity).ExitCheck();
				}
			break;
		}
		return super.onTouchEvent(event);
	}
	

	@Override
	public void onActivate() {
		// TODO 自动生成的方法存根
		super.onActivate();

	}

	@Override
	public void onDeactivate() {
		// TODO 自动生成的方法存根
		super.onDeactivate();
	}

}
