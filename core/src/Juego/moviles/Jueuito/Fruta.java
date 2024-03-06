package Juego.moviles.Jueuito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Fruta {

    /**
     * Cuerpo
     */
    public Body body;
    public BodyDef def = new BodyDef();
    /**
     * Posicion en la que se encuentra actualemte el body
     */
    private float posicionX, posicionY;
    /**
     * Wold
     */
    World world;
    long tini;
    /**
     * boolean que indica si la fruta va a estar flotando o no
     */
    boolean flota = false;
    /**
     * Textura de la fruta
     */
    Texture gomugomu;

    /**
     * contructor que inicializa los parametros
     * @param posicionX posicion donde se va a crear en x
     * @param posicionY posicion donde se va a crear en y
     * @param world wold
     */

    public Fruta(float posicionX, float posicionY, World world) {
        this.world = world;
        this.posicionX = posicionX;
        this.posicionY = posicionY;

        crea();
    }


    public float getPosicionX() {
        return posicionX;
    }

    public float getPosicionY() {
        return posicionY;
    }

    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    public void setPosicionY(float posicionY) {
        this.posicionY = posicionY;
    }



    /**
     * creacion del body y añadirlo al wold
     */
    public void crea() {

        gomugomu = new Texture(Gdx.files.internal("Fruta/gomugomu.png"));

        def.position.set(posicionX, posicionY);
        def.fixedRotation = true;

        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25 / 2, 25 / 2);

        body.createFixture(shape, 1.0f);

        body.getFixtureList().get(0).setUserData("fruta");

    }

    /**
     * metodo para poder dibujar el sprite
     * @param batch el Spritebatch
     */
    public void draw(SpriteBatch batch) {

        if (System.currentTimeMillis() - tini > 500) {

                tini = System.currentTimeMillis();
            flota=!flota;
        }

        if (flota) {

            batch.draw(gomugomu, (getPosicionX() - 130) * 2, (getPosicionY() - 155) * 2, 600, 600);

        } else {

            batch.draw(gomugomu, (getPosicionX() - 130) * 2, (getPosicionY() - 165) * 2, 600, 600);
        }

    }


    /**
     * metodo para mover la furta de posicion
     * @param vector2 nuevo lugar al que se transporta
     */
    public void mover(Vector2 vector2) {

        setPosicionX(vector2.x);
        setPosicionY(vector2.y);

        body.setTransform(vector2.x, vector2.y, this.body.getAngle());

    }


}
