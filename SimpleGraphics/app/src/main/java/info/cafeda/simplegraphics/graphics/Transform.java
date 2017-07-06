package info.cafeda.simplegraphics.graphics;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

public class Transform {
    public static void Left(float degrees, Vector3 eye, Vector3 up ){
        float[] r = Rotate(-degrees,false, up);
        Vector3 tmp = new Vector3(eye.X,eye.Y,eye.Z);

        eye.X = Vector3.dot(tmp,new Vector3(r[0],r[1],r[2]));
        eye.Y = Vector3.dot(tmp,new Vector3(r[3],r[4],r[5]));
        eye.Z = Vector3.dot(tmp,new Vector3(r[6],r[7],r[8]));
    }
    public static void Up(float degrees, Vector3 eye, Vector3 up ){

        Vector3 rotateAxis = Vector3.cross(eye,up);
        float[] r = Rotate(-degrees,false,rotateAxis);
        Vector3 tmp = new Vector3(eye.X,eye.Y,eye.Z);

        eye.X = Vector3.dot(tmp,new Vector3(r[0],r[1],r[2]));
        eye.Y = Vector3.dot(tmp,new Vector3(r[3],r[4],r[5]));
        eye.Z = Vector3.dot(tmp,new Vector3(r[6],r[7],r[8]));
        tmp = Vector3.cross(rotateAxis.normalize(),eye);
        up.X = tmp.X;
        up.Y = tmp.Y;
        up.Z = tmp.Z;
    }
    public static float[] Rotate(float angle, boolean isRad, Vector3 axis){
        double theta;
        if (!isRad) {
            theta = (angle * Math.PI / 180.0f);
        }else{
            theta = angle;
        }
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

    // Play around with extended controls of device emulator to understand followed function
    public  static void Yaw(float rad, Vector3 eye, Vector3 centre, Vector3 up ){
        //Rotation axis is from eye to centre, vector to be rotated is up
        Vector3 rotateAxis = new Vector3(centre.X-eye.X,centre.Y-eye.Y,centre.Z-eye.Z);
        float[] r = Rotate(-rad,true,rotateAxis);

        Vector3 tmp = new Vector3(up.X,up.Y,up.Z);
        up.X = Vector3.dot(tmp,new Vector3(r[0],r[1],r[2]));
        up.Y = Vector3.dot(tmp,new Vector3(r[3],r[4],r[5]));
        up.Z = Vector3.dot(tmp,new Vector3(r[6],r[7],r[8]));
    }
    public  static void Pitch(float rad, Vector3 eye, Vector3 centre, Vector3 up ){
        //Rotation axis is cross(eye to centre, up), vectors to be rotated are centre and up
        Vector3 rotateAxis = Vector3.cross(new Vector3(centre.X-eye.X,centre.Y-eye.Y,centre.Z-eye.Z),up);
        float[] r = Rotate(-rad,true,rotateAxis);

        Vector3 tmp = new Vector3(centre.X,centre.Y,centre.Z);
        centre.X = Vector3.dot(tmp,new Vector3(r[0],r[1],r[2]));
        centre.Y = Vector3.dot(tmp,new Vector3(r[3],r[4],r[5]));
        centre.Z = Vector3.dot(tmp,new Vector3(r[6],r[7],r[8]));

        tmp = Vector3.cross(rotateAxis.normalize(),new Vector3(centre.X-eye.X,centre.Y-eye.Y,centre.Z-eye.Z));
        up.X = tmp.X;
        up.Y = tmp.Y;
        up.Z = tmp.Z;
    }
    public  static void Roll(float rad, Vector3 eye, Vector3 centre, Vector3 up ){
        //Rotation axis is up, vector to be rotated is centre
        Vector3 rotateAxis = new Vector3(up.X,up.Y,up.Z);
        float[] r = Rotate(-rad,true,rotateAxis);

        Vector3 tmp = new Vector3(centre.X,centre.Y,centre.Z);
        centre.X = Vector3.dot(tmp,new Vector3(r[0],r[1],r[2]));
        centre.Y = Vector3.dot(tmp,new Vector3(r[3],r[4],r[5]));
        centre.Z = Vector3.dot(tmp,new Vector3(r[6],r[7],r[8]));
    }
}
