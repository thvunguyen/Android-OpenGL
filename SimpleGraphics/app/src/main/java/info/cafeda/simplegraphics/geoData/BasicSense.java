package info.cafeda.simplegraphics.geoData;

import android.opengl.Matrix;

import info.cafeda.simplegraphics.graphics.DrawableGeometry;

/**
 * Created by Vu Nguyen on 7/6/2017.
 */

public class BasicSense {
    public DrawableGeometry[] objectsToDraw;
    public BasicSense(){
        float[] tmpTransformMatrix = new float [16];
        objectsToDraw = new DrawableGeometry[5];

        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,-4.0f,0.0f,-4.0f);
        FlatWhiteGrid terrain = new FlatWhiteGrid(9, 9);
        objectsToDraw[0] = new DrawableGeometry(terrain.positions, terrain.colors, terrain.drawOrder, tmpTransformMatrix, true);

        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,2.5f,0.5f,0.0f);
        objectsToDraw[1] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);

        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,-2.5f,0.5f,0.0f);
        objectsToDraw[2] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);

        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,0.0f,0.5f,2.5f);
        objectsToDraw[3] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);

        Matrix.setIdentityM(tmpTransformMatrix,0);
        Matrix.translateM(tmpTransformMatrix,0,0.0f,0.5f,-2.5f);
        objectsToDraw[4] = new DrawableGeometry(Cube.positions,Cube.colors,Cube.drawOrder,tmpTransformMatrix,false);
    }
}
