package Juego.moviles.Jueuito.Screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.sql.Time;
import java.text.Format;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import javax.swing.DebugGraphics;

import Juego.moviles.Jueuito.Constantes.DIR;
import Juego.moviles.Jueuito.Fruta;
import Juego.moviles.Jueuito.MainClass;
import Juego.moviles.Jueuito.Mapa;
import Juego.moviles.Jueuito.MyGestureListener;
import Juego.moviles.Jueuito.Serpiente;
import Juego.moviles.Jueuito.UICreator;

public class MyGdxGame implements Screen {

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tmr;
    private Box2DDebugRenderer b2dr;
    private World world;
    Serpiente player, cuerpo;
    boolean movimiento = true;
    long tini = 0;
    float x, y, creaX, creaY;
    ArrayList<Serpiente> cuerpos = new ArrayList<>(3);
    boolean hit = false;
    DIR direccion = DIR.NO_DIRECTION, direccionFinal = DIR.LEFT, direccionAnterior = DIR.NO_DIRECTION;
    Fruta fruta;
    boolean crear = false, colision = true;
    Mapa mapa;
    Texture imagenCabeza;
    Texture imagenCuerpo;
    Stage stage;
    Viewport v;
    Label labelPuntuacion, labelTiempo, labelRecord;
    private I18NBundle lang;
    long time, segundos;
    long minutos = 0;
    MainClass mainclass;
    int records;
    Preferences record = Gdx.app.getPreferences("Records");
    boolean pause = false;
    boolean isGiroscopio;

    public MyGdxGame(final MainClass mainclass, boolean isGiroscopio) {

        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        this.isGiroscopio = isGiroscopio;
        this.mainclass = mainclass;
        lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));
        imagenCabeza = new Texture(Gdx.files.internal("Serpiente/cabeza.png"));
        imagenCuerpo = new Texture(Gdx.files.internal("Serpiente/cuerpo.png"));

        float x = Gdx.graphics.getWidth();
        float y = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, x / 2, y / 2);

        v = new ExtendViewport(x / 2, y / 2, camera);
        stage = new Stage(v);
        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();

        TiledMap tiledMap = new TmxMapLoader().load("Mapa.tmx");
        tmr = new OrthogonalTiledMapRenderer(tiledMap);
        mapa = new Mapa(world, tiledMap.getLayers().get("Colisiones").getObjects());

        player = new Serpiente(32, 32, 525, 225, world, false);
        cuerpos.add(player);

        for (int i = 1; i < 8; i++) {
            cuerpos.add(new Serpiente(32, 32, cuerpos.get(i - 1).getPosicionX() + 33, cuerpos.get(i - 1).getPosicionY(), world, true));
        }

        fruta = new Fruta(250, 225, world, false);

        time = System.currentTimeMillis();


        UICreator.createLabel(lang.get("main.score"), 30, Color.WHITE, new Vector2(x / 3 + 200, y / 3 + 100), stage);
        labelPuntuacion = UICreator.createLabel("0", 30, Color.WHITE, new Vector2(x / 3 + 255, y / 3 + 70), stage);
        labelTiempo = UICreator.createLabel("", 30, Color.WHITE, new Vector2(x / 3 + 230, y / 3 + 30), stage);


        labelRecord = UICreator.createLabel(String.format("Record %d", record.getInteger("HighScore")), 30, Color.WHITE, new Vector2(x / 3 + 200, y / 3 - 30), stage);

        Skin skin = new Skin();
        skin.add("btnPause", new Texture(Gdx.files.internal("Buttons/BtnPeque.png")));
        Button btn = UICreator.createTextButton("Pause", 20, new Vector2(x / 2 - 150, y / 6 + 30), 75, 60, skin, "btnPause", stage, 20);

        btn.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pause = !pause;
            }
        });


    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(Gdx.graphics.getDeltaTime());
        SpriteBatch batch = new SpriteBatch();

        tmr.render();

        stage.getCamera().update();
        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        labelPuntuacion.setText((cuerpos.size() - 3) * 10 + "");

        segundos = (System.currentTimeMillis() - time) / 1000;

        labelTiempo.setText(String.format("%02.0f : %02.0f", (float) (segundos / 60), (float) (segundos % 60)));


        batch.begin();
        cuerpos.get(0).draw(batch, imagenCabeza, true);
        for (int i = 1; i < cuerpos.size(); i++) {

            cuerpos.get(i).draw(batch, imagenCuerpo, false);
        }

        fruta.draw(batch);
        batch.end();
        batch.dispose();

        InputMultiplexer toroide = new InputMultiplexer();
        toroide.addProcessor(stage);
        toroide.addProcessor(new GestureDetector(new MyGestureListener()));

        Gdx.input.setInputProcessor(toroide);

        b2dr.render(world, camera.combined);
        b2dr.setDrawBodies(false);

    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 2, height / 2);


    }

    @Override
    public void pause() {
        pause = true;
    }

    @Override
    public void resume() {
        pause = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        this.dispose();
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        cameraUpdate(delta);

        tmr.setView(camera);
        tmr.render();

        if (!hit) {

            setMovimiento(200);
        }


    }


    public void hitContact() {

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {


                if (contact.getFixtureA().getUserData().equals("cabeza") && contact.getFixtureB().getUserData().equals("fruta")) {
                    crear = true;
                    colision = false;
                    Gdx.input.vibrate(100);
                }
                if ((contact.getFixtureA().getUserData().equals("cabeza") && contact.getFixtureB().getUserData().equals("cuerpo"))) {
                    Gdx.app.log("hit", "" + contact.getFixtureA().getUserData() + contact.getFixtureB().getUserData());
                    hit = true;
                    Gdx.input.vibrate(500);

                    if (record.getInteger("HighScore") < Integer.parseInt(labelPuntuacion.getText().toString())) {
                        record.putInteger("HighScore", Integer.parseInt(labelPuntuacion.getText().toString()));

                    }
                    record.flush();

                    mainclass.setScreen(new GameOver(mainclass));
                }
            }

            @Override
            public void endContact(Contact contact) {

                colision = true;
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }


    public void setMovimiento(float tick) {

        if (!hit && !pause) {

            if (!isGiroscopio) {
                
                direccion = MyGestureListener.direccion();

                if (compruebaDireccion()) {

                    if (movimiento) {

                        direccionFinal = direccion;
                        movimiento = false;
                    }
                }
            } else {
                        direccion = DireccionAcelerometro();

                if (compruebaDireccion()) {
                    if (movimiento) {

                        direccionFinal = direccion;
                        movimiento = false;
                    }
                }
            }


            if (System.currentTimeMillis() - tini > tick) {
                movimiento = true;

                hitContact();

                compruebaCabeza();

                if (crear) {
                    //  Mover la fruta, a la cual se le pasa un vector con la (X) y la (Y) a la que moverse
                    fruta.mover(posicionFruta());

                    // Añade cuerpo al array

                    creaCuerpo();

                    crear = false;

                }


                cuerpos.get(0).mover(player.getPosicionX() + direccionFinal.vector2.x, player.getPosicionY() + direccionFinal.vector2.y);

                for (int i = 1; i < cuerpos.size(); i++) {

                    cuerpos.get(i).mover(cuerpos.get(i - 1).getPosicionAnteriorX(), cuerpos.get(i - 1).getPosicionAnteriorY());
                }

                tini = System.currentTimeMillis();
                direccionAnterior = direccionFinal;
            }

        }

    }

    public boolean compruebaDireccion() {

        if (direccion != DIR.NO_DIRECTION) {
            if (!(direccionAnterior == DIR.DOWN && direccion == DIR.UP)) {
                if (!(direccionAnterior == DIR.UP && direccion == DIR.DOWN)) {
                    if (!(direccionAnterior == DIR.LEFT && direccion == DIR.RIGHT)) {
                        if (!(direccionAnterior == DIR.RIGHT && direccion == DIR.LEFT)) {


                            return true;

                        }
                    }
                }
            }
        }

        return false;
    }


    public DIR DireccionAcelerometro() {


        Gdx.app.log("Z", Gdx.input.getAccelerometerY() + "");


        return direccionFinal;
    }

    public void compruebaCabeza() {
        Serpiente cabeza = cuerpos.get(0);

        if (cabeza.getPosicionX() > Gdx.graphics.getWidth() / 2 - 250) {
            cabeza.mover(30, cabeza.getPosicionY());
        } else {
            if (cabeza.getPosicionX() < 10) {
                cabeza.mover(Gdx.graphics.getWidth() / 2 - 250, cabeza.getPosicionY());
            }
        }

        if (cabeza.getPosicionY() > Gdx.graphics.getHeight() / 2) {
            cabeza.mover(cabeza.getPosicionX(), 30);
        } else {
            if (cabeza.getPosicionY() < 10) {

                cabeza.mover(cabeza.getPosicionX(), Gdx.graphics.getHeight() / 2);
            }
        }

    }

    /**
     * Genera una posicion random para la fruta,la cual no puede coincidir con la serpiente y tiene que ser multiplo de 32
     *
     * @return Vector con la posicion a la que se va a mover la fruta
     */
    public Vector2 posicionFruta() {

        Vector2 vector2 = new Vector2();
        float x;
        float y;

        do {

            x = (float) (Math.random() * 870) + 35;
            y = (float) (Math.random() * 480) + 35;

        } while (comprueba(x, y));

        return new Vector2(x, y);
    }


    public boolean comprueba(float posiX, float posiY) {

        float Serx;
        float Sery;

        for (int i = 0; i < cuerpos.size(); i++) {

            Serx = cuerpos.get(i).getPosicionX();
            Sery = cuerpos.get(i).getPosicionY();


            Rectangle rectangle1 = new Rectangle(Serx, Sery, 32, 32);
            Rectangle rectangle2 = new Rectangle(posiX, posiY, 25, 25);


            if (Intersector.overlaps(rectangle1, rectangle2)) {
                return true;
            }

        }

        return false;
    }

    /**
     * genera un nuevo cubo y lo agrega al cuerpo(Array Cuerpos de tipo Serpiente) de la serpiente
     */
    public void creaCuerpo() {

        switch (direccionFinal) {
            case DOWN:
                creaX = cuerpos.get(cuerpos.size() - 1).getPosicionAnteriorX();
                creaY = cuerpos.get(cuerpos.size() - 1).getPosicionAnteriorY() - 33;
                break;
            case UP:
                creaX = cuerpos.get(cuerpos.size() - 1).getPosicionAnteriorX();
                creaY = cuerpos.get(cuerpos.size() - 1).getPosicionAnteriorY() + 33;
                break;
            case RIGHT:
                creaX = cuerpos.get(cuerpos.size() - 1).getPosicionAnteriorX() - 33;
                creaY = cuerpos.get(cuerpos.size() - 1).getPosicionAnteriorY();
                break;
            case LEFT:
                creaX = cuerpos.get(cuerpos.size() - 1).getPosicionAnteriorX() + 33;
                creaY = cuerpos.get(cuerpos.size() - 1).getPosicionAnteriorY();
                break;
        }


        cuerpos.add(new Serpiente(32, 32, creaX, creaY, world, true));

    }


    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        camera.position.set(position);
        camera.update();

    }


}
