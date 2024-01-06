package User;

public class TimeTrialRecords {

	public int id;
	public int users_id;
	public String userTime;
	public int speed;
	public String cdate;
	
	public String username;
	public int racingno;
	
	public TimeTrialRecords(int id, int users_id, String userTime, int speed, String cdate) {
		this.id = id;
		this.users_id = users_id;
		this.userTime = userTime;
		this.speed = speed;
		this.cdate = cdate;
	}
	
	public void getUserInfo() {
		//get the racing number and username
	}
}
