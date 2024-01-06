package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import User.Opponents;
import User.User;
import handlers.MouseHandler;
import main.Controller;
import main.Frame;
import main.ImageLoader;

public class CareerMode {
	
	private Rectangle[] gasPedals = {new Rectangle(550, 485, 170, 190), new Rectangle(550, 485, 170, 190)};
	private Rectangle[] buttons = {new Rectangle(280, 375, 200, 125)};
	private Decoration[] dec;
	
	Color partRed, partYellow, partGreen;
	Color bgcolor = new Color(50, 168, 82);
	Image speedBG, gasNormal, gasPressed, road, userCar, enemyCar, finishLineImg, back, backWave;
	Opponents opponent;
	boolean onGas = false;
	boolean isPlaying = false;
	boolean hasEnded = false;
	boolean didWin = false;
	
	int finishLineDistance = 1000;
	int stoptime = 0;
	long currentTime = 0;
	long enemyTime = 0;
	String currentTimeConverted = "";
	String enemyTimeConverted = "";
	boolean showNumber = false;
	int xNumPos = 0;
	int yNumPos = 0;
	int enemyYNumPos = 0;
	int topSpeed = 0;
	int roadPos = 0;
	int currentSpeed = 0;
	int shiftTime = 3;//If it is red, yellow, or green
	int enemyPos = 0;
	int enemySpeed = 0;
	int enemyDisLeft = 0;
	String enemyName = "";
	
	int winRecord = 0;
	int lossRecord = 0;
	
    long startTime;
    long stopTime;
    long elapsedTime;
    
	public CareerMode(int opponentID) {
		init();
		opponent = Controller.opponents[opponentID];
		finishLineDistance = 500*opponent.speed;
		if(opponent.speed < 18) {
			enemyCar = new ImageLoader(ImageLoader.enemyCar1).getImage();
		}else if(opponent.speed >= 18 && opponent.speed < 36) {
			enemyCar = new ImageLoader(ImageLoader.enemyCar2).getImage();
		}else if(opponent.speed >= 36 && opponent.speed < 54) {
			enemyCar = new ImageLoader(ImageLoader.enemyCar3).getImage();
		}else if(opponent.speed >= 54 && opponent.speed < 72) {
			enemyCar = new ImageLoader(ImageLoader.enemyCar4).getImage();
		}else{
			enemyCar = new ImageLoader(ImageLoader.enemyCar5).getImage();
		}
	}
	public void init() {
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
			yNumPos = 342;
			showNumber = true;
		}else if(User.speed >= 18 && User.speed < 24) {
			userCar = new ImageLoader(ImageLoader.userCar3).getImage();
			xNumPos = 110;
			yNumPos = 335;
			showNumber = true;
		}else if(User.speed >= 24 && User.speed < 32) {
			userCar = new ImageLoader(ImageLoader.userCar4).getImage();
			xNumPos = 77;
			yNumPos = 340;
			showNumber = true;
		}else {
			userCar = new ImageLoader(ImageLoader.userCar5).getImage();
			xNumPos = 80;
			yNumPos = 364;
			showNumber = true;
		}
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
			if(isPlaying) {
				if(enemyPos < finishLineDistance-220) {
					enemyPos += opponent.speed-currentSpeed;
					enemyTimer();
				}else {
					enemyPos = finishLineDistance-200;
				}
			}
			roadPos-= currentSpeed;
			finishLineDistance -= currentSpeed;
			currentTimeConverted = Controller.TimeConversion(currentTime);
			enemyTimeConverted = Controller.TimeConversion(enemyTime);
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
								if(currentSpeed >= User.speed/2) {
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
							if(stoptime >= 700) {
								shiftTime = 1;
	//							System.out.println("Bad Shift - Late");
								if(currentSpeed >= User.speed/2) {
									currentSpeed -= Math.round(User.speed/2);
								}
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
				if(stoptime >= 700) {
					shiftTime = 1;
					stoptime = 0;
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
					if(enemyPos+220 < 235) { //220=enemywidth - 235 is xpos of user car + width. If they are behind us at the finish
						didWin = true;
						opponent.wonRecord += 1;
						enemyTimeConverted = "DNF";
					}else {
						opponent.lossRecord += 1;
					}
					currentSpeed = 0;
					currentTimeConverted = Controller.TimeConversion(currentTime);
					timer(false);
				}
			}
		}else {
			if(buttons[0].contains(Controller.mousePoint) && MouseHandler.MOUSEDOWN) {
				if(didWin) {
					User.money += opponent.money;
					User.updateMoney(User.money);
				}
				String curtime = Controller.TimeConversion(currentTime);
				int winstat = 0;
				if(didWin) {
					winstat = 1;
				}
				User.insertCareerMode(opponent.id, curtime, topSpeed, winstat);
				Controller.switchStates(Controller.STATE.GARAGE);
			}
		}
	}
	public void render(Graphics g) {
		g.setColor(bgcolor);
		g.fillRect(0, 0, Frame.WIDTH, Frame.HEIGHT);
		
		g.drawImage(road, roadPos, Frame.WIDTH/2-75, Frame.WIDTH*2, 100, null);
		g.drawImage(road, roadPos, Frame.WIDTH/2-175, Frame.WIDTH*2, 100, null);
		g.drawImage(finishLineImg, finishLineDistance, Frame.WIDTH/2-175, 50, 200, null);
		g.drawImage(enemyCar, enemyPos, Frame.WIDTH/2-200, 220, 150, null);
		g.drawImage(userCar, 5, Frame.WIDTH/2-105, 220, 150, null);
		if(showNumber) {
			g.setColor(Color.BLUE);
			g.drawString("" + User.racenumber, xNumPos, yNumPos);
		}
		for(int i = 0; i < dec.length; i++){
			if(dec[i] != null) {
				g.drawImage(dec[i].image, dec[i].x, dec[i].y, dec[i].width, dec[i].height, null);
			}
		}
				
		//Left Panels
		g.drawImage(speedBG, -25, 475, 360, 250, null);
		g.drawImage(speedBG, -25, 5, 375, 245, null);
		g.setColor(Color.black);
		g.setFont(Controller.bigBold);	
		g.drawString("" + opponent.name, 40, 35);
		g.drawString("Opponent Speed: " + opponent.speed, 40, 65);
		g.drawString("Distance Left: " + (finishLineDistance-enemyPos), 40, 95);
		g.drawString("Their Time: " + enemyTimeConverted, 40, 125);
		g.drawString("Record: " + opponent.wonRecord + "/" + opponent.lossRecord, 40, 155);
		
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
			if(didWin) {
				g.drawString("You Win!", Frame.WIDTH/2-g.getFontMetrics().stringWidth("You Win!")/2, 175);
				g.setFont(Controller.smallFont);
				g.drawString("Money Earned: $" + opponent.money, 200, 275);
			}else {
				g.drawString("You Lose!", Frame.WIDTH/2-g.getFontMetrics().stringWidth("You Lose!")/2, 175);
				g.setFont(Controller.smallFont);
				g.drawString("Money Earned: $0...", 200, 275);
			}
			g.drawString("Your Time: " + currentTimeConverted, 200, 225);
			g.drawString("Their Time: " + enemyTimeConverted, 385, 225);
			g.drawString("Top Speed: " + topSpeed + "mph", 200, 250);
			g.drawString("Record: " + opponent.wonRecord + "/" + opponent.lossRecord, 200, 300);
			g.drawImage(back, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
			if(buttons[0].contains(Controller.mousePoint)) { // if Mouse is in box 1
				//if (i == 0) {	
					g.drawImage(backWave, buttons[0].x, buttons[0].y, buttons[0].width, buttons[0].height, null);
				//}
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
	//Enemy Timer
	public void enemyTimer() {
			enemyTime = System.currentTimeMillis();
			enemyTime = enemyTime - startTime;
//		    System.out.println(elapsedTime);
	}
	
}
