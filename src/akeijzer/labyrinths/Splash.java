package akeijzer.labyrinths;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends Activity
{
	MediaPlayer startSound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		startSound = MediaPlayer.create(Splash.this, R.raw.starting_sound);
		startSound.start();
		Thread timer = new Thread()
		{
			public void run()
			{
				try
				{
					sleep(1000);
				}
				catch(Exception e)
				{
					
				}
				finally
				{
					Intent openMainActivity = new Intent("akeijzer.labyrinths.MENU");
					startActivity(openMainActivity);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		startSound.release();
		finish();
	}
	
	
	
}
