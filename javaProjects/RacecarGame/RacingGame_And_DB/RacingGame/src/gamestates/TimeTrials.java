package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import User.User;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class TimeTrials {
	
	private Rectangle[] gasPedals = {new Rectangle(550, 485, 170, 190), new Rectangle(550, 485, 170, 190)};
	private Rectangle backButtonBox = new Rectangle(280, 375, 200, 125);
	private Decoration[] dec;
	
	Color partRed, partYellow, partGreen;
	Color bgcolor = new Color(50, 168, 82);
	Image speedBG, gasNormal, gasPressed, road, userCar, finishLineImg, back, backWave;

	boolean onGas = false;
	boolean isPlaying = false;
	boolean hasEnded = false;
	boolean showNumber = false;
	int xNumPos = 0;
	int yNumPos = 0;
	
	int finishLineDistance = 100000;
	int stoptime = 0;
	long currentTime = 0;
	String currentTimeConverted = "";
	int topSpeed = 0;
	int roadPos = 0;
	int currentSpeed = 0;
	int shiftTime = 3;//If it is red, yellow, or green
	
    long startTime;
    long stopTime;
    long elapsedTime;
    
	public TimeTrials() {
		init();
		speedBG = new ImageLoader(ImageLoader.TimeTrialsSpeed).getImage();
		gasNormal = new ImageLoader(ImageLoader.GasPedalNormal).getImage();
		gasPressed = new ImageLoader(ImageLoader.GasPedalPressed).getImage();
		road = new ImageLoader(ImageLoader.TimeTrialRoad).getImage();
		finishLineImg = new ImageLoader(ImageLoader.EndingFlag).getImage();
		back = new ImageLoader(ImageLoader.back).getImage();
		backWave = new ImageLoader(ImageLoader.backWave).getImage();
		if(User.speed < 10) {
			userCar = new ImageLoader(ImageLoader.userCar1).getImage();
		}else if(User.speed >= 10 && User.speed < 18) {
			userCar = new ImageLoader(ImageLoader.userCar2).getImage();
			xNumPos = 95;
			yNumPos = 305;
			showNumber = true;
		}else if(User.speed >= 18 && User.speed < 24) {
			userCar = new ImageLoader(ImageLoader.userCar3).getImage();
			xNumPos = 110;
			yNumPos = 295;
			showNumber = true;
		}else if(User.speed >= 24 && User.speed < 32) {
			userCar = new ImageLoader(ImageLoader.userCar4).getImage();
			xNumPos = 77;
			yNumPos = 298;
			showNumber = true;
		}else {
			userCar = new ImageLoader(ImageLoader.userCar5).getImage();
			xNumPos = 88;
			yNumPos = 325;
			showNumber = true;
		}
	}
	public void init() {
		partRed = new Color(255, 0, 0, 100);
		partYellow = new Color(255, 255, 0, 100);
		partGreen = new Color(0, 255, 0, 100);
		dec = new Decoration[10]; //up to 10 different objects;
	}
	public void tick() {
		if(!hasEnded) {
			if(roadPos <= -Frame.WIDTH) {
				roadPos = -52;
			}
			roadPos-= currentSpeed;
			finishLineDistance -= currentSpeed;
			currentTimeConverted = Controller.TimeConversion(currentTime);
			for(int i = 0; i < gasPedals.length; i++) {
				if(gasPedals[i].contains(Controller.mousePoint) && MouseHandler.hasPressed) {
					onGas = true;
					if(i == 0) {
						if(!isPlaying) {
							isPlaying = true;
							timer(true);
							currentSpeed = User.speed;
							shiftTime = 1;
						}
						if(isPlaying) {
							if(stoptime >= 0 && stoptime < 400) {
								shiftTime = 1;
	//							System.out.println("Bad Shift - Early");
								if(currentSpeed- Math.round(User.speed/2) >= 0) {
									currentSpeed -= Math.round(User.speed/2);
								}
								stoptime = 0;
							}
							if(stoptime >= 400 && stoptime < 600) {
								shiftTime = 2;
	//							System.out.println("Decent Shift");
								Random rand = new Random();
								currentSpeed += User.speed+rand.nextInt(User.speed);
								stoptime = 0;
							}
							if(stoptime >= 600 && stoptime < 700) {
								shiftTime = 3;
	//							System.out.println("Great Shift");
								Random rand = new Random();
								currentSpeed += User.speed+(rand.nextInt(User.speed));
								stoptime = 0;
							}
							if(topSpeed < currentSpeed) {
								topSpeed = currentSpeed;
							}
							MouseHandler.hasPressed = false;
						}
					}
				}
			}
			if(!hasEnded && isPlaying) {
				if(stoptime >= 700) {
					if(currentSpeed- Math.round(User.speed/2) >= 0) {
						currentSpeed -= Math.round(User.speed/2);
					}
					shiftTime = 1;
					stoptime = 0;
				}
			}
			if(!MouseHandler.MOUSEDOWN) {
					onGas = false;
			}
			if(isPlaying) {
				Random rand = new Random();
				stoptime += rand.nextInt(10);
				currentTime = System.currentTimeMillis() - startTime;
	//			System.out.println("StopTime: " + stoptime);
				if(stoptime >= 0 && stoptime < 400) {
					shiftTime = 1;
				}
				if(stoptime >= 400 && stoptime < 600) {
					shiftTime = 2;
				}
				if(stoptime >= 600 && stoptime < 700) {
					shiftTime = 3;
				}
				if(rand.nextInt(500/(currentSpeed+1)) == 5) {
					for(int i = 0; i < dec.length; i++) {
						if(dec[i] == null) {
	//						System.out.println("Spawning!");
							dec[i] = new Decoration();
							break;
						}
					}
				}
				for(int i = 0; i < dec.length; i++) {
					if(dec[i] != null) {
						dec[i].x -= currentSpeed;
						if (dec[i].x < -50) {
							dec[i] = null; //despawn once offscreen
						}
					}
				}
				if(isPlaying && finishLineDistance <= 225) {
					isPlaying = false;
					hasEnded = true;
					currentSpeed = 0;
					currentTimeConverted = Controller.TimeConversion(currentTime);
					timer(false);
				}
			}
		}else {
			if(backButtonBox.contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				User.updateMoney(User.money+topSpeed);
				User.money += topSpeed;
				String curtime = Controller.TimeConversion(currentTime);
				User.insertTimeTrial(curtime, topSpeed);
				Controller.switchStates(Controller.STATE.GARAGE);
			}
		}
	}
	public void render(Graphics g) {
		g.setColor(bgcolor);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		
		g.drawImage(road, roadPos, Frame.WIDTH/2-175, Frame.WIDTH*2, 150, null);
		g.drawImage(finishLineImg, finishLineDistance, Frame.WIDTH/2-175, 50, 150, null);
		g.drawImage(userCar, 15, Frame.WIDTH/2-145, 220, 150, null);
		if(showNumber) {
			g.setColor(Color.BLUE);
			g.drawString("" + User.racenumber, xNumPos, yNumPos);
		}
		
		for(int i = 0; i < dec.length; i++){
			if(dec[i] != null) {
				g.drawImage(dec[i].image, dec[i].x, dec[i].y, dec[i].width, dec[i].height, null);
			}
		}
				
		//Left Panel
		g.drawImage(speedBG, -25, 475, 350, 250, null);
		g.setColor(Color.black);
		g.setFont(Controller.bigBold);
		
		g.drawString("Your Time: " + currentTimeConverted, 40, 515);
		g.drawString("Current Speed: " + currentSpeed, 40, 545);
		g.drawString("Top Speed: " + topSpeed, 40, 575);
		g.drawString("Distance Left: " + finishLineDistance, 40, 605);
		g.setFont(Controller.smallFont);
				
		//Right Panel
			g.drawImage(speedBG, 200, 475, 600, 300, null);
			if(!onGas) {
				g.drawImage(gasNormal, gasPedals[0].x, gasPedals[0].y, gasPedals[0].width, gasPedals[0].height, null);
			}else {
				g.drawImage(gasPressed, gasPedals[1].x, gasPedals[1].y, gasPedals[1].width, gasPedals[1].height, null);	
			}
			g.setColor(Color.black);
			g.fillRoundRect(300, 490, 100, 175, 30, 30);//stoplightbg
			if(shiftTime == 1) {
				g.setColor(Color.RED);
			}else {
				g.setColor(partRed);
			}
			g.fillRoundRect(325, 495, 50, 50, 50, 50);
			if(shiftTime == 2) {
				g.setColor(Color.YELLOW);
			}else {
				g.setColor(partYellow);
			}
			g.fillRoundRect(325, 550, 50, 50, 50, 50);
			if(shiftTime == 3) {
				g.setColor(Color.GREEN);
			}else {
				g.setColor(partGreen);
			}
			g.fillRoundRect(325, 605, 50, 50, 50, 50);
			g.setColor(Color.BLACK);
			g.setFont(Controller.smallFont);
			g.drawString("Press the Gas when the", 405, 515);
			g.drawString("stoplight turns green.", 405, 535);
			g.drawString("The score will increase", 405, 555);
			g.drawString("the most when the light", 405, 575);
			g.drawString("is green.", 405, 595);
			g.setColor(Color.green);
			g.drawString("Green: Perfect Shift", 405, 615);
			g.setColor(Color.yellow);
			g.drawString("Yellow: Decent Shift", 405, 635);
			g.setColor(Color.RED);
			g.drawString("Red: Bad Shift", 405, 655);
			if(!isPlaying) {
				g.setColor(Color.BLACK);
				g.drawString("Press gas to start!", 405, 675);
			}
			//When the game is completed, prompt a menu
		if(hasEnded) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
			g.setColor(Color.darkGray);
			g.fillRoundRect(175, 150, 400, 400, 25, 25);
			g.setColor(Color.white);
			g.setFont(Controller.bigBold);
			g.drawString("Completed!", Frame.WIDTH/2-g.getFontMetrics().stringWidth("Completed!")/2, 175);
			g.setFont(Controller.smallFont);
			g.drawString("Your Time: " + currentTimeConverted, 200, 225);
			g.drawString("Top Speed: " + topSpeed + "mph", 200, 250);
			g.drawString("Money Earned: $" + topSpeed, 200, 275);
			g.drawImage(back, backButtonBox.x, backButtonBox.y, backButtonBox.width, backButtonBox.height, null);
			if (backButtonBox.contains(Controller.mousePoint)) {
				g.drawImage(backWave, backButtonBox.x, backButtonBox.y, backButtonBox.width, backButtonBox.height, null);
			}
		}
	}

	//starts and stops the timer
	public void timer(boolean start) {
		if(start) {
		  startTime = System.currentTimeMillis();
		}else {
			stopTime = System.currentTimeMillis();
		    elapsedTime = stopTime - startTime;
		    currentTime = elapsedTime;
//		    System.out.println(elapsedTime);
		}
	}
	
}
