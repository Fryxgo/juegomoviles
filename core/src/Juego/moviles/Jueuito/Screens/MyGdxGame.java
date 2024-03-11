package Juego.moviles.Jueuito.Screens;


import static Juego.moviles.Jueuito.Constantes.*;

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
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

import java.util.ArrayList;

import Juego.moviles.Jueuito.Constantes.DIR;
import Juego.moviles.Jueuito.Fruta;
import Juego.moviles.Jueuito.MainClass;
import Juego.moviles.Jueuito.Mapa;
import Juego.moviles.Jueuito.MyGestureListener;
import Juego.moviles.Jueuito.Serpiente;
import Juego.moviles.Jueuito.Sonidos;
import Juego.moviles.Jueuito.UICrea;

public class MyGdxGame implements Screen {

    /**
     * camara
     */
    private OrthographicCamera camera;
    /**
     * camara del Mapa
     */
    private OrthogonalTiledMapRenderer tmr;
    /**
     * rederizado de las hitbox
     */
    private Box2DDebugRenderer b2dr;
    /**
     * Wold donde se juega
     */
    private World world;
    Serpiente player, cuerpo;
    /**
     * booleana que indica si la serpiente se puede mover o no
     */
    boolean movimiento = true;
    /**
     *
     */
    long tini = 0;
    /**
     * Variables para la saber donde crear el nuevo cuerpo
     */
    float creaX, creaY;
    /**
     * Lista que contiene todos los cuerpos de la serpiente
     */
    ArrayList<Serpiente> cuerpos = new ArrayList<>();
    /**
     * booleana que indica si se ha chocado
     */
    boolean hit = false;
    /**
     * variables para conrolar la direccion
     */
    DIR direccion = DIR.NO_DIRECTION, direccionFinal = DIR.LEFT, direccionAnterior = DIR.NO_DIRECTION;
    /**
     * stage
     */
    Stage stage;
    /**
     * viewport
     */
    Viewport v;
    /**
     * variables booleanas para cuando crear un nuevo cuerpo de la serpiente  y para saber cuando la serpiete ha chocado
     * para gestionar el movimiento
     */
    boolean crear = false, colision = true;
//    Mapa mapa;
    /**
     * texturas de la serpiente
     */
    Texture imagenCabeza, imagenCuerpo;
    /**
     * Labels de la puntuacion el tiempo y Record
     */
    Label labelPuntuacion, labelTiempo, labelRecord;
    /**
     * clase para la gestion de los strigns
     */
    private I18NBundle lang;
    /**
     * variable de la fruta del juego
     */
    Fruta fruta;
    long time, segundos;
    /**
     * clase donde se ejecuta la screen
     */
    MainClass mainclass;
    /**
     * El archivo preference donde se guarda el record
     */
    Preferences record = Gdx.app.getPreferences("Records");
    /**
     * booleana pasa saber si el juego esta en pausa o no
     */
    boolean pause = false;
    /**
     * tamaño con el que se inicia la serpiente
     */
    int tamInicial = 3;
    /**
     * variable de la propia clase para poder enviarla en un evento
     */
    MyGdxGame juego = this;
    /**
     * clase sonidos, para gestionar los sonidos que quieras
     */
    Sonidos sonidos;

    /**
     * Boton de opciones y de puase
     */
    Button btnOptions, btn;
    /**
     * referencia al fondo de pantalla
     */
    Image fondo, mapa;

    /**
     * contructor de la Screen del juego crea e inicializa lo que se ve en pantalla
     * @param mainclass Clase main en donde se ejecutan las screen
     */
    public MyGdxGame(final MainClass mainclass) {

        Gdx.input.setCatchKey(Input.Keys.BACK, true);

        this.mainclass = mainclass;
        lang = I18NBundle.createBundle(Gdx.files.internal("Locale/Locale"));
        imagenCabeza = new Texture(Gdx.files.internal("Serpiente/cabeza.png"));
        imagenCuerpo = new Texture(Gdx.files.internal("Serpiente/cuerpo.png"));


        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);

        v = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        stage = new Stage(v);
        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();

        Skin skin = new Skin();
        skin.add("btnPause", new Texture(Gdx.files.internal("Buttons/BtnPeque.png")));
        skin.add("btnPlay", new Texture(Gdx.files.internal("Buttons/BtnGrande.png")));
        skin.add("Fondo", new Texture(Gdx.files.internal("Fondo/Fondo.png")));
        skin.add("Mapa", new Texture(Gdx.files.internal("Fondo/mapa.png")));

        fondo = UICrea.createImage(new Vector2(0,0), WORLD_WIDTH, WORLD_HEIGHT,skin,"Fondo",stage);
        mapa=UICrea.createImage(new Vector2(0,0), WORLD_WIDTH*0.8f, WORLD_HEIGHT,skin,"Mapa",stage);

        btn = UICrea.createTextButton("Pause", 15, new Vector2(WORLD_WIDTH*0.85f,  WORLD_HEIGHT*0.4f), 75, 60, skin, "btnPause", stage, 20);


        player = new Serpiente(32, 32, 525, 225, world, false);
        cuerpos.add(player);

        for (int i = 1; i < tamInicial; i++) {
            cuerpos.add(new Serpiente(32, 32, cuerpos.get(i - 1).getPosicionX() + 33, cuerpos.get(i - 1).getPosicionY(), world, true));
        }

        fruta = new Fruta(250, 225, world);

        time = System.currentTimeMillis();


        UICrea.createLabel(lang.get("main.score"), 25, Color.WHITE, new Vector2(WORLD_WIDTH*0.81f,  WORLD_HEIGHT*0.80f), stage);
        labelPuntuacion = UICrea.createLabel("0", 25, Color.WHITE, new Vector2(WORLD_WIDTH*0.89f,  WORLD_HEIGHT*0.75f), stage);
        labelTiempo = UICrea.createLabel("", 25, Color.WHITE, new Vector2(WORLD_WIDTH*0.85f,  WORLD_HEIGHT*0.73f), stage);


        labelRecord = UICrea.createLabel(String.format("Record %d", record.getInteger("HighScore")), 25, Color.WHITE, new Vector2(WORLD_WIDTH*0.81f,  WORLD_HEIGHT*0.61f), stage);

        /**
         * Listener del boton pause
         */
        btn.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pause = !pause;
                if (pause){
                    MainClass.music.pause();
                }else {
                    MainClass.music.play();
                }
            }
        });


        /**
         * Listener del boton opciones
         */
        btnOptions = UICrea.createTextButton(lang.get("mainmenu.settings"),20, new Vector2(WORLD_WIDTH*0.82f, WORLD_HEIGHT*0.2f),120,60,skin,"btnPlay", stage, 30);
        btnOptions.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(100);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                mainclass.setScreen(new Opciones(juego));
            }
        });

        sonidos = new Sonidos("Sonidos/comer.mp3");

    }


    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(Gdx.graphics.getDeltaTime());
        SpriteBatch batch = new SpriteBatch();

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
       v.update(width,height);
       stage.getViewport().update(width,height);

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

    /**
     * metodo para hacer dispos
     */
    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }

    /**
     * metodo para actualizar la camra y el mapa
     * @param delta
     */
    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        cameraUpdate();

        if (!hit) {

            setMovimiento(200);
        }


    }

    /**
     * Funcion para detectar colisiones de cabeza y cuerpo, o cabeza y fruta
     */
    public void hitContact() {

        world.setContactListener(new ContactListener() {
            /**
             * indica el principio del contacto
             * @param contact el contacto de 2 hitbox
             */
            @Override
            public void beginContact(Contact contact) {


                if (contact.getFixtureA().getUserData().equals("cabeza") && contact.getFixtureB().getUserData().equals("fruta")) {
                    crear = true;
                    colision = false;
                    Gdx.input.vibrate(100);
                    sonidos.play(false);

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

            /**
             *  indica el final del contacto
             * @param contact el contacto de 2 hitbox
             */
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

    /**
     * Funcion para hacer el moviemto de la serpiente, por gestos(!IsGiroscopio) o no
     * con cada uno de los miviemntos tambien llama a la funcion para comprbar colisiones
     * y la funcion que comprueba la cabeza
     * @param tick intervalo de tiempo para hacer cada movimiento
     */
    public void setMovimiento(float tick) {

        if (!hit && !pause) {

            if (!MainMenu.isGiroscopio) {
                
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
                    fruta.mover(posicionFruta());
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

    /**
     * Comprobacion del movimiento siguente
     * Detecta si el movimiento que esta siguiendo y el movimiento que le llega son contrarios
     * @return si los movimientos son contrarios devuelve false si no true
     */
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


    /**
     * Funcion que derecta la inclicacion del telefono(con el Acelerometro)
     * y genera una direccion para el movimiento de la serpiente segun la inclinacion la cual
     * tiene que ser mayor que 5 (la mitad de un eje)
     * @return devuelve un Enumerado DIR con la direccion
     */
    public DIR DireccionAcelerometro() {

        DIR giro = DIR.NO_DIRECTION;
         if (Math.abs(Gdx.input.getAccelerometerY())> Math.abs(Gdx.input.getAccelerometerZ())){
             if (Gdx.input.getAccelerometerY()>5){
                 giro = DIR.RIGHT;
             }
             if (Gdx.input.getAccelerometerY()<5){
                 giro = DIR.LEFT;
             }

         }else {
             if (Gdx.input.getAccelerometerZ()>5){
                 giro = DIR.DOWN;
             }
             if (Gdx.input.getAccelerometerZ()<5){
                 giro = DIR.UP;
             }
         }

         return giro;

    }

    /**
     * Esta funcion comprueba los limites de la pantalla, para cuando la cabeza de la serpiente llega
     * a cualquiera de esos 4 limites (arriba, abajo, izquierda, derecha), la envia al limite contrario
     */
    public void compruebaCabeza() {
        Serpiente cabeza = cuerpos.get(0);

        if (cabeza.getPosicionX() > (Gdx.graphics.getWidth() / 2)*0.78f) {
            cabeza.mover(30, cabeza.getPosicionY());
        } else {
            if (cabeza.getPosicionX() < 10) {
                cabeza.mover(  (Gdx.graphics.getWidth() / 2)*0.78f, cabeza.getPosicionY());
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
            x = (float) (Math.random() * WORLD_WIDTH*0.78f) + 35;
            y = (float) (Math.random() * WORLD_HEIGHT) + 35;

        } while (comprueba(x, y));

        return new Vector2(x, y);
    }

    /**
     * Crea un rectangulo con los 2 parametros y comprueba si se genera encima de cualquier parte de la serpiente
     * @param posiX Posicion x donde se genera el cuadrado
     * @param posiY Posicion y donde se genera el cuadrado
     * @return true si se genera encima, false si no
     */
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


    /**
     * Actualizacion de la camara
     */
    public void cameraUpdate() {
        Vector3 position = camera.position;
        camera.position.set(position);
        camera.update();

    }


}
