package info.cafeda.simplegraphics;

import android.opengl.Matrix;
import android.util.Log;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

public class Transform {
    public static void Left(float degrees, Vector3 eye, Vector3 up ){
        float[] r = Rotate(-degrees,up);
        Vector3 tmp = new Vector3(eye.X,eye.Y,eye.Z);

        eye.X = Vector3.dot(tmp,new Vector3(r[0],r[1],r[2]));
        eye.Y = Vector3.dot(tmp,new Vector3(r[3],r[4],r[5]));
        eye.Z = Vector3.dot(tmp,new Vector3(r[6],r[7],r[8]));
    }
    public static void Up(float degrees, Vector3 eye, Vector3 up ){

        Vector3 rotateAxis = Vector3.cross(eye,up);
        float[] r = Rotate(-degrees,rotateAxis);
        Vector3 tmp = new Vector3(eye.X,eye.Y,eye.Z);

        eye.X = Vector3.dot(tmp,new Vector3(r[0],r[1],r[2]));
        eye.Y = Vector3.dot(tmp,new Vector3(r[3],r[4],r[5]));
        eye.Z = Vector3.dot(tmp,new Vector3(r[6],r[7],r[8]));
        tmp = Vector3.cross(rotateAxis.normalize(),eye);
        up.X = tmp.X;
        up.Y = tmp.Y;
        up.Z = tmp.Z;
    }
    public static float[] Rotate(float degrees, Vector3 axis){

        double theta = (degrees*Math.PI/180.0f);
        Vector3 u = axis.normalize();
        float[] result = new float[9];

        result[0] = (float) (cos(theta) + (1 - cos(theta))*(u.X*u.X));
        result[1] = (float) ((1 - cos(theta))*u.X*u.Y + sin(theta)*u.Z);
        result[2] = (float) ((1 - cos(theta))*u.X*u.Z - sin(theta)*u.Y);

        result[3] = (float) ((1 - cos(theta))*u.X*u.Y - sin(theta)*u.Z);
        result[4] = (float) (cos(theta) + (1 - cos(theta))*(u.Y*u.Y));
        result[5] = (float) ((1 - cos(theta))*u.Y*u.Z + sin(theta)*u.X);

        result[6] = (float) ((1 - cos(theta))*u.X*u.Z + sin(theta)*u.Y);
        result[7] = (float) ((1 - cos(theta))*u.Y*u.Z - sin(theta)*u.X);
        result[8] = (float) (cos(theta) + (1 - cos(theta))*(u.Z*u.Z));

        return result;
    }
}
