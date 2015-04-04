package game.ui;

import game.sprite.BtnSprite;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleSound;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;

import danji.doudizhu.game.v1.R;
import danji.doudizhu.game.v1.mainActivity;


public class MenuUI extends AngleUI{
	AngleSpriteLayout btn_layout;
	BtnSprite PlayBtn,exitBtn,musicBtn;
	AngleSurfaceView sview;
	float bili_2;
	public AngleSound bgsound;
	int musicflag=1;//背景音乐开关1开，0关
	public MenuUI(AngleActivity activity) {
		// TODO 自动生成的构造函数存根
		super(activity);
		int width = mActivity.mGLSurfaceView.roWidth;
		int height = mActivity.mGLSurfaceView.roHeight;
		 sview = mActivity.mGLSurfaceView;
		 bili_2 = (float)width/(float)512;
		addObject(new AngleSprite(width/2, height/2, new AngleSpriteLayout(sview, width, height, R.drawable.menubg, 0, 0, 512, 344)));
		btn_layout = new AngleSpriteLayout(sview, (int)(174*bili_2),  (int)(52*bili_2), R.drawable.newbtn, 0, 0, 174, 52,6,2);
		
		PlayBtn=new BtnSprite(btn_layout, new AngleVector(width-(int)(174*bili_2*0.7), height/2-(int)(bili_2*62)), 2);
		exitBtn=new BtnSprite(btn_layout, new AngleVector(width-(int)(174*bili_2*0.7), height/2), 0);
		musicBtn =new BtnSprite(btn_layout, new AngleVector(width-(int)(174*bili_2*0.7), height/2+(int)(bili_2*62)), 4);
		addObject(PlayBtn);
		addObject(exitBtn);
		addObject(musicBtn);
		bgsound= new AngleSound(mActivity,R.raw.menusound);
	
		//addObject(MoreBtn);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
	
		float eX = event.getX();
		float eY = event.getY();
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			eX = event.getX();
			eY = event.getY();
			if(PlayBtn.test(eX, eY)){
				PlayBtn.setframeid(3);
			}
			if(exitBtn.test(eX, eY)){
				exitBtn.setframeid(1);
			}
			break;
		case MotionEvent.ACTION_UP:
			eX = event.getX();
			eY = event.getY();
			if(PlayBtn.test(eX, eY)){
				PlayBtn.setframeid(2);
				((mainActivity)mActivity).setUI(((mainActivity)mActivity).gameui);
				
			 }
			if(exitBtn.test(eX, eY)){
				PlayBtn.setframeid(0);
				((mainActivity)mActivity).ExitCheck();
			 }
			if(musicBtn.test(eX, eY)){
				if(musicflag==1){
					musicflag=0;
					musicBtn.setframeid(5);
					bgsound.pause();
				}else{
					musicflag=1;
					musicBtn.setframeid(4);
					bgsound.play(0.5f, true);
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			eX = event.getX();
			eY = event.getY();
			if(PlayBtn.test(eX, eY)){
				PlayBtn.setframeid(3);
			}else{
				PlayBtn.setframeid(2);
			}
			if(exitBtn.test(eX, eY)){
				exitBtn.setframeid(1);
			}else{
				exitBtn.setframeid(0);
			}
			break;
			
		}
		return false;
	}

	@Override
	public void onActivate() {
		// TODO 自动生成的方法存根
		super.onActivate();
		bgsound.play(0.5f, true);
	}

	@Override
	public void onDeactivate() {
		// TODO 自动生成的方法存根
		super.onDeactivate();
		bgsound.pause();
		
	}

	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		
	}
/*	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.v("log","key="+keyCode);
		// TODO 自动生成的方法存根
		if(keyCode==KeyEvent.KEYCODE_BACK){
			ExitCheck();
			return false;
		}
		
		if(keyCode==KeyEvent.KEYCODE_HOME){
			ExitCheck();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}*/
	
	//游戏说明
	private  void showmsg() {
		  
		  AlertDialog.Builder builder = new Builder(mActivity);
		  builder.setTitle("规则说明");
		  builder.setMessage("游戏介绍:\n");
		 
		  builder.setPositiveButton("确定", new OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
		 });
			  
		  builder.create().show();
	}
}
