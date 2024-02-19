package Juego.moviles.Jueuito;

import static Juego.moviles.Jueuito.Constantes.PPM;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Random;

import Juego.moviles.Jueuito.Constantes.DIR;

public class MyGdxGame extends Game {

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


    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

    camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);

    world = new World(new Vector2(0, 0f), false);

    b2dr = new Box2DDebugRenderer();

        TiledMap tiledMap = new TmxMapLoader().load("Mapa.tmx");
        tmr = new OrthogonalTiledMapRenderer(tiledMap);
        mapa = new Mapa(world, tiledMap.getLayers().get("Colisiones").getObjects());

    player = new Serpiente(32, 32, 525/32, 225/32, world, false);
    cuerpos.add(player);

        for (int i = 1; i < 3; i++) {

        cuerpos.add(new Serpiente(32, 32, cuerpos.get(i - 1).getPosicionX() + 33, cuerpos.get(i - 1).getPosicionY(), world, true));
    }

    fruta = new Fruta( 250, 225, world);

}

    @Override
    public void render() {


        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b2dr.render(world, camera.combined.scl(PPM));



    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 2, height / 2);


    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
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

                if (colision) {
                    if ((contact.getFixtureA().getUserData().equals("cabeza") && contact.getFixtureB().getUserData().equals("cuerpo")) ||(contact.getFixtureA().getUserData().equals("Pared") && contact.getFixtureB().getUserData().equals("cabeza"))) {

                        hit = true;
                        Gdx.input.vibrate(500);

                    }
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

        if (!hit) {

            Gdx.input.setInputProcessor(new GestureDetector(new MyGestureListener()));
            direccion = MyGestureListener.direccion();

            if (movimiento) {
               if (direccion != DIR.NO_DIRECTION) {
                    if (!(direccionAnterior == DIR.DOWN && direccion == DIR.UP)) {
                        if (!(direccionAnterior == DIR.UP && direccion == DIR.DOWN)) {
                            if (!(direccionAnterior == DIR.LEFT && direccion == DIR.RIGHT)) {
                                if (!(direccionAnterior == DIR.RIGHT && direccion == DIR.LEFT)) {

                                    direccionFinal = direccion;
                                    movimiento = false;

                                }
                            }
                        }

                    }
                }
            }

            if (System.currentTimeMillis() - tini > tick) {


                movimiento = true;

                hitContact();

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
     * Genera una posicion random para la fruta,la cual no puede coincidir con la serpiente y tiene que ser multiplo de 32
     * @return Vector con la posicion
     */
    public Vector2 posicionFruta(){

        float x;
        float y;

        do {
            x = (float) (Math.random()*950)+50;

        }while (x%32 == 0  && comprueba(x, false));

        do {
            y = (float) (Math.random()*400)+50;

        }while (y%32 == 0  && comprueba(y, true));

        return new Vector2(x,y);
    }

    /**
     *Para comprobar que la posicion pasada no coincide con la posicion de ninguna parte de la serpiente
     * @param posicion
     * @return
     */
    public boolean comprueba( float posicion, boolean posiY){

        boolean pos = true;

        for (int i = 0; i < cuerpos.size(); i++) {

            if (posiY){
                pos = cuerpos.get(i).getPosicionY() != posicion;
            }else {
                pos = cuerpos.get(i).getPosicionX() != posicion;
            }

            if (!pos){
                return pos;

            }
        }
        return pos;
    }

    /**
     * genera un nuevo cubo y lo agrega al cuerpo de la serpiente
     */
    public void creaCuerpo(){

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
