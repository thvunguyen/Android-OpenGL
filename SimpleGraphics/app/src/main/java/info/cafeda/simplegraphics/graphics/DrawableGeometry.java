package info.cafeda.simplegraphics.graphics;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Vu Nguyen on 6/28/2017.
 */

public class DrawableGeometry implements  DrawableObject{
    private float[] positions;
    private float[] colors;
    private short[] drawOrder;
    private float[] transformMatrix = new float[16]; // local transformation matrix of object (Coordination of object)
    private float[] mMatrix = new float[16];        // Temporary matrix to store result of mutiply of global and local transformation
    private boolean isGLInit = false;
    private boolean isDrawLine = false;

    public DrawableGeometry(float[] positions, float[] colors, short[] drawOrder, float[] transformMatrix, boolean isDrawLine){
        this.positions = new float[positions.length];
        System.arraycopy(positions,0,this.positions,0,positions.length);
        this.colors = new float[colors.length];
        System.arraycopy(colors,0,this.colors,0,colors.length);
        this.drawOrder = new short[drawOrder.length];
        System.arraycopy(drawOrder,0,this.drawOrder,0,drawOrder.length);
        System.arraycopy(transformMatrix,0,this.transformMatrix,0,16);
        this.isDrawLine = isDrawLine;
    }

    public DrawableGeometry(float[] positions, float[] colors, short[] drawOrder, float[] transformMatrix){
        this.positions = new float[positions.length];
        System.arraycopy(positions,0,this.positions,0,positions.length);
        this.colors = new float[colors.length];
        System.arraycopy(colors,0,this.colors,0,colors.length);
        this.drawOrder = new short[drawOrder.length];
        System.arraycopy(drawOrder,0,this.drawOrder,0,drawOrder.length);
        System.arraycopy(transformMatrix,0,this.transformMatrix,0,16);
    }

    public DrawableGeometry(float[] positions, float[] colors, short[] drawOrder){
        this.positions = new float[positions.length];
        System.arraycopy(positions,0,this.positions,0,positions.length);
        this.colors = new float[colors.length];
        System.arraycopy(colors,0,this.colors,0,colors.length);
        this.drawOrder = new short[drawOrder.length];
        System.arraycopy(drawOrder,0,this.drawOrder,0,drawOrder.length);
        Matrix.setIdentityM(transformMatrix,0);// If there is not an transform matrix, set local one to identity
        //Log.d("STATUS", "1 init");
    }

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;
    private ShortBuffer drawListBuffer;
    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    public void GLInit()
    {
        int colorsCount = colors.length / COLOR_VALUES_PER_VERTEX;

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
        isGLInit = true;
    }

    public void draw(float[] mvpMatrix){
        if (isGLInit){
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

            Matrix.multiplyMM(mMatrix,0,mvpMatrix,0,transformMatrix,0);
            // get handle to shape's transformation matrix
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMatrix, 0);

            // Draw the triangle(s)
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length,GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(mPositionHandle);
            GLES20.glDisableVertexAttribArray(mColorHandle);
        }
    }
    public boolean IsDrawLine(){
        return isDrawLine;
    }
    @Override
    public void drawLine(float[] mvpMatrix) {
        if (isGLInit){
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

            Matrix.multiplyMM(mMatrix,0,mvpMatrix,0,transformMatrix,0);
            // get handle to shape's transformation matrix
            mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

            // Pass the projection and view transformation to the shader
            GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMatrix, 0);

            // Draw the triangle(s)
            GLES20.glDrawElements(GLES20.GL_LINES, drawOrder.length,GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(mPositionHandle);
            GLES20.glDisableVertexAttribArray(mColorHandle);
        }
    }
}
