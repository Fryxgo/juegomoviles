package Juego.moviles.Jueuito;

import static Juego.moviles.Jueuito.Constantes.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Fruta {

    public Body body;
    public BodyDef def = new BodyDef();
    private float posicionX, posicionY;

    private Sprite sprite;
    SpriteBatch batch;

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

    Texture imagenTexture;

    public void crea() {

        imagenTexture = new Texture(Gdx.files.internal("Fruta/gomugomu.png"));


        def.position.set(posicionX / PPM, posicionY / PPM);
        def.fixedRotation = true;

        def.type = BodyDef.BodyType.DynamicBody;

        body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(32 / 2 / PPM, 32 / 2 / PPM);

        body.createFixture(shape, 1.0f);


        body.getFixtureList().get(0).setUserData("fruta");

    }

    public void draw(SpriteBatch batch) {

        batch.draw(imagenTexture, body.getPosition().x*32-32, -body.getPosition().y*32+32, 1500,1500);


    }

    public void mover(Vector2 vector2) {

        setPosicionX(vector2.x);
        setPosicionY(vector2.y);

        body.setTransform(vector2.x / PPM, vector2.y / PPM, this.body.getAngle());


    }


}
