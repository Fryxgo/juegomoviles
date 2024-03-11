package Juego.moviles.Jueuito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sonidos{

   public static float volumen = 0.5f;
   private Sound sound;

    public Sonidos(String path) {
        sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    /**
     * Funcion para poder hacer el play del sonido
     * @param loop si se repite o no el sonido
     */
    public void play(boolean loop){
        long id = sound.play(volumen+0.1f);
        sound.setLooping(id,loop);
    }
}
