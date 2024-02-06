package Juego.moviles.Jueuito;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Random;

import Juego.moviles.Jueuito.Constantes.DIR;

public class MyGdxGame extends Game {

    private OrthographicCamera camera;

    private Box2DDebugRenderer b2dr;
    private World world;

    Serpiente player, cuerpo;

    public float ppm = 32;

    boolean movimiento = true;

    long tini = 0;

    float x, y, creaX, creaY;
    ArrayList<Serpiente> cuerpos = new ArrayList<>(3);

    boolean hit = false;

    DIR direccion = DIR.NO_DIRECTION, direccionFinal = DIR.LEFT, direccionAnterior = DIR.NO_DIRECTION;

    Fruta fruta;

    boolean crear = false, colision = true;


    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);

        world = new World(new Vector2(0, 0f), false);

        b2dr = new Box2DDebugRenderer();

        player = new Serpiente(32, 32, 0, 0, world, false);
        cuerpos.add(player);

        for (int i = 1; i < 8; i++) {

            cuerpos.add(new Serpiente(32, 32, cuerpos.get(i - 1).getPosicionX() + 33, cuerpos.get(i - 1).getPosicionY(), world, true));
        }

        fruta = new Fruta(-200, 0, world);
    }

    @Override
    public void render() {

        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
Gdx.app.log("fruta",fruta.body.getPosition().x+":"+fruta.body.getPosition().y);
        b2dr.render(world, camera.combined.scl(ppm));
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

        if (!hit) {

            setMovimiento(1000);
        }


    }


    public void hitContact() {

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Gdx.app.log("hit", contact.getFixtureA().getUserData() + " " + contact.getFixtureB().getUserData());

                if (contact.getFixtureA().getUserData().equals("cabeza") && contact.getFixtureB().getUserData().equals("fruta")) {


                    Gdx.app.log("hit", "hit");
                    crear = true;
                    colision = false;

                }

                if (colision) {
                    if (contact.getFixtureA().getUserData().equals("cabeza") && contact.getFixtureB().getUserData().equals("cuerpo")) {

                        hit = true;

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

            Gdx.input.setInputProcessor(new GestureDetector(new
            if ((direccion = MyGestureListener.direccion())MyGestureListener()));
            if (movimiento) { != DIR.NO_DIRECTION) {
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

                switch (direccionFinal) {
                    case RIGHT:
                        x = 1.1f;
                        y = 0;
                        break;
                    case LEFT:
                        x = -1.1f;
                        y = 0;
                        break;
                    case UP:
                        x = 0;
                        y = -1.1f;
                        break;
                    case DOWN:
                        x = 0;
                        y = 1.1f;
                        break;

                }

                movimiento = true;


                hitContact();

                if (crear) {

                    Gdx.app.log("x", fruta.getPosicionX()+"");
                    Gdx.app.log("y", fruta.getPosicionY()+"");

                    float x = (float) (Gdx.graphics.getWidth() / 2 - Math.random() * Gdx.graphics.getWidth() / 2);
                    float y = (float) (Gdx.graphics.getHeight() / 2 - Math.random() * Gdx.graphics.getHeight() / 2);


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

                    fruta.destruir(200, 0);
                    cuerpos.add(new Serpiente(32, 32, creaX, creaY, world, true));


                    crear = false;

                    Gdx.app.log("x", fruta.getPosicionX()+"");
                    Gdx.app.log("y", fruta.getPosicionY()+"");
                }


                cuerpos.get(0).mover(player.getPosicionX() + x, player.getPosicionY() + y);

                for (int i = 1; i < cuerpos.size(); i++) {

                    cuerpos.get(i).mover(cuerpos.get(i - 1).getPosicionAnteriorX(), cuerpos.get(i - 1).getPosicionAnteriorY());
                }

                tini = System.currentTimeMillis();
                direccionAnterior = direccionFinal;
            }

        }

    }


    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = 0;
        position.y = 0;
        camera.position.set(position);
        camera.update();

    }


}
