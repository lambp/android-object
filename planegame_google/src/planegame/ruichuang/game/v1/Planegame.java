package planegame.ruichuang.game.v1;

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
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.android.angle.AngleActivity;


public class Planegame extends AngleActivity {
	//public  GameUI gameui;
	FrameLayout mainLayout;
	public MenuUI menuUI;
	public boolean isCn=true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    
    private void getLang(){
    	
    	if(getResources().getConfiguration().locale.getCountry().equals("CN")){
    		isCn=true;
    	}
    }
    
    private void init(){
    	//getLang();
    	mainLayout=new FrameLayout(this);
    	if(mGLSurfaceView!=null){
	  		mainLayout.addView(mGLSurfaceView);
	  		setContentView(mainLayout);
	  		menuUI = new MenuUI(this);
	  	    setUI(menuUI);
    	}
    	//监听电话
    	TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE); 
    	phoneListen myPhoneCallListener = new phoneListen(handler);  
    	tm.listen(myPhoneCallListener,PhoneStateListener.LISTEN_CALL_STATE);  
  	    //ADview();
    }
   
    @Override
	protected void onPause() {
		// TODO 自动生成的方法存根
    	//return 0;
    	menuUI.bgsound.pause();
		super.onPause();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根

		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(mCurrentUI instanceof GameUI){
				setUI(menuUI);
			}else{
				ExitCheck();
			}
			return false;
		}

		 return super.onKeyDown(keyCode, event);
	}
	
    Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch(msg.what){
			case 1:
				//电话来的时候退出
				Planegame.this.finish();
				break;
			}
			super.handleMessage(msg);
		}
    	
    };
    
    private  void ExitCheck() {
		  
		  AlertDialog.Builder builder = new Builder(this);
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
    private void ADview(){
 		LayoutParams params = new LayoutParams(320, 50);
 		//a1516ea457acbb5
 		//AdView	adView = new AdView(this, AdSize.BANNER, "a15191e77c8b1eb");
 		//this.addContentView(adView, params);
 		//adView.loadAd(new AdRequest());
	}
	

}