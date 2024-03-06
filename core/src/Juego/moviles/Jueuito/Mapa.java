package Juego.moviles.Jueuito;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class Mapa {

    /**
     * constrtcutor del mapa
     * @param world el mundo
     * @param mapObjects
     */
    public Mapa(World world, MapObjects mapObjects) {

        for (MapObject object : mapObjects) {

        Shape shape = null;
        Body body;
        BodyDef def =  new BodyDef();
        Fixture fixture;
        def.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(def);

        fixture = body.createFixture(shape, 1f);
        fixture.setUserData("Pared");
        shape.dispose();
        }


    }


}
