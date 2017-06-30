package info.cafeda.simplegraphics;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class GraphicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String item = getIntent().getExtras().getString(MainActivity.OBJECT_TO_DRAW);
        int length = getIntent().getIntExtra("LENGTH",0);
        int width = getIntent().getIntExtra("WIDTH",0);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        GLSurfaceView mGLView;
        if ((length == 0)&&(width==0)) {
            mGLView = new MyGLSurfaceView(this, item);
        }else{
            mGLView = new MyGLSurfaceView(this, item,length,width);
        }
        setContentView(mGLView);
    }
}
