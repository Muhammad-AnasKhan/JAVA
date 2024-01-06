package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class MainMenu {
	//Original Size 630, 325. Divide the whole size by 3
	Rectangle[] buttons = {new Rectangle(50, 180, 200, 100), new Rectangle(50, 290, 200, 100), new Rectangle(50, 400, 200, 100)};

	public MainMenu() {
		init();//load everything upon creation
	}
	
	
	Image background, playButton, loginButton, highScoresButton, signupButton, optionsButton, loginWave, signupWave, optionsWave;
	
	public void init() {
		//if we need to load/initialize anything before we render it.
		//Load All Images
		background = new ImageLoader(ImageLoader.titleBackground).getImage();
		//playButton = new ImageLoader(ImageLoader.mainPlay).getImage();
		loginButton = new ImageLoader(ImageLoader.logIn).getImage();
		//highScoresButton = new ImageLoader(ImageLoader.mainHighScores).getImage();
		signupButton = new ImageLoader(ImageLoader.SignUp).getImage();
		optionsButton = new ImageLoader(ImageLoader.Options).getImage();
		loginWave = new ImageLoader(ImageLoader.loginWave).getImage();
		signupWave = new ImageLoader(ImageLoader.signUpWave).getImage();
		optionsWave = new ImageLoader(ImageLoader.optionsWave).getImage();
		
		User.User.money = 0;
		User.User.racenumber = 0;
		User.User.userID = 0;
		User.User.username = "";
		Controller.resetUserUpgrades();
	}
	public void tick() {
		//display any calculations
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) { //if we are talking about the first rectangle, we will switch the states to game
					Controller.switchStates(Controller.STATE.LOGIN);//switch to game gamestate.
				}
				if(i == 1) { //Sign Up view
					Controller.switchStates(Controller.STATE.CREATEUSER);//switch to game gamestate.
				}
				if(i == 2) { //Options Up view
					Controller.switchStates(Controller.STATE.OPTIONS);//switch to game gamestate.
				}
			}
		}
	}
	public void render(Graphics g) {
		//display graphics.
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.drawImage(loginButton, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		g.drawImage(signupButton, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
		g.drawImage(optionsButton, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
		g.setColor(Color.BLACK);
		g.setFont(Controller.bigBold);
		//g.drawString("" + Frame.TITLE, Frame.WIDTH/2-g.getFontMetrics().stringWidth("" + Frame.TITLE)/2, 25);
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(loginWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
				if (i == 1) {	
					g.drawImage(signupWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
				if (i == 2) {	
					g.drawImage(optionsWave, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
				}
			}
		}		
	}
}
