package akeijzer.labyrinths.object.upgrade;

import java.util.Iterator;

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

public class UpgradeIncreaseSize extends Upgrade
{
    private Bitmap icon;
    private Paint paint;

    public UpgradeIncreaseSize(int posX, int posY, GameView view)
    {
        super(posX, posY, view);
        icon = BitmapFactory.decodeResource(view.getResources(), R.drawable.increase_size);
        icon = Bitmap.createScaledBitmap(icon, sizeX, sizeY, false);
        paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY));
    }

    public void update()
    {
        Iterator<Ball> it = view.world.balls.iterator();
        while (it.hasNext())
        {
            Ball ball = it.next();
            if (OverlapTester.overlapCircleRectangle(ball.bounds, bounds))
            {
                ball.radius = (int) (ball.radius * 1.5);
                view.iObjects.remove();
            }
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(icon, posX - sizeX / 2, posY - sizeY / 2, paint);
    }
}
