package org.kaka.popstar2015;

import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class phoneListen extends PhoneStateListener { 
		Handler _handler;
		public phoneListen(Handler handler){
			_handler = handler;
		}
		public void onCallStateChanged(int state, String incomingNumber) {  
			switch (state) {   
		       case TelephonyManager.CALL_STATE_IDLE ://待机   
		          break;   
		       case TelephonyManager.CALL_STATE_OFFHOOK ://挂断   
		          break;   
		       case TelephonyManager.CALL_STATE_RINGING ://来电话   
		         //来电免打扰程序要处理的关键事务   
		    	   _handler.sendEmptyMessage(1);
		          break;   
		       default :   
		         break;   
		    }   
		   super.onCallStateChanged(state, incomingNumber);   
		  }
}
