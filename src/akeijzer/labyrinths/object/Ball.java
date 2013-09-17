package akeijzer.labyrinths.object;

import akeijzer.labyrinths.R;
import akeijzer.labyrinths.game.GameThread;
import akeijzer.labyrinths.physics.Physics;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

public class Ball extends GameObject
{
	public Bitmap icon;
	public float mass;
	public float velocityX;
	public float velocityY;
	
	public Ball(int sizeX, int sizeY, float mass, GameView view)
	{
		super(sizeX, sizeY, view);
		this.mass = mass;
		posX = posY = 200;
		this.icon = BitmapFactory.decodeResource(view.getResources(), R.drawable.options);
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(icon, posX - icon.getWidth()/2, posY - icon.getHeight()/2, null);
	}
	
	public void update()
	{
		velocityX += (Physics.calculateAcceleration(mass, view.orientation[2], 0.20F)*GameThread.SECONDS_PER_FRAME);
		velocityY += (Physics.calculateAcceleration(mass, -view.orientation[1], 0.20F)*GameThread.SECONDS_PER_FRAME);
		float posDiffMeterX = velocityX*GameThread.SECONDS_PER_FRAME;
		float posDiffMeterY = velocityY*GameThread.SECONDS_PER_FRAME;
		Log.d("pdm", Float.toString(posDiffMeterX));
		posX += (velocityX*GameThread.SECONDS_PER_FRAME)*126;
		posY += (velocityY*GameThread.SECONDS_PER_FRAME)*126;
	}
}
