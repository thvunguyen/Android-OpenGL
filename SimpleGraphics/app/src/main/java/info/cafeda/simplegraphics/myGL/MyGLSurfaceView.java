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

    public MyGLSurfaceView(Context context, String itemName) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        DrawableGeometry[] objectsToDraw = new DrawableGeometry[1];
        Vector3 eye = new Vector3(0.0f, 0.0f, -1.0f);
        Vector3 centre = new Vector3(0, 0, 0);
        Vector3 up = new Vector3(0, 1, 0);
        if (itemName.equals("triangle")) {
            objectsToDraw[0] = new DrawableGeometry(Triangle.positions, Triangle.colors, Triangle.drawOrder);
        }
        if (itemName.equals("square")) {
            objectsToDraw[0] = new DrawableGeometry(Square.positions, Square.colors, Square.drawOrder);
        }
        if (itemName.equals("cube")) {
            objectsToDraw[0] = new DrawableGeometry(Cube.positions, Cube.colors, Cube.drawOrder);
            eye = new Vector3(0.0f, 2.0f, -2.0f);
        }
        if (itemName.equals("cubes")) {
            Cubes tmp = new Cubes();
            objectsToDraw = tmp.objectsToDraw;
            eye = new Vector3(0.0f, 5.0f, -5.0f);
        }

        mRenderer = new MyGLRenderer(objectsToDraw, eye, centre, up);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }

    public MyGLSurfaceView(Context context, String itemName, int length, int width) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        DrawableGeometry[] objectsToDraw = new DrawableGeometry[1];
        Vector3 eye = new Vector3(0.0f, 0.0f, -1.0f);
        Vector3 centre = new Vector3(0, 0, 0);
        Vector3 up = new Vector3(0, 1, 0);
        if (itemName.equals("geometry")) {
            FlatWhiteGrid terrain = new FlatWhiteGrid(length, width);
            objectsToDraw[0] = new DrawableGeometry(terrain.positions, terrain.colors, terrain.drawOrder, terrain.transformMatrix, true);
            eye = new Vector3(0.0f, 1.0f, -1.0f);
        }
        mRenderer = new MyGLRenderer(objectsToDraw, eye, centre, up);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

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
        return true;
    }
}
