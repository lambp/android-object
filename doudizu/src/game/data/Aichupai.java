package game.data;

import java.util.ArrayList;

import android.util.Log;

public class Aichupai {
	//抢地主
	public static final int QIANGDIZHU=100,JIAODIZHU=101,BUQIANG=102,BUJIAO=103,PASS=104,YAOBUQI=105,
			MEIYOUDAGUO=106,BUFUHEGZ=107,QINGXUANP=108,DIZHUYIN=109,NONGMINGY=110,YAOBUGUO=111,XUANPAI=112,
			FAPAI=119,LOST=120,WIN=121;
	//牌型
	public static final int PXDANG=1,PXDUI=2,
			PXSAN=3,PXSANO=4,PXSANT=5,
			PXZAD=6,PXSDO=7,PXSDT=8,
			PXSUN=9,PXLIAND=10,PXFEIJI=11,PXHUOJIAN=12;
	//AI跟牌
	public static ArrayList<paival> AIgenpai(ArrayList<paival> all,ArrayList<paival> last){
		ArrayList<paival> res = new ArrayList<paival>();
		int lpx = getpx(last);
		
		ArrayList<paival> sortpai = Sortpai(all);
		last = Sortpai(last);
		switch(lpx){
			case PXDANG:
				 res = AIgetOnepai(sortpai,last.get(0));
				break;
			case PXDUI:
				res = AIgetDuipai(sortpai,last.get(0));
				break;
			case PXSAN:
				res = AIgetSANpai(sortpai,last.get(0));
				break;
			case PXSANO:
					res = AIgetSANOpai(sortpai,last.get(2));
				break;
			case PXSANT:
					res = AIgetSANTpai(sortpai,last.get(2));
				break;
			case PXZAD:
					res=getZadan(sortpai,last.get(0));
					if(res.size()==0){
						if(getpx(getnohj(sortpai))>0){
							res = getHuoJian(all);
						}
					}
				break;
			case PXSDO:
				res=getZadan(sortpai,last.get(0));
				if(res.size()==0){
					if(getpx(getnohj(sortpai))>0){
						res = getHuoJian(sortpai);
					}
				}
				break;
			case PXSDT:
				res=getZadan(sortpai,last.get(0));
				if(res.size()==0){
					if(getpx(getnohj(sortpai))>0){
						res = getHuoJian(sortpai);
					}
				}
				break;
			case PXSUN:
				
				res=getBigshun(sortpai,last.get(0),last.get(last.size()-1));
				if(res.size()==0){
					res=getZadan(sortpai,last.get(0));
					if(res.size()==0){
						if(getpx(getnohj(sortpai))>0){
							res = getHuoJian(sortpai);
						}
					}
				}
				
				break;
			case PXLIAND:
				res=getBigLiandui(sortpai,last.get(0),last.get(last.size()-1));
				if(res.size()==0){
					res=getZadan(sortpai,last.get(0));
					if(res.size()==0){
						if(getpx(getnohj(sortpai))>0){
							res = getHuoJian(sortpai);
						}
					}
				}
				break;
			case PXFEIJI:
				
				res=getZadan(sortpai,last.get(0));
				if(res.size()==0){
					if(getpx(getnohj(sortpai))>0){
						res = getHuoJian(sortpai);
					}
				}
				break;
			case PXHUOJIAN:
			
				break;
		}
		return Sortpai(res);
	}
	//获取更大连对
	public static ArrayList<paival> getBigLiandui(ArrayList<paival> all,paival min,paival big){
		ArrayList<paival> res = new ArrayList<paival>();
		//ArrayList<paival> temp = get(all);
		int num=(big.val-min.val+1)*2;
		
		return res;
	}
	
	//获取跟大的顺
	public static ArrayList<paival> getBigshun(ArrayList<paival> all,paival min,paival big){
		ArrayList<paival> temp = getSunpai(all);
		ArrayList<paival> res = new ArrayList<paival>();
		int num=(big.val-min.val+1);
		if(temp.size()>=num){
			if(temp.size()==num&&temp.get(0).val>min.val){
				res = temp;
			}else{
				boolean flag=false;
				int val=0;
				for(int i=0;i<temp.size();i++){
					if(!flag&&temp.get(i).val>min.val){
						val=i;
						flag=true;
					}
				}
				if(flag){
					if(temp.size()-val>num){
						for(int j=val;j<val+num;j++){
							res.add(temp.get(j));
						}
					}
				}
			}
		}
		return res;
	}
	//获取能压过的3-1
			public static ArrayList<paival> AIgetSANTpai(ArrayList<paival> all,paival big){
				ArrayList<paival> res = new ArrayList<paival>();
				int sum = all.size();
				if(sum>=2){
					int[] count = getvalnum(all);
					int val=0;
					for(int i=3;i<count.length;i++){
						if(count[i]==3&&val==0&&i>big.val){
							val = i;
						}
					}
					ArrayList<paival> zd=getZadan(all,new paival(2,2));
					ArrayList<paival> hj=getHuoJian(all);
					if(val>0){
						if(sum>4){
							for(int i=0;i<all.size();i++){
								if(all.get(i).val==val){
									res.add(all.get(i));
								}
							}
							ArrayList<paival> one=getminDuipai(all);
							if(one.size()>0){
								res.addAll(one);
							}else{
								if(zd.size()>0&&sum<9){
									res = zd;
								}else{
									if(hj.size()>0&&sum<6){
										res = hj;
									}else{
										res = new ArrayList<paival>();
									}
								}
							}
						}
					}else{
					
						if(hj.size()>0){
							if(zd.size()>0){
								res = zd;
							}else{
								if(getpx(getnohj(all))>0||sum<5){
									res = hj;
								}
							}
						}else{
							if(getpx(getnozd(all))>0||(zd.size()>0&&sum<7)){
								res = zd;
							}
						}
					}
				}
				return res;
			}
	//获取能压过的对牌
		public static ArrayList<paival> AIgetSANpai(ArrayList<paival> all,paival big){
			ArrayList<paival> res = new ArrayList<paival>();
			int sum = all.size();
			if(sum>=2){
				int[] count = getvalnum(all);
				int val=0;
				for(int i=3;i<count.length;i++){
					if(count[i]==3&&val==0&&i>big.val){
						val = i;
					}
				}
				if(val>0){
					for(int i=0;i<all.size();i++){
						if(all.get(i).val==val){
							res.add(all.get(i));
						}
					}
				}else{
					ArrayList<paival> zd=getZadan(all,new paival(2,2));
					ArrayList<paival> hj=getHuoJian(all);
					if(hj.size()>0){
						if(zd.size()>0){
							res = zd;
						}else{
							if(getpx(getnohj(all))>0||sum<5){
								res = hj;
							}
						}
					}else{
						if(getpx(getnozd(all))>0||(zd.size()>0&&sum<7)){
							res = zd;
						}
					}
				}
			}
			return res;
		}
		//获取能压过的3-1
		public static ArrayList<paival> AIgetSANOpai(ArrayList<paival> all,paival big){
			ArrayList<paival> res = new ArrayList<paival>();
			int sum = all.size();
			if(sum>=2){
				int[] count = getvalnum(all);
				int val=0;
				for(int i=3;i<count.length;i++){
					if(count[i]==3&&val==0&&i>big.val){
						val = i;
					}
				}
				ArrayList<paival> zd=getZadan(all,new paival(2,2));
				ArrayList<paival> hj=getHuoJian(all);
				if(val>0){
					if(sum>3){
						for(int i=0;i<all.size();i++){
							if(all.get(i).val==val){
								res.add(all.get(i));
							}
						}
						ArrayList<paival> one=getMinbig(getnohj(all),new paival(3,2));
						if(one.size()>0){
							res.addAll(one);
						}else{
							if(zd.size()>0){
								res = zd;
							}else{
								if(hj.size()>0&&sum<6){
									res = hj;
								}else{
									res = new ArrayList<paival>();
								}
							
							}
						}
					}
				}else{
				
					if(hj.size()>0){
						if(zd.size()>0){
							res = zd;
						}else{
							if(getpx(getnohj(all))>0||sum<5){
								res = hj;
							}
						}
					}else{
						if(getpx(getnozd(all))>0||(zd.size()>0&&sum<7)){
							res = zd;
						}
					}
				}
			}
			return res;
		}

	//获取能压过的对牌
	public static ArrayList<paival> AIgetDuipai(ArrayList<paival> all,paival big){
		ArrayList<paival> res = new ArrayList<paival>();
		int sum = all.size();
		if(sum>=2){
			int[] count = getvalnum(all);
			int val=0;
			for(int i=3;i<count.length;i++){
				if(count[i]==2&&val==0&&i>big.val){
					val = i;
				}
			}
			if(val>0){
				for(int i=0;i<all.size();i++){
					if(all.get(i).val==val){
						res.add(all.get(i));
					}
				}
			}else{
				ArrayList<paival> zd=getZadan(all,new paival(2,2));
				ArrayList<paival> hj=getHuoJian(all);
				if(hj.size()>0){
					if(zd.size()>0){
						res = zd;
					}else{
						if(getpx(getnohj(all))>0||sum<5){
							res = hj;
						}
					}
				}else{
					if(getpx(getnozd(all))>0||(zd.size()>0&&sum<7)){
						res = zd;
					}
				}
			}
		}
		return res;
	}
	public static ArrayList<paival> getZadan(ArrayList<paival> all,paival big){
		ArrayList<paival> res = new ArrayList<paival>();
		int[] count = getvalnum(all);
		int val=0;
		for(int i=3;i<count.length;i++){
			if(count[i]==4&&val==0&&i>big.val){
				val = i;
			}
		}
		if(val>0){
			for(int i=0;i<all.size();i++){
				if(all.get(i).val==val){
					res.add(all.get(i));
				}
			}
		}
		return res;
	}
	//获取能压的过的单牌
	public static ArrayList<paival> AIgetOnepai(ArrayList<paival> all,paival big){
		ArrayList<paival> res = new ArrayList<paival>();
		int sum = all.size();
		
		switch(sum){
		case 1:
			if(big.val<all.get(0).val) res = all;
			break;
		case 2:
			if(all.get(0).val==53&&all.get(1).val==52) {
				res = all;
			}else if(all.get(0).val>big.val){
				res.add(all.get(0));
			}else if(all.get(1).val>big.val&&all.get(0).val<=big.val){
				res.add(all.get(1));
			}
			break;
		case 3:
			res = getHuoJian(all);
			if(res.size()==0){
				res = getMinbig(all,big);	
			}
			break;
		case 4:
			if(getpx(all)==PXZAD){
				res = all;
			}
			if(res.size()==0){
				ArrayList<paival> temp = getHuoJian(all);
				if(temp.size()>0){
					ArrayList<paival> temp2 = new ArrayList<paival>();
					temp2.add(all.get(0));
					temp2.add(all.get(1));
					res = getMinbig(temp2,big);
					if(res.size()==0){
						res.add(temp.get(0));
					}
				}else{
					res = getMinbig(all,big);	
				}
			}
			break;
		case 5:
			if(getpx(all)!=PXSUN){
				ArrayList<paival> temp = getHuoJian(all);
				ArrayList<paival> temp2 = new ArrayList<paival>();
				ArrayList<paival> temp3 = new ArrayList<paival>();
				if(temp.size()>0){
					temp2.add(all.get(0));
					temp2.add(all.get(1));
					temp2.add(all.get(2));
					res = getMinbig(temp2,big);
					if(res.size()==0){
						res.add(temp.get(0));
					}
				}else{
					temp2.add(all.get(0));
					temp2.add(all.get(1));
					temp2.add(all.get(2));
					temp2.add(all.get(3));
					temp3.add(all.get(0));
					temp3.add(all.get(1));
					temp3.add(all.get(2));
					temp3.add(all.get(3));
					if(getpx(temp2)==PXZAD){
						if(all.get(4).val>big.val){
							res.add(all.get(4));
						}else{
							res.addAll(temp2);
						}
					}else if(getpx(temp2)==PXZAD){
						if(all.get(0).val>big.val){
							res.add(all.get(0));
						}else{
							res.addAll(temp2);
						}
					}else{
						res = getMinbig(all,big);
					}
				}
			}
			break;
		case 6:
			res = getzhadan(all);
			if(res.size()>0){
				ArrayList<paival> huoj = getHuoJian(getnozd(all));
				if(huoj.size()>0){
					res = huoj;	
				}else{
					if(getpx(getnozd(all))!=PXDUI){
						res = getMinbig(getnozd(all),big);
					}
				}
			}else{
				int px = getpx(all);
				if(px!=PXLIAND&&px!=PXFEIJI){
					res = getMinbig(all,big);
				}
			}
			break;
		case 7:
			res = getzhadan(all);
			if(res.size()>0){
				if(getpx(getnozd(all))!=PXSAN){
					if(getHuoJian(getnozd(all)).size()==0){
						res = getMinbig(getnozd(all),big);
					}
				}
			}else{
				ArrayList<paival> left = getHuoJian(all);
				if(left.size()>0){
					res = getMinbig(getnohj(all),big);
				}else{
					res = getMinbig(all,big);
				}
			}
			break;
		case 8:
			res = getzhadan(all);
			if(res.size()>0){
				if(getpx(getnozd(all))!=PXSANO){
					if(getHuoJian(getnozd(all)).size()==0){
						if(getzhadan(getnozd(all)).size()==0){
							res = getMinbig(getnozd(all),big);
						}
					}
				}
			}else{
				if(getHuoJian(all).size()==0){
					res = getMinbig(all,big);
				}else{
					if(getpx(getnohj(all))>0){
						res = getHuoJian(all);
					}else{
						res = getMinbig(getnohj(all),big);
					}
				}
			}
			break;
		case 9:
			res = getzhadan(all);
			if(res.size()>0){
				int px = getpx(getnozd(all));
				if(px==0){
					if(getHuoJian(getnozd(all)).size()==0){
						if(getzhadan(getnozd(all)).size()==0){
							res = getMinbig(getnozd(all),big);
						}
					}
				}
			}else{
				if(getHuoJian(all).size()==0){
					res = getMinbig(all,big);
				}else{
					if(getpx(getnohj(all))>0){
						res = getHuoJian(all);
					}else{
						res = getMinbig(getnohj(all),big);
					}
				}
			}
			break;
		default:
			res = getzhadan(all);
			if(res.size()>0){
				int px = getpx(getnozd(all));
				if(px==0){
					if(getHuoJian(getnozd(all)).size()==0){
						if(getzhadan(getnozd(all)).size()==0){
							res = getMinbig(getnozd(all),big);
						}
					}
				}
			}else{
				if(getHuoJian(all).size()==0){
					res = getMinbig(all,big);
				}else{
					if(getpx(getnohj(all))>0){
						res = getHuoJian(all);
					}else{
						res = getMinbig(getnohj(all),big);
					}
				}
			}
			break;
			
		}
		
		return res;
	}
	//获取非火箭的牌
	public static ArrayList<paival> getnohj(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		
			for(int i=0;i<all.size();i++){
				if(all.get(i).val!=53||all.get(i).val!=52){
					res.add(all.get(i));
				}
			}
		
		return res;
	}
	//获取不是炸弹以为的牌
	public static ArrayList<paival> getnozd(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int[] count = getvalnum(all);
		int val=0;
		for(int i=3;i<count.length;i++){
			if(count[i]==4&&val==0){
				val = i;
			}
		}
		if(val>0){
			for(int i=0;i<all.size();i++){
				if(all.get(i).val!=val){
					res.add(all.get(i));
				}
			}
		}else{
			res = all;
		}
		return res;
	}
	//获取炸弹
	public static ArrayList<paival> getzhadan(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int[] count = getvalnum(all);
		int val=0;
		for(int i=3;i<count.length;i++){
			if(count[i]==4&&val==0){
				val = i;
			}
		}
		if(val>0){
			for(int i=0;i<all.size();i++){
				if(all.get(i).val==val){
					res.add(all.get(i));
				}
			}
		}
		return res;
	}
	//获取大得过的最小单牌
	public static ArrayList<paival> getMinbig(ArrayList<paival> all,paival big){
		ArrayList<paival> res = new ArrayList<paival>();
		int[] count = getvalnum(all);
		int min_1=0,min_2=0,min_3=0;
		for(int i=3;i<count.length;i++){

			if(count[i]==2&&i>big.val&&min_2==0){
				min_2=i;
			}
			if(count[i]==1&&i>big.val&&min_1==0){
				min_1=i;
			}
		}
		if(min_1>0){
			for(int i=0;i<all.size();i++){
				if(min_1==all.get(i).val){
					res.add(all.get(i));
				}
			}
		}else{
			if(min_2>0){
				boolean flag=false;
				for(int i=0;i<all.size();i++){
					if(min_2==all.get(i).val&&!flag){
						res.add(all.get(i));
						flag = true;
					}
				}
			}
		}
		return res;
	}
	//获取火箭
	public static ArrayList<paival> getHuoJian(ArrayList<paival> all){
			ArrayList<paival> res = new ArrayList<paival>();
			boolean xiao=false,da=false;
			for(int i=0;i<all.size();i++){
				if(all.get(i).val==52){
					xiao=true;
				}
				if(all.get(i).val==53){
					da=true;
				}
			}
			if(xiao&&da){
				for(int i=0;i<all.size();i++){
					if(all.get(i).val==53||all.get(i).val==52){
						res.add(all.get(i));
					}
				}
			}
		return Sortpai(res);
	}
	//获取牌型
	public static int getpx(ArrayList<paival> all){
		int px=0;
		int count = all.size();
		if(count<6){
			switch(count){
			case 1:
					px = PXDANG;
				break;
			case 2:
				if((((paival)all.get(0)).val>18&&((paival)all.get(1)).val>18)){
					px = PXHUOJIAN;
				}
				if(((paival)all.get(0)).val==((paival)all.get(1)).val){
					px = PXDUI;
				}
				break;
			case 3:
				if((((paival)all.get(0)).val==((paival)all.get(2)).val)&&(((paival)all.get(0)).val==((paival)all.get(1)).val)){
					px = PXSAN;
				}
				break;
			case 4:
				px = checkfour(all);
				break;
			case 5:
				px =  CheckFine(all);
				break;
			}
		}else{
			px=checkmoref(all);
		
		}
		return px;
	}
	//检测>5张牌的排型
	public static int checkmoref(ArrayList<paival> all){
		int res = 0;
		ArrayList<paival>  temp = Sortpai(all);
		int count = temp.size();
		if(count>5){
			if(CheckFeiji(temp)){
				res = PXFEIJI;
			}else if(CheckSun(temp)){
				res = PXSUN;
			}
			else if(CheckLiandui(temp)){
				res = PXLIAND;
			}
		}
		return res;
	}
	//检测连对
	public static boolean CheckLiandui(ArrayList<paival> all){
		boolean res = false;
		if(all.size()%2==1||all.size()<6){return res;}
		ArrayList<paival>  temp1 = getsub(0,all);
		ArrayList<paival>  temp2 = getsub(1,all);
		
		if(lianCheckSun(temp1)&&lianCheckSun(temp2)&&(temp1.get(0)).val==(temp2.get(0)).val){
			res = true;
		}
		return res;
	}
	//检查是否为飞机
	public static boolean CheckFeiji(ArrayList<paival> all){
		boolean res = false;
		int sum = all.size();
		int[] count = getvalnum(all);
		int num=0,twonum=0;
		int[] tval = new int[5]; 
		for(int i=0;i<60;i++){
			if(count[i]>2){
				tval[num]=i;
				num++;
			}
			if(count[i]==2){
				twonum++;
			}
		}
		
		if(num>1){
			if(num==2&&((tval[1]-tval[0])==1)){
				if(sum==6||sum==8){
					res = true;
				}else if(sum==10&&twonum==2){
					res = true;
				}
			}else if(num==3&&((tval[1]-tval[0])==1&&(tval[2]-tval[1])==1)){
				if(sum==9&&sum==12){
					res = true;
				}else if(sum==15&&twonum==3){
					res = true;
				}
			}else if(num==4&&((tval[3]-tval[2])==1&&(tval[1]-tval[0])==1&&(tval[2]-tval[1])==1)){
				if(sum==16&&sum==12){
					res = true;
				}
			}
			
		}
		return res;
	}
	//获取子列
	public static ArrayList<paival> getsub(int type,ArrayList<paival> all){
		ArrayList<paival> temp = new ArrayList<paival>();
		for(int i=0;i<all.size();i++){
			if(i%2==type){
				temp.add(all.get(i));
			}
		}
		return temp;
	}
	//检测四张牌排型
	public static int checkfour(ArrayList<paival> all){
		int res = 0;
		ArrayList<paival>  temp = new ArrayList<paival>();
		if((((paival)all.get(0)).val==((paival)all.get(3)).val)&&(((paival)all.get(0)).val==((paival)all.get(2)).val)&&(((paival)all.get(0)).val==((paival)all.get(1)).val)){
			res = PXZAD;
		}else{
			temp =Sortpai(all);
			if((((paival)temp.get(0)).val==((paival)temp.get(1)).val)&&(((paival)all.get(0)).val==((paival)all.get(2)).val)){
				res = PXSANO;
			}else if((((paival)temp.get(2)).val==((paival)temp.get(1)).val)&&(((paival)all.get(1)).val==((paival)all.get(3)).val)){
				res = PXSANO;
			}
		}
		
		return res;
	}
	//检查5张牌型
	public static int CheckFine(ArrayList<paival> all){
		ArrayList<paival>  temp = Sortpai(all);
		int res = 0;
		if((((paival)temp.get(0)).val==((paival)temp.get(1)).val)&&(((paival)temp.get(0)).val==((paival)temp.get(2)).val)&&(((paival)all.get(3)).val==((paival)all.get(0)).val)){
			res = PXSDT;
		}else if((((paival)temp.get(1)).val==((paival)temp.get(2)).val)&&(((paival)temp.get(1)).val==((paival)temp.get(3)).val)&&(((paival)all.get(4)).val==((paival)all.get(1)).val)){
			res = PXSDT;
		}else if(CheckSun(temp)){
			res = PXSUN;
		}else if((((paival)temp.get(0)).val==((paival)temp.get(1)).val)&&(((paival)temp.get(0)).val==((paival)temp.get(2)).val)&&(((paival)all.get(3)).val==((paival)all.get(4)).val)){
			res = PXSANT;
		}else if((((paival)temp.get(0)).val==((paival)temp.get(1)).val)&&(((paival)temp.get(2)).val==((paival)temp.get(3)).val)&&(((paival)all.get(2)).val==((paival)all.get(4)).val)){
			res = PXSANT;
		}
		return res;
	}
	//检查是否为顺
	public static boolean CheckSun(ArrayList<paival> all){
			ArrayList<paival>  temp = Sortpai(all);
			if(temp.size()<5) return false;
			boolean res = true;
			for(int i=0;i<all.size();i++){
				if((((paival)temp.get(i)).val-i)!=((paival)temp.get(0)).val){
					res =false;
				}
			}
		return res;
	}
	//检查是否为顺
	public static boolean lianCheckSun(ArrayList<paival> all){
			ArrayList<paival>  temp = Sortpai(all);
			if(temp.size()<3) return false;
			boolean res = true;
			for(int i=0;i<all.size();i++){
				if((((paival)temp.get(i)).val-i)!=((paival)temp.get(0)).val){
					res =false;
				}
			}
		return res;
	}
	//排序
	public static ArrayList<paival> Sortpai(ArrayList<paival> pais){
		if(pais.size()<2) return pais;
		boolean flag=true;
		for(int i=1;i<pais.size() && flag;i++){
			flag = false;
			for(int j=0;j<pais.size()-i;j++){
				if(((pais.get(j)).val > (pais.get(j+1)).val)){
					paival temp = pais.get(j);
					pais.set(j, pais.get(j+1));
					pais.set(j+1,temp);
					flag = true;
				}
			}
		}
		return pais;
	}
	//AI主动出牌
	public static ArrayList<paival> AIautocp(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		all = Sortpai(all);
		int sum = all.size();
		int px = getpx(all);
		if(sum==0) return res;
		switch(sum){
			case 1:
				res = all;
				break;
			case 2:
				if(px>0){
					res = all;
				}else{
					res.add(all.get(0));
				}
				break;
			case 3:
				if(px>0){
					res = all;
				}else{
					if((all.get(0)).val==(all.get(1)).val){
						res.add(all.get(0));
						res.add(all.get(1));
					}else if((all.get(1)).val==(all.get(2)).val){
						res.add(all.get(1));
						res.add(all.get(2));
					}else{
						res.add(all.get(0));
					}
				}
				break;
			case 4:
				if(px>0){
					res = all;
				}else{
					if((all.get(0)).val==(all.get(1)).val){
						res.add(all.get(0));
						res.add(all.get(1));
					}else if((all.get(1)).val==(all.get(2)).val){
						res.add(all.get(1));
						res.add(all.get(2));
					}else if((all.get(2)).val==(all.get(3)).val){
						res.add(all.get(2));
						res.add(all.get(3));
					}else{
						res.add(all.get(0));
					}
				}
				break;
			case 5:
				if(px>0){
					res = all;
				}else{
					res = AutogetZadan(all);
					if(res.size()==0){
						res = checkHasThree(all);
						if(res.size()==0){
							res = getminDuipai(all);
							if(res.size()==0){
								res.add(all.get(0));
							}
						}
					}
				}
				break;
			case 6:
				if(px>0){
					res = all;
				}else{
					res = AutogetZadan(all);
					if(res.size()==0){
						res = checkHasThree(all);
						if(res.size()==0){
							res = getminDuipai(all);
							if(res.size()==0){
								res.add(all.get(0));
							}
						}
					}
				}
				break;
			default:
				if(px>0){
					res = all;
				}else{
					res = AutogetZadan(all);
					Log.v("log","21");
					if(res.size()==0){
						Log.v("log","2");
						res=AutogetFeiji(all);
						if(res.size()==0){
							Log.v("log","3");
							res = getSunpai(all);
							if(res.size()==0){
								Log.v("log","4");
								res = getminDan(all);
								if(res.size()==0){
									Log.v("log","5");
									res = checkHasThree(all);
									if(res.size()==0){
										Log.v("log","6");
										res = getminDuipai(all);
										if(res.size()==0){
											Log.v("log","7");
											res.add(all.get(0));
										}
									}	
								}
							}
						}
						
					}
				}
				
				break;
		}
		return res;
	}
	public static  ArrayList<paival> getSunpai(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int [] count = getvalnum(all);
		int[] danval = new int[100];
		int num=0;
		for(int i=3;i<count.length;i++){
			if(count[i]==1&&i<15){
				danval[num]=i;
				num++;
			}
		}
		if(num>=5){
			boolean flag=false;
			int id = 0;
			for(int i=num-1;i>=4;i--){
				if((danval[i]-danval[0])==i&&flag){
					flag = true;
					id=i;
				}
			}
			if(flag){
				for(int i=0;i<=id;i++){
					for(int j=0;j<all.size();j++){
						if(danval[i]==all.get(j).val){
							res.add(all.get(j));
						}
					}
				}
			}
		}
		return Sortpai(res);
	}
	//获取最小的单牌
	public static ArrayList<paival> getminDan(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int [] count = getvalnum(all);
		int sanval=0;
		boolean flag=false;
		for(int i=3;i<count.length;i++){
			if(count[i]==1&&i<11&&!flag){
				sanval = i;
				flag=true;
			}
		}
		if(flag){
			for(int i=0;i<all.size();i++){
				if(sanval==all.get(i).val){
					res.add(all.get(i));
				}
			}
		}
		return res;
	}
	//检查是否有炸弹
	public static ArrayList<paival> AutogetZadan(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int [] count = getvalnum(all);
		int thval=0,thnum=0;
		int twoval = 0,sanval=0;
		for(int i=3;i<count.length;i++){
			if(count[i]==4){
				thval = i;
				thnum++;
			}
			if(count[i]==2){
				twoval = i;
			}
			if(count[i]==3){
				sanval = i;
			}
		}
		if(thval>0){
			if(all.size()==5){
				if(thval==all.get(0).val){
					res.add(all.get(4));
				}else{
					res.add(all.get(0));
				}
			}else if(all.size()==6){
				if(twoval>0){
					res =all;
				}else{
					if(twoval==all.get(0).val){
						if(all.get(4).val==53&&all.get(5).val==52){
							for(int i=0;i<all.size();i++){
								if((all.get(i)).val==twoval){
									res.add(all.get(i));
								}
							}
						}else{
							res.add(all.get(4));
						}
					}else{
						res.add(all.get(0));
					}
				}
			}else if(all.size()==7){
				if(sanval>0){
					res = all;
				}else{
					if(twoval>0){
						for(int i=0;i<all.size();i++){
							if((all.get(i)).val==twoval){
								res.add(all.get(i));
							}
						}
					}else{
						for(int i=0;i<all.size();i++){
							if((all.get(i)).val==thval){
								res.add(all.get(i));
							}
						}
						if((all.get(0)).val==thval){
							res.add(all.get(4));
							res.add(all.get(5));
						}else if((all.get(0)).val!=thval&&(all.get(1)).val==thval){
							res.add(all.get(0));
							res.add(all.get(5));
						}else if((all.get(1)).val!=thval){
							res.add(all.get(0));
							res.add(all.get(1));
						}
					}
				}
			}else if(all.size()==8){
				if(sanval>0){
					for(int i=0;i<all.size();i++){
						if((all.get(i)).val!=thval){
							res.add(all.get(i));
						}
					}
				}else{
					if(twoval>0){
						for(int i=0;i<all.size();i++){
							if((all.get(i)).val!=twoval){
								res.add(all.get(i));
							}
						}
					}else{
						if(thnum>1){
							for(int i=0;i<all.size();i++){
								if((all.get(i)).val!=thval){
									res.add(all.get(i));
								}
							}
						}else{
							if((all.get(0)).val==thval){
								res.add(all.get(4));
							}else{
								res.add(all.get(5));
							}
						}
					}
				}
			}else if(all.size()>=9){
				ArrayList<paival> temp=new ArrayList<paival>();
				for(int i=0;i<all.size();i++){
					if((all.get(i)).val!=thval){
						temp.add(all.get(i));
					}
				}
				if(getpx(temp)>0){
				  res=temp;
				}else{
					if(thnum>1){
						res = AutogetZadan(temp);
					}else{
						ArrayList<paival> temp2=checkHasThree(temp);
						if(temp2.size()>0){
							res = temp2;
						}else{
							temp2=getminDuipai(temp);
							if(temp2.size()>0){
								res = temp2;
							}else{
								res.add(temp.get(0));
							}
						}
					}
				}
			}
		}
		return Sortpai(res);
	}
	//检查是否有飞机返回
	public static ArrayList<paival> AutogetFeiji(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		if(CheckFeiji(all)) return all;
		int [] count = getvalnum(all);
		int thnum=0;
		int[] vals = new int[5];
		int twonum=0,onenum=0;
		int[] tvals = new int[10];
		int[] ovals = new int[15];
		for(int i=3;i<count.length;i++){
			if(count[i]>2){
				vals[thnum]=i;
				thnum++;
			}
			if(count[i]==2){
				tvals[twonum]=i;
				twonum++;
			}
			if(count[i]==1){
				ovals[onenum]=i;
			    onenum++;
			}
		}
		if(thnum>1){
			if(thnum==2&&vals[1]-vals[0]==1){
				int num_1=0,num_2=0;
				ArrayList<paival> three = new ArrayList<paival>();
				for(int i=0;i<all.size();i++){
					if(all.get(i).val==vals[0]&&num_1<3){
						three.add(all.get(i));
						num_1++;
					}
					if(all.get(i).val==vals[1]&&num_2<3){
						three.add(all.get(i));
						num_2++;
					}
				}
				if(twonum>=2){
					ArrayList<paival> two = new ArrayList<paival>();
					for(int j=0;j<2;j++){
						num_1=0;
						for(int i=0;i<all.size();i++){
							if(all.get(i).val==tvals[j]&&num_1<2){
								two.add(all.get(i));
								num_1++;
							}
						}
					}
					res.addAll(three);
					res.addAll(two);
				}else{
					if(onenum>1){
						ArrayList<paival> one = new ArrayList<paival>();
						for(int j=0;j<2;j++){
							num_1=0;
							for(int i=0;i<all.size();i++){
								if(all.get(i).val==ovals[j]&&num_1<1){
									one.add(all.get(i));
									num_1++;
								}
							}
						}
						res.addAll(three);
						res.addAll(one);
					}
				}
				
			}
			
		}
		return Sortpai(res);
	}
	//返回最小的对牌
	public static ArrayList<paival> getminDuipai(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int [] count = getvalnum(all);
		int thval=0;
		boolean flag = false;
		for(int i=3;i<count.length;i++){
			if(count[i]==2&&!flag){
				thval = i;
				flag=true;
			}
		}
		if(flag){
			for(int i=0;i<all.size();i++){
				if((all.get(i)).val==thval){
					res.add(all.get(i));
				}
			}
		}
		return res;
	}
	//返回3带一，或3带2
	public static ArrayList<paival> checkHasThree(ArrayList<paival> all){
		ArrayList<paival> res = new ArrayList<paival>();
		int [] count = getvalnum(all);
		int threenum=0,twonum=0;
		int thval=0,twoval=0;
		for(int i=3;i<count.length;i++){
			if(count[i]==3&&thval==0){
				threenum++;
				thval = i;
			}
			if(count[i]==2&&i<11&&twoval==0){
				twonum++;
				twoval = i;
			}
		}
		if(threenum>0){
			if(twonum>0){
				for(int i=0;i<all.size();i++){
					if((all.get(i)).val==thval){
						res.add(all.get(i));
					}
				}
				for(int i=0;i<all.size();i++){
					if((all.get(i)).val==twoval){
						res.add(all.get(i));
					}
				}
			}else{
				for(int i=0;i<all.size();i++){
					if((all.get(i)).val==thval){
						res.add(all.get(i));
					}
				}
				if(thval==(all.get(0)).val){
					res.add(all.get(3));
				}else if(thval>(all.get(0)).val){
					res.add(all.get(0));
				}
			}
		}
		return Sortpai(res);
	}
	//玩家压牌判断
	public static boolean personGp(ArrayList<paival> mypai,ArrayList<paival> lastpai){
		boolean res = false;
		int mpx = getpx(mypai);
		int lpx = getpx(lastpai);
		if(lpx==PXHUOJIAN) return false;
		if(lpx==PXZAD){
			if(mpx==PXHUOJIAN) return true;
			if(mpx!=PXZAD) return false;
			if(mpx==PXZAD&&(mypai.get(0).val>lastpai.get(0).val)){
				return true;
			}else{
				return false;
			}
		}else{
			if(mpx==PXZAD||mpx==PXHUOJIAN) return true;
			if(mpx!=lpx) return false;
			if(mpx==lpx){
				int mnum = mypai.size();
				int lnum = lastpai.size();
				if(mnum==lnum){
					if(mpx==PXDANG||mpx==PXDUI||mpx==PXSAN||mpx==PXSUN||mpx==PXLIAND){
						if(mypai.get(0).val>lastpai.get(0).val){
							return true;
						}else{
							return false;
						}
					}else if(mpx==PXSANO||mpx==PXSANT||mpx==PXSDT){
						if(mypai.get(2).val>lastpai.get(2).val){
							return true;
						}else{
							return false;
						}
					}else if(mpx==PXFEIJI){
						if(getbigFeiji(mypai)>getbigFeiji(lastpai)) return true;
					}
				}else{
					return false;
				}
			}
		}
		
		return res;
	}
	//获取飞机中最大的
	public static int getbigFeiji(ArrayList<paival> all){
		int res = 0;
		int[] count = getvalnum(all);
		for(int i=0;i<60;i++){
			if(count[i]>2){
				res = i;
			}
		}
		return res;
	}
	//获取各个牌的张数
	public static int[] getvalnum(ArrayList<paival> all){
		int[] count = new int[60];
		for(int i=3;i<60;i++){
			for(int j=0;j<all.size();j++){
				if(i==(all.get(j)).val){
					count[i]++;
				}
			}
		}
		return count;
	}
}
