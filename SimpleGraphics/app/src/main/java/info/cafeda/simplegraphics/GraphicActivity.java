package info.cafeda.simplegraphics;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class GraphicActivity extends Activity {

    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String item = getIntent().getExtras().getString(MainActivity.OBJECT_TO_DRAW);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        mGLView = new MyGLSurfaceView(this,item);
        setContentView(mGLView);
    }
}
