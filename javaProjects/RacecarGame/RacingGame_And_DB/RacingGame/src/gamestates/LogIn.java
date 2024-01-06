package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.*;

import Other.Hashing;
import User.User;
import handlers.KeyHandler;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class LogIn {

	Rectangle[] buttons = {new Rectangle(Frame.WIDTH/2-105, 250, 210, 109), new Rectangle(Frame.WIDTH/2-105, 360, 210, 109),
						   new Rectangle(Frame.WIDTH/2-105, 470, 210, 109)};
	Rectangle[] textboxes = {new Rectangle(Frame.WIDTH/2-100, 125, 380, 40),
							new Rectangle(Frame.WIDTH/2-100, 175, 380, 40)};
	
	private Image background, back, login, backWave, loginWave;
//	private Image forgotPass, forgotPassWave;
	private String username = "", password = "";
	private boolean inUsername = false, inPassword = false;
	private String errorMessage = "";
	private Color errorColor = Color.red;
	public LogIn() {
		init();
	}
	public void init() {
		background = new ImageLoader(ImageLoader.loginBackground).getImage();
		back = new ImageLoader(ImageLoader.back).getImage();
		login = new ImageLoader(ImageLoader.logIn).getImage();
//		forgotPass = new ImageLoader(ImageLoader.forgotPass).getImage();
		backWave = new ImageLoader(ImageLoader.backWave).getImage();
		loginWave = new ImageLoader(ImageLoader.loginWave).getImage();
//		forgotPassWave = new ImageLoader(ImageLoader.forgotPassWave).getImage();
		
	}
	public void tick() {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) { 
					checkAvailability();
				}
				if(i == 1) { 
					//Controller.switchStates(Controller.STATE.FORGOTPASSWORD);
				}
				if(i == 2) { //if we are talking about the first rectangle, we will switch the states to game
					Controller.switchStates(Controller.STATE.MENU);//switch to game gamestate.
				}
			}
		}
		for(int i = 0; i < textboxes.length; i++) {
			if(textboxes[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) {
					inUsername = true;
					inPassword = false;
				}
				if(i == 1) {
					inPassword = true;
					inUsername = false;
				}
			}
		}
		if(KeyHandler.RECENTCHAR != "") {
			if(inUsername) {
				if(username.length() < 30) {
					username += KeyHandler.RECENTCHAR;
				}
			}
			if(inPassword && !KeyHandler.RECENTCHAR.contains(" ")) {
				if(password.length() < 30) {
					password += KeyHandler.RECENTCHAR;
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
			if(inPassword) {
				if(password != "") {
					password = password.substring(0, password.length() - 1);
				}
			}
			KeyHandler.BACKSPACE = false;
		}
	}
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.drawImage(login, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		//g.drawImage(forgotPass, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
		g.drawImage(back, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(loginWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
				if (i == 1) {	
					//g.drawImage(forgotPassWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
				if (i == 2) {	
					g.drawImage(backWave, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
				}
			}
		}	
		g.setFont(Controller.bigFont);
		//Draw Label Text
		g.setColor(Color.black);
		g.drawString("Username:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Username: ")/2-160, 150);
		g.drawString("Password:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Password: ")/2-160, 200);
		//Draw Boxes
		
		for(int i = 0; i < textboxes.length; i++) {
			g.setColor(Color.white);
			if(inUsername && i==0) {
				g.setColor(new Color(220, 220, 220));
			}
			if(inPassword && i==1) {
				g.setColor(new Color(220, 220, 220));
			}
			g.fillRoundRect(textboxes[i].x, textboxes[i].y, textboxes[i].width, textboxes[i].height, 5, 5);
		}
		//Draw User Input
		g.setColor(Color.black);
		g.drawString(username, textboxes[0].x+10, textboxes[0].y+25);
		g.drawString(password, textboxes[1].x+10, textboxes[1].y+25);
		
		//Errors
		g.setFont(Controller.bigBold);
		g.setColor(errorColor);
		g.drawString(errorMessage, Frame.WIDTH/2-g.getFontMetrics().stringWidth(errorMessage)/2, 240);
		g.setFont(Controller.smallFont);
	}
	public void checkAvailability(){  
		if(!username.isEmpty() && !password.isEmpty()) {
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
//							System.out.println("Valid Username");
							query = "SELECT password, id FROM users WHERE username='" + username + "';";
//							System.out.println("Query: " + query);
							passresults = statement.executeQuery(query);
							if(passresults.next()) {
								Hashing hashing = null;
								hashing = new Hashing();
								String passwordhash = hashing.decrypt(passresults.getString("password")); //encrypt the password
								int userID = Integer.parseInt(passresults.getString("id")); //encrypt the password
								if(passwordhash.equals(password)) {
										User.username = username;
										User.userID = userID;
										User.pullInformation();
										Controller.switchStates(Controller.STATE.GARAGE);
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
}
