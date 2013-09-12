package akeijzer.labyrinths;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
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
					Intent openMainActivity = new Intent("akeijzer.labyrinths.MAIN");
					startActivity(openMainActivity);
				}
			}
		};
		timer.start();
	}
	
}
