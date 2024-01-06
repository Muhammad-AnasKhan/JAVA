package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import User.TimeTrialRecords;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class Leaderboards {

	private int rows = 10;
	private Image back, timeTrials, careerMode, backWave, timeTrialsWave, careerModeWave;
	private TimeTrialRecords[] records;
	
	private boolean showTimeTrials = true;
	private String mode = "time_trials";
	private int totalRecords;
//	private int userrank;
	private Rectangle[] buttons = {new Rectangle(25, 610, 180, 95), new Rectangle(300, 610, 180, 95), 
			new Rectangle(300, 610, 180, 95)};
	
	public Leaderboards(){
		init();
	}
	
	public void init() {
		records = new TimeTrialRecords[rows];
		back = new ImageLoader(ImageLoader.back).getImage();
		timeTrials = new ImageLoader(ImageLoader.timeTrials).getImage();
		careerMode = new ImageLoader(ImageLoader.careerMode).getImage();
		backWave = new ImageLoader(ImageLoader.backWave).getImage();
		timeTrialsWave = new ImageLoader(ImageLoader.timeTrialsWave).getImage();
		careerModeWave = new ImageLoader(ImageLoader.careerModeWave).getImage();
		pullData();
	}
	
	public void tick() {
		for(int i = 0; i < buttons.length; i++) {
			if(MouseHandler.MOUSEDOWN && buttons[i].contains(Controller.mousePoint)){
				if(i == 0) {
					//Go Back to Garage Mode
					Controller.switchStates(Controller.STATE.GARAGE);
				}
				if(i == 1) { // have to do this way because the boundaries are on top. It always chooses one before 2.
					if(showTimeTrials) {
						showTimeTrials = false;
						mode = "career_mode_stats";
						MouseHandler.MOUSEDOWN = false;
					}else {
						showTimeTrials = true;
						mode = "time_trials";
						MouseHandler.MOUSEDOWN = false;
					}
					records = new TimeTrialRecords[rows];
					pullData();
				}			
			}
		}
	}
	public void render(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		g.setColor(Color.black);
		g.setFont(Controller.bigBold);
		if(showTimeTrials) {
			g.drawString("Time Trials!", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Time Trials!")/2, 50);
			g.drawImage(careerMode, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
		}else {
			g.drawString("Career Mode!", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Career Mode!")/2, 50);
			g.drawImage(timeTrials, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
		}
		g.drawLine(300, 60, 450, 60);
		g.setFont(Controller.smallFont);
		g.drawString("Rank   |        Username        |   Racing No.   |   Top Speed   |   Best Time   |       Date   ", 50, 90);
		g.drawImage(back, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		
		for(int i = 0; i < records.length; i++) {
			if(records[i] != null) {
				g.drawString("" + (i+1), 65, 125+50*i);
				g.drawString("" + records[i].username, 185-g.getFontMetrics().stringWidth(records[i].username)/2, 125+50*i);
				g.drawString("" + records[i].racingno, 315-g.getFontMetrics().stringWidth(records[i].racingno + "")/2, 125+50*i);
				g.drawString("" + records[i].speed, 435-g.getFontMetrics().stringWidth(records[i].speed + "")/2, 125+50*i);
				g.drawString("" + records[i].userTime, 545-g.getFontMetrics().stringWidth(records[i].userTime + "")/2, 125+50*i);
				g.drawString("" + records[i].cdate, 650-g.getFontMetrics().stringWidth(records[i].cdate + "")/2, 125+50*i);
				g.drawLine(40, 125+50*i+10, 700, 125+50*i+10);
			}
		}
//		g.drawString("Your Rank: " + userrank + " out of " + totalRecords, 510, 650);
		g.drawString("Total Overall Races: " + totalRecords, 510, 650);
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(backWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
				if (i == 1 && showTimeTrials) {	
					g.drawImage(careerModeWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
				if (i == 1 && !showTimeTrials) {
					g.drawImage(timeTrialsWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
			}
		}
	}
	
	public void pullData() {
			ResultSet userresults = null, nameresults = null;
			String query = "";
			try {
				//Create Connection
				Connection myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
				Statement statement = myConn.createStatement();
				Statement statement2 = myConn.createStatement();
				query = "SELECT * FROM " + mode + " ORDER BY userTime limit " + rows +";";
				userresults = statement.executeQuery(query);
				int count = 0;
				while(userresults.next()) {
					int id = Integer.parseInt(userresults.getString("id"));
					int userID = Integer.parseInt(userresults.getString("users_id"));
					String userTime = userresults.getString("userTime");
					String cdate = userresults.getString("cdate"); 
					int speed = Integer.parseInt(userresults.getString("speed"));
					records[count] = new TimeTrialRecords(id, userID, userTime, speed, cdate);
					nameresults = statement2.executeQuery("SELECT username, racing_no FROM users WHERE id=" + records[count].users_id +";");
					if(nameresults.next()) {
						records[count].username = nameresults.getString("username");
						records[count].racingno = Integer.parseInt(nameresults.getString("racing_no"));
//						System.out.println("Setting Username: " + records[count].username + ", RN: " + records[count].racingno);
					}
					count++;
				}
				query = "SELECT COUNT(*) FROM " + mode + ";";
				userresults = statement.executeQuery(query);
				if(userresults.next()) {
					totalRecords = Integer.parseInt(userresults.getString("COUNT(*)"));
				}
//				query = "SELECT COUNT(*) FROM " + mode + ";";
//				userresults = statement.executeQuery(query);
//				if(userresults.next()) {
//					totalRecords = Integer.parseInt(userresults.getString("COUNT(*)"));
//				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
	}
}
