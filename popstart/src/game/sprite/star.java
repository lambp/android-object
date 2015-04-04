package game.sprite;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class star extends AngleSprite{
	private AngleVector toPos;
	private int movestep,value,x,y;
	private int[] xy;
	
	public star(AngleSpriteLayout layout,AngleVector _toPos,int _x,int _y,int _value) {
		super(layout);
		// TODO 自动生成的构造函数存根
		toPos=new AngleVector();
		toPos.set(_toPos);
		x = _x;
		y = _y;
		this.mPosition.set(toPos.mX, -x*100-(y%2)*50);
		movestep = 15-x;
		value = _value;
		xy = new int[2];
		xy[0]=x;
		xy[1]=y;
		setFrame(value);
	}
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(this.mPosition.mY!=toPos.mY){
			this.mPosition.mY+=movestep*secondsElapsed;
			movestep+=8;
			if(this.mPosition.mY>toPos.mY) {
				this.mPosition.mY=toPos.mY;
				movestep=2;
			}
		}
		if(this.mPosition.mX!=toPos.mX){
			this.mPosition.mX-=movestep*secondsElapsed;
			movestep+=8;
			if(this.mPosition.mX<toPos.mX) {
				this.mPosition.mX=toPos.mX;
				movestep=2;
			}		}
	}
	public boolean ismove(){
		if(this.mPosition.mX==toPos.mX&&this.mPosition.mY==toPos.mY) return false;
		return true;
	}
	public int[] test(float ex,float ey){
		int[] res= new int[2];
		res[0]=-1;
		res[1]=-1;
		boolean flag = false;
		if(mDie) return res;
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		if (ex > minx && ex < maxx && ey > miny && ey < maxy){
			flag=true;
		}
		if(flag){
			return xy;
		}
		return res;
	}
	public int[] getXY(){
		return xy;
	}
	public int getVal(int _x,int _y){
		if(x==_x&&_y==y){
			return value;
		}else{
			return -1;
		}
	}
	public void setApa(int _x,int _y,boolean show){
		if(x==_x&&_y==y){
			if(show){
				setFrame(value);
				this.mAlpha=1;
			}else{
				this.mAlpha=(float)0.5;
				//setFrame(value+5);
			}
		}
	}
	public void setDie(int _x,int _y){
		if(x==_x&&_y==y){
			this.mDie=true;
		}
	}
	public void setDie(){
		this.mDie=true;
	}
	public void Moveto(int _x,int _y,AngleVector _to){
		x = _x;
		y = _y;
		xy[0]=x;
		xy[1]=y;
		toPos.set(_to.mX,_to.mY);
		movestep=5;
	}
	public boolean check(int _x,int _y){
		if(mDie) return false;
		if(x==_x&&_y==y){
			return true;
		}else{
			return false;
		}
	}
}
