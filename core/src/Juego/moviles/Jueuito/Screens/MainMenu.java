package Juego.moviles.Jueuito.Screens;

import static Juego.moviles.Jueuito.Constantes.WORLD_HEIGHT;
import static Juego.moviles.Jueuito.Constantes.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Juego.moviles.Jueuito.MainClass;
import Juego.moviles.Jueuito.UICrea;

public class MainMenu implements Screen {
    /**
     * camara
     */
    private OrthographicCamera camera;
    /**
     * clase para la gestion de los strigns
     */
    I18NBundle lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));
    /**
     * stage
     */
    Stage stage;
    /**
     * veiwport
     */
    Viewport v;
    /**
     * botones de la clase
     */
    Button btnPlay, btnOptions, btnCreditos, btnAyuda;
    /**
     * clase principal
     */
    MainClass mainClass;
    /**
     * boolean pasa saber si utilizar el giroscopio(acelerometro) o no
     */
    public static boolean isGiroscopio = false;

    /**
     * skin
     */
    Skin skin = new Skin();

    /**
     * Costructor del menu, inicializa lo que se ve en pantalla
     * @param mainClass
     */
    public MainMenu(final MainClass mainClass) {

        this.mainClass = mainClass;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);

        v = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        stage = new Stage(v);


        skin.add("btnPlay", new Texture(Gdx.files.internal("Buttons/BtnGrande.png")));
        skin.add("Fondo", new Texture(Gdx.files.internal("Fondo/Fondo.png")));
        Image fondo = UICrea.createImage(new Vector2(0,0),WORLD_WIDTH, WORLD_HEIGHT,skin,"Fondo",stage);
        btnPlay = UICrea.createTextButton(lang.get("mainmenu.start"), 30, new Vector2(WORLD_WIDTH*0.38f,  WORLD_HEIGHT*0.6f), 250, 90, skin, "btnPlay", stage, 50);
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

        btnOptions = UICrea.createTextButton(lang.get("mainmenu.settings"),30, new Vector2(WORLD_WIDTH*0.40f,  WORLD_HEIGHT*0.35f),200,90,skin,"btnPlay", stage, 50);
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

        btnCreditos= UICrea.createTextButton(lang.get("mainmenu.creds"),30, new Vector2(WORLD_WIDTH*0.50f,  WORLD_HEIGHT*0.13f),200,90,skin,"btnPlay", stage, 50);
        btnCreditos.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                mainClass.setScreen(new Credits(mainClass));

            }
        });
        btnAyuda= UICrea.createTextButton(lang.get("mainmenu.help"),30, new Vector2(WORLD_WIDTH*0.30f,  WORLD_HEIGHT*0.13f),200,90,skin,"btnPlay", stage, 50);

        btnAyuda.addListener(new InputListener(){

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                mainClass.setScreen(new Help(mainClass));

            }
        });
    }


    @Override
    public void show() {

    }

    /**
     * funcion de renderizado
     * @param delta The time in seconds since the last render.
     */
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
        dispose();
    }

    /**
     * metodo para liberar memoria
     */
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
