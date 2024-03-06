package Juego.moviles.Jueuito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class UICrea {
    private static FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/FonteysCAPS-05regular.otf"));
    private static FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private static BitmapFont font;

    /**
     * Creacion de un boton
     *
     * @param pos      posicion del boton
     * @param width    ancho del boton
     * @param height   alto del boton
     * @param skin     texturas a usar
     * @param drawable textura especifica
     * @param stage    stage donde se añade para dibujarlo
     * @return
     */
    public static Button createButton(Vector2 pos, float width, float height, Skin skin, String drawable, Stage stage) {
        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.up = skin.getDrawable(drawable);
        Button button = new Button(buttonStyle);
        button.setBounds(pos.x, pos.y, width, height);
        stage.addActor(button);
        return button;
    }

    /**
     * Creacion de un boton con texto
     *
     * @param text     texto a mostrar
     * @param textSize tamaño del texto
     * @param pos      posicion boton
     * @param width    ancho del boton
     * @param height   alto del boton
     * @param skin     texturas a usar
     * @param drawable textura especifica
     * @param stage    stage donde se añade para dibujarlo
     * @return
     */
    public static TextButton createTextButton(String text, int textSize, Vector2 pos, float width, float height, Skin skin, String drawable, Stage stage, int pad) {
        parameter.size = textSize;
        font = generator.generateFont(parameter);

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable(drawable);
        TextButton textButton = new TextButton(text, textButtonStyle);
        textButton.setBounds(pos.x, pos.y, width, height);
        textButton.getLabelCell().padBottom(pad);
        stage.addActor(textButton);
        return textButton;
    }

    /**
     * Creacion de un boton con imagen
     *
     * @param pos       posicion del boton
     * @param width     ancho del boton
     * @param height    alto del boton
     * @param skin      texturas a usar
     * @param drawable  textura especifica
     * @param drawable textura de la imagen del boton
     * @param stage     stage donde se añade para dibujarlo
     * @return
     */
    public static ImageButton createImageButton(Vector2 pos, float width, float height, Skin skin, String drawable, Stage stage) {
        ImageButtonStyle imageButtonStyle = new ImageButtonStyle();
        imageButtonStyle.up = skin.getDrawable(drawable);
        imageButtonStyle.imageUp = skin.getDrawable(drawable);
        imageButtonStyle.imageUp.setMinWidth(width - 10);
        imageButtonStyle.imageUp.setMinHeight(height - 10);
        ImageButton imageButton = new ImageButton(imageButtonStyle);
        imageButton.setBounds(pos.x, pos.y, width, height);
        stage.addActor(imageButton);
        return imageButton;
    }

    /**
     * Creacion de una etiqueta
     *
     * @param text      texto de la etiqueta
     * @param textSize  tamaño del texto
     * @param fontColor color de la fuente
     * @param pos       posicion de la etiqueta
     * @param stage     stage donde se añade para dibujarlo
     * @return
     */
    public static Label createLabel(String text, int textSize, Color fontColor, Vector2 pos, Stage stage) {
        parameter.size = textSize;
        font = generator.generateFont(parameter);

        LabelStyle labelStyle = new LabelStyle();
        labelStyle.font = font;
        labelStyle.fontColor = fontColor;
        Label label = new Label(text, labelStyle);
        label.setPosition(pos.x, pos.y);
        stage.addActor(label);
        return label;
    }

    /**
     * Creacion de una imagen
     *
     * @param pos      posicion de una imagen
     * @param width    ancho de una imagen
     * @param height   alto de una imagen
     * @param skin     texturas a usar
     * @param drawable textura especifica
     * @param stage    stage donde se añade para dibujarlo
     * @return
     */
    public static Image createImage(Vector2 pos, float width, float height, Skin skin, String drawable, Stage stage) {
        Image image = new Image(skin, drawable);
        image.setBounds(pos.x, pos.y, width, height);
        stage.addActor(image);
        return image;
    }
}
