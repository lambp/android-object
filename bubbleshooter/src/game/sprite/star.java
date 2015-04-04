package game.sprite;

import game.data.Mapij;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.angle.AngleCircleCollider;
import com.android.angle.AnglePhysicObject;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class star extends AnglePhysicObject{
	AngleSprite _ball;
	AngleSpriteLayout _layout;
	float R;
	public int value;
	public Mapij mapid;
	public star(AngleSpriteLayout _layout,int id,AngleVector Start,float _r,Mapij _mid) {
		super(0, 1);
		_ball = new AngleSprite(_layout);
		_ball.mScale.set(0.6f, 0.6f);
		_ball.mAlpha=0;
		mPosition.set(Start);
		R = _r;
		addCircleCollider(new AngleCircleCollider(0, 0, _r));
		mBounce=0;
		mMass = 0;
		value = id;
		mapid = _mid;
		mVelocity.set(new AngleVector(0, 0));
		
		
	}
	@Override
	public float getSurface()
	{
		return R * 2; // Radius * 2  >Radio * 2
	}
	
	@Override
	public void draw(GL10 gl)
	{	
		//if(mPosition.mY<-100) mDie = true;
		_ball.mPosition.set(mPosition);
		_ball.draw(gl);
		if(mDie) _ball.mDie=true;
	}
	public void setMass(float mass){
		mMass = mass;
	}
	public void setMapid(int _x,int _y){
		mapid.i=_x;
		mapid.j=_y;
	}
	
	public void setPos(AngleVector pos){
		Log.v("log", "px="+mapid.i+","+mapid.j);
		mPosition.set(pos);
	}
	public void setShow(boolean af){
		if(af){
			_ball.mAlpha = 1;
		}else{
			_ball.mAlpha = 0;
		}
	}
	public boolean check(int _x,int _y){
		//Log.v("log","star-xy"+mapid.x+","+mapid.y);
		if(mapid.i==_x&&mapid.j==_y) return true;
		return false;
	}
	
}
