package game.ui;

import game.data.maplist;
import game.data.maplist2;
import game.data.tools;
import game.sprite.LockSprite;
import game.sprite.titleshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import bubble.shooter.rcgame.v1.R;
import bubble.shooter.rcgame.v1.mainActivity;

import com.android.angle.AngleActivity;
import com.android.angle.AngleFont;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;

public class mapUI  extends AngleUI{
	int type=1;
	private ArrayList<LockSprite> locklist;
	public  AngleSpriteLayout lock,lockbg,titles;
	private Map<String,AngleSpriteLayout> unlockImg;
	AnglePhysicsEngine mapEng;
	int Width,Height;
	float move=0,moveStep;
	int move_to=0,pages = 5,MaxPage=1;
	//关卡
	int page=1;
	int downX;
	int upX;
	int moveX;
	int nowPage=1;
	public int maxlock=10;
	boolean isMove=false;
	//锁的地图
	private int[][] mapVal = new int[(maplist.list.length+maplist2.list2.length)/4][4];
	//private ButtonSp backbtn;
	
	
	public mapUI(AngleActivity activity) {
		super(activity);
		//比例
		Width = mActivity.mGLSurfaceView.roWidth;
		Height = mActivity.mGLSurfaceView.roHeight;
		float bili = (float)Width/(float)344;
		locklist = new ArrayList<LockSprite>();
		unlockImg = new HashMap<String,AngleSpriteLayout>();
		
		moveStep=Width/16;
		
		titles = new AngleSpriteLayout(mActivity.mGLSurfaceView,(int)(bili*197) , (int)(bili*60), R.drawable.titles,0,49,197,60,14,2); 
		
		int lang = ((mainActivity) mActivity).lagId;
		Log.v("log", "l="+lang);
		//背景
		addObject(new AngleSprite(Width/2,Height/2, 
						new AngleSpriteLayout(mActivity.mGLSurfaceView,Width , Height, R.drawable.menubg,30,0,344,512)));
		addObject(new titleshow(titles,new AngleVector(Width/2, titles.roHeight/2+20),12+lang));
		///锁的大小				
		int lWidth = (int)(64*bili);
		int lHeight = (int)(64*bili);
		lock = new AngleSpriteLayout(mActivity.mGLSurfaceView, lWidth,lHeight,R.drawable.lock,0,0,64,64);
		lockbg = new AngleSpriteLayout(mActivity.mGLSurfaceView, lWidth,lHeight,R.drawable.lock_bg,0,0,64,64);
		
		mapEng = new AnglePhysicsEngine(1800);		
		addObject(mapEng);

		//backbtn = new ButtonSp( new AngleSpriteLayout(mActivity.mGLSurfaceView, (int)(52*bili), (int)(30*bili), R.drawable.btns,0, 53, 52, 30),(int)(52*bl)/2,(int)(30*bl/2) );
		//addObject(backbtn);
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			downX=(int) event.getX();
			/*if(backbtn.test(event.getX(),event.getY())){
				((mainActivity) mActivity).setUI(((mainActivity) mActivity).menuui);
			}*/
			
			break;
		case MotionEvent.ACTION_UP:
			upX=(int) event.getX();
			if(downX>0)	moveX = upX-downX;
			if(Math.abs(moveX)>50 && !isMove){
				Log.v("log","move-");
				if(moveX>0){//向右移动
					if(nowPage>1 && !isMove){
						isMove=true;
						move = Width;
						move_to =1;
						nowPage--;
					}
				}else{//向左
					if(nowPage<(MaxPage) &&!isMove){
						Log.v("log","Left");
						isMove=true;
						move = Width;
						move_to =-1;
						nowPage++;
					}
				}
			}else{
				for(int i=0;i<locklist.size();i++){
					if(downX>0&&!locklist.get(i).isLock && locklist.get(i).test(event.getX(), event.getY())){
						((mainActivity) mActivity).setUI(new GameUI(mActivity,locklist.get(i).step-1));
					}
				}
			}
			downX=0;
			upX=0;
			break;
	
		}
		
		return super.onTouchEvent(event);
	}
	//初始化地图
		public void initmap(float bl){
			isMove = false;
			String maxmap = "null";
			maxmap = tools.getstrformmem(tools.stepmun, mActivity);
			if("null".equals(maxmap)) { 
				tools.savetomem(tools.stepmun,"1",mActivity);
				maxlock = 1;
			}else{
				maxlock = Integer.parseInt(maxmap);
			}
			//maxlock = maplist.list.length+maplist2.list2.length;
			for(int i=0;i<mapVal.length;i++){
				for(int j=0;j<mapVal[0].length;j++){
					if(maxlock>0){
						 mapVal[i][j]=1;
						 maxlock--;
					}else{
						 mapVal[i][j]=0;
					}
				}
			}
			
			//锁的大小				
			int lWidth = (int)(70/bl);
			int lHeight = (int)(70/bl);
			//x方向的间隔
			int startX = ((Width-4*lWidth)/4);
			//y方向的间隔
			int startY = (Height-pages*lHeight-(int)(64/bl)-60)/pages;
			
			for(int i=0;i<locklist.size();i++){
				locklist.get(i).mDie=true;
			}
			float bili = (float)Width/(float)344;
			AngleSpriteLayout	nums =  new AngleSpriteLayout(mActivity.mGLSurfaceView,(int)(32*bili),(int)(48*bili),R.drawable.bobom,215, 263, 32, 48, 10, 9);		
			locklist = new ArrayList<LockSprite>();
			//AngleFont font_18 = new AngleFont(mActivity.mGLSurfaceView, Width/7, Typeface.createFromAsset(mActivity.getAssets(),"NINA.TTF"), 222, 0, 0, 251, 237, 87, 255);
			//addObject(new AngleString(font_18, "Choose level",Width/2, Width/7, AngleString.aCenter));	
			MaxPage = mapVal.length/pages +1;
			int step=1;
			for(int i=0;i<mapVal.length;i++){
				page =(int)i/pages;
				for(int j=0;j<mapVal[0].length;j++){
					int x= (startX+lWidth)/2+j*(lWidth+startX)+(page)*Width;
					int y=(int)(64/bl)+startY+lHeight/2+(i%pages)*(lHeight+startY);
					if(mapVal[i][j]>0){
						locklist.add(new LockSprite(lockbg, step, x, y,false,nums));
					}else{
						locklist.add(new LockSprite(lock, step, x, y,true,nums));
					}
					step++;
				}
			}
			for(int i=0;i<locklist.size();i++){
				mapEng.addObject(locklist.get(i));
			}
					
		}
	@Override
	public void step(float secondsElapsed) {
		
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(move>0&&isMove){
			moveLock();
			move -= moveStep;
		}else{
			isMove=false;
		}
	}
	//移动
	private void moveLock(){
		for(int i =0;i<locklist.size();i++){
			locklist.get(i).setXY(moveStep*move_to);
		}
	}


	@Override
	public void onActivate() {
		// TODO 自动生成的方法存根
		super.onActivate();
		float bl = 344/mActivity.mGLSurfaceView.roWidth; 
		initmap(bl);
		moveX =0;
		downX = 0;
		upX =0;
		nowPage=1;
	}

	@Override
	public void onDeactivate() {
		// TODO 自动生成的方法存根
		super.onDeactivate();
	}

}
