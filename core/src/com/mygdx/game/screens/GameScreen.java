package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.GarageCars;
import com.mygdx.game.actors.Background;
import com.mygdx.game.actors.Car;
import com.mygdx.game.Constants;
import com.mygdx.game.MainGame;
import com.mygdx.game.actors.Coin;
import com.mygdx.game.actors.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameScreen extends BaseScreen {


    private static final float HEIGHT = 16;
    private static final float WIDTH = 9;
    private static final float PADDING = 30;
    private static final float INITIAL_SPEED = 3;
    private static final float INITIAL_SPAWN_TIME = 1.4f;
    private static final float COIN_SPAWN_TIME = 3f;
    private static final float BLOCK_SPACING = 0.5f;
    private static final float FONT_SCALE = 2.5f;
    private Stage stage;
    private World world;
    private Player player;
    private Label scoreDisplay;
    private float score;
    private List<Car> cars;
    private List<Car> deletedCars;
    private List<Coin> coins;
    private float elapsedTime;
    private float elapsedCoinTime;
    private float speed;
    private Background background;
    private Image gameOver;
    private Image coinIcon;
    private Label coinDisplay;
    private ImageButton adButton;
    private ImageButton retryButton;
    private ImageButton homeButton;
    private float spawnTime;
    //Debugging
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;
    private boolean lost;
    private boolean stopped;

    public GameScreen(final MainGame game) {
        super(game);
        background = new Background(game.getManager());
        lost = false;
        cars = new ArrayList<Car>();
        coins = new ArrayList<Coin>();
        deletedCars = new ArrayList<Car>();
        renderer = new Box2DDebugRenderer();

        gameOver = new Image((Texture)game.getManager().get("game-over.png",Texture.class));
        gameOver.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - gameOver.getWidth()/2f,Constants.VIEWPORT_HEIGHT.getValue()/2f + gameOver.getWidth()/2f);
        gameOver.setVisible(false);
        retryButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getManager().get("buttons/retry.png",Texture.class))));
        retryButton.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - retryButton.getWidth()/2f, Constants.VIEWPORT_HEIGHT.getValue()/2f - retryButton.getHeight()/2f);
        retryButton.setDisabled(true);
        retryButton.setVisible(false);
        retryButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameOver.setVisible(false);
                retryButton.setVisible(false);
                retryButton.setDisabled(true);
                homeButton.setVisible(false);
                homeButton.setDisabled(true);
                adButton.setVisible(false);
                adButton.setDisabled(true);
                reset();
                scoreDisplay.setPosition(Constants.VIEWPORT_WIDTH.getValue() - scoreDisplay.getWidth()*FONT_SCALE - PADDING,Constants.VIEWPORT_HEIGHT.getValue() - scoreDisplay.getHeight()*FONT_SCALE - PADDING);

                return true;
            }
        });

        homeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getManager().get("garage-icon.png",Texture.class))));
        homeButton.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - homeButton.getWidth()/2f, retryButton.getY() - homeButton.getHeight()*2f);
        homeButton.setDisabled(true);
        homeButton.setVisible(false);
        homeButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.getHomeScreen());
                return true;
            }
        });
        adButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(game.getManager().get("video_icon.png",Texture.class))));
        adButton.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - adButton.getWidth()/2f, retryButton.getY() + adButton.getHeight()*2f);
        adButton.setDisabled(true);
        adButton.setVisible(false);
        this.scoreDisplay = new Label("Score: " + score + "m",new Skin(Gdx.files.internal("vhs/skin/vhs-ui.json")));
        scoreDisplay.setFontScale(FONT_SCALE);
        scoreDisplay.setPosition(Constants.VIEWPORT_WIDTH.getValue() - scoreDisplay.getWidth()* FONT_SCALE - PADDING,
                Constants.VIEWPORT_HEIGHT.getValue() - scoreDisplay.getHeight()* FONT_SCALE - PADDING);
        coinIcon = new Image(game.getManager().get("coin.png",Texture.class));
        coinIcon.setPosition(PADDING,Constants.VIEWPORT_HEIGHT.getValue() - coinIcon.getHeight() - PADDING);
        coinDisplay = new Label(" " + game.getCoins(),new Skin(Gdx.files.internal("vhs/skin/vhs-ui.json")));
        coinDisplay.setFontScale(FONT_SCALE);
        coinDisplay.setPosition(coinIcon.getX() + coinIcon.getWidth(),
                Constants.VIEWPORT_HEIGHT.getValue() - coinDisplay.getHeight()* FONT_SCALE - PADDING);
    }
    private void reset(){
        stopped = false;
        score = 0;
        speed = INITIAL_SPEED;
        spawnTime = INITIAL_SPAWN_TIME;
        elapsedTime = spawnTime;
        elapsedCoinTime = 0;
        for(Car car : cars){
            car.destroy();
            car.remove();
        }
        cars.clear();
        for(Coin coin : coins){
            coin.remove();
        }
        coins.clear();
        player.getBody().setLinearVelocity(0,0);
        player.getBody().setAngularVelocity(0);
        player.getBody().setTransform(new Vector2(WIDTH/2f,HEIGHT/2f),0);
        setGameOverVisible(false);
    }
    private void setGameOverVisible(boolean visible){
        if(visible){
            gameOver.toFront();
            retryButton.toFront();
            homeButton.toFront();
            adButton.toFront();
        }
        gameOver.setVisible(visible);
        retryButton.setVisible(visible);
        retryButton.setDisabled(!visible);
        homeButton.setVisible(visible);
        homeButton.setDisabled(!visible);
        adButton.setVisible(visible);
        adButton.setDisabled(!visible);
        background.setSpeed(0);
    }
    @Override
    public void show() {
        camera = new OrthographicCamera(WIDTH,HEIGHT);
        camera.position.set(WIDTH/2f,HEIGHT/2f,0);
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH.getValue() ,Constants.VIEWPORT_HEIGHT.getValue()));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);
        stage.addActor(new Image(game.getManager().get("backgrounds/background_marks.png",Texture.class)));
        background.setSpeed(speed);
        //Creating world.
        world = new World(new Vector2(0,0),true);
        world.setContactListener(new ContactListener() {

            private int areColliding(Contact contact,Object objectA, Object objectB) {
                if (contact.getFixtureA().getUserData().equals(objectA) && contact.getFixtureB().getUserData().equals(objectB))
                    return 0;
                else if (contact.getFixtureB().getUserData().equals(objectA) && contact.getFixtureA().getUserData().equals(objectB))
                    return 1;
                else
                    return -1;
            }
            @Override
            public void beginContact(Contact contact) {
                if(areColliding(contact,Player.getUserData(), Car.getUserData()) != -1){
                    lost = true;
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        GarageCars playerCar = GarageCars.getGarageCar(game.getOwnedCars().get(game.getSelectedCar()));
        player = new Player(stage,world,new Vector2(WIDTH/2f,HEIGHT/2f),playerCar.getImagePath(),game.getManager());
        reset();
        stage.addActor(adButton);
        stage.addActor(gameOver);
        stage.addActor(retryButton);
        stage.addActor(homeButton);
        player.addListener(new ActorGestureListener(){
            public void pan(InputEvent event,float x,float y,float deltaX,float deltaY){
                if(!stopped) {
                    player.getBody().setTransform(new Vector2(player.getBody().getPosition().x + deltaX / Constants.PPM.getValue(),
                            player.getBody().getPosition().y + deltaY / Constants.PPM.getValue()), player.getBody().getAngle());

                    Rectangle playerHitbox = new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());
                    Coin touchingCoin = null;
                    Rectangle coinHitbox = null;
                    for(Coin coin : coins){
                        coinHitbox = new Rectangle(coin.getX(),coin.getY(),coin.getWidth(),coin.getHeight());
                        if(playerHitbox.overlaps(coinHitbox)){
                            touchingCoin = coin;
                            break;
                        }
                    }
                    if(touchingCoin != null){
                        touchingCoin.remove();
                        coins.remove(touchingCoin);
                        int earnedCoins = 0;
                        game.setCoins(game.getCoins() + GarageCars.getGarageCar(game.getOwnedCars().get(game.getSelectedCar())).getCoinMultiplier());
                        coinDisplay.setText(" " + game.getCoins());
                        Preferences prefs = Gdx.app.getPreferences("My Preferences");
                        prefs.putInteger("coins",(int)game.getCoins());
                        prefs.flush();
                    }
                }
            }

        });
        stage.addActor(scoreDisplay);
        stage.addActor(coinIcon);
        stage.addActor(coinDisplay);
        coinDisplay.setText(" " + game.getCoins());
        scoreDisplay.setPosition(Constants.VIEWPORT_WIDTH.getValue() - scoreDisplay.getWidth()* FONT_SCALE - PADDING,
                Constants.VIEWPORT_HEIGHT.getValue() - scoreDisplay.getHeight()* FONT_SCALE - PADDING);
    }
    private float previousScore;
    private float positionScoreFactor;
    @Override
    public void render(float delta) {
        if(lost){
            stopped = true;
            lost = false;
            if(score > game.getHighscore()){
                Preferences prefs = Gdx.app.getPreferences("My Preferences");
                prefs.putInteger("highscore",(int)score);
                prefs.flush();
                game.setHighscore((int)score);
            }
            setGameOverVisible(true);
            return;
        }
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(!stopped) {
            previousScore = score;
            speed += delta * 0.18f;
            background.setSpeed(2*speed + 15);
            positionScoreFactor = ((int) (3 * player.getBody().getPosition().y / HEIGHT + 1));
            if(positionScoreFactor == 3)
                positionScoreFactor -= 0.5f;
            score += delta * game.getDifficulty() * Math.pow(speed*1.5f,positionScoreFactor);
            scoreDisplay.setText("Score: " + (int) score + "m");
            if (Integer.toString((int) score).length() > Integer.toString((int) previousScore).length())
                scoreDisplay.setPosition(scoreDisplay.getX() - 40, scoreDisplay.getY());
            spawnTime -= delta * 0.021;
            elapsedTime += delta;
            elapsedCoinTime += delta;
            if (elapsedTime >= spawnTime) {
                spawnCars();
                scoreDisplay.toFront();
                coinIcon.toFront();
                coinDisplay.toFront();
                elapsedTime = 0;
            }
            if (elapsedCoinTime >= COIN_SPAWN_TIME){
                elapsedCoinTime = 0;
                Random rand = new Random();
                Vector2 position = new Vector2(rand.nextFloat()*(Constants.VIEWPORT_WIDTH.getValue() - Coin.getSize()),
                        rand.nextFloat()*(Constants.VIEWPORT_HEIGHT.getValue() - Coin.getSize()));
                coins.add(new Coin(stage,position,game.getManager()));
            }
            for (Car car : cars)
                car.updateSpeed(speed);
            world.step(delta, 2, 6);
            for (Car car : cars) {
                if (car.getPosition().y < 0) {
                    car.destroy();
                    car.remove();
                    deletedCars.add(car);
                }
            }
            cars.removeAll(deletedCars);
            deletedCars.clear();
        }
        stage.act();
        camera.update();
        //Debugging
        //renderer.render(world,camera.combined);
        stage.draw();
    }

    private void spawnCars() {
        Random rand = new Random();
        float x = BLOCK_SPACING;
        Car car = null;
        boolean removed = false;
        List<Car> spawnedCars = new ArrayList<Car>();
        while(Float.compare(x ,WIDTH) < 0){
            if(Float.compare(rand.nextFloat(), game.getDifficulty()) <= 0) {
                car = new Car(stage, world, x, game.getManager());
                cars.add(car);
                spawnedCars.add(car);
            }
            else
                removed = true;
            x += (car != null ? car.getBodyWidth() : player.getWidth() / Constants.PPM.getValue()) + BLOCK_SPACING;
        }
        if(!removed){
            int randIndex = rand.nextInt(spawnedCars.size());
            car = spawnedCars.get(randIndex);
            car.destroy();
            car.remove();
            cars.remove(car);
        }
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
        player.destroy();
        for(Car car : cars)
            car.destroy();
        cars.clear();
        stage.dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        world.dispose();
    }
}
