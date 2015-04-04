package game.ui;

import game.data.tools;
import game.sprite.BtnSprite;
import game.sprite.openBtn;
import game.sprite.setView;

import java.util.HashMap;
import java.util.Map;

import planegame.ruichuang.game.v1.Planegame;
import planegame.ruichuang.game.v1.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleObject;
import com.android.angle.AngleSound;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleUI;


public class MenuUI extends AngleUI{
	AngleObject Menu_list ;
	BtnSprite PlayBtn,SetBtn,Smbtn,morebtn;
	setView setview;
	Map<String,AngleSpriteLayout> setLayouts;
	private boolean isSet=false;	//是否处于设置
	openBtn soundBtn;      			//背景音乐开关
	public AngleSound bgsound;
	public MenuUI(AngleActivity activity) {
		// TODO 自动生成的构造函数存根
		super(activity);
		int width = mActivity.mGLSurfaceView.roWidth;
		int height = mActivity.mGLSurfaceView.roHeight;
		//设置背景
		 if(((Planegame)mActivity).isCn){
		addObject(new AngleSprite(width/2,height/2, 
				new AngleSpriteLayout(mActivity.mGLSurfaceView,width , height, R.drawable.bgmenu,0,0,344,512)));
		PlayBtn = new BtnSprite(width/2, height/2-20, new AngleSpriteLayout(mActivity.mGLSurfaceView,
					136*width/344+1,50*width/344+1,R.drawable.set,5,431,136,50));
		SetBtn = new BtnSprite(width/2, height/2+40, new AngleSpriteLayout(mActivity.mGLSurfaceView,
				136*width/344+1,50*width/344+1,R.drawable.set,95,287,136,50));
		 }else{
			 addObject(new AngleSprite(width/2,height/2, 
						new AngleSpriteLayout(mActivity.mGLSurfaceView,width , height, R.drawable.bgmenu_en,0,0,344,512)));
				PlayBtn = new BtnSprite(width/2, height/2-20, new AngleSpriteLayout(mActivity.mGLSurfaceView,
							136*width/344+1,50*width/344+1,R.drawable.set_en,5,431,136,50));
				SetBtn = new BtnSprite(width/2, height/2+40, new AngleSpriteLayout(mActivity.mGLSurfaceView,
						136*width/344+1,50*width/344+1,R.drawable.set_en,95,287,136,50)); 
		 }
		addObject(PlayBtn);
		addObject(SetBtn);
		AngleSpriteLayout btnss = new AngleSpriteLayout(mActivity.mGLSurfaceView,
				78*width/344+1,45*width/344+1,R.drawable.winbg,280,0,78,45,2,2);
		Smbtn = new BtnSprite(78*width/344+1, height-78*width/344+1,btnss,0 );
		addObject(Smbtn);
		morebtn = new BtnSprite(78*width/344+btnss.roWidth, height-78*width/344+1,btnss,1 );
		addObject(morebtn);
		if(((Planegame)mActivity).isCn){
		setLayouts = new HashMap<String,AngleSpriteLayout>();
		setLayouts.put("bg", new AngleSpriteLayout(mActivity.mGLSurfaceView,248*width/344, 229*width/344,
				R.drawable.set,0,0,248,229));
		setLayouts.put("ok", new AngleSpriteLayout(mActivity.mGLSurfaceView,70*width/344, 38*width/344,
				R.drawable.set,0,306,70,38));
		setLayouts.put("cancle", new AngleSpriteLayout(mActivity.mGLSurfaceView,70*width/344, 38*width/344,
				R.drawable.set,0,263,70,38));
		setLayouts.put("move", new AngleSpriteLayout(mActivity.mGLSurfaceView,29*width/344, 30*width/344,
				R.drawable.set,12,231,29,30,5,5));
		}else{
			setLayouts = new HashMap<String,AngleSpriteLayout>();
			setLayouts.put("bg", new AngleSpriteLayout(mActivity.mGLSurfaceView,248*width/344, 229*width/344,
					R.drawable.set_en,0,0,248,229));
			setLayouts.put("ok", new AngleSpriteLayout(mActivity.mGLSurfaceView,70*width/344, 38*width/344,
					R.drawable.set_en,0,306,70,38));
			setLayouts.put("cancle", new AngleSpriteLayout(mActivity.mGLSurfaceView,70*width/344, 38*width/344,
					R.drawable.set_en,0,263,70,38));
			setLayouts.put("move", new AngleSpriteLayout(mActivity.mGLSurfaceView,29*width/344, 30*width/344,
					R.drawable.set_en,12,231,29,30,5,5));
		}
		
		String playset = tools.getstrformmem(tools.jdconif,mActivity);
		if(playset.equals("null")){
			playset =  "1,3,2,3";
			tools.savetomem(tools.jdconif, "1,3,2,3", mActivity);
		}
		setview = new setView(setLayouts,width/2,height/2,0,playset);
		addObject(setview);
		
		bgsound = new AngleSound(mActivity, R.raw.bgsound);
		//bgsound.play((float)0.3, true);
		String soundid = tools.getstrformmem(tools.bgsound,mActivity);
		if(soundid.equals("null")){
			soundid =  "0";
			tools.savetomem(tools.bgsound, "0", mActivity);
		}
		if(soundid.equals("0")) bgsound.play((float)0.5, true);
		
		//背景音乐开关
		soundBtn = new openBtn(width-60*width/344,height-50,new AngleSpriteLayout(mActivity.mGLSurfaceView,110*width/344,45*width/344,R.drawable.winbg,0,371,109,45,2,2),Integer.parseInt(soundid));
		addObject(soundBtn);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			float eX = event.getX();
			float eY = event.getY();
			
			if(isSet){
				int res = setview.checkClk(eX, eY);
				if(res>1){
					isSet = false;
					if(2==res){
						tools.savetomem(tools.jdconif, setview.getSetstr(), mActivity);	
					}
				}
			}
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			float eX = event.getX();
			float eY = event.getY();
			if(SetBtn.test(eX, eY)&&!isSet){
				isSet = true;
				setview.setShow();
			}
			if(PlayBtn.test(eX, eY)&&!isSet){
				mActivity.setUI(new GameUI(mActivity));
			}
			if(Smbtn.test(eX, eY)){
				showmsg();
			}
			int resid = soundBtn.test(eX, eY);
			if(0==resid){
				bgsound.resume();
				tools.savetomem(tools.bgsound, "0", mActivity);
			}else if(1==resid){
				bgsound.pause();
				tools.savetomem(tools.bgsound, "1", mActivity);
			}
			
			if(morebtn.test(eX, eY)){
				//((Planegame)mActivity).mobAd();
			}
		}
		return false;
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
/*
	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		bgsound.stop();
	}*/

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
	//退出确认
		private  void ExitCheck() {
			  
			  AlertDialog.Builder builder = new Builder(mActivity);
			  builder.setMessage("确定要经典飞行棋游戏退出吗？");
			  builder.setTitle("提示");
			  builder.setPositiveButton("是", new OnClickListener() {
				   @Override
				   public void onClick(DialogInterface dialog, int which) {
					   dialog.dismiss();
					   handler.sendEmptyMessage(1);
				   }
				  });
				  
				  builder.setNegativeButton("否", new OnClickListener() {
					   @Override
					   public void onClick(DialogInterface dialog, int which) {
					    dialog.dismiss();
					    handler.sendEmptyMessage(2);
					   }
				  });
			  builder.create().show();
		}
		Handler handler = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch(msg.what){
			case 1:
				mActivity.finish();
				//bgmusic.stop();
				break;
			case 2:
				break;
			}
			super.handleMessage(msg);
		}
		
		};
	//游戏说明
	private  void showmsg() {
		  
		  AlertDialog.Builder builder = new Builder(mActivity);
		  builder.setMessage("经典飞行棋玩法和传统的飞行棋一致，游戏规则大家一定很熟悉了.\n本游戏特点：\n 1.支援多人对战（1-4人）;\n 2.可与1-3个AI对战; \n 3.传统介面，经典玩法.");
		  builder.setTitle("游戏说明");
		  builder.setPositiveButton("确定", new OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
		 });
			  
		  builder.create().show();
	}
}
