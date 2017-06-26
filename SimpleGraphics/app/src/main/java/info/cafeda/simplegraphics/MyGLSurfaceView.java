package info.cafeda.simplegraphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer mRenderer;
    private final float TOUCH_SCALE_FACTOR = 1.0f / 16;
    private float mPreviousX;
    private float mPreviousY;
    public MyGLSurfaceView(Context context, String item) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer(item);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }
     @Override
    public boolean onTouchEvent(MotionEvent e){
         // MotionEvent reports input details from the touch screen
         // and other input controls. In this case, you are only
         // interested in events where the touch position changed.

         float x = e.getX();
         float y = e.getY();

         switch (e.getAction()) {
             case MotionEvent.ACTION_MOVE:

                 float dx = x - mPreviousX;
                 float dy = y - mPreviousY;

                 mRenderer.setAngle(dx * TOUCH_SCALE_FACTOR,dy * TOUCH_SCALE_FACTOR);
                 requestRender();
         }

         mPreviousX = x;
         mPreviousY = y;
         return true;
     }
}
