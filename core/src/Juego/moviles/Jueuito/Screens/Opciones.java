package Juego.moviles.Jueuito.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Juego.moviles.Jueuito.MainClass;
import Juego.moviles.Jueuito.Sonidos;
import Juego.moviles.Jueuito.UICreator;


public class Opciones implements Screen {


    private OrthographicCamera camera;
    I18NBundle lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));
    Stage stage;
    Viewport v;
    Button btnGiroscopio, btnAtras, btnVolumenUp, btnVolumenDown, btnMainMenu;
    Label lblGiroscopio, lblVol, lblVolumen;
    MainClass mainClass;
    MyGdxGame juego;


    public Opciones(MyGdxGame juego) {
        this.juego = juego;
        mainClass = juego.mainclass;
        init();
    }

    public Opciones(final MainClass mainClass) {
        this.mainClass = mainClass;

        init();

    }

    public void init() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);

        v = new ExtendViewport(w / 2, h / 2, camera);
        stage = new Stage(v);

        Skin skin = new Skin();
        skin.add("btnPlay", new Texture(Gdx.files.internal("Buttons/BtnGrande.png")));
        skin.add("btnPeque", new Texture(Gdx.files.internal("Buttons/BtnPeque.png")));
        skin.add("Fondo", new Texture(Gdx.files.internal("Fondo/Fondo.png")));

        Image fondo = UICreator.createImage(new Vector2(0, 0), w / 2, h / 2, skin, "Fondo", stage);
        btnAtras = UICreator.createTextButton(lang.get("setting.back"), 25, new Vector2(w / 3 + 250, h / 3 + 80), 100, 70, skin, "btnPlay", stage, 30);

        btnAtras.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (juego != null) {
                    mainClass.setScreen(juego);
                } else {

                    mainClass.setScreen(new MainMenu(mainClass));
                }
            }

        });

        lblGiroscopio = UICreator.createLabel("", 30, Color.BLACK, new Vector2(w / 5 + 120, h / 7 + 20), stage);
        lblGiroscopio.setColor(Color.WHITE);
        btnGiroscopio = UICreator.createTextButton(lang.get("settings.gyro"), 30, new Vector2(w / 4, h / 7 - 100), 200, 80, skin, "btnPlay", stage, 40);
        btnGiroscopio.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MainMenu.isGiroscopio = !MainMenu.isGiroscopio;
            }

        });

        btnMainMenu = UICreator.createTextButton(lang.get("setting.menu"), 25, new Vector2(w / 3 + 250, h / 3), 100, 70, skin, "btnPlay", stage, 35);
        btnMainMenu.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {


                    mainClass.setScreen(new MainMenu(mainClass));
            }
        });


        lblVol = UICreator.createLabel("", 40, Color.BLACK, new Vector2(w / 7, h / 7 - 50), stage);
        lblVolumen = UICreator.createLabel(lang.get("settings.volume"), 35, Color.BLACK, new Vector2(w / 7 - 55, h / 7), stage);
        btnVolumenUp = UICreator.createTextButton("+", 40, new Vector2(w / 7 + 70, h / 7 - 100), 80, 80, skin, "btnPeque", stage, 30);
        btnVolumenDown = UICreator.createTextButton("-", 40, new Vector2(w / 8 - 80, h / 7 - 100), 80, 80, skin, "btnPeque", stage, 30);

        btnVolumenUp.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Sonidos.volumen < 1f) {
                    Sonidos.volumen += 0.1f;
                    MainClass.music.setVolume(Sonidos.volumen);
                }
            }

        });

        btnVolumenDown.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (Sonidos.volumen > 0) {
                    Sonidos.volumen -= 0.1f;
                    MainClass.music.setVolume(Sonidos.volumen);
                }
            }

        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector3 position = camera.position;
        camera.position.set(position);
        camera.update();

        stage.getCamera().update();
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        Gdx.input.setInputProcessor(stage);

        if (MainMenu.isGiroscopio) {
            lblGiroscopio.setText(lang.get("settings.gyro") + " [X]");
        } else {
            lblGiroscopio.setText(lang.get("settings.gyro") + " [ ]");
        }

        lblVol.setText(((int) (Sonidos.volumen * 10)) + "");

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

    }

    @Override
    public void dispose() {
        stage.dispose();
        this.dispose();
    }
}
