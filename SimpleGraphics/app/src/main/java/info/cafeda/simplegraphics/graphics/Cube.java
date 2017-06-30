package info.cafeda.simplegraphics.graphics;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Vu Nguyen on 6/27/2017.
 */

public class Cube implements DrawableObject {

    private float positions[] = {   // in counterclockwise order:
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
    private float colors[] = {
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
    private short drawOrder[] = {
            0, 1, 2, 0, 2, 3,
            4, 5, 6, 4, 6, 7,
            8, 9, 10, 8, 10, 11,
            12, 13, 14, 12, 14, 15,
            16, 17, 18, 16, 18, 19,
            20, 21, 22, 20, 22, 23
            }; // order to draw vertices

    private final FloatBuffer vertexBuffer;
    private final FloatBuffer colorBuffer;
    private final ShortBuffer drawListBuffer;
    private final int mProgram;

    public Cube() {
        // initialize vertex byte buffer for shape coordinates and colors
        ByteBuffer bb = ByteBuffer.allocateDirect(positions.length * 4);   // [float] : 4 bytes
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);          // [float] : 4 bytes
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);       // [short] : 2 bytes
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());
        cbb.order(ByteOrder.nativeOrder());
        dlb.order(ByteOrder.nativeOrder());
        // create a floating point buffer from the ByteBuffer s
        vertexBuffer = bb.asFloatBuffer();
        colorBuffer = cbb.asFloatBuffer();
        drawListBuffer = dlb.asShortBuffer();
        // add the coordinates  and colors to the FloatBuffer s
        vertexBuffer.put(positions);
        colorBuffer.put(colors);
        drawListBuffer.put(drawOrder);
        // set the buffers to read the begin
        vertexBuffer.position(0);// Another option is: vertexBuffer.rewind();
        colorBuffer.position(0);// Another option is:  colorBuffer.rewind();
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
        //Log.d("vertexShaderCompile:", GLES20.glGetShaderInfoLog(vertexShader));
        //Log.d("fragmentShaderCompile:", GLES20.glGetShaderInfoLog(vertexShader));

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
        //Log.d("mProgramLinkLog", GLES20.glGetProgramInfoLog(mProgram));
    }



    private int vertexCount = positions.length / COORDS_PER_VERTEX;
    private int colorsCount = colors.length / COLOR_VALUES_PER_VERTEX;

    public void draw(float[] mvpMatrix) {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member and fragment shader's vColor member
        int mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        int mColorHandle = GLES20.glGetAttribLocation(mProgram, "vColor");
        // Enable a handle to the position and color of triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mColorHandle);

        // Prepare the triangle coordinate and color data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        GLES20.glVertexAttribPointer(mColorHandle, COLOR_VALUES_PER_VERTEX, GLES20.GL_FLOAT, false, colorStride, colorBuffer);

        // get handle to shape's transformation matrix
        int mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Draw the triangle(s)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }

    @Override
    public void drawLine(float[] mvpMatrix) {

    }
}