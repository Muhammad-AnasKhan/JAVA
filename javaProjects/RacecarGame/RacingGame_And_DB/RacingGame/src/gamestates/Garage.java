package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import User.AllUpgrades;
import User.User;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class Garage {

	private AllUpgrades[] upgradeList = {};

	private Rectangle[] buttons = {new Rectangle(25, 610, 180, 115), new Rectangle(200, 610, 180, 115),
			new Rectangle(375, 610, 180, 115), new Rectangle(550, 610, 180, 115)};
	
	private int money = 0;
	private int speed = 0;
	private Color purchased = new Color(0, 100, 0);
	private Image careerMode, timeTrials, leaderboards, logOut, careerModeWave, timeTrialsWave, leaderboardsWave, logOutWave;
	
	public Garage() {
		init();
	}
	public void init() {
		pullUpgrades();
		pullUserInfo();
		careerMode = new ImageLoader(ImageLoader.careerMode).getImage();
		timeTrials = new ImageLoader(ImageLoader.timeTrials).getImage();
		leaderboards = new ImageLoader(ImageLoader.leaderboards).getImage();
		logOut = new ImageLoader(ImageLoader.logOut).getImage();
		careerModeWave = new ImageLoader(ImageLoader.careerModeWave).getImage();
		timeTrialsWave = new ImageLoader(ImageLoader.timeTrialsWave).getImage();
		leaderboardsWave = new ImageLoader(ImageLoader.leaderboardsWave).getImage();
		logOutWave = new ImageLoader(ImageLoader.logOutWave).getImage();
		Controller.scrollLmt = (50*upgradeList.length)-(0);
		getSpeed();
	}
	public void tick() {
		for(int i = 0; i < upgradeList.length; i++) {
			for(int j = 0; j < upgradeList[i].upgrade.returnHighestLevel(); j++) {
				upgradeList[i].updateBounds(j, ((j)*xspacing)+15,(yspacing*(i))+100+Controller.scrollAmt);
				if(upgradeList[i].upgrade.bounds[j].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
					if(!upgradeList[i].upgrade.getPurchased(j) && money >= upgradeList[i].upgrade.getCost(j)) {
							if(upgradeList[i].upgrade.bounds[j].y + upgradeList[i].upgrade.bounds[j].height > 70
									&& upgradeList[i].upgrade.bounds[j].y + upgradeList[i].upgrade.bounds[j].height < 570) {
								//so its out of the top/bottom boundaries so the user can see
							
							for(int k = 0; k < j; k++) {
								if(!upgradeList[i].upgrade.getPurchased(k)) {
									purchaseUpgrade(upgradeList[i].id, upgradeList[i].getID(k), i, k, true);
								}
							}
							purchaseUpgrade(upgradeList[i].id, upgradeList[i].getID(j), i, j, false);
							System.out.println("Setting: " + upgradeList[i].getEffect(j) + " Active");
							upgradeList[i].setActive(j);
							getSpeed();
						}
					}else {
						if(upgradeList[i].upgrade.getPurchased(j)) {
							upgradeList[i].setActive(j);
							
							System.out.println("Setting: " + upgradeList[i].getEffect(j) + " Active 2");
							getSpeed();
						}
					}
					MouseHandler.MOUSEDOWN = false;
				}
			}
		}
		for(int i = 0; i < buttons.length; i++) {
			if(MouseHandler.MOUSEDOWN && buttons[i].contains(Controller.mousePoint)){
				if(i == 0) {
					//Go to Career Mode
					Controller.switchStates(Controller.STATE.CHOOSEOPPONENT);
				}
				if(i == 1) {
					//Go to Time Trials Mode
					Controller.switchStates(Controller.STATE.TIMETRIALS);
				}
				if(i == 2) {
					//Go to Leaderboards
					Controller.switchStates(Controller.STATE.LEADERBOARDS);
				}
				if(i == 3) {
					//Log Out
					Controller.switchStates(Controller.STATE.MENU);
				}
			}
		}
	}
	int xspacing = 140;
	int yspacing = 115;
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		g.setColor(Color.white);
		
		for(int i = 0; i < upgradeList.length; i++) {
			g.drawString(upgradeList[i].effect, 30, yspacing*(i+1)-25 + Controller.scrollAmt);
			for(int j = 0; j < upgradeList[i].upgrade.returnHighestLevel(); j++) {
				if(upgradeList[i].getPurchased(j)) {
					g.setColor(purchased);
					g.fillRect(upgradeList[i].upgrade.bounds[j].x, 
							upgradeList[i].upgrade.bounds[j].y, upgradeList[i].upgrade.bounds[j].width, upgradeList[i].upgrade.bounds[j].height);
					g.setColor(Color.WHITE);
				}
				if(upgradeList[i].upgrade.getActive() == j) {
					g.setColor(Color.RED);
					g.fillRect(upgradeList[i].upgrade.bounds[j].x, 
							upgradeList[i].upgrade.bounds[j].y, upgradeList[i].upgrade.bounds[j].width, upgradeList[i].upgrade.bounds[j].height);
					g.setColor(Color.WHITE);
				}
				if(upgradeList[i].upgrade.bounds[j].contains(Controller.mousePoint)) {
					g.setColor(Color.CYAN);
					g.fillRect(upgradeList[i].upgrade.bounds[j].x, 
							upgradeList[i].upgrade.bounds[j].y, upgradeList[i].upgrade.bounds[j].width, upgradeList[i].upgrade.bounds[j].height);
					g.setColor(Color.WHITE);
				}
				g.drawString(upgradeList[i].getEffect(j), upgradeList[i].upgrade.bounds[j].x+5, upgradeList[i].upgrade.bounds[j].y+20);
				g.drawString("Cost: " + upgradeList[i].getCost(j), upgradeList[i].upgrade.bounds[j].x+5, upgradeList[i].upgrade.bounds[j].y+40);
				g.drawString("Incr. Amount: " + upgradeList[i].getAmount(j), upgradeList[i].upgrade.bounds[j].x+5, upgradeList[i].upgrade.bounds[j].y+60);
				g.drawRect(upgradeList[i].upgrade.bounds[j].x, 
						upgradeList[i].upgrade.bounds[j].y, upgradeList[i].upgrade.bounds[j].width, upgradeList[i].upgrade.bounds[j].height);
			}
			g.setColor(Color.BLACK);
			g.fillRect(0, upgradeList[i].upgrade.bounds[0].y + upgradeList[i].upgrade.bounds[0].height + 10, Frame.WIDTH, 2);
			g.setColor(Color.WHITE);
		}
		g.setColor(Color.black);
		g.fillRect(0, 0, Frame.WIDTH, 52);
		g.fillRect(0, 598, Frame.WIDTH, 200);
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, Frame.WIDTH, 50);
		g.setColor(Color.black);
		
		g.drawString("Money: $" + money, 15, 20);
		g.drawString("Speed: " + speed, 15, 40);
		g.drawString("User: " + User.username, Frame.WIDTH-50-g.getFontMetrics().stringWidth(User.username), 20);
		g.drawString("Race No: " + User.racenumber, Frame.WIDTH-80-g.getFontMetrics().stringWidth("" + User.racenumber), 40);
		g.setFont(Controller.bigBold);
		g.drawString("Garage", Frame.WIDTH/2-10-g.getFontMetrics().stringWidth("Garage")/2, 30);
		g.setFont(Controller.smallFont);
		g.setColor(Color.ORANGE);
		g.fillRect(0, 600, Frame.WIDTH, 200);
		
		g.drawImage(careerMode, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
		g.drawImage(timeTrials, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
		g.drawImage(leaderboards, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
		g.drawImage(logOut, buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height, null);
		for(int i = 0; i < buttons.length; i++) {
			if(buttons[i].contains(Controller.mousePoint)) { // if Mouse is in box 1
				if (i == 0) {	
					g.drawImage(careerModeWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				}
				if (i == 1) {	
					g.drawImage(timeTrialsWave, buttons[1].x, buttons[1].y, buttons[1].width, buttons[1].height, null);
				}
				if (i == 2) {	
					g.drawImage(leaderboardsWave, buttons[2].x, buttons[2].y, buttons[2].width, buttons[2].height, null);
				}
				if (i == 3) {	
					g.drawImage(logOutWave, buttons[3].x, buttons[3].y, buttons[3].width, buttons[3].height, null);
				}
			}
		}
		
	}
	public void pullUpgrades() {
		upgradeList = Controller.upgradeList;
//		for(int i = 0; i < upgradeList.length; i++) {
//			System.out.println(upgradeList[i].effect);
//		}
	}
	public void pullUserInfo() {
		User.pullInformation();
		money = User.money;
	}
	private void getSpeed() {
		speed = 0;
		for(int i = 0; i < upgradeList.length; i++) {
			if(upgradeList[i].upgrade.getActive() != -1) {
				speed += upgradeList[i].upgrade.getAmount(upgradeList[i].upgrade.getActive());
			}
		}
		User.speed = speed;
	}
	public void purchaseUpgrade(int upgradeParent, int upgradeChild, int parentlevel, int level, boolean isFree) {
		if(!isFree) {
			money -= upgradeList[parentlevel].upgrade.getCost(level);
			User.money = money;
			User.updateMoney(money);
		}
		String query = "";
		Connection myConn;
		try {
			myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
			Statement statement = myConn.createStatement();
			query = "INSERT INTO user_upgrades "
					+ "(users_id, upgrades_id, upgrades_details_id, current_upgrade_level, active) "
					+ "VALUES "
					+ "("+ User.userID + ", " + upgradeParent + ", " + upgradeChild + ", " + (level+1) + ", " + "1" + ");";
			int successind = 0;
			successind = statement.executeUpdate(query);
			if(successind == 1) {
				System.out.println("Purchased!");
//				errorColor = Color.green;
//				errorMessage = "User Has Been Created!";
			}else if(successind == 0) {
				//0 rows affected
//				errorColor = Color.RED;
//				errorMessage = "Uh OH! Error Entering User...";
			}else {
				//0 rows affected
//				errorColor = Color.RED;
//				errorMessage = "Uh OH! Error Entering User...";
			}
			Controller.upgradeList[parentlevel].setPurchased(level+1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
