package info.cafeda.simplegraphics;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static info.cafeda.simplegraphics.MainActivity.OBJECT_TO_DRAW;

public class GeometryActivity extends AppCompatActivity {

    EditText geometryLenght, geometryWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry);
        geometryLenght = (EditText) findViewById(R.id.geometryLength);
        geometryWidth = (EditText) findViewById(R.id.geometryWidth);
    }

    public void clickCreateGeometry(View view) {
        String length = geometryLenght.getText().toString();
        String width = geometryWidth.getText().toString();
        if ((Integer.parseInt(length)>0) && (Integer.parseInt(width)>0)) {
            String item = "geometry";
            Intent intent = new Intent(this, GraphicActivity.class);
            intent.putExtra(OBJECT_TO_DRAW, item);
            intent.putExtra("LENGHT", Integer.parseInt(length));
            intent.putExtra("WIDTH", Integer.parseInt(width));
            startActivity(intent);
        }else{
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Invalid size", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }
}
