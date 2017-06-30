package info.cafeda.simplegraphics.graphics;

/**
 * Created by Vu Nguyen on 6/26/2017.
 */

public class Vector3 {

    public static Vector3 cross(Vector3 v1, Vector3 v2){
        Vector3 result = new Vector3(0,0,0);
        result.X = v1.Y*v2.Z - v1.Z*v2.Y;
        result.Y = v1.Z*v2.X - v1.X*v2.Z;
        result.Z = v1.X*v2.Y - v1.Y*v2.X;
        return result;
    }
    public static float dot(Vector3 v1, Vector3 v2){
        return v1.X*v2.X+v1.Y*v2.Y+v1.Z*v2.Z;
    }
    public Vector3(float X,float Y, float Z){
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        //length = (float) Math.sqrt( X*X + Y*Y + Z*Z);
    }
    public float X;
    public float Y;
    public float Z;
    public float length(){
        return (float) Math.sqrt( X*X + Y*Y + Z*Z);
    }
    public Vector3 normalize(){
        float l = length();
        return new Vector3(X/l,Y/l,Z/l);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder(64);

        sb.append("[");
        sb.append(X);
        sb.append(", ");
        sb.append(Y);
        sb.append(", ");
        sb.append(Z);
        sb.append(']');
        return sb.toString();
    }
}
