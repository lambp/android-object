package game.sprite;

import android.util.Log;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class shaizi extends AngleSprite{
	private double start;
	private int frame=0;
	private AngleVector toPoint;
	public boolean ismove=false;
	private double cos,sin;
	private float movesteps=280;
	
	public shaizi(AngleSpriteLayout layout) {
		super(layout);
		 toPoint = new AngleVector();
		 mPosition.set(-900,-900);
		 toPoint.set(-900,-900);
	}
	//设置坐标-改变方向
	public void moveto(AngleVector from,AngleVector to){
			mPosition.set(from);
			toPoint.set(to);
			double dx= Math.abs(mPosition.mX-toPoint.mX),dy= Math.abs(mPosition.mY-toPoint.mY);
			double Dxy = Math.sqrt(dx*dx+dy*dy);
			cos = dx/Dxy;
			sin = dy/Dxy;
			ismove = true;
			Log.v("log", "moveto");
	}
	
	private void setPosition(AngleVector src){
		this.mPosition.set(src);
	}
	
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		moving(secondsElapsed);
	}
	private void moving(float secondsElapsed){
		if((System.currentTimeMillis()-start)>10&&ismove){
			setFrame(frame);
			start=System.currentTimeMillis();
			frame++;
			if(frame>9)frame=0;
			start=System.currentTimeMillis();
			AngleVector toPos = new AngleVector();
			if(this.mPosition.mX!=toPoint.mX||this.mPosition.mY!=toPoint.mY){
				if(this.mPosition.mX!=toPoint.mX && this.mPosition.mY==toPoint.mY){
					if(toPoint.mX>mPosition.mX){
						toPos.mX = (float) (this.mPosition.mX+secondsElapsed*movesteps*cos); 
						if(toPos.mX>toPoint.mX) toPos.mX=toPoint.mX;
					}else{
						toPos.mX = (float) (this.mPosition.mX-secondsElapsed*movesteps*cos); 
						if(toPos.mX<toPoint.mX) toPos.mX=toPoint.mX;
					}
					toPos.mY=toPoint.mY;
				}
				
				if(this.mPosition.mY!=toPoint.mY && this.mPosition.mX==toPoint.mX){
					if(toPoint.mY>mPosition.mY){
						toPos.mY = (float) (this.mPosition.mY+secondsElapsed*movesteps*sin); 
						if(toPos.mY>toPoint.mY) toPos.mY=toPoint.mY;
					}else{
						toPos.mY = (float) (this.mPosition.mY-secondsElapsed*movesteps*sin); 
						if(toPos.mY<toPoint.mY) toPos.mY=toPoint.mY;
					}
					toPos.mX=toPoint.mX;
				}
				if(this.mPosition.mY!=toPoint.mY && this.mPosition.mX!=toPoint.mX){
					if(toPoint.mX>mPosition.mX){
						toPos.mX = (float) (this.mPosition.mX+secondsElapsed*movesteps*cos); 
						if(toPos.mX>toPoint.mX) toPos.mX=toPoint.mX;
					}else{
						toPos.mX = (float) (this.mPosition.mX-secondsElapsed*movesteps*cos); 
						if(toPos.mX<toPoint.mX) toPos.mX=toPoint.mX;
					}
					if(toPoint.mY>mPosition.mY){
						toPos.mY = (float) (this.mPosition.mY+secondsElapsed*movesteps*sin); 
						if(toPos.mY>toPoint.mY) toPos.mY=toPoint.mY;
					}else{
						toPos.mY = (float) (this.mPosition.mY-secondsElapsed*movesteps*sin); 
						if(toPos.mY<toPoint.mY) toPos.mY=toPoint.mY;
					}
				}
				
				setPosition(toPos);
			}
			
			if(this.mPosition.mY==toPoint.mY&&this.mPosition.mX==toPoint.mX){
				  ismove=false;
				  AngleVector out = new AngleVector();
				  out.mX = -900;
				  out.mY = -900;
				  mPosition.set(out);
				  toPoint.set(out);
			}
			
		}
	}
}
