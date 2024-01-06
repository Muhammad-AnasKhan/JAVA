package User;

public class AllUpgrades {

	public int id;
	public String effect;
	public String img_loc;
	
	public UpgradeDetails upgrade;
	
	public AllUpgrades(int id, String effect) {
		this.id = id;
		this.effect = effect;
		upgrade = new UpgradeDetails();
	}
	public void addUpgrade(int lvl, String effect, int amount, int cost, int id) {
		upgrade.effect[lvl] = effect;
		upgrade.amount[lvl] = amount;
		upgrade.cost[lvl] = cost;
		upgrade.id[lvl] = id;
	}
	public String getEffect(int lvl) {
		return upgrade.effect[lvl];
	}
	public int getAmount(int lvl) {
		return upgrade.amount[lvl];
	}
	public int getCost(int lvl) {
		return upgrade.cost[lvl];
	}
	public int getID(int lvl) {
		return upgrade.id[lvl];
	}
	public boolean getPurchased(int lvl) {
		return upgrade.purchased[lvl];
	}
	public void setPurchased(int level) {
		upgrade.purchased[level-1] = true;
	}
	public void setActive(int level) {
		upgrade.activeUpgrade = level;
	}
	public void updateBounds(int bounds, int x, int y) {
			upgrade.bounds[bounds].x = x;
			upgrade.bounds[bounds].y = y;
	}
	public void resetUpgrades() {
		for(int i = 0; i < upgrade.purchased.length; i++) {
			upgrade.purchased[i] = false;
		}
	}
}
