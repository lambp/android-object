package com.android.tutorial;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Client {
	private String CONECTIP;
	private int PORT=5000;
	private Socket Newsocket;
	public Client(String IP){
		CONECTIP = IP;
		try {
			ConnectTO(CONECTIP);
		} catch (UnknownHostException e) {
			Log.v("log", "找不到主机");
		} catch (IOException e) {
			Log.v("log", "异常");
		}
	}

    public void ConnectTO(String ip) throws UnknownHostException, IOException
    {
    	Log.v("log", "start=");
    	Newsocket=RequestSocket(ip,PORT);
    	//SendMsg("connect to server");  
    	//String txt = ReceiveMsg();
    	Log.v("log", "client=");
    }
    
    private Socket RequestSocket(String host,int port) throws UnknownHostException, IOException
    {   
    	Socket socket = new Socket(host, port);
    	return socket;
    }
    
    public void SendMsg(String msg) throws IOException
    {
    	BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Newsocket.getOutputStream()));
    	writer.write(msg.replace("\n", " ")+"\n");
    	writer.flush();
    }
    
    public String ReceiveMsg() throws IOException
    {
    	BufferedReader reader = new BufferedReader(new InputStreamReader(Newsocket.getInputStream()));
    	String txt=reader.readLine();
    	return txt;

    }    
}
