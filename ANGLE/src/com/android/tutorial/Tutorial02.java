package com.android.tutorial;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.android.angle.AngleActivity;
import com.android.angle.AngleDrawLine;
import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;

/**
 * Override an Angle class and create our first animated object
 * >Sobrecargamos una clase Angle para crear nuestro primer objeto animado
 * 
 * 
 */
public class Tutorial02 extends AngleActivity
{
	private class MyAnimatedSprite extends AngleRotatingSprite
	{
		public MyAnimatedSprite(int x, int y, AngleSpriteLayout layout)
		{
			super(x, y, layout);
		}

		@Override
		public void step(float secondsElapsed)
		{
			mRotation+=secondsElapsed*10;//10� per second
			super.step(secondsElapsed);
		}
		
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mGLSurfaceView.addObject(new MyAnimatedSprite (160, 200, new AngleSpriteLayout(mGLSurfaceView, R.drawable.anglelogo)));
		
		mGLSurfaceView.addObject(new AngleDrawLine());
		
		FrameLayout mMainLayout=new FrameLayout(this);
		mMainLayout.addView(mGLSurfaceView);
		setContentView(mMainLayout);
	}
}
