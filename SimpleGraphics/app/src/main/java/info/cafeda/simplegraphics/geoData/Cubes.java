package info.cafeda.simplegraphics.geoData;

import android.opengl.Matrix;

import info.cafeda.simplegraphics.graphics.DrawableGeometry;

/**
 * Created by Vu Nguyen on 7/4/2017.
 */

public class Cubes {
    public DrawableGeometry[] objectsToDraw;
    public Cubes(){
        float[] tmpTransformMatrix = new float [16];
        objectsToDraw = new DrawableGeometry[5];
        Matrix.setIdentityM(tmpTransformMatrix,0);
        objectsToDraw[0] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);
        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,1.5f,0.0f,0.0f);
        objectsToDraw[1] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);
        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,-1.5f,0.0f,0.0f);
        objectsToDraw[2] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);
        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,0.0f,0.0f,1.5f);
        objectsToDraw[3] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);
        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,0.0f,0.0f,-1.5f);
        objectsToDraw[4] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);
    }
}
