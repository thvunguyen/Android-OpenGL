package info.cafeda.simplegraphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import info.cafeda.simplegraphics.graphics.Cube;
import info.cafeda.simplegraphics.graphics.Cubes;
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
    private DrawableGeometry[] objectsToDraw;
    private Vector3 eye, centre, up;
    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];

    public MyGLRenderer(DrawableGeometry[] objectsToDraw,Vector3 eye, Vector3 centre,Vector3 up) {
        this.objectsToDraw = objectsToDraw;
        this.eye = eye;
        this.centre = centre;
        this.up = up;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);

        if (objectsToDraw != null)
            for (int i = 0 ; i<objectsToDraw.length;i++)
                objectsToDraw[i].GLInit();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.perspectiveM(mProjectionMatrix, 0, 90, ratio, 0.1f, 100);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, eye.X, eye.Y, eye.Z, centre.X, centre.Y, centre.Z, up.X, up.Y, up.Z);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw shapes
        for (int i = 0; i < objectsToDraw.length; i++) {
            if (objectsToDraw[i].IsDrawLine()) {
                objectsToDraw[i].drawLine(mMVPMatrix);
            } else {
                objectsToDraw[i].draw(mMVPMatrix);
            }
        }

    }

    public void setAngle(float angleLeft, float angleUp) {
        Transform.Left(-angleLeft, eye, up);
        Transform.Up(angleUp, eye, up);
        //Log.d("GL","Eye: " + eye.toString() + "  Up: " + up.toString());
    }
}
