package akeijzer.labyrinths;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity
{

    int count;
    TextView shownText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = 0;
        shownText = (TextView) findViewById(R.id.textViewNumber);
        updateNumber();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void addOne(View view)
    {
        count++;
        updateNumber();
    }

    public void subOne(View view)
    {
        count--;
        updateNumber();
    }

    public void updateNumber()
    {
        shownText.setText("Number = " + count);
    }
}