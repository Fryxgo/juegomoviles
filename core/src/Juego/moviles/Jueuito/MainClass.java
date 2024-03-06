package Juego.moviles.Jueuito;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import Juego.moviles.Jueuito.Screens.MainMenu;
import Juego.moviles.Jueuito.Screens.MyGdxGame;

public class MainClass extends Game {

    public static Music music;
    @Override
    public void create() {

        setScreen(new MainMenu(this));
        music = Gdx.audio.newMusic(Gdx.files.internal("Sonidos/song.mp3"));
        music.setVolume(Sonidos.volumen);
        music.setLooping(true);
        music.play();

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
