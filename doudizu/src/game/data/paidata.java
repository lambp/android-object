package game.data;

import java.util.ArrayList;
import java.util.Random;

public class paidata {
	private int[] paival;
	Random rand;
	public paidata(){
		paival = new int[54];
		for(int i=0;i<54;i++){
			paival[i] = i+1;
		}
		rand = new Random();
	}
	//洗牌
	public int[] xipai(){
		for(int j=0;j<10;j++){
			for(int i=0;i<54;i++){
				int tp = rand.nextInt(1000)%54;
				int val = paival[i];
				if(tp!=i){
					paival[i] = paival[tp];
					paival[tp] = val;
				}
			}
		}
		return paival;
	}
	public int[] getdipai(){
		ArrayList<Object> list = new ArrayList<Object>();
		
		for(int i=51;i<54;i++){
		   list.add(paival[i]);
		}
		int[] val = new int[list.size()];
		for(int i=0;i<list.size();i++){
			val[i] = (Integer) list.get(i);
		}
		return sortpai(val);
	}
	//获取玩家的牌并排序
	public int[] getPlaypai(int playid){
		ArrayList<Object> list = new ArrayList<Object>();
		
		for(int i=0;i<51;i++){
			if(i%3 == playid){
				list.add(paival[i]);
			}
		}
		int[] val = new int[list.size()];
		for(int i=0;i<list.size();i++){
			val[i] = (Integer) list.get(i);
		}
		return sortpai(val);
		
	}
	public int[] sortpai(int[] val){
		 //排序
		 for(int i = 0;i < val.length-1;i++)
		  {
		   for(int j = 0;j<  val.length-i-1;j++)
		   {
			   	int h_j = val[j]/13;
			  	int h_j1 = val[j+1]/13;
			  
				int temp_j = val[j] %13;
				int temp_j1 = val[j+1]%13;
				
				if(temp_j==0) temp_j = 13;
				if(temp_j1==0) temp_j1 = 13;
				
				if(temp_j==1) temp_j = 14;
				if(temp_j1==1) temp_j1 = 14;
				
				if(temp_j==2) temp_j = 16;
				if(temp_j1==2) temp_j1 = 16;
				
				if( val[j]>52) {h_j=val[j];temp_j=val[j];}
				if( val[j+1]>52) {h_j1=val[j+1];temp_j1 = val[j+1];}
				
			    if((temp_j < temp_j1)||(temp_j == temp_j1&&(h_j<h_j1)))
			    {
			         int temp = val[j];
			         val[j] = val[j+1];
			         val[j+1] = temp;
			    } 
		   }
		  }
		 return val;
	}
	
}
