package com.android.game;

import android.graphics.Typeface;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleFont;
import com.android.angle.AngleObject;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;
import com.android.angle.AngleUI;

/** 
* @author Ivan Pajuelo
*/
public class MenuUI extends AngleUI
{

	private AngleObject ogMenuTexts;
	

	private AngleString strPlay;
	private AngleString strHiScore;
	private AngleString strExit;

	private int mHiScore;

	public MenuUI(AngleActivity activity)
	{
		super(activity);
		ogMenuTexts = new AngleObject();
		
		addObject(new AngleSprite(160, 240, new AngleSpriteLayout(mActivity.mGLSurfaceView, 320, 480, com.android.tutorial.R.drawable.bg_menu)));

		addObject(ogMenuTexts);

		AngleFont fntCafe=new AngleFont(mActivity.mGLSurfaceView, 25, Typeface.createFromAsset(mActivity.getAssets(),"cafe.ttf"), 222, 0, 0, 30, 200, 255, 255);
		
		strPlay = (AngleString) ogMenuTexts.addObject(new AngleString(fntCafe, "Play", 160, 180, AngleString.aCenter));
		strHiScore = (AngleString) ogMenuTexts.addObject(new AngleString(fntCafe, "Hi Score", 160, 210, AngleString.aCenter));
		strExit = (AngleString) ogMenuTexts.addObject(new AngleString(fntCafe, "Exit", 160, 390, AngleString.aCenter));
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			float eX = event.getX();
			float eY = event.getY();

			if (strPlay.test(eX, eY))((StepByStepGame) mActivity).setUI(((StepByStepGame) mActivity).mTheGame);
			
		}
		return false;
	}

	@Override
	public void onActivate()
	{
	/*	if (((StepByStepGame) mActivity).mTheGame.mScore>mHiScore)
				mHiScore=((StepByStepGame) mActivity).mTheGame.mScore;
		strHiScore.set("Hi Score: "+mHiScore);
		super.onActivate();*/
	}
	
}
