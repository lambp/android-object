package game.data;

import game.sprite.star;
import game.ui.GameUI;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.android.angle.AngleCircleCollider;
import com.android.angle.AnglePhysicObject;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class ball extends AnglePhysicObject{
	AngleSprite _ball;
	AngleSpriteLayout layout;
	float R;
	public int value;
	public Mapij mapij;
	public boolean isdrop;
	private int height;
	
	public ball(AngleSpriteLayout _layout,int _val,AngleVector pos,float _r,Mapij _ij,int _height) {
		super(0, 1);
		layout = _layout;
		_ball = new AngleSprite(_layout);
		_ball.setFrame(_val);
		mPosition.set(pos);
		R = _r;
		addCircleCollider(new AngleCircleCollider(0, 0, _r-3));
		mBounce=0;
		mMass = 0;
		value =  _val;
		mapij = _ij;
		mVelocity.set(new AngleVector(0, 0));
		isdrop = false;
		height = _height+10;
		//_ball.mAlpha=0.8f;
	}
	@Override
	public float getSurface()
	{
		return R * 2; // Radius * 2  >Radio * 2
	}
	
	@Override
	public void draw(GL10 gl)
	{	
		if(mDie){
			_ball.mDie=true;
		}
		_ball.mPosition.set(mPosition);
		_ball.draw(gl);
		if(mPosition.mY<-400||(mPosition.mY>height+20)||mPosition.mX>height||mPosition.mX<-100){
			mDie=true;
			_ball.mDie=true;
		}
		if(isdrop){
			if(mVelocity.mY<=100) mVelocity.mY+=10;
			_ball.mAlpha-=0.01;
			if(_ball.mAlpha<0.3){
				((GameUI)((AnglePhysicsEngine)mParent).mParent).showCoins(mPosition);
				mDie=true;
				_ball.mDie=true;
			}
		}
	}
	public void setMass(float mass){
		mMass = mass;
	}
	public void setMove(AngleVector to){
		mVelocity.add(to);
		mMass=5;
		mBounce=0.5f;
	}
	public void setPos(AngleVector pos){
		mPosition.set(pos);
	}
	@Override
	public boolean collide(AnglePhysicObject other) {
		// TODO 自动生成的方法存根
		if ((other instanceof ball) ){
			if (this.test((ball)other)&&!((ball)other).isdrop&&!isdrop){
					mVelocity.set(new AngleVector(0, 0));
					mBounce=0;
					mMass = 0;
					other.mBounce=0;
					other.mMass = 0;
					check((ball) other);
					//((GameUI)((AnglePhysicsEngine)mParent).mParent).setpopmove();
			}
		}
		else if((other instanceof star)){
			if (this.test((star)other)){
				mVelocity.set(new AngleVector(0, 0));
				mBounce=0;
				mMass = 0;
				mapij.i=((star)other).mapid.i;
				mapij.j=((star)other).mapid.j;
				((star)other).mPosition.mY=-100;
				((star)other).mDie=true;
				AngleVector poss = ((GameUI)((AnglePhysicsEngine)mParent).mParent).getPos(mapij.i,mapij.j);
			    setPos(poss);
				((GameUI)((AnglePhysicsEngine)mParent).mParent).initval[mapij.i][mapij.j]=value;
			    ((GameUI)((AnglePhysicsEngine)mParent).mParent).removeBall(mapij.i,mapij.j);
			    //((GameUI)((AnglePhysicsEngine)mParent).mParent).setpopmove();
			}
		}else{
			if (this.test(other)){
				((GameUI)((AnglePhysicsEngine)mParent).mParent).playsound(2);
			}
		}
			
		return super.collide(other);
	}
	
	private void check(ball other){
		int x=	other.mapij.i;
		int y = other.mapij.j;
		try{
			int _x=-1,_y=-1;
			int dx = (int) (mPosition.mX-other.mPosition.mX);
			if(0==x%2){
				if(Math.abs(dx)>1.2*R){
					if(dx>0) {_y=y+1;}else{_y=y-1;}
					_x = x;
				}else{
					if(dx>0) {_y=y;}else{_y=y-1;}
					_x = x+1;
				}
			}else{
				if(Math.abs(dx)>1.2*R){
					if(dx<0) {_y=y-1;}else{_y=y+1;}
					_x = x;
				}else{
					if(dx>0) {_y=y+1;}else{_y=y;}
					_x = x+1;
				}
			}
		//	Log.v("log","x_y="+_x+","+_y);
			if(_y<0) _y=0;
			if(_x<10){
				mapij.i=_x;
				mapij.j=_y;
				((GameUI)((AnglePhysicsEngine)mParent).mParent).initval[_x][_y]=value;
			    setPos(((GameUI)((AnglePhysicsEngine)mParent).mParent).getPos(_x,_y));
			    ((GameUI)((AnglePhysicsEngine)mParent).mParent).removeBall(_x,_y);
			    
			}else{
				((GameUI)((AnglePhysicsEngine)mParent).mParent).setGameOver();
			}
		}catch(Exception e){
			Log.v("log", "e="+e.getMessage());
		}
	}
	public boolean checkSelect(int x,int y){

		if(mapij.i==x&&mapij.j==y) return true;
		return false;
	}
	public boolean checkXY(Mapij cid){
		if(mapij.i==cid.i&&mapij.j==cid.j) return true;
		return false;
	}
	public void drop(int x){
		isdrop = true;
		mapij.i = -1;
		mVelocity.add(new AngleVector(0, 150));
		
	}
	public boolean checkMove(){
		if((mVelocity.mX>0||mVelocity.mY>0)&&!mDie) {
			return true;
		}
		return false;
	}
}
