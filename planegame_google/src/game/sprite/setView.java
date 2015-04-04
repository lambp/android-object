package game.sprite;

import game.data.tools;

import java.util.Map;

import com.android.angle.AnglePhysicsEngine;
import com.android.angle.AngleSprite;
import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class setView extends AngleSprite{
	private AnglePhysicsEngine buttons;
	private BtnSprite movebtn_1,movebtn_2,movebtn_3,movebtn_4,movebtn_5,movebtn_6,movebtn_7,movebtn_8,movebtn_9,movebtn_10,movebtn_11,movebtn_12;
	private BtnSprite Okbtn,cancelbtn;
	private AngleSprite get_1,get_2,get_3,get_4;
	private int play_1,play_2,play_3,play_4;
	private String oldSet;
	public setView(Map<String,AngleSpriteLayout> layout,int wx,int wy,float ap,String playset) {
		super(layout.get("bg"));
		mPosition.set(wx, wy);
		oldSet = playset;
		String[] sets= playset.split(",");
		play_1 = Integer.parseInt(sets[0]);
		play_2 = Integer.parseInt(sets[1]);
		play_3 = Integer.parseInt(sets[2]);
		play_4 = Integer.parseInt(sets[3]);
		
		buttons = new AnglePhysicsEngine(20);
		int bgheight = layout.get("bg").roHeight;
		int bgwidth = layout.get("bg").roWidth;
		Okbtn = new BtnSprite(wx/2+layout.get("ok").roWidth/2,wy+layout.get("bg").roHeight/3,layout.get("ok"));
		cancelbtn = new BtnSprite(wx+layout.get("ok").roWidth/2,wy+layout.get("bg").roHeight/3,layout.get("cancle"));
		
		
		movebtn_1 = new BtnSprite((int)(wx-(bgwidth/5)),wy-bgheight/3+2,layout.get("move"),0);
		movebtn_2 = new BtnSprite(wx,wy-bgheight/3+2,layout.get("move"),0);
		movebtn_3 = new BtnSprite((int)(wx+(bgwidth/4)),wy-bgheight/3+2,layout.get("move"),0);
		
		movebtn_4 = new BtnSprite((int)(wx-(bgwidth/5)),wy-bgheight/6,layout.get("move"),1);
		movebtn_5 = new BtnSprite(wx,wy-bgheight/6,layout.get("move"),1);
		movebtn_6 = new BtnSprite((int)(wx+(bgwidth/4)),wy-bgheight/6,layout.get("move"),1);
		
		movebtn_7 = new BtnSprite((int)(wx-(bgwidth/5)),wy,layout.get("move"),2);
		movebtn_8 = new BtnSprite(wx,wy,layout.get("move"),2);
		movebtn_9 = new BtnSprite((int)(wx+(bgwidth/4)),wy,layout.get("move"),2);
		
		movebtn_10 = new BtnSprite((int)(wx-(bgwidth/5)),wy+bgheight/6,layout.get("move"),3);
		movebtn_11 = new BtnSprite(wx,wy+bgheight/6,layout.get("move"),3);
		movebtn_12 = new BtnSprite((int)(wx+(bgwidth/4)),wy+bgheight/6,layout.get("move"),3);
		
		get_1 = new BtnSprite((int)(wx-(bgwidth/5)),wy-bgheight/3+2,layout.get("move"),4);
		get_2 =new BtnSprite((int)(wx-(bgwidth/5)),wy-bgheight/6,layout.get("move"),4);
		get_3 = new BtnSprite((int)(wx-(bgwidth/5)),wy,layout.get("move"),4);
		get_4 = new BtnSprite((int)(wx-(bgwidth/5)),wy+bgheight/6,layout.get("move"),4);
		setGet(1,play_1);
		setGet(2,play_2);
		setGet(3,play_3);
		setGet(4,play_4);
		buttons.addObject(Okbtn);
		buttons.addObject(cancelbtn);
		buttons.addObject(movebtn_1);
		buttons.addObject(movebtn_2);
		buttons.addObject(movebtn_3);
		buttons.addObject(movebtn_4);
		buttons.addObject(movebtn_5);
		buttons.addObject(movebtn_6);
		buttons.addObject(movebtn_7);
		buttons.addObject(movebtn_8);
		buttons.addObject(movebtn_9);
		buttons.addObject(movebtn_10);
		buttons.addObject(movebtn_11);
		buttons.addObject(movebtn_12);
		buttons.addObject(get_1);
		buttons.addObject(get_2);
		buttons.addObject(get_3);
		buttons.addObject(get_4);
		addObject(buttons);
		this.mAlpha = ap;
		for(int i=0;i<buttons.count();i++){
			((BtnSprite)buttons.childAt(i)).mAlpha=0;
		}
		buttons.mDie=true;
	}
	private void setGet(int play,int id){
		switch(play){
		case 1:
			switch(id){
			case 1:
				get_1.mPosition.set(movebtn_1.mPosition);
				break;
			case 2:
				get_1.mPosition.set(movebtn_2.mPosition);
				break;
			case 3:
				get_1.mPosition.set(movebtn_3.mPosition);
				break;
			}
			break;
			
		case 2:
			switch(id){
			case 1:
				get_2.mPosition.set(movebtn_4.mPosition);
				break;
			case 2:
				get_2.mPosition.set(movebtn_5.mPosition);
				break;
			case 3:
				get_2.mPosition.set(movebtn_6.mPosition);
				break;
			}
			break;
		case 3:
			switch(id){
			case 1:
				get_3.mPosition.set(movebtn_7.mPosition);
				break;
			case 2:
				get_3.mPosition.set(movebtn_8.mPosition);
				break;
			case 3:
				get_3.mPosition.set(movebtn_9.mPosition);
				break;
			}
			break;
		case 4:
			switch(id){
			case 1:
				get_4.mPosition.set(movebtn_10.mPosition);
				break;
			case 2:
				get_4.mPosition.set(movebtn_11.mPosition);
				break;
			case 3:
				get_4.mPosition.set(movebtn_12.mPosition);
				break;
			}
			break;
		}
	}
	public void setShow(){
		this.mAlpha = 1;
		addObject(buttons);
		for(int i=0;i<buttons.count();i++){
			((BtnSprite)buttons.childAt(i)).mAlpha=1;
		}
		String[] sets= oldSet.split(",");
		play_1 = Integer.parseInt(sets[0]);
		play_2 = Integer.parseInt(sets[1]);
		play_3 = Integer.parseInt(sets[2]);
		play_4 = Integer.parseInt(sets[3]);
		setGet(1,play_1);
		setGet(2,play_2);
		setGet(3,play_3);
		setGet(4,play_4);
	}
	public void hidden(){
		this.mAlpha = 0;
		buttons.mDie=true;
		for(int i=0;i<buttons.count();i++){
			((BtnSprite)buttons.childAt(i)).mAlpha=0;
		}
	}
	public int checkClk(float ex,float dy){
		if(movebtn_1.test(ex, dy)){
			get_1.mPosition.set(movebtn_1.mPosition);
			play_1 =1;
		}
		if(movebtn_2.test(ex, dy)){
			get_1.mPosition.set(movebtn_2.mPosition);	
			play_1 =2;
			
		}
		if(movebtn_3.test(ex, dy)){
			get_1.mPosition.set(movebtn_3.mPosition);
			play_1 =3;
		}
		
		if(movebtn_4.test(ex, dy)){
			get_2.mPosition.set(movebtn_4.mPosition);
			play_2 =1;
		}
		if(movebtn_5.test(ex, dy)){
			get_2.mPosition.set(movebtn_5.mPosition);
			play_2 =2;
		}
		if(movebtn_6.test(ex, dy)){
			get_2.mPosition.set(movebtn_6.mPosition);	
			play_2 =3;
		}
		
		if(movebtn_7.test(ex, dy)){
			get_3.mPosition.set(movebtn_7.mPosition);
			play_3 =1;
		}
		if(movebtn_8.test(ex, dy)){
			get_3.mPosition.set(movebtn_8.mPosition);
			play_3 =2;
		}
		if(movebtn_9.test(ex, dy)){
			get_3.mPosition.set(movebtn_9.mPosition);
			play_3 =3;
		}
		
		if(movebtn_10.test(ex, dy)){
			get_4.mPosition.set(movebtn_10.mPosition);	
			play_4 =1;
		}
		if(movebtn_11.test(ex, dy)){
			get_4.mPosition.set(movebtn_11.mPosition);
			play_4 =2;
		}
		if(movebtn_12.test(ex, dy)){
			get_4.mPosition.set(movebtn_12.mPosition);
			play_4 =3;
		}
		if(Okbtn.test(ex, dy)){
				
				if(checkSet()){
					this.hidden();	
					oldSet = ""+play_1+","+play_2+","+play_3+","+play_4+"";
					return 2;
				}
		}
		if(cancelbtn.test(ex, dy)){
			    this.hidden();
			    return 3;
		}
		return 1;
	}
	private boolean checkSet(){
		if(play_1==play_2&&play_2==play_3&&play_4==play_3){
			return false;
		}
		int[] plays = {play_1,play_2,play_3,play_4};
		int one = 0;
		int two = 0;
		for(int i=0;i<4;i++){
			if(plays[i]==1) one++;
			if(plays[i]==2) two++;
		}
		if(one<1) return false;
		if(one<2 && two<1) return false;
		return true;
	}
	public String getSetstr(){
		return this.oldSet;
	}
}
