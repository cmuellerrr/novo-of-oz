package edu.cmu.hcii.novo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSON;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A representation of a prototype screen. Just an image and a 
 * map of possible menus and how they are triggered.
 * 
 * @author Chris
 *
 */
public class Screen {

	  private String path;
	  private PImage img;
	  private Map<Character, Menu> menus;
	  
	  /**
	   * Create a Screen object from the given json object.
	   * The PApplet is only used to load the image here.
	   * 
	   * @param json
	   * @param parent
	   */
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
	  
	  /**
	   * Return if the screen contains a menu which is triggered
	   * by the given key.
	   * 
	   * @param k
	   * @return
	   */
	  public boolean hasMenu(char key) {
	    return menus.containsKey(key); 
	  }
	  
	  /**
	   * Get the Menu which is triggered by the given key.
	   * 
	   * @param key
	   * @return
	   */
	  public Menu getMenu(char key) {
	    return menus.get(key); 
	  }

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the img
	 */
	public PImage getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(PImage img) {
		this.img = img;
	}	  
}
