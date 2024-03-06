package Juego.moviles.Jueuito;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class Serpiente {


    /**
     * Textura de la cabeza con la boca abierta
     */
    Texture bocaabiertga = new Texture(Gdx.files.internal("Serpiente/bocaabierta.png"));

    long tini;
    /**
     * variable utilizada para hacer el cambio de sprite
     */
    boolean flota = false;
    /**
     * posiociones de x e y actuales
     */
    private float posicionX, posicionY;
    /**
     * posicion que tenia antes de moverse en x
     */
    private float posicionAnteriorX;

    public float getPosicionAnteriorX() {
        return posicionAnteriorX;
    }

    public void setPosicionAnteriorX(float posicionAnteriorX) {
        this.posicionAnteriorX = posicionAnteriorX;
    }

    /**
     * posicion que tenia antes de moverse en y
     */
    private float posicionAnteriorY;
    public float getPosicionAnteriorY() {
        return posicionAnteriorY;
    }

    public void setPosicionAnteriorY(float posicionAnteriorY) {
        this.posicionAnteriorY = posicionAnteriorY;
    }

    /**
     * alto y ancho del cuadrado de la hitbox
     */
    float with, height;
    /**
     * El mundo
     */
    World world;
    /**
     * booleana para saber si el body es la cabeza o no
     */
    boolean isCabeza;
    /**
     * Cuerpo
     */
    public Body body;
    public BodyDef def = new BodyDef();

    /**
     * constructor de Serpiente que inicializa los parametros
     * @param with ancho de la hitbox
     * @param height alto de la hitbox
     * @param posicionX posicion donde se genera en x
     * @param posicionY posicion donde se genera en x
     * @param world El mundo donde se crea
     * @param isCabeza boolean para saber si el opjeto es la cabeza o no
     */
    public Serpiente(float with, float height, float posicionX, float posicionY, World world, boolean isCabeza) {
        setPosicionX(posicionX);
        setPosicionY(posicionY);
        this.world = world;
        this.with = with;
        this.height = height;
        this.isCabeza = isCabeza;

        crea();
    }

    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    public float getPosicionX() {
        return posicionX;
    }

    public void setPosicionY(float posicionY) {
        this.posicionY = posicionY;
    }

    public float getPosicionY() {
        return posicionY;
    }

    /**
     * Crea el body todas las hitbox de la serpiente y las añade al world
     */
    public void crea() {

        def.position.set(posicionX, posicionY);
        def.fixedRotation = true;
        if (isCabeza) {

            def.type = BodyDef.BodyType.DynamicBody;
        } else {
            def.type = BodyDef.BodyType.KinematicBody;
        }

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(with / 2, height / 2);

        Fixture fix = body.createFixture(shape, 1.0f);
        if (isCabeza) {
            fix.setUserData("cuerpo");
        } else {
            fix.setUserData("cabeza");
        }

    }

    /**
     *
     * @param batch el bach para poder dibujar el sprite
     * @param imagen la imagen a representar
     * @param isCabeza boolean que indica si la imagen a pintar es la cabeza o no
     */
    public void draw(SpriteBatch batch, Texture imagen, boolean isCabeza) {

        if (isCabeza) {
            if (System.currentTimeMillis() - tini > 500) {

                tini = System.currentTimeMillis();
                flota = !flota;
            }

            if (flota) {

                batch.draw(imagen, ((getPosicionX() - 70) * 2f), ((getPosicionY() - 130) * 2), 450, 450);

            } else {

                batch.draw(bocaabiertga, ((getPosicionX() - 70) * 2f), ((getPosicionY() - 130) * 2), 450, 450);
            }

        } else {

            batch.draw(imagen, ((getPosicionX() - 100) * 2), ((getPosicionY() - 130) * 2), 450, 450);

        }

    }

    /**
     * Funcion para poder mover con SetTransform() cada una de las hitbox
     * @param x posicion a la que se mueve en x
     * @param y poscion a la que se mueve en y
     */
    public void mover(float x, float y) {


        setPosicionAnteriorX(this.getPosicionX());
        setPosicionAnteriorY(this.getPosicionY());

        setPosicionX(x);
        setPosicionY(y);

        this.body.setTransform(x, y, this.body.getAngle());

    }


}