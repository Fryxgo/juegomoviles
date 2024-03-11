package Juego.moviles.Jueuito;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import Juego.moviles.Jueuito.Screens.MainMenu;
import Juego.moviles.Jueuito.Screens.MyGdxGame;

public class MainClass extends Game {

    /**
     * variable estatica para poder cambiar el volumen de la musica
     */
    public static Music music;

    /**
     * constructor de la clase principal con la musica
     */
    @Override
    public void create() {
        setScreen(new MainMenu(this));
        music = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/song.mp3"));
        music.setVolume(Sonidos.volumen-0.2f);
        music.setLooping(true);
        music.play();
    }

    /**
     * metodo de renderizado
     */
    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
