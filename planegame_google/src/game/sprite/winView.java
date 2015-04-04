package game.sprite;

import game.data.txtShow;
import game.data.winScore;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.android.angle.AngleFont;
import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleString;

public class winView extends AngleSprite{
	private AnglePhysicsEngine buttons;
	private BtnSprite Okbtn,cancelbtn;
	Map<String,winScore> score;
	AngleFont txtfnt;
	Map<String,txtShow> text;
	public winView(Map<String,AngleSpriteLayout> layout,int wx,int wy,float ap,Map<String,winScore> score,AngleFont txtfnt) {
		super(layout.get("bg"));
		mPosition.set(wx, wy);
		this.score = score;
		this.txtfnt = txtfnt;
		
		buttons = new AnglePhysicsEngine(20);
		int bgwidth = layout.get("bg").roWidth;
		int stepH = layout.get("ok").roHeight;
		Okbtn = new BtnSprite(wx/2+layout.get("ok").roWidth/2,wy+layout.get("bg").roHeight/3,layout.get("ok"));
		cancelbtn = new BtnSprite(wx+layout.get("ok").roWidth/2,wy+layout.get("bg").roHeight/3,layout.get("cancle"));
		
		buttons.addObject(Okbtn);
		buttons.addObject(cancelbtn);
		addObject(buttons);
		for(int i=0;i<buttons.count();i++){
			((BtnSprite)buttons.childAt(i)).mAlpha=ap;
		}
		
		text = new HashMap<String,txtShow>();
		
		Set set =score.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()){
	       Map.Entry<String, winScore>  entry=(Entry<String, winScore>) it.next();
	       if("1"==entry.getKey()){
		       AngleSprite num = new AngleSprite(wx,wy-3*stepH/2+3,layout.get("num"));
		       AngleSprite scnum = new AngleSprite((int)(wx+(bgwidth/4)),wy-3*stepH/2+3,layout.get("num"));
		      
		       text.put(entry.getKey(),new txtShow(num,scnum));
	       }else if("2"==entry.getKey()){
	    	   AngleSprite num = new AngleSprite( wx,wy-stepH/2,layout.get("num"));
	    	   AngleSprite scnum = new AngleSprite((int)(wx+(bgwidth/4)),wy-stepH/2,layout.get("num"));
		       text.put(entry.getKey(),new txtShow(num,scnum));
	       }
	       else if("3"==entry.getKey()){
	    	   AngleSprite num = new AngleSprite( wx,wy+stepH/2,layout.get("num"));
	    	   AngleSprite scnum = new AngleSprite( (int)(wx+(bgwidth/4)),wy+stepH/2,layout.get("num"));
		       text.put(entry.getKey(),new txtShow(num,scnum));
	       }
	       else if("4"==entry.getKey()){
	    	   AngleSprite num = new AngleSprite( wx,wy+3*stepH/2,layout.get("num"));
	    	   AngleSprite scnum = new AngleSprite((int)(wx+(bgwidth/4)),wy+3*stepH/2,layout.get("num"));
		       text.put(entry.getKey(),new txtShow(num,scnum));
	       }
		}
		
		for(int i=1;i<5;i++){
			buttons.addObject(text.get(""+i).getNum());
			buttons.addObject(text.get(""+i).getScore());
		}
		this.mAlpha = ap;
		Okbtn.mAlpha = this.mAlpha;
		cancelbtn.mAlpha = this.mAlpha;
		if(0==ap){
		for(int i=1;i<5;i++){
			text.get(""+i).hidden();
		}
		}
	}
	
	public void setScore(Map<String,winScore> scores){
		text.get("1").setScore(scores.get("1").endNum, scores.get("1").Score);
		text.get("2").setScore(scores.get("2").endNum, scores.get("2").Score);
		text.get("3").setScore(scores.get("3").endNum, scores.get("3").Score);
		text.get("4").setScore(scores.get("4").endNum, scores.get("4").Score);
	}
	public void setShow(){
		this.mAlpha = 1;
		Okbtn.mAlpha = this.mAlpha;
		cancelbtn.mAlpha = this.mAlpha;
		for(int i=1;i<5;i++){
			text.get(""+i).show();
		}
	}
	public void hidden(){
		this.mAlpha = 0;
		Okbtn.mAlpha = this.mAlpha;
		cancelbtn.mAlpha = this.mAlpha;
		for(int i=1;i<5;i++){
			text.get(""+i).hidden();
		}
	}
	public int checkClk(float ex,float dy){
		
		if(Okbtn.test(ex, dy)){
			this.hidden();	
			return 2;
		}
		if(cancelbtn.test(ex, dy)){
			this.hidden();
			return 3;
		}
		return 1;
	}
	

}
