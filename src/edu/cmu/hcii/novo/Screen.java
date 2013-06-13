package edu.cmu.hcii.novo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSON;

import processing.core.PApplet;
import processing.core.PImage;

public class Screen {

	  String path;
	  PImage img;
	  Map<Character, Menu> menus;
	  
	  Screen(JSON json, PApplet parent) {
	    this.path = json.getString("path");
	    this.img = parent.loadImage("images/" + path);
	    this.menus = new HashMap<Character, Menu>();
	    
	    JSON jsonMenus = json.getArray("menus");
	    for (int i = 0; i < jsonMenus.length(); i++) {
	      JSON curMenu = jsonMenus.getJSON(i);
	      this.menus.put(curMenu.getString("key").charAt(0), new Menu(curMenu, parent));
	    }
	  }
	  
	  public boolean hasMenu(char k) {
	    return menus.containsKey(k); 
	  }
	  
	  public Menu getMenu(char k) {
	    return menus.get(k); 
	  }
}

class Menu {
	String path;
	PImage img;
	
	Menu(JSON json, PApplet parent) {
		this.path = json.getString("path");
	    this.img = parent.loadImage("images/" + path);
	}
}
