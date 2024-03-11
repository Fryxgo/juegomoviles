package Juego.moviles.Jueuito.Screens;

import static Juego.moviles.Jueuito.Constantes.WORLD_HEIGHT;
import static Juego.moviles.Jueuito.Constantes.WORLD_WIDTH;

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
import Juego.moviles.Jueuito.UICrea;

public class  Credits implements Screen {


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
     * viewport
     */
    Viewport v;
    /**
     * label para los creditos
     */
    Label lblayuda;
    /**
     * boton para volver
     */
    Button btnAtras;

    /**
     * imagen de fondo
     */
    Image fondo;
    /**
     * clase principal
     */
    MainClass mainclass;
    /**
     * skin
     */
    Skin skin = new Skin();

    /**
     * Costructor del menu, inicializa lo que se ve en pantalla
     * @param mainclass
     */
    public Credits(final MainClass mainclass) {
        this.mainclass = mainclass;


        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);

        v = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        stage = new Stage(v);


        skin.add("btnPlay", new Texture(Gdx.files.internal("Buttons/BtnGrande.png")));
        skin.add("btnPeque", new Texture(Gdx.files.internal("Buttons/BtnPeque.png")));
        skin.add("Fondo", new Texture(Gdx.files.internal("Fondo/Fondo.png")));

        fondo = UICrea.createImage(new Vector2(0,0),WORLD_WIDTH, WORLD_HEIGHT,skin,"Fondo",stage);

        btnAtras = UICrea.createTextButton(lang.get("setting.back"), 25, new Vector2(WORLD_WIDTH*0.9f, WORLD_HEIGHT*0.8f), 100, 70, skin, "btnPlay", stage, 30);

        btnAtras.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mainclass.setScreen(new MainMenu(mainclass));
            }
        });

        lblayuda = UICrea.createLabel(lang.get("credit.help"),30,Color.BLACK,new Vector2(WORLD_WIDTH*0.1f, WORLD_HEIGHT*0.3f),stage);

    }

    @Override
    public void show() {
    }

    /**
     * metodo de renderizado
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
