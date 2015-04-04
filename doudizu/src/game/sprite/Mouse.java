package game.sprite;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.angle.AngleCircleCollider;
import com.android.angle.AnglePhysicObject;
import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSpriteLayout;

public class Mouse extends AnglePhysicObject{
	AngleRotatingSprite paiimg;
	AngleSpriteLayout pailay;
	
	public Mouse(AngleSpriteLayout pai) {
		super(0, 1);
		//addSegmentCollider(new AngleSegmentCollider(0,0, 10, 10));
		addCircleCollider(new AngleCircleCollider(0,0, 10));
		paiimg = new AngleRotatingSprite(pai);
		paiimg.mAlpha =0;
	}
	
	@Override
	public float getSurface()
	{
		return 10 * 2; 
	}
	@Override
	public void draw(GL10 gl) {
		// TODO 自动生成的方法存根
		paiimg.mPosition.set(mPosition);
		paiimg.draw(gl);
	}

	@Override
	public boolean collide(AnglePhysicObject other) {
		// TODO 自动生成的方法存根
		
		//Log.v("log","v="+((pais)other).getval());
		
		return super.collide(other);
	}

	@Override
	public boolean test(AnglePhysicObject other) {
		// TODO 自动生成的方法存根
		Log.v("log","test---2");
		return super.test(other);
	}


}
