package game.data;

import com.android.angle.AngleVector;

public class map {
	public static AngleVector[][] getmap(int width,int height){
		AngleVector[][] index = new AngleVector[10][10];
		float perwidth = (float)width/(float)10;
		for(int x=0;x<10;x++){
			for(int y=0;y<10;y++){
				float ex = (float)y*perwidth+(float)perwidth/(float)2;
				float ey = (float)height-(float)x*perwidth-(float)perwidth/(float)2;
				index[x][y]=new AngleVector(ex, ey);
			}
		}
		return index;
	}
	
}
