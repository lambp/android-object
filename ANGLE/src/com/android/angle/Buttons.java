package com.android.angle;


public class Buttons extends AngleSprite{
	AnglePhysicsEngine btns;
	AngleSprite bgsp,textsp;
	int langs=0;
	public Buttons(AngleSpriteLayout bg,AngleSpriteLayout text,AngleVector pos,int lang) {
		super(bg);
		btns = new AnglePhysicsEngine(10);
		mPosition.set(pos);
		this.addObject(btns);
		bgsp = new AngleSprite(bg);
		bgsp.mPosition.set(pos);
		btns.addObject(bgsp);
		textsp = new AngleSprite(text);
		textsp.setFrame(lang);
		textsp.mPosition.set(pos);
		btns.addObject(textsp);
		this.mAlpha=0;
	}
	public Buttons(AngleSpriteLayout bg,AngleVector pos,int lang) {
		super(bg);
		this.mAlpha=0;
		btns = new AnglePhysicsEngine(10);
		mPosition.set(pos);
		this.addObject(btns);
		bgsp = new AngleSprite(bg);
		bgsp.mPosition.set(pos);
		btns.addObject(bgsp);

	}
	public void setpos(AngleVector pos){
		this.mPosition.set(pos);
		bgsp.mPosition.set(pos);
		
	}
	public void mouseDown(float x, float y){
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		if (x > minx && x < maxx && y > miny && y < maxy){
			bgsp.setFrame(langs+1);
		}
	}
	public void mouseMove(float x, float y){
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		if (x > minx && x < maxx && y > miny && y < maxy){
			bgsp.setFrame(langs+1);
		}else{
			bgsp.setFrame(langs);
		}
	}
	
	public boolean mouseUp(float x, float y) {
		if(mDie) return false;
		float minx = mPosition.mX - roLayout.roWidth / 2;
		float miny = mPosition.mY - roLayout.roHeight / 2;
		float maxx = mPosition.mX + roLayout.roWidth / 2;
		float maxy = mPosition.mY + roLayout.roHeight / 2;
		bgsp.setFrame(langs);
		if (x > minx && x < maxx && y > miny && y < maxy){
			return true;
		}else{
			return false;
		}
	
	}
	
}
