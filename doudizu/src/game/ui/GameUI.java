package game.ui;

import game.data.Aichupai;
import game.data.Tishidata;
import game.data.paidata;
import game.data.paival;
import game.data.soundlist;
import game.sprite.Mouse;
import game.sprite.pais;
import game.sprite.showTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.util.Log;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleMusic;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleSurfaceView;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;
import com.android.angle.ButtonSp;

import danji.doudizhu.game.v1.R;

public class GameUI extends AngleUI{
	AngleSurfaceView sview;
	public int width,height;
	float bili,bili_2;
	AnglePhysicsEngine bgitems,paiimgs,topitems,temppais;
	AngleSpriteLayout pai,btns_lay,title_lay;
	Random rand = new Random();
	paidata pdata;
	Mouse mouse;
	int NowPlay;
	ButtonSp jiaodzBtn,bujiaoBtn,qiangdzBtn,buqiangBtn,kaisBtn, chupaiBtn,passBtn,tishiBtn,chongxuanBtn;
	int GAMESTEP=0;//0-等待开始 ,1-游戏开始发牌,2-叫地主,3-打牌阶段,4-游戏结束,5-开始清除
	int myscore=0,wd,hd;
	ButtonSp dipai_1,dipai_2,dipai_3,soundbtn;
	showTag Header_0,Header_1,Header_2;
	boolean soundopen=true;
	ArrayList<paival> lastPai = new ArrayList<paival>();
	public AngleMusic paisound,fapaisd,gamebgsound;
	public GameUI(AngleActivity activity) {
		super(activity);
		 width = mActivity.mGLSurfaceView.roWidth;
		 height = mActivity.mGLSurfaceView.roHeight;
		 sview = mActivity.mGLSurfaceView;
		 bili = (float)width/(float)344;
		 bili_2 = (float)width/(float)512;
		 
		 paisound = new AngleMusic(activity);
		 fapaisd = new AngleMusic(activity,R.raw.fapai);
		 gamebgsound =  new AngleMusic(activity,R.raw.gamebg);
		 
		// gamebgsound.play(0.5f, true);
		 
		 fapaisd.load(R.raw.fapai);
		 pdata = new paidata();
		 
		 bgitems = new AnglePhysicsEngine(50);
		 addObject(bgitems);
		 temppais = new AnglePhysicsEngine(58);
		 addObject(temppais);
		 paiimgs = new AnglePhysicsEngine(85);
		 addObject(paiimgs);
		 topitems = new AnglePhysicsEngine(55);
		 addObject(topitems);
		 mouse = new Mouse(new AngleSpriteLayout(sview, 10,10, R.drawable.pai, 0, 0, 10, 10));
		 paiimgs.addObject(mouse);
		 init();
		 
	}
	void playfapaisound(){
		if(soundopen){
			fapaisd.play();
		}
	}
	//播放声音
	void playpaiSound(int px,int val){
		switch(px){
		case Aichupai.PXDANG:
			if(val==52){
				paisound.load(soundlist.dangpailist[17]);
			}else if(val==53){
				paisound.load(soundlist.dangpailist[18]);
			}else {
				paisound.load(soundlist.dangpailist[val]);
			}
			break;
		case Aichupai.PXDUI:
			paisound.load(soundlist.dduipailist[val]);
			break;
		case Aichupai.PXSAN:
			paisound.load(soundlist.SANlist[val]);
			break;
			
		case Aichupai.PXSANO:
			paisound.load(soundlist.otherlist[0]);
			break;
		case Aichupai.PXSANT:
			paisound.load(soundlist.otherlist[1]);
			break;
		case Aichupai.PXSDT:
			paisound.load(soundlist.otherlist[3]);
			break;
		case Aichupai.PXSUN:
			paisound.load(soundlist.otherlist[2]);
			break;
		case Aichupai.PXLIAND:
			paisound.load(soundlist.otherlist[4]);
			break;
		case Aichupai.PXFEIJI:
			paisound.load(soundlist.otherlist[7]);
			break;
		case Aichupai.PXHUOJIAN:
			paisound.load(soundlist.otherlist[6]);
			break;
		case Aichupai.PXZAD:
			paisound.load(soundlist.otherlist[5]);
			break;
		case Aichupai.JIAODIZHU:
			paisound.load(soundlist.otherlist[8]);
			break;
		case Aichupai.QIANGDIZHU:
			paisound.load(soundlist.otherlist[9]);
			break;
		case Aichupai.BUJIAO:
			paisound.load(soundlist.otherlist[10]);
			break;
		case Aichupai.BUQIANG:
			paisound.load(soundlist.otherlist[11]);
			break;
		case Aichupai.PASS:
			paisound.load(soundlist.otherlist[14]);
			break;
		case Aichupai.YAOBUQI:
			paisound.load(soundlist.otherlist[15]);
			break;
		case Aichupai.XUANPAI:
			paisound.load(soundlist.otherlist[18]);
			break;
		case Aichupai.LOST:
			paisound.load(soundlist.otherlist[20]);
			break;
		case Aichupai.WIN:
			paisound.load(soundlist.otherlist[21]);
			break;
		}
		if(soundopen){
			paisound.play();
		}
	}
	private void init(){
		bgitems.addObject(new AngleSprite(width/2, height/2, new AngleSpriteLayout(sview, width, height, R.drawable.gamebg, 0, 0, 512, 344)));
		btns_lay = new AngleSpriteLayout(sview, (int)(90*bili_2),(int)(bili_2*48), R.drawable.buttons, 0, 0, 90, 48, 40, 5);
		
		jiaodzBtn = new ButtonSp(width/2+btns_lay.roWidth*2/3,height-btns_lay.roHeight*2,btns_lay, 0);
		bujiaoBtn = new ButtonSp(width/2-btns_lay.roWidth*2/3,height-btns_lay.roHeight*2,btns_lay, 1);
		qiangdzBtn = new ButtonSp(width/2+btns_lay.roWidth*2/3,height-btns_lay.roHeight*2,btns_lay, 3);
		buqiangBtn = new ButtonSp(width/2-btns_lay.roWidth*2/3,height-btns_lay.roHeight*2,btns_lay, 2);
		kaisBtn = new ButtonSp(width/2,height/2,btns_lay, 9);
		
		chupaiBtn =new ButtonSp(width/2+btns_lay.roWidth*3/5,height-btns_lay.roHeight*7/3,btns_lay, 4);
		passBtn=  new ButtonSp(width/2-btns_lay.roWidth*3/5-btns_lay.roWidth,height-btns_lay.roHeight*7/3,btns_lay, 6);
		tishiBtn = new ButtonSp(width/2+btns_lay.roWidth+btns_lay.roWidth*3/5,height-btns_lay.roHeight*7/3,btns_lay, 7);
		chongxuanBtn = new ButtonSp(width/2-btns_lay.roWidth*3/5,height-btns_lay.roHeight*7/3,btns_lay, 8);
		
		jiaodzBtn.show(false);
		bujiaoBtn.show(false);
		qiangdzBtn.show(false);
		buqiangBtn.show(false);
		chupaiBtn.show(false);
		passBtn.show(false);
		tishiBtn.show(false);
		chongxuanBtn.show(false);
		bgitems.addObject(tishiBtn);
		bgitems.addObject(chongxuanBtn);
		bgitems.addObject(jiaodzBtn);
		bgitems.addObject(bujiaoBtn);
		bgitems.addObject(qiangdzBtn);
		bgitems.addObject(buqiangBtn);
		topitems.addObject(kaisBtn);
		bgitems.addObject(chupaiBtn);
		bgitems.addObject(passBtn);
		
		title_lay = new AngleSpriteLayout(sview, (int)(170*bili_2),(int)(bili_2*40), R.drawable.buttons, 0, 384, 170, 40, 12, 3);
		pai = new AngleSpriteLayout(sview, (int)(50*bili_2),(int)(64*bili_2), R.drawable.pai, 0, 0, 50, 64, 80, 10);
		wd = pai.roWidth/2;
		hd = pai.roHeight/10;
		//底牌
		dipai_1 = new ButtonSp((int) (width/2-pai.roWidth*1.5),height/3,pai,54);
		dipai_2 = new ButtonSp((int) (width/2),height/3,pai,54);
		dipai_3  = new ButtonSp((int) (width/2+pai.roWidth*1.5),height/3,pai,54);
		dipai_1.show(false);
		dipai_2.show(false);
		dipai_3.show(false);
		topitems.addObject(dipai_1);
		topitems.addObject(dipai_2);
		topitems.addObject(dipai_3);
		
		Header_0=new showTag(pai, new AngleVector(pai.roWidth, height-1.5f*pai.roHeight),55);
		Header_1=new showTag(pai, new AngleVector(width-pai.roHeight, pai.roHeight/2),55);
		Header_2=new showTag(pai, new AngleVector(pai.roHeight, pai.roHeight/2),55);
		Header_0.setShow(0);
		Header_1.setShow(0);
		Header_2.setShow(0);
		topitems.addObject(Header_0);
		topitems.addObject(Header_1);
		topitems.addObject(Header_2);
		soundbtn = new ButtonSp(width-pai.roWidth/2, pai.roHeight/2,pai, 57);
		topitems.addObject(soundbtn);
	}
		//洗牌发牌
	    int[] play_1,play_2,play_3,dipai;
	    int DONGHUASTEP=0;//1-发牌动画控制 2-叫地主控制,3-移除临时牌
	    int ShowCount=0;   //以发牌张数
	    int preWin=-1;	   //上一次赢家
		private void xipai(){
			 jiaodzstep=0;  //当前叫地主步骤
			 nowJiaodz=-1;  //当前叫地主玩家
			 Endjiaodz=-1;  //最后叫地主的玩家
			 hasjiaodz=false;  //是否已经有人叫过地主了
			 Wanjiajdz=false;  //是否玩家叫过地主了
			playfapaisound();
			showheader(false);
			pdata.xipai();
			dipai = pdata.getdipai();
			play_3 = pdata.getPlaypai(2);
			play_1 = pdata.getPlaypai(0);
			play_2 = pdata.getPlaypai(1);
			lastPai = new ArrayList<paival>();
			passplay = new boolean[3];
			ShowCount=0;
			DONGHUASTEP=1;
			hasjiaodz=false;
		}
		//发牌动画控制
		void showmovie(){
			if(ShowCount<17 && DONGHUASTEP==1){
				//发牌动画
				temppais.addObject(new pais(pai,play_3[ShowCount]-1,new AngleVector(pai.roWidth/2, height/3+(ShowCount-play_3.length/2)*hd),2));
				temppais.addObject(new pais(pai,play_1[ShowCount]-1,new AngleVector(width/2+(ShowCount-play_1.length/2)*wd+wd, height-(int)(76*bili_2)/2),0));
				temppais.addObject(new pais(pai,play_2[ShowCount]-1,new AngleVector(width-pai.roWidth/2, height/3+(ShowCount-play_2.length/2)*hd),1));
			}else if(ShowCount==17&& DONGHUASTEP==1){
				//发牌结束
				DONGHUASTEP=0;
				showdipai(1);
				jiaodzstep=0;
				if(preWin<0){
					nowJiaodz = rand.nextInt(1000)%3;
				}else{
					nowJiaodz = preWin;
				}
				if(nowJiaodz==0){
					showqdzbtn(1,true);
				}
				time=10;
				GAMESTEP = 2;
				DONGHUASTEP=2;
			}
			ShowCount++;
		}
		//排序前去掉临时牌
		void removetemppai(){
			if(DONGHUASTEP==3){
				for(int i=temppais.count()-1;i>=0;i--){
					((pais)temppais.childAt(i)).mDie = true;
				}
				if(temppais.count()==0){
					DONGHUASTEP=0;	
				}
			}
		}
		//显示头像
		void showheader(boolean show){
			if(show){
				Header_0.setFid(56);
				Header_1.setFid(56);
				Header_2.setFid(56);
				if(Endjiaodz==0){
					Header_0.setFid(55);
				}else if(Endjiaodz==1){
					Header_1.setFid(55);
				}else if(Endjiaodz==2){
					Header_2.setFid(55);
				}
				Header_0.setShow(1);
				Header_1.setShow(1);
				Header_2.setShow(1);
			}else{
				Header_0.setShow(0);
				Header_1.setShow(0);
				Header_2.setShow(0);
			}
		}
		//增加牌后重新排序牌
		void resortpai(){
			for(int i=temppais.count()-1;i>=0;i--){
				((pais)temppais.childAt(i)).setHidden();
			}
			DONGHUASTEP=3;
			play_3 = pdata.getPlaypai(2);
			play_1 = pdata.getPlaypai(0);
			play_2 = pdata.getPlaypai(1);
			int[] pai_1=new int[20],pai_2=new int[20],pai_3=new int[20]; 
			Log.v("log","dizhu="+Endjiaodz);
			showheader(true);
			switch(Endjiaodz){
			case 0:
				pai_1 = new int[play_1.length+3];
				pai_2=new int[17];
				pai_3=new int[17];
				for(int i=0;i<pai_1.length;i++){
					if(i<play_1.length){
						pai_1[i] = play_1[i];
						pai_2[i] = play_2[i];
						pai_3[i] = play_3[i];
					}else{
						pai_1[i] = dipai[i-play_1.length];
					}
				}
				pai_1 = pdata.sortpai(pai_1);
				break;
			case 1:
				pai_2 = new int[play_2.length+3];
				pai_1=new int[17];
				pai_3=new int[17];
				for(int i=0;i<pai_2.length;i++){
					if(i<play_2.length){
						pai_2[i] = play_2[i];
						pai_1[i] = play_1[i];
						pai_3[i] = play_3[i];
					}else{
						pai_2[i] = dipai[i-play_2.length];
					}
				}
				pai_2 = pdata.sortpai(pai_2);
				break;
			case 2:
				pai_3 = new int[play_3.length+3];
				pai_1=new int[17];
				pai_2=new int[17];
				for(int i=0;i<pai_3.length;i++){
					if(i<play_3.length){
						pai_3[i] = play_3[i];
						pai_2[i] = play_2[i];
						pai_1[i] = play_1[i];
					}else{
						pai_3[i] = dipai[i-play_3.length];
					}
				}
				pai_3 = pdata.sortpai(pai_3);
				break;
			}
			
		
			for(int i=0;i<pai_3.length;i++){
				if(pai_3[i]>0){
					paiimgs.addObject(new pais(pai,pai_3[i]-1,new AngleVector(pai.roWidth/2, height/3+(i-pai_3.length/2)*hd),2));
				}
			}
			for(int i=0;i<pai_1.length;i++){
				if(pai_1[i]>0){
					paiimgs.addObject(new pais(pai,pai_1[i]-1,new AngleVector(width/2+(i-pai_1.length/2)*wd+wd, height-(int)(76*bili_2)/2),0));
				}
			}
			for(int i=0;i<pai_2.length;i++){
				if(pai_2[i]>0){
					paiimgs.addObject(new pais(pai,pai_2[i]-1,new AngleVector(width-pai.roWidth/2, height/3+(i-pai_2.length/2)*hd),1));
				}
			}
			//开始进入出牌阶段
			NowPlay = Endjiaodz;
			time=10;
			if(NowPlay==0){
				showChupaiBtn(1,true);
			}
			GAMESTEP = 3;
		}
		//出牌按钮控制
		void showChupaiBtn(int type,boolean show){
			if(show){
				switch(type){
					case 1:
						//第一次出牌
						chupaiBtn.show(true);
						chongxuanBtn.show(true);
						break;
					case 2:
						//接牌
						tishiid=0;
						tishilist= new HashMap<String,ArrayList<paival>>();
						tishilist = Tishidata.gettishilist(getAIPai(0),lastPai);
						chupaiBtn.show(true);
						passBtn.show(true);
						chongxuanBtn.show(true);
						tishiBtn.show(true);
						break;
				}
			}else{
				chupaiBtn.show(show);
				passBtn.show(show);
				chongxuanBtn.show(show);
				tishiBtn.show(show);
			}
		}
		int jiaodzstep=0;  //当前叫地主步骤
		int nowJiaodz=-1;  //当前叫地主玩家
		int Endjiaodz=-1;  //最后叫地主的玩家
		boolean hasjiaodz=false;  //是否已经有人叫过地主了
		boolean Wanjiajdz=false;  //是否玩家叫过地主了
		//叫地主控制
		void jiaodizhu_AI(){
			if(DONGHUASTEP==2){
				time--;
				if(time<0){
					if(nowJiaodz==1){
					 	if(jiaodzstep==0){
					 		Log.v("log","1-叫地主");
					 		playpaiSound(Aichupai.JIAODIZHU,0);
					 		showText(Aichupai.JIAODIZHU,nowJiaodz);
					 		Endjiaodz=1;
					 		hasjiaodz=true;
					 	}else{
					 		if(hasjiaodz){
					 			if(Wanjiajdz){
					 				Log.v("log","1-不抢地主");
						 			playpaiSound(Aichupai.BUQIANG,0);
						 			showText(Aichupai.BUQIANG,nowJiaodz);
					 			}else{
					 				Log.v("log","1-抢地主");
					 				playpaiSound(Aichupai.QIANGDIZHU,0);
					 				showText(Aichupai.QIANGDIZHU,nowJiaodz);
					 			}
					 		}else{
					 			Log.v("log","1-叫地主");
					 			playpaiSound(Aichupai.JIAODIZHU,0);
					 			showText(Aichupai.JIAODIZHU,nowJiaodz);
					 		}
					 		Endjiaodz=1;
					 		hasjiaodz=true;
					 	}
						nowJiaodz++;
						nowJiaodz%=3;
						jiaodzstep++;
						time=7;
						//结束挣地主
						if(jiaodzstep==4){
							DONGHUASTEP=0;
							showdipai(2);
						}
					}else if(nowJiaodz==2){
						if(jiaodzstep==0){
					 		Log.v("log","2-叫地主");
					 		playpaiSound(Aichupai.JIAODIZHU,0);
					 		showText(Aichupai.JIAODIZHU,nowJiaodz);
					 		Endjiaodz=2;
					 		hasjiaodz=true;
					 	}else{
					 		Endjiaodz=2;
					 		if(hasjiaodz){
					 			if(Wanjiajdz){
					 				Log.v("log","2-不抢地主");
						 			playpaiSound(Aichupai.BUQIANG,0);
						 			showText(Aichupai.BUQIANG,nowJiaodz);
					 			}else{
					 			Log.v("log","2-抢地主");
					 			playpaiSound(Aichupai.QIANGDIZHU,0);
					 			showText(Aichupai.QIANGDIZHU,nowJiaodz);
					 			}
					 		}else{
					 			Log.v("log","2-叫地主");
					 			playpaiSound(Aichupai.JIAODIZHU,0);
					 			showText(Aichupai.JIAODIZHU,nowJiaodz);
					 		}
					 		hasjiaodz=true;
					 	}
						nowJiaodz++;
						nowJiaodz%=3;
						jiaodzstep++;
						time=7;
						//结束挣地主
						if(jiaodzstep==4){
							DONGHUASTEP=0;
							showdipai(2);
						}else{
							if(nowJiaodz==0){
								showqdzbtn(2,true);
							}
						}
					}
				}
			}
		}
		//显示叫地主,抢按钮
		void showqdzbtn(int type,boolean show){
			switch(type){
			case 1:
				jiaodzBtn.show(show);
				bujiaoBtn.show(show);
				break;
			case 2:
				qiangdzBtn.show(show);
				buqiangBtn.show(show);
				break;
			}
		}
		
	double start,start2;
	int time;
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		if((System.currentTimeMillis()-start)>200){
			start = System.currentTimeMillis();
			showmovie();		//发牌动画控制
			jiaodizhu_AI();		//叫地主动画控制
			removetemppai();	//移除临时牌,重新排序动画控制
			chupaistep_AI();	//AI出牌控制
			
		}
		if((System.currentTimeMillis()-start2)>50){
			start2 = System.currentTimeMillis();
			if(GAMESTEP==5){
				int num = paiimgs.count();
				if(num>1){
					if(paiimgs.childAt(num-1) instanceof pais)((pais)paiimgs.childAt(num-1)).mDie=true;
				}else{
					GAMESTEP=0;
					xipai();
				}
			}
		}
		super.step(secondsElapsed);
	}
	
	//出牌控制
	void chupaistep_AI(){
		if(GAMESTEP==3){
			if(NowPlay>0&&time<0){
				Log.v("log","NowPlay="+NowPlay);
				//time=20;
				if(ispass()||lastPai.size()==0){
					//主动出牌
					AI_Autochupai();
				}else{
					//跟牌
					AI_Gengpai();
				}
			}
			time--;
		}
	}
	
	//显示底牌
			void showdipai(int show){
				switch(show){
				case 1:
					dipai_1.Moveto(new AngleVector((int) (width/2-pai.roWidth*1.5),height/3), 1);
					dipai_2.Moveto(new AngleVector((int) (width/2),height/3), -1);
					dipai_3.Moveto(new AngleVector((int) (width/2+pai.roWidth*1.5),height/3), 1);
					//显示盖的牌
					dipai_1.setFrame(54);
					dipai_2.setFrame(54);
					dipai_3.setFrame(54);
					dipai_1.show(true);
					dipai_2.show(true);
					dipai_3.show(true);
					break;
				case 2:
					//显示翻开的牌
					dipai_1.setFrame(dipai[0]-1);
					dipai_2.setFrame(dipai[1]-1);
					dipai_3.setFrame(dipai[2]-1);
					dipai_1.show(true);
					dipai_2.show(true);
					dipai_3.show(true);
					 movedptop();
					break;
				case 3:
					//不显示底牌
					dipai_1.show(false);
					dipai_2.show(false);
					dipai_3.show(false);
					break;
				}
			}
		//把底牌移动到顶部
		void movedptop(){
			dipai_1.Moveto(new AngleVector(width/4, pai.roHeight/2), -1);
			dipai_2.Moveto(new AngleVector(width/4+pai.roWidth, pai.roHeight/2), -1);
			dipai_3.Moveto(new AngleVector(width/4+2*pai.roWidth, pai.roHeight/2), -1);
			resortpai();
		}
		//设置显示提示位置
		AngleVector getWhere(int where){
			AngleVector pos = new AngleVector();
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
			return pos;
		}
	//获取AI剩余的牌
	private ArrayList<paival> getAIPai(int typeid){
		ArrayList<paival> slist = new ArrayList<paival>();
		//获取牌
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==typeid&&!((pais)paiimgs.childAt(i)).out){
				paival temp = ((pais)paiimgs.childAt(i)).getvals();
				temp.index = i;
				slist.add(temp);
			}
		}
		return slist;
	}
	//Ai主动出牌
	void AI_Autochupai(){
		ArrayList<paival> putapi = Aichupai.AIautocp(getAIPai(NowPlay));
		AIputout(putapi);
		lastPai = new ArrayList<paival>();
		lastPai.addAll(putapi);
		nextPlay();
	}
	//Ai跟牌
	void AI_Gengpai(){
		ArrayList<paival> putapi = Aichupai.AIgenpai(getAIPai(NowPlay), lastPai);
		if(putapi.size()>0){
			AIputout(putapi);
			lastPai = new ArrayList<paival>();
			lastPai.addAll(putapi);
		}else{
			passplay[NowPlay]=true;
			showText(Aichupai.PASS,NowPlay);
			playpaiSound(Aichupai.PASS, 0);
		}
		Log.v("log","AI跟");
		
		nextPlay();
	}
	//AI出牌动画
	void AIputout(ArrayList<paival> putapi){
		float wx=0;
		if(NowPlay==1){
			wx = width*3/4;
		}else if(NowPlay==2){
			wx =  width/4;
		}
		int px = Aichupai.getpx(putapi);
		playpaiSound(px,putapi.get(0).val);
		showText(px,NowPlay);
		String AIpi = "AI:";
		for(int i=0;i<putapi.size();i++){
			paival is = putapi.get(i);
			((pais)paiimgs.childAt(is.index)).moveto(new AngleVector(wx-(i-putapi.size()/2)*wd,height/3));
			((pais)paiimgs.childAt(is.index)).setfx(1);
			AIpi += is.val+",";
		}
		Log.v("log",AIpi);
	}

	//下一家叫地主
	void nextjiaodz(){
		nowJiaodz++;
		nowJiaodz%=3;
		jiaodzstep++;
		time=7;
		if(jiaodzstep==4){
			Log.v("log","叫地主结束");
			DONGHUASTEP=0;
			showdipai(2);
		}
	}
	//按钮控制
	void mouseevent(float ex,float ey){
		switch(GAMESTEP){
			case 0:
				 if(kaisBtn.test(ex,ey)){
					GAMESTEP=5; 
					kaisBtn.show(false);
				 }
				break;
			case 1:
				
				break;
			case 2:
				if(jiaodzBtn.test(ex, ey)){
					playpaiSound(Aichupai.JIAODIZHU,0);
					showText(Aichupai.JIAODIZHU,0);
					showqdzbtn(1,false);
					Endjiaodz=0;
					hasjiaodz=true;
					Wanjiajdz=true;
					nextjiaodz();
				}
				if(qiangdzBtn.test(ex,ey)){
					playpaiSound(Aichupai.QIANGDIZHU,0);
					showText(Aichupai.QIANGDIZHU,0);
					showqdzbtn(2,false);
					Endjiaodz=0;
					Wanjiajdz=true;
					nextjiaodz();
				}
				if(bujiaoBtn.test(ex, ey)){
					playpaiSound(Aichupai.BUJIAO,0);
					showText(Aichupai.BUJIAO,0);
					showqdzbtn(1,false);
					nextjiaodz();
				}
				if(buqiangBtn.test(ex, ey)){
					playpaiSound(Aichupai.BUQIANG,0);
					showText(Aichupai.BUQIANG,0);
					showqdzbtn(2,false);
					nextjiaodz();
				}
				break;
			case 3:
				//玩家出牌控制
				 mouse.mPosition.set(ex,ey);
				 test();
				 if(chupaiBtn.test(ex,ey)){
					 chupai();
				 }
				 if(passBtn.test(ex,ey)&&!ispass()){
					 playpaiSound(Aichupai.PASS,0);
					 showText(Aichupai.PASS,0);
					 showChupaiBtn(0,false);
					 passplay[0]=true;
					 nextPlay();
				 }
				 if(chongxuanBtn.test(ex,ey)){
					 resetpai();
					 playpaiSound(Aichupai.XUANPAI,0);
				 }
				 if(tishiBtn.test(ex,ey)&&!ispass()){
					 tishichupai();
				
				 }
				break;
			case 4:
				 if(kaisBtn.test(ex,ey)){
						GAMESTEP=5; 
						kaisBtn.show(false);
						showdipai(3);
				 }
				break;
			default:
			
				
				break;
		}
	}
	int tishiid=0;
	Map<String,ArrayList<paival>> tishilist= new HashMap<String,ArrayList<paival>>();
	//提示
	void tishichupai(){
		if(tishilist.size()>0){
			tishiid%=tishilist.size();
			if(tishiid<tishilist.size()){
				 resetpai();
				 ArrayList<paival> slist = new ArrayList<paival>();
				 slist = tishilist.get(""+tishiid);
				 settishipai(slist);
			}
			playpaiSound(Aichupai.XUANPAI,0);
		}else{
			 playpaiSound(Aichupai.YAOBUQI,0);
			 showText(Aichupai.YAOBUQI,0);
			 showChupaiBtn(0,false);
			 passplay[0]=true;
			 nextPlay();
		}
		tishiid++;
		
	}
	//选择提示的牌
	void settishipai(ArrayList<paival> slist){
		for(int i=slist.size()-1;i>=0;i--){
			((pais)paiimgs.childAt(slist.get(i).index)).setSel();
		}
	}
	//回归选择过的牌
	void resetpai(){
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).isme()&&((pais)paiimgs.childAt(i)).selected&&!((pais)paiimgs.childAt(i)).out){
				((pais)paiimgs.childAt(i)).setSel();
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO 自动生成的方法存根
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				mouseevent(event.getX(),event.getY());
				 if(soundbtn.test(event.getX(),event.getY())){
						if(soundopen){
							soundopen=false;
							gamebgsound.pause();
						}else{
							soundopen=true;
							gamebgsound.play(0.5f, true);
						}
					 }
				break;
			case MotionEvent.ACTION_MOVE:
				
				break;
			case MotionEvent.ACTION_UP:
				
				break;
		}
		
		return super.onTouchEvent(event);
	}
	//记录pass
	boolean[] passplay = new boolean[3];
	boolean ispass(){
		int count=0;
		for(int i=0;i<passplay.length;i++){
			if(passplay[i]) count++;
		}
		if(count>1){ 
			return true;
		}else{
			return false;
		}
		
	}

	//检查选牌 
	private int test(){
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).isme()&&(mouse.collide((pais)paiimgs.childAt(i)))&&!((pais)paiimgs.childAt(i)).out){
				((pais)paiimgs.childAt(i)).setSel();
				playpaiSound(Aichupai.XUANPAI,0);
				return 1;
			}
		}
		return 0;
	}
	//玩家出牌
	private void chupai(){
		ArrayList<paival> slist = new ArrayList<paival>();
		String temppai = "";
		//获取已选择牌
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).isme()&&((pais)paiimgs.childAt(i)).isselected()&&!((pais)paiimgs.childAt(i)).out){
				paival temp = ((pais)paiimgs.childAt(i)).getvals();
				temp.index = i;
				slist.add(temp);
				temppai += temp.val+",";
			}
		}
		Log.v("log","选中的牌="+temppai);
		int count = slist.size();
		if(count>0){
			//第一次出牌权
			int px = Aichupai.getpx(slist);
			if(ispass()||lastPai.size()==0){
				if(px>0){
					//符合出牌
					pschupai(slist);
					showChupaiBtn(0,false);
					Log.v("log","px="+px);
					playpaiSound(px,slist.get(0).val);
					
				}else{
					Log.v("log","牌型不对");
					showText(Aichupai.BUFUHEGZ,0);
				}
				
			}else{
			//接牌
				if(px>0){
					if(Aichupai.personGp(slist,lastPai)){
						//可以出牌
						pschupai(slist);
					}else{
						Log.v("log","压不过上一家");
						showText(Aichupai.YAOBUGUO,0);
					}
				}else{
					  Log.v("log","牌型不对");
					  showText(Aichupai.BUFUHEGZ,0);
				}
			}
			
		}else{
			Log.v("log","选择要出的牌");
			showText(Aichupai.QINGXUANP,0);
		}
	
	}
	//玩家出牌动画
	private void pschupai(ArrayList<paival> pais){
		int count = pais.size();
		for(int i=0;i<count;i++){
			paival tp = pais.get(i);
			((pais)paiimgs.childAt(tp.index)).moveto(new AngleVector(width/2+(count/2-i)*20,height/2));
		}
		if(pais.size()>0){
			lastPai  = new ArrayList<paival>();
			lastPai = pais;
			showChupaiBtn(0,false);
			int px = Aichupai.getpx(pais);
			playpaiSound(px,pais.get(0).val);
			showText(px,0);
		}
		nextPlay();
	}
	//获取没出的牌数量
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
	private boolean isgameOver(){
		int count = 0;
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==NowPlay&&!((pais)paiimgs.childAt(i)).out){
				count++;
			}
		}
		if(count>0) return false;
		return true;
	}
	//下一家出牌
	private void nextPlay(){
		if(!isgameOver()){
			time=7;
			removepai();
			NowPlay++;
			NowPlay%=3;
			passplay[NowPlay]=false;
			movepai();
			if(NowPlay==0) {
				if(ispass()) {
					showChupaiBtn(1, true);
				}else{
					showChupaiBtn(2, true);
				}
			}
		}else{
			if(NowPlay==Endjiaodz){
				topitems.addObject(new showTag(btns_lay,new AngleVector(width/2, height/3), 21, true));
			}else{
				topitems.addObject(new showTag(btns_lay, new AngleVector(width/2, height/3), 22, true));
			}
			if(NowPlay==0){
				playpaiSound(Aichupai.WIN, 0);
			}else{
				playpaiSound(Aichupai.LOST, 0);
			}
			GAMESTEP=4;
			kaisBtn.show(true);
		}
	}
	//显示提示
	void showText(int type,int with){
		switch(type){
		case Aichupai.PXFEIJI:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 18, true));
			break;
		case Aichupai.PXHUOJIAN:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 25, true));
			break;
		case Aichupai.PXLIAND:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 19, true));
			break;
		case Aichupai.PXSAN:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 26, true));
			break;
		case Aichupai.PXSANO:
			topitems.addObject(new showTag(title_lay, getWhere(with), 3, true));
			break;
		case Aichupai.PXSANT:
			topitems.addObject(new showTag(title_lay, getWhere(with), 4, true));
			break;
		case Aichupai.PXSDT:
			topitems.addObject(new showTag(title_lay, getWhere(with), 5, true));
			break;
		case Aichupai.PXSUN:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 17, true));
			break;
		case Aichupai.PXZAD:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 20, true));
			break;
		case  Aichupai.QIANGDIZHU:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 13, true));
			break;
		case Aichupai.JIAODIZHU:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 10, true));
			break;
		case Aichupai.BUQIANG:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 12, true));
			break;
		case Aichupai.BUJIAO:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 11, true));
			break;
		case Aichupai.PASS:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 16, true));
			break;
		case Aichupai.YAOBUQI:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 14, true));
			break;
		case Aichupai.MEIYOUDAGUO:
			topitems.addObject(new showTag(title_lay, getWhere(with), 0, true));
			break;
		case Aichupai.QINGXUANP:
			topitems.addObject(new showTag(title_lay, getWhere(with), 2, true));
			break;
		case Aichupai.BUFUHEGZ:
			topitems.addObject(new showTag(title_lay, getWhere(with), 1, true));
			break;
		case Aichupai.DIZHUYIN:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 21, true));
			break;
		case Aichupai.NONGMINGY:
			topitems.addObject(new showTag(btns_lay, getWhere(with), 22, true));
			break;
		case Aichupai.YAOBUGUO:
			topitems.addObject(new showTag(title_lay, getWhere(with), 6, true));
			break;
		}
	}
	
	
	//去掉打出去的牌-上一次出的牌
	private void movepai(){
		for(int i=paiimgs.count()-1;i>=0;i--){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==NowPlay&&((pais)paiimgs.childAt(i)).out){
			 ((pais)paiimgs.childAt(i)).mDie=true;
			}
		}
	}
	
	//重新设置牌位置
	private void removepai(){
		
		ArrayList<paival> slist = new ArrayList<paival>();
		//获取已选择牌
		for(int i=0;i<paiimgs.count();i++){
			if((paiimgs.childAt(i) instanceof pais)&&((pais)paiimgs.childAt(i)).playid==NowPlay&&!((pais)paiimgs.childAt(i)).out){
				paival temp = ((pais)paiimgs.childAt(i)).getvals();
				temp.index = i;
				slist.add(temp);
			}
		}
		
		int count = slist.size();
		switch(NowPlay){
			case 0:
				for(int i=0;i<count;i++){
					paival tp = slist.get(i);
					((pais)paiimgs.childAt(tp.index)).selected = false;
					((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(width/2+(i-count/2)*wd+wd, height-(76*bili_2)/2));
				}
				break;
			case 1:
				for(int i=count-1;i>=0;i--){
					paival tp = slist.get(i);
					((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(width-pai.roWidth/2,height/3+(i-count/2)*hd));
				}
				break;
			case 2:
				for(int i=count-1;i>=0;i--){
					paival tp = slist.get(i);
					((pais)paiimgs.childAt(tp.index)).setPos(new AngleVector(pai.roWidth/2,height/3+(i-count/2)*hd));
				}
				break;
		}
		
	}
	@Override
	public void onActivate() {
		// TODO 自动生成的方法存根
		super.onActivate();
		gamebgsound.play(0.5f, true);
	}

	@Override
	public void onDeactivate() {
		// TODO 自动生成的方法存根
		super.onDeactivate();
		gamebgsound.pause();
		
	}
	
}
