package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.actors.Car;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GarageScreen;
import com.mygdx.game.screens.HomeScreen;

import java.util.ArrayList;
import java.util.List;

public class MainGame extends Game {


	public static final float LIGHT_TRAFFIC_DIFFICULTY = 0.4f;
	public static final float MEDIUM_TRAFFIC_DIFFICULTY = 0.7f;
	public static final float HEAVY_TRAFFIC_DIFFICULTY = 0.9f;
	private AssetManager manager;
	private HomeScreen homeScreen;
	private GameScreen gameScreen;
	private GarageScreen garageScreen;
	private List<Integer> ownedCarsId;
	private int selectedCar;
	private float difficulty;
	private int highscore;
	private int coins;
	private Music backgroundMusic;

	public MainGame() { super(); }

	@Override
	public void create () {
		manager = new AssetManager();
		Preferences prefs = Gdx.app.getPreferences("My Preferences");
		this.setHighscore(prefs.getInteger("highscore"));
        this.setCoins(prefs.getInteger("coins"));
		ownedCarsId = new ArrayList<Integer>();
		String ownedCars = "0 1 2 3 4 5 6"/*prefs.getString("ownedCars")*/;
		if(ownedCars == ""){
			prefs.putString("ownedCars","0");
			prefs.putInteger("selectedCar",0);
			selectedCar = 0;
			ownedCarsId.add(0);
			prefs.flush();
		}
		else{
			String[] cars = ownedCars.split("\\s+");
			for(String car : cars)
				ownedCarsId.add(Integer.parseInt(car));
			selectedCar = prefs.getInteger("selectedCar");
		}
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background_music.wav"));
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
		setDifficulty(0);
		getManager().load("backgrounds/background.png",Texture.class);
		getManager().load("backgrounds/background_marks.png",Texture.class);
		getManager().load("backgrounds/garage_background.png",Texture.class);
		getManager().load("game-over.png",Texture.class);
		getManager().load("buttons/retry.png",Texture.class);
		getManager().load("buttons/green_button.png",Texture.class);
		getManager().load("buttons/yellow_button.png",Texture.class);
		getManager().load("buttons/red_button.png",Texture.class);
		getManager().load("buttons/blue_button.png",Texture.class);
		getManager().load("buttons/right_button.png",Texture.class);
		getManager().load("buttons/left_button.png",Texture.class);
		for(int i=0; i <= Car.CAR_NUMBER; i++)
			getManager().load("cars/car" + i + ".png",Texture.class);
		getManager().load("cars/motorcycle.png",Texture.class);
		getManager().load("game-logo.png",Texture.class);
		getManager().load("garage-icon.png",Texture.class);
		getManager().load("video_icon.png",Texture.class);
		getManager().load("coin.png",Texture.class);
		getManager().load("car_portrait.png",Texture.class);
		getManager().load("thumbnails/starter.png",Texture.class);
		getManager().load("thumbnails/fast.png",Texture.class);
		getManager().load("thumbnails/medium.png",Texture.class);
		getManager().load("thumbnails/motorcycle.png",Texture.class);
		getManager().load("thumbnails/yellow_car.png",Texture.class);
		getManager().load("thumbnails/red_car.png",Texture.class);
		getManager().load("thumbnails/sedan.png",Texture.class);
		getManager().finishLoading();
		this.setGameScreen(new GameScreen(this));
		this.setHomeScreen(new HomeScreen(this));
		this.setGarageScreen(new GarageScreen(this));
		this.setScreen(getHomeScreen());
	}

	public AssetManager getManager(){
		return manager;
	}
	@Override
	public void dispose () {
		super.dispose();
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public HomeScreen getHomeScreen() {
		return homeScreen;
	}

	public void setHomeScreen(HomeScreen homeScreen) {
		this.homeScreen = homeScreen;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public GarageScreen getGarageScreen() {
		return garageScreen;
	}

	public void setGarageScreen(GarageScreen garageScreen) {
		this.garageScreen = garageScreen;
	}

	public List<Integer> getOwnedCars() {
		return ownedCarsId;
	}

	public void setOwnedCars(List<Integer> ownedCarsId) {
		this.ownedCarsId = ownedCarsId;
	}

	public int getSelectedCar() {
		return selectedCar;
	}

	public void setSelectedCar(int selectedCar) {
		this.selectedCar = selectedCar;
	}

}
