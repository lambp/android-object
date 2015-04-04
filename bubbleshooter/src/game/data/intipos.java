package game.data;

import com.android.angle.AngleVector;

public class intipos {
	public static AngleVector[][] initpos(int hang,int lie,float pwidth){
		AngleVector[][] pos = new AngleVector[hang][lie];
		float r = pwidth/2f;
		double h = Math.tan(Math.PI/3)*r;
		
		for(int i=0;i<hang;i++){
			for(int j=0;j<lie;j++){
				float ex,ey;
				if(i%2==0){
					ex = (float)(r+pwidth*j);
				}else{
					ex = (float)(pwidth+pwidth*j);
				}
				ey = (float) (r+h*(i));
				pos[i][j] = new AngleVector(ex,ey); 
			}
		}
		return pos;
	}
	
}
