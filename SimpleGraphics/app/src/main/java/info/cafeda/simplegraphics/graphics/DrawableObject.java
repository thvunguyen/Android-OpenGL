package info.cafeda.simplegraphics.graphics;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

public interface DrawableObject {
    // number of coordinates per vertex in this array
    int COORDS_PER_VERTEX = 3;
    int COLOR_VALUES_PER_VERTEX = 4;
    int vertexStride = COORDS_PER_VERTEX * 4;       // 4 bytes per vertex
    int colorStride = COLOR_VALUES_PER_VERTEX * 4; // 4 bytes per vertex

    String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec3 vPosition;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 color;" +
                    "void main() "+
                    "{" +
                    "   gl_Position = uMVPMatrix * vec4(vPosition,1.0);" +
                    "   color = vColor;"+
                    "}";

    String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 color;" +
                    "void main()"+
                    "{" +
                    "  gl_FragColor = color;" +
                    "}";

    void draw(float[] mvpMatrix);
    void drawLine(float[] mvpMatrix);
    boolean IsDrawLine();
}
