package akeijzer.labyrinths.object;

import java.util.Iterator;

import akeijzer.labyrinths.game.World;
import akeijzer.labyrinths.game.level.LevelHandler;
import akeijzer.labyrinths.lib.Resources;
import akeijzer.labyrinths.maths.OverlapTester;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DeathPoint extends GameRect
{
    public Paint paint;
    private Bitmap icon;

    public DeathPoint(int posX, int posY, int sizeX, int sizeY, World world)
    {
        super(posX, posY, sizeX, sizeY, world);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(100);
        this.icon = Resources.pit;
        this.icon = Bitmap.createScaledBitmap(icon, sizeX, sizeY, false);
    }

    public void update()
    {
        super.update();
        Iterator<Ball> it = world.balls.iterator();
        while (it.hasNext())
        {
            Ball ball = it.next();
            if (OverlapTester.pointInRectangle(bounds, ball.posX, ball.posY))
            {
                LevelHandler.reloadLevel();
                break;
            }
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(icon, posX - sizeX / 2, posY - sizeY / 2, null);
    }
}
