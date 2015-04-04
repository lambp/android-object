package game.sprite;

import game.data.mapData;

import java.util.ArrayList;

import android.util.Log;

import com.android.angle.AngleSpriteLayout;
import com.android.angle.AngleVector;

public class player {
	private AngleVector startPos;	//起飞台位置
	private int sWidth,sHeight,myplay;
	private ArrayList<Plane> planes;  //飞机
	private boolean isAI;			  //是否为电脑AI
	AngleSpriteLayout plane_layout;   //飞机图片
	int[][] initLine = new int[56][2];//飞行路线
	public boolean isPlaying;		  //是否参加战斗
	public boolean Isover;			  //是否结束游戏
	public player(int play,boolean AI,int width,int height,AngleSpriteLayout layout,boolean isplay){
		startPos = mapData.getStartPos(play, width, height);
		myplay = play;
		sWidth = width;
		sHeight = height;
		planes = new ArrayList<Plane>();
		this.isAI=AI;
		plane_layout = layout;
		initPlanes();
		initLine = mapData.initLine(play);
		isPlaying = isplay;
		Isover=false;
	}
	//获取棋子所在的地图 位置
	public int[] getMapij(int planei){
		
		int[] ij = {-1,-1};
		int nid = planes.get(planei).getLineid();
		if(nid>=0){
			ij[0] = initLine[nid][0];
			ij[1] = initLine[nid][1];
		}
		return ij;
	}
	//判断给定的位置是否有棋子
	public int[] getPlaneids(int[] xy){
		int[] planeid = {-1,-1,-1,-1}; 
		for(int i=0;i<4;i++){
			if(planes.get(i).IsStart()){
				int[]  tpij =  getMapij(i);
				if(xy[0]==tpij[0]&&xy[1]==tpij[1]){
						planeid[i]=i;
				}
			}
		}
		return planeid;
	}
	//判断跑道上是否有棋子
	public int[] getPlanedaoids(){
		int[] planeid = {-1,-1,-1,-1}; 
		int[] xy = new int[2];
		xy[0]=initLine[52][0];
		xy[1]=initLine[52][1];
		for(int i=0;i<4;i++){
			if(planes.get(i).IsStart()){
				int[]  tpij =  getMapij(i);
				if(xy[0]==tpij[0]&&xy[1]==tpij[1]){
						planeid[i]=i;
				}
			}
		}
		return planeid;
	}
	
	public AngleVector getStartPos(){
		return this.startPos;
	}
	//初始化坐标
	public void initPlanes(){
		ArrayList<AngleVector> aVlist = mapData.getInitPos(myplay, sWidth, sHeight);
		if(this.planes.size()>0){
			//重置
			for(int i=0;i<4;i++){
				AngleVector tp = aVlist.get(i);
				this.planes.get(i).setPos(tp,-2);
			}
		
		}else{
			for(int i=0;i<4;i++){
				AngleVector tp = aVlist.get(i);
				this.planes.add(new Plane(tp.mX,tp.mY,plane_layout,myplay));
			}
		}
		for(int i=0;i<this.planes.size();i++){
			if(myplay==1||myplay==2){
				this.planes.get(i).initFrame(3);
			}else{
				this.planes.get(i).initFrame(0);
			}
		}
		Isover=false;
	}
	//恢复上一
	//获取所有棋子
	public ArrayList<Plane> getplanes(){
		return this.planes;
	}
	//获取棋子
	public Plane getplane(int i){
		return this.planes.get(i);
	}
	//获取玩家位置
	public int getPlayN(){
		return this.myplay;
	}
	//是否为AI
	public boolean isAi(){
		return this.isAI;
	}
	
	//设置坐标
	public void setPlanePos(int i,AngleVector src,int stepid){
		if(i>=0){
			this.planes.get(i).setPos(src,stepid);
		}
	}
	//起飞
	public void startFly(int i){
		this.planes.get(i).startFly(startPos);
	}
	//获取路线某个点的 ij
	public int[] getLine(int index){
		int[] temp = initLine[index];
		return temp;
	}
	//设置为已经到达目的地
	public void setEnd(int i){
		this.planes.get(i).setEnd(true);
		if(isWin()){
			Isover=true;
		}
	}
	//判断游戏是否胜利
	public boolean isWin(){
		boolean iswin=true;
		for(int i=0;i<planes.size();i++){
			if(!planes.get(i).ISend()) iswin = false;
		}
		return iswin;
	}
	//触摸选择
	public int checkTest(float ex,float ey){
		for(int i=0;i<planes.size();i++){
			if(planes.get(i).test(ex,ey)) return i;
		}
		return -1;
	}
	//选择要起飞的棋子
	public int SelectStart(float ex,float ey){
		for(int i=0;i<planes.size();i++){
			if(planes.get(i).SelectStart(ex,ey)) return i;
		}
		return -1;
	}
	//获取已经起飞的棋子个数
	public int getStartNum(){
		int count=0;
		for(int i=0;i<planes.size();i++){
			if(planes.get(i).IsStart()&&!planes.get(i).ISend()) count++;
		}
		return count;
	}
	//未起飞的棋子个数-不包括已到达目的地的
	public int getNoStartNum(){
		int count=0;
		for(int i=0;i<planes.size();i++){
			if(!planes.get(i).IsStart()&&!planes.get(i).ISend()) count++;
		}
		return count;
	}
	//获取能走的棋子
	public int getCanPlay(){
		for(int i=0;i<planes.size();i++){
			if(planes.get(i).IsStart()&&!planes.get(i).ISend()) return i;
		}
		return -1;
	}
	//获取能起飞的棋子第一个棋子
	public int getCanstart(){
		for(int i=0;i<planes.size();i++){
			if(!planes.get(i).IsStart()) return i;
		}
		return -1;
	}
	//获取已经起飞的棋子---AI所有能走棋的棋子
	public ArrayList<String> getYearStart(){
		ArrayList<String> starts = new ArrayList<String>();
		for(int i=0;i<planes.size();i++){
			if(!planes.get(i).ISend()&&planes.get(i).IsStart()){
				starts.add(""+i);
			}
		}
		return starts;
	}
	//打回原地
	public void setTostart(int i){
		planes.get(i).setTostart();
	}
	//获取到达终点的棋子数
	public int getEndCount(){
		int count=0;
		for(int i=0;i<planes.size();i++){
			if(planes.get(i).ISend()) count++;
		}
		return count;
	}
}
