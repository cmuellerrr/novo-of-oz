package edu.cmu.hcii.novo;

import org.json.JSON;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * A class representing a menu.  Essentially just an image because we don't have
 * nested menus.
 * 
 * @author Chris
 *
 */
public class Menu {

	private String path;
	private PImage img;
	
	/**
	 * Create a Menu object from the given json object.
	 * The PApplet is only used to load the image here.
	 * 
	 * @param json
	 * @param parent
	 */
	Menu(JSON json, PApplet parent) {
		this.path = json.getString("path");
	    this.img = parent.loadImage("images/" + path);
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
