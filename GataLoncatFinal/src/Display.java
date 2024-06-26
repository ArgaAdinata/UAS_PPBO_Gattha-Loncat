import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
class Display extends JPanel implements ActionListener {
	private Sprite logo = new Sprite("assets/Gattha_Loncat.png");
	private boolean showLogo = false;
	private boolean newGame = false;
	private StripGenerator stripGen = new StripGenerator();
	public static final int numOfStrips = 9;
	public Sprite[][] allStrips = new Sprite[numOfStrips][8];
	private ArrayList<Integer> special = new ArrayList<>();
	private int land = 1, water = 0;
	private ArrayList<Sprite> cars = new ArrayList<>();
	private ArrayList<Sprite> trains = new ArrayList<>();
	private JButton startButton, controlsButton;
	private Car vManager = new Car();
	private Train tManager = new Train();
	public Hero hero = new Hero("assets/Chick/Chick_up.png");
	private int score = 0, movement = 0;
	public void decMovement() {
		this.movement--;
	}
	public void addMovement() {
		this.movement++;
	}
	private Score scoreManager = new Score();
	public int up = 0, down = 0, left = 0, right = 0;
	public static boolean press = false;
	private Timer gameLoop;
    private Random rand = new Random();
	Display(boolean pause) {
		setLayout(null);
		startButton = new JButton(new ImageIcon(getClass().getResource("assets/Start.png")));
		startButton.setBorder(BorderFactory.createEmptyBorder());
		controlsButton = new JButton(new ImageIcon(getClass().getResource("assets/Controls.png")));
		controlsButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.addActionListener(this);
		controlsButton.addActionListener(this);
		add(startButton);
		add(controlsButton);
		startButton.setBounds(250, 250, 300, 150);
		controlsButton.setBounds(250, 440, 300, 150);
		
		addKeyListener(new KeyPressing());
		
		setFocusable(true);
		
		setDoubleBuffered(true);
		
		setInitialLocations();
		
		gameLoop = new Timer(25, this);
		
		if (!pause) {
			startButton.setVisible(false);
			controlsButton.setVisible(false);
			gameLoop.start();
		} else
			showLogo = true;
	}
	private void setInitialLocations() {
		
		hero.setXLoc(298);
		hero.setYLoc(400);
		
		for (int i = 0; i < numOfStrips; i++) {
			
			Sprite[] strip = stripGen.getLandStrip();
			
			allStrips[i] = strip;
		}
		allStrips[5][3].setImage("assets/Grass.png");
		allStrips[4][3].setImage("assets/Grass.png");

		int x = 0;
		
		int y = -100;
		for (int i = 0; i < numOfStrips; i++) {
			for (int z = 0; z < 8; z++) {
				allStrips[i][z].setXLoc(x);
				allStrips[i][z].setYLoc(y);
				x += 100;
			}
			x = 0;
			y += 100;
		}

		for (int i = 0; i < 8; i++) {
			if (allStrips[0][i].getFileName().equals("assets/Grass.png")) {
				special.add(i);
				land++;
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == startButton) {
			newGame = true;
			newGame();
		}
		
		else if (e.getSource() == controlsButton) {
			JOptionPane.showMessageDialog(null, "WASD Keys:  Move the frog." +
					"\nShift:  Pause / Resume the game." +
					"\nEnter:  Start game / Restart game while paused.");
		}
		
		else {
			hero.heroBounds();
			
			hero.jumpHero();
			
			hero.move();
			
			manageCars();
			
			manageTrains();
			
			for (int i = 0; i < numOfStrips; i++) {
				for (int x = 0; x < 8; x++) {
					allStrips[i][x].move();
				}
			}
			
			manageStrips();
			
			scrollScreen();
			
			if (movement > score)
				score = movement;
			
			repaint();
			
			Toolkit.getDefaultToolkit().sync();
		}
	}
	private void newGame() {
		if (newGame) {
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
			frame.dispose();
			new Window(false);
		}
	}
	void killMsg(String killer) {
        repaint();
        gameLoop.stop();
        scoreManager.updateScores(score);
        switch (killer) {
            case "water":
                JOptionPane.showMessageDialog(null, "You drowned!" + "\nScore: " + score);
                break;
            case "tooFarDown":
                JOptionPane.showMessageDialog(null, "You were trapped!" + "\nScore: " + score);
                break;
            case "tooFarUp":
                JOptionPane.showMessageDialog(null, "You left the game!" + "\nScore: " + score);
                break;
            case "car":
                JOptionPane.showMessageDialog(null, "You got hit by a car!" + "\nScore: " + score);
                break;
            case "train":
                JOptionPane.showMessageDialog(null, "You got hit by a train!" + "\nScore: " + score);
                break;
        }
        startButton.setVisible(true);
        controlsButton.setVisible(true);
        showLogo = true;
    }
	private void manageCars() {
		
		for (int i = 0; i < cars.size(); i++) {
			Sprite car = cars.get(i);
            
            if (car.getYLoc() > 800) {
                cars.remove(i);
            }else {
                
                car.move();
                
                if (car.getXLoc() < -(rand.nextInt(700) + 400)){
                    
                    car.setXDir(-(rand.nextInt(10) + 10));
                    car.setXLoc(900);
                    car.setImage(vManager.randomCar("left"));
                } else if (car.getXLoc() > (rand.nextInt(700) + 1100)) {
                    
                    car.setXDir((rand.nextInt(10) + 10));
                    car.setXLoc(-200);
                    car.setImage(vManager.randomCar("right"));
                }
            }
			
			if (collision(car, hero) && !hero.invincibility) {
				
				killMsg("car");
			}
		}
	}
	private void manageTrains() {
		
		for (int i = 0; i < trains.size(); i++) {
			Sprite train = trains.get(i);
            
            if (train.getYLoc() > 800) {
                trains.remove(i);
            }else {
                
                train.move();
                
                if (train.getXLoc() < -(rand.nextInt(2500) + 2600)) {
                    train.setXDir(-(rand.nextInt(10) + 30));
                    train.setXLoc(900);
                    train.setImage(tManager.randomTrain());
                } else if (train.getXLoc() > rand.nextInt(2500) + 1800) {
                    train.setXDir((rand.nextInt(10) + 30));
                    train.setXLoc(-1500);
                    train.setImage(tManager.randomTrain());
                }
            }
			
			if (collision(train, hero) && !hero.invincibility) {
				
				killMsg("train");
			}
		}
	}
	private void manageStrips() {
		
		int allWater;
		int allGrass;
		
		for (int v = 0; v < numOfStrips; v++) {
			
			if (allStrips[v][0].getYLoc() > 800) {
				allStrips[v] = stripGen.getStrip();
				do {
					allWater = 0;
					allGrass = 0;
					
					for (Sprite s : allStrips[v]) {
						if (s.getFileName().equals("assets/Water.png"))
							allWater++;
						if (s.getFileName().equals("assets/Grass.png"))
							allGrass++;
					}
					if (allWater == 8)
						allStrips[v] = stripGen.getWaterStrip();
					if (allGrass == 8)
						allStrips[v] = stripGen.getLandStrip();
				} while (allWater == 8 || allGrass == 8);
				
				if (water > 0) {
					if (allStrips[v][0].getFileName().equals("assets/Water.png") || allStrips[v][0].getFileName().equals("assets/Lillypad.png")) {
						water = 0;
						for (int i : special) {
							allStrips[v][i].setImage("assets/Lillypad.png");
						}
					}
				}
				
				if (water > 0) {
					if (allStrips[v][0].getFileName().equals("assets/Grass.png") || allStrips[v][0].getFileName().equals("assets/Shrub.png") ||
							allStrips[v][0].getFileName().equals("assets/Tree_One.png") || allStrips[v][0].getFileName().equals("assets/Rock.png")) {
						allStrips[v] = stripGen.getSpecialLandStrip();
						water = 0;
						for (int i : special) {
							allStrips[v][i].setImage("assets/Grass.png");
						}
					}
				}
				
				if (land > 0) {
					if (allStrips[v][0].getFileName().equals("assets/Water.png") || allStrips[v][0].getFileName().equals("LilyPad.png")) {
						land = 0;
						int val = 0;
						while (val == 0) {
							allStrips[v] = stripGen.getWaterStrip();
							for (int i = 0; i < 8; i++) {
								if (allStrips[v][i].getFileName().equals("assets/Lillypad.png")) {
									
                                    for(int s : special){
                                        if (i == s) {
                                            val++;
                                        }
                                    }
								}
							}
						}
					}
				}
				
				if (allStrips[v][0].getFileName().equals("assets/Water.png") || allStrips[v][0].getFileName().equals("assets/Lillypad.png")) {
					special.clear();
					water = 0;
					for (int i = 0; i < 8; i++) {
						if (allStrips[v][i].getFileName().equals("assets/Lillypad.png")) {
							special.add(i);
							water++;
						}
					}
				} else
					water = 0;
				
				if (allStrips[v][0].getFileName().equals("assets/Grass.png") || allStrips[v][0].getFileName().equals("assets/Shrub.png") ||
						allStrips[v][0].getFileName().equals("assets/Tree_One.png") || allStrips[v][0].getFileName().equals("assets/Rock.png")) {
					special.clear();
					land = 0;
					for (int i = 0; i < 8; i++) {
						if (allStrips[v][i].getFileName().equals("assets/Grass.png")) {
							special.add(i);
							land++;
						}
					}
				}
				
				int X = 0;
				
				for (int i = 0; i < 8; i++) {
					allStrips[v][i].setYLoc(-99);
					allStrips[v][i].setXLoc(X);
					X += 100;
				}
				
				if (allStrips[v][0].getFileName().equals("assets/Road.png")){
				    cars.add(vManager.setVehicle(allStrips[v][0].getYLoc() + 10));
				}
				
                if (allStrips[v][0].getFileName().equals("assets/Tracks.png")) {
                    trains.add(tManager.setVehicle(allStrips[v][0].getYLoc() + 10));
                }
			}
		}
	}
	
	private void scrollScreen() {
		for (int v = 0; v < numOfStrips; v++) {
			for (int x = 0; x < 8; x++) {
				allStrips[v][x].setYDir(2);
			}
		}
		if (!press) {
			hero.setYDir(2);
		}
	}
	
	public static boolean collision(Sprite one, Sprite two) {
		
		Rectangle first = new Rectangle(one.getXLoc(), one.getYLoc(), one.getWidth(), one.getHeight());
		Rectangle second = new Rectangle(two.getXLoc(), two.getYLoc(), two.getWidth(), two.getHeight());
		return first.intersects(second);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < numOfStrips; i++) {
			for (int x = 0; x < 8; x++) {
				allStrips[i][x].paint(g, this);
			}
		}
		hero.paint(g, this);
		for (Sprite s : cars)
			s.paint(g, this);
		for (Sprite s : trains)
			s.paint(g, this);
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 3f);
		g.setFont(newFont);
		g.setColor(Color.green);
		
		g.drawString("Top: " + scoreManager.getScore(), 50, 50);
		
		Font cF = g.getFont();
		Font nF = cF.deriveFont(cF.getSize() * 3f);
		g.setFont(nF);
		g.setColor(Color.yellow);
		
		g.drawString("" + score, 50, 150);
		
		Font CF = g.getFont();
		Font NF = CF.deriveFont(CF.getSize() * 0.3f);
		g.setFont(NF);
		g.setColor(Color.red);
		
		if (hero.invincibility)
			g.drawString("Dev Mode", 50, 700);
		
		if (showLogo) {
			logo.setXLoc(227);
			logo.setYLoc(75);
			logo.paint(g, this);
		}
		
		Toolkit.getDefaultToolkit().sync();
	}
	private class KeyPressing extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				
				case KeyEvent.VK_D:
					if (!press && hero.getXLoc() < 695) {
						right = 8;
						press = true;
					}
					break;
				case KeyEvent.VK_A:
					if (!press && hero.getXLoc() > 0) {
						left = 8;
						press = true;
					}
					break;
				case KeyEvent.VK_W:
					if (!press) {
						up = 10;
						press = true;
					}
					break;
				case KeyEvent.VK_S:
					if (!press) {
						down = 10;
						press = true;
					}
					break;
				case KeyEvent.VK_CONTROL:
					if (hero.invincibility)
						hero.invincibility = false;
					else
						hero.invincibility = true;
					break;
				case KeyEvent.VK_SHIFT:
					if (gameLoop.isRunning())
						gameLoop.stop();
					else
						gameLoop.start();
					break;
				case KeyEvent.VK_ENTER:
					if (!gameLoop.isRunning()) {
						newGame = true;
						newGame();
					}
					break;
			}
		}
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_D:
					hero.setXDir(0);
					break;
				case KeyEvent.VK_A:
					hero.setXDir(0);
					break;
				case KeyEvent.VK_W:
					hero.setYDir(2);
					break;
				case KeyEvent.VK_S:
					hero.setYDir(2);
					break;
			}
		}
	}
}
