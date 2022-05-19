package com.mygdx.game.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Constants;
import com.mygdx.game.GarageCars;
import com.mygdx.game.MainGame;


public class GarageScreen extends BaseScreen {

    private static final float PADDING = 70f;
    private static final float THUMBNAIL_WIDTH = 410f;
    private static final float THUMBNAIL_HEIGHT = 750f;

    private Stage stage;
    private Image background;
    private Image coinIcon;
    private Label coinDisplay;
    private GarageCars current;
    private Image carThumbnail;
    private Label carInfo;
    private Label carWidth;
    private Label carLength;
    private Image green;
    private Image red;
    private Image blue;
    private Image currentColor;
    private Image priceIcon;
    private Image carPortrait;
    private Image carSprite;
    private Label price;
    private Label requiredScore;
    private ImageButton right;
    private ImageButton left;
    private ImageButton home;

    public GarageScreen(final MainGame game) {
        super(game);
        this.background = new Image(game.getManager().get("backgrounds/garage_background.png",Texture.class));
        Skin skin = new Skin(Gdx.files.internal("vhs/skin/vhs-ui.json"));
        this.coinDisplay = new Label(" " + game.getCoins(),skin);
        this.coinDisplay.setFontScale(2f);
        this.coinDisplay.setPosition(Constants.VIEWPORT_WIDTH.getValue() - coinDisplay.getWidth()*2f - PADDING,
                Constants.VIEWPORT_HEIGHT.getValue() - coinDisplay.getHeight()*2f - PADDING);
        this.coinIcon = new Image(game.getManager().get("coin.png",Texture.class));
        coinIcon.setPosition(coinDisplay.getX() - coinIcon.getWidth(), Constants.VIEWPORT_HEIGHT.getValue() - coinIcon.getHeight() - PADDING);
        this.carSprite = null;
        this.carPortrait = new Image(game.getManager().get("car_portrait.png",Texture.class));
        this.price = new Label("" ,skin);
        this.priceIcon = new Image(game.getManager().get("coin.png",Texture.class));
        green = new Image(game.getManager().get("buttons/green_button.png",Texture.class));
        red = new Image(game.getManager().get("buttons/red_button.png",Texture.class));
        blue = new Image(game.getManager().get("buttons/blue_button.png",Texture.class));
        currentColor = null;
        green.setVisible(false);
        red.setVisible(false);
        blue.setVisible(false);
        this.right = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getManager().get("buttons/right_button.png",Texture.class))));
        right.setPosition(Constants.VIEWPORT_WIDTH.getValue() - right.getWidth() - PADDING,Constants.VIEWPORT_HEIGHT.getValue()/2f - right.getHeight()/2f);
        right.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showNext(true);
                return true;
            }
        });
        this.left = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getManager().get("buttons/left_button.png",Texture.class))));
        left.setPosition(PADDING,Constants.VIEWPORT_HEIGHT.getValue()/2f - left.getHeight()/2f);
        left.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showNext(false);
                return true;
            }
        });
        this.home = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getManager().get("garage-icon.png",Texture.class))));;
        this.home.setPosition(PADDING,Constants.VIEWPORT_HEIGHT.getValue() - home.getHeight() - PADDING);
        home.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.getHomeScreen());
                return true;
            }
        });
        this.current = GarageCars.getGarageCar(game.getOwnedCars().get(game.getSelectedCar()));
        this.carThumbnail = new Image(game.getManager().get(current.getThumbnailPath(), Texture.class));
        this.carInfo = new Label("",skin);
        this.carWidth = new Label("",skin);
        this.carLength = new Label("",skin);
        this.requiredScore = new Label("Required score:     m",skin);
        requiredScore.setVisible(false);
        setLabel(carInfo,null);
        setLabel(carLength,carInfo);
        setLabel(carWidth,carLength);
    }
    private void showCarData() {
        if(carThumbnail != null)
            carThumbnail.remove();
        carThumbnail = new Image(game.getManager().get(current.getThumbnailPath(),Texture.class));
        carThumbnail.setSize(THUMBNAIL_WIDTH,THUMBNAIL_HEIGHT);
        this.carThumbnail.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - carThumbnail.getWidth()/2f,
                Constants.VIEWPORT_HEIGHT.getValue()/2f - PADDING);
        stage.addActor(carThumbnail);
        if(carSprite != null)
            carSprite.remove();
        carSprite = new Image(game.getManager().get(current.getImagePath(),Texture.class));
        stage.addActor(carSprite);
        double roundedWidth = Math.round((2.5*carSprite.getWidth() / Constants.PPM.getValue()) * 10d) / 10d;
        double roundedLength = Math.round((2.5*carSprite.getHeight() / Constants.PPM.getValue()) * 10d) / 10d;
        carWidth.setText("Width: " + roundedWidth + "m");
        carLength.setText("Length: " + roundedLength + "m");
        carInfo.setText("Coins: x" + current.getCoinMultiplier());
        this.carPortrait.setPosition(Constants.VIEWPORT_WIDTH.getValue() * (3f/4f) - carPortrait.getWidth()/2f,
                carLength.getY() - carPortrait.getHeight()/2f);
        carSprite.setPosition(carPortrait.getX() + carPortrait.getWidth()/2f - carSprite.getWidth()/2f,
                carPortrait.getY() + carPortrait.getHeight()/2f - carSprite.getHeight()/2f );
        price.setText(" " + current.getPrice());
        price.setFontScale(2f);
        priceIcon.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - priceIcon.getWidth() - PADDING/2f, carThumbnail.getY() - priceIcon.getHeight() - PADDING);
        priceIcon.setVisible(true);
        price.setPosition(priceIcon.getX() + priceIcon.getWidth(),
                priceIcon.getY() + priceIcon.getWidth()/2);
        if(currentColor != null)
            currentColor.setVisible(false);
        if(game.getOwnedCars().contains(current.getId())){
            requiredScore.setVisible(false);
            if(game.getOwnedCars().get(game.getSelectedCar()).equals(current.getId())){
                currentColor = blue;
                price.setText("Selected");
            }
            else{
                currentColor = green;
                price.setText(" Select");
            }
            priceIcon.setVisible(false);
            price.setPosition(priceIcon.getX(),
                    price.getY());
            currentColor.setPosition(price.getX() - PADDING/2f, priceIcon.getY() - PADDING/2f);
            currentColor.setSize(price.getText().length*30 + PADDING*2, priceIcon.getHeight() + PADDING);
        }
        else{
            requiredScore.setVisible(true);
            requiredScore.setFontScale(2f);
            requiredScore.setText("Required score: " + current.getRequiredScore() + "m");
            requiredScore.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - requiredScore.getWidth(),
                    currentColor.getY() - requiredScore.getHeight()*2.5f);
            if(!game.getOwnedCars().contains(current.getId())
                    && (current.getPrice() > game.getCoins() || game.getHighscore() < current.getRequiredScore())) {
                currentColor = red;
            }
            else
                currentColor = green;

            currentColor.setPosition(priceIcon.getX() - PADDING/2f, priceIcon.getY() - PADDING/2f);
            currentColor.setSize(priceIcon.getWidth() + price.getText().length*30 + PADDING*2, priceIcon.getHeight() + PADDING);
        }
        currentColor.setVisible(true);
        price.toFront();
        priceIcon.toFront();
    }
    private void showNext(boolean right){

        int index = current.ordinal();
        if(right)
            index = (index + 1) % GarageCars.values().length;
        else
            index = (index - 1 + GarageCars.values().length) % GarageCars.values().length;
        int i = 0;
        for(GarageCars car : GarageCars.values()) {
            if(i == index){
                current = car;
                showCarData();
                break;
            }
            i++;
        }
    }
    private void setLabel(Label label, Label bottomLabel){
        label.setFontScale(2);
        if(bottomLabel == null)
            label.setPosition(PADDING,PADDING*2);
        else
            label.setPosition(PADDING,bottomLabel.getY() + label.getHeight()*2 + PADDING*2);
    }
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH.getValue() , Constants.VIEWPORT_HEIGHT.getValue()));
        stage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(currentColor == null)
                    return true;
                Rectangle rectangle = new Rectangle(currentColor.getX(),currentColor.getY(),currentColor.getWidth(),currentColor.getHeight());
                if(!rectangle.contains(x,y))
                    return true;
                Gdx.app.setLogLevel(Application.LOG_DEBUG);
                if(game.getOwnedCars().get(game.getSelectedCar()).equals(current.getId())) {
                    return true;
                }
                if(game.getOwnedCars().contains(current.getId())){
                    game.setSelectedCar(game.getOwnedCars().indexOf(current.getId()));
                    Preferences prefs = Gdx.app.getPreferences("My Preferences");
                    prefs.putInteger("selectedCar",game.getSelectedCar());
                    prefs.flush();
                    showCarData();
                }
                else if(game.getCoins() >= current.getPrice() && game.getHighscore() >= current.getRequiredScore()){
                    game.getOwnedCars().add(current.getId());
                    game.setSelectedCar(game.getOwnedCars().indexOf(current.getId()));
                    Preferences prefs = Gdx.app.getPreferences("My Preferences");
                    String ownedCars = prefs.getString("ownedCars");
                    ownedCars = ownedCars.concat(" " + current.getId());
                    prefs.putString("ownedCars",ownedCars);
                    game.setCoins(game.getCoins() - current.getPrice());
                    prefs.flush();
                    showCarData();
                    coinDisplay.setText(" " + game.getCoins());
                }
                return true;
            }
        });
        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);
        stage.addActor(home);
        stage.addActor(right);
        stage.addActor(left);
        stage.addActor(carThumbnail);
        stage.addActor(carInfo);
        stage.addActor(carWidth);
        stage.addActor(carLength);
        stage.addActor(coinDisplay);
        stage.addActor(coinIcon);
        stage.addActor(price);
        stage.addActor(priceIcon);
        stage.addActor(blue);
        stage.addActor(green);
        stage.addActor(red);
        stage.addActor(requiredScore);
        stage.addActor(carPortrait);
        coinDisplay.setText(" " + game.getCoins());
        showCarData();
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    @Override
    public void dispose() {

        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.dispose();
    }
}
