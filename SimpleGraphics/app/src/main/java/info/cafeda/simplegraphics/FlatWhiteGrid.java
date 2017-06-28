package info.cafeda.simplegraphics;

import android.util.Log;

/**
 * Created by Vu Nguyen on 6/28/2017.
 */

public class FlatWhiteGrid {
    private int lenght, width;
    private int verticesCount;
    public float[] positions;
    public float[] colors;
    public short[] drawOrder;
    public FlatWhiteGrid(int l, int w){
        lenght = l;
        width = w;
        verticesCount = lenght *width;
        positions = new float[verticesCount*3];
        colors = new float[verticesCount*4];
        for (int z = 0; z < width; z++)
            for (int x = 0; x <lenght; x++)
            {
                positions[(x + z * lenght)*3 + 0] = x;
                positions[(x + z * lenght)*3 + 1] = 0.0f;
                positions[(x + z * lenght)*3 + 2] = z;
                colors[(x + z * lenght)*4 + 0] = 1.0f;
                colors[(x + z * lenght)*4 + 1] = 1.0f;
                colors[(x + z * lenght)*4 + 2] = 1.0f;
                colors[(x + z * lenght)*4 + 3] = 1.0f;
            }
        drawOrder = new short[((verticesCount * 2) - (width + lenght)) * 2];
        int k = 0;
        for (int z = 0; z < width; z++)
            for (int x = 0; x < lenght; x++)
            {
                if ((z != (width - 1)) && (x != (lenght - 1)))
                {
                    drawOrder[k] = (short) ( x + z * lenght);
                    drawOrder[k + 1] = (short) (x + (z + 1) * lenght);
                    drawOrder[k + 2] = (short) (x + z * lenght);
                    drawOrder[k + 3] = (short) ((x + 1) + z * lenght);
                    k += 4;
                }
                if ((z == (width - 1)) && (x != (lenght - 1)))
                {
                    drawOrder[k] = (short) (x + z * lenght);
                    drawOrder[k + 1] = (short) ((x + 1) + z * lenght);
                    k += 2;
                }
                if ((z != (width - 1)) && (x == (lenght - 1)))
                {
                    drawOrder[k] = (short) (x + z * lenght);
                    drawOrder[k + 1] = (short) (x + (z + 1) * lenght);
                    k += 2;
                }

            }
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i< drawOrder.length;i++)
            tmp.append(drawOrder[i]);
        Log.d("ORDER", tmp.toString());
        tmp = new StringBuilder();
        for (int i = 0; i< verticesCount;i++){
            tmp.append(positions[i+0]); tmp.append(" ");
            tmp.append(positions[i+1]); tmp.append(" ");
            tmp.append(positions[i+2]);
            tmp.append("; ");
        }
        Log.d("POS", tmp.toString());
        tmp = new StringBuilder();
        for (int i = 0; i< verticesCount;i++){
            tmp.append(colors[i+0]); tmp.append(" ");
            tmp.append(colors[i+1]); tmp.append(" ");
            tmp.append(colors[i+2]); tmp.append(" ");
            tmp.append(colors[i+3]);
            tmp.append("; ");
        }
        Log.d("COLOR", tmp.toString());
    }
}
