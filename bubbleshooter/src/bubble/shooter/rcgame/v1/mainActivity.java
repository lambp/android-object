package bubble.shooter.rcgame.v1;

import game.ui.GameUI;
import game.ui.mapUI;
import game.ui.menuUI;
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
import android.widget.LinearLayout;

import com.android.angle.AngleActivity;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;



public class mainActivity extends AngleActivity {
	public GameUI gameui;
	public menuUI menuui;
	public mapUI mapui;
	LinearLayout mainLayout,addview;
	public int lagId=0;
	//private InterstitialAd interstitial;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
       // ADview();
       // showad();
       // displayInterstitial();
    }

   private void getLang(){
    	String lang = getResources().getConfiguration().locale.getCountry();
    	if(lang.equals("CN")||lang.equals("TW")){
    		lagId=0;
    	}else{
    		lagId=1;
    	}
    }
   /*
   public void displayInterstitial() {
	    if (interstitial.isLoaded()) {
	    	Log.v("log","loaded");
	      interstitial.show();
	    }else{
	    	Log.v("log","no loaded");
	    }
	  }

   
   private void showad(){
	// 制作插页式广告。
	    interstitial = new InterstitialAd(this);
	    interstitial.setAdUnitId("ca-app-pub-9203205924126544/7319603715");

	    // 创建广告请求。
	    AdRequest adRequest = new AdRequest.Builder().build();

	    // 开始加载插页式广告。
	    interstitial.loadAd(adRequest);

   }
   */
    private void init(){
    	//getLang();
    	setContentView(R.layout.main);
    	mainLayout=(LinearLayout) findViewById(R.id.mainview);
    	addview =(LinearLayout) findViewById(R.id.adview);		
  		mainLayout.addView(mGLSurfaceView);
  		menuui = new menuUI(this);
  		mapui = new mapUI(this);
  		
  	    setUI(menuui);
    	//监听电话
    	TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE); 
    	phoneListen myPhoneCallListener = new phoneListen(handler);  
    	tm.listen(myPhoneCallListener,PhoneStateListener.LISTEN_CALL_STATE);  
  	  
    }
    
    Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch(msg.what){
			case 1:
				//电话来的时候退出
				mainActivity.this.finish();
				break;
			case 2:
				setUI(mapui);
				break;
			case 3:
				setUI(menuui);
				break;
				
			}
			super.handleMessage(msg);
		}
    	
    };
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		if(keyCode==KeyEvent.KEYCODE_BACK){
			  	   if(mCurrentUI instanceof GameUI){
					   handler.sendEmptyMessage(2);
				   }else if(mCurrentUI instanceof mapUI){
					   handler.sendEmptyMessage(3);
				   }
			return false;
		}
		
		if(keyCode==KeyEvent.KEYCODE_HOME){
			
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
  //退出确认
  		public  void ExitCheck() {
  			  
  			  AlertDialog.Builder builder = new Builder(this);
  			  builder.setMessage("Are you sure exit game?");
  			  builder.setTitle("");
  			  builder.setPositiveButton("yes", new OnClickListener() {
  				   @Override
  				   public void onClick(DialogInterface dialog, int which) {
  					   dialog.dismiss();
  					   if(mCurrentUI instanceof GameUI){
  						   handler.sendEmptyMessage(3);
  					   }else if(mCurrentUI instanceof mapUI){
  						   handler.sendEmptyMessage(1);
  					   }else{
  						 handler.sendEmptyMessage(1);
  					   }
  				   }
  				  });
  				  
  				  builder.setNegativeButton("no", new OnClickListener() {
  					   @Override
  					   public void onClick(DialogInterface dialog, int which) {
  					    dialog.dismiss();
  					   // handler.sendEmptyMessage(2);
  					   }
  				  });
  			  builder.create().show();
  		}
  		/*
   private void ADview(){
 	
	   			AdView	adView = new AdView(this);
				adView.setAdUnitId("ca-app-pub-9203205924126544/7319603715");
				adView.setAdSize(AdSize.LARGE_BANNER);
		 		addview.addView(adView);
		 		AdRequest adRequest = new AdRequest.Builder().build();
		 		adView.loadAd(adRequest);
		 		
	}
    
 */
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		menuui.bgsound.pause();
	}
}