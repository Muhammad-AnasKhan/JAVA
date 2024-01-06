package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.*;
import java.util.Random;

import handlers.KeyHandler;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class ForgotPassword {

	Rectangle[] buttons = {new Rectangle(Frame.WIDTH/2-105, 300, 210, 109), new Rectangle(Frame.WIDTH/2-105, 410, 210, 109),
						   new Rectangle(Frame.WIDTH/2-105, 300, 210, 109)};
	
	Rectangle[] textboxes = {new Rectangle(Frame.WIDTH/2-100, 125, 380, 40),
							new Rectangle(Frame.WIDTH/2-100, 175, 380, 40),
							new Rectangle(Frame.WIDTH/2-100, 225, 380, 40)};
	
	private Image background, back, submit, sendcode, backWave, submitWave, sendCodeWave;
	private String username = "", newPassword = "", resetCode = "";
	private boolean inUsername = false, inPassword = false, inResetCode = false;
	private boolean sentCode = false;
	private String errorMessage = "";
	private String generatedCode = "";
	private Color errorColor = Color.red;
	
	public ForgotPassword() {
		init();
	}
	public void init() {
		background = new ImageLoader(ImageLoader.optionsBackground).getImage();
		back = new ImageLoader(ImageLoader.back).getImage();
		submit = new ImageLoader(ImageLoader.submit).getImage();
		sendcode = new ImageLoader(ImageLoader.sendResetCode).getImage();
		backWave = new ImageLoader(ImageLoader.backWave).getImage();
		submitWave = new ImageLoader(ImageLoader.submitWave).getImage();
		sendCodeWave = new ImageLoader(ImageLoader.sendResetCodeWave).getImage();
		
	}
	public void tick() {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				inUsername = false;
				inPassword = false;
				inResetCode = false;
				if(i == 0) { 
					//If the reset code matches the one that is generated - 
					//If it doesn't, prompt the user
					generatedCode = generateUserCode();
					System.out.println(generatedCode);
					sentCode = true;
				}
				if(i == 1) { //if we are talkign about the first rectangle, we will switch the states to game
					Controller.switchStates(Controller.STATE.MENU);//switch to game gamestate.
				}
				if(i == 2) { 
					//Check to see if the user exists based on email or username provided then send them an email.
				}
			}
		}
		for(int i = 0; i < textboxes.length; i++) {
			if(textboxes[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) {
					inUsername = true;
					inPassword = false;
					inResetCode = false;
				}
				if(i == 1) {
					inResetCode = true;
					inPassword = false;
					inUsername = false;
					
				}
				if(i == 2) {
					inPassword = true;
					inUsername = false;
					inResetCode = false;
				}
			}
		}
		if(KeyHandler.RECENTCHAR != "") {
			if(inUsername) {
				if(username.length() < 30) {
					username += KeyHandler.RECENTCHAR;
				}
			}
			if(inResetCode && !KeyHandler.RECENTCHAR.contains(" ")) {
				if(resetCode.length() < 10) {
					resetCode += KeyHandler.RECENTCHAR;
				}
			}
			if(inPassword && !KeyHandler.RECENTCHAR.contains(" ")) {
				if(newPassword.length() < 30) {
					newPassword += KeyHandler.RECENTCHAR;
				}
			}
			KeyHandler.RECENTCHAR = "";
		}
		if(KeyHandler.BACKSPACE) {
			if(inUsername) {
				if(username != "") {
					username = username.substring(0, username.length() - 1);
				}
			}
			if(inResetCode) {
				if(resetCode != "") {
					resetCode = resetCode.substring(0, resetCode.length() - 1);
				}
			}
			if(inPassword) {
				if(newPassword != "") {
					newPassword = newPassword.substring(0, newPassword.length() - 1);
				}
			}
			KeyHandler.BACKSPACE = false;
		}
	}
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		if(sentCode) {
			g.drawImage(submit, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		}else {
			g.drawImage(sendcode, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
		}
		g.drawImage(back, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
		
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(submitWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
				if (i == 1) {	
					g.drawImage(sendCodeWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
				if (i == 2) {
					g.drawImage(backWave, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
				}
			}
		}	
		g.setFont(Controller.bigFont);
		//Draw Label Text
		g.setColor(Color.black);
		g.drawString("Username OR Email:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Username OR Email:")/2-210, 150);
		g.drawString("Reset Code:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Reset Code:")/2-170, 200);
		g.drawString("New Password:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("New Password:")/2-190, 250);
		//Draw Boxes
		
		for(int i = 0; i < textboxes.length; i++) {
			g.setColor(Color.white);
			if(inUsername && i==0) {
				g.setColor(new Color(220, 220, 220));
			}
			if(inResetCode && i==1) {
				g.setColor(new Color(220, 220, 220));
			}
			if(inPassword && i==2) {
				g.setColor(new Color(220, 220, 220));
			}
			g.fillRoundRect(textboxes[i].x, textboxes[i].y, textboxes[i].width, textboxes[i].height, 5, 5);
		}
		//Draw User Input
		g.setColor(Color.black);
		g.drawString(username, textboxes[0].x+10, textboxes[0].y+25);
		g.drawString(resetCode, textboxes[1].x+10, textboxes[1].y+25);
		g.drawString(newPassword, textboxes[2].x+10, textboxes[2].y+25);
		
		//Errors
		g.setFont(Controller.bigBold);
		g.setColor(errorColor);
		g.drawString(errorMessage, Frame.WIDTH/2-g.getFontMetrics().stringWidth(errorMessage)/2, 295);
		g.setFont(Controller.smallFont);
	}
	public void checkAvailability(){  
		if(!username.isEmpty() && !newPassword.isEmpty()) {
				ResultSet userresults = null, passresults = null;
				String query = "";
				try {
					//Create Connection
					Connection myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
					Statement statement = myConn.createStatement();
					query = "SELECT username FROM users WHERE username='" + username + "';";
//					System.out.println("Query: " + query);
					userresults = statement.executeQuery(query);
					if(userresults.next()) {
							System.out.println("Valid Username");
							query = "SELECT password FROM users WHERE username='" + username + "';";
//							System.out.println("Query: " + query);
							passresults = statement.executeQuery(query);
							if(passresults.next()) {
								if(passresults.getString("password").equals(newPassword)) {
										Controller.switchStates(Controller.STATE.GAME);
										/***
										 * TO DO:
										 * Pull user info and assign it to the Controller user class.
										 */
								}else {
									errorColor = Color.red;
									errorMessage = "Incorrect Password";
								}
							}else {
								errorColor = Color.red;
								errorMessage = "Incorrect Password";
							}
					}else {
						//System.out.println("User Does Not Exist!!");
						errorColor = Color.red;
						errorMessage = "This Username Does Not Exists!";
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
		}else {
			errorColor = Color.RED;
			errorMessage = "Username OR Password is Blank!";
		}
	}
	public String generateUserCode() {
		 String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 10) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;
	}
}
