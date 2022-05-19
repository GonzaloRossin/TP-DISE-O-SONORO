package com.mygdx.game;

public enum GarageCars {

    STARTER(0,"cars/car15.png","thumbnails/starter.png",1,0,0),
    MEDIUM(1,"cars/car14.png","thumbnails/medium.png",2,75,3500),
    SEDAN(2,"cars/car18.png","thumbnails/sedan.png",2,125,4750),
    FAST(3,"cars/car16.png","thumbnails/fast.png",3,200,5800),
    RED_CAR(4,"cars/car20.png","thumbnails/red_car.png",3,250,6500),
    YELLOW_CAR(5,"cars/car17.png","thumbnails/yellow_car.png",4,300,7000),
    MOTORCYCLE(6,"cars/motorcycle.png","thumbnails/motorcycle.png",5,500,10000);

    GarageCars(int id,String imagePath,String thumbnailPath,int coinMultiplier,int price,int requiredScore){
        this.setId(id);
        this.setImagePath(imagePath);
        this.setThumbnailPath(thumbnailPath);
        this.setCoinMultiplier(coinMultiplier);
        this.setPrice(price);
        this.setRequiredScore(requiredScore);
    }
    private int id;
    private String imagePath;
    private String thumbnailPath;
    private int coinMultiplier;
    private int price;
    private int requiredScore;

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

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getCoinMultiplier() {
        return coinMultiplier;
    }

    public void setCoinMultiplier(int coinMultiplier) {
        this.coinMultiplier = coinMultiplier;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequiredScore() {
        return requiredScore;
    }

    public void setRequiredScore(int requiredScore) {
        this.requiredScore = requiredScore;
    }
}
