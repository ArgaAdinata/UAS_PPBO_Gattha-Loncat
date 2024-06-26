import java.awt.*;
public class Train extends ManageVehicles {
    public Sprite setVehicle(int stripYLoc) {
        
        Sprite train = new Sprite(randomTrain());
        
        train.setYDir(2);
        
        train.setYLoc(stripYLoc);
        if (rand.nextInt(2) == 1) {
            
            train.setXLoc(900);
            train.setXDir(-(rand.nextInt(10) + 30));
        } else {
            
            train.setXLoc(-1500);
            train.setXDir((rand.nextInt(10) + 30));
        }
        return train;
    }
    
    public String randomTrain() {
        int trainNum = rand.nextInt(10);
        String trainImage = "";
        switch (trainNum) {
            case 0:
                trainImage = "assets/Trains/Train_Blue.png";
                break;
            case 1:
                trainImage = "assets/Trains/Train_Green.png";
                break;
            case 2:
                trainImage = "assets/Trains/Train_Grey.png";
                break;
            case 3:
                trainImage = "assets/Trains/Train_Orange.png";
                break;
            case 4:
                trainImage = "assets/Trains/Train_Purple.png";
                break;
            case 5:
                trainImage = "assets/Trains/Train_Red.png";
                break;
            case 6:
                trainImage = "assets/Trains/Train_White.png";
                break;
            case 7:
                trainImage = "assets/Trains/Train_Yellow.png";
                break;
        }
        return trainImage;
    }
}
