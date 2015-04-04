package com.android.game;

import javax.microedition.khronos.opengles.GL10;

import com.android.angle.AngleObject;
import com.android.angle.AngleSprite;
import com.android.angle.AngleVector;

/** 
* @author Ivan Pajuelo
*/
public class Field extends AngleObject
{
	private static final float sSmileySpawn = 1.3f;
	private float smileyTO;
	private GameUI mGame;
	public AngleSprite mGround;
	public Field(GameUI game)
	{
		super(100); //99 smileys como m醲imo + 1 fondo
		mGame=game;

		mGround=(AngleSprite)addObject(new AngleSprite(160,240,mGame.slGround));
	}

	@Override
	public void step(float secondsElapsed)
	{
		smileyTO-=secondsElapsed;
		if (smileyTO<0)
		{
			addObject(new Smiley(mGame));
			smileyTO=sSmileySpawn;
		}
		super.step(secondsElapsed);
	}

	public void moveTo(AngleVector mSight)
	{
		mGround.mPosition.set(160-mSight.mX*(256-160),240-mSight.mY*(256-240));
		for (int s=1;s<count();s++)
			((Scrollable)childAt(s)).place();
	}

	public void shotAt(AngleVector mSight, int mWeapon)
	{
		float sX=(256-mGround.mPosition.mX)+mSight.mX;
		float sY=(256-mGround.mPosition.mY)+mSight.mY;
		for (int s=1;s<count();s++)
			((Smiley)childAt(s)).shotAt(sX,sY);
	}

	@Override
	public void draw(GL10 gl)
	{
		mChilds[0].draw(gl);
		for (int t=mChildsCount-1;t>0;t--)
			mChilds[t].draw(gl);
	}
	

}
