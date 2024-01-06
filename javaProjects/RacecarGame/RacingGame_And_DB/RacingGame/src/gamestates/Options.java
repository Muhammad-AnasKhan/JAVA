package gamestates;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

//import Version.AutoUpdater;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class Options {
	//Original Size 630, 325. Divide the whole size by 3
	Rectangle[] buttons = {new Rectangle(240, 400, 210, 109), new Rectangle(100, 100, 210, 109), new Rectangle(400, 100, 210, 109)};

	private String updateMsg = "";
	
	public Options() {
		init();//load everything upon creation
	}
	
	
	Image background, back, submitBug, update, backWave, submitBugWave, updateWave;
	
	public void init() {
		//if we need to load/initialize anything before we render it.
		//Load All Images
		background = new ImageLoader(ImageLoader.optionsBackground).getImage();
		back = new ImageLoader(ImageLoader.back).getImage();
		backWave = new ImageLoader(ImageLoader.backWave).getImage();
		submitBug = new ImageLoader(ImageLoader.submitBug).getImage();
		update = new ImageLoader(ImageLoader.update).getImage();
		submitBugWave = new ImageLoader(ImageLoader.submitBugWave).getImage();
		updateWave = new ImageLoader(ImageLoader.updateWave).getImage();
	}
	public void tick() {
		//display any calculations
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) { //if we are talking about the first rectangle, we will switch the states to game
					Controller.switchStates(Controller.STATE.MENU);//switch to game gamestate.
				}
				if(i == 1) { //if we are talking about the first rectangle, we will switch the states to game
					Controller.switchStates(Controller.STATE.SUBMITBUG);//switch to game gamestate.
				}
				if(i == 2) {
					//updateMsg = AutoUpdater.checkForUpdate();
				}
			}
		}
	}
	public void render(Graphics g) {
		//display graphics.
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.drawImage(back, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		g.drawImage(submitBug, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
//		g.drawImage(update, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
		
		g.drawString(updateMsg, buttons[2].x-10, buttons[2].y+buttons[2].height+25);
		
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(backWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
				if (i == 1) {	
					g.drawImage(submitBugWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
				if (i == 2) {	
					//g.drawImage(updateWave, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
				}
			}
		}		
	}
}
