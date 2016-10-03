package brad.tw.mysensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager smgr;
    private Sensor sensor;
    private MySensorListener listener;
    private TextView textX,textY, textZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textX = (TextView)findViewById(R.id.vX);
        textY = (TextView)findViewById(R.id.vY);
        textZ = (TextView)findViewById(R.id.vZ);


        smgr = (SensorManager)getSystemService(SENSOR_SERVICE);

        List<Sensor> sensors = smgr.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s: sensors){
            String name = s.getName();
            String vendor = s.getVendor();
            Log.v("brad", name + ":" + vendor);
        }

        sensor = smgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new MySensorListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        smgr.registerListener(listener,sensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        smgr.unregisterListener(listener);
    }

    private class MySensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            float vx, vy, vz;
            vx = values[0];
            vy = values[1];
            vz = values[2];

            textX.setText("X: " + vx);
//            textY.setText("Y: " + vy);
//            textZ.setText("Z: " + vz);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


}
