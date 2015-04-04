package game.data;

import java.util.ArrayList;
import java.util.Random;

public class paidata {
	private int[] paival;
	Random rand;
	public paidata(){
		paival = new int[52];
		for(int i=0;i<52;i++){
			paival[i] = i+1;
		}
		rand = new Random();
	}
	//洗牌
	public int[] xipai(){
		for(int j=0;j<10;j++){
			for(int i=0;i<52;i++){
				int tp = rand.nextInt(1000)%52;
				int val = paival[i];
				if(tp!=i){
					paival[i] = paival[tp];
					paival[tp] = val;
				}
			}
		}
		return paival;
	}
	//获取玩家的牌并排序
	public int[] getPlaypai(int playid,int type){
		ArrayList<Object> list = new ArrayList<Object>();
				if(type>2){
					for(int i=0;i<52;i++){
						if(i%4 == playid){
							list.add(paival[i]);
						}
					}
				}else{
					boolean flag = false;
					for(int i=0;i<51;i++){
						if(i%3 == playid){
							list.add(paival[i]);
							if(paival[i]==3) flag=true;
						}
					}
					if(paival[51]==3 && playid==0) flag=true;
					if(flag) list.add(paival[51]);
				}
				
				int[] val = new int[list.size()];
				for(int i=0;i<list.size();i++){
					val[i] = (Integer) list.get(i);
				}
		
		
		 //排序
		 for(int i = 0;i < list.size()-1;i++)
		  {
		   for(int j = 0;j<list.size()-i-1;j++)
		   {
			  int h_j = val[j]/13;
			  int h_j1 = val[j+1]/13;
			  
			int temp_j = val[j] %13;
			int temp_j1 = val[j+1]%13;
			
			if(temp_j==0) temp_j = 13;
			if(temp_j1==0) temp_j1 = 13;
			
			if(temp_j==1) temp_j = 14;
			if(temp_j1==1) temp_j1 = 14;
			
			if(temp_j==2) temp_j = 15;
			if(temp_j1==2) temp_j1 = 15;
			
		    if((temp_j > temp_j1)||(temp_j == temp_j1&&(h_j>h_j1)))
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
