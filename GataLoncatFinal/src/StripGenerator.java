import java.util.Random;
class StripGenerator {
	Sprite[] getStrip() {
		Sprite[] spriteStrip = new Sprite[8];
		Random gen = new Random();
		int y = spriteStrip.length;
		int x = gen.nextInt(4);
		switch (x) {
			case 0:
				for (int i = 0; i < y; i++) {
					Sprite strip = new Sprite("assets/Road.png");
					spriteStrip[i] = strip;
				}
				break;
			case 1:
				for (int i = 0; i < y; i++) {
					Sprite strip = new Sprite("assets/Tracks.png");
					spriteStrip[i] = strip;
				}
				break;
			case 2:
				for (int i = 0; i < y; i++) {
					
					x = gen.nextInt(5);
					spriteStrip[i] = makeSpecialStrip(i, x, "assets/Grass.png", "assets/Grass.png", "assets/Tree_One.png");
				}
				break;
			case 3:
				for (int i = 0; i < y; i++) {
					
					x = gen.nextInt(5);
					spriteStrip[i] = makeSpecialStrip(i, x, "assets/Water.png", "assets/Water.png", "assets/Lillypad.png");
				}
		}
		return spriteStrip;
	}
	private Sprite makeSpecialStrip(int i, int x, String background, String specialBlockOne, String specialBlockTwo) {
		Sprite oneBlock = new Sprite();
		switch (x) {
			case 0:
				oneBlock.setImage(background);
				break;
			case 1:
				oneBlock.setImage(background);
				break;
			case 2:
				oneBlock.setImage(background);
				break;
			case 3:
				oneBlock.setImage(specialBlockOne);
				break;
			case 4:
				oneBlock.setImage(specialBlockTwo);
				break;
		}
		return oneBlock;
	}
	Sprite[] getLandStrip() {
		Random gen = new Random();
		Sprite[] spriteStrip = new Sprite[8];
		for (int i = 0; i < 8; i++) {
			int x = gen.nextInt(5);
			spriteStrip[i] = makeSpecialStrip(i, x, "assets/Grass.png", "assets/Grass.png", "assets/Tree_One.png");
		}
		return spriteStrip;
	}
	Sprite[] getSpecialLandStrip() {
		Random gen = new Random();
		Sprite[] spriteStrip = new Sprite[8];
		for (int i = 0; i < 8; i++) {
			int x = gen.nextInt(5);
			spriteStrip[i] = makeSpecialStrip(i, x, "assets/Grass.png", "assets/Rock.png", "assets/Shrub.png");
		}
		return spriteStrip;
	}
	Sprite[] getWaterStrip() {
		
		Random gen = new Random();
		
		Sprite[] spriteStrip = new Sprite[8];
		for (int i = 0; i < 8; i++) {
			
			int x = gen.nextInt(5);
			spriteStrip[i] = makeSpecialStrip(i, x, "assets/Water.png", "assets/Water.png", "assets/Lillypad.png");
		}
		return spriteStrip;
	}
}
