package aaaaaaa.aaa.aaa;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class MyGdxGame extends Game {

    private OrthographicCamera camera;

    private Box2DDebugRenderer b2dr;
    private World world;
    private Body player, platform;

    public float ppm = 32;

    int velocity = 1;


    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);

        world = new World(new Vector2(0, 0f), false);

        b2dr = new Box2DDebugRenderer();

        player = createBox(2, 10, 32, 32, false);
        platform = createBox(0, 0, 64, 32, true);

    }

    @Override
    public void render() {

        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

        inputUpdate(delta);
        cameraUpdate(delta);



    }

    public void inputUpdate(float delta) {

        Gdx.input.setInputProcessor(new GestureDetector(new MyGestureListener()));

        switch (MyGestureListener.direccion()) {
            case "d":
                player.setLinearVelocity(velocity, 0);
                break;
            case "-d":
                player.setLinearVelocity(-velocity, 0);
                break;
            case "y":
                player.setLinearVelocity(0, -velocity);
                break;
            case "-y":
                player.setLinearVelocity(0, velocity);
                break;
            default:
                player.setLinearVelocity(player.getLinearVelocity().x, player.getLinearVelocity().y);
                break;

        }


    }

    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = platform.getPosition().x * ppm;
        position.y = platform.getPosition().y * ppm;
        camera.position.set(position);
        camera.update();

    }

    public Body createBox(int x, int y, int with, int height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();
        if (isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x / ppm, y / ppm);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(with / 2 / ppm, height / 2 / ppm);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }


}
