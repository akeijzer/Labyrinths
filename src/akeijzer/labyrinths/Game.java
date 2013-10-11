package akeijzer.labyrinths;

import akeijzer.labyrinths.listener.OrientationListener;
import akeijzer.labyrinths.view.GameView;
import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;

public class Game extends Activity
{
    public GameView view;
    public OrientationListener orientation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        orientation = new OrientationListener((SensorManager) getSystemService(SENSOR_SERVICE));

        view = new GameView(this);
        setContentView(view);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        orientation.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        orientation.onResume();
    }

}