package Juego.moviles.Jueuito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class Constantes {

    public static final float WORLD_HEIGHT = 480;
    public static final float PPU = Gdx.graphics.getHeight() / WORLD_HEIGHT;
    public static final float WORLD_WIDTH = Gdx.graphics.getWidth() / PPU;

    /**
     * Enumerado con la direccion en vector de la serpiente
     */
    public static enum DIR {
        RIGHT (new Vector2(32.05f, 0)),
        LEFT (new Vector2(-32.05f,0)),
        UP  (new Vector2(0, -32.05f)),
        DOWN (new Vector2(0, 32.05f)),
        NO_DIRECTION (new Vector2(0,0));

        public final Vector2 vector2;

        private DIR(Vector2 vector2){
            this.vector2 = vector2;
        }

    }



}
