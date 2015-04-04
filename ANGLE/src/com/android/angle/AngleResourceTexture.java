package com.android.angle;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Creates a texture using drawable resource
 * @author Ivan Pajuelo
 *
 */
public class AngleResourceTexture extends AngleTexture
{
	private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
	public int mResourceID,mX,mY;
	public boolean oppose = false;
	public AngleResourceTexture(AngleTextureEngine textureEngine, int resourceId)
	{
		super(textureEngine);
		mResourceID = resourceId;
		mX = 1;
		mY = 1;
		oppose = false;
	}
	public AngleResourceTexture(AngleTextureEngine textureEngine, int resourceId,int mx,int my)
	{
		super(textureEngine);
		mResourceID = resourceId;
		mX = mx;
		mY = my;
		oppose=true;
	}
	public Bitmap create()
	{
		//Log.e("Texture", "HID:"+mHWTextureID+", RID:"+mResourceID);
		sBitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
		InputStream is = AngleSurfaceView.mContext.getResources().openRawResource(mResourceID);
		Bitmap bitmap;
		try
		{
			if(mX==1&&mY==1){
				Log.v("log", "create");
				bitmap = BitmapFactory.decodeStream(is, null, sBitmapOptions);
			}else{
				Bitmap	img = BitmapFactory.decodeStream(is, null, sBitmapOptions);
				Matrix matrix = new Matrix();  
				matrix.postScale(mX,mY);
				bitmap = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true); 
				img.recycle();
			}
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				Log.e("AngleTextureEngine", "loadTexture::InputStream.close error: " + e.getMessage());
			}
		}
		return bitmap;
	}
}
