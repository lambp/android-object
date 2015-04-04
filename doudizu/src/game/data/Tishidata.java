package game.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Tishidata {
	public static Map<String,ArrayList<paival>> gettishilist(ArrayList<paival> all,ArrayList<paival> last){
		Map<String,ArrayList<paival>> res= new HashMap<String,ArrayList<paival>>();
		all = Aichupai.Sortpai(all);
		last = Aichupai.Sortpai(last);
		int px=Aichupai.getpx(last);
		Log.v("log","ti="+px);
		switch(px){
		case Aichupai.PXDANG:
			res = hebinlist(all,getDzlist(all,last.get(0)));
			break;
		case Aichupai.PXDUI:
			res = hebinlist(all,getDuilist(all,last.get(0)));
			break;
		case Aichupai.PXSAN:
			res = hebinlist(all,getSanlist(all,last.get(0)));
			break;
		case Aichupai.PXSANO:
			res = hebinlist(all,getSanonelist(all,last.get(2)));
			break;
		case Aichupai.PXSANT:
			res = hebinlist(all,getSanTwolist(all,last.get(2)));
			break;
		case Aichupai.PXZAD:
			res = getbigZd(all,last.get(1));
			break;
		case Aichupai.PXSDO:
			Log.v("log","4+1");
			break;
		case Aichupai.PXSDT:
			res = hebinlist(all,getSITwolist(all,last.get(2)));
			break;
		case Aichupai.PXSUN:
			res = hebinlist(all,getBigshun(all,last.get(0),last.get(last.size()-1)));
			break;
		case Aichupai.PXLIAND:
			res = hebinlist(all,getBigLiandui(all,last.get(0),last.get(last.size()-1)));
			break;
		case Aichupai.PXFEIJI:
			res = hebinlist(all,getBigFeiji(all,last));
			break;
		case Aichupai.PXHUOJIAN:
		
			break;
		}
		return (res);
	}
	static HashMap<String,ArrayList<paival>> getBigFeiji(ArrayList<paival> all,ArrayList<paival> last){
		HashMap<String, ArrayList<paival>> res = new HashMap<String,ArrayList<paival>>();
		int[] lcount = Aichupai.getvalnum(last);
		int min=0,max=0;
		for(int i=3;i<15;i++){
			if(lcount[i]>2&&min==0){
				min=i;
			}
			if(lcount[i]>2){
				max=i;
			}
		}
		int[] count = Aichupai.getvalnum(all);
		int  znum=0;
		int[] zvals = new int[17];
		for(int i=min+1;i<15;i++){
			if(count[i]>2){
				zvals[znum]=i;
				znum++;
			}
		}
		int step = max-min+1;
		int size=0;
		int lsize = (last.size()-step*3)/step;
		if(znum>=step){
			for(int j=0;j<(znum-step+1);j++){
				int[] temp = new int[step];
				int k=0;
				for(int i=j;i<j+step;i++){
					temp[k]=zvals[i];
				}
				if(isshun(temp)){
					HashMap<String, ArrayList<paival>> pei = new HashMap<String,ArrayList<paival>>();
					if(lsize==1){
						pei = getnoinonemore(all,temp);
					}
					if(lsize==2){
						pei = getnointwomore(all,temp);
					}
					if(pei.size()>0){
						ArrayList<paival> tsun = new ArrayList<paival>();
						for(int s=0;s<temp.length;s++){
							int flag=0;
							for(int z=0;z<all.size();z++){
								if(temp[s]==all.get(z).val&&flag<3){
									tsun.add(all.get(z));
									flag++;
								}
							}
						}
						for(int i=0;i<pei.size();i++){
							ArrayList<paival> ts = tsun;
							ts.addAll(pei.get(""+i));
							res.put(""+size,Aichupai.Sortpai(ts));
							size++;
						}
						
					}
					
				}
			}
		}
		
		return res;
	}
	static HashMap<String,ArrayList<paival>> getnointwomore(ArrayList<paival> all,int[] vals){
		HashMap<String, ArrayList<paival>> res = new HashMap<String,ArrayList<paival>>();
		ArrayList<paival> temp = new ArrayList<paival>();
		for(int i=0;i<all.size();i++){
			boolean flag=true;
			for(int j=0;j<vals.length;j++){
				if(vals[j]==all.get(i).val){
					flag=false;
				}
			}
			if(flag){
				temp.add(all.get(i));
			}
		}
		Map<String, ArrayList<paival>> duilist = getDuilist(temp, new paival(0,0));
		int size=0;
		if(duilist.size()>=vals.length){
			int sum = duilist.size()-vals.length+1;
			for(int i=0;i<sum;i++){
				ArrayList<paival> tl = new ArrayList<paival>();
				tl.addAll(duilist.get(""+i));
				int n = vals.length-1;
				for(int j=i+1;j<duilist.size();j++){
					if(n>0){
						tl.addAll(duilist.get(""+j));
						n--;
					}
				}
				res.put(""+size, tl);
				size++;
			}
		}
		return res;
	}
	
	static HashMap<String,ArrayList<paival>> getnoinonemore(ArrayList<paival> all,int[] vals){
		HashMap<String, ArrayList<paival>> res = new HashMap<String,ArrayList<paival>>();
		int size=0;
		ArrayList<paival> temp = new ArrayList<paival>();
		for(int i=0;i<all.size();i++){
			boolean flag=true;
			for(int j=0;j<vals.length;j++){
				if(vals[j]==all.get(i).val){
					flag=false;
				}
			}
			if(flag){
				temp.add(all.get(i));
			}
		}
		if(temp.size()>=vals.length){
			int sum = temp.size()-vals.length+1;
			for(int i=0;i<sum;i++){
				ArrayList<paival> tl = new ArrayList<paival>();
				tl.add(temp.get(i));
				int n = vals.length-1;
				for(int j=i+1;j<temp.size();j++){
					if(n>0){
						tl.add(temp.get(j));
						n--;
					}
				}
				res.put(""+size, tl);
				size++;
			}
		}
		return res;
	}
	static HashMap<String,ArrayList<paival>> getBigLiandui(ArrayList<paival> all,paival min,paival max){
		HashMap<String, ArrayList<paival>> res = new HashMap<String,ArrayList<paival>>();
		int[] count = Aichupai.getvalnum(all);
		int  znum=0;
		int[] zvals = new int[17];
		for(int i=min.val+1;i<15;i++){
			if(count[i]>1){
				zvals[znum]=i;
				znum++;
			}
		}
		int step = max.val-min.val+1;
		int size=0;
		if(znum>=step){
			for(int j=0;j<(znum-step+1);j++){
				int[] temp = new int[step];
				int k=0;
				for(int i=j;i<j+step;i++){
					temp[k]=zvals[i];
				}
				if(isshun(temp)){
					ArrayList<paival> tsun = new ArrayList<paival>();
					for(int s=0;s<temp.length;s++){
						int flag=0;
						for(int z=0;z<all.size();z++){
							if(temp[s]==all.get(z).val&&flag<2){
								tsun.add(all.get(z));
								flag++;
							}
						}
					}
					res.put(""+size,tsun);
					size++;
				}
			}
		}
		return res;
	}
	
	static HashMap<String,ArrayList<paival>> getBigshun(ArrayList<paival> all,paival min,paival max){
		HashMap<String, ArrayList<paival>> res = new HashMap<String,ArrayList<paival>>();
		int[] count = Aichupai.getvalnum(all);
		int  znum=0;
		int[] zvals = new int[17];
		for(int i=min.val+1;i<15;i++){
			if(count[i]>0){
				zvals[znum]=i;
				znum++;
			}
		}
		int step = max.val-min.val+1;
		int size=0;
		if(znum>=step){
			for(int j=0;j<(znum-step+1);j++){
				int[] temp = new int[step];
				int k=0;
				for(int i=j;i<j+step;i++){
					temp[k]=zvals[i];
				}
				if(isshun(temp)){
					ArrayList<paival> tsun = new ArrayList<paival>();
					for(int s=0;s<temp.length;s++){
						boolean flag=true;
						for(int z=0;z<all.size();z++){
							if(temp[s]==all.get(z).val&&flag){
								tsun.add(all.get(z));
								flag=false;
							}
						}
					}
					res.put(""+size,tsun);
					size++;
				}
			}
		}
		return res;
	}
	static boolean isshun(int[] pais){
		boolean res = true;
		for(int i=0;i<pais.length-1;i++){
			if(pais[i+1]-pais[i]!=1){
				res=false;
			}
		}
		return res;
	}
	static HashMap<String,ArrayList<paival>>  getSITwolist(ArrayList<paival> all,paival one){
		HashMap<String, ArrayList<paival>> res = new HashMap<String,ArrayList<paival>>();
		Map<String,ArrayList<paival>> allzd = getZaDan(all);
		int size=0;
		for(int i=0;i<allzd.size();i++){
			ArrayList<paival> tp = allzd.get(i);
			if(tp.get(0).val>one.val){
				HashMap<String, ArrayList<paival>> duipai = getnoduilist(all,tp.get(0));
				if(duipai.size()>0){
					for(int j=0;j<duipai.size();j++){
						ArrayList<paival> rstp = tp;
						rstp.addAll(duipai.get(""+j));
						res.put(""+size,Aichupai.Sortpai(rstp));
						size++;
					}
				}
				
			}
		}
		return res;
	}
	static HashMap<String,ArrayList<paival>> getbigZd(ArrayList<paival> all,paival one){
		HashMap<String, ArrayList<paival>> res = new HashMap<String,ArrayList<paival>>();
		Map<String,ArrayList<paival>> allzd = getZaDan(all);
		int size=0;
		for(int i=0;i<allzd.size();i++){
			if((allzd.get(i)).get(0).val>one.val){
				res.put(""+size,allzd.get(""+i));
				size++;
			}
		}
		ArrayList<paival> hj= Aichupai.getHuoJian(all);
		if(hj.size()>0){
			res.put(""+size, hj);
			Log.v("log","有火箭");
		}
		return res;
	}
	static ArrayList<paival> getnoinlist(ArrayList<paival> all,paival one){
		ArrayList<paival> temp = new ArrayList<paival>();
		for(int i=0;i<all.size();i++){
			if(all.get(i).val!=one.val){
				temp.add(all.get(i));
			}
		}
		return temp;
	}
	static HashMap<String,ArrayList<paival>> getnoduilist(ArrayList<paival> all,paival one){
		HashMap<String, ArrayList<paival>> temp = new HashMap<String,ArrayList<paival>>();
		Map<String,ArrayList<paival>> alldui = getDuilist(all,new paival(0,0));
		int size = 0;
		for(int i=0;i<alldui.size();i++){
			if((alldui.get(""+i)).get(0).val!=one.val){
				temp.put(""+size,alldui.get(""+i));
				size++;
			}
		}
		return temp;
	}
	static  Map<String,ArrayList<paival>>  getSanTwolist(ArrayList<paival> all,paival big){
		Map<String,ArrayList<paival>> res= new HashMap<String,ArrayList<paival>>();
		int size=0;
		Map<String,ArrayList<paival>> sanlist = getSanlist(all,big);
		int snum = sanlist.size();
		if(snum>0){
			for(int i=0;i<snum;i++){
				ArrayList<paival> temp = sanlist.get(""+i);
				Map<String,ArrayList<paival>> one = getnoduilist(all,temp.get(0));
				Log.v("log","duisize="+one.size());
				if(one.size()>0){
					for(int j=0;j<one.size();j++){
						ArrayList<paival> san = new ArrayList<paival>();
						Log.v("log","duisizenum="+one.get(""+j).size());
						san.addAll(one.get(""+j));
						san.addAll(temp);
						res.put(""+size, Aichupai.Sortpai(san));
						size++;
					}
				}
			}
		}
		
		return res;
	}
	static  Map<String,ArrayList<paival>>  getSanonelist(ArrayList<paival> all,paival big){
		Map<String,ArrayList<paival>> res= new HashMap<String,ArrayList<paival>>();
		int size=0;
		Map<String,ArrayList<paival>> sanlist = getSanlist(all,big);
		int snum = sanlist.size();
		if(snum>0){
			for(int i=0;i<snum;i++){
				ArrayList<paival> temp = sanlist.get(""+i);
				ArrayList<paival> one = getnoinlist(all,temp.get(0));
				if(one.size()>0){
					for(int j=0;j<one.size();j++){
						ArrayList<paival> san = temp;
						san.add(one.get(j));
						res.put(""+size, Aichupai.Sortpai(san));
						size++;
					}
				}
			}
		}
		
		return res;
	}
	static Map<String,ArrayList<paival>> getZaDan(ArrayList<paival> all){
		Map<String,ArrayList<paival>> res= new HashMap<String,ArrayList<paival>>();
		int[] count = Aichupai.getvalnum(all);
		int  znum=0;
		int[] zvals = new int[4];
		for(int i=0;i<count.length;i++){
			if(count[i]==4){
				zvals[znum]=i;
				znum++;
			}
		}
		int size=0;
		if(znum>0){
			for(int i=0;i<znum;i++){
				ArrayList<paival> temp = new ArrayList<paival>();
				for(int j=0;j<all.size();j++){
					if(all.get(j).val==zvals[i]){
						temp.add(all.get(j));
					}
				}
				res.put(""+size, temp);
				size++;
			}
		}
		
		return res;
	}
	static Map<String,ArrayList<paival>> hebinlist(ArrayList<paival> all,Map<String,ArrayList<paival>> res){
		Map<String,ArrayList<paival>> reslist = res;
		int size = res.size();
		Log.v("log","binds="+size);
		Map<String,ArrayList<paival>> zd = getZaDan(all);
		ArrayList<paival> hj = Aichupai.getHuoJian(all);
		int zsum = zd.size();
		if(zsum>0){
			Log.v("log","有砸蛋1");
			for(int i=0;i<zsum;i++){
				reslist.put(""+size, zd.get(""+i));
				size++;
			}
		}
		if(hj.size()>0){
			Log.v("log","有火箭");
			reslist.put(""+size, hj);
		}
		return reslist;
	}
	static Map<String,ArrayList<paival>> getSanlist(ArrayList<paival> all,paival big){
		Map<String,ArrayList<paival>> res= new HashMap<String,ArrayList<paival>>();
		int[] count = Aichupai.getvalnum(all);
		int num=0;
		int[] vals = new int[10];
		for(int i=0;i<count.length;i++){
			if(count[i]>2&&i>big.val){
				vals[num]=i;
				num++;
			}
		}
		int size=0;
		if(num>0){
			for(int i=0;i<num;i++){
				ArrayList<paival> temp = new ArrayList<paival>();
				int d=0;
				for(int j=0;j<all.size();j++){
					if(all.get(j).val==vals[i]&&d<3){
						temp.add(all.get(j));
						d++;
					}
				}
				res.put(""+size, temp);
				size++;
			}
		}
		
		return res;
	}
	static Map<String,ArrayList<paival>> getDuilist(ArrayList<paival> all,paival big){
		Map<String,ArrayList<paival>> res= new HashMap<String,ArrayList<paival>>();
		int[] count = Aichupai.getvalnum(all);
		int num=0;
		int[] vals = new int[10];
		for(int i=0;i<count.length;i++){
			if(count[i]>1&&i>big.val){
				vals[num]=i;
				num++;
			}
		}
		int size=0;
		if(num>0){
			for(int i=0;i<num;i++){
				ArrayList<paival> temp = new ArrayList<paival>();
				int d=0;
				for(int j=0;j<all.size();j++){
					if(all.get(j).val==vals[i]&&d<2){
						temp.add(all.get(j));
						d++;
					}
				}
				res.put(""+size, temp);
				size++;
			}
		}
		
		return res;
	}
	//提示单牌
	static Map<String,ArrayList<paival>> getDzlist(ArrayList<paival> all,paival big){
		Map<String,ArrayList<paival>> res= new HashMap<String,ArrayList<paival>>();
		int size=0;
		for(int i=0;i<all.size();i++){
			if(all.get(i).val>big.val){
				ArrayList<paival> temp = new ArrayList<paival>();
				temp.add(all.get(i));
				res.put(""+size, temp);
				size++;
			}
		}
		return res;
	}
}
