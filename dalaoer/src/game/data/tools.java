package game.data;

import android.content.Context;
import android.content.SharedPreferences;

public class tools {
	public final static String score = "myscore";
	public final static String newdate = "stepmun";
	public final static String sconf= "sconf";
	public final static String bgsound="bgsound";
	 //保存到系统文件  ---优先数据
	 public static boolean savetomem(String filename,String msg,Context cont){
		 SharedPreferences settings = cont.getSharedPreferences(filename,0);
		 SharedPreferences.Editor editor = settings.edit();
		 editor.putString("ERINFO",msg);
		 return editor.commit();
	 }

	 //获取数据
	 public static String getstrformmem(String filename,Context cont){
		 SharedPreferences settings = cont.getSharedPreferences(filename,0);
		 String msg = settings.getString("ERINFO", "null");//如果为空 返回null
		return msg;
	 }
}
