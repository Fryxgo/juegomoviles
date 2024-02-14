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

    public Mapa(World world, MapObjects mapObjects) {

        for (MapObject object : mapObjects) {

        Shape shape = null;
        Body body;
        BodyDef def =  new BodyDef();
        Fixture fixture;
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);


        if (object instanceof PolylineMapObject)  {
            shape = create((PolylineMapObject) object);

        }else{
            continue;
        }

        fixture = body.createFixture(shape, 1f);
        shape.dispose();
        }


    }

    private ChainShape create(PolylineMapObject polygon){

        float[] vertices = polygon.getPolyline().getTransformedVertices();
        Vector2[] woldVertices = new Vector2[vertices.length/2];

        for (int i = 0; i < woldVertices.length; i++) {
            woldVertices[i] = new Vector2(vertices[i*2]/10f, vertices[i*2+1]/10f);
        }

        ChainShape chainShape = new ChainShape();
        chainShape.createChain(woldVertices);

        return chainShape;
    }
}
