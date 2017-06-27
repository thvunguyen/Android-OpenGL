package info.cafeda.simplegraphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

class MyGLRenderer implements GLSurfaceView.Renderer {
    private Triangle mTriangle;
    private Square mSquare;
    private DrawableObject objectToDraw;
    private String itemName;
    private Vector3 eye, centre,up;
    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    public MyGLRenderer(String item){
        itemName = item;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        if(itemName.equals("triangle"))
            objectToDraw = new Triangle();
        if(itemName.equals("square"))
            objectToDraw = new Square();
        eye = new Vector3(0,0,-2);
        centre = new Vector3(0,0,0);
        up = new Vector3(0,1,0 );
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 5);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, eye.X, eye.Y, eye.Z, centre.X,centre.Y, centre.Z, up.X, up.Y, up.Z);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        //float[] scratch = new float[16];
        // Create a rotation transformation for the triangle
        //long time = SystemClock.uptimeMillis() % 4000L;
        //float angle = 0.090f * ((int) time);
        //Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 1.0f, 0);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw shape
        //mTriangle.draw(mMVPMatrix);
        objectToDraw.draw(mMVPMatrix);

    }

    public void setAngle(float angleLeft,float angleUp) {
        Transform.Left(angleLeft,eye,up);
        Transform.Up(angleUp,eye,up);
        //Log.d("GL","Eye: " + eye.toString() + "  Up: " + up.toString());
    }
}
