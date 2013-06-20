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
	  private Map<Character, Screen> subScreens;
	  private Character subScreenIndex;
	  
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
	    subScreenIndex = null;
	    
	    JSON jsonPaths = json.getArray("paths");
	    for (int i = 0; i < jsonPaths.length(); i++) {
	    	String curPath = jsonPaths.getString(i);
	    	paths.add(curPath);
	    	images.add(parent.loadImage("images/" + curPath));
	    }
	    
	    try {
		    subScreens = new HashMap<Character, Screen>();	    
		    JSON jsonSubScreens = json.getArray("subScreens");
		    for (int i = 0; i < jsonSubScreens.length(); i++) {
		        JSON curSubScreen = jsonSubScreens.getJSON(i);
		        subScreens.put(curSubScreen.getString("key").charAt(0), new Screen(curSubScreen, parent));
		    }
	    } catch (RuntimeException e) {
	    	System.out.println("No subscreens specified on screen: " + paths.toString());
	    }
	  }
	  
	  /**
	   * Draw the current screen image.  If a subscreen is active, draw that.
	   */
	  public void draw() {
		  if (subScreenIndex != null) {
			  subScreens.get(subScreenIndex).draw();
		  } else {
			  parent.image(images.get(index), 0, 0, OZ.screenW, OZ.screenH);
		  }
	  }
	  
	  /**
	   * Handle any cleanup when the user leaves the screen.
	   * Call this on screen changes.
	   */
	  public void leaveScreen() {
		  //clean up any pointers
		  if (subScreenIndex != null) {
			  subScreens.get(subScreenIndex).leaveScreen();
			  subScreenIndex = null;
		  }
	  }
	  
	  public boolean handleKeyPressed(char key) {
		  if (subScreenIndex != null) {
			  return subScreens.get(subScreenIndex).handleKeyPressed(key);
		  } else {
			  if (subScreens.containsKey(key)) {
				  subScreenIndex = key;
				  return true;
			  }
			  return false;
		  }	  
	  }

	  /**
	   * Scroll down the screen, so just increase the index.
	   */
	  public boolean scrollDown() {
		  if (subScreenIndex != null) {
			  return subScreens.get(subScreenIndex).scrollDown();
		  } else {
			  if (index < paths.size()-1) {
				  index++;
			  	  return true;
			  }
			  return false;
		  }
	  }
	  
	  /**
	   * Scroll up the screen, so just decrease the index.
	   */
	  public boolean scrollUp() {
		  if (subScreenIndex != null) {
			  return subScreens.get(subScreenIndex).scrollUp();
		  } else {
			  if (index > 0) {
				  index--;
			      return true;
			  }
			  return false;
		  }
	  }
	  
	  /**
	   * @return the path
	   */
	  public String getPath() {
		  if (subScreenIndex != null) {
			  return subScreens.get(subScreenIndex).getPath();
		  } else {
			  return paths.get(index);
		  }
	  }
	  
	  /**
	   * Return the head of the paths list.  Used for quick jumps.
	   * @return
	   */
	  public String getOriginalPath() {
		  return paths.get(0);
	  }

	  /**
	   * @return the img
	   */
	  public PImage getImage() {
		  if (subScreenIndex != null) {
			  return subScreens.get(subScreenIndex).getImage();
		  } else {
			  return images.get(index);
		  }
	  }
}
