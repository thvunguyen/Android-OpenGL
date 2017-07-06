package info.cafeda.simplegraphics;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import info.cafeda.simplegraphics.geoData.BasicSense;
import info.cafeda.simplegraphics.geoData.Cube;
import info.cafeda.simplegraphics.geoData.Cubes;
import info.cafeda.simplegraphics.geoData.FlatWhiteGrid;
import info.cafeda.simplegraphics.geoData.Square;
import info.cafeda.simplegraphics.geoData.Triangle;
import info.cafeda.simplegraphics.graphics.DrawableGeometry;
import info.cafeda.simplegraphics.graphics.Vector3;
import info.cafeda.simplegraphics.myGL.MyGLSurfaceView;

public class GraphicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String itemName = getIntent().getExtras().getString(MainActivity.OBJECT_TO_DRAW);
        int length = getIntent().getIntExtra("LENGTH", 0);
        int width = getIntent().getIntExtra("WIDTH", 0);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        MyGLSurfaceView mGLView;
        DrawableGeometry[] objectsToDraw = new DrawableGeometry[1];
        Vector3 eye = new Vector3(0.0f, 0.0f, -1.0f);
        Vector3 centre = new Vector3(0, 0, 0);
        Vector3 up = new Vector3(0, 1, 0);
        if ((length == 0) && (width == 0)) {
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
            if (itemName.equals("basicVR")) {
                BasicSense tmp = new BasicSense();
                objectsToDraw = tmp.objectsToDraw;
                eye = new Vector3(0.0f, 5.0f, -5.0f);
            }

        } else {
            if (itemName.equals("geometry")) {
                FlatWhiteGrid terrain = new FlatWhiteGrid(length, width);
                objectsToDraw[0] = new DrawableGeometry(terrain.positions, terrain.colors, terrain.drawOrder, terrain.transformMatrix, true);
                eye = new Vector3(0.0f, 1.0f, -1.0f);
            }
        }
        mGLView = new MyGLSurfaceView(this, objectsToDraw, eye, centre, up);
        setContentView(mGLView);
    }
}
