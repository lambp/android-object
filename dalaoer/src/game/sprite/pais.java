package game.sprite;

import game.data.paival;

import javax.microedition.khronos.opengles.GL10;

import com.android.angle.AngleCircleCollider;
import com.android.angle.AnglePhysicObject;
import com.android.angle.AngleRotatingSprite;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class pais extends AnglePhysicObject{
	AngleSprite paiimg;
	AngleSpriteLayout pailay;
	public int value,playid,frid;
	public int huase;
	public boolean selected,out;
	AngleVector pos;
	int R;
	paival vals;
	
	public pais(AngleSpriteLayout pai,int fid,AngleVector _pos,int pid) {
		super(0, 1);
		//addSegmentCollider(new AngleSegmentCollider(0,0, pai.roWidth, pai.roHeight));
		addCircleCollider(new AngleCircleCollider(0,0, pai.roWidth/2));
		R = pai.roWidth/2;
		mBounce=0;
		mMass = 0;
		pos = new AngleVector(_pos);
		frid = fid;
		mPosition.set(_pos);
		paiimg = new AngleSprite(pai);
		
		value = (fid+1)%13;
		if(value==1) value = 14;
		if(value==2) value = 15;
		if(value==0) value = 13;
		huase = (fid)/13;
		if(pid==0){
			paiimg.setFrame(fid);
		}else{
			paiimg.setFrame(53);
			//paiimg.mRotation=90;
		}
		selected = false;
		playid = pid;
		
		vals = new paival(value,huase);
		vals.value = frid;
	}
	@Override
	public float getSurface()
	{
		return R * 2;
	}


	@Override
	public void draw(GL10 gl) {
		// TODO 自动生成的方法存根
		paiimg.mPosition.set(mPosition);
		paiimg.draw(gl);
		if(mDie) paiimg.mDie = true;
	}
	double start;
	int spead = 700;
	float cos,sin;
	boolean moving;
	
	public void moveto(AngleVector to){
		float r = (float) Math.sqrt((to.mX-mPosition.mX)*(to.mX-mPosition.mX)+(to.mY-mPosition.mY)*(to.mY-mPosition.mY));
		cos = (to.mX-mPosition.mX)/r;
		sin = (to.mY-mPosition.mY)/r;
		pos.set(to);
		moving=true;
		out = true;
		paiimg.setFrame(frid);
	}
	public void setPos(AngleVector src){
		mPosition.set(src);
		pos.set(src);
	}
	public void setfx(float jd){
		paiimg.mScale=new AngleVector(jd,jd);
	}
	
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		if((System.currentTimeMillis()-start)>200 && moving){
			if(pos.mX!=mPosition.mX){
				mPosition.mX+=cos*spead;
				if(cos>0){
					if(mPosition.mX>pos.mX){
						mPosition.mX=pos.mX;
					}
				}else{
					if(mPosition.mX<pos.mX){
						mPosition.mX=pos.mX;
					}
				}
			}
			if(pos.mY!=mPosition.mY){
				mPosition.mY+=sin*spead;
				if(sin>0){
					if(mPosition.mY>pos.mY){
						mPosition.mY=pos.mY;
					}
				}else{
					if(mPosition.mY<pos.mY){
						mPosition.mY=pos.mY;
					}
				}
			}
		}
		super.step(secondsElapsed);
	}
	
	
	public int getval(){
		return value;
	}
	
	public paival getvals(){
		return vals;
	}
	
	public void setSel(float bili){
		if(selected){
			mPosition.mY+=25*bili;
			selected = false;
		}else{
			selected = true;
			mPosition.mY-=25*bili;
		}
	}
	
	public boolean isselected(){
		return selected;
	}
	public boolean isme(){
		if(playid==0){
			return true;
		}
		return false;
	}
}
