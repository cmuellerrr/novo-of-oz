package edu.cmu.hcii.novo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	  private PApplet parent;
	  private List<String> paths;
	  private List<PImage> images;
	  private int index;
	  private Map<Character, Menu> menus;
	  
	  /**
	   * Create a Screen object from the given json object.
	   * 
	   * @param json
	   * @param parent
	   */
	  Screen(JSON json, PApplet parent) {
	    this.parent = parent;
	    
	    index = 0;
	    paths = new ArrayList<String>();
	    images = new ArrayList<PImage>();
	    
	    JSON jsonPaths = json.getArray("paths");
	    for (int i = 0; i < jsonPaths.length(); i++) {
	    	String curPath = jsonPaths.getString(i);
	    	paths.add(curPath);
	    	images.add(parent.loadImage("images/" + curPath));
	    }
	    
	    menus = new HashMap<Character, Menu>();
	    
	    JSON jsonMenus = json.getArray("menus");
	    for (int i = 0; i < jsonMenus.length(); i++) {
	        JSON curMenu = jsonMenus.getJSON(i);
	        menus.put(curMenu.getString("key").charAt(0), new Menu(curMenu, parent));
	    }
	  }
	  
	  /**
	   * Draw the current screen image.
	   */
	  public void draw() {
		  parent.image(images.get(index), 0, 0, OZ.screenW, OZ.screenH);
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
	   * Scroll down the screen, so just increase the index.
	   */
	  public void scrollDown() {
		  if (index < paths.size()-1) index++;
	  }
	  
	  /**
	   * Scroll up the screen, so just decrease the index.
	   */
	  public void scrollUp() {
		  if (index > 0) index--;
	  }
	  
	  /**
	   * @return the path
	   */
	  public String getPath() {
		return paths.get(index);
	  }

	  /**
	   * @return the img
	   */
	  public PImage getImage() {
		  return images.get(index);
	  }
}
