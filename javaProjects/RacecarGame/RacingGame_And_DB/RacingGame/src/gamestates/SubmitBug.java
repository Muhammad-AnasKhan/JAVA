package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import handlers.KeyHandler;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class SubmitBug {
	//Original Size 630, 325. Divide the whole size by 3
	Rectangle[] buttons = {new Rectangle(130, 400, 210, 109), new Rectangle(370, 400, 210, 109)};
	Rectangle[] textboxes = {new Rectangle(Frame.WIDTH/2-100, 125, 380, 40),
			new Rectangle(Frame.WIDTH/2-100, 175, 380, 40),
			new Rectangle(Frame.WIDTH/2-100, 225, 380, 120)};
	private String email = "", subject = "", description = "";
	private boolean inEmail = false, inSubject = false, inDescription = false;
	private String errorMessage = "";
	private Color errorColor = Color.red;
	public SubmitBug() {
		init();//load everything upon creation
	}
	
	
	Image background, back, submit, backWave, submitWave;
	
	public void init() {
		//if we need to load/initialize anything before we render it.
		//Load All Images
		background = new ImageLoader(ImageLoader.submitBugBackground).getImage();
		back = new ImageLoader(ImageLoader.back).getImage();
		submit = new ImageLoader(ImageLoader.submit).getImage();
		backWave = new ImageLoader(ImageLoader.backWave).getImage();
		submitWave = new ImageLoader(ImageLoader.submitWave).getImage();
	}
	public void tick() {
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) { 
					Controller.switchStates(Controller.STATE.OPTIONS);//switch to game gamestate.
				}
				if(i == 1) { //if we are talking about the first rectangle, we will switch the states to game
					submitBug();
				}
			}
		}
		for(int i = 0; i < textboxes.length; i++) {
			if(textboxes[i].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				MouseHandler.MOUSEDOWN = false;
				if(i == 0) {
					inEmail = true;
					inSubject = false;
					inDescription = false;
				}
				if(i == 1) {
					inEmail = false;
					inSubject = true;
					inDescription = false;
				}if(i == 2) {
					inEmail = false;
					inSubject = false;
					inDescription = true;
				}
			}
		}
		if(KeyHandler.RECENTCHAR != "") {
			if(inEmail && !KeyHandler.RECENTCHAR.contains(" ")) {
				if(email.length() < 30) {
					email += KeyHandler.RECENTCHAR;
				}
			}
			if(inSubject) {
				if(subject.length() < 30) {
					subject += KeyHandler.RECENTCHAR;
				}
			}
			if(inDescription) {
				if(description.length() < 120) {
					description += KeyHandler.RECENTCHAR;
				}
			}
			KeyHandler.RECENTCHAR = "";
		}
		if(KeyHandler.BACKSPACE) {
			if(inEmail) {
				if(email != "") {
					email = email.substring(0, email.length() - 1);
				}
			}
			if(inSubject) {
				if(subject != "") {
					subject = subject.substring(0, subject.length() - 1);
				}
			}
			if(inDescription) {
				if(description != "") {
					description = description.substring(0, description.length() - 1);
				}
			}
			KeyHandler.BACKSPACE = false;
		}
	}
	public void render(Graphics g) {
		//display graphics.
		g.drawImage(background, 0, 0, Frame.WIDTH, Frame.HEIGHT, null);
		g.drawImage(back, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		g.drawImage(submit, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(backWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
				if (i == 1) {	
					g.drawImage(submitWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
				
			}
		}
		g.setFont(Controller.bigFont);
		//Draw Label Text
		g.setColor(Color.black);
		g.drawString("        Email:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("      Email: ")/2-180, 150);
		g.drawString("    Subject:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Subject: ")/2-180, 200);
		g.drawString("Description:", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Description: ")/2-180, 250);
		//Draw Boxes
		
		for(int i = 0; i < textboxes.length; i++) {
			g.setColor(Color.white);
			if(inEmail && i==0) {
				g.setColor(new Color(220, 220, 220));
			}
			if(inSubject && i==1) {
				g.setColor(new Color(220, 220, 220));
			}
			if(inDescription && i==2) {
				g.setColor(new Color(220, 220, 220));
			}
			g.fillRoundRect(textboxes[i].x, textboxes[i].y, textboxes[i].width, textboxes[i].height, 5, 5);
		}
		//Draw User Input
		g.setColor(Color.black);
		g.drawString(email, textboxes[0].x+10, textboxes[0].y+25);
		g.drawString(subject, textboxes[1].x+10, textboxes[1].y+25);
		//Need to space out the description if on multiple lines.
		if(description.length() < 30) {
			g.drawString(description, textboxes[2].x+10, textboxes[2].y+25);
		}else if (description.length() >= 30 && description.length() <= 60) {
			String line1 = description.substring(0, 30);
			String line2 = description.substring(30, description.length());
			g.drawString(line1, textboxes[2].x+10, textboxes[2].y+25);
			g.drawString(line2, textboxes[2].x+10, textboxes[2].y+50);
		}else if (description.length() >= 60 && description.length() <= 90) {
			String line1 = description.substring(0, 30);
			String line2 = description.substring(30, 60);
			String line3 = description.substring(60, description.length());
			g.drawString(line1, textboxes[2].x+10, textboxes[2].y+25);
			g.drawString(line2, textboxes[2].x+10, textboxes[2].y+50);
			g.drawString(line3, textboxes[2].x+10, textboxes[2].y+75);
		}else {
			String line1 = description.substring(0, 30);
			String line2 = description.substring(30, 60);
			String line3 = description.substring(60, 90);
			String line4 = description.substring(90, description.length());

			g.drawString(line1, textboxes[2].x+10, textboxes[2].y+25);
			g.drawString(line2, textboxes[2].x+10, textboxes[2].y+50);
			g.drawString(line3, textboxes[2].x+10, textboxes[2].y+75);
			g.drawString(line4, textboxes[2].x+10, textboxes[2].y+100);
		}
		
		//Errors
		g.setFont(Controller.bigBold);
		g.setColor(errorColor);
		g.drawString(errorMessage, Frame.WIDTH/2-g.getFontMetrics().stringWidth(errorMessage)/2, 380);
		g.setFont(Controller.smallFont);
	}
	public void submitBug() {
		System.out.println("Submitting Bug");
		if(!email.isEmpty() && !subject.isEmpty() && !description.isEmpty()) {
			if(email.contains("@") && email.contains(".")) {
				ResultSet results = null;
				String query = "";
				try {
					//Create Connection
					Connection myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
					Statement statement = myConn.createStatement();
					query = "SELECT email FROM users WHERE email='" + email + "';";
					results = statement.executeQuery(query);
					if(!results.next()) {
						//System.out.print("Email Exists!!");
						errorColor = Color.red;
						errorMessage = "This Email Does Not Exist!";
					}else {
		//				System.out.println("Email Does Exist, Submitting Bug");
						String curdate = Controller.getDate();
						query = "INSERT INTO bugs (player_email, bug_subject, bug_description, status, date_submitted) VALUES ('" 
								+ email + "', '" + subject + "', '" + description + "', '" + "0" + "', '" + curdate + "');";
									int successind = 0;
									successind = statement.executeUpdate(query);
									if(successind == 1) {
										errorColor = Color.green;
										errorMessage = "Bug Has Been Created! Thank You for Helping!";
										email = "";
										subject = "";
										description = "";
									}else if(successind == 0) {
										//0 rows affected
										errorColor = Color.RED;
										errorMessage = "Uh OH! Error Entering User...";
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
}
