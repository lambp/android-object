package com.android.tutorial;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.android.angle.AngleActivity;
import com.android.angle.AngleDrawLine;
import com.android.angle.AngleFont;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AnglecnString;

/**
 * This tutorial demonstrates how to create an ANGLE engine and add a sprite to render.
 * In this example we use the main GL view directly
 * >Este tutorial demuestra como crear una instancia del motor usando una AngleActivity
 * >y como a�adir un AngleSprite para que se pinte.
 * 
 * @author Ivan Pajuelo
 * 
 */
public class Tutorial01 extends AngleActivity
{
	private AnglecnString play;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*AngleSpriteLayout mLogoLayout2 = new AngleSpriteLayout(mGLSurfaceView,mGLSurfaceView.roWidth,mGLSurfaceView.roHeight ,R.drawable.bg_menu,0,0,344,512);
		
		AngleSprite mLogo2 = new AngleSprite (mLogoLayout2);
		mLogo2.mPosition.set(mGLSurfaceView.roWidth/2,mGLSurfaceView.roHeight/2); 
		mGLSurfaceView.addObject(mLogo2);
		
		
		AngleSpriteLayout mLogoLayout = new AngleSpriteLayout(mGLSurfaceView,68,80 ,R.drawable.pet_bear,0,0,138,217);
		mLogoLayout.changeTextureY( R.drawable.pet_bear);
		
		AngleSprite mLogo = new AngleSprite (mLogoLayout);
		mLogo.mPosition.set(100, 100); 
		
		mGLSurfaceView.addObject(mLogo);
		
		
		//AngleFont fntCafe=new AngleFont(mGLSurfaceView, 25, Typeface.createFromAsset(getAssets(),"cafe.ttf"), 222, 0, 0, 30, 200, 255, 255);
		//mGLSurfaceView.addObject(new AngleString(fntCafe, "Play", 160, 180, AngleString.aCenter));
		
		AngleFont fntCafe=new AngleFont(mGLSurfaceView, 18, Typeface.createFromAsset(getAssets(),"cafe.ttf"),  255, 0, 10);
		AngleFont fntCafe2=new AngleFont(mGLSurfaceView, 25, Typeface.createFromAsset(getAssets(),"cafe.ttf"),  255, 10, 10);
		AngleFont fntCafe3=new AngleFont(mGLSurfaceView, 25, Typeface.createFromAsset(getAssets(),"cafe.ttf"), 255, 30, 10);
		
		mGLSurfaceView.addObject(new AnglecnString(fntCafe, "高级2", mGLSurfaceView.roWidth/2, 10));
		play = (AnglecnString) mGLSurfaceView.addObject(new AnglecnString(fntCafe2, "开始", mGLSurfaceView.roWidth/2, 40));
		mGLSurfaceView.addObject(new AnglecnString(fntCafe3, "退出", mGLSurfaceView.roWidth/2, 80));
		mGLSurfaceView.addObject(new AnglecnString(fntCafe3, "退出22", mGLSurfaceView.roWidth/2, 180));
		*/
		mGLSurfaceView.addObject(new AngleDrawLine());
		
		setContentView(mGLSurfaceView);

	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			float eX = event.getX();
			float eY = event.getY();

			if (play.test(eX, eY)){

				//AnglePopupWindow pw =	new AnglePopupWindow(Tutorial01.this,com.android.tutorial.R.layout.pop_fight);
				//pw.setBg(getResources().getDrawable(R.drawable.panel));
				//pw.setWH(200, 300);
				//pw.show(mGLSurfaceView,0,100);
				
				
			}
			

			return true;
		}
		return false;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	
/*	@Override
	public void onAttachedToWindow() {
		// TODO 自动生成的方法存根
		//(this.getWindow()).setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		Log.v("log", "onAttachedToWindow");
		super.onAttachedToWindow();
		
	}*/
	@Override
	public Window getWindow() {
		// TODO 自动生成的方法存根
		return super.getWindow();
	}
	@Override
	public WindowManager getWindowManager() {
		// TODO 自动生成的方法存根
		return super.getWindowManager();
		
	}
	/*@Override
	  protected void onSaveInstanceState(Bundle outState){
	             outState.putString("lastPath", "/sdcard/android123/cwj/test");
	   }


	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
			super.onRestoreInstanceState(savedInstanceState);

			String cwjString = savedInstanceState.getString("lastPath");
	} */
}
