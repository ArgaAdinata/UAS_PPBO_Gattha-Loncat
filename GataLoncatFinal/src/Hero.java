public class Hero extends Sprite {
    
    public boolean invincibility = false;
    public void heroBounds() {
        for (int i = 0; i < Display.numOfStrips; i++) {
            for (Sprite s : Window.dis.allStrips[i]) {
                if (!invincibility) {
                    
                    if (s.getFileName().equals("assets/Tree_One.png") || s.getFileName().equals("assets/Rock.png")) {
                        if (Display.collision(this, s)) {
                            if ((s.getYLoc() + 100) - (Window.dis.hero.getYLoc()) < 5 && (s.getXLoc() + 100) - Window.dis.hero.getXLoc() < 125 && (s.getXLoc() + 100) - Window.dis.hero.getXLoc() > 20) {
                                Window.dis.up = 0;
                            } else if ((Window.dis.hero.getYLoc() + 105) > (s.getYLoc()) && (Window.dis.hero.getXLoc() + 100) - s.getXLoc() < 125 && (Window.dis.hero.getXLoc() + 100) - s.getXLoc() > 20) {
                                Window.dis.down = 0;
                            } else if (Window.dis.hero.getXLoc() - (s.getXLoc() + 100) > -5 && (s.getYLoc() + 100) - Window.dis.hero.getYLoc() < 125 && (s.getYLoc() + 100) - Window.dis.hero.getYLoc() > 20) {
                                Window.dis.left = 0;
                            } else if (s.getXLoc() - (Window.dis.hero.getXLoc() + 100) > -5 && (Window.dis.hero.getYLoc() + 100) - s.getYLoc() < 125 && (Window.dis.hero.getYLoc() + 100) - s.getYLoc() > 20) {
                                Window.dis.right = 0;
                            }
                        }
                    }
                    
                    if (s.getFileName().equals("assets/Water.png")) {
                        if (s.getXLoc() - Window.dis.hero.getXLoc() > 0 && s.getXLoc() - Window.dis.hero.getXLoc() < 10) {
                            if (s.getYLoc() - Window.dis.hero.getYLoc() > 0 && s.getYLoc() - Window.dis.hero.getYLoc() < 10) {
                                
                                Window.dis.killMsg("water");
                            }
                        }
                    }
                }
                
                if (Window.dis.hero.getYLoc() > 800) {
                    
                    Window.dis.hero.setYLoc(500);
                    Window.dis.hero.setXLoc(900);
                    
                    Window.dis.killMsg("tooFarDown");
                }
                
                if (Window.dis.hero.getYLoc() < -110) {
                    
                    Window.dis.hero.setYLoc(500);
                    Window.dis.hero.setXLoc(900);
                    
                    Window.dis.killMsg("tooFarUp");
                }
            }
        }
    }
    public void jumpHero() {
        
        int location;
        
        if (Window.dis.left > 0 && Display.press) {
            Window.dis.hero.setXDir(-12.5);
            Window.dis.left--;
            Window.dis.hero.setImage("assets/Chick/Chick_Left.png");
        } else if (Window.dis.right > 0 && Display.press) {
            Window.dis.hero.setXDir(12.5);
            Window.dis.right--;
            Window.dis.hero.setImage("assets/Chick/Chick_Right.png");
        } else if (Window.dis.left == 0 && Window.dis.right == 0 && Window.dis.up == 0 && Window.dis.down == 0) {
            Window.dis.hero.setXDir(0);
            Display.press = false;
        }
        
        if (Window.dis.up > 0 && Display.press) {
            
            Window.dis.hero.setYDir(-10);
            Window.dis.hero.move();
            Window.dis.hero.setImage("assets/Chick/Chick_up.png");
            
            location = Window.dis.hero.getYLoc();
            
            for (int i = 0; i < Display.numOfStrips; i++) {
                Sprite x = Window.dis.allStrips[i][0];
                
                if (location - x.getYLoc() > 95 && location - x.getYLoc() < 105) {
                    Window.dis.hero.setYDir(0);
                    Window.dis.up = 0;
                    Display.press = false;
                    Window.dis.hero.setYLoc(x.getYLoc() + 101);
                    
                    Window.dis.addMovement();
                    i = Display.numOfStrips;
                }
            }
        }
        
        else if (Window.dis.down > 0 && Display.press) {
            
            Window.dis.hero.setYDir(10);
            Window.dis.hero.move();
            Window.dis.hero.setImage("assets/Chick/Chick_Down.png");
            
            location = Window.dis.hero.getYLoc();
            
            for (int i = 0; i < Display.numOfStrips; i++) {
                Sprite x = Window.dis.allStrips[i][0];
                
                if (location - x.getYLoc() < -95 && location - x.getYLoc() > -105) {
                    Window.dis.hero.setYDir(0);
                    Window.dis.down = 0;
                    Display.press = false;
                    Window.dis.hero.setYLoc(x.getYLoc() - 99);
                    
                    
                    Window.dis.decMovement();
                    i = Display.numOfStrips;
                }
            }
        }
    }
    Hero(String fileName) {
        super(fileName);
    }
}
