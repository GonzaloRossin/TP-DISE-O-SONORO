package com.mygdx.game;

public enum GarageCars {

    STARTER(0,"cars/car15.png","thumbnails/starter.png",1,0,0, SoundsPlayer.SoundEffect.CarStart3),
    MEDIUM(1,"cars/car14.png","thumbnails/medium.png",2,75,3500, SoundsPlayer.SoundEffect.CarStart1),
    SEDAN(2,"cars/car18.png","thumbnails/sedan.png",2,125,4750, SoundsPlayer.SoundEffect.CarStart5),
    FAST(3,"cars/car16.png","thumbnails/fast.png",3,200,5800, SoundsPlayer.SoundEffect.CarStart4),
    RED_CAR(4,"cars/car20.png","thumbnails/red_car.png",3,250,6500, SoundsPlayer.SoundEffect.CarStart2),
    YELLOW_CAR(5,"cars/car17.png","thumbnails/yellow_car.png",4,300,7000, SoundsPlayer.SoundEffect.CarStart6),
    MOTORCYCLE(6,"cars/motorcycle.png","thumbnails/motorcycle.png",5,500,10000, SoundsPlayer.SoundEffect.BikeStart);

    GarageCars(int id, String imagePath, String thumbnailPath, int coinMultiplier, int price, int requiredScore, SoundsPlayer.SoundEffect startSound){
        this.id = id;
        this.imagePath = imagePath;
        this.thumbnailPath = thumbnailPath;
        this.coinMultiplier = coinMultiplier;
        this.price = price;
        this.requiredScore = requiredScore;
        this.startSound = startSound;
    }
    private final int id;
    private final String imagePath;
    private final String thumbnailPath;
    private final int coinMultiplier;
    private final int price;
    private final int requiredScore;
    private final SoundsPlayer.SoundEffect startSound;

    public static GarageCars getGarageCar(int id){
        for(GarageCars car : GarageCars.values()){
            if(car.getId() == id)
                return car;
        }
        return null;
    }
    public String getImagePath() {
        return imagePath;
    }

    public int getCoinMultiplier() {
        return coinMultiplier;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }
    public int getPrice() {
        return price;
    }
    public int getId() {
        return id;
    }

    public int getRequiredScore() {
        return requiredScore;
    }

    public SoundsPlayer.SoundEffect getStartSound() {
        return startSound;
    }
}
