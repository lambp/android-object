package game.ui;

import game.data.map;
import game.sprite.Posstars;
import game.sprite.buttonS;
import game.sprite.combtn;
import game.sprite.star;

import java.util.Random;

import org.kaka.popstar2015.PopstartActivity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleMusic;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;
import org.kaka.popstar2015.R;

public class MenuUI extends AngleUI{
	public AngleSpriteLayout stars,bg,smallstars,btnbg,btntitle;
	AngleSurfaceView sview;
	int width,height;
	combtn play,exit,help;
	AnglePhysicsEngine sprites;
	AnglePhysicsEngine movestars;
	float bili;
	public AngleMusic bgmusic;
	private int[][] maps = {			
			{1,1,1,1,1,1,1,1,1,1},	
			{1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1},	
			{1,1,1,1,1,1,1,1,1,1},	
			{1,1,1,1,1,1,1,1,1,1},
			{1,1,1,0,1,1,1,0,1,1},
			{0,1,0,0,0,0,1,0,1,0},
			{0,1,0,0,0,0,1,0,1,0},					
			{0,0,0,0,0,0,0,0,0,0},	
			{0,0,0,0,0,0,0,0,0,0},	
				
	};
	public MenuUI(AngleActivity activity) {
		super(activity);
		sview = mActivity.mGLSurfaceView;
		width = sview.roWidth;
		height = sview.roHeight;
		bili = (float)(width)/(float)688;		
		loadImg();	
	}
	
	private void loadImg(){
		//背景
		 bg = new AngleSpriteLayout(sview, width, height, R.drawable.menubg, 0, 0, 688,1024);
		 addObject(new AngleSprite(width/2, height/2, bg));
		 
		 
		 movestars = new AnglePhysicsEngine(351);
		 addObject(movestars);
		 
		 
		 sprites = new AnglePhysicsEngine(300);
		 addObject(sprites);
		 
		 //添加logo
		movestars.addObject(new AngleSprite(width/2,(int)(430*bili)/2+10,new AngleSpriteLayout(sview,width,(int)(430*bili),R.drawable.bgitems,0,150,688,430)));
		 
		 //加载星星
		 float pw = (float)width/(float)10;
		 stars = new AngleSpriteLayout(sview, (int)pw,(int)pw, R.drawable.bgitems, 0, 0, 72, 72, 10, 5);
		 //初始化坐标
		 AngleVector[][] pos = map.getmap(width, height); 
		 
		 Random rand = new Random();
		 for(int x=0;x<10;x++){
			for(int y=0;y<10;y++){
				if(maps[x][y]>0){
				int rd = rand.nextInt(100)%5;
				   movestars.addObject(new star(stars,pos[x][y],x,y,rd));
				}
			}
		 }
		 
		//小星星
		 smallstars = new AngleSpriteLayout(sview, 60, 60, R.drawable.bgitems, 0, 72, 60, 60, 5, 5);
		
		 btnbg = new AngleSpriteLayout(sview,(int)( 440*bili), (int)(116*bili), R.drawable.bgitems, 0, 656, 440, 116);
		 
		 btntitle = new AngleSpriteLayout(sview, (int)( 293*bili), (int)(105*bili), R.drawable.titletext, 0, 0, 293, 105,6,2);
		 
		play = new combtn(new AngleVector(-400,height/2-btnbg.roHeight),btnbg,btntitle,((PopstartActivity)mActivity).lagId );
		help = new combtn(new AngleVector(-400,height/2),btnbg,btntitle,((PopstartActivity)mActivity).lagId+2 );
		exit = new combtn(new AngleVector(-400,height/2+btnbg.roHeight),btnbg,btntitle,((PopstartActivity)mActivity).lagId+4 );
			
	
		addObject(play);
		addObject(exit);
		addObject(help);
		
		play.Moveto(new AngleVector(width/2, height/2-btnbg.roHeight), 56);
		help.Moveto(new AngleVector(width/2, height/2), 48);
		exit.Moveto(new AngleVector(width/2, height/2+btnbg.roHeight), 32);
	
		
		bgmusic = new AngleMusic(mActivity, R.raw.bgms);
		bgmusic.play((float)0.5,true);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
			
			if(play.test(event.getX(), event.getY())){
				play.setAp(0.5f);
			}
			
			if(help.test(event.getX(), event.getY())){
				help.setAp(0.5f);
			}
			
			if(exit.test(event.getX(), event.getY())){
				exit.setAp(0.5f);
			}
			
			break;
			case MotionEvent.ACTION_UP:

				if(play.test(event.getX(), event.getY())){
					((PopstartActivity)mActivity).gameui.resetgame();
					((PopstartActivity)mActivity).setUI(((PopstartActivity)mActivity).gameui);
				}
				
				if(help.test(event.getX(), event.getY())){
					//showmsg();	
					((PopstartActivity)mActivity).setUI(((PopstartActivity)mActivity).gameui);
				}
				
				if(exit.test(event.getX(), event.getY())){
					mActivity.finish();				
				}
				
				play.setAp(1f);
				help.setAp(1f);
				exit.setAp(1f);
				
				break;
		}
		return super.onTouchEvent(event);
	}
	
	//游戏说明
		private  void showmsg() {
			  
			  AlertDialog.Builder builder = new Builder(mActivity);
			  builder.setMessage("消灭星星是一个很锻炼脑力的游戏，玩法介绍：\n 1.只需点击链接相同颜色的两个或两个以上的星级;\n 2.尽量挖掘更多的明星一起，以获得更多的积分。有没有时间，但你需要达到目标分数，向前推进到一个新的水平;\n 3.没有任何时间的限制，可以尽情的思考，选择最优的消除方法");
			  builder.setTitle("游戏说明");
			  builder.setPositiveButton("确定", new OnClickListener() {
				   @Override
				   public void onClick(DialogInterface dialog, int which) {
					   dialog.dismiss();
				   }
			  });
				  
			  builder.create().show();
		}
	
	double start;
	float starpos=height-height/3; 
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if((System.currentTimeMillis()-start)>300){
			
			start = System.currentTimeMillis();
			
			sprites.addObject(new Posstars(22,smallstars,new AngleVector(width/2, starpos))); 
			
			starpos-=20f;
			if(starpos<10){
				starpos=height-height/3;
			}		
			
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		if(keyCode==KeyEvent.KEYCODE_BACK){
			//mActivity.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
