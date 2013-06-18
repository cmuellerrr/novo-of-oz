package edu.cmu.hcii.novo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSON;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A class representing a menu.  Essentially just an image because we don't have
 * nested menus.
 * 
 * TODO: This and Screen need to change to extend a base class - too much repeated code.
 * 
 * @author Chris
 *
 */
public class Menu {

	private PApplet parent;
	private List<String> paths;
	private List<PImage> images;
	private int index;
	
	/**
	 * Create a Menu object from the given json object.
	 * The PApplet is only used to load the image here.
	 * 
	 * @param json
	 * @param parent
	 */
	Menu(JSON json, PApplet parent) {
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
	}
	
	/**
	 * Draw the current menu image.
	 */
	public void draw() {
		  parent.image(images.get(index), 0, 0, OZ.screenW, OZ.screenH);
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
