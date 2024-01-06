package User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import main.Controller;

public class User {

	public static String username;
	public static int money;
	public static int racenumber;
	public static int userID;	
	public static int speed;
	
	public static void insertCareerMode(int opponentID, String time, int speed, int didWin) {
		String query = "";
		Connection myConn;
		String cdate = "";
		cdate = Controller.getDate();
		try {
			myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
			Statement statement = myConn.createStatement();
			query = "INSERT INTO career_mode_stats (users_id, career_opponent_id, userTime, speed, did_win, cdate) VALUES"
					+ "('" + userID + "', '" + opponentID + "', '" + time + "', '" + speed + "', '" + didWin + "', '" + cdate + "');";
			System.out.println(query);
			int successind = 0;
						successind = statement.executeUpdate(query);
						if(successind == 1) {
							System.out.println("Record Entered Successfuly");
						}else if(successind == 0) {
							//0 rows affected
							System.out.println("Can't Find User!!");
						}else {
							//0 rows affected
							System.out.println("Bad Error!");
						}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void insertTimeTrial(String time, int speed) {
		String query = "";
		Connection myConn;
		String cdate = "";
		cdate = Controller.getDate();
		try {
			myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
			Statement statement = myConn.createStatement();
			query = "INSERT INTO time_trials (users_id, userTime, speed, cdate) VALUES"
					+ "('" + userID + "', '" + time + "', '" + speed + "', '" + cdate + "');";
//			System.out.println("INSERT INTO time_trials (users_id, userTime, speed, cdate) VALUES ('11', '00:18:33', '143', '04/25/2021');");
//			System.out.println(query);
						int successind = 0;
						successind = statement.executeUpdate(query);
						if(successind == 1) {
							System.out.println("TIME TRIAL Entered Successfuly");
						}else if(successind == 0) {
							//0 rows affected
							System.out.println("Can't Find User!!");
						}else {
							//0 rows affected
							System.out.println("Bad Error!");
						}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void updateMoney(int money) {
		String query = "";
		Connection myConn;
		try {
			myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
			Statement statement = myConn.createStatement();
			query = "UPDATE users SET money='" + money + "' WHERE username='" + username + "';";
						int successind = 0;
						successind = statement.executeUpdate(query);
						if(successind == 1) {
							System.out.println("Money Entered Successfuly");
						}else if(successind == 0) {
							//0 rows affected
							System.out.println("Can't Find User!!");
						}else {
							//0 rows affected
							System.out.println("Bad Error!");
						}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
public static void pullInformation() {
		String query = "";
		Connection myConn;
		ResultSet dbresults = null;
		try {
			myConn = DriverManager.getConnection(Controller.dbConnection, Controller.dbUsername, Controller.dbPassword);
			Statement statement = myConn.createStatement();
			//Get Money and Racing Number
			query = "SELECT money, racing_no FROM users WHERE username='" + username + "';";
			dbresults = statement.executeQuery(query);
			if(dbresults.next()) {
				//Get the first column
				money = Integer.parseInt(dbresults.getString(1));
				racenumber = Integer.parseInt(dbresults.getString(2));
			}
			//Controller.upgradeList;
			//Get all upgrades:
			query = "SELECT * FROM user_upgrades WHERE users_id='" + userID + "ORDER BY upgrades_id, current_upgrade_level';";
			dbresults = statement.executeQuery(query);
			while(dbresults.next()) {
				//Get the first column
				int parentID = Controller.findUpgradeParent(Integer.parseInt(dbresults.getString("upgrades_id")));
				Controller.upgradeList[parentID].setPurchased(Integer.parseInt(dbresults.getString("current_upgrade_level")));
				int act = Integer.parseInt(dbresults.getString("current_upgrade_level"));
//				System.out.println("Parent: " + Controller.upgradeList[parentID].effect + ": " + Controller.upgradeList[parentID].getEffect(act-1));
				Controller.upgradeList[parentID].setActive(act-1);
//				System.out.println("ID of: " + dbresults.getString("upgrades_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
