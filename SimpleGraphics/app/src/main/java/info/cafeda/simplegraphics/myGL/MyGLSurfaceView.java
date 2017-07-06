package info.cafeda.simplegraphics.myGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import info.cafeda.simplegraphics.geoData.Cube;
import info.cafeda.simplegraphics.geoData.Cubes;
import info.cafeda.simplegraphics.graphics.DrawableGeometry;
import info.cafeda.simplegraphics.geoData.FlatWhiteGrid;
import info.cafeda.simplegraphics.geoData.Square;
import info.cafeda.simplegraphics.geoData.Triangle;
import info.cafeda.simplegraphics.graphics.Vector3;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;
    private float mPreviousX;
    private float mPreviousY;
    private boolean isTouchable = true;

    public MyGLSurfaceView(Context context, DrawableGeometry[] objectsToDraw, Vector3 eye, Vector3 centre, Vector3 up) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer(objectsToDraw, eye, centre, up);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        if (isTouchable) {
            float x = e.getX();
            float y = e.getY();

            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:

                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    float TOUCH_SCALE_FACTOR = 1.0f / 16;
                    mRenderer.setAngle(dx * TOUCH_SCALE_FACTOR, dy * TOUCH_SCALE_FACTOR);
                    requestRender();
            }

            mPreviousX = x;
            mPreviousY = y;
        }
        return true;
    }
    public void setRotate(){
        isTouchable = false;
    }
    public boolean onRotateEvent(float radX, float radY, float radZ) {
        if (!isTouchable) {
            mRenderer.setRotate(radX, radY, radZ);
            requestRender();
        }
        return true;
    }
}
