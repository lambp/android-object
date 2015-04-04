package game.ui;


import game.data.Mapij;
import game.data.ball;
import game.data.intipos;
import game.data.maplist;
import game.data.maplist2;
import game.data.tempball;
import game.data.tools;
import game.sprite.bombsp;
import game.sprite.linesp;
import game.sprite.shownum;
import game.sprite.star;
import game.sprite.timelinesp;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;
import android.view.MotionEvent;
import bubble.shooter.rcgame.v1.R;
import bubble.shooter.rcgame.v1.mainActivity;

import com.android.angle.AngleActivity;
import com.android.angle.AngleObject;
import com.android.angle.AnglePhysicObject;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSegmentCollider;
import com.android.angle.AngleSound;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;
import com.android.angle.ButtonSp;
import com.android.angle.ShowPopText;

public class GameUI extends AngleUI{
	public int width,height,levelnum,hang=10,lie=8,scores=0;
	float bili,bili_2,ballwidth;
	Random rand=new Random();
	AngleSurfaceView sview;
	AngleSpriteLayout balllay,bglay,bombs,tzz,timely,paotai,ptbg,
	combos,coins,nums,cooltext,gametext,buttons,gameoverbg,mark,titletext,levelscore;
	AnglePhysicsEngine bottom,middle,balls,tops,gameoverly;
	public AngleVector[][] xypos;
	public int[][] initval;
	private linesp shotbg;
	tempball tempBall,tempball2;
	int newval=rand.nextInt(6)+1;
	int oldval=rand.nextInt(6)+1;
	float maxY;
	//AngleSprite leftzz,rightzz;
	AngleRotatingSprite topzz;
	timelinesp timeline;
	ButtonSp replaybtn,nextbtn,exitbtn,gametextshow,levelbg,scorebg;
	AngleSound shootsd,clickwall,clickball,alerm,bgmusic;
	int maxlel = 0;
	int lang=0;
	public GameUI(AngleActivity activity,int _levelnum) {
		super(activity);
		 width = mActivity.mGLSurfaceView.roWidth;
		 height = mActivity.mGLSurfaceView.roHeight;
		 sview = mActivity.mGLSurfaceView;
		 bili = (float)width/(float)344;
		 ballwidth = (float)width/8f;
		 levelnum = _levelnum;
		 maxY = (float) (9.2*ballwidth);
		 maxlel = maplist.list.length + maplist2.list2.length-1;
		 lang =  ((mainActivity) mActivity).lagId;
		 loadimg();
		 initlay();
	}
	//加载图片
	void loadimg(){
		levelscore = new AngleSpriteLayout(sview,(int)(bili*82),(int)(bili*39),R.drawable.buttons,0,45,82,39,4,2);
		balllay=new AngleSpriteLayout(sview, (int)ballwidth, (int)ballwidth, R.drawable.balls, 0, 0, 60, 60, 32, 8);
		bglay = new AngleSpriteLayout(sview, width, height, R.drawable.menubg, 0, 0, 344, 512);
		
		bombs = new AngleSpriteLayout(sview,(int)ballwidth,(int)(ballwidth),R.drawable.bobom,0, 0, 60, 60, 11, 8);
		tzz= new AngleSpriteLayout(sview,width,(int)(49*bili),R.drawable.titles,0,0,344,49);
		titletext =  ((mainActivity) mActivity).mapui.titles;
		timely = new AngleSpriteLayout(sview,4,width+5,R.drawable.wall,222,0,4,340,5,5);
		
		paotai = new AngleSpriteLayout(sview, (int)(86*bili), (int)(71*bili), R.drawable.balls, 0, 290, 86, 71,2,2);
		
		combos = new AngleSpriteLayout(sview,(int)(190*bili),(int)(44*bili),R.drawable.bobom,0, 141, 190, 44, 8, 1);
		coins  = new AngleSpriteLayout(sview,(int)ballwidth,(int)(ballwidth),R.drawable.bobom,215, 161, 40, 40, 9, 7);
		nums =  new AngleSpriteLayout(sview,(int)(32*bili),(int)(48*bili),R.drawable.bobom,215, 263, 32, 48, 10, 9);
		//goodtext =  new AngleSpriteLayout(sview,(int)(190*bili),(int)(60*bili),R.drawable.goodtext,0, 0, 190, 60, 7, 1);
		cooltext =  new AngleSpriteLayout(sview,(int)(190*bili),(int)(60*bili),R.drawable.goodtext,190, 0, 190, 60, 7, 1);
		
		gametext = new AngleSpriteLayout(sview,(int)(380*bili),(int)(60*bili),R.drawable.buttons,0, 0, 380, 60, 7, 1);	
		buttons=new AngleSpriteLayout(sview,(int)(54*bili),(int)(45*bili),R.drawable.buttons,0, 0, 54, 45, 7, 6);
		gameoverbg = new AngleSpriteLayout(sview,(int)(340*bili),(int)(290*bili),R.drawable.buttons,0, 218, 340, 290);
		mark = new AngleSpriteLayout(sview,width,height,R.drawable.buttons,400, 220, 100, 290);
		
		shootsd = new AngleSound(mActivity, R.raw.shoot);
		clickwall = new AngleSound(mActivity, R.raw.bubblehit);
		//clickball = new AngleSound(mActivity, R.raw.beatcl);
		alerm= new AngleSound(mActivity, R.raw.alarm);
		bgmusic= new AngleSound(mActivity, R.raw.main);
		
		//bgmusic.play(0.5f, true);
	}
	final int SHOOT=1,CLICKWALL=2,BALLCLIC=3,BRUST=4,COINS=5,ALERM=6,GOVER=7,GWIN=8;
	public void playsound(int type){
		boolean play=true;
		if(play){
			switch(type){
			case SHOOT:
				shootsd.load(R.raw.shoot);
				shootsd.play();
				break;
			case CLICKWALL:
				 clickwall.load(R.raw.bubblehit);
				 clickwall.play();
				break;
			case BALLCLIC:
				// clickball.load(R.raw.beatcl);
				// clickball.play();
				break;
			case BRUST:
				clickball.load(R.raw.burst);
				clickball.play();
				break;
			case COINS:
				clickwall.load(R.raw.coins);
				clickwall.play();
				break;
			case ALERM:
				if(!alerm.isPlaying()){
					alerm.play(0.5f, true);
				}
				break;
			case GOVER:
				 shootsd.load(R.raw.lose);
				 shootsd.play();
				break;
			case GWIN:
				 shootsd.load(R.raw.win);
				 shootsd.play();
				break;
			}
		}
	}
	//初始化图层
	void initlay(){
		
		bottom = new AnglePhysicsEngine(30);
		middle = new AnglePhysicsEngine(30);
		balls = new AnglePhysicsEngine(330);
		tops = new AnglePhysicsEngine(100);
		gameoverly = new AnglePhysicsEngine(50);
		addObject(bottom);
		addObject(middle);
		addObject(balls);
		addObject(tops);
		addObject(gameoverly);
		//背景
		bottom.addObject(new AngleSprite(width/2, height/2, bglay));
		//顶部可以下移部分
		topzz=new AngleRotatingSprite(width/2,(int)(-tzz.roHeight/2), tzz);
		bottom.addObject(topzz);
		
		//警戒线
		timeline = new timelinesp(timely,new AngleVector(width/2, maxY));
		bottom.addObject(timeline);
		//反弹墙
		initWall();
		
		
		//发射炮台
		ptbg=new AngleSpriteLayout(sview, (int)(bili*8*ballwidth/3*1.3), (int)(bili*ballwidth*1.3), R.drawable.balls, 0, 120, 160, 60);
		//炮台背景
		middle.addObject(new AngleSprite(width/2,height-ptbg.roHeight,paotai));
		shotbg = new linesp(width/2,height-ptbg.roHeight,ptbg);
		middle.addObject(shotbg);
		
		//准备吐泡背景
		AngleSprite stbg=new AngleSprite(width/4,height-ptbg.roHeight,paotai);
		stbg.setFrame(1);
		stbg.mScale.set(1.2f,1.2f);
		middle.addObject(stbg);
		
		//可以移动的泡泡
		tempBall = new tempball(balllay, new AngleVector(width/4,height-ptbg.roHeight), newval);
		middle.addObject(tempBall);
		//静止的泡泡
		tempball2 = new tempball(balllay, new AngleVector(width/2,height-ptbg.roHeight), oldval);
		middle.addObject(tempball2);
		tempball2.mScale.set(1,1);
		//初始化泡泡
		initballs();
		
		//游戏结束元素
		gametextshow = new ButtonSp(width/2, height/2-gameoverbg.roHeight/2-titletext.roHeight/3, titletext, 0);
		//等级
		levelbg =  new ButtonSp(width/3, height/2-nums.roHeight, levelscore, lang);
		//得分
		scorebg =  new ButtonSp(width/3-8, height/2, levelscore, 2+lang);
		
		replaybtn=new ButtonSp(width/2, (int)(height/2+1.5*buttons.roHeight), buttons, 2);
		nextbtn=new ButtonSp((int)(width/2+buttons.roWidth*1.2), (int)(height/2+1.5*buttons.roHeight), buttons, 3);
		exitbtn=new ButtonSp((int)(width/2-buttons.roWidth*1.2), (int)(height/2+1.5*buttons.roHeight), buttons, 1);
		//背景
		ButtonSp gobg = new ButtonSp(width/2, height/2, gameoverbg,0);
		ButtonSp markbg = new ButtonSp(width/2, height/2, mark,0);
		
		gobg.show(false);
		markbg.show(false);
		gametextshow.show(false);
		levelbg.show(false);
		scorebg.show(false);
		replaybtn.show(false);
		nextbtn.show(false);
		exitbtn.show(false);
		
		gameoverly.addObject(markbg);
		gameoverly.addObject(gobg);
		
		gameoverly.addObject(gametextshow);
		gameoverly.addObject(levelbg);
		gameoverly.addObject(scorebg);
		
		gameoverly.addObject(replaybtn);
		gameoverly.addObject(nextbtn);
		gameoverly.addObject(exitbtn);
		
	}
	//初始化球
	void initballs(){
		 xypos = intipos.initpos(hang,lie,ballwidth);
		 int count1=maplist.list.length;
		 Log.v("log","count1="+maplist.list.length);
		 if(levelnum>count1){
			 initval = maplist2.getmap(levelnum-count1,hang,lie);
		 }else{
			 initval = maplist.getmap(levelnum,hang,lie);
		 }
		 for(int i=0;i<hang;i++){
			 for(int j=0;j<lie;j++){
				 if(initval[i][j]>0){
					 balls.addObject(new ball(balllay,initval[i][j],xypos[i][j],ballwidth/2,new Mapij(i,j),height));
				 }
				 if(initval[i][j]<=0&&i==0){
					 balls.addObject(new star(balllay,0,xypos[i][j],ballwidth/3,new Mapij(i,j)));
				 }
			 }
		 }
		 
	}
	//获取坐标
	public AngleVector getPos(int i,int j){
		return xypos[i][j];
	}
	//显示游戏结束
	void showgameover(boolean show){
		alerm.pause();
		if(show){
			for(int i=0;i<gameoverly.count();i++){
				if(gameoverly.childAt(i) instanceof ButtonSp){
					((ButtonSp)gameoverly.childAt(i)).show(show);
				}
			}
			gameoverly.addObject(new shownum(""+(levelnum+1),nums,new AngleVector(levelbg.mPosition.mX+levelscore.roWidth, height/2-nums.roHeight),0,false));
			gameoverly.addObject(new shownum(""+scores,nums,new AngleVector(scorebg.mPosition.mX+levelscore.roWidth, height/2),0,false));
		}else{
			for(int i=0;i<gameoverly.count();i++){
				if(gameoverly.childAt(i) instanceof ButtonSp){
					((ButtonSp)gameoverly.childAt(i)).show(show);
				}
				if(gameoverly.childAt(i) instanceof shownum){
					((shownum)gameoverly.childAt(i)).hidden=true; 
				}
			}
			Log.v("log", "showgameover="+gameoverly.count());
		}
	}
	//下移一格
	void xiayi(){
		for(int i=0;i<hang;i++){
			for(int j=0;j<lie;j++){
				xypos[i][j].mY+=0.2;
			}
		}
		for(int i=0;i<balls.count();i++){
			if(balls.childAt(i) instanceof ball) ((ball)balls.childAt(i)).mPosition.mY+=0.2;
			if(balls.childAt(i) instanceof star) ((star)balls.childAt(i)).mPosition.mY+=0.2;
		}
		topzz.mPosition.mY+=0.2;
	}
	//上移一格
	void shangyi(){
		for(int i=0;i<hang;i++){
			for(int j=0;j<lie;j++){
				xypos[i][j].mY-=3;
			}
		}
		for(int i=0;i<balls.count();i++){
			if(balls.childAt(i) instanceof ball) ((ball)balls.childAt(i)).mPosition.mY-=3;
			if(balls.childAt(i) instanceof star) ((star)balls.childAt(i)).mPosition.mY-=3;
		}
		topzz.mPosition.mY-=3;
	}
	void shangyi_1(){
		for(int i=0;i<hang;i++){
			for(int j=0;j<lie;j++){
				xypos[i][j].mY-=2;
			}
		}
		for(int i=0;i<balls.count();i++){
			if(balls.childAt(i) instanceof ball) ((ball)balls.childAt(i)).mPosition.mY-=2;
			if(balls.childAt(i) instanceof star) ((star)balls.childAt(i)).mPosition.mY-=2;
		}
		topzz.mPosition.mY-=2;
	}
	//检测是否有在移动的
	boolean checkanymove(){
		boolean res=false;
		for(int i=0;i<balls.count();i++){
			if(balls.childAt(i) instanceof ball) {
				if(((ball)balls.childAt(i)).checkMove()){
					return true;
				}
			}
		}
		return res;
	}
	AnglePhysicObject TopWall;
	//初始化反弹墙
	private void initWall(){

		// Right wall
		AnglePhysicObject	mWall = new AnglePhysicObject(1, 0);
		mWall.mPosition.set(width, height/2);
		mWall.addSegmentCollider(new AngleSegmentCollider(0, height/2, 0, -height/2));
		mWall.mBounce = 0.5f;
		//mWall.mMass = 10;
		balls.addObject(mWall); 
		// Left wall
		mWall = new AnglePhysicObject(1, 0);
		mWall.mPosition.set(0, height/2);
		mWall.addSegmentCollider(new AngleSegmentCollider(0, height/2, 0, -height/2));
		mWall.mBounce = 0.5f;
		balls.addObject(mWall); 
		// top wall
		TopWall = new AnglePhysicObject(1, 0);
		TopWall.mPosition.set(width/2, 0);
		TopWall.addSegmentCollider(new AngleSegmentCollider(width/2, -1, -width/2, -1));
		TopWall.mBounce = 0.5f;
		balls.addObject(TopWall); 
		
	}
	//重置游戏
	void resetgame(){
		scores=0;
		//初始化泡泡
		initballs();
		nextPop();
		shooting=false;
		timeline.setshow(false);
		alerm.pause();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		float ex,ey;
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			ex=event.getX();
			ey=event.getY();
			
			if(!Gameover&&ey<(maxY+ballwidth)){
				shotbg.setRotation(new AngleVector(ex, ey));
			}
			if(Gameover){
				if(replaybtn.test(ex, ey)){
					showgameover(false);
					moveallball=true;
				}
				if(nextbtn.test(ex, ey)){
					if(levelnum<maxlel&&GAMESTAP==2){
						levelnum++;
						showgameover(false);
						moveallball=true;
					}
				}
				if(exitbtn.test(ex, ey)){
					((mainActivity) mActivity).setUI(((mainActivity) mActivity).mapui);
				}
				
			}
			break;
		case MotionEvent.ACTION_UP:
			ex=event.getX();
			ey=event.getY();
			if(!Gameover&&ey<(maxY+ballwidth)&&!shooting&&!checkanymove()){
				shootball(ex,ey);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			ex=event.getX();
			ey=event.getY();
			if(!Gameover&&ey<(maxY+ballwidth)){
				shotbg.setRotation(new AngleVector(ex, ey));
			}
			break;
		}
		
		return super.onTouchEvent(event);
	}
	//把泡泡移动到炮台
	public void setpopmove(){
		
		tempBall.moveto(new AngleVector(width/2,height-ptbg.roHeight));
	}
	//下一个
	public void nextPop(){
		
		oldval=newval;
		newval=getnewval();
		tempball2.setFrame(oldval);
		tempball2.mAlpha=1;
		
		tempBall.setFrame(newval);
		tempBall.setpos(new AngleVector(width/4,height-ptbg.roHeight));
		
	}
	//获取新的泡泡
	int getnewval(){
		int res = 1;
		int[] values = new int[10];
		for(int i=0;i<hang;i++){
			for(int j=0;j<lie;j++){
				if(initval[i][j]>0){
					values[initval[i][j]]=initval[i][j];
				}
			}
		}
		int count=0;
		int ms=1;
		for(int i=0;i<10;i++){
			if(values[i]>0){
				count++;
				ms=i;
			}
		}
		if(count>1){
			res = rand.nextInt(ms)+1;
		}else{
			res = ms;
		}
		return res;
	}
	//清泡泡
	void removeballs(){
		int sum = balls.count();
		boolean clear=true;
		if(balls.childAt(sum-1) instanceof ball) {
			tops.addObject(new bombsp(bombs,((ball)balls.childAt(sum-1)).mPosition,false));
			((ball)balls.childAt(sum-1)).mDie=true;
			clear=false;
		}
		if(balls.childAt(sum-1) instanceof star) {
			((star)balls.childAt(sum-1)).mDie=true;
			clear=false;
		}
		
		
		if(clear){
			if(topzz.mPosition.mY>ballwidth/2){
				topzz.mPosition.mY-=5;
			}
			if(topzz.mPosition.mY<=ballwidth/2){
				topzz.mPosition.mY=(int)(-tzz.roHeight/2);
				Gameover=false;
				moveallball=false;
				resetgame();
			}
		}
	}
	boolean moveallball=false;//清楚泡泡
	
	double startT=System.currentTimeMillis();
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		if(System.currentTimeMillis()-startT>100){
			startT=System.currentTimeMillis();
			if(dropMovie){
				//悬空掉落清除
				popDown();
			}
			if(Gameover&&moveallball){
				removeballs();
			}else{
				if(!Gameover){
					xiayi();
					checkgameover();
				}else{
					timeline.setshow(false);
					alerm.pause();
				}
				
			}
		}
		
		super.step(secondsElapsed);
	}
	//检测游戏是否结束
	boolean Gameover=false;
	int GAMESTAP=0;//1-游戏失败,2-游戏过关
	void checkgameover(){
		boolean win=true;
		for(int i=0;i<hang;i++){
			for(int j=0;j<lie;j++){
				if(initval[i][j]> 0){
					win=false;
				}
			}
		}
		if(win){
			Gameover=true;
			GAMESTAP =2;
			gametextshow.setFrame(lang);
			showgameover(true);
			playsound(GWIN);
			int maxlv = Integer.parseInt(tools.getstrformmem(tools.stepmun, mActivity));
			if((levelnum+2)>maxlv){
			  tools.savetomem(tools.stepmun,""+(levelnum+2),mActivity);
			}
		}else{
			GAMESTAP =1;
			float my=0;
			for(int i=0;i<balls.count();i++){
				if(balls.childAt(i) instanceof ball){
					if(!((ball)balls.childAt(i)).isdrop&&((ball)balls.childAt(i)).mPosition.mY>=(maxY)&&((ball)balls.childAt(i)).mapij.i>=0){
						Gameover=true;
						gametextshow.setFrame(2+lang);
						showgameover(true);
						Log.v("log", "gameover-1");
						playsound(GOVER);
					}
					
					if(((ball)balls.childAt(i)).mPosition.mY>my&&((ball)balls.childAt(i)).mapij.i>=0){
						my = ((ball)balls.childAt(i)).mPosition.mY;
					}
				}
			}
			if(my>6*ballwidth){
				playsound(ALERM);
				timeline.setshow(true);
			}else{
				alerm.pause();
				timeline.setshow(false);
			}
		}
		shooting=false;
	}
	//设置游戏结束
	public void setGameOver(){
		Gameover=true;
		gametextshow.setFrame(2+lang);
		showgameover(true);
		playsound(GOVER);
		Log.v("log", "gameover-2");
	}
	//设置悬空的掉落
	private void popDown(){
		if(dropList.size()>0){
			int count = dropList.size()-1;
			Mapij  temp = dropList.get(count);
			initval[temp.i][temp.j] = 0;
			setballdrop(temp);
			dropList.remove(count);
		}else{
			dropMovie=false;
		}
	}
	//悬空掉落
	public void setballdrop(Mapij ids){
		for(int i=0;i<balls.count();i++){
			if (balls.childAt(i) instanceof ball){
				if(((ball)balls.childAt(i)).checkXY(ids) && !((ball)balls.childAt(i)).isdrop){
					 int rs = rand.nextInt(100);
					 int rv = -1;
					 if(rs%2==0){
						 rv =1;
					 }
					((ball)balls.childAt(i)).drop(rv*rand.nextInt(200));
					//showNums(((ball)balls.childAt(i)).mPosition);
					
				}
			}
		}
	}
	//显示金币
	public void showCoins(AngleVector pos){
		//tops.addObject(new bombsp(coins,pos,true));
		scores+=(10*(combonum-1)+10);
		tops.addObject(new shownum(""+(10*(combonum-1)+10),nums, pos, 1, true));
	}
	//掉落的显示分值
	public void showNums(AngleVector pos){
		scores+=(10*(combonum-1)+10);
		tops.addObject(new shownum(""+(10*(combonum-1)+10),nums, pos, 1, true));
	}
	int sum=1;
	boolean shooting=false;
	//发射泡泡
	void shootball(float ex,float ey){
		
		playsound(SHOOT);
		shooting=true;
		tempball2.mAlpha=0;
		ball test = new ball(balllay,tempball2.roFrame,new AngleVector(width/2, (int)(height-ptbg.roHeight)),ballwidth/2,new Mapij(-1,-1),height);
		balls.addObject(test);
		int spead = 500;
		float r = (float) Math.sqrt((ex-test.mPosition.mX)*(ex-test.mPosition.mX)+(ey-test.mPosition.mY)*(ey-test.mPosition.mY));
		float cos = (ex-test.mPosition.mX)/r;
		float sin = (ey-test.mPosition.mY)/r;
		test.setMove(new AngleVector(cos*spead,sin*spead));
/*		if(sum%15==0){
			xiayi();
		}
		sum++;*/
	}
	//检测清除
	ArrayList<Mapij> popNeedCleared = new ArrayList<Mapij>();
	boolean[][] popCheacked=new boolean[hang][lie];
	int combonum=0;
	public void removeBall(int posY,int posX){
		
		popNeedCleared = new ArrayList<Mapij>();
		popCheacked =  new boolean[hang][lie];
		popNeedCleared.add(new Mapij(posY,posX));
		popCheacked[posY][posX]=true;
		//递归检查
		recursionCheack(posY,posX,initval[posY][posX]);
		Log.v("log","count="+popNeedCleared.size());
		int	count = popNeedCleared.size();
		if(count>2){
			shangyi();
			//playsound(BRUST);
			for(int x=0;x<count;x++){
				for(int b=0;b<balls.count();b++){
					AngleObject O=balls.childAt(b);
					if (O instanceof ball){
						int i=popNeedCleared.get(x).i;
						int j=popNeedCleared.get(x).j;
						if(((ball) O).checkSelect(i,j)){
							O.mDie = true;
							initval[i][j]=0;
							if(popNeedCleared.get(x).i==0){
								 balls.addObject(new star(balllay,0,xypos[i][j],ballwidth/3,new Mapij(i,j)));	
								 //第一行有空的就补上	
							}
							//爆炸效果
							//tops.addObject(new bombsp(bombs,xypos[i][j],false));
							//数显示分值
							scores+=(10*(combonum)+10);
							tops.addObject(new shownum(""+(10*combonum+10),nums, xypos[i][j], 1, true));
							
						}
					}					
				}
				
			}
			combonum++;

			if((count>3&&count<5)){
			   //不错哦
				tops.addObject(new ShowPopText(titletext,new AngleVector(width/2, height/4),6+lang,4));
			}
			if(count>=5&&count<7){
				//太棒了
				tops.addObject(new ShowPopText(titletext,new AngleVector(width/2, height/4),4+lang,4));
			}
			if(count>=7&&count<9){
				//帅呆了
				tops.addObject(new ShowPopText(titletext,new AngleVector(width/2, height/4),8+lang,4));
			}
			if(count>=9){
				//酷毙
				tops.addObject(new ShowPopText(titletext,new AngleVector(width/2, height/4),10+lang,4));
			}
		}else{
			//playsound(BALLCLIC);
			combonum=0;
			shangyi_1();
		}

		//清除泡泡后需要检测是否有悬空的泡泡
		 
		 dropCheack();
		if(dropList.size()>0){
			dropMovie = true;
			//playsound(COINS);
		}
		checkgameover();
		setpopmove();
		
		//nextPop();
	}
	//下落动画控制
	boolean dropMovie=false;
	int[][] cloneViewArray =   new int[hang][lie];
	ArrayList<Mapij> dropList = new ArrayList<Mapij>(); 
	//检测悬空掉落
	private void dropCheack(){
		cloneViewArray = new int[hang][lie];
		popCheacked =  new boolean[hang][lie];
		
		for(int i=0;i<hang;i++){
			for(int m = 0;m<lie;m++){
				if(initval[i][m]>0){
					cloneViewArray[i][m] = 1;
				}else{
					cloneViewArray[i][m] = 0;
				}
			}
		}
		for(int j= 0;j<lie;j++){
			if(initval[0][j]>0&&nerverCheacked(0,j)){
				popCheacked[0][j]=true;
				cloneViewArray[0][j] = 0;
				dropCursionCheack(0,j);
			}
		}
		//保存悬空的
		dropList = new ArrayList<Mapij>(); 
		for(int i=0;i<hang;i++){
			for(int j=0;j<lie;j++){
				if(cloneViewArray[i][j]>0){
					dropList.add(new Mapij(i,j));
					initval[i][j] = 0;
				}
			}
		}
		Log.v("log","drop-3");
	}
	
	//drop检测要执行的递归检查--------------------------------------------
	private void dropCursionCheack(int i,int j){
		ArrayList<Mapij> arr = surroundPosition(i,j);
			for(int a = 0;a<arr.size();a++){
				int Y = arr.get(a).i;
				int X = arr.get(a).j;
				if(initval[Y][X]>0&&nerverCheacked(Y,X)){
					popCheacked[Y][X]=true;
					cloneViewArray[Y][X] = -1;
					dropCursionCheack(Y,X);
				}
			}
	}
	private void recursionCheack(int posY,int posX,int type){
		ArrayList<Mapij> arr = surroundPosition(posY,posX);
		for(int i = 0;i<arr.size();i++){
			int X = arr.get(i).j;
			int Y = arr.get(i).i;
			if(initval[Y][X]>0 && nerverCheacked(Y,X)){
				popCheacked[Y][X]=true;
				if(initval[Y][X]==type){
					popNeedCleared.add(new Mapij(Y,X));
					recursionCheack(Y,X,type);
				}
			}
		}
	}
	private boolean nerverCheacked(int Y,int X){
		
		if(popCheacked[Y][X]){
			return false;
		}
		return true;
	}
	//获取指定点周围合法的点,返回一个数组----------------------
		private ArrayList<Mapij> surroundPosition(int posY,int posX){
			ArrayList<Mapij> posArr = new ArrayList<Mapij>();
			if(posY%2==0){
				//左点
				if(posX-1>-1){posArr.add(new Mapij(posY,posX-1));}
				//右点
				if(posX+1<lie){posArr.add(new Mapij(posY,posX+1));}
				//左上点
				if((posY-1>-1)&&(posX-1>-1)){posArr.add(new Mapij(posY-1,posX-1));}
				//右上点
				if((posY-1>-1)&&(posX<lie)){posArr.add(new Mapij(posY-1,posX));}
				//左下点
				if((posY+1<hang)&&(posX-1>-1)){posArr.add(new Mapij(posY+1,posX-1));}
				//右下点
				if((posY+1<hang)&&(posX<lie)){posArr.add(new Mapij(posY+1,posX));}
			}else{
				//左点
				if(posX-1>-1){posArr.add(new Mapij(posY,posX-1));}
				//右点
				if(posX+1<lie){posArr.add(new Mapij(posY,posX+1));}
				//左上点
				if((posY-1>-1)&&(posX>-1)){posArr.add(new Mapij(posY-1,posX));}
				//右上点
				if((posY-1>-1)&&(posX+1<lie)){posArr.add(new Mapij(posY-1,posX+1));}
				//左下点
				if((posY+1<hang)&&(posX>-1)){posArr.add(new Mapij(posY+1,posX));}
				//右下点
				if((posY+1<hang)&&(posX+1<lie)){posArr.add(new Mapij(posY+1,posX+1));}
			}
			return posArr;
		}
		@Override
		public void onPause() {
			// TODO 自动生成的方法存根
			bgmusic.pause();
			alerm.pause();
			super.onPause();
		}
		@Override
		public void onActivate() {
			// TODO 自动生成的方法存根
			super.onActivate();
		}
		@Override
		public void onDeactivate() {
			// TODO 自动生成的方法存根
			bgmusic.pause();
			super.onDeactivate();
		}
		
		
}
