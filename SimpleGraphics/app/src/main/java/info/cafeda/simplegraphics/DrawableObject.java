package info.cafeda.simplegraphics;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

public interface DrawableObject {
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COLOR_VALUES_PER_VERTEX = 4;
    static final int vertexStride = COORDS_PER_VERTEX * 4;       // 4 bytes per vertex
    static final int colorStride = COLOR_VALUES_PER_VERTEX * 4; // 4 bytes per vertex

    static final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec3 vPosition;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 color;" +
                    "void main() "+
                    "{" +
                    "   gl_Position = uMVPMatrix * vec4(vPosition,1.0);" +
                    "   color = vColor;"+
                    "}";

    static final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 color;" +
                    "void main()"+
                    "{" +
                    "  gl_FragColor = color;" +
                    "}";

    public void draw(float[] mvpMatrix);
    public void drawLine(float[] mvpMatrix);
}
