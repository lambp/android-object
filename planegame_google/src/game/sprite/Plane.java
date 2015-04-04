package game.sprite;

import android.util.Log;

import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class Plane extends AngleSprite{
	boolean isStartplay; //是否已经起飞
	int line_id;         //当前所在的路线图的点
	int play;            //所属玩家
	private boolean isEnd;	  //到达目的地
	private AngleVector Initxy; //初始位置
	private double start;
	private AngleVector toPoint;//目的地
	private boolean ismove=false;
	private double cos,sin;
	private int initframe;
	public Plane(float mX, float mY, AngleSpriteLayout layout,int player) {
		super(layout);
		this.mPosition.set(mX, mY);
		toPoint = new AngleVector();
		toPoint.set(mX, mY);
		isStartplay =  false;
		isEnd = false;
		line_id = -2;
		setFrame(1);
		play = player;
		Initxy = new AngleVector();
		Initxy.set(mX,mY);
		// TODO 自动生成的构造函数存根
	}
	//设置坐标-改变方向
	public void setPos(AngleVector src,int stepid){
		line_id = stepid;
		if(src!=null){
			toPoint.set(src);
			double dx= Math.abs(mPosition.mX-toPoint.mX),dy= Math.abs(mPosition.mY-toPoint.mY);
			double Dxy = Math.sqrt(dx*dx+dy*dy);
			cos = dx/Dxy;
			sin = dy/Dxy;
			ismove=true;
			int fid =getFrame(play);
			if(fid>=0) setFrame(fid);
		}
	}
	private void setPosition(AngleVector src){
		this.mPosition.set(src);
	}
	float movesteps=180;
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		
		if((System.currentTimeMillis()-start)>10&&ismove){
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
			
			if(this.mPosition.mY==toPoint.mY&&this.mPosition.mX==toPoint.mX) ismove=false;
			
		}
	}
	
		public AngleVector getPos(){
			return mPosition;
		}
	//设置起飞
	public void startFly(AngleVector src){
		if(!isStartplay){
			//this.mPosition.set(src);
			Log.v("log","fly");
			setPos(src,-1);
			isStartplay=true;
			
		}
	}
	
	//设置line_id;
	public void setLineid(int id){
		this.line_id = id;
	}
	//获取line_id
	public int getLineid(){
		return this.line_id;
	}
	//设置方向
	public void setFrameid(int Frame){
		setFrame(Frame);
	}
	//游戏重置
	public void initFrame(int Frame){
		initframe = Frame;
		isStartplay =  false;
		isEnd = false;
		line_id = -2;
		setFrameid(initframe);
	}
	//到达终点
	public void setEnd(boolean end){
		this.isEnd = true;
		setPos(Initxy,-2);
		setFrameid(4);
	}
	
	public boolean ISend(){
		return this.isEnd;
	}
	//起飞
	public boolean test(float x,float y){
		if(mDie||isEnd||!isStartplay) return false;
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		if (x > minx && x < maxx && y > miny && y < maxy) return true;
			
		return false;
	}
	//走棋
	public boolean SelectStart(float x,float y){
		if(mDie||isEnd||isStartplay) return false;
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		if (x > minx && x < maxx && y > miny && y < maxy) return true;
			
		return false;
	}
	public boolean IsStart(){
		return this.isStartplay;
	}
	//打回原地
	public void setTostart(){
		this.mPosition.set(Initxy);
		isStartplay =  false;
		line_id = -2;
		setFrameid(initframe);
	}
	private int getFrame(int player){
		int frameid = -1;
		switch(player){
		case 1:
			if((line_id>=0 && line_id<=3)||(line_id>=8 && line_id<=12)||(line_id>=17 && line_id<=20)||line_id>48){
				frameid=1;
			}else if((line_id>=4 && line_id<=7)||(line_id>=46 && line_id<=48)||(line_id>=39 && line_id<=42)){
				frameid=0;
			}else if((line_id>=13 && line_id<=16)||(line_id>=21 && line_id<=25)||(line_id>=30 && line_id<=33)){
				frameid=3;
			}else if((line_id>=26 && line_id<=29)||(line_id>=34 && line_id<=38)||(line_id>=43 && line_id<=46)){
				frameid=2;
			}
			break;
		case 2:
			if((line_id>=0 && line_id<=3)||(line_id>=8 && line_id<=12)||(line_id>=17 && line_id<=20)||line_id>48){
				frameid=3;
			}else if((line_id>=4 && line_id<=7)||(line_id>=46 && line_id<=48)||(line_id>=39 && line_id<=42)){
				frameid=1;
			}else if((line_id>=13 && line_id<=16)||(line_id>=21 && line_id<=25)||(line_id>=30 && line_id<=33)){
				frameid=2;
			}else if((line_id>=26 && line_id<=29)||(line_id>=34 && line_id<=38)||(line_id>=43 && line_id<=46)){
				frameid=0;
			}
			break;
		case 3:
			if((line_id>=0 && line_id<=3)||(line_id>=8 && line_id<=12)||(line_id>=17 && line_id<=20)||line_id>48){
				frameid=2;
			}else if((line_id>=4 && line_id<=7)||(line_id>=46 && line_id<=48)||(line_id>=39 && line_id<=42)){
				frameid=3;
			}else if((line_id>=13 && line_id<=16)||(line_id>=21 && line_id<=25)||(line_id>=30 && line_id<=33)){
				frameid=0;
			}else if((line_id>=26 && line_id<=29)||(line_id>=34 && line_id<=38)||(line_id>=43 && line_id<=46)){
				frameid=1;
			}
			break;
		case 4:
			if((line_id>=0 && line_id<=3)||(line_id>=8 && line_id<=12)||(line_id>=17 && line_id<=20)||line_id>48){
				frameid=0;
			}else if((line_id>=4 && line_id<=7)||(line_id>=46 && line_id<=48)||(line_id>=39 && line_id<=42)){
				frameid=2;
			}else if((line_id>=13 && line_id<=16)||(line_id>=21 && line_id<=25)||(line_id>=30 && line_id<=33)){
				frameid=1;
			}else if((line_id>=26 && line_id<=29)||(line_id>=34 && line_id<=38)||(line_id>=43 && line_id<=46)){
				frameid=3;
			}
			break;
		}
		return frameid;
	}
	
}
