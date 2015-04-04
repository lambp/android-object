package game.ui;


import game.data.map;
import game.data.tools;
import game.sprite.Posstars;
import game.sprite.buttonS;
import game.sprite.combtn;
import game.sprite.font_numbers;
import game.sprite.showLevel;
import game.sprite.showTag;
import game.sprite.star;

import java.util.ArrayList;
import java.util.Random;

import org.kaka.popstar2015.PopstartActivity;

import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.angle.AngleActivity;
import com.android.angle.AngleFont;
import com.android.angle.AngleMusic;
import com.android.angle.AngleObject;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;
import org.kaka.popstar2015.R;

public class GameUI extends AngleUI{
	
	float bili;
	 AngleSurfaceView sview;
	 AngleSpriteLayout stars;
	 AnglePhysicsEngine Bigstars,bgs,btns;
	 int[][] starVal;
	 boolean[][] clickBoard=new boolean[10][10];
	 boolean gameOver=false,ispause=false,resetGame=false,isShowTag = false,replay=false,isshowlv;
	 
	 int[] every_color_count=new int[100];
	 AngleVector[][] pos = new AngleVector[10][10];
	
	int TopScore,myScore,ToScore,gameStep=1,width,height;;
	
	private AngleObject Texts;
	private AngleString TopStr,ToStr,AddStr,stepStr;
	
	font_numbers myStr;
	
	int[] jiangli = {1500,1200,900,800,700,600,500,400,300,200};
	
	buttonS pauseBtn,gameOver_show;
	combtn rePlay,conBtn,exitbtn;
	showTag guoguan,showCool;
	showLevel showlv;
	AngleMusic clickS,winS,loseS,moveS,removeS;
	
	public GameUI(AngleActivity activity) {
		super(activity);
		 width = mActivity.mGLSurfaceView.roWidth;
		 height = mActivity.mGLSurfaceView.roHeight;
		 sview = mActivity.mGLSurfaceView;
		 bili = (float)width/(float)688;
		 
		 starVal = new int[10][10];
		 
		 init();
	}
	
	private void init(){
		
		bgs = new AnglePhysicsEngine(20);
		
		addObject(bgs);
		AngleSpriteLayout bg = new AngleSpriteLayout(sview, width, height, R.drawable.gamebg, 0, 0, 688,1024);
		bgs.addObject(new AngleSprite(width/2, height/2, bg));
	
		Texts = new AngleObject();
		
		addObject(Texts);
		
		Bigstars = new AnglePhysicsEngine(200);
		addObject(Bigstars);
		 
		float pw = (float)width/(float)10;
		 
		stars = ((PopstartActivity)mActivity).menuui.stars;
		 
		pos = map.getmap(width, height); 
	
		
		String top = tools.getstrformmem(tools.topscore,mActivity);
		
		if(top.equals("null")){
			TopScore=0;
			tools.savetomem(tools.topscore, "0",mActivity);
		}else{
			TopScore=Integer.parseInt(top);
		}
		
		ToScore = 1000;
		//加载字体
		AngleFont fntCafe=new AngleFont(mActivity.mGLSurfaceView, width/16, Typeface.createFromAsset(mActivity.getAssets(),"NINA.TTF"), 222, 0, 0, 255, 255, 255, 255);
		AngleFont fnte=new AngleFont(mActivity.mGLSurfaceView, width/12, Typeface.createFromAsset(mActivity.getAssets(),"NINA.TTF"), 222, 0, 0, 255, 255, 255, 255);
		

		
		
		AngleSpriteLayout bgs_lay = new AngleSpriteLayout(sview,(int)(260*bili),(int)(60*bili),R.drawable.bgitems,0,592,260,60);
		AngleSpriteLayout title_lay = new AngleSpriteLayout(sview,(int)(130*bili),(int)(70*bili),R.drawable.titletext,426,877,130,70,4,2);
		AngleSpriteLayout title_lay_2 = new AngleSpriteLayout(sview,(int)(210*bili),(int)(68*bili),R.drawable.titletext,0,877,210,68,4,2);
		//关卡背景
		bgs.addObject(new AngleSprite((int)(175*bili)/2,(int)(100*bili),new AngleSpriteLayout(sview,(int)(175*bili),(int)(60*bili),R.drawable.bgitems,260,592,175,60)));
		stepStr = new AngleString(fnte," "+gameStep,(int)(175*bili)/2,(int)(115*bili),AngleString.aCenter);
				
		//最高纪录背景
		bgs.addObject(new AngleSprite((int)(175*bili)+title_lay_2.roWidth+bgs_lay.roWidth/2,(int)(100*bili),bgs_lay));		
		bgs.addObject(new buttonS((int)(175*bili)+title_lay_2.roWidth/2,(int)(100*bili),title_lay_2,((PopstartActivity)mActivity).lagId+2));
		TopStr=new AngleString(fntCafe,""+TopScore,(int)(175*bili)+title_lay_2.roWidth+bgs_lay.roWidth/2,(int)(100*bili)+(int)(14*bili),AngleString.aCenter);
		
		//当前分数背景//目标分数
		bgs.addObject(new AngleSprite((int)(width)/2,(int)(100*bili)+bgs_lay.roHeight+(int)(10*bili),bgs_lay));			
		myStr = new font_numbers(0,fntCafe,new AngleVector(width/2,(int)(100*bili)+bgs_lay.roHeight*3/2),width/30);		
		ToStr=new AngleString(fntCafe," |"+ToScore,width/2,(int)(100*bili)+bgs_lay.roHeight*3/2,AngleString.aLeft);
		
		//加分数
		AddStr = new AngleString(fnte,"",width/2,height/4,AngleString.aCenter);
		AddStr.mAlpha=0;		
	
		
		
		Texts.addObject(stepStr);
		Texts.addObject(TopStr);
		Texts.addObject(myStr);
		Texts.addObject(ToStr);
		Texts.addObject(AddStr);
		
		
		//bgs.addObject(new buttonS(width/2-width/4,65+width/10+5,new AngleSpriteLayout(sview,(int)(80*bili),(int)(28*bili),R.drawable.btns,0,56,80,28,2,2),((PopstartActivity)mActivity).lagId));
		
		btns = new AnglePhysicsEngine(20);
		addObject(btns);
		
		gameOver_show = new buttonS(width/2,-200, new AngleSpriteLayout(sview, (int)(160*bili*3)/2, (int)(45*bili*3)/2, R.drawable.btns,0, 217, 160, 45, 2, 2),((PopstartActivity)mActivity).lagId); 
		btns.addObject(gameOver_show);
		

		rePlay = new combtn(new AngleVector(width/2,-300),((PopstartActivity)mActivity).menuui.btnbg,((PopstartActivity)mActivity).menuui.btntitle,((PopstartActivity)mActivity).lagId );
		conBtn = new combtn(new AngleVector(width/2,-300),((PopstartActivity)mActivity).menuui.btnbg,((PopstartActivity)mActivity).menuui.btntitle,((PopstartActivity)mActivity).lagId+2 );
		exitbtn = new combtn(new AngleVector(width/2,-300),((PopstartActivity)mActivity).menuui.btnbg,((PopstartActivity)mActivity).menuui.btntitle,((PopstartActivity)mActivity).lagId+4 );
		
		btns.addObject(conBtn);
		btns.addObject(rePlay);
		btns.addObject(exitbtn);
		
		pauseBtn =  new buttonS(width- (int)(28*bili),(int)(65*bili)+width/10, new AngleSpriteLayout(sview,  (int)(50*bili), (int)(50*bili), R.drawable.btns,160, 0, 28, 28),0);
		btns.addObject(pauseBtn);
		
		guoguan = new showTag(new AngleSpriteLayout(sview, (int)(230*bili),(int)(180*bili), R.drawable.btns,0, 352, 115, 90, 2, 2),new AngleVector(-300,-300),((PopstartActivity)mActivity).langid);
		
		showCool =  new showTag(new AngleSpriteLayout(sview, (int)(305*bili),(int)(132*bili), R.drawable.titletext,0, 315, 305, 132, 10, 2),new AngleVector(-300,-300),((PopstartActivity)mActivity).lagId);
	
		btns.addObject(guoguan);
		btns.addObject(showCool);
		
		clickS = new AngleMusic(mActivity, R.raw.choose);
		winS = new AngleMusic(mActivity, R.raw.win);
		loseS = new AngleMusic(mActivity, R.raw.lose);
		moveS = new AngleMusic(mActivity, R.raw.item1);
		removeS = new AngleMusic(mActivity, R.raw.remove);
		
		showlv = new showLevel(new AngleSpriteLayout(sview, (int)(2*256*bili),(int)(2*320*bili), R.drawable.texts,0, 0, 256, 320, 2, 2),fnte,width,height,((PopstartActivity)mActivity).langid,bili);
		btns.addObject(showlv);
		showlv.setmove(new AngleVector(width/2, 100), 1, 1000);
		
		isshowlv=true;
		

		 
	}
	//重新来一局
	private void resetGame(){
		
		isShowTag = false;
		guoguan.setmove(new AngleVector(-200,-200), new AngleVector(-200,-200));
		res = new ArrayList<int[]>();
		Random rand = new Random();
		for(int x=0;x<10;x++){
			for(int y=0;y<10;y++){
				int rd = rand.nextInt(100)%5;
				Bigstars.addObject(new star(stars,pos[x][y],x,y,rd));
				starVal[x][y]=rd;
			}
		}
	}
	
	private void rePlaying(){
	
		gameOver_show.Moveto(new AngleVector(width/2,-100), 5);
		myScore = 0;
		myStr.reset_num();
		
		gameOver = false;
		gameStep=1;
		
		stepStr.set("1");
		
		ToScore=1000;
		ToStr.set(" / 1000");
		showlv.setmove(new AngleVector(width/2, 100), 1, 1000);
		isshowlv=true;
	
	}
	//检测是否有在移动的
	private boolean checkmove(){
		for(int i=0;i<Bigstars.count();i++){
			if(((star)Bigstars.childAt(i)).ismove()) return false;
		}
		return true;
	}
	
	//重置游戏
	public void resetgame(){
		if(myScore>0){
		ispause = false;
		movebtns(false);
		replay=true;
		}
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			if(!gameOver&&!ispause&&checkmove()){
				checkStar(event.getX(),event.getY());
			}
			break;
		case MotionEvent.ACTION_UP:
			if(conBtn.test(event.getX(),event.getY())){
				movebtns(false);
			    ispause=false;
			}
			if(rePlay.test(event.getX(),event.getY())){
				ispause = false;
				movebtns(false);
				replay=true;
			}
			if(exitbtn.test(event.getX(),event.getY())){
				movebtns(false);
				ispause = false;
				((PopstartActivity)mActivity).setUI(((PopstartActivity)mActivity).menuui);
			}
			if(pauseBtn.test(event.getX(),event.getY())){
				ispause = true;
				movebtns(true);
			}
			
			break;
		}
		return super.onTouchEvent(event);
	}
	//移动按钮
	private void movebtns(boolean show){
		if(show){
			rePlay.Moveto(new AngleVector(width/2, height/2-((PopstartActivity)mActivity).menuui.btnbg.roHeight), 25);
			conBtn.Moveto(new AngleVector(width/2, height/2), 25);
			exitbtn.Moveto(new AngleVector(width/2, height/2+((PopstartActivity)mActivity).menuui.btnbg.roHeight), 25);
		}else{
			conBtn.Moveto(new AngleVector(width/2, -300), -25);
			exitbtn.Moveto(new AngleVector(width/2, -200), -25);
			rePlay.Moveto(new AngleVector(width/2,-300), -25);
		}
	}
	//检查选中
	private void checkStar(float ex,float ey){
		
		if(selected){
			int[] resxy = new int[2];
			resxy[0]=-1;
			resxy[1]=-1;
			for(int x=0;x<Bigstars.count();x++){
				int[] checked=((star)Bigstars.childAt(x)).test(ex,ey);
				if(checked[0]>=0){
					resxy[0]=checked[0];
					resxy[1]=checked[1];
				}
			}
			
			if(resxy[0]>=0){
				boolean flag=false;
				for(int i=0;i<res.size();i++){
					if(res.get(i)[0]==resxy[0]&&res.get(i)[1]==resxy[1]) flag=true;
				}
				if(flag){	
					//在已选中的点击
					for(int i=0;i<res.size();i++){
						for(int x=0;x<Bigstars.count();x++){
							try{
								((star)Bigstars.childAt(x)).setDie(res.get(i)[0], res.get(i)[1]);
								 starVal[res.get(i)[0]][res.get(i)[1]] =-1;
							}catch(Exception e){
								Log.v("log","x="+x );
							}
						}
					}
					
					int counts = res.size();
					myScore += counts*counts*5;
					myStr.add_num(counts*counts*5,5);
					//myStr.set(""+myScore);
					
					moveS.play();
					//现实提示信息
					if(counts>2){
						showTag(counts);
						//showCool.setmove(new AngleVector(width/2,height/2),new AngleVector(width/2,65+width/5+10), true);
					}
					//更新地图
					reSetmap();
					//判断是否还有可以消除的
					if(checkGameOver()){
						int count=getleftNum();
						//奖励
						if(count<10){
							myScore+=jiangli[count];							
							myStr.add_num(jiangli[count]);
							Toast.makeText(mActivity, "+"+jiangli[count],Toast.LENGTH_LONG).show();
						}
						
						if(myScore>TopScore){
							TopScore =myScore;
							TopStr.set(""+TopScore);
							tools.savetomem(tools.topscore, ""+TopScore,mActivity);
						}
						
						//游戏结束
						if(myScore<ToScore){
							gameOver=true;
							removestars=true;
							loseS.play();
						}else{
							removestars=true;
						}
					}
					//过关动画
					if(myScore>=ToScore && !isShowTag){
						guoguan.setmove(new AngleVector(width/2,height/2), new AngleVector(115*bili, 200*bili));
						isShowTag = true;
						winS.play();
					}
					res = new ArrayList<int[]>();
				}else{
					for(int i=0;i<res.size();i++){
						for(int x=0;x<Bigstars.count();x++){
							((star)Bigstars.childAt(x)).setApa(res.get(i)[0], res.get(i)[1], true);
						}
					}
					res = new ArrayList<int[]>();
					clickS.play();
				}
				selected=false;
				AddStr.mAlpha=0;
				recheck(ex,ey);
			}else{
					selected=false;
					for(int i=0;i<res.size();i++){
						for(int x=0;x<Bigstars.count();x++){
							((star)Bigstars.childAt(x)).setApa(res.get(i)[0], res.get(i)[1], true);
						}
					}
					AddStr.mAlpha=0;
					clickS.play();
			}
		}else{
			recheck(ex,ey);
			
		}
	}
	private void showTag(int count){
		int frame = ((PopstartActivity)mActivity).lagId;
		int index = 0;
		if(count>=3&&count<5){
			index =0;
		}else if(count>=5&&count<7){
			index =2;
		}else if(count>=7&&count<10){
			index =4;
		}else if(count>=10){
			index =6;
		}
		frame+=index;
		showCool.setFrame(frame);
		showCool.setmove(new AngleVector(width/2,height/2),new AngleVector(width/2,65+width/5+10), true);
		
	}
	//获取剩余数量
	private int getleftNum(){
		int count=0;
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(starVal[i][j]>=0) count++;
			}
		}
		return count;
	}
	double startTime;
	boolean removestars=false;
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if((System.currentTimeMillis()-startTime)>50){
			startTime=System.currentTimeMillis();
			if(removestars){
				int count = Bigstars.count();
				if(Bigstars.count()>0){
					int[] tempij = ((star)(Bigstars.childAt(count-1))).getXY();
					addObject(new Posstars(3, ((PopstartActivity)mActivity).menuui.smallstars, starVal[tempij[0]][tempij[1]], getVpos(tempij[0],tempij[1])));
					((star)(Bigstars.childAt(count-1))).setDie();
					removeS.play();
				}else{
					removestars=false;
					if(!gameOver){
						nextStep();
					}else{
						gameover();
					}
				}
			}
			
			if(replay){
				int count = Bigstars.count();
				if(Bigstars.count()>0){
					int[] tempij = ((star)(Bigstars.childAt(count-1))).getXY();
					addObject(new Posstars(3, ((PopstartActivity)mActivity).menuui.smallstars, starVal[tempij[0]][tempij[1]], getVpos(tempij[0],tempij[1])));
					((star)(Bigstars.childAt(count-1))).setDie();
					removeS.play();
				}else{
					replay=false;
					rePlaying();
				}
			}
			
			if(movedownid>=0){
				moveDown();
			}
			if(moveleftid>=0){
				moveLeft();
			}
			if(isshowlv){
				if(showlv.checkmove()){
					resetGame();
					isshowlv=false;
				}
			}
		}
	}
	//游戏结束
	private void gameover(){
		gameOver = true;
		movebtns(true);
	}
	//下一关
	private void nextStep(){
		//继续下一关
		//if(gameStep<6){
			ToScore += 1000+(gameStep*500);
		//}else{
		//	ToScore += 2000;
		//}
		
		gameStep++;
		stepStr.set(""+gameStep);
		ToStr.set("/"+ToScore);
		
		showlv.setmove(new AngleVector(width/2, 100), gameStep, ToScore);
		
		isshowlv=true;
	
	}
	//检查有几个可以消除--计算分数
	private void recheck(float ex,float ey){
		int[] resxy = new int[2];
		resxy[0]=-1;
		resxy[1]=-1;
		for(int x=0;x<Bigstars.count();x++){
			int[] checked=((star)Bigstars.childAt(x)).test(ex,ey);
			if(checked[0]>=0){
				resxy[0]=checked[0];
				resxy[1]=checked[1];
			}
		}
		clickBoard = new boolean[10][10];
		res = new ArrayList<int[]>();
		if(resxy[0]>=0){
			int count =clickOn(resxy[0],resxy[1],true);
			if(count>1){ 
				selected=true;	
				for(int i=0;i<res.size();i++){
					for(int x=0;x<Bigstars.count();x++){
						((star)Bigstars.childAt(x)).setApa(res.get(i)[0], res.get(i)[1], false);
					}
				}
				
				AddStr.set("+ "+(res.size()*res.size()*5));
				AddStr.mAlpha=1;
				
			}else{
				res = new ArrayList<int[]>();
			}
			clickS.play();
		}
		 
	}
	
	boolean selected = false;
	ArrayList<int[]> res = new ArrayList<int[]>();
	int nowcolor;
	//点击获取相同的星星
	private int clickOn(int i, int j, boolean ifChange)
	{
	    int color = starVal[i][j];
	    nowcolor = color;
	    clickBoard[i][j] = true;
	    int[] clk = {i,j};
	    res.add(clk);
	    int count = 1;
	    //检测左边
	    if(i > 0 && starVal[i - 1][j] == color && !clickBoard[i - 1][j])
	    {
	    	
	        count += clickOn(i - 1, j, ifChange);
	    }
	    //检测下面
	    if(j > 0 && starVal[i][j - 1] == color && !clickBoard[i][j - 1])
	    {
	        count += clickOn(i, j - 1, ifChange);
	    }
	    //检测右边
	    if(i < 9 && starVal[i + 1][j] == color && !clickBoard[i + 1][j])
	    {
	        count += clickOn(i + 1, j, ifChange);
	    }
	    //检测上面
	    if(j < 9 && starVal[i][j + 1] == color && !clickBoard[i][j + 1])
	    {
	        count += clickOn(i, j + 1, ifChange);
	    }
	    
	    return count;
	}
	//检测是否游戏结束
	private boolean checkGameOver(){
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				if(starVal[i][j]>=0){
					clickBoard = new boolean[10][10];
					int count=clickOn(i,j,true);
					
					if(count>1) return false;
				}
			}
		}
		return true;
	}
	//消除被选中的--播放动画
	private void reSetmap(){
		int star_count=res.size();
		int play_count = 15;
		if(star_count<4){
			play_count=30;
		}else if(star_count>3&&star_count<6){
			play_count=25;
		}else if(star_count>5&&star_count<8){
			play_count=23;
		}else if(star_count>7&&star_count<10){
			play_count=20;
		}
		for(int i=0;i<star_count;i++){
			int[] temp=res.get(i);
			addObject(new Posstars(play_count , ((PopstartActivity)mActivity).menuui.smallstars, nowcolor, getVpos(temp[0],temp[1])));
		}
		movedownid=10;
	}
	
	int movedownid=-1,moveleftid=-1;
	//下移
	private void moveDown(){
		//从上往下掉
		for(int i=9;i>0;i--){
			for(int j=0;j<10;j++){
				if(starVal[i][j]>=0&&starVal[i-1][j]==-1){
					int temp=starVal[i][j];
					starVal[i-1][j]=temp;
					starVal[i][j]=-1;
					int indexId = return_starid(i,j);
					if(indexId>=0){
						Log.v("log","index="+indexId);
						AngleVector to =getVpos(i-1,j);
						((star)Bigstars.childAt(indexId)).Moveto(i-1, j, to);
					}
				}
			}
		}
		movedownid--;
		if(movedownid==0){
			moveleftid = 10;
		}
	}
	//从右往左靠拢
	private void moveLeft(){
		for(int j=0;j<9;j++){
			boolean flag=true;
			for(int i=0;i<10;i++){
				if(starVal[i][j]>=0) flag=false;
			}
			if(flag){
				for(int x=0;x<10;x++){
					int y=j+1;
					if(starVal[x][y]>=0){
						int temp=starVal[x][y];
						starVal[x][j]=temp;
						starVal[x][y]=-1;
						int indexid=return_starid(x,y);
						if(indexid>=0){
							AngleVector to =getVpos(x,j);
							((star)Bigstars.childAt(indexid)).Moveto(x, j, to);
						}
					}
				}
			}
		}
		moveleftid--;
	}
	//获取 星星id值
	private int return_starid(int x,int y){
		int res = -1;
		int count = Bigstars.count();
		for(int n=0;n<count;n++){
			try{
				if(((star)Bigstars.childAt(n)).check(x,y)){
					res = n;
				}
			}catch(Exception e){
				Log.v("log","n="+n);
			}
		}
		return res;
	}
	
	//获取坐标x,y
	private AngleVector getVpos(int x,int y){
		AngleVector temp = new AngleVector();
		if(x>=0&&y>=0&&x<10&&y<10){
			temp.set(pos[x][y]);
		}
		//Log.v("log","x,y="+temp.mX+","+temp.mY);
		return temp;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(!gameOver){
				ispause = true;
				movebtns(true);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
