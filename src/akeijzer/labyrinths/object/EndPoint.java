package akeijzer.labyrinths.object;

import java.util.Iterator;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.maths.OverlapTester;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EndPoint extends GameRect
{
    public Paint paint;

    public EndPoint(int posX, int posY, int sizeX, int sizeY, World world)
    {
        super(posX, posY, sizeX, sizeY, world);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAlpha(100);
    }

    public void update()
    {
        super.update();
        Iterator<Ball> it = world.balls.iterator();
        while (it.hasNext())
        {
            Ball ball = it.next();
            if (OverlapTester.overlapCircleRectangle(ball.bounds, bounds))
            {
                ball.kill();
            }
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawRect(posX - sizeX / 2, posY + sizeY / 2, posX + sizeX / 2, posY - sizeY / 2, paint);
    }
}
