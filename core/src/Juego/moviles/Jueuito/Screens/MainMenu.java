package Juego.moviles.Jueuito.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Juego.moviles.Jueuito.MainClass;
import Juego.moviles.Jueuito.UICreator;
import sun.jvm.hotspot.utilities.Assert;

public class MainMenu implements Screen {
    private OrthographicCamera camera;
    I18NBundle lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));
    Stage stage;
    Viewport v;
    Button btnPlay, btnOptions;
    MainClass mainClass;
    public static boolean isGiroscopio = false;

    public MainMenu(final MainClass mainClass) {

        this.mainClass = mainClass;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);

        v = new ExtendViewport(w / 2, h / 2, camera);
        stage = new Stage(v);

        Skin skin = new Skin();
        skin.add("btnPlay", new Texture(Gdx.files.internal("Buttons/BtnGrande.png")));
        skin.add("Fondo", new Texture(Gdx.files.internal("Fondo/Fondo.png")));
        Image fondo = UICreator.createImage(new Vector2(0,0),w/2,h/2,skin,"Fondo",stage);
        btnPlay = UICreator.createTextButton(lang.get("mainmenu.start"), 30, new Vector2(w / 5, h / 5), 250, 90, skin, "btnPlay", stage, 50);
        btnPlay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mainClass.setScreen(new MyGdxGame(mainClass));
            }

        });

        btnOptions = UICreator.createTextButton(lang.get("mainmenu.settings"),30, new Vector2(w / 5+25, h / 8-15),200,90,skin,"btnPlay", stage, 50);
        btnOptions.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                mainClass.setScreen(new Opciones(mainClass));

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
        this.dispose();
        stage.dispose();
    }
}
