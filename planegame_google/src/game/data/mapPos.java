package game.data;

import game.sprite.Plane;

import java.util.ArrayList;

import com.android.angle.AngleVector;
//地图对应的点
public class mapPos {
	private AngleVector xy;
	private int posval;   //对应的颜色属性 1-绿，2-红，3-黄，4-蓝 ,1-7,2-9,3-6,4-8
	private int[] pi = new int[2];   //2维数组里面对应的键值 0-行 1-列
	private ArrayList<Plane> planes; //当前节点的飞机
	
	public mapPos(AngleVector src,int[] ij,int posval){
		xy = new AngleVector();
		xy.set(src);
		this.pi = ij;
		this.posval = posval;
		planes = new ArrayList<Plane>();
	}
	public AngleVector getXY(){
		return this.xy;
	}
	public int getVal(){
		return this.posval;
	}
	public int[] getpi(){
		
		return this.pi;
	}
	public ArrayList<Plane> getPlanes(){
		
		return this.planes;
	}
	
	
}
