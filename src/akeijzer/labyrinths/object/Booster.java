package akeijzer.labyrinths.object;

import java.util.Iterator;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.maths.OverlapTester;
import akeijzer.labyrinths.maths.Side;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Booster extends GameRect
{
    public Paint paint;
    private Side boostingSide;

    public Booster(int posX, int posY, int sizeX, int sizeY, Side boostingSide, World world)
    {
        super(posX, posY, sizeX, sizeY, world);
        this.boostingSide = boostingSide;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(100);
    }

    @Override
    public void draw(Canvas canvas)
    {
        canvas.drawRect(posX - sizeX / 2, posY + sizeY / 2, posX + sizeX / 2, posY - sizeY / 2, paint);
    }

    @Override
    public void update()
    {
        super.update();
        Iterator<Ball> it = world.balls.iterator();
        while (it.hasNext())
        {
            Ball ball = it.next();
            if (OverlapTester.overlapCircleRectangle(ball.bounds, bounds))
            {
                if (boostingSide == Side.LEFT)
                {
                    ball.velocityX = ball.velocityX - ball.density / 5;
                }
                else if (boostingSide == Side.RIGHT)
                {
                    ball.velocityX = ball.velocityX + ball.density / 5;
                }
                else if (boostingSide == Side.TOP)
                {
                    ball.velocityY = ball.velocityY - ball.density / 5;
                }
                else if (boostingSide == Side.BOTTOM)
                {
                    ball.velocityY = ball.velocityY + ball.density / 5;
                }
            }
        }
    }

}
