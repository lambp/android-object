package com.android.angle;
//中文显示类
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Have the string and its position. Length is automatically set when string content is changed. But can be altered to create typing effect.
 * 
 * @author 罗志坚
 * 
 */
public class AnglecnString extends AngleObject
{
	public static final int aLeft = 0;
	public static final int aCenter = 1;
	public static final int aRight = 2;
	protected String mString;
	protected String mWantString;
	public int mLength; // Length to display
	protected AngleFont mFont; // Font
	protected int[] mTextureIV = new int[4]; // Texture coordinates
	public AngleVector mPosition; // Position
	public float mZ; // Z position (0=Near, 1=Far)
	public int mAlignment; // Text alignment
	public float mRed; // Red tint (0 - 1)
	public float mGreen; // Green tint (0 - 1)
	public float mBlue; // Blue tint (0 - 1)
	public float mAlpha; // Alpha channel (0 - 1)
	public int mDisplayWidth;
	public int mDisplayLines;
	protected boolean mIgnoreNL;


	/**
	 * @param font AngleFont
	 * @param string The text
	 * @param x Position
	 * @param y Position
	 */
	public AnglecnString(AngleFont font, String string, int x, int y)
	{
		init(font, 3, false);
		mPosition.set(x,y); 
		mFont.mString = string;
		mFont.isChinese = true;
		mLength = string.length();
	}
	private void init(AngleFont font, int tabLength, boolean ignoreNL)
	{
		mPosition = new AngleVector();
		mFont = font;
		mAlignment = aLeft;
		mRed = 1;
		mGreen = 1;
		mBlue = 1;
		mAlpha = 1;
		mIgnoreNL = ignoreNL;
	}
	

	//碰撞检测
	public boolean test(float x, float y)
	{
		if(x>=mPosition.mX && x<(mPosition.mX+mFont.mFontSize*mFont.mString.length()) && y>=mPosition.mY && y<(mPosition.mY+mFont.mFontSize+5)){
			return true;
		}
		return false;
	}
	
	//显示文字
	private void drawfont(GL10 gl){
		mTextureIV[0] = 0;
		mTextureIV[1] = (int) mFont.mFontSize+5;
		mTextureIV[2] = (int) (mFont.mFontSize*mFont.mString.length());
		mTextureIV[3] = -((int) mFont.mFontSize+5);
		((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, GL11Ext.GL_TEXTURE_CROP_RECT_OES, mTextureIV, 0);

		((GL11Ext) gl).glDrawTexfOES(mPosition.mX,mFont.sHeight-mPosition.mY-mFont.mFontSize, 0, (int) (mFont.mFontSize*mFont.mString.length()), ((int) mFont.mFontSize+5));
	
	}
	

	@Override
	public void draw(GL10 gl)
	{
		if (mFont != null)
		{
			if (mFont.mTexture != null)
			{
				if (mFont.mTexture.mHWTextureID > -1)
				{
						if (mLength>0)
						{
							gl.glBindTexture(GL10.GL_TEXTURE_2D, mFont.mTexture.mHWTextureID);
							gl.glColor4f(mRed, mGreen, mBlue, mAlpha);
							
							drawfont(gl);	
						}
				}
				else
					mFont.mTexture.linkToGL(gl);
			}
		}
		super.draw(gl);
	}
	

}
