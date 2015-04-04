package game.sprite;

import java.util.Random;

import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class Posstars extends AnglePhysicsEngine{
	double start;
	public Posstars(int maxObjects,AngleSpriteLayout layout,int id,AngleVector pos) {
		super(maxObjects);
		for(int i=0;i<maxObjects;i++){
			addObject(new star_item(layout,id,pos));
		}
	}
	public Posstars(int maxObjects,AngleSpriteLayout layout,AngleVector pos) {
		super(maxObjects);
		for(int i=0;i<maxObjects;i++){
			addObject(new star_item(layout,pos));
		}
	}
	
	@Override
	public void step(float secondsElapsed) {
		// TODO 自动生成的方法存根
		super.step(secondsElapsed);
		if(System.currentTimeMillis()-start>50){
			start = System.currentTimeMillis();
			if(this.count()<1){
				this.mDie=true;
			}
		}
	}
	
	private class star_item extends AngleSprite{
		AngleVector endPos;
		AngleVector StartPos;
		int rnum,move=5;
		public star_item(AngleSpriteLayout layout,int framid,AngleVector start) {
			super(layout);
			mScale.set((float)0.3,(float)0.3);
			setFrame(framid);
			Random rand = new Random();
			move +=rand.nextInt(10); 
			StartPos = new AngleVector(start);
			rnum = rand.nextInt(360); 
			float x=start.mX+(float)100*(float)Math.cos(rnum);
			float y=start.mY+(float)100*(float)Math.sin(rnum);
			 start.mX+=(float)rand.nextInt(20)*(float)Math.cos(rnum);
			 start.mY+=(float)rand.nextInt(20)*(float)Math.sin(rnum);
			 mPosition.set(start);
			 endPos = new AngleVector(x,y);
		}
		
		public star_item(AngleSpriteLayout layout,AngleVector start) {
			super(layout);
			mScale.set((float)0.3,(float)0.3);
			
			Random rand = new Random();
			move +=rand.nextInt(10); 
			StartPos = new AngleVector(start);
			rnum = rand.nextInt(360); 
			setFrame(rand.nextInt(5));
			
			float x=start.mX+(float)100*(float)Math.cos(rnum);
			float y=start.mY+(float)100*(float)Math.sin(rnum);
		
			 mPosition.set(start);
			 endPos = new AngleVector(x,y);
		}
		@Override
		public void step(float secondsElapsed) {
			// TODO 自动生成的方法存根
			super.step(secondsElapsed);
			if(mPosition.mX!=endPos.mX){
				mPosition.mX += (float)secondsElapsed*(float)Math.cos(rnum)*move;
			}
			if(mPosition.mY!=endPos.mY){
				mPosition.mY += (float)secondsElapsed*(float)Math.sin(rnum)*move;
			}
			Dr();
			move+=5;
			mAlpha-=secondsElapsed*(float)0.5;
		}
		private void Dr(){
			float dr = (mPosition.mX-StartPos.mX)*(mPosition.mX-StartPos.mX)+(mPosition.mY-StartPos.mY)*(mPosition.mY-StartPos.mY);
			if(dr>150*150) this.mDie=true;
		}
		
		
	}
}
