package info.cafeda.simplegraphics;

import android.opengl.GLES20;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

public class Triangle implements DrawableObject {


    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COLOR_VALUES_PER_VERTEX = 4;
    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f, 0.5f, 0.0f, // top
            -0.5f, 0.0f, 0.0f, // bottom left
            0.5f, 0.0f, 0.0f  // bottom right
    };

    // Set color with red, green, blue and alpha (opacity) values
    float colors[] = {
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
    };
    private final short drawOrder[] = { 0, 1, 2 }; // order to draw vertices
    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    private final String vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
            "attribute vec3 vPosition;" +
            "attribute vec4 vColor;" +
            "varying vec4 color;" +
            "void main() "+
            "{" +
            "   gl_Position = uMVPMatrix * vec4(vPosition,1.0);" +
            "   color = vColor;"+
            "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
            "varying vec4 color;" +
            "void main()"+
            "{" +
            "  gl_FragColor = color;" +
            "}";

    public Triangle() {
        // initialize vertex byte buffer for shape coordinates and colors
        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4); //(float : 4 bytes))
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);// (float : 4 bytes)

        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());
        cbb.order(ByteOrder.nativeOrder());
        // create a floating point buffer from the ByteBuffer s
        vertexBuffer = bb.asFloatBuffer();
        colorBuffer = cbb.asFloatBuffer();
        // add the coordinates  and colors to the FloatBuffer s
        vertexBuffer.put(triangleCoords);
        colorBuffer.put(colors);
        // set the buffers to read the begin
        vertexBuffer.position(0);// Another option is: vertexBuffer.rewind();
        colorBuffer.position(0);// Another option is:  colorBuffer.rewind();

        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        //Creating shader
        //Allocate shader
        int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

        //set the shader's source
        GLES20.glShaderSource(vertexShader,vertexShaderCode);
        GLES20.glShaderSource(fragmentShader,fragmentShaderCode);

        //Compile shader
        GLES20.glCompileShader(vertexShader);
        GLES20.glCompileShader(fragmentShader);

        //Get status
        Log.d("vertexShaderCompile:", GLES20.glGetShaderInfoLog(vertexShader));
        Log.d("fragmentShaderCompile:", GLES20.glGetShaderInfoLog(vertexShader));

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
        Log.d("mProgramLinkLog", GLES20.glGetProgramInfoLog(mProgram));
    }



    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;       // 4 bytes per vertex
    private final int colorsCount = colors.length / COLOR_VALUES_PER_VERTEX;
    private final int colorStride = COLOR_VALUES_PER_VERTEX * 4; // 4 bytes per vertex

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member and fragment shader's vColor member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
        // Enable a handle to the position and color of triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray( mColorHandle);

        // Prepare the triangle coordinate and color data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        GLES20.glVertexAttribPointer(mColorHandle, COLOR_VALUES_PER_VERTEX, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        // Draw the triangle
        //GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }
}
