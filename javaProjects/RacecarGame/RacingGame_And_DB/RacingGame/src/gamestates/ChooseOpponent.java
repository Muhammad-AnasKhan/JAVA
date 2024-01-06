package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import User.Opponents;
import User.User;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class ChooseOpponent {
	
	public Rectangle[] buttons = {new Rectangle(10, 5, 125, 75)};
	public Opponents[] opponents = {};
	private Image background, back, backWave;
	
	public ChooseOpponent() {
		init();
	}
	public void init() {
		gatherOpponents();
		Controller.opponents = opponents;
		background = new ImageLoader(ImageLoader.optionsBackground).getImage();
		back = new ImageLoader(ImageLoader.back).getImage();
		backWave = new ImageLoader(ImageLoader.backWave).getImage();
		Controller.scrollLmt = (150*opponents.length);
	}
	
	public void tick() {
		for(int i = 0; i < opponents.length; i++) { 
			opponents[i].y = (150*(i+1))+Controller.scrollAmt;
			opponents[i].tick();
			if(MouseHandler.MOUSEDOWN && opponents[i].bounds.contains(Controller.mousePoint)){
				Controller.opponents = opponents;
				Controller.switchToCareerMode(i);
			}
		}
		for(int i = 0; i < buttons.length; i++) {
			if(MouseHandler.MOUSEDOWN && buttons[i].contains(Controller.mousePoint)){
				if(i == 0) {
					Controller.switchStates(Controller.STATE.GARAGE);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		g.drawImage(background, 0, 0, Frame.WIDTH,Frame.HEIGHT, null);
		
		for(int i = 0; i < opponents.length; i++) { 
			opponents[i].render(g);
		}
		g.setColor(Color.black);
		g.fillRect(0, 0, Frame.WIDTH, 87);
		g.setColor(Color.gray);
		g.fillRect(0, 0, Frame.WIDTH, 85);
		g.setColor(Color.WHITE);
		g.setFont(Controller.bigBold);
		g.drawString("Choose an Opponent!", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Choose an Opponent!")/2, 40);
		g.drawImage(back, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		g.setFont(Controller.smallFont);
		for (int i = 0; i < buttons.length; i++) {	
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(backWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
			}
		}
	}
	
	public void gatherOpponents() {
		String query = "", query2 = "";
		Connection myConn;
		ResultSet dbresults = null, recordresults = null;
		try {
			myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
			Statement statement = myConn.createStatement();
			//Get Money and Racing Number
			query = "SELECT COUNT(*) FROM career_opponent;";
			
			dbresults = statement.executeQuery(query);
			if(dbresults.next()) {
				//Get the first column
				opponents = new Opponents[Integer.parseInt(dbresults.getString("COUNT(*)"))];
				query = "SELECT * FROM career_opponent ORDER BY bracket, speed;";
				dbresults = statement.executeQuery(query);
				int count = 0;
				while(dbresults.next()) {
					String name = dbresults.getString("name");
					String descr = dbresults.getString("description");
					int opponentID = Integer.parseInt(dbresults.getString("id"));
					int speed = Integer.parseInt(dbresults.getString("speed"));
					int bracket = Integer.parseInt(dbresults.getString("bracket"));
					int money = Integer.parseInt(dbresults.getString("money"));
					int wonRecord = 0;
					int totalRecords = 0;
					int topSpeed = 0;
					String bestTime = "99:99:99";
					Statement statement2 = myConn.createStatement();
					query2 = "SELECT * FROM career_mode_stats WHERE users_id='" + User.userID + "' AND career_opponent_id='" + opponentID + "';";
					recordresults = statement2.executeQuery(query2);
					while(recordresults.next()) {
						if(Integer.parseInt(recordresults.getString("did_win")) == 1){
							wonRecord++;
						}
						if(topSpeed < Integer.parseInt(recordresults.getString("speed"))) {
							topSpeed = Integer.parseInt(recordresults.getString("speed"));
						}
						if(bestTime.compareTo(recordresults.getString("userTime")) != -1) {
							bestTime = recordresults.getString("userTime");
						}
						totalRecords++;
					}
					if(bestTime.equals("99:99:99")) {
						bestTime = "00:00:00";
					}
					opponents[count] = new Opponents(opponentID, name, descr, (150*(count+1)), speed, money, bracket, wonRecord, totalRecords-wonRecord, topSpeed, bestTime);
					count++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
