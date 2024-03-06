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
import Juego.moviles.Jueuito.UICrea;

public class Credits implements Screen {

    private OrthographicCamera camera;
    I18NBundle lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));
    Stage stage;
    Viewport v;
    Label lblsprites, lblmapa, lblmusica,lblfuente,lblayuda;
    Button btnAtras;

    Image fondo;
    MainClass mainclass;

    /**
     * Costructor del menu, inicializa lo que se ve en pantalla
     * @param mainclass
     */
    public Credits(final MainClass mainclass) {
        this.mainclass = mainclass;

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

        fondo = UICrea.createImage(new Vector2(0,0),w/2,h/2,skin,"Fondo",stage);
        lblsprites = UICrea.createLabel(lang.get("credit.sprite"),20,Color.BLACK,new Vector2(w/10,h/3),stage);

        btnAtras = UICrea.createTextButton(lang.get("setting.back"), 25, new Vector2(w / 3 + 250, h / 3 + 80), 100, 70, skin, "btnPlay", stage, 30);

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

        lblayuda = UICrea.createLabel(lang.get("credit.help"),20,Color.BLACK,new Vector2(w/10,h/3-40),stage);
        lblfuente = UICrea.createLabel(lang.get("credit.font"),20,Color.BLACK,new Vector2(w/10,h/3-80),stage);
        lblmusica = UICrea.createLabel(lang.get("credit.music"),20,Color.BLACK,new Vector2(w/10,h/3-120),stage);
        lblmapa = UICrea.createLabel(lang.get("credit.map"),20,Color.BLACK,new Vector2(w/10,h/3-160),stage);






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
    }

    /**
     * metodo para liberar memoria
     */
    @Override
    public void dispose() {
        stage.dispose();
        this.dispose();
    }
}
