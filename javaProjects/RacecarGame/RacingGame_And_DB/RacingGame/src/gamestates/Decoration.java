package gamestates;

import java.awt.Image;
import java.util.Random;

import main.ImageLoader;

public class Decoration {

	int x = 1200;
	int y = 100;
	int width = 0;
	int height = 0;
	int type = 0;
	Image image;
	public Decoration() {
		Random rand = new Random();
		this.y = rand.nextInt(125);
		type = rand.nextInt(2);
		if(type == 0) {
			image = new ImageLoader(ImageLoader.treeObj).getImage();
			width = 50;
			height = 100;
			if(rand.nextInt(2) == 1) {
				this.y = rand.nextInt(110);//spawn top
			}else {
				this.y = rand.nextInt(50)+400;//spawn bottom
			}
		}
		if(type == 1) {
			image = new ImageLoader(ImageLoader.rockObj).getImage();
			width = 40;
			height = 40;
			if(rand.nextInt(2) == 1) {
				this.y = rand.nextInt(110);//spawn top
			}else {
				this.y = rand.nextInt(50)+400;//spawn bottom
			}
		}
	}

}
