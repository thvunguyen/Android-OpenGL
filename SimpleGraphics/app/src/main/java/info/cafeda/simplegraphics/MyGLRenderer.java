package info.cafeda.simplegraphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import info.cafeda.simplegraphics.graphics.Cube;
import info.cafeda.simplegraphics.graphics.DrawableGeometry;
import info.cafeda.simplegraphics.graphics.DrawableObject;
import info.cafeda.simplegraphics.graphics.FlatWhiteGrid;
import info.cafeda.simplegraphics.graphics.Square;
import info.cafeda.simplegraphics.graphics.Transform;
import info.cafeda.simplegraphics.graphics.Triangle;
import info.cafeda.simplegraphics.graphics.Vector3;

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
    private int length,width;
    public MyGLRenderer(String item, int length,int width){
        itemName = item;
        this.length = length;
        this.width = width;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glEnable( GLES20.GL_DEPTH_TEST );
        GLES20.glDepthFunc( GLES20.GL_LEQUAL );
        GLES20.glDepthMask( true );

        eye = new Vector3(0.0f,0.0f,-2.0f);
        centre = new Vector3(0,0,0);
        up = new Vector3(0,1,0 );

        if(itemName.equals("triangle"))
            objectToDraw = new Triangle();
        if(itemName.equals("square"))
            objectToDraw = new Square();
        if(itemName.equals("cube"))
            objectToDraw = new Cube();
        if(itemName.equals("geometry")) {
            FlatWhiteGrid terrain = new FlatWhiteGrid(length,width);
            objectToDraw = new DrawableGeometry(terrain.positions,terrain.colors,terrain.drawOrder,terrain.transformMatrix);
            //objectToDraw = new DrawableGeometry(terrain.positions,terrain.colors,terrain.drawOrder);
            eye = new Vector3(0.0f,1.0f,-1.0f);
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 0.1f, 100);
        Matrix.perspectiveM(mProjectionMatrix,0,90,ratio,0.1f,100);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
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
        if(objectToDraw.getClass().equals(DrawableGeometry.class) ){
            objectToDraw.drawLine(mMVPMatrix);
        }
        else{
            objectToDraw.draw(mMVPMatrix);
        }

    }

    public void setAngle(float angleLeft,float angleUp) {
        Transform.Left(-angleLeft,eye,up);
        Transform.Up(angleUp,eye,up);
        //Log.d("GL","Eye: " + eye.toString() + "  Up: " + up.toString());
    }
}
