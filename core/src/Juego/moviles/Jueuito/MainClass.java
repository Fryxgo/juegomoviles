package Juego.moviles.Jueuito;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import Juego.moviles.Jueuito.Screens.MainMenu;
import Juego.moviles.Jueuito.Screens.MyGdxGame;

public class MainClass extends Game {
    @Override
    public void create() {

        setScreen(new MainMenu(this));

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
