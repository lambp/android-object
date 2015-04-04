package danji.doudizhu.game.v1;

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
import android.widget.LinearLayout;

import com.android.angle.AngleActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class mainActivity extends AngleActivity {
	public GameUI gameui;
	public MenuUI menuui;
	LinearLayout mainLayout,addview;
	public int lagId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
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
    		//	new RelativeLayout(R.layout.main);
  		mainLayout.addView(mGLSurfaceView);
  		
  		gameui = new GameUI(this);
  		menuui = new MenuUI(this);
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
				mainActivity.this.finish();
				break;
			case 2:
				//gameui.exit();
				//setUI(mapui);
				break;
			case 3:
				setUI(menuui);
				break;
			}
			super.handleMessage(msg);
		}
    	
    };
    private void ADview(){

 		AdView	adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-9203205924126544/7319603715");
		adView.setAdSize(AdSize.LARGE_BANNER);
 		addview.addView(adView);
 		AdRequest adRequest = new AdRequest.Builder().build();
 		adView.loadAd(adRequest);
	}
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		if(keyCode==KeyEvent.KEYCODE_BACK){
			ExitCheck();
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
		gameui.gamebgsound.pause();
		menuui.bgsound.pause();
	}
	
}