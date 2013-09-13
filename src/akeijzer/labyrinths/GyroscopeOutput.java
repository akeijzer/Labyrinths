package akeijzer.labyrinths;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class GyroscopeOutput extends Activity implements SensorEventListener{

	SensorManager sensorManager = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

	@Override
	protected void onStop() 
	{
		super.onStop();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) 
	{
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) 
	{
		
	}

}
