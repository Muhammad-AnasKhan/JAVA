package User;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Controller;
import main.Frame;

public class Opponents {

	public Rectangle bounds;
	public String name;
	String descr;
	int x;
	public int y;
	public int id;
	int width;
	int height;
	public int speed;
	public int money;
	int bracket;
	public int wonRecord = 0;
	public int lossRecord = 0;
	int topUserSpeed = 0;
	String bestUserTime = "99:99:99";
	
	public Opponents(int id, String name, String descr, int y, int speed, int money, int bracket, int wonRecord, int lossRecord, int topUserSpeed, String bestUserTime) {
		this.id = id;
		this.name = name;
		this.descr = descr;
		this.y = y;
		this.speed = speed;
		this.money = money;
		this.bracket = bracket;
		this.wonRecord = wonRecord;
		this.lossRecord = lossRecord;
		this.topUserSpeed = topUserSpeed;
		this.bestUserTime = bestUserTime;
		this.width = 500;
		this.height = 125;
		x = Frame.WIDTH/2-width/2;
		bounds = new Rectangle(x, y, width, height);
	}
	public void tick() {
		bounds = new Rectangle(x, y, width, height);
	}
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRoundRect(x, y, width, height, 20, 20);
		g.setColor(Color.white);
		g.drawString(name, x+25, y+25);
		g.drawString("Your Best Speed: " + topUserSpeed, x+300, y+25);
		g.drawString(descr, x+25, y+50);
		g.drawString("Your Best Time: " + bestUserTime, x+300, y+50);
		g.drawString("Opponent Speed: " + speed, x+25, y+75);
		g.drawString("W/L Ratio: " + wonRecord + "/" + lossRecord, x+300, y+75);
		g.drawString("Racing Bracket: " + bracket, x+25, y+100);
		g.drawString("Money To Earn: $" + money, x+300, y+100);
		if(bounds.contains(Controller.mousePoint)) {
			g.setColor(new Color(200, 200, 200, 100));
			g.fillRoundRect(x, y, width, height, 20, 20);
		}
	}
	
}
