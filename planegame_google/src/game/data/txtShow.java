package game.data;

import com.android.angle.AngleSprite;

public class txtShow {
	private AngleSprite endNum;
	private AngleSprite Score;
	public txtShow(AngleSprite num,AngleSprite score){
		this.endNum = num;
		this.Score = score;
	}
	public void setScore(int num,int score){
		this.endNum.setFrame(num);
		this.Score.setFrame(score);
		
	}
	public void show(){
		this.endNum.mAlpha=1;
		this.Score.mAlpha=1;
	}
	public void hidden(){
		this.endNum.mAlpha=0;
		this.Score.mAlpha=0;
	}
	public AngleSprite getNum(){
		AngleSprite temp =endNum;
		return temp;
	}
	public AngleSprite getScore(){
		AngleSprite  temp=Score;
		return temp;
	}
}
