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

    public Body body;
    public BodyDef def = new BodyDef();
    private float posicionX, posicionY;

    private Sprite sprite;
    SpriteBatch batch;

    World world;
    boolean isgomu;
    long tini;
    boolean flota = false;

    public Fruta(float posicionX, float posicionY, World world, boolean isgomu) {
        this.world = world;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.isgomu = isgomu;

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

    Texture gomugomu, carne;


    public void crea() {

        gomugomu = new Texture(Gdx.files.internal("Fruta/gomugomu.png"));
        carne = new Texture(Gdx.files.internal("Fruta/carne.png"));

        def.position.set(posicionX, posicionY);
        def.fixedRotation = true;

        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(25 / 2, 25 / 2);

        body.createFixture(shape, 1.0f);

        body.getFixtureList().get(0).setUserData("fruta");

    }

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

    public void mover(Vector2 vector2) {

        setPosicionX(vector2.x);
        setPosicionY(vector2.y);

        body.setTransform(vector2.x, vector2.y, this.body.getAngle());

    }


}
