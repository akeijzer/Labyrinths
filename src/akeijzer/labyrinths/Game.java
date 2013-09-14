package akeijzer.labyrinths;

import akeijzer.labyrinths.view.GameView;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class Game extends Activity implements SensorEventListener
{
	GameView view;
	
	SensorManager sensorManager = null;
	float[] accelerationArr = new float[3];
	float[] magneticFieldArr = new float[3];
	boolean gotAccArr;
	boolean gotMagArr;
	float[] rotationMatrix = new float[16];
	float[] orientationArr = new float[3];
	
	public float x = 150F;
	public float y = 250F;
	float speed = 5F;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		view = new GameView(this);
		setContentView(view);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
		sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
		view.pause();
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
		view.resume();
	}
	
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) 
	{
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		gotAccArr = false;
		gotMagArr = false;
		switch (event.sensor.getType()) 
		{
        	case Sensor.TYPE_ACCELEROMETER:
                 System.arraycopy(event.values, 0, accelerationArr, 0, 3);
                 gotAccArr = true;
                 break;
        	case Sensor.TYPE_MAGNETIC_FIELD:
                 System.arraycopy(event.values, 0, magneticFieldArr, 0, 3);
                 gotMagArr = true;
                 break;
        	default:
                 return;
		}
		
		if (gotAccArr || gotMagArr)
		{
			boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, accelerationArr, magneticFieldArr);
            if (success) 
            {
            	SensorManager.getOrientation(rotationMatrix, orientationArr);
            	
            	if (orientationArr[1] > 0 && y != 0)
            	{
            		y = y - speed;
            	}
            	else if (orientationArr[1] < 0 && !(y >= view.getHeight() - 350))
            	{
            		y = y + speed;
            	}
            	
            	if (orientationArr[2] > 0 && !(x >= view.getWidth() - 350))
            	{
            		x = x + speed;
            	}
            	else if (orientationArr[2] < 0 && x != 0)
            	{
            		x = x - speed;
            	}
            }
		}
	}
}
