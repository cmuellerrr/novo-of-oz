package edu.cmu.hcii.novo;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSON;

import processing.core.PApplet;

@SuppressWarnings("serial")
public class OZ extends PApplet {

	int screenW = 640;
	int screenH = 480;

	//Capture cam;
	List<Screen> screens;
	Map<Character, Menu> menus;
	int screenIndex;
	Menu activeMenu;
	boolean hide;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PApplet.main(new String[] { "edu.cmu.hcii.novo.OZ" });
	}

	public void setup() {
		size(screenW, screenH);

		/*
		String[] cameras = Capture.list();

		if (cameras.length == 0) {
			println("There are no cameras available for capture.");
			exit();
		} else {
			println("Available cameras:");
			for (int i = 0; i < cameras.length; i++) {
				println(cameras[i]);
			}
			cam = new Capture(this, cameras[0]);

			// Start capturing the images from the camera
			cam.start();
		}
		 */

		setupScreens();
		connect("192.168.1.101");
		sendScreenUpdate();
	}

	public void draw() {
		//if (cam.available() == true) cam.read();
		//set(0, 0, cam);

		rect(0, 0, width, height);
		if (!hide) {
			image(screens.get(screenIndex).img, 0, 0, screenW, screenH);
			if (activeMenu != null) image(activeMenu.img, 0, 0, screenW, screenH);
		}
	}

	private void setupScreens() {
		screens = new ArrayList<Screen>();
		menus = new HashMap<Character, Menu>();

		JSON json = JSON.load(dataPath("proto.json"));

		//load the menus
		JSON jsonMenus = json.getArray("menus");
		for (int i = 0; i < jsonMenus.length(); i++) {
			JSON curMenu = jsonMenus.getJSON(i);
			menus.put(curMenu.getString("key").charAt(0), new Menu(curMenu, this));
		}

		//load the screens
		JSON jsonScreens = json.getArray("screens");
		for (int i = 0; i < jsonScreens.length(); i++) {
			screens.add(new Screen(jsonScreens.getJSON(i), this)); 
		}

		//set the starting image and active menu
		screenIndex = 0;
		activeMenu = null;
		hide = false;
	}

	public void keyPressed() {
		boolean updated = false;

		if (key == CODED) {
			if (keyCode == LEFT) {
				if (screenIndex > 0) screenIndex--;
				activeMenu = null;
				updated = true;
			} else if (keyCode == RIGHT) {
				if (screenIndex < screens.size()-1) screenIndex++;
				activeMenu = null;
				updated = true;
			} 
		}

		if (screens.get(screenIndex).hasMenu(key)) {
			if (activeMenu != screens.get(screenIndex).getMenu(key)) {
				activeMenu = screens.get(screenIndex).getMenu(key);
			} else {
				activeMenu = null;
			}
			updated = true;
		} else if (menus.containsKey(key)) {
			if (activeMenu != menus.get(key)) {
				activeMenu = menus.get(key);
			} else {
				activeMenu = null;
			}
			updated = true;
		} else {
			//Any other key actions
			if (key == 'h') {
				hide = !hide;
				updated = true;
			}
		}

		if (updated) sendScreenUpdate();
	}

	private void sendScreenUpdate() {
		String bg = (screens.get(screenIndex) != null && !hide) ? screens.get(screenIndex).path : "";
		String fg = (activeMenu != null && !hide) ? activeMenu.path : "";

		String msg = "{\"background\": \"" + bg + "\", \"foreground\": \"" + fg + "\"} \n";
		sendMsg(msg);
	}



	/**
	 * CONNECTION PORTION
	 * TODO: Put this in another file
	 */



	// socket
	private Socket socket;
	private String ip;
	private final int port = 5555;
	private boolean connected = false; 		// is connected or not

	// connect/send thread
	private Runnable connectRunnable	= null;
	private Thread sendThread           = null;
	private DataOutputStream streamOut  = null;
	
	// receive thread
	private Runnable recRunnable		= null;
	private Thread recThread            = null;
	private DataInputStream streamIn	= null;

	public void connect(String ip_address){
		connected = false;
		try {
			if (socket!= null) socket.close();
			if (streamOut != null) streamOut.close();
			if (streamIn != null) streamIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ip = ip_address;
		socket = new Socket();
		connectRunnable = new connectSocket();
		sendThread = new Thread(connectRunnable);
		sendThread.start();
	}

	// sends bytes to output stream
	public void sendMsg(String msg){
		if (connected) { // if system is connected
			try {
				System.out.println("Sending message: " + msg);
				streamOut.writeBytes(msg);
				streamOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else{
			// if system is not connected
			System.out.println("Not connected.");
		}
	} 

	// Forces the socket to disconnect
	public void disconnect(){
		connected = false;

		try {
			if (socket!= null) socket.close();
			if (streamOut != null) streamOut.close();
			if (streamIn != null) streamIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  

	// thread sets up socket connection
	class connectSocket implements Runnable {
		@Override
		public void run() {
			//Log.v(TAG, "connectSocket_run");
			SocketAddress socketAddress = new InetSocketAddress(ip, port);
			try {               
				socket.connect(socketAddress, 1000);
				streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                recRunnable = new receiveSocket();
                recThread = new Thread(recRunnable);
                recThread.start();
			} catch (IOException e) {
				disconnect();
				e.printStackTrace();
			}
		}    
	}

	// thread receives messages
	class receiveSocket implements Runnable {
		@Override
		public synchronized void run() {
			try {  
				//Log.v(TAG, "receive socket ");
				streamIn  = new DataInputStream(socket.getInputStream()); // sets up input stream
				connected = true; // sets connection flag to true
			}
			catch(IOException ioe) {  
				//Log.v(TAG, "Error getting input stream: " + ioe);
			}
			// when connected, this thread will stay in this while loop
			while (connected) {  
				
			}
		}
	}
}