package akeijzer.labyrinths;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Orientation extends Activity implements SensorEventListener {

	SensorManager sensorManager = null;
	float[] accelerationArr = new float[3];
	float[] magneticFieldArr = new float[3];
	boolean gotAccArr;
	boolean gotMagArr;
	float[] rotationMatrix = new float[16];
	float[] orientationArr = new float[3];
	TextView orientation[] = new TextView[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.orientation);

		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		orientation[0] = (TextView) findViewById(R.id.tvOrientationX);
		orientation[1] = (TextView) findViewById(R.id.tvOrientationY);
		orientation[2] = (TextView) findViewById(R.id.tvOrientationZ);
	}

	@Override
	protected void onResume() {
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause() {
		super.onPause();
		sensorManager.unregisterListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
		sensorManager.unregisterListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		gotAccArr = false;
		gotMagArr = false;
		switch (event.sensor.getType()) {
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

		if (gotAccArr || gotMagArr) {
			boolean success = SensorManager.getRotationMatrix(rotationMatrix,
					null, accelerationArr, magneticFieldArr);
			if (success) {
				SensorManager.getOrientation(rotationMatrix, orientationArr);

				orientation[0].setText(Float.toString(orientationArr[0]));
				orientation[1].setText(Float.toString(orientationArr[1]));
				orientation[2].setText(Float.toString(orientationArr[2]));
			}
		}
	}
}
