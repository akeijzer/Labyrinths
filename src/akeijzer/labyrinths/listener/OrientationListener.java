package akeijzer.labyrinths.listener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationListener implements SensorEventListener
{
    SensorManager sensorManager;
    float[] accelerationArr = new float[3];
    float[] magneticFieldArr = new float[3];
    boolean newValue;
    private float[] rotationMatrix = new float[16];
    private float[] orientationArr = new float[3];

    public OrientationListener(SensorManager sensorManager)
    {
        this.sensorManager = sensorManager;
    }

    public float[] getOrientation()
    {
        calculateOrientation();
        return orientationArr;
    }

    public void onPause()
    {
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD));
    }

    public void onResume()
    {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        switch (event.sensor.getType())
        {
        case Sensor.TYPE_ACCELEROMETER:
            System.arraycopy(event.values, 0, accelerationArr, 0, 3);
            newValue = true;
            break;
        case Sensor.TYPE_MAGNETIC_FIELD:
            System.arraycopy(event.values, 0, magneticFieldArr, 0, 3);
            newValue = true;
            break;
        default:
            return;
        }
    }

    public void calculateOrientation()
    {
        if (newValue)
        {
            boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, accelerationArr, magneticFieldArr);
            if (success)
            {
                SensorManager.getOrientation(rotationMatrix, orientationArr);
            }
            newValue = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
    }
}
