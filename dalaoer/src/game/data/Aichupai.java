package game.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Aichupai {
	static boolean hasThree(ArrayList<paival> all){
		boolean res = false;
		for(int i=all.size()-1;i>=0;i--){
				paival temp = ((paival)all.get(i));
				if(temp.val==3&&temp.huase==0) res=true;
		}
		return res;
	}
	
	public static ArrayList<paival> first(ArrayList<paival> all,int nextcount){
		ArrayList<paival> res = new ArrayList<paival>();
		if(hasThree(all)){
			//第一次出牌
			int count=0;
			ArrayList<paival> three = new ArrayList<paival>();
			for(int i=0;i<all.size();i++){
				paival tp = all.get(i);
				if(tp.val==3){
					count++;
					three.add(tp);
				}
			}
			
			switch(count){
				case 1:
				    res = getFirstshun(all);
					if(res.size()==5){
						return Sortpai(res);
					}else{
						return Sortpai(three);
					}
				case 2:
					return Sortpai(three);
				case 3:
					ArrayList<paival> dui = hasduipai(all);
					if(dui.size()>0){
						res.addAll(three);
						res.addAll(dui);
						return Sortpai(res);
					}else{
						int t = -1;
						for(int i=0;i<3;i++){
							if((three.get(i)).huase==0){
								res.add(three.get(i));
								t=i;
							}
						}
						int nt = (t+1)%3;
						res.add(three.get(nt));
						return Sortpai(res);
					}
				case 4:
					three.add(onepai(all));
					return Sortpai(three);
			}
			
		}else{
			//普通主动出牌 
			all = Sortpai(all);
			int count = all.size();
			switch(count){
			case 1:
				res.add(all.get(0));
				break;
			case 2:
				if(isdui(all)){
					res = all;
				}else{
					if(nextcount==1){
						res.add(all.get(1));
					}else{
						res.add(all.get(0));
					}
				}
				break;
			case 3:
				if(nextcount==1){
					if(AI_isSun_3(all)){
						res=all;
					}else{
						ArrayList<paival> temp = AI_hasDui(all);
						if(temp.size()>0){
							res = temp;
						}else{
							res.add(all.get(2));
						}
					}
				}else{
					ArrayList<paival> temp = AI_hasDui(all);
					if(temp.size()>0){
						res = temp;
					}else{
						res.add(all.get(0));
					}
				}
				break;
			case 4:
				if(nextcount==1){
					if(AI_isSun_4(all)){
						res = all;
					}else{
						ArrayList<paival> temp = AI_hasDui(all);
						if(temp.size()>0){
							res = temp;
						}else{
							res.add(all.get(3));
						}
					}
				}else{
					ArrayList<paival> temp = AI_hasDui(all);
					if(temp.size()>0){
						res = temp;
					}else{
						res.add(all.get(0));
					}
				}
				break;
			case 5:
				if(ishulu(all)||issun(all)||istiezi(all)||istonghua(all)){
					res = all;
				}else{
					ArrayList<paival> temp = AI_hasDui(all);
					if(nextcount==1){
						if(temp.size()>0){
							res=temp;
						}else{
							res.add(all.get(4));
						}
					}else{
						if(temp.size()>0){
							res=temp;
						}else{
							res.add(all.get(0));
						}
					}
				}
				break;
			default:
				if(nextcount==1){
					//是否有单独的顺牌
					res = AI_hasshun(all);
					if(res.size()==0){
						res = AI_hasHulu(all);
						if(res.size()==0){
							res = AI_hasTieZhi(all);
							if(res.size()==0){
								res = hasTonghuasun(all);
								if(res.size()==0){
									res = AI_hasTonghua(all);
								}
							}
						}
					}
					if(res.size()==0){
						res=AI_hasDuipai(all);
						
					}
					if(res.size()==0){
						res.add(all.get(count-1));
					}
					
				}else{
					res = AI_hasshun(all);
					if(res.size()==0){
						res = AI_hasHulu(all);
					}
					if(res.size()==0){
						res=AI_hasDuipai(all);
					}
					
					if(res.size()==0){
						res.add(all.get(0));
					}
				}
					
			 break;
			}
			
		}
		
		return Sortpai(res);
	}
	static ArrayList<paival> getFirstshun(ArrayList<paival> all){
		
		ArrayList<paival> res = new ArrayList<paival>();
		int[] num =getpainums(all);
		boolean temp = true;
		for(int i=3;i<8;i++){
			if(num[i]>2){
				temp = false;
			}
		}
		
		if(temp){
			for(int i=3;i<8;i++){
				boolean get = true;
				for(int j=0;j<all.size();j++){
					if(get&&(all.get(j)).val==i){
						res.add(all.get(j));
						get=false;
					}
				}
			}
		}
		return res;
	}
	static int[] getpainums(ArrayList<paival> all){
		int[] num = new int[16];
		for(int i=3;i<16;i++){
			for(int j=0;j<all.size();j++){
				if(i==(all.get(j)).val){
					num[i]++;
				}
			}
		}
		return num;
	}
	//是否有对牌
	static ArrayList<paival> AI_hasDuipai(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		if(all.size()<5) return res;
		int[] num = getpainums(all);
		int val_2=0;
		boolean hs_2=false;
		for(int i=0;i<13;i++){
			if(num[i]==2&&!hs_2){
				val_2=i;
				hs_2 = true;
			}
		}
		for(int i=0;i<all.size();i++){
			if((all.get(i)).val==val_2){
				res.add(all.get(i));
			}
		}
		return res;
	}
	//是否有同花
		static ArrayList<paival> AI_hasTonghua(ArrayList<paival> pais){
			ArrayList<paival> res = new ArrayList<paival>();
			if(pais.size()<5) return res;
			ArrayList<paival> meihua = new ArrayList<paival>();
			ArrayList<paival> fangkuai = new ArrayList<paival>();
			ArrayList<paival> hongtao = new ArrayList<paival>();
			ArrayList<paival> heitao = new ArrayList<paival>();
			for(int i=0;i<pais.size();i++){
				if((pais.get(i)).huase==0){
					meihua.add(pais.get(i));
				}
				if((pais.get(i)).huase==1){
					fangkuai.add(pais.get(i));
				}
				if((pais.get(i)).huase==2){
					hongtao.add(pais.get(i));
				}
				if((pais.get(i)).huase==3){
					heitao.add(pais.get(i));
				}
			}
			if(meihua.size()>4){
				res.add(meihua.get(0));
				res.add(meihua.get(1));
				res.add(meihua.get(2));
				res.add(meihua.get(3));
				res.add(meihua.get(4));
				return  res;
			}
			if(fangkuai.size()>4){
				res.add(fangkuai.get(0));
				res.add(fangkuai.get(1));
				res.add(fangkuai.get(2));
				res.add(fangkuai.get(3));
				res.add(fangkuai.get(4));
				return  res;
			}
			
			if(hongtao.size()>4){
				res.add(hongtao.get(0));
				res.add(hongtao.get(1));
				res.add(hongtao.get(2));
				res.add(hongtao.get(3));
				res.add(hongtao.get(4));
				return  res;
			}
			
			if(heitao.size()>4){
				res.add(heitao.get(0));
				res.add(heitao.get(1));
				res.add(heitao.get(2));
				res.add(heitao.get(3));
				res.add(heitao.get(4));
				return  res;
			}
			
			return res;
		}
	//是否有铁支
		static ArrayList<paival> AI_hasTieZhi(ArrayList<paival> all){
			ArrayList<paival> res = new ArrayList<paival>();
			if(all.size()<5) return res;
			int[] num = getpainums(all);
			int val_4=0,val_1=0,val_2=0,val_3=0;
			boolean hs_4=false,hs_1=false,hs_2=false,hs_3=false;
			for(int i=0;i<num.length;i++){
				if(num[i]==4&&!hs_4){
					val_4=i;
					hs_4 = true;
				}
				if(num[i]==1&&!hs_1){
					val_1=i;
					hs_1 = true;
				}
				if(num[i]==2&&!hs_2){
					val_2=i;
					hs_2 = true;
				}
				if(num[i]==3&&!hs_3){
					val_3=i;
					hs_3 = true;
				}
			}
			if(hs_4){
				for(int i=0;i<all.size();i++){
					if(val_4==(all.get(i)).val){
						res.add(all.get(i));
					}
				}
				
				if(hs_1||hs_2){
					if(hs_1){
						res.add(getpaiVal(all,val_1));
					}else{
						boolean tp = true;
						for(int i=0;i<all.size();i++){
							if(val_2==(all.get(i)).val && tp){
							  res.add(all.get(i));	
							  tp = false;
							}
						}
					}
				}
				
				if(res.size()==4&&hs_3){
					boolean tp = true;
					for(int i=0;i<all.size();i++){
						if(val_3==(all.get(i)).val && tp){
						  res.add(all.get(i));	
						  tp = false;
						}
					}
				}
				if(res.size()==4){
					res = new ArrayList<paival>();
				}
			}
			return res;
			
		}
	//是否有葫芦
	static ArrayList<paival> AI_hasHulu(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		if(all.size()<5) return res;
		int[] num = getpainums(all);
		int val_2=0,val_3=0;
		boolean hs_2=false,hs_3=false;
		for(int i=0;i<num.length;i++){
			if(num[i]==2&&!hs_2){
				val_2=i;
				hs_2 = true;
			}
			if(num[i]==3&&!hs_3){
				val_3=i;
				hs_3=true;
			}
		}
		if(hs_2&&hs_3&&val_3<15){
			for(int i=0;i<all.size();i++){
				if(val_2==(all.get(i)).val||val_3==(all.get(i)).val){
					res.add(all.get(i));
				}
			}
		}
		return res;
		
	}
	//是否有对牌，有就取得对牌
	static ArrayList<paival> AI_hasDui(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int[] num = getpainums(all);
		int value=0;
		boolean has=false;
		for(int i=0;i<num.length;i++){
			if(num[i]==2&&!has){
				value=i;
				has = true;
			}
		}
		if(value>0){
			for(int j=0;j<all.size();j++){
				if(value==(all.get(j)).val){
					res.add(all.get(j));
				}
			}
		}
		return res;
	}
	//是否最后4张牌相等
	static boolean AI_isSun_4(ArrayList<paival> all){
		boolean res = false;
		if((all.get(2)).val == (all.get(3)).val &&(all.get(0)).val == (all.get(1)).val &&(all.get(1)).val == (all.get(2)).val){
			res = true;
		}
		return res;
		
	}
	//是否最后3张牌相等
	static boolean AI_isSun_3(ArrayList<paival> all){
		boolean res = false;
		if((all.get(0)).val == (all.get(1)).val &&(all.get(1)).val == (all.get(2)).val){
			res = true;
		}
		return res;
		
	}
	//是否存在独立的顺子
	static ArrayList<paival> AI_hasshun(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int[] num = getpainums(all);
		//获取只有一张牌的所有牌
		ArrayList<paival> res_1 = new ArrayList<paival>();
		for(int i=0;i<num.length;i++){
			if(num[i]==1){
				res_1.add(getpaiVal(all,i));
			}
		}
		if(res_1.size()>4){
			for(int i=0;i<res_1.size()-4;i++){
				ArrayList<paival> temp = new ArrayList<paival>();
				temp.add(res_1.get(i));
				temp.add(res_1.get(i+1));
				temp.add(res_1.get(i+2));
				temp.add(res_1.get(i+3));
				temp.add(res_1.get(i+4));
				if(issun(temp)){
					res=temp;
				}
			}
		}
		return res;
		
	}
	//获取只要唯一的牌
	static paival getpaiVal(ArrayList<paival> all,int val){
		for(int j=0;j<all.size();j++){
			if(val==(all.get(j)).val){
				return all.get(j);
			}
		}
		return null;
	}
	//AI压牌
	public static ArrayList<paival> genpai(ArrayList<paival> all,ArrayList<paival> pre,int nextcount){
		ArrayList<paival>  res = new ArrayList<paival>();
		int num = pre.size();
		int px = returnpx(pre);
		all = Sortpai(all);
		int allnum = all.size();
		paival big = getBiggest(pre, px);
		switch(num){
		case DangPai:
			paival pai = pre.get(0);
			ArrayList<paival>  temp = new ArrayList<paival>();
			for(int i=0;i<all.size();i++){
				if((all.get(i)).val>pai.val ||((all.get(i)).val==pai.val && (all.get(i)).huase > pai.huase)){
					temp.add(all.get(i));
				}
			}
			temp = Sortpai(temp);
			if(temp.size()>0){
				if(temp.size()<3){
					res.add(temp.get(0));
				}else{
					res = getMinonePai(temp);
					if(res.size()==0){
						if(pai.val!=2&&pai.huase!=3){
							ArrayList<paival> tiezitemp = hasTiezi(all);
							if(tiezitemp.size()>0){
								res = tiezitemp;
							}else{
								res = hasTonghuasun(all);
							}
						}
					}
					
				}
			}
			if(nextcount==1){
				if(res.size()==1){
					res = new ArrayList<paival>();
					res.add(all.get(allnum-1));
				}
			}
			return res;
		case DuiPai:
			 
			 res = getmindui(all,big);
			 if(res.size()==0){
				 ArrayList<paival> tiezitemp = hasTiezi(all);
				 if(tiezitemp.size()>0){
					res = tiezitemp;
				 }else{
					res = hasTonghuasun(all);
				 }
			 }
			break;
		case 5:
			switch(px){
			case SunPai:
				if(allnum>=5){
					res = hasBigShun(all,big);
					if(res.size()==0){
						 ArrayList<paival> tiezitemp = hasTiezi(all);
						if(tiezitemp.size()>0){
							res = tiezitemp;
						}else{
							res = hasTonghuasun(all);
						}
					}
				}
				break;
			case HuluPai:
				res = hasHulu(all,big);
				if(res.size()==0){
					 ArrayList<paival> tiezitemp = hasTiezi(all);
					if(tiezitemp.size()>0){
						res = tiezitemp;
					}else{
						res = hasTonghuasun(all);
					}
				}
				break;
			case TongHuaPai:
				res = hasTongHua(all,big);
				if(res.size()==0){
					 ArrayList<paival> tiezitemp = hasTiezi(all);
					if(tiezitemp.size()>0){
						res = tiezitemp;
					}else{
						res = hasTonghuasun(all);
					}
				}
				break;
			case TieziPai:
				res = hasBigTiezi(all,big);
				if(res.size()==0){
					res = hasTonghuasun(all);
				}
				break;
			case TongHuasunPai:
				res = hasbigTonghuasun(all,big);
				break;
			}
			
			break;
		}
		return Sortpai(res);
	}
	//是否有更大的顺
	static  ArrayList<paival> hasBigShun(ArrayList<paival> pais,paival big){
		ArrayList<paival> res = new ArrayList<paival>();
		res = hasshun_big(pais,big);
		
		return res;
	}
	//是否存在独立的更大的顺
		static ArrayList<paival> hasshun_big(ArrayList<paival> all,paival big){
			ArrayList<paival> res = new ArrayList<paival>();
			int[] num = getpainums(all);
			//获取只有一张牌的所有牌
			ArrayList<paival> res_1 = new ArrayList<paival>();
			for(int i=0;i<num.length;i++){
				if(num[i]==1){
					res_1.add(getpaiVal(all,i));
				}
			}
			if(res_1.size()>4){
				for(int i=0;i<res_1.size()-4;i++){
					ArrayList<paival> temp = new ArrayList<paival>();
					temp.add(res_1.get(i));
					temp.add(res_1.get(i+1));
					temp.add(res_1.get(i+2));
					temp.add(res_1.get(i+3));
					temp.add(res_1.get(i+4));
					if(issun(temp)&&hasbig(temp,big)){
						res=temp;
					}
				}
			}
			return res;
			
		}
	//是否有同花
	static ArrayList<paival> hasTongHua(ArrayList<paival> pais,paival big){
		ArrayList<paival> res = new ArrayList<paival>();
		ArrayList<paival> meihua = new ArrayList<paival>();
		ArrayList<paival> fangkuai = new ArrayList<paival>();
		ArrayList<paival> hongtao = new ArrayList<paival>();
		ArrayList<paival> heitao = new ArrayList<paival>();
		for(int i=0;i<pais.size();i++){
			if((pais.get(i)).huase==0){
				meihua.add(pais.get(i));
			}
			if((pais.get(i)).huase==1){
				fangkuai.add(pais.get(i));
			}
			if((pais.get(i)).huase==2){
				hongtao.add(pais.get(i));
			}
			if((pais.get(i)).huase==3){
				heitao.add(pais.get(i));
			}
		}
		if(meihua.size()>4){
			res =  hastonghuashun_2(meihua);
			if(res.size()>0) {
				return res;
			}else{
				if(hasbig(meihua,big)){
					int nu = 0;
					for(int i=meihua.size()-1;i>0&&nu<5;i--){
							res.add(meihua.get(i));
							nu++;
					}
				}
			}
			if(res.size()>0)  return res;
		}
		if(fangkuai.size()>4){
			res =  hastonghuashun_2(fangkuai);
			if(res.size()>0){
				return res;
			}else{
				if(hasbig(fangkuai,big)){
					int nu = 0;
					for(int i=fangkuai.size()-1;i>0&&nu<5;i--){
							res.add(fangkuai.get(i));
							nu++;
					}
				}
			}
			if(res.size()>0)  return res;
		}
		
		if(hongtao.size()>4){
			res =  hastonghuashun_2(hongtao);
			if(res.size()>0){
				return res;
			}else{
				if(hasbig(hongtao,big)){
					int nu = 0;
					for(int i=hongtao.size()-1;i>0&&nu<5;i--){
							res.add(hongtao.get(i));
							nu++;
					}
				}
			}
			if(res.size()>0)  return res;
		}
		
		if(heitao.size()>4){
			res =  hastonghuashun_2(heitao);
			if(res.size()>0){
				return res;
			}else{
				if(hasbig(heitao,big)){
					int nu = 0;
					for(int i=heitao.size()-1;i>0&&nu<5;i--){
							res.add(heitao.get(i));
							nu++;
					}
				}
			}
			if(res.size()>0)  return res;
		}
		
		
		return res;
	}
	//是否有更大的
	static boolean hasbig(ArrayList<paival> pais,paival big){
		boolean res = false;
		for(int i=0;i<pais.size();i++){
			if(big.val<(pais.get(i)).val || (big.val==(pais.get(i)).val &&big.huase<(pais.get(i)).huase)){
				res = true;
			}
		}
		return res;
	}
	//判断是否有葫芦,返回最大的葫芦
	static ArrayList<paival> hasHulu(ArrayList<paival> pais,paival big){
		int[] num = new int[16];
		int[] num_2 = new int[16];
		ArrayList<paival> res = new ArrayList<paival>();
		if(pais.size()<5) return res;
		for(int i=3;i<16;i++){
			for(int j=0;j<pais.size();j++){
				if(i==(pais.get(j)).val){
					num[i]++;
				}
			}
		}
		boolean has_2=false,has_3=false;
		int count = 0,val_2 = 0,val_3 = 0,val=0;
		for(int i=0;i<num.length;i++){
			if(num[i]==3&&!has_3&& i>big.val){
				val_3 = i;
				has_3=true;
			}
			if(num[i]==2&&!has_2){
				val_2 = i;
				has_2=true;
			}
			
			if(num[i]==3){
				count++;
				num_2[i] = i;
				if(i>val_3) val=i;
			}
		}
		if(has_2&&has_3){
			for(int j=0;j<pais.size();j++){
				if(val_2==(pais.get(j)).val||val_3==(pais.get(j)).val){
					res.add(pais.get(j));
				}
			}
		}else{
			if(count>1){
				int n = 0;
				for(int j=0;j<pais.size();j++){
					if(val_3==(pais.get(j)).val&&n<2){
						res.add(pais.get(j));
						n++;
					}
					if(val==(pais.get(j)).val){
						res.add(pais.get(j));
					}
				}
			}
		}
		if(res.size()<5){
			res =  new ArrayList<paival>();
		}
		return Sortpai(res);
		
	}
	//是否有更大的铁支
	static ArrayList<paival> hasBigTiezi(ArrayList<paival> pais,paival big){
		int[] num = new int[16];
		ArrayList<paival> res = new ArrayList<paival>();
		if(pais.size()<5) return res;
		for(int i=3;i<16;i++){
			for(int j=0;j<pais.size();j++){
				if(i==(pais.get(j)).val&&i>big.val){
					num[i]++;
				}
			}
		}
		boolean has=false,has_1=false,has_2=false,has_3=false;
		int val = 0,val_1 = 0,val_2 = 0,val_3 = 0;
		for(int i=0;i<num.length;i++){
			if(num[i]==4&&!has){
				val = i;
				has=true;
			}
			if(num[i]==3&&!has_3){
				val_3 = i;
				has_3=true;
			}
			if(num[i]==2&&!has_2){
				val_2 = i;
				has_2=true;
			}
			if(num[i]==1&&!has_1){
				val_1 = i;
				has_1=true;
			}
		}
		if(val>0){
			for(int j=0;j<pais.size();j++){
				if(val==(pais.get(j)).val){
					res.add(pais.get(j));
				}
			}
			
			if(val_1>0){
				boolean temp=false;
				for(int j=0;j<pais.size();j++){
					if(val_1==(pais.get(j)).val&&!temp){
						res.add(pais.get(j));
					}
				}
			}
			
			if(val_1==0&&val_2>0){
				boolean temp=false;
				for(int j=0;j<pais.size();j++){
					if(val_2==(pais.get(j)).val&&!temp){
						res.add(pais.get(j));
						temp = true;
					}
				}
			}
			
			if(val_1==0&&val_2==0&&val_3>0){
				boolean temp=false;
				for(int j=0;j<pais.size();j++){
					if(val_3==(pais.get(j)).val&&!temp){
						res.add(pais.get(j));
						temp = true;
					}
				}
			}
		}
		return res;
	}
	//是否有铁子,返回最小的一组铁支
	static ArrayList<paival> hasTiezi(ArrayList<paival> pais){
		int[] num = new int[16];
		ArrayList<paival> res = new ArrayList<paival>();
		if(pais.size()<5) return res;
		for(int i=3;i<16;i++){
			for(int j=0;j<pais.size();j++){
				if(i==(pais.get(j)).val){
					num[i]++;
				}
			}
		}
		boolean has=false,has_1=false,has_2=false,has_3=false;
		int val = 0,val_1 = 0,val_2 = 0,val_3 = 0;
		for(int i=0;i<num.length;i++){
			if(num[i]==4&&!has){
				val = i;
				has=true;
			}
			if(num[i]==3&&!has_3){
				val_3 = i;
				has_3=true;
			}
			if(num[i]==2&&!has_2){
				val_2 = i;
				has_2=true;
			}
			if(num[i]==1&&!has_1){
				val_1 = i;
				has_1=true;
			}
		}
		
		if(val>0){
			for(int j=0;j<pais.size();j++){
				if(val==(pais.get(j)).val){
					res.add(pais.get(j));
				}
			}
			
			if(val_1>0){
				boolean temp=false;
				for(int j=0;j<pais.size();j++){
					if(val_1==(pais.get(j)).val&&!temp){
						res.add(pais.get(j));
					}
				}
			}
			
			if(val_1==0&&val_2>0){
				boolean temp=false;
				for(int j=0;j<pais.size();j++){
					if(val_2==(pais.get(j)).val&&!temp){
						res.add(pais.get(j));
						temp = true;
					}
				}
			}
			
			if(val_1==0&&val_2==0&&val_3>0){
				boolean temp=false;
				for(int j=0;j<pais.size();j++){
					if(val_3==(pais.get(j)).val&&!temp){
						res.add(pais.get(j));
						temp = true;
					}
				}
			}
		}
		
		return res;
	}
	//是否有跟大的同花顺,返回最小一组同花顺
	static ArrayList<paival> hasbigTonghuasun(ArrayList<paival> pais,paival big){
		ArrayList<paival> res = new ArrayList<paival>();
		ArrayList<paival> meihua = new ArrayList<paival>();
		ArrayList<paival> fangkuai = new ArrayList<paival>();
		ArrayList<paival> hongtao = new ArrayList<paival>();
		ArrayList<paival> heitao = new ArrayList<paival>();
		for(int i=0;i<pais.size();i++){
			if((pais.get(i)).huase==0){
				meihua.add(pais.get(i));
			}
			if((pais.get(i)).huase==1){
				fangkuai.add(pais.get(i));
			}
			if((pais.get(i)).huase==2){
				hongtao.add(pais.get(i));
			}
			if((pais.get(i)).huase==3){
				heitao.add(pais.get(i));
			}
		}
		switch(big.huase){
		case 0:
			res = hastonghuashun_3(meihua,big);
			break;
		case 1:
			res = hastonghuashun_3(fangkuai,big);
			break;
		case 2:
			res = hastonghuashun_3(hongtao,big);
			break;
		case 3:
			res = hastonghuashun_3(heitao,big);
			break;
		}
		if(res.size()==0){
			res = hastonghuashun_3(meihua,big);
			if(res.size()==0){
				res = hastonghuashun_3(fangkuai,big);
				if(res.size()==0){
					res = hastonghuashun_3(hongtao,big);
					if(res.size()==0){
						res = hastonghuashun_3(heitao,big);
					}
				}
			}
		}
		return res;
	}
	//是否有同花顺,返回最小一组同花顺
	static ArrayList<paival> hasTonghuasun(ArrayList<paival> pais){
		ArrayList<paival> meihua = new ArrayList<paival>();
		ArrayList<paival> fangkuai = new ArrayList<paival>();
		ArrayList<paival> hongtao = new ArrayList<paival>();
		ArrayList<paival> heitao = new ArrayList<paival>();
		for(int i=0;i<pais.size();i++){
			if((pais.get(i)).huase==0){
				meihua.add(pais.get(i));
			}
			if((pais.get(i)).huase==1){
				fangkuai.add(pais.get(i));
			}
			if((pais.get(i)).huase==2){
				hongtao.add(pais.get(i));
			}
			if((pais.get(i)).huase==3){
				heitao.add(pais.get(i));
			}
		}
		ArrayList<paival> res = new ArrayList<paival>();
		if(meihua.size()>4){
			return  hastonghuashun_2(meihua);
		}
		if(fangkuai.size()>4){
			return  hastonghuashun_2(fangkuai);
		}
		
		if(hongtao.size()>4){
			return  hastonghuashun_2(hongtao);
		}
		
		if(heitao.size()>4){
			return  hastonghuashun_2(heitao);
		}
		
		return res;
	
	}
	//是否有大同花顺
		static ArrayList<paival> hastonghuashun_3(ArrayList<paival> pais,paival big){
			ArrayList<paival> res = new ArrayList<paival>();
			if(pais.size()<5) return res;
			pais = Sortpai(pais);
			for(int i=0;i<pais.size()-4;i++){
				ArrayList<paival> temp = new ArrayList<paival>();
				temp.add(pais.get(i));
				temp.add(pais.get(i+1));
				temp.add(pais.get(i+2));
				temp.add(pais.get(i+3));
				temp.add(pais.get(i+4));
				if(isthsun(temp)&&hasbig(pais, big)){
					return temp;
				}
			}
			return res;
		}
		
	//是否含同花顺
	static ArrayList<paival> hastonghuashun_2(ArrayList<paival> pais){
		pais = Sortpai(pais);
		ArrayList<paival> res = new ArrayList<paival>();
		for(int i=0;i<pais.size()-4;i++){
			ArrayList<paival> temp = new ArrayList<paival>();
			temp.add(pais.get(i));
			temp.add(pais.get(i+1));
			temp.add(pais.get(i+2));
			temp.add(pais.get(i+3));
			temp.add(pais.get(i+4));
			if(isthsun(temp)){
				return temp;
			}
		}
		return res;
	}
	//获取最小的一张单牌
	static ArrayList<paival> getMinonePai(ArrayList<paival> pais){
		int[] nums = new int[16];
		for(int i=3;i<16;i++){
			for(int j=0;j<pais.size();j++){
				if(i==(pais.get(j)).val){
					nums[i]++;
				}
			}
		}
		boolean has = false,has_2 = false;
		int val = 0,val_2=0;
		for(int i=0;i<nums.length;i++){
			if(nums[i]==1&&!has){
				has = true;
				val=i;
			}
			if(nums[i]==2&&!has_2){
				val_2 = i;
				has_2 = true;
			}

		}
		ArrayList<paival> res = new ArrayList<paival>(); 
		//存在单牌
		if(val>0){
			for(int j=0;j<pais.size();j++){
				if(val==(pais.get(j)).val){
					res.add(pais.get(j));
				}
			}
		}
		//没有单牌，有对牌
		if(val==0&&val_2>0){
			boolean temp=false;
			for(int j=0;j<pais.size();j++){
				if(val_2==(pais.get(j)).val&&!temp){
					res.add(pais.get(j));
					temp=true;
				}
			}
		}
		return res;
	}
	
	//获取符合的牌组合
	static Map<String,ArrayList<paival>> getpailist(ArrayList<paival> pais,paival big,int px){
		Map<String,ArrayList<paival>> res = new HashMap<String,ArrayList<paival>>();
		ArrayList<paival> tempall = new ArrayList<paival>();
		switch(px){
			case DuiPai:
				
				for(int i=0;i<pais.size();i++){
					if((pais.get(i)).val>=big.val){
						tempall.add(pais.get(i));
					}
				}
				
			break;
			case SunPai:
				
				break;
		}
		
		return res;
	}
	//最小对牌
	static ArrayList<paival> getmindui(ArrayList<paival> pais,paival big){
		ArrayList<paival> fh = new ArrayList<paival>();
		ArrayList<paival> res = new ArrayList<paival>();
		//获取比big大的所有牌
		for(int i=0;i<pais.size();i++){
			if((pais.get(i)).val>=big.val){
				fh.add(pais.get(i));
			}
		}
		
		int[] nums = new int[17];
		ArrayList<paival> fhs = new ArrayList<paival>();
		ArrayList<paival> fhs_2 = new ArrayList<paival>();
		for(int i=big.val;i<16;i++){
			for(int j=0;j<fh.size();j++){
				if(i==(fh.get(j)).val){
					nums[i]++;
				}
			}
			//获得所有大于big的对牌
			if(nums[i]>=2){
				for(int j=0;j<fh.size();j++){
					if(i==(fh.get(j)).val){
						fhs.add(fh.get(j));
						if(nums[i]==2){
							fhs_2.add(fh.get(j));
						}
					}
					
				}
			}
		}
		fhs=Sortpai(fhs);
		fhs_2=Sortpai(fhs_2);
		if(fhs_2.size()>1){
			if((fhs_2.get(0)).huase>big.huase||(fhs_2.get(1)).huase>big.huase){
				res.add(fhs_2.get(0));
				res.add(fhs_2.get(1));
			}else{
				if(fhs_2.size()>2){
					res.add(fhs_2.get(2));
					res.add(fhs_2.get(3));
				}else{
					if(fhs.size()>1){
						res.add(fhs.get(0));
						res.add(fhs.get(1));
					}
				}
			}
		}else{
			if(fhs.size()>1){
				res.add(fhs.get(0));
				res.add(fhs.get(1));
			}
		}
		//再次判断
		if(res.size()>0){
			paival temp = getBiggest(res,DuiPai);
			if(temp.huase<big.huase&&temp.val==big.val) res=new ArrayList<paival>();
		}
		return res;
	}
	//排序
	static ArrayList<paival> Sortpai(ArrayList<paival> pais){
		if(pais.size()<2) return pais;
		boolean flag=true;
		for(int i=1;i<pais.size() && flag;i++){
			flag = false;
			for(int j=0;j<pais.size()-i;j++){
				if(((pais.get(j)).val > (pais.get(j+1)).val)||((pais.get(j)).val == (pais.get(j+1)).val && (pais.get(j)).huase > (pais.get(j+1)).huase)){
					paival temp = pais.get(j);
					pais.set(j, pais.get(j+1));
					pais.set(j+1,temp);
					flag = true;
				}
			}
		}
		return pais;
	}
	
	//单张牌
	
	static paival onepai(ArrayList<paival> pais){
		ArrayList<paival> res = new ArrayList<paival> ();
		for(int i=4;i<15;i++){
			int n = 0;
			ArrayList<paival> temp = new ArrayList<paival>();
			for(int j=0;j<pais.size();j++){
				if(i==(pais.get(j)).val){
					n++;
					temp.add(pais.get(j));
				}
			}
			if(n==1){
				return temp.get(0);
			}else{
				res.addAll(temp);
			}
			
		}
		
		return res.get(0);
	}
	//3<i<12小于12的对牌
	static ArrayList<paival> hasduipai(ArrayList<paival> pais){
		ArrayList<paival> res = new ArrayList<paival>();
		for(int i=4;i<12;i++){
			int n = 0;
			ArrayList<paival> temp = new ArrayList<paival>();
			for(int j=0;j<pais.size();j++){
				if(i==(pais.get(j)).val){
					n++;
					temp.add(pais.get(j));
				}
			}
			if(n==2){
				return temp;
			}
		}
		return res;
	}
	//对牌
	static int[][] duipai ={
		{3,3},
		{4,4},
		{5,5},
		{6,6},
		{7,7},
		{8,8},
		{9,9},
		{10,10},
		{11,11},
		{12,12},
		{13,13},
		{14,14},
		{15,15},
	};
	//顺牌
	static int[][] sunpai={{14,15,3,4,5},
					{3,4,5,6,7},
					{4,5,6,7,8},
					{5,6,7,8,9},
					{6,7,8,9,10},
					{7,8,9,10,11},
					{8,9,10,11,12},
					{9,10,11,12,13},
					{10,11,12,13,14},
					{15,3,4,5,6},
	};
	//获取顺牌大小
	public static int getSunindex(ArrayList<paival> pais){
		int res = -1;
		for(int i=0;i<sunpai.length;i++){
			boolean[] temp = new boolean[5]; 
			for(int z=0;z<5;z++){
				for(int j=0;j<pais.size();j++){
					if(sunpai[i][z]==(pais.get(j)).val){
						temp[z]=true;
					}
				}
			}
			if(temp[0]&&temp[1]&&temp[2]&&temp[3]&&temp[4]){
				res = i;
			}
		}
		return res;
	}
	
	//是否是对
	public static boolean isdui(ArrayList<paival> pais){
		if(pais.size()<2) return false;
		if((pais.get(0)).val==(pais.get(1)).val && pais.size()==2) return true;
		return false;
	}
	//是否为顺牌
	public static boolean issun(ArrayList<paival> pais){
		if(pais.size()<5) return false;
		boolean res = false;
		for(int i=0;i<sunpai.length;i++){
			boolean ress[] = new boolean[5];
			for(int z=0;z<5;z++){
				for(int j=0;j<pais.size();j++){
					if(sunpai[i][z]==(pais.get(j)).val){
						ress[z] = true;
					}
				}
			}
			if(ress[0]&&ress[1]&&ress[2]&&ress[3]&&ress[4]){
				return true;
			}
		}
		return res;
	}
	
	//是否为同花
	public static boolean istonghua(ArrayList<paival> pais){
		if(pais.size()<5) return false;
		if((pais.get(0)).huase==(pais.get(1)).huase&&(pais.get(1)).huase==(pais.get(2)).huase&&(pais.get(2)).huase==(pais.get(3)).huase&&(pais.get(3)).huase==(pais.get(4)).huase){
			return true;
		}
		return false;
	}
	//是否同花顺
	public static boolean isthsun(ArrayList<paival> pais){
		if(pais.size()<5) return false;
		return issun(pais)&&istonghua(pais);
	}
	//是否为铁只
	public static boolean istiezi(ArrayList<paival> pais){
		if(pais.size()<5) return false;
		boolean res = false;
		for(int i=0;i<5;i++){
			int count = 0;
			int val = (pais.get(i)).val;
			for(int j=0;j<5;j++){
				if(val == (pais.get(j)).val){
					count++;
				}
			}
			if(count==4){
				return true;
			}
		}
		
		return res;
	}
	//葫芦
	public static boolean ishulu(ArrayList<paival> pais){
		boolean res = false;
		boolean res_3=false;
		if(pais.size()<5){
			return false;
		}
		for(int i=0;i<5;i++){
			int count = 0;
			int val = (pais.get(i)).val;
			for(int j=0;j<5;j++){
				if(val == (pais.get(j)).val){
					count++;
				}
			}
			if(count==3){
				res_3 = true;
			}
			if(count==2){
				res = true;
			}
		}
		
		return res&&res_3;
	}
	//判断牌型
	public static int returnpx(ArrayList<paival> pais){
		int res = 0;
		if(pais.size()==1){
			res = DangPai;
		}else if(isdui(pais)){
			res = DuiPai;
		}else if(issun(pais)){
			res = SunPai;
		}else if(istonghua(pais)){
			res = TongHuaPai;
		}else if(ishulu(pais)){
			res = HuluPai;
		}else if(istiezi(pais)){
			res = TieziPai;
		}else if(isthsun(pais)){
			res = TongHuasunPai;
		}
		if(issun(pais)&&istonghua(pais)) res = TongHuasunPai;
		return res;
	}
	static final int DangPai=1,DuiPai=2,SunPai=3,TongHuaPai=4,HuluPai=5,TieziPai=6,TongHuasunPai=7;
	//判断大小
	public static boolean returnBig(ArrayList<paival> pais,ArrayList<paival> pre){
		boolean res = false;
		int px_1 = returnpx(pais);
		int px_2 = returnpx(pre);
		paival p_1 = getBiggest(pais,px_1);
		paival p_2 = getBiggest(pre,px_2);
		if(px_1==px_2){
			if(p_1.val>p_2.val || (p_1.val==p_2.val&&p_1.huase>p_2.huase)){
				res = true;
			}
		}else{
			if(px_1==TieziPai){
				if(p_1.val==15||p_2.val<TieziPai||(px_2<px_1&&!(px_2==DangPai&&p_2.val==15&&p_2.huase==3))){
					res=true;
				}
			}
			
			if(px_1==TongHuasunPai){
				if(px_2<TongHuasunPai||(px_2<px_1&&!(px_2==DangPai&&p_2.val==15&&p_2.huase==3))){
					res=true;
				}
			}
		}
		
		return res;
	}
	//最大的牌
	public static paival getBiggest(ArrayList<paival> pais,int px){
		//Log.v("log","getBiggest="+pais.size());
		
			int num = pais.size();
			int index=0;
		switch(px){
			case DangPai:
				
				break;
			case DuiPai:
				if((pais.get(1)).huase > (pais.get(index)).huase){
					index=1;
				}
				break;
			case SunPai:
				for(int j=1;j<num;j++){
					if((pais.get(j)).val > (pais.get(index)).val){
						index=j;
					}
				}
				break;
			case TongHuaPai:
				for(int j=1;j<num;j++){
					if((pais.get(j)).val > (pais.get(index)).val){
						index=j;
					}
				}
				break;
			case HuluPai:
				for(int i=0;i<num;i++){
					int count = 0;
					int val = (pais.get(i)).val;
					for(int j=0;j<num;j++){
						if(val == (pais.get(j)).val){
							count++;
						}
					}
					if(count==3){
						index = i;
					}
				}
				break;
			case TieziPai:
				for(int i=0;i<num;i++){
					int count = 0;
					int val = (pais.get(i)).val;
					for(int j=0;j<num;j++){
						if(val == (pais.get(j)).val){
							count++;
						}
					}
					if(count==4){
						index = i;
					}
				}
				break;
			case TongHuasunPai:
				for(int j=1;j<num;j++){
					if((pais.get(j)).val > (pais.get(index)).val){
						index=j;
					}
				}
		break;	
	}
		
		return pais.get(index);	
	}
	//葫芦音效
	public static int getHulusound(ArrayList<paival> all,int px){
		int res = ((getBiggest(all,px)).val)%13-1;
		if(res<0) res= 12;
		return res;
	}
	
	//获取提示牌
	public static ArrayList<ArrayList<paival>> Play_tishi(ArrayList<paival> all,ArrayList<paival> pre){
		//int pnum = pre.size();
		//int allnum = all.size();
		int prepx = returnpx(pre);
		paival prebig = getBiggest(pre, prepx); 
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		
		Log.v("log",""+prepx);
		switch(prepx){
		case DangPai:
			for(int i=0;i<all.size();i++){
				if((all.get(i).val>prebig.val)||(all.get(i).val==prebig.val&&all.get(i).huase>prebig.huase)){
					ArrayList<paival> tp = new ArrayList<paival>();
					tp.add(all.get(i));
					res.add(tp);
				}				
			}
			if(prebig.val!=15&&prebig.huase!=3){
				res.addAll(gettiezhilist(all,0));
				res.addAll(gettonghusunlist(all,pre,1));
			}
			break;
		case DuiPai:
			res = getduipai(all,prebig);
			res.addAll(gettiezhilist(all,0));
			res.addAll(gettonghusunlist(all,pre,1));
			break;
		case SunPai:
			res=getshunpai(all,pre); 
			res.addAll(gettiezhilist(all,0));
			res.addAll(gettonghusunlist(all,pre,1));
			break;
		case TongHuaPai:
			res = gettonghualist(all,pre);
			res.addAll(gettiezhilist(all,0));
			res.addAll(gettonghusunlist(all,pre,1));
			break;
		case HuluPai:
			res = gethululist(all, prebig);
			res.addAll(gettiezhilist(all,0));
			res.addAll(gettonghusunlist(all,pre,1));
			break;
		case TieziPai:
			res = gettiezhilist(all,prebig.val);
			res.addAll(gettonghusunlist(all,pre,1));
			break;
		case TongHuasunPai:
			res = gettonghusunlist(all,pre,0);
			break;
		}
		for(int i=0;i<res.size();i++){
		//	Log.v("log", "l="+res.get(i).get(0).val+","+res.get(i).get(1).val+","+res.get(i).get(2).val+","+res.get(i).get(3).val+","+res.get(i).get(4).val+"");
		}
		return res;
	}
	static ArrayList<ArrayList<paival>> gettonghusunlist(ArrayList<paival> all,ArrayList<paival> pre,int type){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		ArrayList<ArrayList<paival>> thlist = gettonghualist(all,pre);
		if(type==1){
			//获取所有同花顺
			for(int i=0;i<thlist.size();i++){
				if(issun(thlist.get(i))){
					res.add(thlist.get(i));
				}
			}
		}else{
			for(int i=0;i<thlist.size();i++){
				if(issun(thlist.get(i))&&returnBig(thlist.get(i), pre)){
					res.add(thlist.get(i));
				}
			}
		}
		
		return res;
	}
	//获取铁支
	static ArrayList<ArrayList<paival>> gettiezhilist(ArrayList<paival> all,int bigest){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		int[] count = new int[16];
		//计算符合条件的牌
		for(int i=0;i<all.size();i++){
			count[all.get(i).val]+=1;
		}
		int size = all.size();
		if(size<5) return res;
		ArrayList<ArrayList<paival>> fourlist = new ArrayList<ArrayList<paival>>();
		
		if(bigest>0){
			for(int i=0;i<count.length;i++){
				if(count[i]==4&&i>bigest){
					ArrayList<paival> t = new ArrayList<paival>();
					for(int j=0;j<size;j++){
						if(i==all.get(j).val ){
							t.add(all.get(j));
						}
					}
					fourlist.add(t);
				}
			}
			
		}else{
			for(int i=0;i<count.length;i++){
				if(count[i]==4){
					ArrayList<paival> t = new ArrayList<paival>();
					for(int j=0;j<size;j++){
						if(i==all.get(j).val ){
							t.add(all.get(j));
						}
					}
					fourlist.add(t);
				}
			}
		}
		
		for(int i=0;i<fourlist.size();i++){
		
			for(int j=0;j<size;j++){
				if(all.get(j).val!=fourlist.get(i).get(0).val){
					ArrayList<paival> t = new ArrayList<paival>();
					t.addAll(fourlist.get(i));
					t.add(all.get(j));
					res.add(t);
				}
			}
		}
		
		return res;
	}
	//获取葫芦组合
	static ArrayList<ArrayList<paival>> gethululist(ArrayList<paival> all,paival  pre){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		int[] count = new int[16];
		//计算符合条件的牌
		for(int i=0;i<all.size();i++){
			count[all.get(i).val]+=1;
		}
		int size = all.size();
		//获得对牌
		ArrayList<ArrayList<paival>> twolist = new ArrayList<ArrayList<paival>>();
		for(int i=3;i<count.length;i++){
			if(count[i]==2){
				ArrayList<paival> tp = new ArrayList<paival>();
				for(int j=0;j<size;j++){
					if(i==all.get(j).val){
						tp.add(all.get(j));
					}
				}
				twolist.add(tp);
			}
		}
		//获得3张以上的牌
		ArrayList<ArrayList<paival>> threelist = new ArrayList<ArrayList<paival>>();
		for(int i=3;i<count.length;i++){
			if(count[i]>2){
				ArrayList<paival> tp = new ArrayList<paival>();
				for(int j=0;j<size;j++){
					if(i==all.get(j).val&&i>pre.val){
						tp.add(all.get(j));
					}
				}
				threelist.add(tp);
			}
		}
		
		if((twolist.size()==0&&threelist.size()<2)||threelist.size()==0){
			return res;
		}else
		{  
			int tnum = threelist.size();
			int twnum = twolist.size();
			if(twnum>0){
				for(int j=0;j<twnum;j++){
					ArrayList<paival> tp = new ArrayList<paival>();
					tp.addAll(twolist.get(j));
					for(int i=0;i<tnum;i++){
						tp.addAll(threelist.get(i));
					}
					res.add(tp);
				}
				if(tnum>1){
					for(int i=0;i<tnum;i++){
						ArrayList<ArrayList<paival>> threecom = new ArrayList<ArrayList<paival>>();
						ArrayList<ArrayList<paival>> twocom = new ArrayList<ArrayList<paival>>();
						threecom.addAll(combinlist(threelist.get(i),3));
						for(int j=0;j<tnum;j++){
							if(j!=i){
								twocom.addAll(combinlist(threelist.get(j),2));
							}
						}
						for(int k=0;k<threecom.size();k++){
							ArrayList<paival> t = new ArrayList<paival>();
							t.addAll(threecom.get(k));
							for(int n=0;n<twocom.size();n++){
								t.addAll(twocom.get(n));
							}
							res.add(t);
						}
					}
				}
			}else{
				if(tnum>1){
					for(int i=0;i<tnum;i++){
						ArrayList<ArrayList<paival>> threecom = new ArrayList<ArrayList<paival>>();
						ArrayList<ArrayList<paival>> twocom = new ArrayList<ArrayList<paival>>();
						threecom.addAll(combinlist(threelist.get(i),3));
						for(int j=0;j<tnum;j++){
							if(j!=i){
								twocom.addAll(combinlist(threelist.get(j),2));
							}
						}
						for(int k=0;k<threecom.size();k++){
							ArrayList<paival> t = new ArrayList<paival>();
							t.addAll(threecom.get(k));
							for(int n=0;n<twocom.size();n++){
								t.addAll(twocom.get(n));
							}
							res.add(t);
						}
					}
				}
			}
		}
		
		return res;
	}
	//获得组合3选2，4,2,4,3
	static ArrayList<ArrayList<paival>> combinlist(ArrayList<paival> all,int num){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		int sum = all.size();
		if(sum<num) return res;
		if(sum==num) {
			res.add(all);
			return res;
		}
		if(num==2){
			if(sum==3){
				for(int i=0;i<sum;i++){
					ArrayList<paival> a = new ArrayList<paival>();
					a.addAll(all);
					a.remove(i);
					res.add(a);
				}
			}
			if(sum==4){
				for(int i=0;i<sum;i++){
					ArrayList<paival> a = new ArrayList<paival>();
					a.addAll(all);
					a.remove(i);
					ArrayList<ArrayList<paival>> b = combinlist(a,2);
					res.addAll(b);
				}
			}
			
		}else if(num==3){
			for(int i=0;i<sum;i++){
				ArrayList<paival> a = new ArrayList<paival>();
				a.addAll(all);
				a.remove(i);
				res.add(a);
			}
		}
		
		
		return res;
	}
	//获取同花组合
	static ArrayList<ArrayList<paival>> gettonghualist(ArrayList<paival> all,ArrayList<paival>  pre){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		ArrayList<ArrayList<paival>> res2 = new ArrayList<ArrayList<paival>>();
		int[] huase = new int[4];
		for(int i=0;i<all.size();i++){
			huase[all.get(i).huase]+=1;
		}
		ArrayList<ArrayList<paival>> t = new ArrayList<ArrayList<paival>>();
		for(int i=0;i<huase.length;i++){
			if(huase[i]>=5){
				ArrayList<paival> tp = new ArrayList<paival>();
				for(int j=0;j<all.size();j++){
					if(all.get(j).huase==i){
						tp.add(all.get(j));
					}
				}
				t.add(tp);
			}
		}
		
		for(int i=0;i<t.size();i++){
			int tnum = t.get(i).size();
			if(tnum==5) {
				res2.add(t.get(i));
			}else{
				
			    for(int j=0;j<tnum-4;j++){
			    	ArrayList<paival> lf = new ArrayList<paival>();
			    	for(int z=j+5;z<tnum;z++){
			    		lf.add(t.get(i).get(z));
					}
			    	ArrayList<paival> tp = new ArrayList<paival>();
			    	tp.add(t.get(i).get(j));
			    	tp.add(t.get(i).get(j+1));
			    	tp.add(t.get(i).get(j+2));
			    	tp.add(t.get(i).get(j+3));
			    	tp.add(t.get(i).get(j+4));
			    	res2.add(tp);
			    	for(int s=0;s<lf.size();s++){
			    		for(int k=0;k<tp.size();k++){
			    			ArrayList<paival> tps = tp;
			    			tps.set(k, lf.get(s));
			    			res2.add(tps);
			    		}
			    	}
			    	
			    }
			}
			
		}
		
		for(int i=0;i<res2.size();i++){
			if(returnBig(res2.get(i), pre)){
				res.add(res2.get(i));
			}
		}
		
		return res;
	}
	
	//获取符合的顺牌
	static ArrayList<ArrayList<paival>> getshunpai(ArrayList<paival> all,ArrayList<paival>  pre){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
			
		int[] count = new int[16];
		//计算符合条件的牌
		for(int i=0;i<all.size();i++){
			count[all.get(i).val]+=1;
		}
		String c="count=";
		for(int i=3;i<16;i++){
			c += count[i]+",";
			
		}
		Log.v("log",c);
		int index=1;
		boolean[] isshun = new boolean[sunpai.length];
		
		for(int i=3;i<11;i++){
			if(count[i]>0 &&count[i+1]>0 && count[i+2]>0 &&count[i+3]>0 && count[i+4]>0){
				isshun[index]=true;
			}
			index++;
		}
		if(count[14]>0&&count[15]>0&&count[3]>0&&count[4]>0&&count[5]>0){
			isshun[0]=true;
		}
		
		if(count[6]>0&&count[15]>0&&count[3]>0&&count[4]>0&&count[5]>0){
			isshun[sunpai.length-1]=true;
		}
		
		for(int i=0;i<isshun.length;i++){
			if(isshun[i]){
				Log.v("log", "ishunid="+i);
			}
		}
		
		int pindex = 0;
		for(int i=0;i<sunpai.length;i++){	
			
			boolean[] ok= new boolean[5];			
			for(int j=0;j<sunpai[i].length;j++){				
				for(int z=0;z<pre.size();z++){
					if(pre.get(z).val==sunpai[i][j]){
						ok[j]=true;
					}
				}			
			}
			
			if(ok[0]&&ok[1]&&ok[2]&&ok[3]&&ok[4]){
				pindex=i;
			}
		}
		Log.v("log", "pindex="+pindex);
		for(int i=pindex+1;i<isshun.length;i++){
			 if(isshun[i]){
				Log.v("log", "isshun"+i);
				res.addAll(getshunlist(all,i));
			}			
		}
		
		
		return res;
	}
	//获得顺牌列表
	static ArrayList<ArrayList<paival>> getshunlist(ArrayList<paival> all,int index){
		
		ArrayList<ArrayList<paival>> tlist=getmoreshun(all,index);
	
		ArrayList<ArrayList<paival>> res = combination(tlist);
		
		
		return res;
	}
	//获取组合
	static ArrayList<ArrayList<paival>> combination(ArrayList<ArrayList<paival>> tlist){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		int size = tlist.size();
		if(size==0) return res;
		ArrayList<paival> a = tlist.get(0);
		ArrayList<paival> b = tlist.get(1);
		for(int i=0;i<a.size();i++){
			for(int j=0;j<b.size();j++){
				ArrayList<paival> c = new ArrayList<paival>();
				c.add(a.get(i));
				c.add(b.get(j));
				res.add(c);
			}
		}
		
		for(int z=2;z<tlist.size();z++){
			ArrayList<paival> c = tlist.get(z);
			ArrayList<ArrayList<paival>> res2 = new ArrayList<ArrayList<paival>>();
			
			for(int i=0;i<res.size();i++){
				for(int j=0;j<c.size();j++){
					ArrayList<paival> tp = new ArrayList<paival>();
					tp.addAll(res.get(i));
					tp.add(c.get(j));
					res2.add(tp);
				}
			}
			
			res=res2;
		}
		
		
		return res;
	}
	
	//获取顺牌组合
	static ArrayList<ArrayList<paival>> getmoreshun(ArrayList<paival> all,int index){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		int[] shun = sunpai[index];
		for(int i=0;i<5;i++){
			ArrayList<paival> tp = new ArrayList<paival>();
			for(int j=0;j<all.size();j++){
				if(shun[i]==all.get(j).val){
					tp.add(all.get(j));
				}
			}
			res.add(tp);
		}
		return res;
		
	}
	//获取符合的对牌
	static ArrayList<ArrayList<paival>> getduipai(ArrayList<paival> all,paival pre){
		ArrayList<ArrayList<paival>> res = new ArrayList<ArrayList<paival>>();
		int[] count = new int[16];
		//计算符合条件的牌
		for(int i=0;i<all.size();i++){
			if(pre.huase==3){
				if(all.get(i).val>pre.val){
					count[all.get(i).val]+=1;
				}
			}else{
				if(all.get(i).val>=pre.val){
					count[all.get(i).val]+=1;
				}
			}
		}
		
		ArrayList<ArrayList<paival>> reslist = new ArrayList<ArrayList<paival>>();
		for(int i=3;i<count.length;i++){
			if(count[i]>1){
				ArrayList<paival> temp = new ArrayList<paival>();
				for(int j=0;j<all.size();j++){
					if(i==all.get(j).val){
						temp.add(all.get(j));
					}
				}
				reslist.add(temp);
			}
		}
		if(reslist.size()==0){
			return res;
		}else{
			for(int i=0;i<reslist.size();i++){
				
				ArrayList<paival> temp = reslist.get(i);
				int num=temp.size();
				if(num==2){
					res.add(temp);
				}else if(num>2){
					for(int j=0;j<num-1;j++){
						paival tpai = temp.get(j);
						for(int z=j+1;z<num;z++){
							ArrayList<paival> temp2 = new ArrayList<paival>();
							temp2.add(tpai);
							temp2.add(temp.get(z));
							res.add(temp2);
						}
					}
				}
			}
		}
		return res;
	}
	//获得符合条件的顺牌
	
}
