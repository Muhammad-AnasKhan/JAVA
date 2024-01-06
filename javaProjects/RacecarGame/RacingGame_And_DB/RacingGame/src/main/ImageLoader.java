package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	//How to add resource folder to the project(where images will be stored)
	//https://techdora.com/how-to-add-eclipse-resource-folder-and-files/
	
	private BufferedImage img;
	
	public static String LOGO = "backgrounds/Logo.png";
	
// Backgrounds
	public static String titleBackground = "backgrounds/titleBG.png";
	public static String loginBackground = "backgrounds/solidBG.png";
	public static String optionsBackground = "backgrounds/solidBG.png";
	public static String submitBugBackground = "backgrounds/solidBG.png";
	
// Normal buttons
	public static String logIn = "buttonsDefault/LogIn.png";
	public static String SignUp = "buttonsDefault/signUp.png";
	public static String Options = "buttonsDefault/Options.png";
	public static String forgotPass = "buttonsDefault/forgotPass.png";
	public static String back = "buttonsDefault/back.png";
	public static String submitBug = "buttonsDefault/submitBug.png";
	public static String update = "buttonsDefault/Update.png";
	public static String submit = "buttonsDefaultsubmit.png";
	public static String careerMode = "buttonsDefault/careerMode.png";
	public static String leaderboards = "buttonsDefault/Leaderboards.png";
	public static String timeTrials = "buttonsDefault/TimeTrials.png";
	public static String logOut = "buttonsDefault/LogOut.png";
	public static String sendResetCode = "buttonsDefault/sendResetCode.png";
	
// Waved Buttons
	public static String signUpWave = "buttonsWave/signUp.png";
	public static String loginWave = "buttonsWave/LogIn.png";
	public static String optionsWave = "buttonsWave/Options.png";
	public static String forgotPassWave = "buttonsWave/forgotPass.png";
	public static String backWave = "buttonsWave/back.png";
	public static String submitBugWave = "buttonsWave/submitBug.png";
	public static String updateWave = "buttonsWave/update.png";
	public static String submitWave = "buttonsWave/submit.png";
	public static String careerModeWave = "buttonsWave/careerMode.png";
	public static String leaderboardsWave = "buttonsWave/leaderboards.png";
	public static String timeTrialsWave = "buttonsWave/timeTrials.png";
	public static String logOutWave = "buttonsWave/logOut.png";
	public static String sendResetCodeWave = "buttonsWave/sendRestCode.png";
	
// Sprites or other objects
	public static String TimeTrialsSpeed = "sprites/TimeTrialsSpeedBG.png";
	public static String GasPedalNormal = "sprites/GasNormal.png";
	public static String GasPedalPressed = "sprites/GasPressed.png";
	public static String TimeTrialRoad = "sprites/TimeTrialRoad.png";
	public static String EndingFlag = "sprites/EndingFlag.png";
	public static String treeObj = "sprites/Tree.png";
	public static String rockObj = "sprites/Rock.png";

// Car variants
	public static String userCar = "cars/UserCar.png";
	public static String enemyCar = "cars/EnemyCar.png";
	public static String userCar1 = "cars/UserCarLevel1.png";
	public static String enemyCar1 = "cars/EnemyCarLevel1.png";
	public static String userCar2 = "cars/UserCarLevel2.png";
	public static String enemyCar2 = "cars/EnemyCarLevel2.png";
	public static String userCar3 = "cars/UserCarLevel3.png";
	public static String enemyCar3 = "cars/EnemyCarLevel3.png";
	public static String userCar4 = "cars/UserCarLevel4.png";
	public static String enemyCar4 = "cars/EnemyCarLevel4.png";
	public static String userCar5 = "cars/UserCarLevel5.png";
	public static String enemyCar5 = "cars/EnemyCarLevel5.png";
	
	public ImageLoader(String path) {
		try {
			img = ImageIO.read(ImageLoader.class.getResourceAsStream(path));//Loads image as resource.

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage getImage() {
		return img;
	}
//	public BufferedImage getSubImage(int section) {
//		return img.getSubimage(0, section*25, 50, 25);
		//If we want a whole spritemap and split in sections. 
//	}
}
