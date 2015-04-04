package game.qipai.bigtwo.v2;

import game.data.tools;
import game.ui.GameUI;
import game.ui.MenuUI;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.angle.AngleActivity;
import com.baidu.mobstat.SendStrategyEnum;
import com.baidu.mobstat.StatService;
public class mainActivity extends AngleActivity {
	public GameUI gameui;
	public MenuUI menuui;
	LinearLayout mainLayout,addview,admain;
	ImageButton adbtn;
	public int lagId;
	boolean isclick=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setbaidu();
    }
	void setbaidu(){
		Log.v("log","start");
		StatService.setAppKey("6f02ed3d2c");
		StatService.setAppChannel("谷歌市场");
		StatService.setSessionTimeOut(30);
		StatService.setOn(this, StatService.EXCEPTION_LOG);
		StatService.setLogSenderDelayed(10);
		StatService.setSendLogStrategy(this, SendStrategyEnum.APP_START, 1, true);
		StatService.setDebugOn(false);
	}
	
 private void getLang(){
    	
    	if(getResources().getConfiguration().locale.getCountry().equals("CN")){
    		lagId=1;
    	}else{
    		lagId=0;
    	}
    }
    private void init(){
    	getLang();
    	setContentView(R.layout.main);
    	mainLayout=(LinearLayout) findViewById(R.id.mainview);
    	addview =(LinearLayout) findViewById(R.id.adview);		
    	admain 	=(LinearLayout) findViewById(R.id.admain);
    	adbtn = (ImageButton)findViewById(R.id.btnclos);
    	admain.setVisibility(View.GONE);
    	adbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("log","closead");
				admain.setVisibility(View.GONE);
				String val = tools.getstrformmem(tools.score,mainActivity.this);
				if(val.equals("null")){
						int myscore = 500;
						tools.savetomem(tools.score,""+myscore, mainActivity.this);
						menuui.score.set(""+myscore);
				}else{
					long now = System.currentTimeMillis();
 					String lasttime = tools.getstrformmem("lastclosetime",mainActivity.this);
 					boolean addtrue=false;
 					if(lasttime.equals("null")){
 						Log.v("log","last=null");
	 					addtrue=true;
 					}else{
 						Log.v("log","last=nonull");
 						long last =Long.parseLong(lasttime) ;
 						if((now-last)>60*30*1000){
 							addtrue=true;
 						}else{
 							Toast.makeText(mainActivity.this,"点击可获取更多金币",Toast.LENGTH_LONG).show();
 						}
 					}
 					
					if(!isclick&&addtrue){
						int myscore = Integer.parseInt(val)+100;
						tools.savetomem(tools.score,""+myscore, mainActivity.this);
						menuui.score.set(""+myscore);
						gameui.score.set(""+myscore);
						
						Toast.makeText(mainActivity.this,"恭喜你获得100金币奖励",Toast.LENGTH_LONG).show();
						tools.savetomem("lastclosetime",""+now,mainActivity.this);
					}
				}
			}
		});
    	
  		mainLayout.addView(mGLSurfaceView);
  		
  		menuui = new MenuUI(this);
  		gameui = new GameUI(this);
  		
  	    setUI(menuui);
    	//监听电话
    	TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE); 
    	phoneListen myPhoneCallListener = new phoneListen(handler);  
    	tm.listen(myPhoneCallListener,PhoneStateListener.LISTEN_CALL_STATE);  
    	
  	   // ADview();
  	   // openadmain();
    }
    //现实广告
    public void openadmain(){
    	isclick=false;
    	admain.setVisibility(View.VISIBLE);
    }
    
    Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch(msg.what){
			case 1:
				//电话来的时候退出
				mainActivity.this.finish();
				System.exit(0);
				break;
			case 2:
				Toast.makeText(mainActivity.this,"游戏中...,不能退出",Toast.LENGTH_LONG).show();
				break;
			case 3:
				
				setUI(menuui);
				
				break;
			}
			super.handleMessage(msg);
		}
    	
    };
    /*
    private void ADview(){
		AdView	adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-1838347844421390/7440846464");
		
		//adView.setAdUnitId("ca-app-pub-9203205924126544/7319603715");
		adView.setAdSize(AdSize.LARGE_BANNER);
 		addview.addView(adView);
 		AdRequest adRequest = new AdRequest.Builder().build();
 		
 		adView.setAdListener(new AdListener() {
 			  @Override
 			  public void onAdLeftApplication() {
 				  	isclick=true;
 				    String val = tools.getstrformmem(tools.score,mainActivity.this);
	 				if(val.equals("null")){
	 					int myscore = 1000;
	 					tools.savetomem(tools.score,""+myscore, mainActivity.this);
	 					Toast.makeText(mainActivity.this,"首次登陆送:"+myscore+" 金币",Toast.LENGTH_LONG).show();
	 					menuui.score.set(""+myscore);
	 				}else{
	 					long now = System.currentTimeMillis();
	 					String lasttime = tools.getstrformmem("lasttime",mainActivity.this);
	 					int score=500;
	 					if(lasttime.equals("null")){
		 					
		 					Toast.makeText(mainActivity.this,"恭喜你获得:"+score+"金币",Toast.LENGTH_LONG).show();
		 					int myscore = Integer.parseInt(val)+score;
		 					tools.savetomem(tools.score,""+myscore, mainActivity.this);
		 					menuui.score.set(""+myscore);
		 					tools.savetomem("lasttime",""+now,mainActivity.this);
	 					}else{
	 						long last =Long.parseLong(lasttime) ;
	 						if((now-last)>60*20*1000){
	 							Toast.makeText(mainActivity.this,"恭喜你获得:"+score+"金币",Toast.LENGTH_LONG).show();
			 					int myscore = Integer.parseInt(val)+score;
			 					tools.savetomem(tools.score,""+myscore, mainActivity.this);
			 					menuui.score.set(""+myscore);
			 					tools.savetomem("lasttime",""+now,mainActivity.this);
	 						}else{
	 							Toast.makeText(mainActivity.this,"请20分钟候再获取金币",Toast.LENGTH_LONG).show();
	 						}
	 					}
	 				}
 			  }
 			});
 	    // 在adView中加载广告请求。
 	    adView.loadAd(adRequest);
 	    
 		//adView.loadAd(new AdRequest());
 		//addview.setVisibility(View.VISIBLE);
	}
	*/
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		if(keyCode==KeyEvent.KEYCODE_BACK){
			   if(mCurrentUI instanceof GameUI){
				   if(gameui.isgameOver()){
					   ExitCheck();
				   }else{
					   handler.sendEmptyMessage(2);
				   }
			   }else if(mCurrentUI instanceof MenuUI){
				      ExitCheck();
			   }
			
			
			return false;
		}
		
		if(keyCode==KeyEvent.KEYCODE_HOME){
			ExitCheck();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	//退出确认
		public  void ExitCheck() {
			  
			  AlertDialog.Builder builder = new Builder(this);
			  builder.setMessage("确定要退出游戏吗？");
			  builder.setTitle("提示");
			  builder.setPositiveButton("是", new OnClickListener() {
				   @Override
				   public void onClick(DialogInterface dialog, int which) {
					   dialog.dismiss();
					   if(mCurrentUI instanceof GameUI){
							   handler.sendEmptyMessage(3);
					   }else if(mCurrentUI instanceof MenuUI){
						       handler.sendEmptyMessage(1);
					   }
				   }
				  });
				  
				  builder.setNegativeButton("否", new OnClickListener() {
					   @Override
					   public void onClick(DialogInterface dialog, int which) {
					    dialog.dismiss();
					   // handler.sendEmptyMessage(2);
					   }
				  });
			  builder.create().show();
		}
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
	}
	
}