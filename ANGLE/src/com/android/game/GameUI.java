package com.android.game;

import android.graphics.Typeface;
import android.view.MotionEvent;

import com.android.angle.AngleActivity;
import com.android.angle.AngleFont;
import com.android.angle.AngleObject;
import com.android.angle.AngleSound;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;
import com.android.angle.AngleUI;
import com.android.angle.AngleVector;

/** 
* @author Ivan Pajuelo
*/
public class GameUI extends AngleUI
{
	private static final float sSightSpeed = 5;
	public Field mField;
	private Sight mSight;
	private AngleObject ogDashboard;
	private AngleSprite sprTrackPad;
	private AngleVector mAim;
	private AngleVector mTrackPadPos;
	private AngleVector mTrackPadDelta;
	private AngleString strScore;
	private AngleString strLifes;
	public AngleSpriteLayout slGround;
	public AngleSpriteLayout slSmiley;
	public AngleSpriteLayout slSight;
	private AngleSound sndMachineGun;
	public int mScore;
	private int mLifes;
	private boolean mInverted;
	
	@Override
	public void onActivate()
	{
		mLifes=3;
		mScore=0;
		strLifes.set(""+mLifes);
		strScore.set(""+mScore);
		super.onActivate();
	}

	public GameUI(AngleActivity activity)
	{
		super(activity);
		
		slGround=new AngleSpriteLayout(mActivity.mGLSurfaceView,com.android.tutorial.R.drawable.fondo);
		slSmiley=new AngleSpriteLayout(mActivity.mGLSurfaceView,32,45,com.android.tutorial.R.drawable.salto,0,0,32,45,8,8); 
		slSight=new AngleSpriteLayout(mActivity.mGLSurfaceView,32,32,com.android.tutorial.R.drawable.mira,0,0,32,32,2,2);

		sndMachineGun=new AngleSound(mActivity,com.android.tutorial.R.raw.machinegun);
		
		AngleFont fntBazaronite=new AngleFont(mActivity.mGLSurfaceView, 18, Typeface.createFromAsset(mActivity.getAssets(),"bazaronite.ttf"), 222, 0, 2, 255, 100, 255, 255);

		mField=(Field)addObject(new Field(this));
		mSight=(Sight)addObject(new Sight(this));
		ogDashboard=addObject(new AngleObject());
		sprTrackPad=(AngleSprite) ogDashboard.addObject(new AngleSprite(160,380,new AngleSpriteLayout(mActivity.mGLSurfaceView,320,200,com.android.tutorial.R.drawable.panel)));

		//STEP 20:
		strScore = (AngleString) ogDashboard.addObject(new AngleString(fntBazaronite, "0", 310, 30, AngleString.aRight));
		strLifes = (AngleString) ogDashboard.addObject(new AngleString(fntBazaronite, "0", 60, 30, AngleString.aRight));
		
		//STEP 21:
		mAim=new AngleVector();
		mTrackPadPos=new AngleVector();
		mTrackPadDelta=new AngleVector();
		
		mInverted=false;
	}
	@Override
	public void step(float secondsElapsed)
	{
		super.step(secondsElapsed);
		mSight.moveTo(mTrackPadPos);
		mTrackPadDelta.set(mTrackPadPos);
		mTrackPadDelta.mul(sSightSpeed*secondsElapsed);
		mAim.add(mTrackPadDelta);
		if (mAim.mX>1)
			mAim.mX=1;
		if (mAim.mX<-1)
			mAim.mX=-1;
		if (mAim.mY>1)
			mAim.mY=1;
		if (mAim.mY<-1)
			mAim.mY=-1;
		mField.moveTo(mAim);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		try
		{
			Thread.sleep(16);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		// -------------------------
		float eX=event.getX();
		float eY=event.getY();
		if (event.getAction()==MotionEvent.ACTION_DOWN)
		{
			if (eX>sprTrackPad.mPosition.mX-sprTrackPad.roLayout.roWidth/2)
				if (eY>sprTrackPad.mPosition.mY-sprTrackPad.roLayout.roHeight/2)
					if (eX<sprTrackPad.mPosition.mX+sprTrackPad.roLayout.roWidth/2)
						if (eY<sprTrackPad.mPosition.mY+sprTrackPad.roLayout.roHeight/2)
						{
							sndMachineGun.play(1,true);
							mSight.fire(true);
						}
		}
		else if (event.getAction()==MotionEvent.ACTION_UP)
		{
			sndMachineGun.stop();
			mSight.fire(false);
			mTrackPadPos.set(0,0);
		}
		else if (event.getAction()==MotionEvent.ACTION_MOVE)
		{
			if (eX>sprTrackPad.mPosition.mX-sprTrackPad.roLayout.roWidth/2)
				if (eY>sprTrackPad.mPosition.mY-sprTrackPad.roLayout.roHeight/2)
					if (eX<sprTrackPad.mPosition.mX+sprTrackPad.roLayout.roWidth/2)
						if (eY<sprTrackPad.mPosition.mY+sprTrackPad.roLayout.roHeight/2)
						{
							if (mInverted)
								mTrackPadPos.set(-(eX-sprTrackPad.mPosition.mX)/(sprTrackPad.roLayout.roWidth/2),-(eY-sprTrackPad.mPosition.mY)/(sprTrackPad.roLayout.roHeight/2));
							else
								mTrackPadPos.set((eX-sprTrackPad.mPosition.mX)/(sprTrackPad.roLayout.roWidth/2),(eY-sprTrackPad.mPosition.mY)/(sprTrackPad.roLayout.roHeight/2));
						}
		}
		return true;
	}

	//STEP 26:
	//This callback updates the number of remaining lives, also see how to return to the menu
	//setting its UI	
	//>PASO 26:
	//>En este callback para actualizar la cantidad de vidas restantes, vemos como volver al men�
	//>estableciendo su UI
	public void updateLifes(int lifes)
	{
		mLifes+=lifes;
		if (mLifes<0)
			((StepByStepGame) mActivity).setUI(((StepByStepGame) mActivity).mTheMenu);
		strLifes.set(""+mLifes);
	}

	public void updateScore(int score)
	{
		mScore+=score;
		strScore.set(""+mScore);
	}

	//STEP 27:
	//Overload the onDeactivate event to empty the smileys' list and stop the sound
	//if it is playing
	//>PASO 27:
	//>Sobrecargamos el evento onDeactivate para vaciar la lista de smileys y parar el sonido
	//>si est� sonando
	@Override
	public void onDeactivate()
	{
		sndMachineGun.stop();
		for (int s=1;s<mField.count();s++)
			mField.childAt(s).mDie=true;
		super.onDeactivate();
	}

	///Continues in FIeld)
	//(continua en Field)
}
