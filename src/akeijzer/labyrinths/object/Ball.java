package akeijzer.labyrinths.object;

import akeijzer.labyrinths.R;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Ball extends GameObject
{
	public Bitmap icon;
	public int speed = 10;
	
	public Ball(int sizeX, int sizeY, GameView view)
	{
		super(sizeX, sizeY, view);
		posX = posY = 200;
		this.icon = BitmapFactory.decodeResource(view.getResources(), R.drawable.options);
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(icon, posX - icon.getWidth()/2, posY - icon.getHeight()/2, null);
	}
	
	public void update()
	{
    	if (view.orientation[1] > 0 && posY != icon.getHeight()/2)
    	{
    		posY = posY - speed;
    	}
    	else if (view.orientation[1] < 0 && !(posY >= view.getHeight() - icon.getHeight()/2))
    	{
    		posY = posY + speed;
    	}
    	
    	if (view.orientation[2] > 0 && !(posX >= view.getWidth() - icon.getWidth()/2))
    	{
    		posX = posX + speed;
    	}
    	else if (view.orientation[2] < 0 && posX != icon.getWidth()/2)
    	{
    		posX = posX - speed;
    	}
	}
}
