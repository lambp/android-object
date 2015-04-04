package com.android.angle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

public class AngleDrawLine extends AngleObject{
	// 画点的坐标
    float[] vertexArray = new float[] { 
            -0.8f, -0.4f * 1.732f, 0.0f, 
            0.8f,-0.4f * 1.732f, 0.0f,
            0.0f, 0.4f * 1.732f, 0.0f, };

    // 画线的坐标
    float vertexArray2[] = { 
            -0.8f, -0.4f * 1.732f, 0.0f,
            -0.4f, 0.4f * 1.732f,0.0f, 
            0.0f, -0.4f * 1.732f, 0.0f, 
            0.4f, 0.4f * 1.732f, 0.0f, };
    
    float[][] vertices = {   
    	    { 100.25f,  100.25f  },  
    	    { 100.75f,  100.25f  },  
    	    { 200.75f,  200.75f  },  
    	    { 100.25f,  200.75f  }  
    	};   
    float[][] colors = {   
    	    { 255, 0, 0, 80 },    
    	    { 255, 0, 0, 80 },    
    	    { 255, 0, 0, 80 },    
    	    { 255, 0, 0, 80 }    
    	};    
    public AngleVector mPosition; // Position
	public AngleDrawLine(){
		mPosition = new AngleVector();
		//mPosition.set(100,100);
	}
	int index;
	@Override
	public void draw(GL10 gl){
		 ByteBuffer vbb    
		 = ByteBuffer.allocateDirect(vertexArray.length*4);    
		 vbb.order(ByteOrder.nativeOrder());    
		 FloatBuffer vertex = vbb.asFloatBuffer();    
		 vertex.put(vertexArray);    
		 vertex.position(0);    
		           
		 gl.glLoadIdentity();    
		 gl.glTranslatef(0, 0, -4);    
		           
		 gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);    
		           
		 gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertex);    
		 index++;    
		 index%=10;    
		 switch(index){    
		 case 0:    
		 case 1:    
		 case 2:    
		 gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);    
		 gl.glDrawArrays(GL10.GL_LINES, 0, 4);    
		 break;    
		 case 3:    
		 case 4:    
		 case 5:    
		 gl.glColor4f(0.0f, 1.0f, 0.0f, 1.0f);    
		 gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, 4);    
		 break;    
		 case 6:    
		 case 7:    
		 case 8:    
		 case 9:    
		 gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);    
		 gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 4);    
		 break;    
		 }    
		           
		 gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  
        super.draw(gl);
	}
}
