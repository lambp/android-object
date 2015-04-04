package game.ui;

import game.data.Aichupai;
import game.data.paidata;
import game.data.paival;
import game.data.soundlist;
import game.data.tools;
import game.qipai.bigtwo.v2.R;
import game.qipai.bigtwo.v2.mainActivity;
import game.sprite.BtnSprite;
import game.sprite.Mouse;
import game.sprite.ShowWin;
import game.sprite.pais;
import game.sprite.showTag;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleFont;
import com.android.angle.AngleMusic;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;

public class GameUI extends AngleUI{
	AngleSurfaceView sview;
	//GAMESTEP1-游戏开始,2-游戏结束,3-清除所有牌,4-退出                     type 游戏类型  1,2 ==> 3人场,3,4==》四人场                         
	public int width,height,NowPlay,GAMESTEP=0,myscore=0,wd,hd,type=1,time, ShowCount=0,DONGHUASTEP=0;
	float bili_2;
	AnglePhysicsEngine bgitems,paiimgs,topitems;
	AngleSpriteLayout pai,btns_lay,title_lay,headers_lay;
	Random rand = new Random();
	paidata pdata;
	Mouse mouse;
	BtnSprite chupaiBtn,passBtn,startBtn,endtitle,tishibtn,exit_btn;
	public AngleString score;
	AngleMusic sounds,fapaisd,startsound;
	int[] play_1,play_2,play_3,play_4;
	double start,start2;
	AngleFont font;			//字体
	ShowWin showend;		//结算面板
	public boolean playsound=true;
	ArrayList<paival> lastPai = new ArrayList<paival>();  
	ArrayList<ArrayList<paival>> tishiPai = new ArrayList<ArrayList<paival>>();  
	boolean[] passplay;
	BtnSprite header_1,header_2,header_3,header_4;
	int dscore=10,TIMEOUT=5;
	public void setType(int _type){
		type=_type;
		if(type<3){
			header_4.sethidden(true);
			if(1==type){dscore=10;}else{dscore=50;}
		}else{
			header_4.sethidden(false);
			if(3==type){dscore=10;}else{dscore=50;}
		}
	}
	
	public GameUI(AngleActivity activity) {
		 super(activity);
		 width = mActivity.mGLSurfaceView.roWidth;
		 height = mActivity.mGLSurfaceView.roHeight;
		 sview = mActivity.mGLSurfaceView;
		 bili_2 = (float)width/(float)1024;
		 wd = width/25;
		 hd = height/30;
		 if(wd>(int)(40*bili_2)){
			 wd = (int)(40*bili_2);
		 }
			
		 pdata = new paidata();
		 
		 init();
		 
		 
		 
	}
	//初始化资源
	private void init(){
		//游戏背景
		 addObject(new AngleSprite(width/2, height/2, new AngleSpriteLayout(sview, width, height, R.drawable.gamebg, 0, 0, 1024, 688)));
		 
		 bgitems = new AnglePhysicsEngine(50);
		 addObject(bgitems);
		 
		 paiimgs = new AnglePhysicsEngine(55);
		 addObject(paiimgs);
		 
		 topitems = new AnglePhysicsEngine(55);
		 addObject(topitems);
		 
		 mouse = new Mouse(new AngleSpriteLayout(sview, 10,10, R.drawable.pai, 0, 0, 10, 10));
		 
		 paiimgs.addObject(mouse);
		 
		 sounds = new AngleMusic(mActivity);
		 
		 fapaisd = new AngleMusic(mActivity,R.raw.fapai);
		 startsound = new AngleMusic(mActivity);
		 
		 font =((mainActivity)mActivity).menuui.font;
		 score = new AngleString(font,"100",(int)(56*3*bili_2),(int)(height-76*1.5f*bili_2) ,AngleString.aCenter);
	
		
		//游戏背景
		//bgitems.addObject(new AngleSprite(width/2, height/2, new AngleSpriteLayout(sview, width, height, R.drawable.gamebg, 0, 0, 1024, 688)));
		//按钮
		btns_lay = new AngleSpriteLayout(sview, (int)(161*bili_2),(int)(bili_2*68), R.drawable.titles, 0, 479, 161, 68, 6, 2);
		title_lay = new AngleSpriteLayout(sview, (int)(341*bili_2),(int)(bili_2*88), R.drawable.titles, 0, 0, 341, 88, 18, 3);
		headers_lay = ((mainActivity)mActivity).menuui.header_lay;
		
		header_1 = new BtnSprite(headers_lay,new AngleVector((int)(56*3*bili_2),height-76*2.5f*bili_2),rand.nextInt(1000)%12);
		header_2 = new BtnSprite(headers_lay,new AngleVector((int)(56*3*bili_2), height/4), rand.nextInt(1000)%12);
		header_3 = new BtnSprite(headers_lay,new AngleVector(width-(int)(56*3*bili_2), height/4),rand.nextInt(1000)%12);
		header_4 = new BtnSprite(headers_lay,new AngleVector(width/2, height/4),rand.nextInt(1000)%12);
		exit_btn = new BtnSprite(((mainActivity)mActivity).menuui.top_layout, new AngleVector((int)(47*bili_2), (int)(47*bili_2)), 3);
		paiimgs.addObject(exit_btn);
		paiimgs.addObject(header_1);
		paiimgs.addObject(header_2);
		paiimgs.addObject(header_3);
		paiimgs.addObject(header_4);
		
		chupaiBtn = new BtnSprite(btns_lay,new AngleVector(width/2+(int)(161*bili_2), height*2/3),3);
		passBtn= new BtnSprite(btns_lay,new AngleVector(width/2-(int)(161*bili_2), height*2/3),4);

		startBtn = new BtnSprite(btns_lay,new AngleVector(width/2, height/2),0);
		
		tishibtn = new BtnSprite(btns_lay,new AngleVector(width/2+2*(int)(161*bili_2),  height*2/3),2);
		
		bgitems.addObject(chupaiBtn);
		bgitems.addObject(passBtn);
		bgitems.addObject(tishibtn);
		
		passBtn.sethidden(true);
		chupaiBtn.sethidden(true);
		tishibtn.sethidden(true);
		
		bgitems.addObject(startBtn);
		
		
		pai = new AngleSpriteLayout(sview, (int)(56*1.5*bili_2),(int)(76*1.5*bili_2), R.drawable.pai, 0, 0, 56, 76, 54, 9);
	
		showend = new ShowWin(new AngleSpriteLayout(sview, (int)(685*bili_2),(int)(335*bili_2), R.drawable.titles, 340, 351, 685, 335),btns_lay,new AngleVector(width, height),font);
		
		topitems.addObject(showend);
		topitems.addObject(score);
		
		endtitle =new BtnSprite(title_lay,new AngleVector(width/2, height/2-(int)(335*bili_2)/2),11);
		
		topitems.addObject(endtitle);
		endtitle.sethidden(true);
		
		updateScore(0);
		
		
	}
	void start_end(int type){
		if(type==1){
			startsound.load(R.raw.start);
			startsound.play();
		}else if(type==2){
			startsound.load(R.raw.lose);
			startsound.play();
		}else if(type==3){
			startsound.load(R.raw.win);
			startsound.play();
		}
	}
	//发牌
	private void fapai(){
		 Log.v("log","type="+type);
		 exit_btn.sethidden(true);
		 endtitle.sethidden(true);
		 pdata.xipai();
		if(type<3){
		    	//3人场
				play_3 = pdata.getPlaypai(2,type);
				play_1 = pdata.getPlaypai(0,type);
				play_2 = pdata.getPlaypai(1,type);
				if(play_1.length==18) {
					NowPlay=0;
					hiddenBtn(false,true);
				}else{
					hiddenBtn(true,true);
				}
				if(play_2.length==18) NowPlay=1;
				if(play_3.length==18) NowPlay=2;
				passplay = new boolean[3];
		}else{	
				//4人场
				 play_3 = pdata.getPlaypai(2,type);
				 play_4 = pdata.getPlaypai(3,type);
				 play_1 = pdata.getPlaypai(0,type);
				 play_2 = pdata.getPlaypai(1,type);
				 Log.v("log","p4="+play_4.length);
				if(hasThree(play_1)) {
					NowPlay=0;
					hiddenBtn(false,true);
				}else{
					hiddenBtn(true,true);
				}
				if(hasThree(play_2)) NowPlay=1;
				
				if(hasThree(play_3)) NowPlay=2;
				
				if(hasThree(play_4)) NowPlay=3;
				
				passplay = new boolean[4];		
		}
		
		time=TIMEOUT;
		lastPai = new ArrayList<paival>();
		GAMESTEP=0;
		ShowCount=0;
		DONGHUASTEP=1;
		//playfapaisound();
		
	}
	//是否有梅花三
	boolean hasThree(int[] ppai){
		boolean res = false;
		for(int  i=0;i<ppai.length;i++){
			if(ppai[i]==3) res=true;
		}
		return res;
	}
	
	boolean hasThree(int playid){
		boolean res = false;
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==playid&&!((pais)paiimgs.childAt(i)).out){
				paival temp = ((pais)paiimgs.childAt(i)).getvals();
				if(temp.val==3&&temp.huase==0) res=true;
			}
		}
		return res;
	}
	
	//pass音效
	void passsound(){
		 sounds.load(R.raw.pass);
		 sounds.play();
	}
	
	//牌音效
	void playsound(int px,ArrayList<paival> putapi){
		int index=0;
		
		switch(px){
			case 1:
				index=(putapi.get(0)).value;
				sounds.load(soundlist.dangpailist[index]);
			    sounds.play();
			break;
			case 2:
				index=(putapi.get(0)).value;
				sounds.load(soundlist.dduipailist[index%13]);
				sounds.play();
			break;
			case 3:
			    index=Aichupai.getSunindex(putapi);
			    if(index>=0){
			    	sounds.load(soundlist.sunpailist[index]);
			    	sounds.play();
			    }
			break;
			case TITLE_HULU:
				index=Aichupai.getHulusound(putapi,px);
				sounds.load(soundlist.hulupailist[index]);
		    	sounds.play();
				break;
			case TITLE_TIEZI:
				index=Aichupai.getHulusound(putapi,px);
				sounds.load(soundlist.tiepailist[index]);
		    	sounds.play();
			break;
			case TITLE_THUASHUN:
				index=Aichupai.getSunindex(putapi);
				if(index>=0){
					sounds.load(soundlist.tsunpailist[index]);
			    	sounds.play();
				}
			break;
			
		}
	}
	//保存积分
	void updateScore(int num){
		if(num==0){
			String val = tools.getstrformmem(tools.score,mActivity);
			if(val.equals("null")){
				myscore = 500;
				tools.savetomem(tools.score,""+myscore, mActivity);
				score.set(""+myscore);
			}else{
				myscore = Integer.parseInt(val);
				score.set(""+myscore);
			}
		}else{
			myscore +=num;
			score.set(""+myscore);
			tools.savetomem(tools.score,""+myscore, mActivity);
		}
	}
	


	final int TITLE_PASS=1,TITLE_PX=2,TITLE_NOBIG=3,
			TITLE_SELECTED=4,TITLE_HAS3=5,TITLE_GAMEOVER=6,
			TITLE_MAXBIG=7,TITLE_SHUNZI=3,TITLE_TONGHUA=4,TITLE_HULU=5,TITLE_TIEZI=6,TITLE_THUASHUN=7;
	//显示提示信息
	private void showTitle(int type,int where){
		switch(type){
		case TITLE_PASS:
			topitems.addObject(new showTag(title_lay, getWhere(where), 0, true));
			break;
		case TITLE_PX:
			topitems.addObject(new showTag(title_lay, getWhere(where), 1, true));
			break;
		case TITLE_NOBIG:
			topitems.addObject(new showTag(title_lay, getWhere(where), 2, true));
			break;
		case TITLE_SELECTED:
			topitems.addObject(new showTag(title_lay, getWhere(where),5, true));
			break;
		case TITLE_HAS3:
			topitems.addObject(new showTag(title_lay, getWhere(where), 9, true));
			break;
		case TITLE_GAMEOVER:
			topitems.addObject(new showTag(title_lay, getWhere(where),11, true));
			break;
		case TITLE_MAXBIG:
			topitems.addObject(new showTag(title_lay, getWhere(where),10, true));
			break;
		}
	}
	
	//牌型显示
	void showPaix(int px){
		int where =NowPlay;
		switch(px){
		case TITLE_SHUNZI:
			topitems.addObject(new showTag(title_lay, getWhere(where),6, true));
			break;
		case TITLE_TONGHUA:
			topitems.addObject(new showTag(title_lay, getWhere(where),5, true));
			break;
		case TITLE_HULU:
			topitems.addObject(new showTag(title_lay, getWhere(where),8, true));
			break;
		case TITLE_TIEZI:
			topitems.addObject(new showTag(title_lay, getWhere(where),7, true));
			break;
		case TITLE_THUASHUN:
			topitems.addObject(new showTag(title_lay, getWhere(where),3, true));
			break;
		}
	}
	
	AngleVector getWhere(int where){
		AngleVector pos = new AngleVector();
		if(type<3){
			switch(where){
			case 0:
				pos.set(width/2, height/2);
				break;
			case 1:
				pos.set(width*3/4, height/3);
				break;
			case 2:
				pos.set(width/4, height/3);
				
				break;
			}
		}else{
			switch(where){
			case 0:
				pos.set(width/2, height*3/4);
				break;
			case 1:
				pos.set(width*3/4, height/2);
				break;
			case 2:
				pos.set(width/2, height/4);
				break;
			case 3:
				pos.set(width/4, height/2);	
				break;
			}
		}
		return pos;
	}
	
		   //发牌动画控制
	       
			void showmovie(){
				if(type<3 && DONGHUASTEP==1){
					if(ShowCount<17 ){
						//发牌动画
					//Log.v("log","ShowCount="+ShowCount);
						paiimgs.addObject(new pais(pai,play_3[ShowCount]-1,new AngleVector(pai.roWidth/2, height/3+(ShowCount-play_3.length/2)*hd),2));
						paiimgs.addObject(new pais(pai,play_1[ShowCount]-1,new AngleVector(width/2+(ShowCount-play_1.length/2)*wd, height-(int)(76*bili_2)/2),0));
						paiimgs.addObject(new pais(pai,play_2[ShowCount]-1,new AngleVector(width-pai.roWidth/2, height/3+(ShowCount-play_2.length/2)*hd),1));
						if(ShowCount==16){
							if(NowPlay==0){
								paiimgs.addObject(new pais(pai,play_1[ShowCount+1]-1,new AngleVector(width/2+(17-play_1.length/2)*wd, height-(int)(76*bili_2)/2),0));	
							}else if(NowPlay==1){
								paiimgs.addObject(new pais(pai,play_2[ShowCount+1]-1,new AngleVector(width-pai.roWidth/2, height/3+(17-play_2.length/2)*hd),1));
							}else{
								paiimgs.addObject(new pais(pai,play_3[ShowCount+1]-1,new AngleVector(pai.roWidth/2, height/3+(17-play_3.length/2)*hd),2));
							}
						}
					}else if(ShowCount==17){
						//发牌结束
						DONGHUASTEP=0;
						GAMESTEP=1;
					}
					ShowCount++;
				}else if(type>2 && DONGHUASTEP==1){
					Log.v("log","ShowCount="+ShowCount);
					if(ShowCount<13 ){
						//发牌动画
						paiimgs.addObject(new pais(pai,play_3[ShowCount]-1,new AngleVector(width/2+(ShowCount-play_3.length/2)*wd, 0),2));
						paiimgs.addObject(new pais(pai,play_4[ShowCount]-1,new AngleVector(pai.roWidth/2, height/3+(ShowCount-play_4.length/2)*hd),3));
						paiimgs.addObject(new pais(pai,play_1[ShowCount]-1,new AngleVector(width/2+(ShowCount-play_1.length/2)*wd, height-(int)(76*bili_2)/2),0));
						paiimgs.addObject(new pais(pai,play_2[ShowCount]-1,new AngleVector(width-pai.roWidth/2, height/3+(ShowCount-play_2.length/2)*hd),1));
					}else if(ShowCount==13&& DONGHUASTEP==1){
						//发牌结束
						DONGHUASTEP=0;
						GAMESTEP=1;
						time=TIMEOUT;
					}
					ShowCount++;
				}
			}
			
			//判断是否发牌音效
			void playfapaisound(){
				if(playsound&&DONGHUASTEP==1){
					fapaisd.play();
				}
			}

				
	
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		if((System.currentTimeMillis()-start2)>120){
			start2 = System.currentTimeMillis();
			showmovie();
		}
		
		if((System.currentTimeMillis()-start)>150){
				start = System.currentTimeMillis();
				
			    if(NowPlay>0&&time<0&&GAMESTEP==1){
				   AI();
				}
			    
			    time--;
				if(GAMESTEP==3){
					//游戏结束--清除-发牌
					int count = 0;
					int ind = 0;
					for(int i=paiimgs.count()-1;i>=0;i--){
						if((paiimgs.childAt(i) instanceof pais)){
							count++;
							ind = i;
						}
					}
					if(count>0){
						(paiimgs.childAt(ind)).mDie = true;
					}else{
						GAMESTEP =0;
						
						fapai();
					}
				}
				
				if(GAMESTEP==4){
					
					int count = 0;
					int ind = 0;
					for(int i=paiimgs.count()-1;i>=0;i--){
						if((paiimgs.childAt(i) instanceof pais)){
							count++;
							ind = i;
						}
					}
					if(count>0){
						(paiimgs.childAt(ind)).mDie = true;
					}else{
						
						GAMESTEP =5;
						showend.show(false, 0);
					}
				}
				
				if(GAMESTEP==5){
					
					GAMESTEP =0;
					fapai();
				}
		}
		
		super.step(secondsElapsed);
	}
	
	private void AI(){
		ArrayList<paival> slist = new ArrayList<paival>();
		//获取当前玩家的牌
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==NowPlay&&!((pais)paiimgs.childAt(i)).out){
				paival temp = ((pais)paiimgs.childAt(i)).getvals();
				temp.index = i;
				slist.add(temp);
			}
		}
		
		float wx=0,hx=0;
		if(type<3){
			if(NowPlay==1){
				wx = width*3/4;
			}else if(NowPlay==2){
				wx =  width/4;
			}
		}else{
			if(NowPlay==1){
				wx = width*3/4;
				hx = height/2;
			}else if(NowPlay==2){
				wx =  width/2;
				hx = height/4;
			}else if(NowPlay==3){
				wx =  width/4;
				hx = height/2;
			}
		}
		
		Log.v("log","paisize="+slist.size());
		Log.v("log","nowpaly="+NowPlay);
		
		ArrayList<paival> putapi = getAichupai(slist);
		
		String AIpi = "AI:"+NowPlay+"=";
	
		for(int i=putapi.size()-1;i>=0;i--){
			paival is = putapi.get(i);
			((pais)paiimgs.childAt(is.index)).setfx(0.8f);
			if(type<3){
				((pais)paiimgs.childAt(is.index)).moveto(new AngleVector(wx+(i-putapi.size()/2)*wd*0.8f,height/2));
			}else{
				((pais)paiimgs.childAt(is.index)).moveto(new AngleVector(wx+(i-putapi.size()/2)*wd*0.8f,hx));
			}
			
			AIpi += is.val+",";
		}
		
		Log.v("log",AIpi);
		
		if(putapi.size()>0){
			lastPai =  new ArrayList<paival>();
			lastPai.addAll(putapi);
			int px = Aichupai.returnpx(putapi);
			showPaix(px);
			playsound(px,putapi);
		}else{
			passsound();
			passplay[NowPlay]=true;
			showTitle(TITLE_PASS,NowPlay);
		}
		nextPlay();
	}	

	//AI出牌
	private ArrayList<paival> getAichupai(ArrayList<paival> all){
		ArrayList<paival> list = new ArrayList<paival>();
		if(type<3){
			
			if(hasThree(NowPlay)||ispass()){
			   list = Aichupai.first(all,getPainum((NowPlay+1)%3));
			   passplay = new boolean[3];
			}else{
			   list = Aichupai.genpai(all,lastPai,getPainum((NowPlay+1)%3));
			}
		}else{
			if(hasThree(NowPlay)||ispass()){
			   list = Aichupai.first(all,getPainum((NowPlay+1)%4));
			   passplay = new boolean[4];
			}else{
			   list = Aichupai.genpai(all,lastPai,getPainum((NowPlay+1)%4));
			}
		}
		return list;
	}

	
	boolean ispass(){
		int count=0;
		for(int i=0;i<passplay.length;i++){
			if(passplay[i]) count++;
		}
		
		if((count>1 &&type<3)||(count>2 && type>2)){
			
			return true;
		}else{
			return false;
		}
		
	}
	
	int listnum=0;   //提示牌序号
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				 if(GAMESTEP==1){
					 //正在打牌
					 mouse.mPosition.set(event.getX(), event.getY());
					 test();
					 if(chupaiBtn.test(event.getX(), event.getY())){
						 chupai();
						 
						// ((mainActivity)mActivity).openadmain();
					 }
					 if(passBtn.test(event.getX(), event.getY())&&!ispass()&&((Mycount()!=13&&type>2)||(Mycount()!=18&&type<3))){
						 passsound();
						 hiddenBtn(true,true);
						 showTitle(TITLE_PASS,NowPlay);
						 passplay[0]=true;
						 nextPlay();
					 }
					 if(tishibtn.test(event.getX(), event.getY())){
						 tishibtn.mAlpha=0.7f;
					 }
				 }
				 
				 if(GAMESTEP==2){
					 //游戏结算阶段-显示结算面板
					 int end = showend.test(event.getX(), event.getY());
					 if(end==1){
						 //重新开始游戏
						 GAMESTEP=3; 
						 showend.show(false, 0);
						 ((mainActivity)mActivity).openadmain();
					 }
					 if(end==2){
						//返回大厅
						 GAMESTEP = 4;
						 ((mainActivity)mActivity).setUI(((mainActivity)mActivity).menuui);
						 ((mainActivity)mActivity).openadmain();
					 }
					 
				 }
				break;
			case MotionEvent.ACTION_MOVE:
				
				break;
			case MotionEvent.ACTION_UP:
				
				if(startBtn.test(event.getX(), event.getY())){
					startBtn.sethidden(true);
					fapai();
					start_end(1);
				}
				
				if(header_1.test(event.getX(), event.getY())){
					((mainActivity)mActivity).openadmain();
				}
				
				 if(tishibtn.test(event.getX(), event.getY())){
					 Log.v("log", "提示"+tishiPai.size());
					 tishibtn.mAlpha=1;
				     if(tishiPai.size()==0){
				    	 showTitle(TITLE_NOBIG,NowPlay);
				    	 
				    	 passsound();
						 hiddenBtn(true,true);						
						 passplay[0]=true;
						 nextPlay();
						 
				     }else{
				    	 getlectpai();
				     }
				 }
				 if(exit_btn.test(event.getX(), event.getY())){
						((mainActivity)mActivity).ExitCheck();
				 }
				break;
		}
		
		return super.onTouchEvent(event);
	}
	
	//按钮控制
	private void hiddenBtn(boolean show,boolean showpass){
		if(showpass){
			chupaiBtn.mPosition.set(width/2,height*2/3);
		}else{
			chupaiBtn.mPosition.set(width/2+(int)(80*bili_2)*2/3,height*2/3);
		}
		passBtn.sethidden(showpass);
		chupaiBtn.sethidden(show);
		tishibtn.sethidden(showpass);
		
	}
	//检查选牌 
	private int test(){
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).isme()&&(mouse.collide((pais)paiimgs.childAt(i)))&&!((pais)paiimgs.childAt(i)).out){
				((pais)paiimgs.childAt(i)).setSel(bili_2);
				return 1;
			}
		}
		return 0;
	}
	ArrayList<paival> oldlist = new ArrayList<paival>();
	//玩家出牌
	private void chupai(){
		
		ArrayList<paival> slist = new ArrayList<paival>();
		//获取已选择牌
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).isme()&&((pais)paiimgs.childAt(i)).isselected()&&!((pais)paiimgs.childAt(i)).out){
				paival temp = ((pais)paiimgs.childAt(i)).getvals();
				temp.index = i;
				slist.add(temp);
			}
		}
		
		int count = slist.size();
		if(count>0){
			if(fuhepx(slist)){
				
				if((Mycount()==18&&type<3)||(hasThree(0))){
				
					//梅花3第一次出牌
					if(isfirstOk(slist)){
						pchupai(slist);
					}else{
					  //第一次必须出梅花3
						
						showTitle(TITLE_HAS3,0);
					}
					
				}else{
					Log.v("log","second");
						if(count==1&&getPainum(1)==1){
							if(getmyBiggest(slist.get(0))){
								if(ispass()){
									  pchupai(slist);
									  if(type<3) {
											passplay = new boolean[3];
										}else{
											passplay = new boolean[4];
										}
								}else{
									if(Aichupai.returnBig(slist,lastPai)){
										pchupai(slist);
									}else{
										Log.v("log","不能大过上一家");
										showTitle(TITLE_NOBIG,0);
									}
								}
							}else{
								Log.v("log","必须选择最大的牌");
								showTitle(TITLE_MAXBIG,0);
							}
						}else{
							if(ispass()){
								pchupai(slist);
								if(type<3) {
										passplay = new boolean[3];
									}else{
										passplay = new boolean[4];
									}
							}else{
								if(Aichupai.returnBig(slist,lastPai)){
									pchupai(slist);
								}else{
									Log.v("log","不能大过上一家");
									showTitle(TITLE_NOBIG,0);
								}
							}
						}
				}
			}else{
				//牌型不对
				Log.v("log","牌型不对");
				showTitle(TITLE_PX,0);
			}
		}else{
			Log.v("log","选择要出的牌");
			showTitle(TITLE_SELECTED,0);
		}
	
	}
	
	//是否为手牌中最大的
	private boolean getmyBiggest(paival pai){
		boolean res = true;
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==0&&!((pais)paiimgs.childAt(i)).out){
				if(((pais)paiimgs.childAt(i)).value>pai.val ||(((pais)paiimgs.childAt(i)).value==pai.val&&((pais)paiimgs.childAt(i)).huase>pai.huase)){
					res = false;
				}
			}
		}
		
		return res;
	}
	
	//玩家出牌动画
	private void pchupai(ArrayList<paival> pais){
		int count = pais.size();
		String log="";
		for(int i=0;i<count;i++){
			paival tp = pais.get(i);
			((pais)paiimgs.childAt(tp.index)).setfx(0.8f);
			((pais)paiimgs.childAt(tp.index)).moveto(new AngleVector(width/2+(count/2-i)*wd*0.8f,height/2));
			log+=tp.val+",";
		}
		Log.v("log","me="+log);
		if(pais.size()>0){
			lastPai  = new ArrayList<paival>();
			lastPai = pais;
			int px = Aichupai.returnpx(pais);
			showPaix(px);
			playsound(px,pais);
		}
		hiddenBtn(true,true);
		nextPlay();
	}
	//获取没出的牌
	private int getPainum(int playid){
		int count = 0;
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==playid&&!((pais)paiimgs.childAt(i)).out){
				count++;
			}
		}
		return count;
	}
	//检查是否游戏结束
	public boolean isgameOver(){
		int count = 0;
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==NowPlay&&!((pais)paiimgs.childAt(i)).out){
				count++;
			}
		}
		if(count>0) return false;
		return true;
	} 
	//获取手牌
	private ArrayList<paival> getmypai(){
		ArrayList<paival> myPai = new ArrayList<paival>(); 
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==0&&!((pais)paiimgs.childAt(i)).out){
				myPai.add(((pais)paiimgs.childAt(i)).getvals());
			}
		}
		return myPai;
	}
	
	//下一家出牌
	private void nextPlay(){
		
			
		if(!isgameOver()){
			removepai();
			
			NowPlay++;
			if(type<3){
				NowPlay%=3;
			}else{
				NowPlay%=4;
			}
		
			passplay[NowPlay]=false;
			
			movepai();
			time=TIMEOUT;
			
			if(NowPlay==0) {
				if(ispass()) {
					hiddenBtn(false,true);
				}else{
					hiddenBtn(false,false);
					//获取提示牌组
				
					 tishiPai = new ArrayList<ArrayList<paival>>();
					 tishiPai = Aichupai.Play_tishi(getmypai(),lastPai);
					 listnum=tishiPai.size()-1;
				}
			}
		}else{
			Log.v("log","游戏结束");
			
			
			
			 exit_btn.sethidden(false);
			 endtitle.sethidden(false);
			
			 GAMESTEP=2;
			
			int[] scores = jieshuan(); 		
			
			if(scores[0]>0){
				endtitle.setFrame(11);
			}else{
				endtitle.setFrame(12);
			}
			showend.show(true,scores[0]);
			
			
			
		}
		
	}
	
	//结算
	int[] jieshuan(){
		int[] scores = new int[4];
		int num_0 =  getPainum(0);
		int num_1 = getPainum(1);
		int num_2 = getPainum(2);
		int num_4 = 0;
		if(type>2) num_4= getPainum(3);
		
		if(num_0>0){
			if((type>2&&num_0==13) || (type<3&&num_0==17)){
				scores[0] = -1*num_0*dscore*2;
			}else{
				scores[0] = -1*num_0*dscore;
			}
		}else{
			if((type>2&&num_1==13) || (type<3&&num_1==17)){
				num_1*=2;
			}
			if((type>2&&num_2==13) || (type<3&&num_2==17)){
				num_2*=2;
			}
			if((type>2&&num_4==13) || (type<3&&num_4==17)){
				num_4*=2;
			}
			scores[0] = (num_1+num_2+num_4)*dscore;
		}
		
		if(num_1>0){
			scores[1] = -1*num_1*dscore;
		}else{
			scores[1] = (num_0+num_2+num_4)*dscore;
		}
		if(num_2>0){
			scores[2] = -1*num_2*10;
		}else{
			scores[2] = (num_1+num_0+num_4)*dscore;
		}
		if(scores[0]>0){
			start_end(3);
		}else{
			start_end(2);
		}
		
		updateScore(scores[0]);
		return scores;
	}
	
	
	
	private boolean isfirstOk(ArrayList<paival> pais){
		boolean res = false;
		for(int i=0;i<pais.size();i++){
			if((pais.get(i)).val==3&&(pais.get(i)).huase==0){
				res = true;
			}
		}
		
		return res;
	}
	
	//符合牌型
	private boolean fuhepx(ArrayList<paival> pais){
		boolean res=false;
	
		int num = pais.size();
		Log.v("log","snum="+num);
		if(num==1){
			res = true;
		}else if(num==2){
			if((pais.get(0)).val==(pais.get(1)).val){
				res = true;
			}
		}else if(num==3){
			if((pais.get(0)).val==(pais.get(1)).val&&(pais.get(1)).val==(pais.get(2)).val){
				if(Mycount()==3) res = true;
			}
		}else if(num==4){
			if((pais.get(0)).val==(pais.get(1)).val&&(pais.get(1)).val==(pais.get(2)).val&&(pais.get(2)).val==(pais.get(3)).val&&(pais.get(4)).val==(pais.get(3)).val){
				if(Mycount()==4) res = true;
			}
		}else if(num==5){
			res = fuhe(pais);
		}
		
		return res;
	}
	
	//是否符合牌型
	private boolean fuhe(ArrayList<paival> pais){
		return Aichupai.ishulu(pais)||Aichupai.issun(pais)||Aichupai.istonghua(pais)||Aichupai.istiezi(pais);
	}
	
	
	//我的牌数
	private int Mycount(){
		int count=0;
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).isme()&&!((pais)paiimgs.childAt(i)).out){
				count++;
			}
		}
		return count;
	}
	
	//去掉打出去的牌
	private void movepai(){
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==NowPlay&&((pais)paiimgs.childAt(i)).out){
			 ((pais)paiimgs.childAt(i)).mDie=true;
			}
		}
	}
	//取消选择的牌
	void resetmypai(){
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==NowPlay&&!((pais)paiimgs.childAt(i)).out){
				if(((pais)paiimgs.childAt(i)).selected){
					((pais)paiimgs.childAt(i)).setSel(bili_2);
				}
				
			}
		}
	}
	void getlectpai(){
		resetmypai();
		
		ArrayList<paival> list = tishiPai.get(listnum);
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==0&&!((pais)paiimgs.childAt(i)).out){
				paival p = ((pais)paiimgs.childAt(i)).getvals();
				for(int j=0;j<list.size();j++){
					if(p.val==list.get(j).val&&list.get(j).huase==p.huase){
						if(!((pais)paiimgs.childAt(i)).selected){
							((pais)paiimgs.childAt(i)).setSel(bili_2);
						}
					}
				}
				
			}
		}
		listnum--;
		if(listnum<0){
			listnum=tishiPai.size()-1;
		}
	}
	//重新设置牌位置
	private void removepai(){
		
		ArrayList<paival> slist = new ArrayList<paival>();
		//获取已选择牌
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==NowPlay&&!((pais)paiimgs.childAt(i)).out){
				paival temp = ((pais)paiimgs.childAt(i)).getvals();
				temp.index = i;
				slist.add(temp);
			}
		}
		
		int count = slist.size();
		
		if(type<3){
			//3人场
			switch(NowPlay){
				case 0:
					for(int i=count-1;i>=0;i--){
						paival tp = slist.get(i);
						((pais)paiimgs.childAt(tp.index)).selected = false;
						((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(width/2-(i-count/2)*wd, height-(76*bili_2)/2));
					}
					break;
				case 1:
					for(int i=count-1;i>=0;i--){
						paival tp = slist.get(i);
						((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(width-pai.roWidth/2,height/3-(i-count/2)*hd));
					}
					break;
				case 2:
					for(int i=count-1;i>=0;i--){
						paival tp = slist.get(i);
						((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(pai.roWidth/2,height/3-(i-count/2)*hd));
					}
					break;
			}
		}else{
			//4人场
			switch(NowPlay){
			case 0:
				for(int i=count-1;i>=0;i--){
					paival tp = slist.get(i);
					((pais)paiimgs.childAt(tp.index)).selected = false;
					((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(width/2-(i-count/2)*wd, height-(76*bili_2)/2));
				}
				break;
			case 1:
				for(int i=count-1;i>=0;i--){
					paival tp = slist.get(i);
					((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(width-pai.roWidth/2,height/3-(i-count/2)*hd));
				}
				break;
			case 2:
				for(int i=count-1;i>=0;i--){
					paival tp = slist.get(i);
					((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(width/2-(i-count/2)*wd, 0));
				}
				break;
			case 3:
				for(int i=count-1;i>=0;i--){
					paival tp = slist.get(i);
					((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(pai.roWidth/2,height/3-(i-count/2)*hd));
				}
				break;
			}
		}
	}
	
}
