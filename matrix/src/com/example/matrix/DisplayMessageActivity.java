package com.example.matrix;

import java.util.Vector;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends Activity {

	private SensorManager mSensorManager;

	private static final int JUMP_THRESH = 15;

	private Sensor mSensor_acc;
	private Sensor mSensor_gravity;

	private SensorEventListener acc_listener;
	private SensorEventListener grav_listener;
	
	private Tuple3D tuple_grav;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
		// Show the Up button in the action bar.
		setupActionBar();

		// Get the message from the intent
		// Intent intent = getIntent();
		// String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

		// Create the text view
		// TextView textView = new TextView(this);
		// textView.setTextSize(40);
		// textView.setText(message);

		// Set the text view as the activity layout
		// setContentView(textView);

		// Create the Sensor Manager
		tuple_grav = new Tuple3D(0,0,1);
		mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		mSensor_acc = mSensorManager
				.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mSensor_gravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

		acc_listener = new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				float values[] = event.values;
				Tuple3D acc = new Tuple3D(values[0], values[1], values[2]);
				if (acc.getLength() < 5){
					return;
				}
				acc.normalize();
				
				((TextView) findViewById(R.id.x_axis)).setText("AccX:"
						+ String.valueOf(acc.get_X_Angle()));
				((TextView) findViewById(R.id.y_axis)).setText("AccY:"
						+ String.valueOf(acc.get_Y_Angle()));
				((TextView) findViewById(R.id.z_axis)).setText("AccZ:"
						+ String.valueOf(acc.get_Z_Angle()));
				
				if (acc.isLinearDependant(tuple_grav)){
					((TextView) findViewById(R.id.result)).setText("gesprungen");
				}
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};

		grav_listener = new SensorEventListener() {

			@Override
			public void onSensorChanged(SensorEvent event) {
				
				tuple_grav = new Tuple3D(event.values[0],event.values[1], event.values[2]);
				tuple_grav.normalize();
				
				((TextView) findViewById(R.id.x_grav_axis)).setText("X:"
						+ String.valueOf(tuple_grav.get_X_Angle()));
				((TextView) findViewById(R.id.y_grav_axis)).setText("Y:"
						+ String.valueOf(tuple_grav.get_Y_Angle()));
				((TextView) findViewById(R.id.z_grav_axis)).setText("Z:"
						+ String.valueOf(tuple_grav.get_Z_Angle()));
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {

			}
		};

	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(acc_listener, mSensor_acc,
				SensorManager.SENSOR_DELAY_NORMAL);
		mSensorManager.registerListener(grav_listener, mSensor_gravity,
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onPause() {
		super.onResume();
		mSensorManager.unregisterListener(acc_listener);
		mSensorManager.unregisterListener(grav_listener);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
