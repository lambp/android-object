package game.ui;

import game.data.mapData;
import game.data.mapPos;
import game.data.tools;
import game.data.winScore;
import game.sprite.BtnSprite;
import game.sprite.openBtn;
import game.sprite.player;
import game.sprite.shaizi;
import game.sprite.winView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import planegame.ruichuang.game.v1.Planegame;
import planegame.ruichuang.game.v1.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleFont;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSound;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;

public class GameUI extends AngleUI{
	public int sWidth,sHeight;
	private ArrayList<mapPos> mapList ; 												//地图情况
	public AngleSpriteLayout gamebg_layout,plane_1,plane_2,plane_3,plane_4,shaizilayout ;//图片资源
	AngleSprite gamebg,szshow;															//背景、色子
	BtnSprite Playszbtn,showWhoPlay;																//色子按钮
	AnglePhysicsEngine Allplanes;                                                       //所有棋子
	int pWidth;																			//棋子尺寸
	private player play_1,play_2,play_3,play_4;											//各个玩家
	private ArrayList<player> players; 												    //所有玩家
	private int Maxplayer,Playing_id;													//玩家总数,当前玩家
	private shaizi szmovie;																//色子动画
	private boolean AIPlaying=false,Waiting=false,PersonPlaying=false,Wait_select=false;
	double StartTime;   //计时器
	int startStep = 0;  //飞行起点
	int endstep = 0;    //飞行终点
	
	int personStep = 0;   //玩家操作-过程
	int Aistep=0;  		  //Ai操作--过程
	int sleeptime=2;	  //中间停顿时间
	private boolean GetMoreRand=false;			//是否奖励丢色子机会
	int canStartnum=6,jampId_1=-2,jampId_2=-2;  //起飞值,第一个跳点,第2个跳点
	private int rand_num = 0,selectedID=-1;		//获取随机色子数,选择的棋子
	private boolean isGameOver;					//游戏是否结束
	winView winview;						    //战斗结果框
	private Map<String, winScore> Score;
	private boolean showWin=false;
	private AngleSound startSound,crashSound,arriveSound,landoffSound,szSound,mvSound;
	private final int STARTS = 1,CRASHS=2,ARRIVES=3,LANDOFF=4,BGSOUND=5,SZSOUND=6,MOVESOUND=7;
	private boolean isbgSound=false;   //是否播放背景音乐
	
	openBtn soundBtn; 
	public GameUI(AngleActivity activity) {
		super(activity);
		initWidth();
		mapList = mapData.initMap(sWidth, sHeight);
		Score = new HashMap<String,winScore>();
		players = new ArrayList<player>();
		initPic();
		isGameOver=false;
	}
	//计算尺寸
	private void initWidth(){
		sWidth = mActivity.mGLSurfaceView.roWidth;
		sHeight= mActivity.mGLSurfaceView.roHeight;
		pWidth = (int)(45*sWidth/512)+2;
	}
	
	//加载图片
	private void initPic(){
	  addObject(new AngleSprite(sWidth/2,sHeight/2,new AngleSpriteLayout(mActivity.mGLSurfaceView, sWidth, sHeight,planegame.ruichuang.game.v1.R.drawable.bgimg, 0, 0, 344, 512)));
	  
	  gamebg_layout = new AngleSpriteLayout(mActivity.mGLSurfaceView, sWidth, sWidth,planegame.ruichuang.game.v1.R.drawable.gamebg, 0, 0, 512, 512);
	  gamebg = new AngleSprite(sWidth/2,sHeight/2,gamebg_layout);
	  addObject(gamebg);
	  
	  Allplanes  = new AnglePhysicsEngine(16);
	  addObject(Allplanes);
	 
	  plane_1  = new AngleSpriteLayout(mActivity.mGLSurfaceView, pWidth, pWidth,planegame.ruichuang.game.v1.R.drawable.planes, 0, 0, 64, 64,5,2);
	  plane_2  = new AngleSpriteLayout(mActivity.mGLSurfaceView, pWidth, pWidth,planegame.ruichuang.game.v1.R.drawable.planes, 384, 0, 64, 64,5,2);
	  plane_3  = new AngleSpriteLayout(mActivity.mGLSurfaceView, pWidth, pWidth,planegame.ruichuang.game.v1.R.drawable.planes, 256, 0, 64, 64,5,2);
	  plane_4  = new AngleSpriteLayout(mActivity.mGLSurfaceView, pWidth, pWidth,planegame.ruichuang.game.v1.R.drawable.planes, 128, 0, 64, 64,5,2);
	  
	  initPlayer();
	  
	  shaizilayout = new AngleSpriteLayout(mActivity.mGLSurfaceView, 64*344/sWidth, 64*344/sWidth,planegame.ruichuang.game.v1.R.drawable.saizi, 0, 0, 64, 64, 10, 8); 
	  szmovie = new shaizi(shaizilayout);
	  //丢色子按钮
	  Playszbtn = new BtnSprite(sWidth/2,sHeight-(64*344/sWidth+1)/2-5, new AngleSpriteLayout(mActivity.mGLSurfaceView, (80*sWidth)/344, (80*sWidth)/344,planegame.ruichuang.game.v1.R.drawable.saizi, 0, 192, 64, 64,4,4));
	  showWhoPlay = new BtnSprite(sWidth/2,(sHeight-sWidth)/2-(64*344/sWidth+1)/2, new AngleSpriteLayout(mActivity.mGLSurfaceView, (64*sWidth)/344, (64*sWidth)/344,planegame.ruichuang.game.v1.R.drawable.saizi, 0, 257, 64, 64,4,4));
	  addObject(szmovie);
	  addObject(showWhoPlay);
	  addObject(Playszbtn);
	  
	  Playszbtn.mAlpha=0;
	  showWhoPlay.mAlpha=0;
	  //显示的色子
	  szshow = new AngleSprite(sWidth/2,sHeight/2,  new AngleSpriteLayout(mActivity.mGLSurfaceView, sWidth/5, sWidth/5,planegame.ruichuang.game.v1.R.drawable.saizi, 0, 128, 64, 64, 6, 6));
	  
	  Map<String,AngleSpriteLayout> setLayouts = new HashMap<String,AngleSpriteLayout>();
	  if(((Planegame)mActivity).isCn){
		setLayouts.put("bg", new AngleSpriteLayout(mActivity.mGLSurfaceView,248*sWidth/344, 260*sWidth/344,
				R.drawable.winbg,0,0,248,260));
		setLayouts.put("ok", new AngleSpriteLayout(mActivity.mGLSurfaceView,70*sWidth/344, 38*sWidth/344,
				R.drawable.winbg,0,306,70,38));
		setLayouts.put("cancle", new AngleSpriteLayout(mActivity.mGLSurfaceView,70*sWidth/344, 38*sWidth/344,
				R.drawable.winbg,0,263,70,38));
		setLayouts.put("move", new AngleSpriteLayout(mActivity.mGLSurfaceView,29*sWidth/344, 30*sWidth/344,
				R.drawable.winbg,12,231,29,30,5,5));
	  }else{
		  setLayouts.put("bg", new AngleSpriteLayout(mActivity.mGLSurfaceView,248*sWidth/344, 260*sWidth/344,
					R.drawable.winbg_en,0,0,248,260));
		  setLayouts.put("ok", new AngleSpriteLayout(mActivity.mGLSurfaceView,70*sWidth/344, 38*sWidth/344,
					R.drawable.winbg_en,0,306,70,38));
		  setLayouts.put("cancle", new AngleSpriteLayout(mActivity.mGLSurfaceView,70*sWidth/344, 38*sWidth/344,
					R.drawable.winbg_en,0,263,70,38));
		  setLayouts.put("move", new AngleSpriteLayout(mActivity.mGLSurfaceView,29*sWidth/344, 30*sWidth/344,
					R.drawable.winbg_en,12,231,29,30,5,5));
	  }
		
	  setLayouts.put("num", new AngleSpriteLayout(mActivity.mGLSurfaceView,20*sWidth/344, 30*sWidth/344,
				R.drawable.num,0,0,24,36,10,10));
		
		
		startSound=new AngleSound(mActivity, R.raw.start);
		crashSound=new AngleSound(mActivity, R.raw.crash);
		arriveSound=new AngleSound(mActivity, R.raw.arrive);
		landoffSound=new AngleSound(mActivity, R.raw.landoff);
		szSound = new AngleSound(mActivity, R.raw.shaizi);
		mvSound = new AngleSound(mActivity, R.raw.mj0);
		
		String soundid = tools.getstrformmem(tools.bgsound,mActivity);
		if(soundid.equals("null")){
			soundid =  "0";
			tools.savetomem(tools.bgsound, "0", mActivity);
		}
		if(soundid.equals("0")) isbgSound=true;
		//背景音乐开关
		if(((Planegame)mActivity).isCn){
			soundBtn = new openBtn(sWidth/2,sHeight-50,new AngleSpriteLayout(mActivity.mGLSurfaceView,52*sWidth/344,52*sWidth/344,R.drawable.winbg,165,429,52,52,2,2),Integer.parseInt(soundid));
		}else{
			soundBtn = new openBtn(sWidth/2,sHeight-50,new AngleSpriteLayout(mActivity.mGLSurfaceView,52*sWidth/344,52*sWidth/344,R.drawable.winbg_en,165,429,52,52,2,2),Integer.parseInt(soundid));	
		}
		addObject(soundBtn);
		
		Score.put("1",new winScore(0,0));
		Score.put("2",new winScore(0,0));
		Score.put("3",new winScore(0,0));
		Score.put("4",new winScore(0,0));
		AngleFont fntCafe=new AngleFont(mActivity.mGLSurfaceView, 25, Typeface.createFromAsset(mActivity.getAssets(),"cafe.ttf"),  222, 0, 0, 30, 200, 255, 255);
		winview = new winView(setLayouts, sWidth/2, sHeight/2, 0, Score,fntCafe);
		//addObject(new  AngleString(fntCafe,"1s00",100,200,AngleString.aCenter));
		addObject(winview);
	}
	private void playMuise(int id){
		if(isbgSound){
		switch(id){
		case STARTS:
			startSound.play((float) 0.3,false);
			break;
		case CRASHS:
			crashSound.play((float) 0.3,false);
			break;
		case ARRIVES:
			arriveSound.play((float) 0.3,false);
			break;
		case LANDOFF:
			landoffSound.play((float) 0.3,false);
			break;
		case BGSOUND:
			landoffSound.play((float) 0.3,false);
			break;
		case SZSOUND:
			szSound.play((float) 0.3,false);
			break;
		case MOVESOUND:
			mvSound.play((float) 0.3,false);
			break;
		}
		}
	}
	//初始化玩家
	private void initPlayer(){
		String playset = tools.getstrformmem(tools.jdconif,mActivity);
		if(playset.equals("null")){
			playset =  "1,3,2,3";
			tools.savetomem(tools.jdconif, "1,3,2,3", mActivity);
		}
		String[] playsets = playset.split(",");
		
		play_1 = new player(1,playsets[0].equals("2")?true:false,sWidth,sHeight,plane_1,playsets[0].equals("3")?false:true);
		play_2 = new player(2,playsets[1].equals("2")?true:false,sWidth,sHeight,plane_2,playsets[1].equals("3")?false:true);
		play_3 = new player(3,playsets[2].equals("2")?true:false,sWidth,sHeight,plane_3,playsets[2].equals("3")?false:true);
		play_4 = new player(4,playsets[3].equals("2")?true:false,sWidth,sHeight,plane_4,playsets[3].equals("3")?false:true);
		
		for(int i=0;i<4;i++){
			if(play_1.isPlaying)  Allplanes.addObject(play_1.getplane(i));
			if(play_2.isPlaying)  Allplanes.addObject(play_2.getplane(i));
			if(play_3.isPlaying)  Allplanes.addObject(play_3.getplane(i));
			if(play_4.isPlaying)  Allplanes.addObject(play_4.getplane(i));
		}
		 
		
		
	  	if(play_1.isPlaying)  players.add(play_1);
		if(play_2.isPlaying)  players.add(play_2);
		if(play_3.isPlaying)  players.add(play_3);
		if(play_4.isPlaying)  players.add(play_4);
		
		Maxplayer=players.size();
		Playing_id=0;
		
/*		for(int i=0;i<players.size();i++){
			for(int j=0;j<4;j++){
				players.get(i).startFly(j);
				players.get(i).setPlanePos(j,getXY(i,49),49);
			}
		}*/
	}
	//重新游戏
	private void replay_game(){
		for(int i=0;i<Maxplayer;i++){
			players.get(i).initPlanes();
		}
		AIPlaying=false;
		Waiting=false;
		PersonPlaying=false;
		Wait_select=false;
		startStep = 0;  //飞行起点
		endstep = 0;    //飞行终点
		personStep = 0;   //玩家操作-过程
		Aistep=0;  		  //Ai操作--过程
		sleeptime=2;	  //中间停顿时间
		GetMoreRand=false;			//是否奖励丢色子机会
		jampId_1=-2;
		jampId_2=-2;  //起飞值,第一个跳点,第2个跳点
		selectedID=-1;		//获取随机色子数,选择的棋子
		Playing_id=0;
		isGameOver=false;
	}

	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(!isGameOver){
				if(sleeptime>0){
					//中间停顿
					if((System.currentTimeMillis()-StartTime)>500){
						StartTime = System.currentTimeMillis();
						sleeptime--;
					}
				}else{
					//游戏开启
					if(isPersonPlay()){
						person_play();
					}else{
						Ai_play();
					}
				}
				
		}
	}
	//玩家开始操作
			private void person_play(){
				//第一步准备丢色子
				if(personStep==0){
					int framid = players.get(Playing_id).getPlayN() -1;
					Playszbtn.setPos(framid, getSzbtn(framid));
					Playszbtn.mAlpha=1;
					showWhoPlay.setFrame(framid);
					showWhoPlay.mAlpha=1;
					szshow.mDie=true;
					Waiting = true;
					personStep = 1;
				}else if(2==personStep){
					//色子动画开始
					if((System.currentTimeMillis()-StartTime)>500){
						StartTime = System.currentTimeMillis();
						if(!szmovie.ismove){
							addObject(szshow);
							szshow.setFrame(rand_num-1);
							afterPutsz();
						}
					}
				}else if(3==personStep){
					//移动动画
					if(!Waiting&&PersonPlaying){
						movePlay(selectedID);
					}
				}
			}
			//AI--方开始
			private void Ai_play(){
				//第一步色子动画
				if(0==Aistep){
					int framid = players.get(Playing_id).getPlayN() -1;
					showWhoPlay.setFrame(framid);
					showWhoPlay.mAlpha=1;
					szshow.mDie=true;
					rand_num = randNum();
					playMuise(SZSOUND);
					Aistep=1;
					setShaizimove();
				}
				if(1==Aistep){
					//色子动画开始
					if((System.currentTimeMillis()-StartTime)>500){
						StartTime = System.currentTimeMillis();
						if(!szmovie.ismove){//色子动画结束
							addObject(szshow);
							szshow.setFrame(rand_num-1);
							Aistep=2;
							int noStartnum = players.get(Playing_id).getNoStartNum();   //未起飞 
							int startNum = players.get(Playing_id).getStartNum();		//已经起飞
							if(rand_num>(canStartnum-1)){
							 //优先起飞---先起飞
								if(noStartnum>0){
									int startid = players.get(Playing_id).getCanstart();
									players.get(Playing_id).startFly(startid);
									playMuise(STARTS);
									if(GetMoreRand){
										//继续丢色子
										Aistep = 0;
										GetMoreRand=false;
										sleeptime =2;
										
									}else{
										//下一家走棋
										comeNext();
										sleeptime =2;
										personStep=0;
										Aistep=0;
									}
									
								}else{
									if(startNum>0){
										selectedID = getAIselect();
										AIPlaying = true;
										getstart_end();
										getjumpID(startStep);     //获取跳转位置
									}
								}
							}else{
								//没有起飞权限---有一家起飞的就走棋，没有就换下家
								if(startNum>0){
									selectedID = getAIselect();
									AIPlaying = true;
									getstart_end();
									getjumpID(startStep);     //获取跳转位置
									sleeptime =2;
								}else{
									comeNext();
									sleeptime =2;
									personStep=0;
									Aistep=0;
								}
							}
							
						}
					}
				}
				if(Aistep==2 && AIPlaying){
					movePlay(selectedID);
				}
			}
			//AI获取走棋棋子
			private int getAIselect(){
				ArrayList<String> list = players.get(Playing_id).getYearStart();
				int count = list.size();
				if(count>0){
					if(1==list.size()){
						return Integer.parseInt(list.get(0));
					}else{
						//判断是否有可以打的棋子
						for(int i=0;i<count;i++){
							int index = Integer.parseInt(list.get(i));
							int endindex = players.get(Playing_id).getplane(index).getLineid()+rand_num;
							if(endindex<50){
								int[] point = getlinePoint(Playing_id,endindex);
								for(int j=0;j<players.size();j++){
									if(j!=Playing_id){
										int[] ij = players.get(j).getPlaneids(point);
										for(int z=0;z<ij.length;z++){
											if(ij[z]>=0) {
												return index;
											}
										}
									}
								}
							}
						}
						//判断是否有跳飞
						for(int i=0;i<count;i++){
							int index = Integer.parseInt(list.get(i));
							int end = (players.get(Playing_id).getplane(index).getLineid()+rand_num)%4;
							if(1==end) return index;
						}
						//没有跳或打子选择第一个
						return Integer.parseInt(list.get(0));
					}
				}else{
					return -1;
				}
			}
	//玩家丢完色子后
			private void afterPutsz(){
				int startNum = players.get(Playing_id).getStartNum();		//已经起飞
				int noStartnum = players.get(Playing_id).getNoStartNum();//未起飞 
				//1-可以起飞
				if(rand_num>(canStartnum-1)){
					Wait_select=true;
					//没得选择-自动走棋
					if(1==startNum&&noStartnum==0){
						selectedID = players.get(Playing_id).getCanPlay();
						getstart_end();  //获取坐标
						getjumpID(startStep);     //获取跳转位置
						PersonPlaying = true;
					}
					personStep = 3;
				}else{
					//只能走棋
					if(startNum>0){
						Log.v("log","go--");
						if(1==startNum){
							//没得选择-自动走棋
							selectedID = players.get(Playing_id).getCanPlay();
							getstart_end();
							getjumpID(startStep);     //获取跳转位置
							PersonPlaying = true;
						}else{
							//等待玩家选择
							Wait_select=true;
						}
						personStep = 3;
					}else{
						//没棋可走-轮到下一家走棋
						if(GetMoreRand){
							GetMoreRand=false;
							personStep = 0;
						}else{
							comeNext();
							personStep = 0;
						}
					}
				}
			}
			//玩家选择 
			private void checkPersonplay(float ex,float ey){
				//等待玩家选择
				if(Wait_select){
					//等待选择棋子或起飞
					selectedID = players.get(Playing_id).checkTest(ex, ey);//选择要飞行的
					if(selectedID>=0){
						//选择走棋
						getstart_end();
						getjumpID(startStep);     //获取跳转位置
						PersonPlaying = true;
						Wait_select=false;
					}else{
						//现在起飞--检查是否起飞
						if(rand_num>(canStartnum-1)){
							//可以起飞
							selectedID = players.get(Playing_id).SelectStart(ex, ey);
							if(selectedID>=0){
								//成功起飞
								Wait_select=false;
								players.get(Playing_id).startFly(selectedID);
								playMuise(STARTS);
								szshow.mDie=true;
								if(GetMoreRand){
									//继续丢色子
									personStep = 0;
									GetMoreRand=false;
								}else{
									//下一家走棋
									comeNext();
									sleeptime =2;
								}
							}
						}
					}
				}
			}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
		
			switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					float ex = event.getX();
					float ey = event.getY();
					if(!isGameOver) checkPersonplay(ex,ey);
			
					//游戏已经结束
					if(showWin&&isGameOver){
						int res = winview.checkClk(ex, ey);
						if(res>1){
							showWin=false;
							if(3==res){
								replay_game();
							}else if(2==res){
								mActivity.setUI(((Planegame)mActivity).menuUI);
							}
						}
					}
				break;
				
				case MotionEvent.ACTION_UP:
					if(!isGameOver){
						//丢色子动画
						if(Playszbtn.test(event.getX(),event.getY())&&Waiting&&!PersonPlaying){
							Waiting = false;
							rand_num = randNum();
							playMuise(SZSOUND);
							setShaizimove();
							StartTime = System.currentTimeMillis();
							personStep = 2;
							Playszbtn.mAlpha=0;
						}
					}
					int sres = soundBtn.test(event.getX(),event.getY());
					if(0==sres){
						isbgSound=true;
						tools.savetomem(tools.bgsound, "0", mActivity);
						((Planegame)mActivity).menuUI.bgsound.resume();
						
					}else if(1==sres){
						isbgSound=false;
						tools.savetomem(tools.bgsound, "1", mActivity);
						((Planegame)mActivity).menuUI.bgsound.pause();
						
					}
					break;
			}
			return super.onTouchEvent(event);
		}
		
		//下一家
		private void comeNext(){
			Playing_id++;
			if(Playing_id>=Maxplayer)  {Playing_id %=Maxplayer;}
			if(players.get(Playing_id).Isover){
				comeNext();
			}
			showWhoPlay.mAlpha=0;
			sleeptime =2;
			personStep = 0;
			Aistep = 0;
			AIPlaying = false;
			PersonPlaying=false;
			Wait_select=false;
			
		}
	//检测是否到玩家走棋
	private boolean isPersonPlay(){
		if(!players.get(Playing_id).isAi()){
			return true;
		}
		return false;
	}
	//判断是否游戏结束
	private void checkIsgameover(){
		boolean res = false;
		int winNum = 0;			//已经结束的数量
		int maxNum = players.size();
		int personNum = 0;  	//人工玩家
		int personWinNum = 0;  //人工玩家胜利个数 
		for(int i=0;i<maxNum;i++){
			if(players.get(i).isWin()) winNum++;
			if(!players.get(i).isAi()) personNum++;
			if(!players.get(i).isAi()&&players.get(i).isWin()) personWinNum++;
		}
		//人工玩家都到达
		if(personNum==personWinNum){
			res = true;
		}
		
		if((maxNum-winNum)==1){
			res = true;
			
		}
		//游戏结束-计算得分
		if(res){
			isGameOver = true;
			jishuan();
		}
	}
	//计算分值
	private void jishuan(){
		szshow.mDie=true;
		for(int i=0;i<players.size();i++){
			int key = players.get(i).getPlayN();
			int num = players.get(i).getEndCount();
			Log.v("log", "num="+num);
			Score.get(""+key).setScore(num, num);
		}
		
		winview.setScore(Score);
		winview.setShow();
		showWin=true;
	}
	//色子按钮位置
	private AngleVector getSzbtn(int pos){
		AngleVector res = new AngleVector();
		float szw = sWidth/4;
		switch(pos){
		case 0:
			res.mX = sWidth-szw/2;
			res.mY = sHeight-szw/2;
			break;
		case 1:
			res.mX =  szw/2+2;
			res.mY =  sHeight-szw/2;
			break;
		case 2:
			res.mX =  szw/2+2;
			res.mY = szw/2+2;
			break;
		case 3:
			res.mX = sWidth-szw/2;
			res.mY = szw/2+2;
			break;
		}
		return res;
	}
	
	
	//获取跳转位置
	private void getjumpID(int start){
		int temp=0;
		temp = start+rand_num;
		if(1==temp%4&&start<46){
			jampId_1 = temp;
			if(jampId_1==17){
				endstep = 29; 
				jampId_2=21;
			}else{
				endstep +=4; 
			}
			
		}
	}
	//获取随机数
	private int randNum(){
		Random rand = new Random();
		int temp = rand.nextInt(1000)%6 +1;
		if(temp==6) GetMoreRand = true;
		return temp;
	}
	//移动棋子
	private void movePlay(int planeid){
		if((System.currentTimeMillis()-StartTime)>500){
			StartTime = System.currentTimeMillis();
			if(startStep<56){
				//获取玩家的位置
				int playid=players.get(Playing_id).getPlayN();
				if(startStep<=endstep){
					if(55==startStep){
						//到达终点
						players.get(Playing_id).setEnd(planeid);
						playMuise(ARRIVES);
						if(GetMoreRand){
							GetMoreRand = false;
							personStep = 0;
							PersonPlaying=false;
						}else{
							comeNext();
						}
						checkIsgameover();
						
					}else{
						if(startStep<endstep){
							if(startStep==jampId_1){
								//有跳转
								if(jampId_2>0){
									startStep=29;
									playMuise(LANDOFF);
									checkPaodao();
								}else{
									startStep+=4;
									playMuise(LANDOFF);
								}
								
								players.get(Playing_id).setPlanePos(planeid,getXY(playid,startStep),startStep);
								checkToStart(planeid);
								jampId_1=-2;
								jampId_2= -2;
							}else{
								startStep++;
								Log.v("log","start="+startStep);
								players.get(Playing_id).setPlanePos(planeid,getXY(playid,startStep),startStep);
								if((startStep==endstep&&endstep<47)||(startStep==jampId_1)){
									checkToStart(planeid);
								}
								playMuise(MOVESOUND);
							}
						}else{
							startStep++;
							
						}
					}
				}else{
					if(GetMoreRand){
						GetMoreRand = false;
						personStep = 0;
						PersonPlaying=false;
					}else{
						comeNext();
					}
					checkIsgameover();
				
				}
			}
		}
	}
	//判断是否有棋子要被打回原地 
	private void checkToStart(int pi){
		if(pi>=0){
			int[] tij = players.get(Playing_id).getMapij(pi);
			for(int i=0;i<players.size();i++){
				if(i!=Playing_id){
					int[] ij = players.get(i).getPlaneids(tij);
					for(int j=0;j<ij.length;j++){
						if(ij[j]>=0) {
							players.get(i).setTostart(ij[j]);
							playMuise(CRASHS);
						}
					}
				}
			}
		}
	}
	//判断跑道上是否有棋子
	private void checkPaodao(){
		int pdplay = (Playing_id+2)%4;
		if(pdplay<players.size()){
			int[] tij = players.get(pdplay).getPlanedaoids();
			for(int j=0;j<tij.length;j++){
				if(tij[j]>=0) {
					players.get(pdplay).setTostart(tij[j]);
					playMuise(CRASHS);
				}
			}
		}
	}
	//获取地图上的坐标
	private AngleVector getXY(int play,int index){
		int[] ij = getlinePoint(play,index);
		for(int i=0;i<mapList.size();i++){
			int temp[] = mapList.get(i).getpi();
			if(ij[0]==temp[0]&&temp[1]==ij[1]) return mapList.get(i).getXY();
		}
		return null;
	}
	
	//获取指点玩家路线图
	private int[] getlinePoint(int play,int index){
		int[] point = new int[2];
		switch(play){
		case 1:
			point = play_1.getLine(index);
			break;
		case 2:
			point = play_2.getLine(index);
			break;
		case 3:
			point = play_3.getLine(index);
			break;
		case 4:
			point = play_4.getLine(index);
			break;
		}
		return point;
	}
		
	//色子动画
	private void setShaizimove(){
		AngleVector from = new AngleVector();
		int whereid = players.get(Playing_id).getPlayN();
		switch(whereid){
		case 1:
			from.mX=sWidth;
			from.mY=sHeight-60;
			break;
		case 2:
			from.mX=0;
			from.mY=sHeight-60;
			break;
		case 3:
			from.mX=0;
			from.mY=60;
			break;
		case 4:
			from.mX=sWidth;
			from.mY=60;
			break;
		}
		AngleVector to = new AngleVector();
		to.mX=sWidth/2;
		to.mY=sHeight/2;
		szmovie.moveto(from, to);
	}
	//计算开始-结束位置
	private void getstart_end(){
		if(selectedID>=0){
			int getid =players.get(Playing_id).getplane(selectedID).getLineid(); 
			startStep = getid;
			endstep = getid+rand_num;
		}
	}
	

/*	@Override
	public void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
	}*/
	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		Log.v("log", "onResume--game");
	}
	@Override
	public void onActivate() {
		super.onActivate();
	
	}
	@Override
	public void onDeactivate() {
		super.onDeactivate();
	}
/*	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			mActivity.setUI(((Planegame)mActivity).menuUI);
			return true;
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
}
