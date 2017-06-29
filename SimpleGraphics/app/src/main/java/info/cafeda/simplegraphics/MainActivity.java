package info.cafeda.simplegraphics;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static String OBJECT_TO_DRAW = "object_to_draw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "thvu.nguyen01@gmail.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickTriangleButtonHandler(View view) {
        String item = "triangle";
        Intent intent = new Intent(this, GraphicActivity.class);
        intent.putExtra(OBJECT_TO_DRAW, item);
        startActivity(intent);
    }

    public void clickCubeButtonHandler(View view) {
        String item = "cube";
        Intent intent = new Intent(this, GraphicActivity.class);
        intent.putExtra(OBJECT_TO_DRAW, item);
        startActivity(intent);
    }

    public void clickSquareButtonHandler(View view) {
        String item = "square";
        Intent intent = new Intent(this, GraphicActivity.class);
        intent.putExtra(OBJECT_TO_DRAW, item);
        startActivity(intent);
    }

    public void clickGeometryHandler(View view) {

        Intent intent = new Intent(this, GeometryActivity.class);
        startActivity(intent);
    }
}
