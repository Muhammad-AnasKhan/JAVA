package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.*;

import Other.Hashing;
import User.AllUpgrades;
import handlers.KeyHandler;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class CreateUser {

	Rectangle[] buttons = {new Rectangle(Frame.WIDTH/2-105, 350, 210, 109), new Rectangle(Frame.WIDTH/2-105, 460, 210, 109)};
	Rectangle[] textboxes = {new Rectangle(Frame.WIDTH/2-100, 125, 380, 40),
							new Rectangle(Frame.WIDTH/2-100, 175, 380, 40),
							new Rectangle(Frame.WIDTH/2-100, 225, 380, 40),
							new Rectangle(Frame.WIDTH/2-100, 275, 380, 40)};
	
	private Image background, back, signup, backWave, signUpWave;
	private String username = "", password = "", email = "", raceno = "";
	private boolean inUsername = false, inPassword = false, inEmail = false, inRaceno = false;
	private String errorMessage = "";
	private Color errorColor = Color.red;
	private  static AllUpgrades[] upgradeList; // to create the initial upgrades when user is created
	
	public CreateUser() {
		init();
	}
	public void init() {
		background = new ImageLoader(ImageLoader.optionsBackground).getImage();
		back = new ImageLoader(ImageLoader.back).getImage();
		signup = new ImageLoader(ImageLoader.SignUp).getImage();
		backWave = new ImageLoader(ImageLoader.backWave).getImage();
		signUpWave = new ImageLoader(ImageLoader.signUpWave).getImage();
	}
	public void tick() {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) { 
					checkAvailability();
				}
				if(i == 1) { //if we are talkign about the first rectangle, we will switch the states to game
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
					inEmail = false;
					inRaceno = false;
				}
				if(i == 1) {
					inPassword = true;
					inUsername = false;
					inEmail = false;
					inRaceno = false;
				}if(i == 2) {
					inEmail = true;
					inUsername = false;
					inPassword = false;
					inRaceno = false;
				}
				if(i == 3) {
					inRaceno = true;
					inEmail = false;
					inUsername = false;
					inPassword = false;
				}
			}
		}
			if(inUsername) {
				if(username.length() < 20) {
					username += KeyHandler.RECENTCHAR;
				}
			}
			if(inPassword && !KeyHandler.RECENTCHAR.contains(" ")) {
				if(password.length() < 30) {
					password += KeyHandler.RECENTCHAR;
				}
			}
			if(inEmail && !KeyHandler.RECENTCHAR.contains(" ")) {
				if(email.length() < 30) {
				email += KeyHandler.RECENTCHAR;
				}
			}
			if(inRaceno && !KeyHandler.RECENTCHAR.isEmpty() && KeyHandler.RECENTCHAR.charAt(0) > 47 && KeyHandler.RECENTCHAR.charAt(0) < 58) {
				if(raceno.length() < 2) {
					raceno += KeyHandler.RECENTCHAR;
				}
			}
			KeyHandler.RECENTCHAR = "";
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
			if(inEmail) {
				if(email != "") {
					email = email.substring(0, email.length() - 1);
				}
			}
			if(inRaceno) {
				if(raceno != "") {
					raceno = raceno.substring(0, raceno.length() - 1);
				}
			}
			KeyHandler.BACKSPACE = false;
		}
	}
	public void render(Graphics g) {
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.drawImage(signup, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		g.drawImage(back, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(signUpWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
				if (i == 1) {	
					g.drawImage(backWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
			}
		}	
		g.setFont(Controller.bigFont);
		//Draw Label Text
		g.setColor(Color.black);
		g.drawString("Username:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Username: ")/2-160, 150);
		g.drawString("Password:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Password: ")/2-160, 200);
		g.drawString("      Email:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("      Email: ")/2-160, 250);
		g.drawString("Race Number:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Race Number:")/2-185, 300);
		//Draw Boxes
		
		for(int i = 0; i < textboxes.length; i++) {
			g.setColor(Color.white);
			if(inUsername && i==0) {
				g.setColor(new Color(220, 220, 220));
			}
			if(inPassword && i==1) {
				g.setColor(new Color(220, 220, 220));
			}
			if(inEmail && i==2) {
				g.setColor(new Color(220, 220, 220));
			}
			if(inRaceno && i==3) {
				g.setColor(new Color(220, 220, 220));
			}
			g.fillRoundRect(textboxes[i].x, textboxes[i].y, textboxes[i].width, textboxes[i].height, 5, 5);
		}
		//Draw User Input
		g.setColor(Color.black);
		g.drawString(username, textboxes[0].x+10, textboxes[0].y+25);
		g.drawString(password, textboxes[1].x+10, textboxes[1].y+25);
		g.drawString(email, textboxes[2].x+10, textboxes[2].y+25);
		g.drawString(raceno, textboxes[3].x+10, textboxes[3].y+25);
		
		//Errors
		g.setFont(Controller.bigBold);
		g.setColor(errorColor);
		g.drawString(errorMessage, Frame.WIDTH/2-g.getFontMetrics().stringWidth(errorMessage)/2, 345);
		g.setFont(Controller.smallFont);
	}
	public void checkAvailability(){
		if(!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !raceno.isEmpty()) {
			if(email.contains("@") && email.contains(".")) {
				ResultSet results = null;
				String query = "";
				try {
					//Create Connection
					Connection myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
					Statement statement = myConn.createStatement();
					query = "SELECT username FROM users WHERE username='" + username + "';";
					results = statement.executeQuery(query);
					if(results.next()) {
						//System.out.print("User Exists!!");
						errorColor = Color.red;
						errorMessage = "This Username Already Exists!";
					}else {
		//				System.out.println("User Does Not Exist!!");
						query = "SELECT email FROM users WHERE username='" + username + "';";
						results = statement.executeQuery(query);
						if(results.next()) {
							errorColor = Color.red;
							errorMessage = "This Email Is Already In Use!";
						}else {
							//https://stackoverflow.com/questions/1905607/cannot-issue-data-manipulation-statements-with-executequery
							String curdate;
							curdate = Controller.getDate();
							Hashing hashing = null;
							try {
								hashing = new Hashing();
							} catch (Exception e) {
								e.printStackTrace();
							}
							password = hashing.encrypt(password); //encrypt the password
							query = "INSERT INTO users (username, password, email, racing_no, date_created) VALUES ('" 
						+ username + "', '" + password + "', '" + email + "', '" + raceno + "', '" + curdate + "');";
							int successind = 0;
							successind = statement.executeUpdate(query);
							if(successind == 1) {
								setStartingValues(username);
								username = "";
								password = "";
								raceno = "";
								email = "";
								errorColor = Color.green;
								errorMessage = "User Has Been Created!";
							}else if(successind == 0) {
								//0 rows affected
								errorColor = Color.RED;
								errorMessage = "Uh OH! Error Entering User...";
							}else {
								//0 rows affected
								errorColor = Color.RED;
								errorMessage = "Uh OH! Error Entering User...";
							}
								
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}else {
				errorColor = Color.RED;
				errorMessage = "Must Be A Valid Email!";
			}
		}else {
			errorColor = Color.RED;
			errorMessage = "A Required Field is Blank!";
		}
	}
	public int getUserID() {
		int userID = 0;
		String query = "";
		ResultSet userresults = null;
		Connection myConn;
		try {
			myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
		Statement statement = myConn.createStatement();
		query = "SELECT id FROM users WHERE username='" + username + "';";
//		System.out.println("Query: " + query);
		userresults = statement.executeQuery(query);
			if(userresults.next()) {
				userID = Integer.parseInt(userresults.getString("id")); //encrypt the password
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userID;
	}
	public void setStartingValues(String username) {
		String query = "";
		Connection myConn;
		int userID = getUserID();
		upgradeList = Controller.upgradeList;
		
		
		query = "INSERT INTO user_upgrades (users_id, upgrades_id, upgrades_details_id, current_upgrade_level, active) VALUES "; 
		for(int i = 0; i < upgradeList.length; i++) {
			if(i != 0) {
				query += ", ";
			}
			query += "('" + userID + "', '" + upgradeList[i].id + "', '" + upgradeList[i].upgrade.getID(0) + "', '1', '1')";
		}
		query += ";";
		try {
			myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
			Statement statement = myConn.createStatement();
			int successind = 0;
			successind = statement.executeUpdate(query);
			if(successind == 1) {
				//inserting sock upgrades
			}else {
				//error in inserting sock upgrades
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
