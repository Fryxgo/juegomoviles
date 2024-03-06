package Juego.moviles.Jueuito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import Juego.moviles.Jueuito.Constantes.DIR;

public class MyGestureListener implements GestureDetector.GestureListener {


    static float puntoInicioX, puntoInicioY, puntoFinalX, puntoFinalY;
    static boolean touch = false;

    /**
     *
     * @param x posicion en al tocar por primera vez en x
     * @param y poscion en al tocar por primera vvez en y
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        puntoInicioX = x;
        puntoInicioY = y;

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }



    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    /**
     *
     * @param x posiocion en la que esta tocando en x
     * @param y posicion en la que esta tocando en y
     * @param deltaX la diferencia en pixels del arrastre en x
     * @param deltaY la diferencia en pixels del arrastre en y
     * @return
     */
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        puntoFinalX = x;
        puntoFinalY = y;

        touch = true;
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom(float originalDistance, float currentDistance) {
        return false;
    }


    @Override
    public boolean pinch(Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
        return false;
    }

    @Override
    public void pinchStop() {
    }


    /**
     * Hace comprovaciones con el valor de x e y para saber cual es mayor y si son positivos
     * y negativos para determinar en que direccion se va a mover
     * @return un Valor del enumerado DIR que determina la direccion
     */
    public static Constantes.DIR direccion() {


        if (touch) {


            touch = false;

            float x = comparar(puntoInicioX, puntoFinalX);
            float y = comparar(puntoInicioY, puntoFinalY);

            if (direcionFinal(x, y)) {
                if (x > 0) {
                    return DIR.RIGHT;
                }
                return DIR.LEFT;
            }

            if (y > 0) {
                return DIR.UP;
            }
            return DIR.DOWN;
        }
        return DIR.NO_DIRECTION;
    }

    /**
     *
     * @param principio principio del drag
     * @param fin fin del drag
     * @return un numero con el valor del recorrido
     */
    public static float comparar(float principio, float fin) {

        return fin - principio;

    }

    /**
     * Direcion en la que se va a mover si es en el eje x o en el y
     * @param x comparacion del principio con el fin del movimiento en x
     * @param y comparacion del principio con el fin del movimiento en y
     * @return el valor mas alto entre x e y
     */
    public static boolean direcionFinal(float x, float y) {

        return Math.abs(x) > Math.abs(y);

    }

}

