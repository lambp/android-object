package game.ui;

import game.data.tools;
import game.qipai.bigtwo.v2.R;
import game.qipai.bigtwo.v2.mainActivity;
import game.sprite.BtnSprite;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

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


public class MenuUI extends AngleUI{
	AngleSpriteLayout btn_layout,top_layout;
	BtnSprite three_g,three_t,four_g,four_t,icon_btn,add_coin,set_btn,exit_btn,header;//PlayBtn,HelpBtn,MoreBtn,
	AngleSurfaceView sview;
	public AngleFont font;
	public AngleString 	score;
	int myscore=0;
	public AngleSpriteLayout header_lay;
	float bili_2;
	AnglePhysicsEngine	btnitems;
	AngleMusic bgmusic;
	public MenuUI(AngleActivity activity) {
		// TODO 自动生成的构造函数存根
		super(activity);
		int width = mActivity.mGLSurfaceView.roWidth;
		int height = mActivity.mGLSurfaceView.roHeight;
		 sview = mActivity.mGLSurfaceView;
		 bili_2 = (float)width/(float)1024;
		 
		 bgmusic = new AngleMusic(mActivity,R.raw.backgroundmusic);
		 bgmusic.play(0.5f,true);
		 
			font = new AngleFont(mActivity.mGLSurfaceView, height/12, Typeface.createFromAsset(mActivity.getAssets(),"NINA.TTF"), 222, 1, 0, 218, 158, 36, 255);
			score = new AngleString(font,""+myscore,(int)(265*bili_2),(int)(65*bili_2),AngleString.aLeft);
			   
			    String val = tools.getstrformmem(tools.score,mActivity);
				if(val.equals("null")){
					myscore = 500;
					Toast.makeText(mActivity,"首次登陆送:"+myscore+" 金币",Toast.LENGTH_LONG).show();
					tools.savetomem(tools.score,""+myscore, mActivity);
					score.set(""+myscore);
				}else{
					
					myscore = Integer.parseInt(val);
					score.set(""+myscore);
				}
				
		addObject(new AngleSprite(width/2, height/2, new AngleSpriteLayout(sview, width, height, R.drawable.menubg, 0, 0, 1024, 688)));
		addObject(new AngleSprite((int)(171*bili_2)+(int)(415*bili_2),(int)(47*bili_2), new AngleSpriteLayout(sview, (int)(830*bili_2),(int)(bili_2*79), R.drawable.menubtns, 0, 609, 830, 79)));
		addObject(score);
		
		 btnitems = new AnglePhysicsEngine(50);
		 addObject(btnitems);
		
		header_lay =  new AngleSpriteLayout(sview, (int)(80*bili_2),(int)(bili_2*80), R.drawable.header, 0, 0, 110, 110, 18, 9);
		
		icon_btn = new BtnSprite(new AngleSpriteLayout(sview,(int)(512*bili_2),(int)(295*bili_2), R.drawable.menubtns, 477, 67, 512, 295), new AngleVector((int)(512*bili_2)/2+20*bili_2, height/2), 0);
		btnitems.addObject(icon_btn);
		
		top_layout = new AngleSpriteLayout(sview, (int)(67*bili_2),(int)(bili_2*67), R.drawable.menubtns, 477, 0, 67, 67, 6, 6);
		
		add_coin=new BtnSprite(top_layout, new AngleVector((int)(466*bili_2), (int)(44*bili_2)), 2);
				
		set_btn = new BtnSprite(new AngleSpriteLayout(sview, (int)(143*bili_2),(int)(bili_2*67), R.drawable.menubtns, 881, 0, 143, 67), new AngleVector(width-(int)(130*bili_2), (int)(47*bili_2)), 0);
		exit_btn = new BtnSprite(top_layout, new AngleVector((int)(47*bili_2), (int)(47*bili_2)), 3);
		header  = new BtnSprite(header_lay, new AngleVector((int)(130*bili_2), (int)(47*bili_2)), 3);
		
		btnitems.addObject(add_coin);
		btnitems.addObject(set_btn);
		btnitems.addObject(exit_btn);
		btnitems.addObject(header);
		
		btn_layout = new AngleSpriteLayout(sview, (int)(477*bili_2),(int)(bili_2*126), R.drawable.menubtns, 0, 0, 477, 126, 4, 1);
		three_g = new BtnSprite(btn_layout, new AngleVector(width-(int)(477*bili_2)/2-100*bili_2, height/2-(int)(bili_2*126*1.5)+(int)(bili_2*30)), 0);
		three_t = new BtnSprite(btn_layout, new AngleVector(width-(int)(477*bili_2)/2-20*bili_2, height/2-(int)(bili_2*126)/2+(int)(bili_2*30)), 1);
		four_g = new BtnSprite(btn_layout, new AngleVector(width-(int)(477*bili_2)/2-20*bili_2, height/2+(int)(bili_2*126)/2+(int)(bili_2*30)), 2);
		four_t = new BtnSprite(btn_layout, new AngleVector(width-(int)(477*bili_2)/2-100*bili_2, height/2+(int)(bili_2*126*1.5+(int)(bili_2*30))), 3);
		
		btnitems.addObject(three_g);
		btnitems.addObject(three_t);
		btnitems.addObject(four_g);
		btnitems.addObject(four_t);

	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		float eX = event.getX();
		float eY = event.getY();
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			
			if(three_g.test(eX, eY)){
				three_g.mAlpha=0.7f;
			}else if(three_t.test(eX, eY)){
				three_t.mAlpha=0.7f;
			}else if(four_g.test(eX, eY)){
				four_g.mAlpha=0.7f;
			}else if(four_t.test(eX, eY)){
				four_t.mAlpha=0.7f;
			}else if(exit_btn.test(eX, eY)){
				exit_btn.mAlpha=0.7f;
			}else if(set_btn.test(eX, eY)){
				set_btn.mAlpha=0.7f;
			}else if(add_coin.test(eX, eY)){
				add_coin.mAlpha=0.7f;
			}else if(header.test(eX, eY)){
				header.mAlpha=0.7f;
			}else if(icon_btn.test(eX, eY)){
				icon_btn.mAlpha=0.7f;
			}
			break;
		case MotionEvent.ACTION_UP:
			resetbtn();
			if(three_g.test(eX, eY)){
				resetmyscore();
				if(myscore<50){
					Toast.makeText(mActivity,"金币太少了，赶紧获取金币后再来吧！",Toast.LENGTH_LONG).show();
				}else{
					((mainActivity)mActivity).gameui.setType(1);
				    ((mainActivity)mActivity).setUI(((mainActivity)mActivity).gameui);
				}
				
			}else if(three_t.test(eX, eY)){
				resetmyscore();
				if(myscore<1000){
					Toast.makeText(mActivity,"进入高级场，金币必须大于1000",Toast.LENGTH_LONG).show();
				}else{
					((mainActivity)mActivity).gameui.setType(2);
					((mainActivity)mActivity).setUI(((mainActivity)mActivity).gameui);
				}
			}else if(four_g.test(eX, eY)){
				resetmyscore();
				if(myscore<50){
					Toast.makeText(mActivity,"金币太少了，赶紧获取金币后再来吧！",Toast.LENGTH_LONG).show();
				}else{
					((mainActivity)mActivity).gameui.setType(3);
					((mainActivity)mActivity).setUI(((mainActivity)mActivity).gameui);
				}
			}else if(four_t.test(eX, eY)){
				resetmyscore();
				if(myscore<1000){
					Toast.makeText(mActivity,"进入高级场，金币必须大于1000",Toast.LENGTH_LONG).show();
				}else{
					((mainActivity)mActivity).gameui.setType(4);
					((mainActivity)mActivity).setUI(((mainActivity)mActivity).gameui);
				}
			}else if(exit_btn.test(eX, eY)){
				((mainActivity)mActivity).ExitCheck();
			}else if(set_btn.test(eX, eY)){
				Toast.makeText(mActivity,"敬请期待",Toast.LENGTH_LONG).show();
			}else if(add_coin.test(eX, eY)){
				((mainActivity)mActivity).openadmain();
			}else if(header.test(eX, eY)){
				((mainActivity)mActivity).openadmain();
			}else if(icon_btn.test(eX, eY)){
				((mainActivity)mActivity).openadmain();
			}
			break;
		
		}
		
		return false;
	}
	
	void resetmyscore(){
		String val = tools.getstrformmem(tools.score,mActivity);
		if(val.equals("null")){
			myscore = 1000;
			Toast.makeText(mActivity,"首次登陆送:"+myscore+" 金币",Toast.LENGTH_LONG).show();
			tools.savetomem(tools.score,""+myscore, mActivity);
			score.set(""+myscore);
		}else{
			myscore = Integer.parseInt(val);
			score.set(""+myscore);
		}
	}
	
	private void resetbtn(){
			three_g.mAlpha=1f;
			three_t.mAlpha=1f;
			four_g.mAlpha=1f;
			four_t.mAlpha=1f;
			exit_btn.mAlpha=1f;
			set_btn.mAlpha=1f;
			add_coin.mAlpha=1f;
			header.mAlpha=1f;
			icon_btn.mAlpha=1f;
	}
	@Override
	public void onActivate() {
		// TODO 自动生成的方法存根
		Log.v("log","onActive");
		super.onActivate();
		bgmusic.resume();
	}

	@Override
	public void onDeactivate() {
		// TODO 自动生成的方法存根
		Log.v("log","onDeactivate");
		super.onDeactivate();
		bgmusic.pause();
		
	}

	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		Log.v("log","onResume");
		super.onResume();
		
	}

	
	//游戏说明
	private  void showmsg() {
		  
		  AlertDialog.Builder builder = new Builder(mActivity);
		  builder.setTitle("规则说明");
		  builder.setMessage("游戏介绍:\n" +
		  		"「大老二」是仿間非常盛行的一種撲克牌遊戲，這個遊戲規定最大的數字是 2，所以就順口叫大老二！遊戲一開始每個玩家都會拿到 17 張牌，拿到梅花 3 的人多一张可以優先出牌，你可以選擇打煉單、對子、順、同花、葫蘆、鐵隻、同花順等牌形。一開始拿到梅花 3 的玩家先出牌，可以任意打你想打的牌型，輪到你時你只能打出比大且張數相同的牌，當上一個玩家打五張牌的牌型如順、同花、葫蘆、鐵隻、同花順時，玩家就可以打同樣是五張牌的牌型去釘死它。\n" +
		  		"五張牌的牌型先後順序為→同花順＞鐵隻＞葫蘆＞同花＞順牌型介紹：\n" +
		  		"煉單：單一張牌數字由小排到大是 3、4、5、6、7、8、9、10、J、Q、K、A、2。\n" +
		  		"花色由小排到大是梅花，方塊，紅心，黑桃要是數字相同，就得比花色。\n" +
		  		"對子：兩張數字相同的牌形數字大小跟煉單的方式一樣，但如果遇到兩個同數字。就得比花色，比的方式只比一隻。\n" +
		  		"順：5 張連續數字的牌形大小順序→ 2、3、4、5、6 最大，A、2、3、4、5 最小。但要注意的是只到10、J、Q、K、A， 沒有 J、Q、K、A、2 。要是遇到相同的牌型就得比最大的那一張牌的花色，由小排到大是梅花，方塊，紅心，黑桃。\n" +
		  		"同花：5 張花色相同的牌形;\n" +
		  		"葫蘆：3 張數子一樣的牌再加一個對子;\n" +
		  		"鐵隻：4 張數字一樣的牌再加任意一張;\n" +
		  		"牌同花順：5 張連續數字且花色相同的牌;\n" +
		  		"功能鍵介紹：選牌：你想打出去的牌請使用觸控筆點選或取消。\n" +
		  		"出牌：選擇好你要打的牌型再按出牌，就會打出選擇好的牌。\n" +
		  		"放棄：要是輪到你打牌但你不想出牌或者沒有可以打時，選擇放棄就會輪到下一家。\n" +
		  		"分數計算：當其中一個玩家把牌全部打完牌局就結束了，其他的玩家要扣分數，此時只要手上還有幾張牌就得扣牌數X10 的分數。");
		 
		  builder.setPositiveButton("确定", new OnClickListener() {
			   @Override
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
		 });
			  
		  builder.create().show();
	}
}
