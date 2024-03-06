package Juego.moviles.Jueuito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sonidos{

   public static float volumen = 0.5f;
   private Sound sound;

    public Sonidos(String path) {
        sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public void play(boolean loop){
        long id = sound.play(volumen);
        sound.setLooping(id,loop);
    }
}
