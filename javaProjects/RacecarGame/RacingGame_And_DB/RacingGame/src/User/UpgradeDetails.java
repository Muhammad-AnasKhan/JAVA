package User;

import java.awt.Rectangle;

public class UpgradeDetails {

	String[] effect = new String[5];
	int[] amount = new int[5];
	int[] cost = new int[5];
	int[] id = new int[5];
	int activeUpgrade = -1;
    boolean[] purchased = {false, false, false, false, false};
	int highestAmount = -1;
	
	public Rectangle[] bounds = new Rectangle[5];
	
	public UpgradeDetails() {
		for(int i = 0; i < bounds.length; i++) {
			bounds[i] = new Rectangle();
			bounds[i].x = 0;
			bounds[i].y = 0;
			bounds[i].width = 140;
			bounds[i].height = 70;
		}
	}
	public String getEffect(int lvl) {
		return effect[lvl];
	}
	public int getAmount(int lvl) {
		return amount[lvl];
	}
	public int getCost(int lvl) {
		return cost[lvl];
	}
	public int getID(int lvl) {
		return id[lvl];
	}
	public boolean getPurchased(int lvl) {
		return purchased[lvl];
	}
	public int getActive() {
		return activeUpgrade;
	}
	public int returnHighestLevel() {
//		if(highestAmount != -1) {
//			return highestAmount;
//		}else {
//			for(int i = 0; i < amount.length; i++) {
//				if(effect[i] == null) {
//					highestAmount = i;
//					break;
//				}
//			}
//		}
		return 5;
	}
	
}
