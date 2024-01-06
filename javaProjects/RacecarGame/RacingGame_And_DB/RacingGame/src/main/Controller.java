package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import User.AllUpgrades;
import User.Opponents;
import User.User;
import gamestates.CareerMode;
import gamestates.ChooseOpponent;
import gamestates.CreateUser;
import gamestates.ForgotPassword;
import gamestates.Garage;
import gamestates.Leaderboards;
import gamestates.LogIn;
import gamestates.MainMenu;
import gamestates.Options;
import gamestates.SubmitBug;
import gamestates.TimeTrials;
import gamestates.WinScreen;
import handlers.KeyHandler;
import handlers.MouseHandler;

public class Controller extends JPanel implements Runnable {
	
	public enum STATE{	//ENUM that explains easier what gamestate we are using
		MENU,
		CREATEUSER,
		LOGIN,
		GARAGE,
		GAME,
		LEADERBOARDS,
		WINSCREEN,
		OPTIONS,
		SUBMITBUG,
		TIMETRIALS,
		FORGOTPASSWORD,
		CAREER,
		CHOOSEOPPONENT;
	}

	private Thread thread;
	private Graphics2D g;
	private BufferedImage image;
	
	private static STATE state = STATE.MENU;// default the gamestate to menu
	private static Garage garage;
	private static LogIn login;
	private static CreateUser createUser;
	private static WinScreen winScreen;
	private static MainMenu menu;
	private static CareerMode careerMode;
	private static ChooseOpponent chooseOpponent;
	private static TimeTrials timeTrials;
	private static Options options;
	private static SubmitBug submitBug;
	private static ForgotPassword forgotPassword;
	private static Leaderboards leaderboards;
	public  static AllUpgrades[] upgradeList;
	public  static Opponents[] opponents; 
	public  static User user;
	
	public static Point mousePoint = new Point(0, 0); //Keep track of where the mouse pointer is.
	public static Font bigFont = new Font("TimesRoman", Font.PLAIN, 25);  //Different fonts if we so choose.
	public static Font bigBold = new Font("TimesRoman", Font.BOLD, 25);  //Different fonts if we so choose.
	public static Font smallFont = new Font("TimesRoman", Font.PLAIN, 18);
	
	//jdbc:mysql://mywebsite.com:3306/mywebsite_dbname     OR   localhost if using local machine
	public static String dbConnection = "jdbc:mysql://mywebsite.com:3306/mywebsite_dbname";
	public static String dbUsername = "myusername";
	public static String dbPassword = "mypassword";
	
	private boolean running = true; //for thread
	private static final long serialVersionUID = 1L;
	private long lastTime;
	@SuppressWarnings("unused")
	private double fps;
	private String version;
	//For FPS
	
	public static int scrollAmt = 0;
	public static int scrollLmt = 0;
	
	public Controller() {
		super();
		setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT));
		setFocusable(true);
		requestFocus(true);
		//Stuff to set up the frame still
	}
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		//Create a new thread to run the game
	}
	private void init() {
		image = new BufferedImage(Frame.WIDTH, Frame.HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics(); //Create/Initialize a new image.
		Controller.switchStates(Controller.STATE.MENU);
		this.addKeyListener(new KeyHandler());
		this.addMouseListener(new MouseHandler());
		this.addMouseMotionListener(new MouseHandler());
		this.addMouseWheelListener(new MouseHandler());
		//Create Mouse and Key Listeners which will be recording user input.
		menu = new MainMenu();
		pullUpgrades();
	}

	public void run() { // Our running function. 
		init();
		while (running) {
			lastTime = System.nanoTime();
			display();
			try {
				Thread.sleep(10);
				//Pause the game every 10ms.
			} catch (InterruptedException e) {

			}
			fps = 1000000000.0 / (System.nanoTime() - lastTime); //Frames per second based on how fast machine is.
			lastTime = System.nanoTime();
		}
	}

	private void display() { //This is what will make the game run/calculate(tick function) & display graphics(render function)
		switch(state) {
		case MENU:
			menu.tick();
			menu.render(g);
			break;
		case GARAGE:
			garage.tick();
			garage.render(g);
			break;
		case TIMETRIALS:
			timeTrials.tick();
			timeTrials.render(g);
			break;
		case CAREER:
			careerMode.tick();
			careerMode.render(g);
			break;
		case LEADERBOARDS:
			leaderboards.tick();
			leaderboards.render(g);
			break;
		case CREATEUSER:
			createUser.tick();
			createUser.render(g);
			break;
		case LOGIN:
			login.tick();
			login.render(g);
			break;
		case CHOOSEOPPONENT:
			chooseOpponent.tick();
			chooseOpponent.render(g);
			break;
		case WINSCREEN:
			winScreen.tick();
			winScreen.render(g);
			break;
		case OPTIONS:
			options.tick();
			options.render(g);
			break;
		case SUBMITBUG:
			submitBug.tick();
			submitBug.render(g);
			break;
		case FORGOTPASSWORD:
			forgotPassword.tick();
			forgotPassword.render(g);
			break;
		default:
			break;
	}
		g.setColor(Color.white);
		g.drawString("Version " + version, Frame.WIDTH/2-g.getFontMetrics().stringWidth("Version: " + version)/2, Frame.HEIGHT-25);
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		//Draw the graphics that are displayed for whatever gamestate is currently active.
	}
	
	public static void switchStates(STATE state) { // Helps us switch gamestates for example to a win screen after we win.
		scrollAmt = 0;
		scrollLmt = 0;
		Controller.state = state;
		if(state == STATE.MENU) {
			menu = new MainMenu();
		}else if(state == STATE.CREATEUSER) {
			createUser = new CreateUser();
		}else if(state == STATE.LOGIN) {
			login = new LogIn();
		}else if(state == STATE.WINSCREEN) {
			winScreen = new WinScreen();
		}else if(state == STATE.CAREER) {
			careerMode = new CareerMode(1);
		}else if(state == STATE.OPTIONS) {
			options = new Options();
		}else if(state == STATE.SUBMITBUG) {
			submitBug = new SubmitBug();
		}else if(state == STATE.FORGOTPASSWORD) {
			forgotPassword = new ForgotPassword();
		}else if(state == STATE.GARAGE) {
			garage = new Garage();
		}else if(state == STATE.LEADERBOARDS) {
			leaderboards = new Leaderboards();
		}else if(state == STATE.TIMETRIALS) {
			timeTrials = new TimeTrials();
		}else if(state == STATE.CHOOSEOPPONENT) {
			chooseOpponent = new ChooseOpponent();
		}
		//Once we switch, restart the file from the beginning and initiallize it again.
	}
	public static void switchToCareerMode(int opponentID) { 
		scrollAmt = 0;
		scrollLmt = 0;
		Controller.state = STATE.CAREER;
		careerMode = new CareerMode(opponentID);
	}
	public static String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		   LocalDateTime now = LocalDateTime.now();
		   return dtf.format(now);
	}
	private static void pullUpgrades() {
		//Pull all upgrades from the database and display them.
				String query = "";
				String itemQuery = "";
				Connection myConn;
				ResultSet dbresults = null;
				ResultSet upgrade_details = null;
				try {
					myConn = DriverManager.getConnection("http://localhost/phpmyadmin/index.php?route=/database/structure&server=1&db=mysite_multiplayergame", "root", "");
					Statement statement = myConn.createStatement();
					Statement statement2 = myConn.createStatement();
					query = "SELECT count(*) FROM upgrades WHERE active=1;";
					int upgradeCount = 0;
					dbresults = statement.executeQuery(query);
					if(dbresults.next()) {
						//Get the first column
						upgradeCount = Integer.parseInt(dbresults.getString(1));
						Controller.scrollLmt = upgradeCount*15;
						upgradeList = new AllUpgrades[upgradeCount];
					}
					query = "SELECT * FROM upgrades WHERE active=1;";
					dbresults = statement.executeQuery(query);
					int count = 0;
					while(dbresults.next()) {
							int id = Integer.parseInt(dbresults.getString("id"));
							String effect = dbresults.getString("effect_name");
							upgradeList[count] = new AllUpgrades(id, effect);
							itemQuery = "SELECT * FROM upgrades_details WHERE upgrades_id='" + id + "';";
							upgrade_details = statement2.executeQuery(itemQuery);
							while(upgrade_details.next()) {
								for(int i = 1; i < 6; i++) {
									String detail_effect = upgrade_details.getString("lvl"+i+"_effect");
									int detail_amount = Integer.parseInt(upgrade_details.getString("lvl"+i+"_amount"));
									int detail_cost = Integer.parseInt(upgrade_details.getString("lvl"+i+"_cost"));
									upgradeList[count].addUpgrade(i-1, detail_effect, detail_amount, detail_cost, id);
//									System.out.println("Adding Effect: " + detail_effect);	
//									System.out.println("   Adding Cost: " + detail_cost);
//									System.out.println("   Adding Amt: " + detail_amount);
								}
							}
							count++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}
	public static int findUpgradeParent(int upgradeID) {
		for(int i = 0; i < upgradeList.length; i++) {
			if(upgradeList[i].id == upgradeID) {
				return i;
			}
		}
		return 0;
	}
	public static void resetUserUpgrades() {
		if(upgradeList != null) {
			for(int i = 0; i < upgradeList.length; i++) {
				upgradeList[i].resetUpgrades();
			}
		}
	}
	public static String TimeConversion(long time) {
		String raceTime = String.format("%02d:%02d:%02d", 
		TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
		TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)),
		TimeUnit.MILLISECONDS.toMillis(time) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(time))
		);
		raceTime = raceTime.substring(0, 8);
		return raceTime;
	}
}
