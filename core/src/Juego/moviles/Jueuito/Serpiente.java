package Juego.moviles.Jueuito;


import static Juego.moviles.Jueuito.Constantes.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class Serpiente {

    private float posicionX, posicionY;

    private float posicionAnteriorX;

    public float getPosicionAnteriorX() {
        return posicionAnteriorX;
    }

    public void setPosicionAnteriorX(float posicionAnteriorX) {
        this.posicionAnteriorX = posicionAnteriorX;
    }

    public float getPosicionAnteriorY() {
        return posicionAnteriorY;
    }

    public void setPosicionAnteriorY(float posicionAnteriorY) {
        this.posicionAnteriorY = posicionAnteriorY;
    }

    private float posicionAnteriorY;
    float with, height;
    World world;

    boolean isCabeza;

    public Body body;
    public BodyDef def = new BodyDef();

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


    public void crea() {


        def.position.set(posicionX / PPM, posicionY / PPM);
        def.fixedRotation = true;
        if (isCabeza) {

            def.type = BodyDef.BodyType.DynamicBody;
        } else {
            def.type = BodyDef.BodyType.KinematicBody;
        }

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(with / 2 / PPM, height / 2 / PPM);

        Fixture fix = body.createFixture(shape, 1.0f);
        if (isCabeza) {
            fix.setUserData("cuerpo");
        } else {
            fix.setUserData("cabeza");
        }
    }

    public void draw(SpriteBatch batch, Texture imagen, boolean isCabeza) {


        if (isCabeza) {
            batch.draw(imagen, getPosicionX()*70+30, getPosicionY()*70+30, 500, 500);


        } else {

            batch.draw(imagen, getPosicionX()*70+30, getPosicionY()*70+30, 500, 500);

        }

    }


    public void mover(float x, float y) {


        setPosicionAnteriorX(this.getPosicionX());
        setPosicionAnteriorY(this.getPosicionY());

        setPosicionX(x);
        setPosicionY(y);

        this.body.setTransform(x, y, this.body.getAngle());

    }


}