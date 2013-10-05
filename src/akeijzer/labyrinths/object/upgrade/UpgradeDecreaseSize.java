package akeijzer.labyrinths.object.upgrade;

import akeijzer.labyrinths.R;
import akeijzer.labyrinths.maths.OverlapTester;
import akeijzer.labyrinths.object.Ball;
import akeijzer.labyrinths.view.GameView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

public class UpgradeDecreaseSize extends Upgrade
{
	private Bitmap icon;
	private Paint paint;
	
	public UpgradeDecreaseSize(int posX, int posY, GameView view)
	{
		super(posX, posY, view);
		icon = BitmapFactory.decodeResource(view.getResources(), R.drawable.decrease_size);
		icon = Bitmap.createScaledBitmap(icon, sizeX, sizeY, false);
		paint = new Paint();
		paint.setColorFilter(new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY));
	}
	
	public boolean update()
	{
		for (Ball ball : view.world.balls)
		{
			if (OverlapTester.overlapCircleRectangle(ball.bounds, bounds))
			{
				ball.radius = (int) (ball.radius*0.75);
				return true;
			}
		}
		return false;
	}
	
	public void draw(Canvas canvas)
	{
		canvas.drawBitmap(icon, posX - sizeX/2, posY - sizeY/2, paint);
	}
}
