package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Constants;
import com.mygdx.game.MainGame;
import com.mygdx.game.MusicPlayer;

public class HomeScreen extends BaseScreen {

    private static final float BUTTON_SPACING = 200f;
    private static final float BUTTON_WIDTH = 300f;
    private static final float LOGO_OFFSET_Y = 380f;
    private static final float PADDING = 50;
    private Stage stage;
    private Image background;
    private Image gameLogo;
    private Image green;
    private Image yellow;
    private Image red;
    private Image blue;
    private Image coinIcon;
    private Label coinDisplay;
    private TextButton lightTraffic;
    private TextButton mediumTraffic;
    private TextButton heavyTraffic;
    private TextButton enterGarage;
    private Label maxScore;

    public HomeScreen(final MainGame game) {
        super(game);
        background = new Image(game.getManager().get("backgrounds/background.png",Texture.class));
        gameLogo = new Image(game.getManager().get("game-logo.png",Texture.class));
        gameLogo.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - gameLogo.getWidth()/2f,
                Constants.VIEWPORT_HEIGHT.getValue()/2f - gameLogo.getHeight()/2f + LOGO_OFFSET_Y);
        Skin skin = new Skin(Gdx.files.internal("vhs/skin/vhs-ui.json"));
        heavyTraffic = new TextButton("Heavy Traffic",skin);
        red = new Image(game.getManager().get("buttons/red_button.png",Texture.class));
        setButton(heavyTraffic,null, MainGame.HEAVY_TRAFFIC_DIFFICULTY,red);
        mediumTraffic = new TextButton("Medium Traffic",skin);
        yellow = new Image(game.getManager().get("buttons/yellow_button.png",Texture.class));
        setButton(mediumTraffic,heavyTraffic,MainGame.MEDIUM_TRAFFIC_DIFFICULTY,yellow);
        lightTraffic = new TextButton("Light Traffic",skin);
        green = new Image(game.getManager().get("buttons/green_button.png",Texture.class));
        setButton(lightTraffic,mediumTraffic,MainGame.LIGHT_TRAFFIC_DIFFICULTY,green);
        maxScore = new Label("Max score: " + game.getHighscore() + "m",skin);
        maxScore.setFontScale(2f);
        maxScore.setPosition(Constants.VIEWPORT_WIDTH.getValue() - maxScore.getWidth()*2 - PADDING,
                Constants.VIEWPORT_HEIGHT.getValue() - maxScore.getHeight()*2 - PADDING);
        coinIcon = new Image(game.getManager().get("coin.png",Texture.class));
        coinIcon.setPosition(PADDING,Constants.VIEWPORT_HEIGHT.getValue() - coinIcon.getHeight() - PADDING);
        coinDisplay = new Label(" " + game.getCoins(),new Skin(Gdx.files.internal("vhs/skin/vhs-ui.json")));
        coinDisplay.setFontScale(2f);
        coinDisplay.setPosition(coinIcon.getX() + coinIcon.getWidth(),
                Constants.VIEWPORT_HEIGHT.getValue() - coinDisplay.getHeight()* 2f - PADDING);
        enterGarage = new TextButton("Garage",skin);
        enterGarage.setTransform(true);
        enterGarage.setSize(BUTTON_WIDTH,enterGarage.getHeight());
        enterGarage.setScale(2f);
        blue = new Image(game.getManager().get("buttons/blue_button.png",Texture.class));
        enterGarage.setPosition(lightTraffic.getX(), lightTraffic.getY() - enterGarage.getHeight() - BUTTON_SPACING);
        enterGarage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(game.getGarageScreen());
                return true;
            }
        });
        blue.setPosition(enterGarage.getX() - 5,enterGarage.getY() - 30);
        blue.setSize(BUTTON_WIDTH*2 + 50, enterGarage.getHeight()*2 + 60);

    }
    private void setButton(TextButton button, TextButton topButton, final float difficulty, Image background){

        button.setTransform(true);
        button.setSize(BUTTON_WIDTH,button.getHeight());
        button.setScale(2f);
        if(topButton != null)
            button.setPosition(topButton.getX(), topButton.getY() - button.getHeight() - BUTTON_SPACING);
        else
            button.setPosition(Constants.VIEWPORT_WIDTH.getValue()/2f - button.getWidth(),
                    Constants.VIEWPORT_HEIGHT.getValue()/2f  - button.getHeight()/2f);
        button.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setDifficulty(difficulty);
                game.setScreen(game.getGameScreen());
                return true;
            }
        });
        background.setPosition(button.getX() - 5,button.getY() - 30);
        background.setSize(BUTTON_WIDTH*2 + 50, button.getHeight()*2 + 60);
    }
    @Override
    public void show() {
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH.getValue() , Constants.VIEWPORT_HEIGHT.getValue()));
        Gdx.input.setInputProcessor(stage);
        stage.addActor(background);
        stage.addActor(gameLogo);
        stage.addActor(green);
        stage.addActor(yellow);
        stage.addActor(red);
        stage.addActor(blue);
        stage.addActor(lightTraffic);
        stage.addActor(mediumTraffic);
        stage.addActor(heavyTraffic);
        stage.addActor(enterGarage);
        stage.addActor(maxScore);
        stage.addActor(coinIcon);
        stage.addActor(coinDisplay);
        coinDisplay.setText(" " + game.getCoins());
        maxScore.setText("Max score: " + game.getHighscore() + "m");
        maxScore.setPosition(Constants.VIEWPORT_WIDTH.getValue() - maxScore.getText().length*38 - PADDING,
                Constants.VIEWPORT_HEIGHT.getValue() - maxScore.getHeight()*2 - PADDING);
        coinDisplay.setText(" " + game.getCoins());
        this.musicPlayer.setTrack(MusicPlayer.MusicTrack.MainMenu);
        this.musicPlayer.play();
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
        this.musicPlayer.stop();
    }

    @Override
    public void dispose() {
        Gdx.gl.glClearColor(0.4f,0.5f,0.8f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.dispose();
        this.musicPlayer.disposeTrack();
    }
}
