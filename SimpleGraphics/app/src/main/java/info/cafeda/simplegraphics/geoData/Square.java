package info.cafeda.simplegraphics.geoData;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

public class Square {

    public static float positions[] = {   // in counterclockwise order:
            -0.5f,  0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
             0.5f, -0.5f, 0.0f,  // bottom right
             0.5f,  0.5f, 0.0f, // top  right
    };
    // Set color with red, green, blue and alpha (opacity) values
    public static float colors[] = {
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
    };
    public static short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

}
