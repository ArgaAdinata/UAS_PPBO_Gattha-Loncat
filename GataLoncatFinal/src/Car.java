public class Car extends ManageVehicles {
    public Sprite setVehicle(int stripYLoc) {
        Sprite car = new Sprite();
        car.setYDir(2);
        car.setYLoc(stripYLoc);
        if (rand.nextInt(2) == 1) {
            car.setXLoc(900);
            car.setXDir(-(rand.nextInt(10) + 10));
            car.setImage(randomCar("left"));
        } else {
            car.setXLoc(-200);
            car.setXDir((rand.nextInt(10) + 10));
            car.setImage(randomCar("right"));
        }
        return car;
    }
    
    public String randomCar(String dir) {
        
        int carColor = rand.nextInt(8);
        String carImage = "";
        if (dir.equals("left")) {
            switch (carColor) {
                case 0:
                    carImage = "assets/Car_Left/Car_Left_Blue.png";
                    break;
                case 1:
                    carImage = "assets/Car_Left/Car_Left_Green.png";
                    break;
                case 2:
                    carImage = "assets/Car_Left/Car_Left_Grey.png";
                    break;
                case 3:
                    carImage = "assets/Car_Left/Car_Left_Orange.png";
                    break;
                case 4:
                    carImage = "assets/Car_Left/Car_Left_Purple.png";
                    break;
                case 5:
                    carImage = "assets/Car_Left/Car_Left_Red.png";
                    break;
                case 6:
                    carImage = "assets/Car_Left/Car_Left_White.png";
                    break;
                case 7:
                    carImage = "assets/Car_Left/Car_Left_Yellow.png";
                    break;
            }
        }
        if (dir.equals("right")) {
            switch (carColor) {
                case 0:
                    carImage = "assets/Car_Right/Car_Right_Blue.png";
                    break;
                case 1:
                    carImage = "assets/Car_Right/Car_Right_Green.png";
                    break;
                case 2:
                    carImage = "assets/Car_Right/Car_Right_Grey.png";
                    break;
                case 3:
                    carImage = "assets/Car_Right/Car_Right_Orange.png";
                    break;
                case 4:
                    carImage = "assets/Car_Right/Car_Right_Purple.png";
                    break;
                case 5:
                    carImage = "assets/Car_Right/Car_Right_Red.png";
                    break;
                case 6:
                    carImage = "assets/Car_Right/Car_Right_White.png";
                    break;
                case 7:
                    carImage = "assets/Car_Right/Car_Right_Yellow.png";
                    break;
            }
        }
        return carImage;
    }
}
