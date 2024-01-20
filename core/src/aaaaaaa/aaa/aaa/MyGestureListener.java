package aaaaaaa.aaa.aaa;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MyGestureListener implements GestureDetector.GestureListener {


    static float puntoInicioX, puntoInicioY, puntoFinalX, puntoFinalY;
    static boolean touch = false;


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

    public static String direccion() {


        if (touch) {



            touch = false;

            float x = comparar(puntoInicioX, puntoFinalX);
            float y = comparar(puntoInicioY, puntoFinalY);

            if (direcionFinal(x, y)) {
                if (x > 0) {
                    return "d";
                }
                return "-d";
            }

            if (y > 0) {
                return "y";
            }
            return "-y";
        }
        return "";
    }

    public static float comparar(float principio, float fin) {

        return fin - principio;

    }


    public static boolean direcionFinal(float x, float y) {

        return Math.abs(x) > Math.abs(y);

    }

}

