package org.kaka.popstar2015;

import game.ui.GameUI;
import game.ui.MenuUI;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.LinearLayout;

import com.android.angle.AngleActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import org.kaka.popstar2015.R;

public class PopstartActivity extends AngleActivity {
	public GameUI gameui;
	public MenuUI menuui;

	public int lagId,langid;
	LinearLayout mainLayout,addview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    	
    }
    
 private void getLang(){
    	String lang = getResources().getConfiguration().locale.getCountry();
    	if(lang.equals("CN")||lang.equals("TW")){
    		lagId=0;
    		langid=1;
    	}else{
    		langid=0;
    		lagId=1;
    	}
    }
 
    private void init(){
    	setContentView(R.layout.main);
    	
    	mainLayout=(LinearLayout) findViewById(R.id.mainview);
    	addview =(LinearLayout) findViewById(R.id.adview);		
  		mainLayout.addView(mGLSurfaceView);
  		
    	getLang();    
  	
  		menuui = new MenuUI(this);
  		gameui = new GameUI(this);
  		
  	    setUI(menuui);
    	//监听电话
    	TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE); 
    	phoneListen myPhoneCallListener = new phoneListen(handler);  
    	tm.listen(myPhoneCallListener,PhoneStateListener.LISTEN_CALL_STATE); 
    	
  	    ADview();
    }
    
    Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch(msg.what){
			case 1:
				//电话来的时候退出
				PopstartActivity.this.finish();
				break;
			}
			super.handleMessage(msg);
		}
    };
    
     private void ADview(){
    	 
 		AdView	adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-9203205924126544/4535538913");		
		adView.setAdSize(AdSize.BANNER);
		addview.addView(adView);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);		
		
	}
    
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		if(menuui.bgmusic.isPlaying()){
			menuui.bgmusic.pause();
		}
		super.onPause();
	}
	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		if(!menuui.bgmusic.isPlaying()){
			menuui.bgmusic.play(0.5f, true);
		}
		super.onResume();
		
	}
}