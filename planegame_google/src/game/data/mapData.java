package game.data;

import java.util.ArrayList;

import android.util.Log;

import com.android.angle.AngleVector;

public class mapData {
	public static int[][] initMap = {
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,1,2,3,4,1,2,3,0,0,0,0,0},
		{0,0,0,0,0,4,0,0,8,0,0,4,0,0,0,0,0},
		{0,0,0,0,0,3,0,0,8,0,0,1,0,0,0,0,0},
		{0,0,0,0,0,2,0,0,8,0,0,2,0,0,0,0,0},
		{0,2,3,4,1,0,0,0,8,0,0,0,3,4,1,2,0},
		{0,1,0,0,0,0,0,0,8,0,0,0,0,0,0,3,0},
		{0,4,0,0,0,0,0,0,8,0,0,0,0,0,0,4,0},
		{0,3,6,6,6,6,6,6,0,7,7,7,7,7,7,1,0},
		{0,2,0,0,0,0,0,0,9,0,0,0,0,0,0,2,0},
		{0,1,0,0,0,0,0,0,9,0,0,0,0,0,0,3,0},
		{0,4,3,2,1,0,0,0,9,0,0,0,3,2,1,4,0},
		{0,0,0,0,0,4,0,0,9,0,0,4,0,0,0,0,0},
		{0,0,0,0,0,3,0,0,9,0,0,1,0,0,0,0,0},
		{0,0,0,0,0,2,0,0,9,0,0,2,0,0,0,0,0},
		{0,0,0,0,0,1,4,3,2,1,4,3,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
	};
	//线路图-1
	public static int[][] line_1 = {
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,34,35,36,37,38,39,40,0,0,0,0,0},
		{0,0,0,0,0,33,0,0,0,0,0,41,0,0,0,0,0},
		{0,0,0,0,0,32,0,0,0,0,0,42,0,0,0,0,0},
		{0,0,0,0,0,31,0,0,0,0,0,43,0,0,0,0,0},
		{0,27,28,29,30,0,0,0,0,0,0,0,44,45,46,47,0},
		{0,26,0,0,0,0,0,0,0,0,0,0,0,0,0,48,0},
		{0,25,0,0,0,0,0,0,0,0,0,0,0,0,0,49,0},
		{0,24,0,0,0,0,0,0,0,56,55,54,53,52,51,50,0},
		{0,23,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,22,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,21,20,19,18,0,0,0,0,0,0,0,4,3,2,1,0},
		{0,0,0,0,0,17,0,0,0,0,0,5,0,0,0,0,0},
		{0,0,0,0,0,16,0,0,0,0,0,6,0,0,0,0,0},
		{0,0,0,0,0,15,0,0,0,0,0,7,0,0,0,0,0},
		{0,0,0,0,0,14,13,12,11,10,9,8,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
	};
	//线路图-2
	public static int[][] line_2 = {
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,21,22,23,24,25,26,27,0,0,0,0,0},
		{0,0,0,0,0,20,0,0,0,0,0,28,0,0,0,0,0},
		{0,0,0,0,0,19,0,0,0,0,0,29,0,0,0,0,0},
		{0,0,0,0,0,18,0,0,0,0,0,30,0,0,0,0,0},
		{0,14,15,16,17,0,0,0,0,0,0,0,31,32,33,34,0},
		{0,13,0,0,0,0,0,0,0,0,0,0,0,0,0,35,0},
		{0,12,0,0,0,0,0,0,0,0,0,0,0,0,0,36,0},
		{0,11,0,0,0,0,0,0,0,0,0,0,0,0,0,37,0},
		{0,10,0,0,0,0,0,0,56,0,0,0,0,0,0,38,0},
		{0,9,0,0,0,0,0,0,55,0,0,0,0,0,0,39,0},
		{0,8,7,6,5,0,0,0,54,0,0,0,43,42,41,40,0},
		{0,0,0,0,0,4,0,0,53,0,0,44,0,0,0,0,0},
		{0,0,0,0,0,3,0,0,52,0,0,45,0,0,0,0,0},
		{0,0,0,0,0,2,0,0,51,0,0,46,0,0,0,0,0},
		{0,0,0,0,0,1,0,0,50,49,48,47,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
	};
	//线路图-3
		public static int[][] line_3 = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,8,9,10,11,12,13,14,0,0,0,0,0},
			{0,0,0,0,0,7,0,0,0,0,0,15,0,0,0,0,0},
			{0,0,0,0,0,6,0,0,0,0,0,16,0,0,0,0,0},
			{0,0,0,0,0,5,0,0,0,0,0,17,0,0,0,0,0},
			{0,1,2,3,4,0,0,0,0,0,0,0,18,19,20,21,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,22,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,23,0},
			{0,50,51,52,53,54,55,56,0,0,0,0,0,0,0,24,0},
			{0,49,0,0,0,0,0,0,0,0,0,0,0,0,0,25,0},
			{0,48,0,0,0,0,0,0,0,0,0,0,0,0,0,26,0},
			{0,47,46,45,44,0,0,0,0,0,0,0,30,29,28,27,0},
			{0,0,0,0,0,43,0,0,0,0,0,31,0,0,0,0,0},
			{0,0,0,0,0,42,0,0,0,0,0,32,0,0,0,0,0},
			{0,0,0,0,0,41,0,0,0,0,0,33,0,0,0,0,0},
			{0,0,0,0,0,40,39,38,37,36,35,34,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
		//线路图-2
		public static int[][] line_4 = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,47,48,49,50,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,46,0,0,51,0,0,2,0,0,0,0,0},
			{0,0,0,0,0,45,0,0,52,0,0,3,0,0,0,0,0},
			{0,0,0,0,0,44,0,0,53,0,0,4,0,0,0,0,0},
			{0,40,41,42,43,0,0,0,54,0,0,0,5,6,7,8,0},
			{0,39,0,0,0,0,0,0,55,0,0,0,0,0,0,9,0},
			{0,38,0,0,0,0,0,0,56,0,0,0,0,0,0,10,0},
			{0,37,0,0,0,0,0,0,0,0,0,0,0,0,0,11,0},
			{0,36,0,0,0,0,0,0,0,0,0,0,0,0,0,12,0},
			{0,35,0,0,0,0,0,0,0,0,0,0,0,0,0,13,0},
			{0,34,33,32,31,0,0,0,0,0,0,0,17,16,15,14,0},
			{0,0,0,0,0,30,0,0,0,0,0,18,0,0,0,0,0},
			{0,0,0,0,0,29,0,0,0,0,0,19,0,0,0,0,0},
			{0,0,0,0,0,28,0,0,0,0,0,20,0,0,0,0,0},
			{0,0,0,0,0,27,26,25,24,23,22,21,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}
		};
		//初始化飞行路线
		
	public static int[][] initLine(int play){
		int[][] line = new int[56][2];
		int[][] map = new int[17][17];
		switch(play){
		case 1:
			map = line_1;
			break;
		case 2:
			map = line_2;
			break;
		case 3:
			map = line_3;
			break;
		case 4:
			map = line_4;
			break;
		}
		int val = 1;
		
		for(int k=0;k<56;k++){
			for(int i=0;i<17;i++){
				for(int j=0;j<17;j++){
					if(map[i][j]==val){
						line[val-1][0] = i;
						line[val-1][1] = j;
						val++;
					}
				}
			}
		}
		return line;
	}
	//计算起飞点位置
	public static AngleVector getStartPos(int play,int width,int height){
		AngleVector pos = new AngleVector();
		int dh = (height-width)/2;
		switch(play){
		case 1:
			pos.mX = width-(width/17+1)/2;
			pos.mY = (float) (height-dh-(4.5*width)/17-1);
			break;
		case 2:
			pos.mX = (float) ((4.5*width)/17+1);
			pos.mY = (float) (height-dh-(width/17+1)/2);
			break;
		case 3:
			pos.mX = (float) ((width)/17+0.5)/2;
			pos.mY = (float) (dh+(width*4.5/17)+0.5);
			break;
		case 4:
			pos.mX = (float) (width-(width*4.5/17+0.5));
			pos.mY = (float) (dh+(width/17+0.5)/2+0.5);;
			break;
		}
		return pos;
	}
	//设置初始位置
	public static ArrayList<AngleVector> getInitPos(int play,int width,int height){
		ArrayList<AngleVector> res = new ArrayList<AngleVector>();
		int dh = (height-width)/2;
		AngleVector pos1 = new AngleVector();
		switch(play){
		case 1:
			
			pos1.mX = (float) (width*14/17+0.5);
			pos1.mY = (float) (dh+(14*width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*14/17+0.5);
			pos1.mY = (float) (dh+(16*width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*16/17+0.5);
			pos1.mY = (float) (dh+(14*width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*16/17+0.5);
			pos1.mY = (float) (dh+(16*width)/17+0.5);
			res.add(pos1);
			
			break;
		case 2:
			
			pos1.mX = (float) (width/17+0.5);
			pos1.mY = (float) (dh+(14*width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width/17+0.5);
			pos1.mY = (float) (dh+(16*width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*3/17+0.5);
			pos1.mY = (float) (dh+(14*width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*3/17+0.5);
			pos1.mY = (float) (dh+(16*width)/17+0.5);
			res.add(pos1);
			
			break;
		case 3:
			pos1.mX = (float) (width/17+0.5);
			pos1.mY = (float) (dh+(width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width/17+0.5);
			pos1.mY = (float) (dh+(3*width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*3/17+0.5);
			pos1.mY = (float) (dh+(width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*3/17+0.5);
			pos1.mY = (float) (dh+(3*width)/17+0.5);
			res.add(pos1);
			break;
		case 4:
			pos1.mX = (float) (width*14/17+0.5);
			pos1.mY = (float) (dh+(width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*14/17+0.5);
			pos1.mY = (float) (dh+(3*width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*16/17+0.5);
			pos1.mY = (float) (dh+(width)/17+0.5);
			res.add(pos1);
			pos1 = new AngleVector();
			pos1.mX = (float) (width*16/17+0.5);
			pos1.mY = (float) (dh+(3*width)/17+0.5);
			res.add(pos1);
			break;
		}
		return res;
	}
	//初始坐标点属性
	public  static ArrayList<mapPos> initMap(int width,int height){
		ArrayList<mapPos> resPos = new ArrayList<mapPos>();
		for(int i=0;i<17;i++){
			for(int j=0;j<17;j++){
				if(initMap[i][j]>0){
					int[] pi = {i,j};
					int val = initMap[i][j];
					AngleVector pos = getXY(pi,val,width,height);
					mapPos itmep = new mapPos(pos,pi,val);
					resPos.add(itmep);
				}
			}
		}
		return resPos;
	}
	//计算坐标
	public static AngleVector getXY(int[] pi,int val,int width,int height){
		AngleVector pos = new AngleVector();
		float dh = (height-width)/2;
		
		if(val>5 || checkMid(pi)){
			
			if((width*(pi[1]+0.5))%17>8){
				pos.mX = (float) (width*(pi[1]+0.5))/17+1;
			}else{
				pos.mX = (float) ((float) (width*(pi[1]+0.5))/17+0.5);
			}
			if((width*(pi[0]+0.5))%17>8){
				pos.mY = (float) (width*(pi[0]+0.5))/17+1+dh;
			}else{
				pos.mY = (float) (width*(pi[0]+0.5))/17+(float)(0.5)+dh;
			}
			
		}else{
			if(checkX(pi)){
				if(pi[1]<8){
					if((width*(pi[1]))%17>8){
						pos.mX = width*pi[1]/17+1;
					}else{
						pos.mX = (float) (width*pi[1]/17+0.5);
					}
				
				}else{
					if((width*(pi[1]+1))%17>8){
						pos.mX = (float) (width*(pi[1]+1)/17+1);
					}else{
						pos.mX = (float) (width*(pi[1]+1)/17+0.5);
					}
				}
				if((width*(pi[0]+0.5))%17>8){
					pos.mY = (float) (width*(pi[0]+0.5))/17+dh+1;
				}else{
					pos.mY = (float) (width*(pi[0]+0.5))/17+(float)(0.5)+dh;
				}
			}else{
				if((width*(pi[1]+0.5))%17>8){
					pos.mX = (float)(width*(pi[1]+0.5))/17+1;
				}else{
					pos.mX = (float)(width*(pi[1]+0.5))/17+(float)0.5;
				}
				if(pi[0]<8){
					
					if((width*pi[0])%17>8){
						pos.mY = (float) (width*pi[0])/17+1+dh;
					}else{
						pos.mY = (float) (width*pi[0])/17+(float)0.5+dh;;
					}
				}else{
					if((width*(pi[0]+1))%17>8){
						pos.mY = (float) (width*(pi[0]+1))/17+1+dh;
					}else{
						pos.mY = (float) (width*(pi[0]+1))/17+(float)0.5+dh;;
					}
				}
			}
		}
		
		return pos;
	}
	//3角的地方
	public static boolean checkMid(int[] pi){
		int[][] midmap = {
				{5,1},
				{5,4},
				{4,5},
				{11,1},
				{11,4},
				{12,5},
				{15,5},
				{15,11},
				{12,11},
				{11,12},
				{11,15},
				{5,15},
				{5,12},
				{4,11},
				{1,11},
				{1,5}
		}; 
		for(int i=0;i<midmap.length;i++){
			if(pi[0]==midmap[i][0] && pi[1]==midmap[i][1]) return true;
		}
		return false;
	}
	//检测
	public static boolean checkX(int[] pi){
		int[][] midmap = {
				{6,1},
				{7,1},
				{8,1},
				{9,1},
				{10,1},
				{13,5},
				{14,5},
				{13,11},
				{14,11},
				{6,15},
				{7,15},
				{8,15},
				{9,15},
				{10,15},
				{2,5},
				{3,5},
				{2,11},
				{3,11}
		};
		boolean temp = false;
		for(int i=0;i<midmap.length;i++){
			if(pi[0]==midmap[i][0] && pi[1]==midmap[i][1]) temp = true;
		}
		
		return temp;
	}
}

