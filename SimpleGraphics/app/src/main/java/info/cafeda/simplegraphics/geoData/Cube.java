package info.cafeda.simplegraphics.geoData;

/**
 * Created by Vu Nguyen on 6/27/2017.
 */

public class Cube{

    public static float positions[] = {   // in counterclockwise order:
            -0.5f,  -0.5f, 0.5f, //Red
            -0.5f,   0.5f, 0.5f,
             0.5f,   0.5f, 0.5f,
             0.5f,  -0.5f, 0.5f,
            -0.5f,  -0.5f, -0.5f, //Green
            -0.5f,   0.5f, -0.5f,
             0.5f,   0.5f, -0.5f,
             0.5f,  -0.5f, -0.5f,
            -0.5f,  -0.5f, -0.5f, //Blue
            -0.5f,  -0.5f,  0.5f,
            -0.5f,   0.5f,  0.5f,
            -0.5f,   0.5f, -0.5f,
             0.5f,  -0.5f, -0.5f, //White
             0.5f,  -0.5f,  0.5f,
             0.5f,   0.5f,  0.5f,
             0.5f,   0.5f, -0.5f,
            -0.5f,   0.5f, -0.5f, //Yellow
            -0.5f,   0.5f,  0.5f,
             0.5f,   0.5f,  0.5f,
             0.5f,   0.5f, -0.5f,
            -0.5f,  -0.5f, -0.5f, // Magenta
            -0.5f,  -0.5f,  0.5f,
             0.5f,  -0.5f,  0.5f,
             0.5f,  -0.5f, -0.5f,
            };
    // Set color with red, green, blue and alpha (opacity) values
    public static float colors[] = {
            1.0f, 0.0f, 0.0f, 1.0f,//Red
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,//Green
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,//Blue
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,//White
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,//Yellow
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,//Magenta
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            };
    public static short drawOrder[] = {
            0, 1, 2, 0, 2, 3,
            4, 5, 6, 4, 6, 7,
            8, 9, 10, 8, 10, 11,
            12, 13, 14, 12, 14, 15,
            16, 17, 18, 16, 18, 19,
            20, 21, 22, 20, 22, 23
            }; // order to draw vertices

}
