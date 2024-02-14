package Juego.moviles.Jueuito;

import static Juego.moviles.Jueuito.Constantes.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Fruta {

    public Body body;
    public BodyDef def = new BodyDef();
    private float posicionX, posicionY;

    World world;

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

    public void crea() {


        def.position.set(posicionX / PPM, posicionY / PPM);
        def.fixedRotation = true;

        def.type = BodyDef.BodyType.DynamicBody;


        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / 2 / PPM, 32 / 2 / PPM);

        body.createFixture(shape, 1.0f);


        body.getFixtureList().get(0).setUserData("fruta");


    }

    public void mover(Vector2 vector2) {


        setPosicionX(vector2.x);
        setPosicionY(vector2.y);
       this.body.setTransform(vector2.x/PPM, vector2.y/PPM, this.body.getAngle());


    }


}
